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

<%@ include file="/propagation_alert.jspf" %>

<liferay-ui:error exception="<%= RequiredLayoutSetPrototypeException.class %>" message="you-cannot-delete-site-templates-that-are-used-by-a-site" />

<clay:management-toolbar
	actionDropdownItems="<%= layoutSetPrototypeDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= layoutSetPrototypeDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= layoutSetPrototypeDisplayContext.isShowAddButton() ? layoutSetPrototypeDisplayContext.getCreationMenu() : null %>"
	filterDropdownItems="<%= layoutSetPrototypeDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= layoutSetPrototypeDisplayContext.getTotalItems() %>"
	propsTransformer="js/LayoutSetPrototypeManagementToolbarPropsTransformer"
	searchActionURL="<%= layoutSetPrototypeDisplayContext.getSearchActionURL() %>"
	searchContainerId="layoutSetPrototype"
	searchFormName="searchFm"
	showInfoButton="<%= false %>"
	showSearch="<%= false %>"
	sortingOrder="<%= layoutSetPrototypeDisplayContext.getOrderByType() %>"
	sortingURL="<%= layoutSetPrototypeDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= layoutSetPrototypeDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="deleteLayoutSetPrototypes" var="deleteLayoutSetPrototypesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutSetPrototypesURL %>" cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= layoutSetPrototypeDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.LayoutSetPrototype"
			escapedModel="<%= true %>"
			keyProperty="layoutSetPrototypeId"
			modelVar="layoutSetPrototype"
		>

			<%
			String rowURL = null;

			Group group = layoutSetPrototype.getGroup();

			if (LayoutSetPrototypePermissionUtil.contains(permissionChecker, layoutSetPrototype.getLayoutSetPrototypeId(), ActionKeys.UPDATE) && (group.getPrivateLayoutsPageCount() > 0)) {
				rowURL = group.getDisplayURL(themeDisplay, true);
			}
			%>

			<c:choose>
				<c:when test="<%= layoutSetPrototypeDisplayContext.isDescriptiveView() %>">
					<liferay-ui:search-container-column-icon
						icon="site-template"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date createDate = layoutSetPrototype.getModifiedDate();
						%>

						<h6 class="text-default">
							<span><liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true) %>" key="created-x-ago" /></span>
						</h6>

						<h5>
							<aui:a href="<%= (rowURL != null) ? rowURL.toString() : StringPool.BLANK %>" target="_blank"><%= layoutSetPrototype.getName(locale) %></aui:a>
						</h5>

						<h6 class="text-default">
							<c:choose>
								<c:when test="<%= layoutSetPrototype.isActive() %>">
									<span><liferay-ui:message key="active" /></span>
								</c:when>
								<c:otherwise>
									<span><liferay-ui:message key="not-active" /></span>
								</c:otherwise>
							</c:choose>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
							dropdownItems="<%= layoutSetPrototypeDisplayContext.getLayoutSetPrototypeActionDropdownItems(layoutSetPrototype) %>"
							propsTransformer="js/LayoutSetPrototypeDropdownDefaultPropsTransformer"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= layoutSetPrototypeDisplayContext.isIconView() %>">
					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							propsTransformer="js/LayoutSetPrototypeDropdownDefaultPropsTransformer"
							verticalCard="<%= new LayoutSetPrototypeVerticalCard(layoutSetPrototype, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test="<%= layoutSetPrototypeDisplayContext.isListView() %>">
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-small table-cell-minw-200 table-title"
						name="name"
					>
						<aui:a href="<%= rowURL %>" target="_blank"><%= layoutSetPrototype.getName(locale) %></aui:a>

						<%
						int mergeFailCount = SitesUtil.getMergeFailCount(layoutSetPrototype);
						%>

						<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
							<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "site-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-300"
						name="description"
						value="<%= layoutSetPrototype.getDescription(locale) %>"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest table-cell-ws-nowrap table-column-text-center"
						name="active"
						value='<%= LanguageUtil.get(request, layoutSetPrototype.isActive()? "yes" : "no") %>'
					/>

					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
							dropdownItems="<%= layoutSetPrototypeDisplayContext.getLayoutSetPrototypeActionDropdownItems(layoutSetPrototype) %>"
							propsTransformer="js/LayoutSetPrototypeDropdownDefaultPropsTransformer"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= layoutSetPrototypeDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>