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

List<AssetRendererFactory<?>> classTypesAssetRendererFactories = new ArrayList<>();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<div class="portlet-configuration-body-content">
	<aui:form action="<%= configurationActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit="event.preventDefault();">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />
		<aui:input name="groupId" type="hidden" />
		<aui:input name="typeSelection" type="hidden" />
		<aui:input name="assetEntryId" type="hidden" />
		<aui:input name="assetEntryOrder" type="hidden" value="-1" />
		<aui:input name="assetEntryType" type="hidden" />

		<%
		request.setAttribute("configuration.jsp-classTypesAssetRendererFactories", classTypesAssetRendererFactories);
		request.setAttribute("configuration.jsp-configurationRenderURL", configurationRenderURL);
		request.setAttribute("configuration.jsp-redirect", redirect);
		%>

		<liferay-ui:form-navigator
			id="<%= AssetPublisherConstants.FORM_NAVIGATOR_ID_CONFIGURATION %>"
			markupView="lexicon"
			showButtons="<%= false %>"
		/>

		<aui:button-row>
			<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />saveSelectBoxes() {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('classNameIds').val(Util.listSelect(form.fm('currentClassNameIds')));

		<%
		for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
			String className = AssetPublisherUtil.getClassName(curRendererFactory);
		%>

			form.fm('classTypeIds<%= className %>').val(Util.listSelect(form.fm('<%= className %>currentClassTypeIds')));

		<%
		}
		%>

		form.fm('metadataFields').val(Util.listSelect(form.fm('currentMetadataFields')));

		submitForm(form);
	}
</aui:script>