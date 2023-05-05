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
String themeId = ParamUtil.getString(request, "themeId");

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

Theme selTheme = null;
ColorScheme selColorScheme = null;

boolean useDefaultThemeSettings = false;

if (Validator.isNotNull(themeId)) {
	selTheme = ThemeLocalServiceUtil.getTheme(company.getCompanyId(), themeId);
	selColorScheme = ThemeLocalServiceUtil.getColorScheme(company.getCompanyId(), themeId, StringPool.BLANK);

	useDefaultThemeSettings = true;
}
else {
	if (selLayout != null) {
		selTheme = selLayout.getTheme();
		selColorScheme = selLayout.getColorScheme();
	}
	else {
		selTheme = selLayoutSet.getTheme();
		selColorScheme = selLayoutSet.getColorScheme();
	}
}

PluginPackage selPluginPackage = selTheme.getPluginPackage();

String styleBookWarningMessage = layoutsAdminDisplayContext.getStyleBookWarningMessage();
%>

<c:if test="<%= Validator.isNotNull(styleBookWarningMessage) %>">
	<clay:alert
		displayType="info"
		message="<%= styleBookWarningMessage %>"
	/>
</c:if>

<aui:input name="regularThemeId" type="hidden" value="<%= selTheme.getThemeId() %>" />
<aui:input name="regularColorSchemeId" type="hidden" value="<%= selColorScheme.getColorSchemeId() %>" />

<clay:row>
	<clay:col
		size="6"
		sm="5"
	>
		<clay:image-card
			cssClass="mb-0"
			imageAlt='<%= Validator.isNotNull(selTheme.getName()) ? HtmlUtil.escapeAttribute(selTheme.getName()) : "" %>'
			imageSrc='<%= themeDisplay.getCDNBaseURL() + HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) + HtmlUtil.escapeAttribute(selTheme.getImagesPath()) + "/thumbnail.png" %>'
			subtitle='<%= ((selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor())) ? HtmlUtil.escape(selPluginPackage.getAuthor()) : "" %>'
			title='<%= Validator.isNotNull(selTheme.getName()) ? HtmlUtil.escapeAttribute(selTheme.getName()) : "" %>'
		/>
	</clay:col>

	<clay:col
		cssClass="pl-4 pt-3"
		size="6"
		sm="7"
	>

		<%
		Map<String, ThemeSetting> configurableSettings = selTheme.getConfigurableSettings();
		%>

		<c:if test="<%= !configurableSettings.isEmpty() %>">

			<%
			ServletContext servletContext = ServletContextPool.get(selTheme.getServletContextName());

			ResourceBundle selThemeResourceBundle = ResourceBundleUtil.getBundle("content.Language", servletContext.getClassLoader());

			ResourceBundle aggregateResourceBundle = new AggregateResourceBundle(resourceBundle, selThemeResourceBundle);

			for (Map.Entry<String, ThemeSetting> entry : configurableSettings.entrySet()) {
				String name = LanguageUtil.get(aggregateResourceBundle, entry.getKey());

				ThemeSetting themeSetting = entry.getValue();

				String type = GetterUtil.getString(themeSetting.getType(), "text");

				String value = StringPool.BLANK;

				if (useDefaultThemeSettings) {
					value = selTheme.getSetting(entry.getKey());
				}
				else {
					if (selLayout != null) {
						value = selLayout.getThemeSetting(entry.getKey(), "regular");
					}
					else {
						value = selLayoutSet.getThemeSetting(entry.getKey(), "regular");
					}
				}

				String propertyName = HtmlUtil.escapeAttribute("regularThemeSettingsProperties--" + entry.getKey() + StringPool.DOUBLE_DASH);
			%>

				<c:choose>
					<c:when test='<%= type.equals("checkbox") %>'>
						<aui:input label="<%= HtmlUtil.escape(name) %>" labelCssClass="font-weight-normal" name="<%= propertyName %>" type="checkbox" value="<%= value %>" />
					</c:when>
					<c:when test='<%= type.equals("text") || type.equals("textarea") %>'>
						<aui:input label="<%= HtmlUtil.escape(name) %>" name="<%= propertyName %>" type="<%= type %>" value="<%= value %>" />
					</c:when>
					<c:when test='<%= type.equals("select") %>'>
						<aui:select label="<%= HtmlUtil.escape(name) %>" name="<%= propertyName %>">

							<%
							for (String option : themeSetting.getOptions()) {
							%>

								<aui:option label="<%= HtmlUtil.escape(option) %>" selected="<%= option.equals(value) %>" />

							<%
							}
							%>

						</aui:select>
					</c:when>
				</c:choose>

				<c:if test="<%= Validator.isNotNull(themeSetting.getScript()) %>">
					<aui:script position="inline">
						<%= StringUtil.replace(themeSetting.getScript(), "[@NAMESPACE@]", liferayPortletResponse.getNamespace()) %>;
					</aui:script>
				</c:if>

			<%
			}
			%>

		</c:if>

		<clay:button
			displayType="secondary"
			id='<%= liferayPortletResponse.getNamespace() + "changeTheme" %>'
			label='<%= LanguageUtil.get(request, "change-current-theme") %>'
		/>

		<portlet:renderURL var="selectThemeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/select_theme.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<portlet:renderURL copyCurrentRenderParameters="<%= true %>" var="lookAndFeelDetailURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<portlet:param name="mvcPath" value="/look_and_feel_theme_details.jsp" />
		</portlet:renderURL>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"changeThemeButtonId", liferayPortletResponse.getNamespace() + "changeTheme"
				).put(
					"initialSelectedThemeId", selTheme.getThemeId()
				).put(
					"lookAndFeelDetailURL", lookAndFeelDetailURL
				).put(
					"selectThemeURL", selectThemeURL
				).put(
					"themeContainerId", liferayPortletResponse.getNamespace() + "currentThemeContainer"
				).build()
			%>'
			module="js/LookAndFeelThemeEdit"
		/>
	</clay:col>
</clay:row>

<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getShortDescription()) %>">
	<h4><liferay-ui:message key="description" /></h4>

	<p class="text-default">
		<%= HtmlUtil.escape(selPluginPackage.getShortDescription()) %>
	</p>
</c:if>

<%
List<ColorScheme> colorSchemes = selTheme.getColorSchemes();
%>

<c:if test="<%= !colorSchemes.isEmpty() %>">
	<h2 class="h4"><liferay-ui:message key="color-schemes" /></h2>

	<div class="clearfix" id="<portlet:namespace />colorSchemesContainer">
		<clay:row>

			<%
			String selColorSchemeId = selColorScheme.getColorSchemeId();

			for (ColorScheme curColorScheme : colorSchemes) {
			%>

				<clay:col
					md="3"
					size="6"
					sm="4"
				>
					<div class="card card-interactive card-interactive-secondary card-type-asset color-scheme-selector image-card <%= selColorSchemeId.equals(curColorScheme.getColorSchemeId()) ? "selected" : StringPool.BLANK %>" data-color-scheme-id="<%= curColorScheme.getColorSchemeId() %>" tabindex="0">
						<div class="aspect-ratio aspect-ratio-16-to-9">
							<img alt="" class="aspect-ratio-item-flush" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(curColorScheme.getColorSchemeThumbnailPath()) %>/thumbnail.png" />
						</div>

						<div class="card-body p-2">
							<div class="card-row">
								<div class="card-title text-truncate">
									<%= HtmlUtil.escapeAttribute(curColorScheme.getName()) %>
								</div>
							</div>
						</div>
					</div>
				</clay:col>

			<%
			}
			%>

		</clay:row>
	</div>
</c:if>

<c:if test="<%= !colorSchemes.isEmpty() %>">
	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"buttonCssClass", ".color-scheme-selector"
			).put(
				"containerId", liferayPortletResponse.getNamespace() + "colorSchemesContainer"
			).put(
				"regularColorSchemeInputId", liferayPortletResponse.getNamespace() + "regularColorSchemeId"
			).build()
		%>'
		module="js/LookAndFeelThemeDetails"
	/>
</c:if>