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
			displayViews='<%= new String[] {"icon"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<c:if test="<%= permissionChecker.isOmniadmin() && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) && PrefsPropsUtil.getBoolean(PropsKeys.AUTO_DEPLOY_ENABLED, PropsValues.AUTO_DEPLOY_ENABLED) %>">

	<%
	PortletURL marketplaceURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.MARKETPLACE_STORE, PortletRequest.RENDER_PHASE);
	%>

	<aui:button-row>
		<aui:button cssClass="btn-lg manage-layout-set-branches-link" href="<%= marketplaceURL.toString() %>" id="installMore" value="install-more" />
	</aui:button-row>
</c:if>

<div class="container-fluid-1280">
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
			row.setCssClass("col-md-2 col-sm-3 col-xs-6");

			PluginPackage selPluginPackage = theme.getPluginPackage();

			String cssClass = "theme-selector";

			if (themeId.equals(theme.getThemeId())) {
				cssClass += " selected";
			}

			String subtitle = StringPool.NBSP;

			if ((selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor())) {
				subtitle = LanguageUtil.format(request, "by-x", selPluginPackage.getAuthor());
			}

			String thumbnailSrc = HtmlUtil.escapeAttribute(theme.getStaticResourcePath()) + HtmlUtil.escapeAttribute(theme.getImagesPath()) + "/thumbnail.png";

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("themeid", theme.getThemeId());
			%>

			<liferay-ui:search-container-column-text>
				<liferay-frontend:vertical-card
					cssClass="<%= cssClass %>"
					data="<%= data %>"
					imageUrl="<%= thumbnailSrc %>"
					resultRow="<%= row %>"
					subtitle="<%= subtitle %>"
					title="<%= theme.getName() %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<aui:script use="aui-base">
	var themes = A.one('#<portlet:namespace />themes');

	themes.delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			themes.all('.theme-selector').removeClass('selected');

			currentTarget.addClass('selected');

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(eventName) %>',
				{
					data: currentTarget.attr('data-themeid')
				}
			);
		},
		'.theme-selector'
	);
</aui:script>