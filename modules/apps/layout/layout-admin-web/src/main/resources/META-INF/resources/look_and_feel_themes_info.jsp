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
Long companyId = ParamUtil.getLong(request, "companyId");
String themeId = ParamUtil.getString(request, "themeId");

Theme selTheme = null;

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

if (Validator.isNotNull(themeId) && Validator.isNotNull(companyId)) {
	selTheme = ThemeLocalServiceUtil.getTheme(companyId, themeId);
}
else if (selLayout == null) {
	selTheme = selLayout.getTheme();
}

PluginPackage selPluginPackage = selTheme.getPluginPackage();
%>

<h1 class="h4 text-default"><liferay-ui:message key="current-theme" /></h1>

<div class="card-horizontal main-content-card">
	<div class="card-body">
		<aui:row>
			<div class="col-6 col-sm-4">
				<div class="card image-card img-thumbnail">
					<div class="aspect-ratio aspect-ratio-16-to-9">
						<img alt="<%= HtmlUtil.escapeAttribute(selTheme.getName()) %>" class="aspect-ratio-item-flush aspect-ratio-item-top-center img-thumbnail theme-screenshot" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(selTheme.getImagesPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(selTheme.getName()) %>" />
					</div>
				</div>
			</div>

			<div class="col-6 col-sm-8">
				<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getName()) %>">
					<h2 class="h4"><liferay-ui:message key="name" /></h2>

					<p class="text-default">
						<%= HtmlUtil.escape(selPluginPackage.getName()) %>
					</p>
				</c:if>

				<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor()) %>">
					<h2 class="h4"><liferay-ui:message key="author" /></h2>

					<p class="text-default">
						<aui:a href="<%= HtmlUtil.escapeHREF(selPluginPackage.getPageURL()) %>" target="_blank"><%= HtmlUtil.escape(selPluginPackage.getAuthor()) %></aui:a>
					</p>
				</c:if>
			</div>
		</aui:row>

		<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getShortDescription()) %>">
			<h2 class="h4"><liferay-ui:message key="description" /></h2>

			<p class="text-default">
				<%= HtmlUtil.escape(selPluginPackage.getShortDescription()) %>
			</p>
		</c:if>

		<%
		ColorScheme selColorScheme = selLayout.getColorScheme();

		List<ColorScheme> colorSchemes = selTheme.getColorSchemes();
		%>

		<c:if test="<%= !colorSchemes.isEmpty() && Validator.isNotNull(selColorScheme) %>">
			<h2 class="h4"><liferay-ui:message key="color-scheme" /></h2>

			<aui:row>
				<div class="col-6 col-md-3 col-sm-4">
					<div class="card image-card img-thumbnail">
						<div class="aspect-ratio aspect-ratio-16-to-9">
							<img alt="" class="aspect-ratio-item-flush aspect-ratio-item-top-center img-thumbnail theme-screenshot" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(selColorScheme.getColorSchemeThumbnailPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(selColorScheme.getName()) %>" />
						</div>

						<div class="card-body p-2">
							<div class="card-row">
								<div class="card-title text-truncate">
									<%= HtmlUtil.escapeAttribute(selColorScheme.getName()) %>
								</div>
							</div>
						</div>
					</div>
				</div>
			</aui:row>
		</c:if>

		<%
		Map<String, ThemeSetting> configurableSettings = selTheme.getConfigurableSettings();
		%>

		<c:if test="<%= !configurableSettings.isEmpty() %>">
			<h2 class="h4"><liferay-ui:message key="settings" /></h2>

			<%
			LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

			for (String name : configurableSettings.keySet()) {
				String value = selLayoutSet.getThemeSetting(name, "regular");
			%>

				<p class="text-default"><liferay-ui:message key="<%= HtmlUtil.escape(name) %>" />: <%= HtmlUtil.escape(LanguageUtil.get(request, value)) %></p>

			<%
			}
			%>

		</c:if>
	</div>
</div>