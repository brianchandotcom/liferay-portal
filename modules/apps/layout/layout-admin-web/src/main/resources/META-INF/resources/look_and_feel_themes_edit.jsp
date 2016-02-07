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
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

Theme selTheme = null;
ColorScheme selColorScheme = null;

if (selLayout != null) {
	selTheme = selLayout.getTheme();
	selColorScheme = selLayout.getColorScheme();
}
else {
	selTheme = selLayoutSet.getTheme();
	selColorScheme = selLayoutSet.getColorScheme();
}
%>

<div class="lfr-theme-list">
	<div class="float-container lfr-current-theme" id="regularLookAndFeel">
		<legend><liferay-ui:message key="current-theme" /></legend>

		<div>
			<img alt="<%= HtmlUtil.escapeAttribute(selTheme.getName()) %>" class="img-thumbnail theme-screenshot" onclick="<portlet:namespace />regularselectTheme('SelTheme', false);" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(selTheme.getImagesPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(selTheme.getName()) %>" />

			<div class="theme-details">
				<aui:input checked="<%= true %>" cssClass="selected-theme theme-title" id="regularSelTheme" label="<%= HtmlUtil.escape(selTheme.getName()) %>" name="regularThemeId" type="radio" value="<%= selTheme.getThemeId() %>" />

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

					<c:if test="<%= (selPluginPackage != null) && Validator.isNotNull(selPluginPackage.getAuthor()) %>">
						<dt>
							<liferay-ui:message key="author" />
						</dt>
						<dd>
							<a href="<%= HtmlUtil.escapeHREF(selPluginPackage.getPageURL()) %>"><%= HtmlUtil.escape(selPluginPackage.getAuthor()) %></a>
						</dd>
					</c:if>
				</dl>
			</div>
		</div>

		<%
		List<ColorScheme> colorSchemes = selTheme.getColorSchemes();
		%>

		<c:if test="<%= !colorSchemes.isEmpty() %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="regularlayoutsAdminLookAndFeelColorsPanel" persistState="<%= true %>" title='<%= LanguageUtil.format(request, "color-schemes-x", colorSchemes.size()) %>'>
				<aui:fieldset cssCclass="color-schemes">
					<div class="lfr-theme-list list-unstyled">

						<%
						for (int i = 0; i < colorSchemes.size(); i++) {
							ColorScheme curColorScheme = colorSchemes.get(i);

							String cssClass = StringPool.BLANK;

							if (selColorScheme.getColorSchemeId().equals(curColorScheme.getColorSchemeId())) {
								cssClass = "selected-color-scheme";
							}
						%>

						<div class="<%= cssClass %> theme-entry">
							<img alt="" class="modify-link theme-thumbnail" onclick="<portlet:namespace />regularselectColorScheme('#<portlet:namespace />regularColorSchemeId<%= i %>');" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(curColorScheme.getColorSchemeThumbnailPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(curColorScheme.getName()) %>" />

							<aui:input checked="<%= selColorScheme.getColorSchemeId().equals(curColorScheme.getColorSchemeId()) %>" cssClass="theme-title" id="regularColorSchemeId" label="<%= HtmlUtil.escape(curColorScheme.getName()) %>" name="regularColorSchemeId" type="radio" value="<%= curColorScheme.getColorSchemeId() %>" />
						</div>

						<%
						}
						%>

					</div>
				</aui:fieldset>
			</liferay-ui:panel>
		</c:if>

		<%
		Map<String, ThemeSetting> configurableSettings = selTheme.getConfigurableSettings();
		%>

		<c:if test="<%= !configurableSettings.isEmpty() %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="regularlayoutsAdminLookAndFeelSettingsPanel" persistState="<%= true %>" title="settings">
				<aui:fieldset>

					<%
					for (String name : configurableSettings.keySet()) {
						ThemeSetting themeSetting = configurableSettings.get(name);

						String type = GetterUtil.getString(themeSetting.getType(), "text");
						String value = StringPool.BLANK;

						if (selLayout != null) {
							value = selLayout.getThemeSetting(name, "regular");
						}
						else {
							value = selLayoutSet.getThemeSetting(name, "regular");
						}

						String propertyName = HtmlUtil.escapeAttribute("regularThemeSettingsProperties--" + name + StringPool.DOUBLE_DASH);
					%>

						<c:choose>
							<c:when test='<%= type.equals("checkbox") || type.equals("text") || type.equals("textarea") %>'>
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
								<%= StringUtil.replace(themeSetting.getScript(), "[@NAMESPACE@]", liferayPortletResponse.getNamespace()) %>
							</aui:script>
						</c:if>

					<%
					}
					%>

				</aui:fieldset>
			</liferay-ui:panel>
		</c:if>
	</div>

	<%
	List<Theme> themes = ThemeLocalServiceUtil.getPageThemes(company.getCompanyId(), layoutsAdminDisplayContext.getLiveGroupId(), user.getUserId());
	%>

	<div class="float-container lfr-available-themes" id="regularavailableThemes">
		<legend>
			<span class="header-title">
				<liferay-ui:message arguments="<%= themes.size() - 1 %>" key="available-themes-x" translateArguments="<%= false %>" />
			</span>

			<c:if test="<%= permissionChecker.isOmniadmin() && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) && PrefsPropsUtil.getBoolean(PropsKeys.AUTO_DEPLOY_ENABLED, PropsValues.AUTO_DEPLOY_ENABLED) %>">

				<%
				PortletURL marketplaceURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.MARKETPLACE_STORE, PortletRequest.RENDER_PHASE);
				%>

				<aui:button-row>
					<aui:button cssClass="btn-lg manage-layout-set-branches-link" href="<%= marketplaceURL.toString() %>" id="installMore" value="install-more" />
				</aui:button-row>
			</c:if>
		</legend>

		<c:if test="<%= themes.size() > 1 %>">
			<ul class="lfr-theme-list list-unstyled">

				<%
				for (int i = 0; i < themes.size(); i++) {
					Theme curTheme = themes.get(i);

					if (!selTheme.getThemeId().equals(curTheme.getThemeId())) {
				%>

						<li>
							<div class="theme-entry">
								<img alt="" class="modify-link theme-thumbnail" onclick="<portlet:namespace />regularselectTheme('ThemeId<%= i %>', true);" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(curTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(curTheme.getImagesPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(curTheme.getName()) %>" />

								<aui:input cssClass="theme-title" id="regularThemeId" label="<%= HtmlUtil.escape(curTheme.getName()) %>" name="regularThemeId" type="radio" value="<%= curTheme.getThemeId() %>" />
							</div>
						</li>

				<%
					}
				}
				%>

			</ul>
		</c:if>
	</div>
</div>

<aui:script sandbox="<%= true %>">
	var colorSchemePanel = $('#regularlayoutsAdminLookAndFeelColorsPanel');

	var toggleDisabled = function(disabled) {
		colorSchemePanel.find('input[name=<portlet:namespace />regularColorSchemeId]').prop('disabled', disabled);
	};

	if (colorSchemePanel.length) {
		$('#regularavailableThemes').find('input[name=<portlet:namespace />regularThemeId]').on(
			'change',
			function() {
				toggleDisabled(true);
			}
		);

		$('#regularLookAndFeel').find('#<portlet:namespace />regularSelTheme').on(
			'change',
			function() {
				toggleDisabled(false);
			}
		);
	}
</aui:script>

<aui:script>
	function <portlet:namespace />regularselectColorScheme(id) {
		var colorSchemeInput = AUI.$(id);

		if (!colorSchemeInput.prop('disabled')) {
			colorSchemeInput.prop('checked', true);
		}
	}

	function <portlet:namespace />regularselectTheme(themeId, colorSchemesDisabled) {
		var $ = AUI.$;

		$('#<portlet:namespace />regular' + themeId).prop('checked', true);

		$('#regularlayoutsAdminLookAndFeelColorsPanel').find('input[name=<portlet:namespace />regularColorSchemeId]').prop('disabled', colorSchemesDisabled);
	}
</aui:script>