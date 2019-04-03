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

if (Validator.isNotNull(redirect)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);
}

renderResponse.setTitle(LanguageUtil.get(request, "add-personalized-variation"));
%>

<portlet:actionURL name="/asset_list/add_asset_list_entry_variation" var="addAssetListEntryVariationURL" />

<liferay-frontend:edit-form
	action="<%= addAssetListEntryVariationURL %>"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= editAssetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryType() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:select label="audience" name="segmentsEntryId">

					<%
					for (SegmentsEntry segmentsEntry : editAssetListDisplayContext.getAvailableSegmentsEntries()) {
					%>

						<aui:option label="<%= segmentsEntry.getName(locale) %>" value="<%= segmentsEntry.getSegmentsEntryId() %>" />

					<%
					}
					%>

				</aui:select>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>