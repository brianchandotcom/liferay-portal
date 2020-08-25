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

<%@ include file="/html/taglib/aui/translation_manager/init.jsp" %>

<div class="lfr-translation-manager" id="<%= namespace + id %>">
	<div class="lfr-translation-manager-content">
		<label class="lfr-translation-manager-default-locale-label" for="<portlet:namespace />defaultLanguageId"><liferay-ui:message key="web-content-default-language" />:</label>

		<span class="label label-default label-lg lfr-translation-manager-default-locale-text lfr-translation-manager-translation lfr-translation-manager-translation-editing">

			<%
			Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);
			%>

			<img alt="<%= HtmlUtil.escapeAttribute(defaultLocale.getDisplayName(locale)) %>" src="<%= HtmlUtil.escapeAttribute(themeDisplay.getPathThemeImages() + "/language/" + defaultLanguageId + ".png") %>" />

			<%= defaultLocale.getDisplayName(locale) %>
		</span>

		<select class="form-control hide lfr-translation-manager-default-locale">

			<%
			Set<Locale> locales = LanguageUtil.getAvailableLocales(groupId);

			for (Locale curLocale : locales) {
			%>

				<aui:option label="<%= curLocale.getDisplayName(locale) %>" selected="<%= defaultLanguageId.equals(LocaleUtil.toLanguageId(curLocale)) %>" value="<%= LocaleUtil.toLanguageId(curLocale) %>" />

			<%
			}
			%>

		</select>

		<c:if test="<%= changeableDefaultLanguage %>">
			<a class="label label-default label-lg lfr-translation-manager-change-default-locale" href="javascript:;"><liferay-ui:message key="change" /></a>
		</c:if>

		<c:if test="<%= !readOnly %>">
			<liferay-ui:icon-menu
				cssClass="lfr-translation-manager-icon-menu"
				direction="down"
				icon="../aui/plus"
				message='<%= LanguageUtil.get(resourceBundle, "add-translation") %>'
				showArrow="<%= true %>"
				showWhenSingleIcon="<%= true %>"
			>

				<%
				for (Locale curLocale : locales) {
				%>

					<liferay-ui:icon
						cssClass="lfr-translation-manager-translation-item"
						id="<%= LocaleUtil.toLanguageId(curLocale) %>"
						image='<%= "../language/" + LocaleUtil.toLanguageId(curLocale) %>'
						lang="<%= LocaleUtil.toLanguageId(curLocale) %>"
						message="<%= curLocale.getDisplayName(locale) %>"
						url="javascript:;"
					/>

				<%
				}
				%>

			</liferay-ui:icon-menu>

			<div class="alert alert-info hide lfr-translation-manager-translations-message" id="<portlet:namespace />translationsMessage">
				<liferay-ui:message key="the-changes-in-your-translations-will-be-available-once-the-content-is-published" />
			</div>

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

							<span class="label label-default label-lg lfr-translation-manager-translation" locale="<%= availableLocales[i] %>">
								<img alt="<%= HtmlUtil.escapeAttribute(availableLocales[i].getDisplayName(locale)) %>" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= LocaleUtil.toLanguageId(availableLocales[i]) %>.png" />

								<%= availableLocales[i].getDisplayName(locale) %>

								<aui:icon cssClass="lfr-translation-manager-delete-translation" image="remove" />
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

	for (Locale curLocale : locales) {
		localesMapJSONObject.put(LocaleUtil.toLanguageId(curLocale), curLocale.getDisplayName(locale));
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
							changeableDefaultLanguage: <%= changeableDefaultLanguage %>,
							boundingBox: '#<%= namespace + id %>',
							defaultLocale: '<%= HtmlUtil.escapeJS(defaultLanguageId) %>',
							editingLocale: '<%= HtmlUtil.escapeJS(editingLanguageId) %>',
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