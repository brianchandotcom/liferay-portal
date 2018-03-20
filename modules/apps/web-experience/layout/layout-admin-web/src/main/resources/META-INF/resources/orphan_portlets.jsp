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
String backURL = ParamUtil.getString(request, "backURL");

OrphanPortletsDisplayContext orphanPortletsDisplayContext = new OrphanPortletsDisplayContext(liferayPortletRequest, liferayPortletResponse);

Layout selLayout = orphanPortletsDisplayContext.getSelLayout();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/orphan_portlets.jsp");
portletURL.setParameter("backURL", backURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "orphan-portlets"));
%>

<clay:navigation-bar
	items="<%= orphanPortletsDisplayContext.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="portlets"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orphanPortletsDisplayContext.getOrderByCol() %>"
			orderByType="<%= orphanPortletsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= orphanPortletsDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteOrphanPortlets"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<div class="text-muted">
		<c:choose>
			<c:when test="<%= selLayout.isLayoutPrototypeLinkActive() %>">
				<liferay-ui:message key="layout-inherits-from-a-prototype-portlets-cannot-be-manipulated" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="warning-preferences-of-selected-portlets-will-be-reset-or-deleted" />
			</c:otherwise>
		</c:choose>
	</div>

	<portlet:actionURL name="/layout/delete_orphan_portlets" var="deleteOrphanPortletsURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="selPlid" value="<%= String.valueOf(orphanPortletsDisplayContext.getSelPlid()) %>" />
	</portlet:actionURL>

	<aui:form action="<%= deleteOrphanPortletsURL %>" name="fm">
		<liferay-ui:search-container
			deltaConfigurable="<%= false %>"
			id="portlets"
			iteratorURL="<%= portletURL %>"
			rowChecker="<%= selLayout.isLayoutPrototypeLinkActive() ? null : new EmptyOnClickRowChecker(liferayPortletResponse) %>"
		>
			<liferay-ui:search-container-results
				results="<%= orphanPortletsDisplayContext.getOrphanPortlets() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Portlet"
				escapedModel="<%= true %>"
				keyProperty="portletId"
				modelVar="portlet"
			>
				<c:choose>
					<c:when test='<%= Objects.equals(orphanPortletsDisplayContext.getDisplayStyle(), "descriptive") %>'>
						<liferay-ui:search-container-column-icon
							icon="archive"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>
							<h5>
								<%= PortalUtil.getPortletTitle(portlet, application, locale) %>
							</h5>

							<h6 class="text-default">
								<span><%= portlet.getPortletId() %></span>
							</h6>

							<h6 class="text-default">
								<strong><liferay-ui:message key="status" /></strong>: <%= orphanPortletsDisplayContext.getStatus(portlet) %>
							</h6>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							path="/orphan_portlets_action.jsp"
						/>
					</c:when>
					<c:when test='<%= Objects.equals(orphanPortletsDisplayContext.getDisplayStyle(), "icon") %>'>

						<%
						row.setCssClass("entry-card lfr-asset-item");
						%>

						<liferay-ui:search-container-column-text>
							<liferay-frontend:icon-vertical-card
								actionJsp="/orphan_portlets_action.jsp"
								actionJspServletContext="<%= application %>"
								icon="archive"
								resultRow="<%= row %>"
								rowChecker="<%= searchContainer.getRowChecker() %>"
								subtitle="<%= portlet.getPortletId() %>"
								title="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>"
							>
								<liferay-frontend:vertical-card-footer>
									<%= orphanPortletsDisplayContext.getStatus(portlet) %>
								</liferay-frontend:vertical-card-footer>
							</liferay-frontend:icon-vertical-card>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:when test='<%= Objects.equals(orphanPortletsDisplayContext.getDisplayStyle(), "list") %>'>
						<liferay-ui:search-container-column-text
							name="title"
							truncate="<%= true %>"
							value="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>"
						/>

						<liferay-ui:search-container-column-text
							name="portlet-id"
							property="portletId"
							truncate="<%= true %>"
						/>

						<liferay-ui:search-container-column-text
							name="status"
							value="<%= orphanPortletsDisplayContext.getStatus(portlet) %>"
						/>

						<liferay-ui:search-container-column-jsp
							path="/orphan_portlets_action.jsp"
						/>
					</c:when>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= orphanPortletsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
				type="none"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteOrphanPortlets').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>