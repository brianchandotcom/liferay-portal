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
					"availableLocalesJsp", availableLocales
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

		<clay:col>
			<h3>Functions (field)</h3>

			<form>
				<aui:input activeLanguageIds="<%= activeLanguageIds %>" adminMode="<%= true %>" availableLocales="<%= availableLocales %>" defaultLanguageId="<%= defaultLanguageId %>" label="" localized="<%= true %>" name="tm-aui-3" onChange="console.log('onChange', event);" onClick="console.log('onClick', event);" type="text" />
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

		<clay:col>
			<h3>Functions (field)</h3>

			<form>
				<liferay-ui:input-localized
					activeLanguageIds="<%= activeLanguageIds %>"
					adminMode="<%= true %>"
					availableLocales="<%= availableLocales %>"
					defaultLanguageId="<%= defaultLanguageId %>"
					name="tm-liferay-ui-3"
					onChange="console.log('onChange', event);"
					onClick="console.log('onClick', event);"
					xml=""
				/>
			</form>
		</clay:col>
	</clay:row>
</clay:container-fluid>