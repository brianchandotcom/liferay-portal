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

SiteChecker siteChecker = new SiteChecker(liferayPortletResponse);

siteChecker.setRememberCheckBoxStateURLRegex("^(?!.*" + liferayPortletResponse.getNamespace() + "redirect).*(groupId=" + siteAdminDisplayContext.getGroupId() + ")");
%>

<liferay-ui:search-container
	id="<%= searchContainerId %>"
	rowChecker="<%= siteChecker %>"
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
		List<Group> childSites = GroupServiceUtil.getGroups(company.getCompanyId(), curGroup.getGroupId(), true);

		boolean hasAddChildSitePermisison = siteAdminDisplayContext.hasAddChildSitePermission(curGroup);

		String siteImageURL = curGroup.getLogoURL(themeDisplay, false);

		PortletURL viewSubsitesURL = null;

		if (hasAddChildSitePermisison && (row != null)) {
			viewSubsitesURL = renderResponse.createRenderURL();

			viewSubsitesURL.setParameter("mvcPath", "/view.jsp");
			viewSubsitesURL.setParameter("groupId", String.valueOf(curGroup.getGroupId()));
		}
		%>

		<c:choose>
			<c:when test='<%= displayStyle.equals("descriptive") %>'>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(siteImageURL) %>">
						<liferay-ui:search-container-column-image
							src="<%= siteImageURL %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-icon
							icon="sites"
						/>
					</c:otherwise>
				</c:choose>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<h5>
						<aui:a href="<%= (viewSubsitesURL != null) ? viewSubsitesURL.toString() : StringPool.BLANK %>" label="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>" localizeLabel="<%= false %>" />
					</h5>

					<c:if test="<%= hasAddChildSitePermisison && GroupPermissionUtil.contains(permissionChecker, curGroup, ActionKeys.VIEW) %>">
						<h6 class="text-default">
							<strong><liferay-ui:message key="child-sites" /></strong>: <%= childSites.size() %>
						</h6>
					</c:if>

					<h6 class="text-default">
						<c:choose>
							<c:when test="<%= curGroup.isActive() %>">
								<liferay-ui:message key="active" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="not-active" />
							</c:otherwise>
						</c:choose>
					</h6>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					path="/site_action.jsp"
				/>
			</c:when>
			<c:when test='<%= displayStyle.equals("icon") %>'>

				<%
				row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
				%>

				<liferay-ui:search-container-column-text>
					<c:choose>
						<c:when test="<%= Validator.isNotNull(siteImageURL) %>">
							<liferay-frontend:vertical-card
								actionJsp="/site_action.jsp"
								actionJspServletContext="<%= application %>"
								imageUrl="<%= siteImageURL %>"
								resultRow="<%= row %>"
								rowChecker="<%= searchContainer.getRowChecker() %>"
								title="<%= curGroup.getDescriptiveName(locale) %>"
								url="<%= (viewSubsitesURL != null) ? viewSubsitesURL.toString() : null %>"
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
								title="<%= curGroup.getDescriptiveName(locale) %>"
								url="<%= (viewSubsitesURL != null) ? viewSubsitesURL.toString() : null %>"
							>
								<%@ include file="/site_vertical_card.jspf" %>
							</liferay-frontend:icon-vertical-card>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>
				<%@ include file="/site_columns.jspf" %>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
</liferay-ui:search-container>

<liferay-util:include page="/add_button.jsp" servletContext="<%= application %>" />