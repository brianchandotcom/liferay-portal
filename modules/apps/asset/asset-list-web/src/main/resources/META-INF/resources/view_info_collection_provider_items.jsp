<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
InfoCollectionProviderItemsDisplayContext infoCollectionProviderItemsDisplayContext = (InfoCollectionProviderItemsDisplayContext)request.getAttribute(AssetListWebKeys.INFO_COLLECTION_PROVIDER_ITEMS_DISPLAY_CONTEXT);
ListItemsActionDropdownItems listItemsActionDropdownItems = (ListItemsActionDropdownItems)request.getAttribute(AssetListWebKeys.LIST_ITEMS_ACTION_DROPDOWN_ITEMS);

InfoItemFieldValuesProvider<Object> infoItemFormProvider = infoCollectionProviderItemsDisplayContext.getInfoItemFieldValuesProvider();
%>

<clay:container-fluid
	cssClass="container-view"
>
	<aui:form name="fm">
		<liferay-ui:search-container
			id="assetEntries"
			searchContainer="<%= infoCollectionProviderItemsDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="Object"
				modelVar="result"
			>
				<liferay-ui:search-container-column-text
					name="title"
				>

					<%
					String displayPageURL = StringPool.BLANK;

					if (infoCollectionProviderItemsDisplayContext.isShowActions()) {
						displayPageURL = listItemsActionDropdownItems.getViewDisplayPageURL(infoCollectionProviderItemsDisplayContext.getInfoCollectionProviderClassName(), result);
					}
					%>

					<c:choose>
						<c:when test="<%= Validator.isNull(displayPageURL) %>">
							<%= HtmlUtil.escape(infoCollectionProviderItemsDisplayContext.getTitle(result)) %>
						</c:when>
						<c:otherwise>
							<aui:a href="<%= displayPageURL %>" target="_top">
								<%= HtmlUtil.escape(infoCollectionProviderItemsDisplayContext.getTitle(result)) %>
							</aui:a>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="type"
					value="<%= infoCollectionProviderItemsDisplayContext.getInfoCollectionItemsType(result) %>"
				/>

				<%
				InfoItemFieldValues infoItemFieldValues = infoItemFormProvider.getInfoItemFieldValues(result);

				InfoFieldValue<Object> userNameInfoFieldValue = infoItemFieldValues.getInfoFieldValue("userName");
				%>

				<c:if test="<%= userNameInfoFieldValue != null %>">
					<liferay-ui:search-container-column-text
						name="author"
						value="<%= String.valueOf(userNameInfoFieldValue.getValue()) %>"
					/>
				</c:if>

				<%
				InfoFieldValue<Object> modifiedDateInfoFieldValue = infoItemFieldValues.getInfoFieldValue("modifiedDate");
				%>

				<c:if test="<%= modifiedDateInfoFieldValue != null %>">
					<liferay-ui:search-container-column-text
						name="modified-date"
						value="<%= String.valueOf(modifiedDateInfoFieldValue.getValue()) %>"
					/>
				</c:if>

				<%
				InfoFieldValue<Object> createDateInfoFieldValue = infoItemFieldValues.getInfoFieldValue("createDate");
				%>

				<c:if test="<%= createDateInfoFieldValue != null %>">
					<liferay-ui:search-container-column-text
						name="create-date"
						value="<%= String.valueOf(createDateInfoFieldValue.getValue()) %>"
					/>
				</c:if>

				<c:if test="<%= infoCollectionProviderItemsDisplayContext.isShowActions() %>">
					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
							dropdownItems="<%= listItemsActionDropdownItems.getActionDropdownItems(infoCollectionProviderItemsDisplayContext.getInfoCollectionProviderClassName(), result) %>"
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