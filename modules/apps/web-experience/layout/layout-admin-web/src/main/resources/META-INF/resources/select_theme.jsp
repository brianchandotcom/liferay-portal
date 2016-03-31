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
String redirect = ParamUtil.getString(request, "redirect");

String themeId = ParamUtil.getString(request, "themeId");

String displayStyle = ParamUtil.getString(request, "displayStyle", "icon");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectTheme");
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_theme.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("themeId", themeId);
portletURL.setParameter("eventName", eventName);

List<Theme> themes = ThemeLocalServiceUtil.getPageThemes(company.getCompanyId(), layoutsAdminDisplayContext.getLiveGroupId(), user.getUserId());

themes = ListUtil.sort(themes, new ThemeNameComparator(orderByType.equals("asc")));
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="available-themes" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar>
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
</liferay-frontend:management-bar>

<c:if test="<%= permissionChecker.isOmniadmin() && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) && PrefsPropsUtil.getBoolean(PropsKeys.AUTO_DEPLOY_ENABLED, PropsValues.AUTO_DEPLOY_ENABLED) %>">

	<%
	PortletURL marketplaceURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.MARKETPLACE_STORE, PortletRequest.RENDER_PHASE);
	%>

	<div class="button-holder">
		<aui:button cssClass="manage-layout-set-branches-link" href="<%= marketplaceURL.toString() %>" id="installMore" value="install-more" />
	</div>
</c:if>

<aui:form cssClass="container-fluid-1280" name="selectThemeFm">
	<liferay-ui:search-container
		id="themes"
		iteratorURL="<%= portletURL %>"
		total="<%= themes.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= ListUtil.subList(themes, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Theme"
			escapedModel="<%= true %>"
			keyProperty="themeId"
			modelVar="theme"
		>

			<%
			PluginPackage selPluginPackage = theme.getPluginPackage();

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("themeid", theme.getThemeId());
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-image
						src='<%= theme.getStaticResourcePath() + theme.getImagesPath() + "/thumbnail.png" %>'
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<h5>
							<c:choose>
								<c:when test="<%= !themeId.equals(theme.getThemeId()) %>">
									<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
										<%= theme.getName() %>
									</aui:a>
								</c:when>
								<c:otherwise>
									<%= theme.getName() %>
								</c:otherwise>
							</c:choose>
						</h5>

						<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor()) %>">
							<h6 class="text-default">
								<span><%= LanguageUtil.format(request, "by-x", selPluginPackage.getAuthor()) %></span>
							</h6>
						</c:if>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					String author = StringPool.NBSP;

					if ((selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor())) {
						author = LanguageUtil.format(request, "by-x", selPluginPackage.getAuthor());
					}

					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:vertical-card
							cssClass='<%= themeId.equals(theme.getThemeId()) ? StringPool.BLANK : "selector-button" %>'
							data="<%= data %>"
							imageUrl='<%= theme.getStaticResourcePath() + theme.getImagesPath() + "/thumbnail.png" %>'
							subtitle="<%= author %>"
							title="<%= theme.getName() %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="content-column name-column title-column"
						name="name"
						truncate="<%= true %>"
					>
						<c:choose>
							<c:when test="<%= !themeId.equals(theme.getThemeId()) %>">
								<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
									<%= theme.getName() %>
								</aui:a>
							</c:when>
							<c:otherwise>
								<%= theme.getName() %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<%
					String author = StringPool.BLANK;

					if ((selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor())) {
						author = selPluginPackage.getAuthor();
					}
					%>

					<liferay-ui:search-container-column-text
						cssClass="author-column text-column"
						name="author"
						value="<%= author %>"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectThemeFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>