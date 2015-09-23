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
PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:error exception="<%= RequiredLayoutSetPrototypeException.class %>" message="you-cannot-delete-site-templates-that-are-used-by-a-site" />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="templates" />
	</aui:nav>
</aui:nav-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-container
		emptyResultsMessage="no-site-templates-were-found"
		headerNames="name"
		iteratorURL="<%= portletURL %>"
		total="<%= LayoutSetPrototypeLocalServiceUtil.searchCount(company.getCompanyId(), null) %>"
	>
		<aui:input name="deleteLayoutSetPrototypesIds" type="hidden" />

		<liferay-ui:search-container-results
			results="<%= LayoutSetPrototypeLocalServiceUtil.search(company.getCompanyId(), null, searchContainer.getStart(), searchContainer.getEnd(), null) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.LayoutSetPrototype"
			escapedModel="<%= true %>"
			keyProperty="layoutSetPrototypeId"
			modelVar="layoutSetPrototype"
		>

			<%
			String rowURL = null;

			Group group = layoutSetPrototype.getGroup();

			PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

			String portletId = panelCategoryHelper.getFirstPortletId(PanelCategoryKeys.SITE_ADMINISTRATION, permissionChecker, group);

			PortletURL siteAdministrationURL = null;

			if (Validator.isNotNull(portletId)) {
				siteAdministrationURL = PortalUtil.getControlPanelPortletURL(request, group, portletId, 0, PortletRequest.RENDER_PHASE);
			}

			if (siteAdministrationURL != null) {
				rowURL = siteAdministrationURL.toString();
			}
			%>

			<liferay-ui:search-container-column-text
				name="name"
			>

				<aui:a href="<%= rowURL %>"><%= layoutSetPrototype.getName(locale) %></aui:a>

				<%
				int mergeFailCount = SitesUtil.getMergeFailCount(layoutSetPrototype);
				%>

				<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
					<liferay-ui:icon
						iconCssClass="icon-warning-sign"
						message='<%= LanguageUtil.format(request, "the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors", new Object[] {mergeFailCount, LanguageUtil.get(request, "site-template")}, false) %>'
					/>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= layoutSetPrototype.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="checkbox-cell"
				name="active"
			>
				<%= LanguageUtil.get(request, layoutSetPrototype.isActive()? "yes" : "no") %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="checkbox-cell entry-action"
				href="<%= rowURL %>"
				path="/layout_set_prototype_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_SET_PROTOTYPE) %>">
	<portlet:renderURL var="addLayoutSetPrototypeURL">
		<portlet:param name="mvcPath" value="/edit_layout_set_prototype.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addLayoutSetPrototypeURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>