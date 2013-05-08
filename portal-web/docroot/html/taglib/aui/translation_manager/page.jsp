<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/aui/translation_manager/init.jsp" %>

<div class="lfr-translation-manager" id="<%= namespace + id %>">
	<div class="lfr-translation-manager-content nobr">
		<label class="lfr-translation-manager-default-locale-label" for="<portlet:namespace />defaultLanguageId"><liferay-ui:message key="web-content-default-language" />:</label>

		<span class="lfr-translation-manager-default-locale-text lfr-translation-manager-translation lfr-translation-manager-translation-editing">
			<img src="<%= themeDisplay.getPathThemeImages() %>/language/<%= defaultLanguageId %>.png" />

			<%= LocaleUtil.fromLanguageId(defaultLanguageId).getDisplayName(locale) %>
		</span>

		<select class="hide lfr-translation-manager-default-locale">

			<%
			Locale[] locales = LanguageUtil.getAvailableLocales();

			for (int i = 0; i < locales.length; i++) {
			%>

				<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= defaultLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

			<%
			}
			%>

		</select>

		<a class="lfr-translation-manager-change-default-locale" href="javascript:;"><liferay-ui:message key="change" /></a>

		<c:if test="<%= !readOnly %>">
			<span class="lfr-translation-manager-add-menu">
				<liferay-ui:icon-menu
					cssClass="lfr-translation-manager-icon-menu"
					direction="down"
					icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>'
					message='<%= LanguageUtil.get(pageContext, "add-translation") %>'
					showArrow="<%= true %>"
					showWhenSingleIcon="<%= true %>"
				>

					<%
					for (int i = 0; i < locales.length; i++) {
					%>

						<liferay-ui:icon
							cssClass="lfr-translation-manager-translation-item"
							image='<%= "../language/" + LocaleUtil.toLanguageId(locales[i]) %>'
							lang="<%= LocaleUtil.toLanguageId(locales[i]) %>"
							message="<%= locales[i].getDisplayName(locale) %>"
							url="javascript:;"
						/>

					<%
					}
					%>

				</liferay-ui:icon-menu>
			</span>

			<c:if test="<%= availableLocales.length > 1 %>">
				<div class="lfr-translation-manager-available-translations">
					<label><liferay-ui:message key="available-translations" /></label>

					<span class="lfr-translation-manager-available-translations-links">

						<%
						for (int i = 0; i < availableLocales.length; i++) {
							if (defaultLanguageId.equals(LocaleUtil.toLanguageId(availableLocales[i]))) {
								continue;
							}
						%>

							<span class="lfr-translation-manager-translation" locale="<%= availableLocales[i] %>">
								<img src="<%= themeDisplay.getPathThemeImages() %>/language/<%= LocaleUtil.toLanguageId(availableLocales[i]) %>.png">

								<%= availableLocales[i].getDisplayName(locale) %>

								<a class="lfr-translation-manager-translation-delete" href="javascript:;">x</a>
							</span>

						<%
						}
						%>

					</span>
				</div>
			</c:if>
		</c:if>
	</div>
</div>

<c:if test="<%= initialize %>">

	<%
	JSONArray availableLocalesJSONArray = JSONFactoryUtil.createJSONArray();

	for (int i = 0; i < availableLocales.length; i++) {
		availableLocalesJSONArray.put(LocaleUtil.toLanguageId(availableLocales[i]));
	}

	JSONObject localesMapJSONObject = JSONFactoryUtil.createJSONObject();

	for (int i = 0; i < locales.length; i++) {
		localesMapJSONObject.put(LocaleUtil.toLanguageId(locales[i]), locales[i].getDisplayName(locale));
	}
	%>

	<aui:script use="liferay-translation-manager">
		var translationManager;

		Liferay.component(
			'<%= namespace + id %>',
			function() {

				if (!translationManager) {
					translationManager = new Liferay.TranslationManager(
						{
							availableLocales: <%= availableLocalesJSONArray.toString() %>,
							boundingBox: '#<%= namespace + id %>',
							defaultLocale: '<%= defaultLanguageId %>',
							editingLocale: '<%= editingLanguageId %>',
							localesMap: <%= localesMapJSONObject.toString() %>,
							readOnly: <%= readOnly %>,
							srcNode: '#<%= namespace + id %> .lfr-translation-manager-content'
						}
					).render();
				}

				return translationManager;
			}
		);

		Liferay.component('<%= namespace + id %>');
	</aui:script>
</c:if>