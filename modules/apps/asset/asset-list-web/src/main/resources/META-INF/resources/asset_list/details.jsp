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
String redirect = editAssetListDisplayContext.getRedirectURL();

AssetListEntry assetListEntry = assetListDisplayContext.getAssetListEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(assetListDisplayContext.getAssetListEntryTitle());
%>

<portlet:actionURL name="/asset_list/edit_asset_list_entry" var="editAssetListEntryURL" />

<liferay-frontend:edit-form
	action="<%= editAssetListEntryURL %>"
	method="post"
	name="fm"
>
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryType() %>" />

	<aui:model-context bean="<%= assetListEntry %>" model="<%= AssetListEntry.class %>" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-subtitle">
			<span class="autofit-padded-no-gutters autofit-row">
				<span class="autofit-col autofit-col-expand">
					<span class="heading-text">
						<liferay-ui:message key="details" />
					</span>
				</span>
			</span>
		</h3>

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				markupView="lexicon"
			>
				<aui:input name="title" placeholder="title" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>