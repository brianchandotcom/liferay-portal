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

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

AssetListEntry assetListEntry = assetListDisplayContext.getAssetListEntry();
%>

<portlet:actionURL name="/asset_list/update_asset_list_entry_dynamic" var="updateAssetListEntryDynamicURL" />

<liferay-frontend:edit-form
	action="<%= updateAssetListEntryDynamicURL %>"
	cssClass="pt-0"
	fluid="<%= true %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= assetListDisplayContext.getSegmentsEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryType() %>" />

	<aui:model-context bean="<%= assetListEntry %>" model="<%= AssetListEntry.class %>" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-title">
			<div class="autofit-row autofit-row-center">
				<div class="autofit-col">
					<%= HtmlUtil.escape(editAssetListDisplayContext.getSegmentsEntryName(editAssetListDisplayContext.getSegmentsEntryId(), locale)) %>
				</div>

				<div class="autofit-col autofit-col-end inline-item-after">
					<liferay-util:include page="/asset_list_entry_variation_action.jsp" servletContext="<%= application %>" />
				</div>
			</div>
		</h3>

		<liferay-frontend:form-navigator
			formModelBean="<%= assetListEntry %>"
			id="<%= AssetListFormConstants.FORM_NAVIGATOR_ID %>"
			showButtons="<%= false %>"
		/>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>