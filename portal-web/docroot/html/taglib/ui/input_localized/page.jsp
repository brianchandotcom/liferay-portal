<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-localized:cssClass"));
String defaultLanguageId = (String)request.getAttribute("liferay-ui:input-localized:defaultLanguageId");
boolean disabled = GetterUtil.getBoolean((String) request.getAttribute("liferay-ui:input-localized:disabled"));
String id = (String)request.getAttribute("liferay-ui:input-localized:id");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ui:input-localized:dynamicAttributes");
String formName = (String)request.getAttribute("liferay-ui:input-localized:formName");
boolean ignoreRequestValue = GetterUtil.getBoolean((String) request.getAttribute("liferay-ui:input-localized:ignoreRequestValue"));
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
%>

<span class="taglib-input-localized">
	<c:choose>
		<c:when test='<%= type.equals("input") %>'>
			<input class="language-value <%= cssClass %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= id + StringPool.UNDERLINE + mainLanguageId %>" name="<portlet:namespace /><%= name + StringPool.UNDERLINE + mainLanguageId %>" type="text" value="<%= HtmlUtil.escape(mainLanguageValue) %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
		</c:when>
		<c:when test='<%= type.equals("textarea") %>'>
			<textarea class="language-value <%= cssClass %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= id + StringPool.UNDERLINE + mainLanguageId %>" name="<portlet:namespace /><%= name + StringPool.UNDERLINE + mainLanguageId %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>><%= HtmlUtil.escape(mainLanguageValue) %></textarea>
		</c:when>
	</c:choose>

	<c:if test="<%= Validator.isNotNull(maxLength) %>">
		<aui:script use="aui-char-counter">
			new A.CharCounter(
				{
					input: '#<portlet:namespace /><%= id + StringPool.UNDERLINE + mainLanguageId %>',
					maxLength: <%= maxLength %>
				}
			);
		</aui:script>
	</c:if>

	<c:if test="<%= (locales.length > 1) && Validator.isNull(languageId) %>">
		<span class="flag-selector nobr">
			<img alt="<%= defaultLocale.getDisplayName() %>" class="default-language" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= mainLanguageId %>.png" />

			<%
			List<String> languageIds = new ArrayList<String>();

			if (Validator.isNotNull(xml)) {
				for (int i = 0; i < locales.length; i++) {
					if (locales[i].equals(defaultLocale)) {
						continue;
					}

					String selLanguageId = LocaleUtil.toLanguageId(locales[i]);
					String languageValue = LocalizationUtil.getLocalization(xml, selLanguageId, false);

					if (Validator.isNotNull(languageValue) || (!ignoreRequestValue && (request.getParameter(name + StringPool.UNDERLINE + selLanguageId) != null))) {
						languageIds.add(selLanguageId);
					}
				}
			}
			%>

			<a class="lfr-floating-trigger" href="javascript:;" id="<%= randomNamespace %>languageSelectorTrigger">
				<liferay-ui:message key="other-languages" /> (<%= languageIds.size() %>)
			</a>
		</span>

		<%
		if (languageIds.isEmpty()) {
			languageIds.add(StringPool.BLANK);
		}
		%>

		<div class="lfr-floating-container lfr-language-selector aui-helper-hidden" id="<%= randomNamespace %>languageSelector">
			<div class="lfr-panel aui-form">
				<div class="lfr-panel-titlebar">
					<h3 class="lfr-panel-title"><span><liferay-ui:message key="other-languages" /></span></h3>
				</div>

				<div class="lfr-panel-content">

					<%
					for (int i = 0; i < languageIds.size(); i++) {
						String curLanguageId = languageIds.get(i);
					%>

						<div class="lfr-form-row">
							<div class="row-names">
								<img alt="<%= Validator.isNotNull(curLanguageId) ? LocaleUtil.fromLanguageId(curLanguageId).getDisplayName() : StringPool.BLANK %>" class="language-flag" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= Validator.isNotNull(curLanguageId) ? curLanguageId : "../spacer" %>.png" />

								<select <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace />languageId<%= i %>">
									<option value="" />

									<%
									for (Locale curLocale : locales) {
										if (curLocale.equals(defaultLocale)) {
											continue;
										}

										String optionStyle = StringPool.BLANK;

										String selLanguageId = LocaleUtil.toLanguageId(curLocale);
										String languageValue = LocalizationUtil.getLocalization(xml, selLanguageId, false);

										if (Validator.isNotNull(xml) && Validator.isNotNull(languageValue)) {
											optionStyle = "style=\"font-weight: bold\"";
										}
									%>

										<option <%= (curLanguageId.equals(selLanguageId)) ? "selected" : "" %> <%= optionStyle %> value="<%= selLanguageId %>"><%= curLocale.getDisplayName(locale) %></option>

									<%
									}
									%>

								</select>

								<%
								String languageValue = StringPool.BLANK;

								if (Validator.isNotNull(xml)) {
									languageValue = LocalizationUtil.getLocalization(xml, curLanguageId, false);
								}

								if (!ignoreRequestValue) {
									languageValue = ParamUtil.getString(request, name + StringPool.UNDERLINE + curLanguageId, languageValue);
								}
								%>

								<c:choose>
									<c:when test='<%= type.equals("input") %>'>
										<input class="language-value" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= id + StringPool.UNDERLINE + curLanguageId %>" name="<portlet:namespace /><%= name + StringPool.UNDERLINE + curLanguageId %>" type="text" value="<%= HtmlUtil.escape(languageValue) %>" />
									</c:when>
									<c:when test='<%= type.equals("textarea") %>'>
										<textarea class="language-value" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= id + StringPool.UNDERLINE + curLanguageId %>" name="<portlet:namespace /><%= name + StringPool.UNDERLINE + curLanguageId %>"><%= HtmlUtil.escape(languageValue) %></textarea>
									</c:when>
								</c:choose>

								<c:if test="<%= Validator.isNotNull(maxLength) %>">
									<aui:script use="aui-char-counter">
										new A.CharCounter(
											{
												input: '#<portlet:namespace /><%= id + StringPool.UNDERLINE + curLanguageId %>',
												maxLength: <%= maxLength %>
											}
										);
									</aui:script>
								</c:if>
							</div>
						</div>

					<%
					}
					%>

				</div>
			</div>
		</div>
	</c:if>
</span>

<c:if test="<%= (locales.length > 1) && Validator.isNull(languageId) %>">
	<aui:script use="liferay-auto-fields,liferay-panel-floating">
		var updateLanguageFlag = function(event) {
			var target = event.target;

			var selectedValue = target.val();

			var newName = '<portlet:namespace /><%= name %>_';
			var newId = '<portlet:namespace /><%= id %>_';

			var currentRow = target.ancestor('.lfr-form-row');

			var img = currentRow.all('img.language-flag');
			var imgSrc = 'spacer';

			if (selectedValue) {
				newName ='<portlet:namespace /><%= name %>_' + selectedValue;
				newId ='<portlet:namespace /><%= id %>_' + selectedValue;

				imgSrc = 'language/' + selectedValue;
			}

			var inputField = currentRow.one('.language-value');

			if (inputField) {
				inputField.attr('name', newName);
				inputField.attr('id', newId);
			}

			if (img) {
				img.attr('src', '<%= themeDisplay.getPathThemeImages() %>/' + imgSrc + '.png');
			}
		};

		var autoFields = null;

		<c:if test="<%= !disabled %>">
			autoFields = new Liferay.AutoFields(
				{
					contentBox: '#<%= randomNamespace %>languageSelector .lfr-panel-content',
					on: {
						'clone': function(event) {
							var instance = this;

							var row = event.row;

							var select = row.one('select');
							var img = row.one('img.language-flag');

							if (select) {
								select.on('change', updateLanguageFlag);
							}

							if (img) {
								img.attr('src', '<%= themeDisplay.getPathThemeImages() %>/spacer.png');
							}
						}
					}
				}
			);
		</c:if>

		var form = A.one(document.<portlet:namespace /><%= formName %>);

		var panel = new Liferay.PanelFloating(
			{
				collapsible: false,
				container: '#<%= randomNamespace %>languageSelector',
				on: {
					hide: function(event) {
						var instance = this;

						instance._positionHelper.appendTo(form);
					},
					show: function(event) {
						var instance = this;

						instance._positionHelper.appendTo(document.body);
					}
				},
				trigger: '#<%= randomNamespace %>languageSelectorTrigger',
				width: 500
			}
		);

		panel._positionHelper.appendTo(form);

		var languageSelectorTrigger = A.one('#<%= randomNamespace %>languageSelectorTrigger');

		if (languageSelectorTrigger) {
			Liferay.component(
				'<%= namespace + name %>languageSelector',
				function(event) {
					if (handle) {
						handle.detach();

						handle = null;
					}

					autoFields.render();

					A.all('#<%= randomNamespace %>languageSelector select').each(
						function(item) {
							if (item) {
								item.on('change', updateLanguageFlag);
							}
						}
					);

					languageSelectorTrigger.setData('autoFieldsInstance', autoFields);
					languageSelectorTrigger.setData('panelInstance', panel);
				}
			);

			var handle = languageSelectorTrigger.once(
				'click',
				function(event) {
					Liferay.component('<%= namespace + name %>languageSelector');
				}
			);
		}
	</aui:script>
</c:if>