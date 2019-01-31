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

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutsAdminDisplayContext.getNavigationItems() %>"
/>

<%
LayoutPrototypeManagementToolbarDisplayContext layoutPrototypeManagementToolbarDisplayContext = new LayoutPrototypeManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, layoutPrototypeDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= layoutPrototypeManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/layout_prototype/delete_layout_prototype" var="deleteLayoutPrototypesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPrototypesURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:error embed="<%= false %>" exception="<%= RequiredLayoutPrototypeException.class %>" message="you-cannot-delete-page-templates-that-are-used-by-a-page" />

	<liferay-ui:search-container
		searchContainer="<%= layoutPrototypeDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="layoutPrototypeId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			LayoutPrototype layoutPrototype = LayoutPrototypeServiceUtil.getLayoutPrototype(layoutPageTemplateEntry.getLayoutPrototypeId());

			Group layoutPrototypeGroup = layoutPrototype.getGroup();

			row.setCssClass("entry-card lfr-asset-item");

			request.setAttribute(LayoutAdminWebKeys.LAYOUT_PROTOTYPE, layoutPrototype);
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
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require='<%= npmResolvedPackageName + "/js/LayoutPrototypeManagementToolbarDefaultEventHandler.es as LayoutPrototypeManagementToolbarDefaultEventHandler" %>'>
	Liferay.component(
		'<%= layoutPrototypeManagementToolbarDisplayContext.getDefaultEventHandler() %>',
		new LayoutPrototypeManagementToolbarDefaultEventHandler.default(
			{
				namespace: '<portlet:namespace />'
			}
		),
		{
			destroyOnNavigate: true,
			portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
		}
	);
</aui:script>