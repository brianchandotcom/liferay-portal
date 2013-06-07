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

Locale mainLocale = LocaleUtil.fromLanguageId(mainLanguageId);

String mainLanguageDir = LanguageUtil.get(mainLocale, "lang.dir");

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

List<String> languageIds = new ArrayList<String>();
%>

<span class="input-localized liferay-input-localized" id="<portlet:namespace /><%= id %>BoundingBox">
	<c:choose>
		<c:when test='<%= type.equals("input") %>'>
			<input class="language-value <%= cssClass %>" dir="<%= mainLanguageDir %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>" name="<portlet:namespace /><%= HtmlUtil.escapeAttribute(name + fieldSuffix) %>" type="text" value="<%= HtmlUtil.escapeAttribute(mainLanguageValue) %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
		</c:when>
		<c:when test='<%= type.equals("textarea") %>'>
			<textarea class="language-value <%= cssClass %>" dir="<%= mainLanguageDir %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>" name="<portlet:namespace /><%= HtmlUtil.escapeAttribute(name + fieldSuffix) %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>><%= HtmlUtil.escape(mainLanguageValue) %></textarea>

			<c:if test="<%= autoSize %>">
				<aui:script use="aui-autosize-deprecated">
					A.one('#<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>').plug(A.Plugin.Autosize);
				</aui:script>
			</c:if>
		</c:when>
	</c:choose>

	<c:if test="<%= (locales.length > 1) && Validator.isNull(languageId) %>">

		<%
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

			Locale curLocale = LocaleUtil.fromLanguageId(curLanguageId);

			String curLanguageDir = LanguageUtil.get(curLocale, "lang.dir");

			String languageValue = StringPool.BLANK;

			if (Validator.isNotNull(xml)) {
				languageValue = LocalizationUtil.getLocalization(xml, curLanguageId, false);
			}

			if (!ignoreRequestValue) {
				languageValue = ParamUtil.getString(request, name + StringPool.UNDERLINE + curLanguageId, languageValue);
			}
		%>

			<aui:input dir="<%= curLanguageDir %>" disabled="<%= disabled %>" id="<%= HtmlUtil.escapeAttribute(id + StringPool.UNDERLINE + curLanguageId) %>" name="<%= HtmlUtil.escapeAttribute(name + StringPool.UNDERLINE + curLanguageId) %>" type="hidden" value="<%= languageValue %>" />

		<%
		}
		%>

	</c:if>

	<div class="input-localized-content" id="<portlet:namespace /><%= id %>ContentBox">
		<table class="palette-container">
			<tr class="palette-items-container">

				<%
				LinkedHashSet<String> uniqueLanguageIds = new LinkedHashSet<String>();

				uniqueLanguageIds.add(defaultLanguageId);
				uniqueLanguageIds.add(themeDisplay.getLanguageId());

				for (int i = 0; i < locales.length; i++) {
					String curLanguageId = LocaleUtil.toLanguageId(locales[i]);

					uniqueLanguageIds.add(curLanguageId);
				}

				int index = 0;

				for (String curLanguageId : uniqueLanguageIds) {
				%>

					<td class='palette-item <%= (index == 0) ? "palette-item-selected" : StringPool.BLANK %>' data-index="<%= index++ %>" data-value="<%= curLanguageId %>">
						<a class="palette-item-inner" href="javascript:void(0);">
							<img class="lfr-input-localized-flag" data-languageid="<%= curLanguageId %>" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= curLanguageId %>.png" />
							<div class="lfr-input-localized-state"></div>
						</a>
					</td>

				<%
				}
				%>

			</tr>
		</table>
	</div>
</span>

<c:if test="<%= Validator.isNotNull(maxLength) %>">
	<aui:script use="aui-char-counter">
		new A.CharCounter(
			{
				input: '#<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>',
				maxLength: <%= maxLength %>
			}
		);
	</aui:script>
</c:if>

<c:if test="<%= (locales.length > 1) && Validator.isNull(languageId) %>">
	<aui:script use="liferay-input-localized">
		Liferay.InputLocalized.register(
			'<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>',
			{
				boundingBox: '#<portlet:namespace /><%= id %>BoundingBox',
				columns: 20,
				contentBox: '#<portlet:namespace /><%= id %>ContentBox',
				inputNamespace: '<portlet:namespace /><%= id + StringPool.UNDERLINE %>',
				inputPlaceholder: '#<portlet:namespace /><%= HtmlUtil.escapeJS(id + fieldSuffix) %>',
				toggleSelection: false,
				translatedLanguages: '<%= StringUtil.merge(languageIds) %>'
			}
		);
	</aui:script>
</c:if>