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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

LayoutsSEODisplayContext layoutsSEODisplayContext = (LayoutsSEODisplayContext)request.getAttribute(LayoutSEOWebKeys.LAYOUT_PAGE_LAYOUT_SEO_DISPLAY_CONTEXT);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsSEODisplayContext.getSelLayout(), themeDisplay);
}

Layout selLayout = layoutsSEODisplayContext.getSelLayout();
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<portlet:actionURL copyCurrentRenderParameters="<%= true %>" name="/layout/edit_open_graph" var="editOpenGraphURL" />

<aui:form action="<%= editOpenGraphURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
	<aui:input name="groupId" type="hidden" value="<%= layoutsSEODisplayContext.getGroupId() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsSEODisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsSEODisplayContext.getLayoutId() %>" />

	<div class="sheet sheet-lg">
		<div class="sheet-header">
			<h2 class="sheet-title"><liferay-ui:message key="open-graph" /></h2>
		</div>

		<div class="sheet-section">
			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="open-graph"
			/>

			<p class="text-muted">
				<liferay-ui:message key="open-graph-description" />
			</p>

			<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

			<div class="form-group">
				<label class="control-label"><liferay-ui:message key="image" /> <liferay-ui:icon-help message="open-graph-image-help" /></label>

				<aui:input label="<%= StringPool.BLANK %>" name="openGraphImageTitle" placeholder="image" readonly="<%= true %>" title="image" type="text" value="<%= layoutsSEODisplayContext.getOpenGraphImageTitle() %>" wrapperCssClass="mb-3" />

				<aui:button-row cssClass="mt-0">
					<aui:button name="openGraphImageButton" value="select" />
					<aui:button name="openGraphClearImageButton" value="clear" />
				</aui:button-row>
			</div>

			<%
			LayoutSEOEntry selLayoutSEOEntry = layoutsSEODisplayContext.getSelLayoutSEOEntry();
			%>

			<div id="<portlet:namespace />openGraphSettings">
				<aui:model-context bean="<%= selLayoutSEOEntry %>" model="<%= LayoutSEOEntry.class %>" />

				<aui:input disabled="<%= (selLayoutSEOEntry != null) ? Validator.isNull(layoutsSEODisplayContext.getOpenGraphImageTitle()) : true %>" helpMessage="open-graph-image-alt-description-help" label="open-graph-image-alt-description" name="openGraphImageAlt" placeholder="open-graph-alt-description" />

				<aui:input checked="<%= (selLayoutSEOEntry != null) ? selLayoutSEOEntry.isOpenGraphTitleEnabled() : false %>" helpMessage="use-custom-open-graph-title-help" label="use-custom-open-graph-title" name="openGraphTitleEnabled" type="checkbox" wrapperCssClass="mb-1" />

				<aui:input disabled="<%= (selLayoutSEOEntry != null) ? !selLayoutSEOEntry.isOpenGraphTitleEnabled() : true %>" label="<%= StringPool.BLANK %>" name="openGraphTitle" placeholder="title" />

				<aui:input checked="<%= (selLayoutSEOEntry != null) ? selLayoutSEOEntry.isOpenGraphDescriptionEnabled() : false %>" helpMessage="use-custom-open-graph-description-help" label="use-custom-open-graph-description" name="openGraphDescriptionEnabled" type="checkbox" wrapperCssClass="mb-1" />

				<aui:input disabled="<%= (selLayoutSEOEntry != null) ? !selLayoutSEOEntry.isOpenGraphDescriptionEnabled() : true %>" label="<%= StringPool.BLANK %>" name="openGraphDescription" placeholder="description" />

				<aui:input id="openGraphImageFileEntryId" name="openGraphImageFileEntryId" type="hidden" />
			</div>

			<div class="form-group">
				<label><liferay-ui:message key="preview" /> <liferay-ui:icon-help message="preview-help" /></label>

				<div>

					<%
					Map<String, Object> data = HashMapBuilder.<String, Object>put(
						"displayType", "og"
					).put(
						"targets",
						HashMapBuilder.<String, Object>put(
							"description",
							HashMapBuilder.<String, Object>put(
								"defaultValue", selLayout.getDescriptionMap()
							).put(
								"id", "openGraphDescription"
							).build()
						).put(
							"imgUrl",
							HashMapBuilder.<String, Object>put(
								"defaultValue",
								layoutsSEODisplayContext.getDefaultOpenGraphImageURL()
							).put(
								"value",
								layoutsSEODisplayContext.getOpenGraphImageURL()
							).build()
						).put(
							"title",
							HashMapBuilder.<String, Object>put(
								"defaultValue", layoutsSEODisplayContext.getDefaultPageTitleMap()
							).put(
								"id", "openGraphTitle"
							).build()
						).put(
							"url",
							Collections.singletonMap("defaultValue", layoutsSEODisplayContext.getDefaultCanonicalURLMap())
						).build()
					).put(
						"titleSuffix", layoutsSEODisplayContext.getPageTitleSuffix()
					).build();
					%>

					<react:component
						data="<%= data %>"
						module="js/seo/PreviewSeo.es"
						servletContext="<%= application %>"
					/>
				</div>
			</div>
		</div>

		<div class="sheet-footer">
			<aui:button primary="<%= true %>" type="submit" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</div>
	</div>
</aui:form>

<portlet:actionURL name="/layout/upload_open_graph_image" var="uploadOpenGraphImageURL" />

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"uploadOpenGraphImageURL", layoutsSEODisplayContext.getItemSelectorURL()
		).build()
	%>'
	module="js/seo/openGraph.es"
	servletContext="<%= application %>"
/>