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

<liferay-ui:error exception="<%= RequiredLayoutPrototypeException.class %>" message="you-cannot-delete-page-templates-that-are-used-by-a-page" />

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutsAdminDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionDropdownItems="<%= layoutPrototypeDisplayContext.getActionDropdownItems() %>"
	componentId="layoutPrototypeManagementToolbar"
	creationMenu="<%= layoutPrototypeDisplayContext.isShowAddButton() ? layoutPrototypeDisplayContext.getCreationMenu() : null %>"
	disabled="<%= layoutPrototypeDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= layoutPrototypeDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= layoutPrototypeDisplayContext.getTotalItems() %>"
	searchContainerId="layoutPrototype"
	showSearch="<%= false %>"
	sortingOrder="<%= layoutPrototypeDisplayContext.getOrderByType() %>"
	sortingURL="<%= layoutPrototypeDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= layoutPrototypeDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="/layout_prototype/delete_layout_prototype" var="deleteLayoutPrototypesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPrototypesURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= layoutPrototypeDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.LayoutPrototype"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="layoutPrototypeId"
			modelVar="layoutPrototype"
		>

			<%
			Group layoutPrototypeGroup = layoutPrototype.getGroup();
			%>

			<c:choose>
				<c:when test="<%= layoutPrototypeDisplayContext.isDescriptiveView() %>">
					<liferay-ui:search-container-column-icon
						icon="page-template"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date createDate = layoutPrototype.getModifiedDate();

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h6 class="text-default">
							<span><liferay-ui:message arguments="<%= modifiedDateDescription %>" key="created-x-ago" /></span>
						</h6>

						<h5>
							<aui:a href="<%= layoutPrototypeGroup.getDisplayURL(themeDisplay, true) %>" target="_blank"><%= layoutPrototype.getName(locale) %></aui:a>
						</h5>

						<h6 class="text-default">
							<c:choose>
								<c:when test="<%= layoutPrototype.isActive() %>">
									<span><liferay-ui:message key="active" /></span>
								</c:when>
								<c:otherwise>
									<span><liferay-ui:message key="not-active" /></span>
								</c:otherwise>
							</c:choose>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/layout_prototype_action.jsp"
					/>
				</c:when>
				<c:when test="<%= layoutPrototypeDisplayContext.isIconView() %>">

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/layout_prototype_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="page-template"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= layoutPrototype.getName(locale) %>"
							url="<%= layoutPrototypeGroup.getDisplayURL(themeDisplay, true) %>"
						>
							<liferay-frontend:vertical-card-header>

								<%
								Date createDate = layoutPrototype.getModifiedDate();

								String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
								%>

								<label class="text-default">
									<liferay-ui:message arguments="<%= modifiedDateDescription %>" key="created-x-ago" />
								</label>
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<label class="text-default">
									<c:choose>
										<c:when test="<%= layoutPrototype.isActive() %>">
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
				<c:when test="<%= layoutPrototypeDisplayContext.isListView() %>">
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
					>
						<aui:a href="<%= layoutPrototypeGroup.getDisplayURL(themeDisplay, true) %>" target="_blank"><%= layoutPrototype.getName(locale) %></aui:a>

						<%
						int mergeFailCount = SitesUtil.getMergeFailCount(layoutPrototype);
						%>

						<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
							<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "page-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="description"
						value="<%= layoutPrototype.getDescription(locale) %>"
					/>

					<liferay-ui:search-container-column-date
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-text
						name="active"
						value='<%= LanguageUtil.get(request, layoutPrototype.isActive()? "yes" : "no") %>'
					/>

					<liferay-ui:search-container-column-jsp
						path="/layout_prototype_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= layoutPrototypeDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	window.<portlet:namespace />deleteSelectedLayoutPrototypes = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}
</aui:script>