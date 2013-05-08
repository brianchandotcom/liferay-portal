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

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_input_localized_page");

boolean autoSize = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:autoSize"));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:cssClass"));
String defaultLanguageId = (String)request.getAttribute("liferay-ui:input-localized:defaultLanguageId");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:disabled"));
int displayWidth = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-localized:displayWidth"));
String id = (String)request.getAttribute("liferay-ui:input-localized:id");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ui:input-localized:dynamicAttributes");
String formName = (String)request.getAttribute("liferay-ui:input-localized:formName");
boolean ignoreRequestValue = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-localized:ignoreRequestValue"));
String languageId = (String)request.getAttribute("liferay-ui:input-localized:languageId");
String maxLength = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:maxLength"));
String name = (String)request.getAttribute("liferay-ui:input-localized:name");
String xml = (String)request.getAttribute("liferay-ui:input-localized:xml");
String type = (String)request.getAttribute("liferay-ui:input-localized:type");

Locale defaultLocale = null;

if (Validator.isNotNull(defaultLanguageId)) {
	defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);
}
else {
	defaultLocale = LocaleUtil.getDefault();
	defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
}

Locale[] locales = LanguageUtil.getAvailableLocales();

String mainLanguageId = defaultLanguageId;

if (Validator.isNotNull(languageId)) {
	mainLanguageId = languageId;
}

String mainLanguageValue = LocalizationUtil.getLocalization(xml, mainLanguageId, false);

if (!ignoreRequestValue) {
	mainLanguageValue = ParamUtil.getString(request, name + StringPool.UNDERLINE + mainLanguageId, mainLanguageValue);
}

if (Validator.isNull(mainLanguageValue)) {
	mainLanguageValue = LocalizationUtil.getLocalization(xml, defaultLanguageId, true);
}

String fieldSuffix = StringPool.BLANK;

if ((locales.length > 1) && !Validator.isNull(languageId)) {
	fieldSuffix = StringPool.UNDERLINE + mainLanguageId;
}
%>

<span class="liferay-input-localized" id="<portlet:namespace /><%= id %>BoundingBox">
	<div id="<portlet:namespace /><%= id %>ContentBox"></div>

	<c:choose>
		<c:when test='<%= type.equals("input") %>'>
			<input class="language-value <%= cssClass %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= id + fieldSuffix %>" name="<portlet:namespace /><%= name + fieldSuffix %>" type="text" value="<%= HtmlUtil.escape(mainLanguageValue) %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
		</c:when>
		<c:when test='<%= type.equals("textarea") %>'>
			<textarea class="language-value <%= cssClass %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= id + fieldSuffix %>" name="<portlet:namespace /><%= name + fieldSuffix %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>><%= HtmlUtil.escape(mainLanguageValue) %></textarea>

			<c:if test="<%= autoSize %>">
				<aui:script use="aui-autosize-deprecated">
					A.one('#<portlet:namespace /><%= id + fieldSuffix %>').plug(A.Plugin.Autosize);
				</aui:script>
			</c:if>
		</c:when>
	</c:choose>

	<c:if test="<%= (locales.length > 1) && Validator.isNull(languageId) %>">

		<%
		List<String> languageIds = new ArrayList<String>();

		languageIds.add(defaultLanguageId);

		if (Validator.isNotNull(xml)) {
			for (int i = 0; i < locales.length; i++) {
				String curLanguageId = LocaleUtil.toLanguageId(locales[i]);

				if (curLanguageId.equals(defaultLanguageId)) {
					continue;
				}

				String languageValue = LocalizationUtil.getLocalization(xml, curLanguageId, false);

				if (Validator.isNotNull(languageValue) || (!ignoreRequestValue && (request.getParameter(name + StringPool.UNDERLINE + curLanguageId) != null))) {
					languageIds.add(curLanguageId);
				}
			}
		}

		for (int i = 0; i < languageIds.size(); i++) {
			String curLanguageId = languageIds.get(i);

			String languageValue = StringPool.BLANK;

			if (Validator.isNotNull(xml)) {
				languageValue = LocalizationUtil.getLocalization(xml, curLanguageId, false);
			}

			if (!ignoreRequestValue) {
				languageValue = ParamUtil.getString(request, name + StringPool.UNDERLINE + curLanguageId, languageValue);
			}
		%>

			<aui:input disabled="<%= disabled %>" id="<%= id + StringPool.UNDERLINE + curLanguageId %>" name="<%= name + StringPool.UNDERLINE + curLanguageId %>" type="hidden" value="<%= HtmlUtil.escape(languageValue) %>" />

		<%
		}
		%>

	</c:if>
</span>

<c:if test="<%= Validator.isNotNull(maxLength) %>">
	<aui:script use="aui-char-counter">
		new A.CharCounter(
			{
				input: '#<portlet:namespace /><%= id + fieldSuffix %>',
				maxLength: <%= maxLength %>
			}
		);
	</aui:script>
</c:if>

<c:if test="<%= (locales.length > 1) && Validator.isNull(languageId) %>">
	<aui:script use="liferay-input-localized">
		Liferay.InputLocalized.register(
			'<portlet:namespace /><%= id + fieldSuffix %>',
			{
				boundingBox: '#<portlet:namespace /><%= id %>BoundingBox',
				columns: 20,
				contentBox: '#<portlet:namespace /><%= id %>ContentBox',
				inputNamespace: '<portlet:namespace /><%= id + StringPool.UNDERLINE %>',
				inputPlaceholder: '#<portlet:namespace /><%= id + fieldSuffix %>',
				toggleSelection: false
			}
		);
	</aui:script>
</c:if>