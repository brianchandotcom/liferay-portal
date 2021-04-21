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
AssetListManagementToolbarDisplayContext assetListManagementToolbarDisplayContext = new AssetListManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, assetListDisplayContext);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= assetListDisplayContext.getNavigationItems("collections") %>'
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= assetListManagementToolbarDisplayContext %>"
	propsTransformer="js/ManagementToolbarPropsTransformer"
/>

<portlet:actionURL name="/asset_list/delete_asset_list_entries" var="deleteAssetListEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteAssetListEntryURL %>" cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:breadcrumb
		showLayout="<%= false %>"
	/>

	<c:choose>
		<c:when test="<%= assetListDisplayContext.getAssetListEntriesCount() > 0 %>">
			<liferay-ui:search-container
				id="assetListEntries"
				searchContainer="<%= assetListDisplayContext.getAssetListEntriesSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.asset.list.model.AssetListEntry"
					keyProperty="assetListEntryId"
					modelVar="assetListEntry"
				>

					<%
					String editURL = StringPool.BLANK;

					if (AssetListEntryPermission.contains(permissionChecker, assetListEntry, ActionKeys.UPDATE) || AssetListEntryPermission.contains(permissionChecker, assetListEntry, ActionKeys.VIEW)) {
						editURL = PortletURLBuilder.createRenderURL(
							liferayPortletResponse
						).setMVCPath(
							"/edit_asset_list_entry.jsp"
						).setRedirect(
							currentURL
						).setParameter(
							"assetListEntryId", assetListEntry.getAssetListEntryId()
						).buildString();
					}

					row.setData(
						HashMapBuilder.<String, Object>put(
							"actions", assetListManagementToolbarDisplayContext.getAvailableActions(assetListEntry)
						).build());
					%>

					<liferay-ui:search-container-column-icon
						icon="list"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<aui:a href="<%= editURL %>">
								<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
							</aui:a>
						</h5>

						<h6 class="text-default">
							<strong><liferay-ui:message key="<%= HtmlUtil.escape(assetListEntry.getTypeLabel()) %>" /></strong>
						</h6>

						<c:if test="<%= Validator.isNotNull(assetListEntry.getAssetEntryType()) %>">

							<%
							String assetEntryTypeLabel = ResourceActionsUtil.getModelResource(locale, assetListEntry.getAssetEntryType());

							long classTypeId = GetterUtil.getLong(assetListEntry.getAssetEntrySubtype(), -1);

							if (classTypeId >= 0) {
								AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetListEntry.getAssetEntryType());

								if ((assetRendererFactory != null) && assetRendererFactory.isSupportsClassTypes()) {
									ClassType classType = assetListDisplayContext.getClassType(assetRendererFactory.getClassTypeReader(), classTypeId);

									if (classType != null) {
										assetEntryTypeLabel = assetEntryTypeLabel + " - " + classType.getName();
									}
								}
							}
							%>

							<h6 class="text-default">
								<strong><%= HtmlUtil.escape(assetEntryTypeLabel) %></strong>
							</h6>
						</c:if>

						<%
						Date statusDate = assetListEntry.getCreateDate();
						%>

						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</liferay-ui:search-container-column-text>

					<%
					AssetEntryListActionDropdownItems assetEntryListActionDropdownItems = new AssetEntryListActionDropdownItems(assetListEntry, liferayPortletRequest, liferayPortletResponse);
					%>

					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							dropdownItems="<%= assetEntryListActionDropdownItems.getActionDropdownItems() %>"
							propsTransformer="js/AssetEntryListDropdownDefaultPropsTransformer"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="descriptive"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</c:when>
		<c:otherwise>
			<liferay-frontend:empty-result-message
				actionDropdownItems="<%= assetListDisplayContext.isShowAddAssetListEntryAction() ? assetListDisplayContext.getAddAssetListEntryDropdownItems() : null %>"
				description="<%= assetListDisplayContext.getEmptyResultMessageDescription() %>"
				elementType='<%= LanguageUtil.get(request, "collections") %>'
				propsTransformer="js/EmptyResultMessagePropsTransformer"
				propsTransformerServletContext="<%= application %>"
			/>
		</c:otherwise>
	</c:choose>
</aui:form>