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
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

Theme selTheme = selLayout.getTheme();
%>

<div class="lfr-theme-list">
	<div class="float-container lfr-current-theme" id="LookAndFeel">
		<legend><liferay-ui:message key="current-theme" /></legend>

		<div>
			<img alt="<%= HtmlUtil.escapeAttribute(selTheme.getName()) %>" class="img-thumbnail theme-screenshot" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(selTheme.getImagesPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(selTheme.getName()) %>" />

			<div class="theme-details">
				<div class="selected-theme theme-title"><%= HtmlUtil.escape(selTheme.getName()) %></div>

				<dl class="theme-fields">

					<%
					PluginPackage selPluginPackage = selTheme.getPluginPackage();
					%>

					<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getShortDescription()) %>">
						<dt>
							<liferay-ui:message key="description" />
						</dt>
						<dd>
							<%= HtmlUtil.escape(selPluginPackage.getShortDescription()) %>
						</dd>
					</c:if>

					<%
					ColorScheme selColorScheme = selLayout.getColorScheme();

					List<ColorScheme> colorSchemes = selTheme.getColorSchemes();
					%>

					<c:if test="<%= !colorSchemes.isEmpty() && Validator.isNotNull(selColorScheme) %>">
						<dt class="current-color-scheme">
							<liferay-ui:message key="color-scheme" />
						</dt>
						<dd>
							<%= HtmlUtil.escape(selColorScheme.getName()) %>
						</dd>
					</c:if>

					<%
					Map<String, ThemeSetting> configurableSettings = selTheme.getConfigurableSettings();
					%>

					<c:if test="<%= !configurableSettings.isEmpty() %>">

						<%
						LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

						for (String name : configurableSettings.keySet()) {
						%>

							<dt class="theme-setting">
								<liferay-ui:message key="<%= HtmlUtil.escape(name) %>" />
							</dt>
							<dd>
								<%= HtmlUtil.escape(selLayoutSet.getThemeSetting(name, "regular")) %>
							</dd>

						<%
						}
						%>

					</c:if>
				</dl>
			</div>
		</div>
	</div>
</div>