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
String searchContainerId = ParamUtil.getString(request, "searchContainerId");

String displayStyle = GetterUtil.getString((String)request.getAttribute("view.jsp-displayStyle"));
SearchContainer groupSearch = (SearchContainer)request.getAttribute("view.jsp-groupSearchContainer");
%>

<liferay-ui:search-container
	id="<%= searchContainerId %>"
	rowChecker="<%= new SiteChecker(renderResponse) %>"
	searchContainer="<%= groupSearch %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="curGroup"
		rowIdProperty="friendlyURL"
		rowVar="row"
	>

		<%
		boolean hasAddChildSitePermisison = siteAdminDisplayContext.hasAddChildSitePermission(curGroup);

		String siteImageURL = curGroup.getLogoURL(themeDisplay, false);
		%>

		<c:choose>
			<c:when test='<%= displayStyle.equals("icon") %>'>

				<%
				row.setCssClass("article-entry col-md-2 col-sm-4 col-xs-6 " + row.getCssClass());
				%>

				<liferay-portlet:renderURL var="viewSubsitesURL">
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(curGroup.getGroupId()) %>" />
					<portlet:param name="displayStyle" value="<%= displayStyle %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:search-container-column-text>
					<c:choose>
						<c:when test="<%= Validator.isNotNull(siteImageURL) %>">
							<liferay-frontend:vertical-card
								actionJsp="/site_action.jsp"
								actionJspServletContext="<%= application %>"
								imageUrl="<%= siteImageURL %>"
								resultRow="<%= row %>"
								rowChecker="<%= searchContainer.getRowChecker() %>"
								showCheckbox="<%= false %>"
								title="<%= curGroup.getDescriptiveName(locale) %>"
								url="<%= hasAddChildSitePermisison ? viewSubsitesURL.toString() : null %>"
							>
								<%@ include file="/site_vertical_card.jspf" %>
							</liferay-frontend:vertical-card>
						</c:when>
						<c:otherwise>
							<liferay-frontend:icon-vertical-card
								actionJsp="/site_action.jsp"
								actionJspServletContext="<%= application %>"
								icon="sites"
								resultRow="<%= row %>"
								rowChecker="<%= searchContainer.getRowChecker() %>"
								showCheckbox="<%= false %>"
								title="<%= curGroup.getDescriptiveName(locale) %>"
								url="<%= hasAddChildSitePermisison ? viewSubsitesURL.toString() : null %>"
							>
								<%@ include file="/site_vertical_card.jspf" %>
							</liferay-frontend:icon-vertical-card>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>

				<%
				List<Group> childSites = GroupServiceUtil.getGroups(company.getCompanyId(), curGroup.getGroupId(), true);
				%>

				<%@ include file="/site_columns.jspf" %>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
</liferay-ui:search-container>

<liferay-util:include page="/add_button.jsp" servletContext="<%= application %>" />