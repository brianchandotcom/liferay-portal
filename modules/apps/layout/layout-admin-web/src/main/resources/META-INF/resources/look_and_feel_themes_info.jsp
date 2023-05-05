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

<p class="h4 mb-3 mt-4"><liferay-ui:message key="current-theme" /></p>

<clay:row>
	<clay:col
		size="6"
		sm="5"
	>
		<clay:image-card
			cssClass="mb-0"
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
			LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

			for (String name : configurableSettings.keySet()) {
				String value = selLayoutSet.getThemeSetting(name, "regular");
			%>

				<div class="mb-3">
					<aui:input checked='<%= value.equals("true") %>' disabled="<%= true %>" label="<%= LanguageUtil.get(request, HtmlUtil.escape(name)) %>" labelCssClass="font-weight-normal" name="<%= LanguageUtil.get(request, HtmlUtil.escape(name)) %>" type="checkbox" />
				</div>

			<%
			}
			%>

		</c:if>
	</clay:col>
</clay:row>

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

<c:if test="<%= !colorSchemes.isEmpty() && (selColorScheme != null) %>">
	<h2 class="h4"><liferay-ui:message key="color-scheme" /></h2>

	<clay:row>
		<clay:col
			md="3"
			size="6"
			sm="4"
		>
			<div class="card image-card img-thumbnail">
				<div class="aspect-ratio aspect-ratio-16-to-9">
					<img alt="" class="aspect-ratio-item-flush theme-screenshot" src="<%= themeDisplay.getCDNBaseURL() %><%= HtmlUtil.escapeAttribute(selTheme.getStaticResourcePath()) %><%= HtmlUtil.escapeAttribute(selColorScheme.getColorSchemeThumbnailPath()) %>/thumbnail.png" title="<%= HtmlUtil.escapeAttribute(selColorScheme.getName()) %>" />
				</div>

				<div class="card-body p-2">
					<div class="card-row">
						<div class="card-title text-truncate">
							<%= HtmlUtil.escapeAttribute(selColorScheme.getName()) %>
						</div>
					</div>
				</div>
			</div>
		</clay:col>
	</clay:row>
</c:if>