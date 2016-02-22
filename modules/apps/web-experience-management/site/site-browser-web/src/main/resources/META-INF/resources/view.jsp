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
String p_u_i_d = ParamUtil.getString(request, "p_u_i_d");
String displayStyle = siteBrowserDisplayContext.getDisplayStyle();
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectSite");
String target = ParamUtil.getString(request, "target");

User selUser = PortalUtil.getSelectedUser(request);

String type = siteBrowserDisplayContext.getType();
String[] types = siteBrowserDisplayContext.getTypes();

PortletURL portletURL = siteBrowserDisplayContext.getPortletURL();
%>

<c:choose>
	<c:when test='<%= !type.equals("parent-sites") || (types.length > 1) %>'>
		<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="sites" selected="<%= types.length == 1 %>" />

				<c:if test="<%= types.length > 1 %>">

					<%
					for (String curType : types) {
						portletURL.setParameter("type", curType);
					%>

						<aui:nav-item href="<%= portletURL.toString() %>" label="<%= curType %>" selected="<%= curType.equals(type) %>" />

					<%
					}
					%>

				</c:if>
			</aui:nav>

			<c:if test='<%= !type.equals("parent-sites") %>'>
				<aui:nav-bar-search>
					<aui:form action="<%= portletURL.toString() %>" name="searchFm">
						<liferay-ui:input-search markupView="lexicon" />
					</aui:form>
				</aui:nav-bar-search>
			</c:if>
		</aui:nav-bar>
	</c:when>
	<c:otherwise>
		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="sites" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>
	</c:otherwise>
</c:choose>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list", "icon"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectGroupFm">
	<liferay-ui:search-container
		searchContainer="<%= siteBrowserDisplayContext.getGroupSearch() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Group"
			escapedModel="<%= true %>"
			keyProperty="groupId"
			modelVar="group"
			rowIdProperty="friendlyURL"
			rowVar="row"
		>

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("groupdescriptivename", group.getDescriptiveName(locale));
			data.put("groupid", group.getGroupId());
			data.put("grouptarget", target);
			data.put("grouptype", LanguageUtil.get(request, group.getTypeLabel()));
			data.put("url", group.getDisplayURL(themeDisplay));
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("article-entry col-md-2 col-sm-4 col-xs-6 " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<c:choose>
							<c:when test="<%= Validator.isNull(p_u_i_d) || SiteMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, group.getGroupId()) %>">

								<%
								Map<String, Object> urlData = data;
								%>

								<%@ include file="/site_vertical_card.jspf" %>
							</c:when>
							<c:otherwise>

								<%
								Map<String, Object> urlData = null;
								%>

								<%@ include file="/site_vertical_card.jspf" %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="text-strong"
						name="name"
					>
						<c:choose>
							<c:when test="<%= Validator.isNull(p_u_i_d) || SiteMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, group.getGroupId()) %>">
								<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
									<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>
								</aui:a>
							</c:when>
							<c:otherwise>
								<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	var openingLiferay = Util.getOpener().Liferay;

	openingLiferay.fire(
		'<portlet:namespace />enableRemovedSites',
		{
			selectors: A.all('.selector-button:disabled')
		}
	);

	Util.selectEntityHandler('#<portlet:namespace />selectGroupFm', '<%= HtmlUtil.escapeJS(eventName) %>', <%= selUser != null %>);
</aui:script>