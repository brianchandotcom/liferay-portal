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
portletDisplay.setURLBack(editAssetListDisplayContext.getBackURL());

AssetListEntry assetListEntry = assetListDisplayContext.getAssetListEntry();
%>

<portlet:actionURL name="/asset_list/update_asset_list_entry_dynamic" var="updateAssetListEntryDynamicURL">
	<portlet:param name="mvcPath" value="/edit_asset_list_entry.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= updateAssetListEntryDynamicURL %>"
	cssClass="pt-0"
	fluid="<%= true %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= editAssetListDisplayContext.getBackURL() %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= assetListDisplayContext.getSegmentsEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryType() %>" />

	<aui:model-context bean="<%= assetListEntry %>" model="<%= AssetListEntry.class %>" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-title">
			<clay:content-row
				verticalAlign="center"
			>
				<clay:content-col>
					<%= HtmlUtil.escape(editAssetListDisplayContext.getSegmentsEntryName(editAssetListDisplayContext.getSegmentsEntryId(), locale)) %>
				</clay:content-col>

				<clay:content-col
					cssClass="inline-item-after"
				>
					<liferay-util:include page="/asset_list_entry_variation_action.jsp" servletContext="<%= application %>" />
				</clay:content-col>
			</clay:content-row>
		</h3>

		<liferay-frontend:form-navigator
			formModelBean="<%= assetListEntry %>"
			id="<%= AssetListFormConstants.FORM_NAVIGATOR_ID %>"
			showButtons="<%= false %>"
		/>
	</liferay-frontend:edit-form-body>

	<c:if test="<%= !editAssetListDisplayContext.isLiveGroup() %>">
		<liferay-frontend:edit-form-footer>
			<aui:button disabled="<%= editAssetListDisplayContext.isNoAssetTypeSelected() %>" id="saveButton" onClick='<%= liferayPortletResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />

			<aui:button href="<%= editAssetListDisplayContext.getBackURL() %>" type="cancel" />
		</liferay-frontend:edit-form-footer>
	</c:if>
</liferay-frontend:edit-form>