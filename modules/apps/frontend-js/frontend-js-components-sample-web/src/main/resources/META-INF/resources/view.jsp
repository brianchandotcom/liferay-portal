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
String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());
Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();
List<String> activeLanguageIds = new ArrayList<String>();

activeLanguageIds.add(defaultLanguageId);
activeLanguageIds.add("ca_ES");
activeLanguageIds.add("fr_FR");
%>

<div class="frontend-js-components-sample-web">
	<h1>Translation Manager</h1>

	<h2>React Component</h2>

	<div>
		<react:component
			module="js/App"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"displayNames", LocaleUtil.toDisplayNames(availableLocales, locale)
				).put(
					"languageIds", LocaleUtil.toLanguageIds(availableLocales)
				).build()
			%>'
		/>
	</div>

	<h2>AUI Tag</h2>

	<clay:container-fluid>
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
	</clay:container-fluid>

	<h2>Liferay UI Tag</h2>

	<clay:container-fluid>
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
</div>