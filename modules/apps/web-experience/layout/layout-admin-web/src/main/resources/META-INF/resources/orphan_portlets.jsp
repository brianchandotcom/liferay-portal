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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

List<Portlet> orphanPortlets = Collections.emptyList();

if (selLayout.isSupportsEmbeddedPortlets()) {
	LayoutTypePortlet selLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	embeddedPortlets = selLayoutTypePortlet.getEmbeddedPortlets();
}

PortletTitleComparator portletTitleComparator = new PortletTitleComparator(application, locale);

orphanPortlets = ListUtil.sort(orphanPortlets, portletTitleComparator);

RowChecker rowChecker = new EmptyOnClickRowChecker(liferayPortletResponse);

if (selLayout.isLayoutPrototypeLinkActive()) {
	rowChecker = null;
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/orphan_portlets.jsp");
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="orphan-portlets" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

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
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteOrphanPortlets" label="delete" />
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

	<portlet:actionURL name="deleteOrphanPortlets" var="deleteOrphanPortletsURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="selPlid" value="<%= String.valueOf(layoutsAdminDisplayContext.getSelPlid()) %>" />
	</portlet:actionURL>

	<aui:form action="<%= deleteOrphanPortletsURL %>" name="fm">
		<liferay-ui:search-container
			deltaConfigurable="<%= false %>"
			id="portlets"
			iteratorURL="<%= portletURL %>"
			rowChecker="<%= rowChecker %>"
		>
			<liferay-ui:search-container-results
				results="<%= orphanPortlets %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Portlet"
				escapedModel="<%= true %>"
				keyProperty="portletId"
				modelVar="portlet"
			>

				<%
				String status = StringPool.BLANK;

				if (!portlet.isActive()) {
					status = LanguageUtil.get(request, "inactive");
				}
				else if (!portlet.isReady()) {
					status = LanguageUtil.format(request, "is-not-ready", "portlet");
				}
				else if (portlet.isUndeployedPortlet()) {
					status = LanguageUtil.get(request, "undeployed");
				}
				else {
					status = LanguageUtil.get(request, "active");
				}
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
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
								<strong><liferay-ui:message key="status" /></strong>: <%= status %>
							</h6>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							path="/orphan_portlets_action.jsp"
						/>
					</c:when>
					<c:when test='<%= displayStyle.equals("icon") %>'>

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
									<%= status %>
								</liferay-frontend:vertical-card-footer>
							</liferay-frontend:icon-vertical-card>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:when test='<%= displayStyle.equals("list") %>'>
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
							value="<%= status %>"
						/>

						<liferay-ui:search-container-column-jsp
							path="/orphan_portlets_action.jsp"
						/>
					</c:when>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" type="none" />
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