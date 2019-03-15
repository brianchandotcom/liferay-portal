<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

String uploadProgressId = PortalUtil.generateRandomKey(request, "portlet_wiki_import_pages_uploadProgressId");
String importProgressId = PortalUtil.generateRandomKey(request, "portlet_wiki_import_pages_importProgressId");

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

long nodeId = BeanParamUtil.getLong(node, request, "nodeId");

WikiImporterTracker wikiImporterTracker = (WikiImporterTracker)request.getAttribute(WikiWebKeys.WIKI_IMPORTER_TRACKER);

String[] importers = ArrayUtil.toStringArray(wikiImporterTracker.getImporters());

if (Validator.isNull(tabs2)) {
	tabs2 = importers[0];
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/wiki/import_pages");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("nodeId", String.valueOf(nodeId));

portletDisplay.setShowBackIcon(true);

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);

PortletURL backToNodeURL = wikiURLHelper.getBackToNodeURL(node);

portletDisplay.setURLBack(backToNodeURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "import-pages"));
%>

<portlet:actionURL name="/wiki/import_pages" var="importPagesURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= importPagesURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "importPages();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="importProgressId" type="hidden" value="<%= importProgressId %>" />
		<aui:input name="nodeId" type="hidden" value="<%= nodeId %>" />
		<aui:input name="importer" type="hidden" value="<%= tabs2 %>" />

		<aui:fieldset-group markupView="lexicon">
			<liferay-ui:tabs
				names="<%= StringUtil.merge(importers) %>"
				param="tabs2"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:error exception="<%= ImportFilesException.class %>" message="please-provide-all-mandatory-files-and-make-sure-the-file-types-are-valid" />
			<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="the-node-could-not-be-found" />

			<liferay-util:include page='<%= wikiImporterTracker.getProperty(tabs2, "page") %>' servletContext="<%= application %>" />
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" value="import" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>

	<liferay-ui:upload-progress
		id="<%= uploadProgressId %>"
		message="uploading"
	/>

	<liferay-ui:upload-progress
		id="<%= importProgressId %>"
		message="importing"
	/>
</div>

<aui:script>
	function <portlet:namespace />importPages() {
		<%= uploadProgressId %>.startProgress();
		<%= importProgressId %>.startProgress();

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>