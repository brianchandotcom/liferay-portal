<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	liferayPortletURL.setWindowState(WindowState.NORMAL);
	liferayPortletURL.setPortletMode(PortletMode.VIEW);
	liferayPortletURL.setAnchor(false);

	liferayPortletURL.setParameter("struts_action", "/language/view");
	liferayPortletURL.setParameter("redirect", currentURL);

	formAction = liferayPortletURL.toString();
}

String name = (String)request.getAttribute("liferay-ui:language:name");
Locale[] locales = (Locale[])request.getAttribute("liferay-ui:language:locales");
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

Set duplicateLanguages = new HashSet();

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
					String label = locales[i].getDisplayName(locales[i]);

					if (LanguageUtil.isBetaLocale(locales[i])) {
						label = label + " - Beta";
					}
				%>

					<aui:option cssClass="taglib-language-option" label="<%= label %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>" selected="<%= (locale.getLanguage().equals(locales[i].getLanguage()) && locale.getCountry().equals(locales[i].getCountry())) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

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
			String language = locales[i].getDisplayLanguage(locales[i]);
			String country = locales[i].getDisplayCountry(locales[i]);

			if (displayStyle == LanguageTag.LIST_SHORT_TEXT) {
				if (language.length() > 3) {
					language = locales[i].getLanguage().toUpperCase();
				}

				country = locales[i].getCountry().toUpperCase();
			}
		%>

			<c:choose>
				<c:when test="<%= (displayStyle == LanguageTag.LIST_LONG_TEXT) || (displayStyle == LanguageTag.LIST_SHORT_TEXT) %>">
					<a class="taglib-language-list-text <%= ((i + 1) < locales.length) ? StringPool.BLANK : "last" %>" href="<%= formAction %>&<%= name %>=<%= locales[i].getLanguage() + "_" + locales[i].getCountry() %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>">
						<%= language %>

						<c:if test="<%= duplicateLanguages.contains(locales[i].getLanguage()) %>">
							(<%= country %>)
						</c:if>

						<c:if test="<%= LanguageUtil.isBetaLocale(locales[i]) %>">
							[Beta]
						</c:if>
					</a>
				</c:when>
				<c:otherwise>

					<%
					String message = locales[i].getDisplayName(locales[i]);

					if (LanguageUtil.isBetaLocale(locales[i])) {
						message = message + " - Beta";
					}
					%>

					<liferay-ui:icon
						image='<%= "../language/" + LocaleUtil.toLanguageId(locales[i]) %>'
						lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>"
						message="<%= message %>"
						url='<%= formAction + "&" + name + "=" + LocaleUtil.toLanguageId(locales[i]) %>'
					/>
				</c:otherwise>
			</c:choose>

		<%
		}
		%>

	</c:otherwise>
</c:choose>