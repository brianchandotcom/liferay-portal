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

<liferay-ui:error exception="<%= RequiredLayoutSetPrototypeException.class %>" message="you-cannot-delete-site-templates-that-are-used-by-a-site" />

<clay:management-toolbar
	actionDropdownItems="<%= layoutSetPrototypeDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= layoutSetPrototypeDisplayContext.getClearResultsURL() %>"
	componentId="layoutSetPrototypeWebManagementToolbar"
	creationMenu="<%= layoutSetPrototypeDisplayContext.isShowAddButton() ? layoutSetPrototypeDisplayContext.getCreationMenu() : null %>"
	filterDropdownItems="<%= layoutSetPrototypeDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= layoutSetPrototypeDisplayContext.getTotalItems() %>"
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

<aui:form action="<%= deleteLayoutSetPrototypesURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= layoutSetPrototypeDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.LayoutSetPrototype"
			cssClass="selectable"
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

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h6 class="text-default">
							<span><liferay-ui:message arguments="<%= modifiedDateDescription %>" key="created-x-ago" /></span>
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

					<liferay-ui:search-container-column-jsp
						path="/layout_set_prototype_action.jsp"
					/>
				</c:when>
				<c:when test="<%= layoutSetPrototypeDisplayContext.isIconView() %>">

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/layout_set_prototype_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="site-template"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= layoutSetPrototype.getName(locale) %>"
							url="<%= (rowURL != null) ? rowURL.toString() : StringPool.BLANK %>"
						>
							<liferay-frontend:vertical-card-header>

								<%
								Date createDate = layoutSetPrototype.getModifiedDate();

								String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
								%>

								<label class="text-default">
									<liferay-ui:message arguments="<%= modifiedDateDescription %>" key="created-x-ago" />
								</label>
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<label class="text-default">
									<c:choose>
										<c:when test="<%= layoutSetPrototype.isActive() %>">
											<liferay-ui:message key="active" />
										</c:when>
										<c:otherwise>
											<liferay-ui:message key="not-active" />
										</c:otherwise>
									</c:choose>
								</label>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
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

					<liferay-ui:search-container-column-jsp
						href="<%= rowURL %>"
						path="/layout_set_prototype_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= layoutSetPrototypeDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	var deleteLayoutSetPrototypes = function() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />'
			)
		) {
			submitForm(document.querySelector('#<portlet:namespace />fm'));
		}
	};

	var ACTIONS = {
		deleteLayoutSetPrototypes: deleteLayoutSetPrototypes
	};

	Liferay.componentReady('layoutSetPrototypeWebManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>