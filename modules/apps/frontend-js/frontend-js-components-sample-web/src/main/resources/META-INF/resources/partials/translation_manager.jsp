<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<String> activeLanguageIds = translationManagerDisplayContext.getActiveLanguageIds();
Set<Locale> availableLocales = translationManagerDisplayContext.getAvailableLocales();
String defaultLanguageId = translationManagerDisplayContext.getDefaultLanguageId();
Map<String, Object> translations = translationManagerDisplayContext.getTranslations();
%>

<clay:container-fluid>
	<clay:row>
		<clay:col>
			<h2>React Component</h2>
		</clay:col>
	</clay:row>

	<clay:row>
		<react:component
			module="js/TranslationManagerSamples"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"activeLanguageIds", activeLanguageIds
				).put(
					"availableLocales", translationManagerDisplayContext.getAvailableLocalesJSONArray()
				).put(
					"defaultLanguageId", defaultLanguageId
				).put(
					"translations", translations
				).build()
			%>'
		/>
	</clay:row>

	<hr />

	<clay:row>
		<clay:col>
			<h2>AUI Tag</h2>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col>
			<h3>Default</h3>

			<form>
				<aui:input activeLanguageIds="<%= activeLanguageIds %>" availableLocales="<%= availableLocales %>" defaultLanguageId="<%= defaultLanguageId %>" label="" localized="<%= true %>" name="tm-aui-1" type="text" />
			</form>
		</clay:col>

		<clay:col>
			<h3>Admin</h3>

			<form>
				<aui:input activeLanguageIds="<%= activeLanguageIds %>" adminMode="<%= true %>" availableLocales="<%= availableLocales %>" defaultLanguageId="<%= defaultLanguageId %>" label="" localized="<%= true %>" name="tm-aui-2" type="text" />
			</form>
		</clay:col>
	</clay:row>

	<hr />

	<clay:row>
		<clay:col>
			<h2>Liferay UI Tag</h2>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col>
			<h3>Default</h3>

			<form>
				<liferay-ui:input-localized
					activeLanguageIds="<%= activeLanguageIds %>"
					availableLocales="<%= availableLocales %>"
					defaultLanguageId="<%= defaultLanguageId %>"
					name="tm-liferay-ui-1"
					xml=""
				/>
			</form>
		</clay:col>

		<clay:col>
			<h3>Admin</h3>

			<form>
				<liferay-ui:input-localized
					activeLanguageIds="<%= activeLanguageIds %>"
					adminMode="<%= true %>"
					availableLocales="<%= availableLocales %>"
					defaultLanguageId="<%= defaultLanguageId %>"
					name="tm-liferay-ui-2"
					xml=""
				/>
			</form>
		</clay:col>
	</clay:row>
</clay:container-fluid>