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
InfoListProviderItemsDisplayContext infoListProviderItemsDisplayContext = (InfoListProviderItemsDisplayContext)request.getAttribute(AssetListWebKeys.INFO_LIST_PROVIDER_ITEMS_DISPLAY_CONTEXT);
ListItemsActionDropdownItems listItemsActionDropdownItems = (ListItemsActionDropdownItems)request.getAttribute(AssetListWebKeys.LIST_ITEMS_ACTION_DROPDOWN_ITEMS);

InfoItemFieldValuesProvider<Object> infoItemFormProvider = infoListProviderItemsDisplayContext.getInfoItemFieldValuesProvider();

String infoListProviderClassName = infoListProviderItemsDisplayContext.getInfoListProviderClassName();
%>

<clay:container-fluid
	cssClass="container-view"
>
	<aui:form name="fm">
		<liferay-ui:search-container
			id="assetEntries"
			searchContainer="<%= infoListProviderItemsDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="Object"
				modelVar="result"
			>

				<%
				InfoItemFieldValues infoItemFieldValues = infoItemFormProvider.getInfoItemFieldValues(result);

				InfoFieldValue<Object> title = infoItemFieldValues.getInfoFieldValue("title");
				InfoFieldValue<Object> userName = infoItemFieldValues.getInfoFieldValue("userName");
				InfoFieldValue<Object> modifiedDate = infoItemFieldValues.getInfoFieldValue("modifiedDate");
				InfoFieldValue<Object> createDate = infoItemFieldValues.getInfoFieldValue("createDate");
				%>

				<liferay-ui:search-container-column-text
					name="title"
					value="<%= HtmlUtil.escape(String.valueOf(title.getValue(locale))) %>"
				/>

				<liferay-ui:search-container-column-text
					name="type"
					value="<%= infoListProviderItemsDisplayContext.getInfoListItemsType(result) %>"
				/>

				<liferay-ui:search-container-column-text
					name="author"
					value="<%= String.valueOf(userName.getValue()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="modified-date"
					value="<%= String.valueOf(modifiedDate.getValue()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="create-date"
					value="<%= String.valueOf(createDate.getValue()) %>"
				/>

				<c:if test="<%= infoListProviderItemsDisplayContext.isShowActions() %>">
					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							defaultEventHandler="<%= AssetListWebKeys.LIST_ITEMS_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
							dropdownItems="<%= listItemsActionDropdownItems.getActionDropdownItems(infoListProviderClassName, result) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<liferay-frontend:component
	componentId="<%= AssetListWebKeys.LIST_ITEMS_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/ListItemsDropdownDefaultEventHandler.es"
/>