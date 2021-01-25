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

				InfoFieldValue<Object> titleInfoFieldValue = infoItemFieldValues.getInfoFieldValue("title");
				%>

				<liferay-ui:search-container-column-text
					name="title"
				>

					<%
					String displayPageURL = StringPool.BLANK;

					if (infoListProviderItemsDisplayContext.isShowActions()) {
						displayPageURL = listItemsActionDropdownItems.getViewDisplayPageURL(AssetEntry.class.getName(), result);
					}
					%>

					<c:choose>
						<c:when test="<%= Validator.isNull(displayPageURL) %>">
							<%= HtmlUtil.escape(String.valueOf(titleInfoFieldValue.getValue(locale))) %>
						</c:when>
						<c:otherwise>
							<aui:a href="<%= displayPageURL %>" target="_top">
								<%= HtmlUtil.escape(String.valueOf(titleInfoFieldValue.getValue(locale))) %>
							</aui:a>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="type"
					value="<%= infoListProviderItemsDisplayContext.getInfoListItemsType(result) %>"
				/>

				<%
				InfoFieldValue<Object> userNameInfoFieldValue = infoItemFieldValues.getInfoFieldValue("userName");
				%>

				<liferay-ui:search-container-column-text
					name="author"
					value="<%= String.valueOf(userNameInfoFieldValue.getValue()) %>"
				/>

				<%
				InfoFieldValue<Object> modifiedDateInfoFieldValue = infoItemFieldValues.getInfoFieldValue("modifiedDate");
				%>

				<liferay-ui:search-container-column-text
					name="modified-date"
					value="<%= String.valueOf(modifiedDateInfoFieldValue.getValue()) %>"
				/>

				<%
				InfoFieldValue<Object> createDateInfoFieldValue = infoItemFieldValues.getInfoFieldValue("createDate");
				%>

				<liferay-ui:search-container-column-text
					name="create-date"
					value="<%= String.valueOf(createDateInfoFieldValue.getValue()) %>"
				/>

				<c:if test="<%= infoListProviderItemsDisplayContext.isShowActions() %>">
					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							dropdownItems="<%= listItemsActionDropdownItems.getActionDropdownItems(infoListProviderClassName, result) %>"
							propsTransformer="js/ListItemsDropdownPropsTransformer"
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
	module="js/TopLinkEventHandler.es"
/>