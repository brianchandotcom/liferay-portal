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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.taglib.ui.LanguageTag" %>

<%
String formName = (String)request.getAttribute("liferay-ui:language:formName");

String formAction = (String)request.getAttribute("liferay-ui:language:formAction");

if (Validator.isNull(formAction)) {
	LiferayPortletURL liferayPortletURL = null;

	if (portletResponse != null) {
		LiferayPortletResponse liferayPortletResponse = (LiferayPortletResponse)portletResponse;

		liferayPortletURL = liferayPortletResponse.createLiferayPortletURL(PortletKeys.LANGUAGE, PortletRequest.ACTION_PHASE);
	}
	else {
		liferayPortletURL = new PortletURLImpl(request, PortletKeys.LANGUAGE, plid, PortletRequest.ACTION_PHASE);
	}

	liferayPortletURL.setAnchor(false);
	liferayPortletURL.setParameter("struts_action", "/language/view");
	liferayPortletURL.setParameter("redirect", currentURL);
	liferayPortletURL.setPortletMode(PortletMode.VIEW);
	liferayPortletURL.setWindowState(WindowState.NORMAL);

	formAction = liferayPortletURL.toString();
}

String name = (String)request.getAttribute("liferay-ui:language:name");
Locale[] locales = (Locale[])request.getAttribute("liferay-ui:language:locales");
boolean displayCurrentLocale = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:language:displayCurrentLocale"), true);
int displayStyle = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:language:displayStyle"));

Map langCounts = new HashMap();

for (int i = 0; i < locales.length; i++) {
	Integer count = (Integer)langCounts.get(locales[i].getLanguage());

	if (count == null) {
		count = new Integer(1);
	}
	else {
		count = new Integer(count.intValue() + 1);
	}

	langCounts.put(locales[i].getLanguage(), count);
}

Set<String> duplicateLanguages = new HashSet<String>();

for (int i = 0; i < locales.length; i++) {
	Integer count = (Integer)langCounts.get(locales[i].getLanguage());

	if (count.intValue() != 1) {
		duplicateLanguages.add(locales[i].getLanguage());
	}
}
%>

<c:choose>
	<c:when test="<%= displayStyle == LanguageTag.SELECT_BOX %>">
		<aui:form action="<%= formAction %>" method="post" name="<%= formName %>">
			<aui:select changesContext="<%= true %>" label="" name="<%= name %>" onChange='<%= "submitForm(document." + namespace + formName + ");" %>' title="language">

				<%
				for (int i = 0; i < locales.length; i++) {
				%>

					<aui:option cssClass="taglib-language-option" label="<%= LocaleUtil.getLongDisplayName(locales[i], duplicateLanguages) %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>" selected="<%= (locale.getLanguage().equals(locales[i].getLanguage()) && locale.getCountry().equals(locales[i].getCountry())) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

				<%
				}
				%>

			</aui:select>
		</aui:form>

		<aui:script>

			<%
			for (int i = 0; i < locales.length; i++) {
			%>

				document.<%= namespace + formName %>.<%= namespace + name %>.options[<%= i %>].style.backgroundImage = "url(<%= themeDisplay.getPathThemeImages() %>/language/<%= LocaleUtil.toLanguageId(locales[i]) %>.png)";

			<%
			}
			%>

		</aui:script>
	</c:when>
	<c:otherwise>

		<%
		for (int i = 0; i < locales.length; i++) {
			String currentLanguageId = LocaleUtil.toLanguageId(locale);
			String languageId = LocaleUtil.toLanguageId(locales[i]);

			if (!displayCurrentLocale && currentLanguageId.equals(languageId)) {
				continue;
			}

			String cssClassName = "taglib-language-list-text";

			if ((i + 1) == locales.length) {
				cssClassName += " last";
			}

			String localeDisplayName = null;

			if (displayStyle == LanguageTag.LIST_SHORT_TEXT) {
				localeDisplayName = LocaleUtil.getShortDisplayName(locales[i], duplicateLanguages);
			}
			else {
				localeDisplayName = LocaleUtil.getLongDisplayName(locales[i], duplicateLanguages);
			}
		%>

			<c:choose>
				<c:when test="<%= (displayStyle == LanguageTag.LIST_LONG_TEXT) || (displayStyle == LanguageTag.LIST_SHORT_TEXT) %>">
					<c:choose>
						<c:when test="<%= currentLanguageId.equals(languageId) %>">
							<span class="<%= cssClassName %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>"><%= localeDisplayName %></span>
						</c:when>
						<c:otherwise>
							<aui:a cssClass="<%= cssClassName %>" href="<%= HttpUtil.addParameter(formAction, name, LocaleUtil.toLanguageId(locales[i])) %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>"><%= localeDisplayName %></aui:a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<liferay-ui:icon
						image='<%= "../language/" + LocaleUtil.toLanguageId(locales[i]) %>'
						lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>"
						message="<%= LocaleUtil.getLongDisplayName(locales[i], duplicateLanguages) %>"
						url='<%= currentLanguageId.equals(languageId) ? null : formAction + "&" + name + "=" + LocaleUtil.toLanguageId(locales[i]) %>'
					/>
				</c:otherwise>
			</c:choose>

		<%
		}
		%>

	</c:otherwise>
</c:choose>