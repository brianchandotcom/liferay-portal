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

<%@ include file="/html/taglib/ui/input_localized/init.jsp" %>

<div class="input-group input-localized input-localized-<%= type %>" id="<portlet:namespace /><%= id %>BoundingBox">
	<c:if test="<%= Validator.isNotNull(inputAddon) %>">
		<div class="input-group-item input-group-item-shrink input-group-prepend">
			<span class="input-group-text" id="<portlet:namespace /><%= id %>InputAddon">
				<liferay-ui:message key="<%= StringUtil.shorten(inputAddon, 40) %>" />
			</span>
		</div>
	</c:if>

	<div class="input-group-item">
		<c:choose>
			<c:when test='<%= type.equals("editor") %>'>
				<liferay-ui:input-editor
					contents="<%= mainLanguageValue %>"
					contentsLanguageId="<%= languageId %>"
					cssClass='<%= \"language-value \" + cssClass %>'
					editorName="<%= editorName %>"
					name='<%= inputEditorName %>'
					onChangeMethod='<%= randomNamespace + \"OnChangeEditor\" %>'
					placeholder="<%= placeholder %>"
					toolbarSet="<%= toolbarSet %>"
				/>

				<aui:script>
					function <portlet:namespace /><%= randomNamespace %>OnChangeEditor() {
						var inputLocalized = Liferay.component('<portlet:namespace /><%= HtmlUtil.escapeJS(fieldName) %>');

						var editor = window['<portlet:namespace /><%= HtmlUtil.escapeJS(inputEditorName) %>'];

						inputLocalized.updateInputLanguage(editor.getHTML());
					}

					$('#<portlet:namespace /><%= id %>ContentBox').on(
						'click',
						'.palette-item-inner',
						function() {
							window['<portlet:namespace /><%= HtmlUtil.escapeJS(inputEditorName) %>'].focus();
						}
					);
				</aui:script>
			</c:when>
			<c:when test='<%= type.equals("input") %>'>
				<input aria-describedby="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>_desc" class="form-control language-value <%= cssClass %>" dir="<%= mainLanguageDir %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= id + HtmlUtil.getAUICompatibleId(fieldSuffix) %>" name="<portlet:namespace /><%= HtmlUtil.escapeAttribute(name + fieldSuffix) %>" <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(resourceBundle, placeholder) + "\"" : StringPool.BLANK %> type="text" value="<%= HtmlUtil.escapeAttribute(mainLanguageValue) %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
			</c:when>
			<c:when test='<%= type.equals("textarea") %>'>
				<textarea aria-describedby="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>_desc" class="form-control language-value <%= cssClass %>" dir="<%= mainLanguageDir %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<portlet:namespace /><%= id + HtmlUtil.getAUICompatibleId(fieldSuffix) %>" name="<portlet:namespace /><%= HtmlUtil.escapeAttribute(name + fieldSuffix) %>" <%= Validator.isNotNull(placeholder) ? "placeholder=\"" + LanguageUtil.get(resourceBundle, placeholder) + "\"" : StringPool.BLANK %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>><%= HtmlUtil.escape(mainLanguageValue) %></textarea>

				<c:if test="<%= autoSize %>">
					<aui:script use="aui-autosize-deprecated">
						A.one('#<portlet:namespace /><%= id + HtmlUtil.getAUICompatibleId(fieldSuffix) %>').plug(A.Plugin.Autosize);
					</aui:script>
				</c:if>
			</c:when>
		</c:choose>
	</div>

	<div class="hide-accessible" id="<portlet:namespace /><%= HtmlUtil.escapeAttribute(id + fieldSuffix) %>_desc"><%= defaultLocale.getDisplayName(LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request))) %> <liferay-ui:message key="translation" /></div>

	<c:if test="<%= !availableLocales.isEmpty() && Validator.isNull(languageId) %>">

		<%
		languageIds.add(defaultLanguageId);

		for (Locale availableLocale : availableLocales) {
			String curLanguageId = LocaleUtil.toLanguageId(availableLocale);

			if (curLanguageId.equals(defaultLanguageId)) {
				continue;
			}

			String languageValue = null;

			if (Validator.isNotNull(xml)) {
				languageValue = LocalizationUtil.getLocalization(xml, curLanguageId, false);
			}

			if (Validator.isNotNull(languageValue) || (!ignoreRequestValue && (request.getParameter(name + StringPool.UNDERLINE + curLanguageId) != null))) {
				languageIds.add(curLanguageId);
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

			if (curLanguageId.equals(defaultLanguageId) && Validator.isNull(languageValue)) {
				languageValue = LocalizationUtil.getLocalization(xml, defaultLanguageId, true);
			}
		%>

			<aui:input dir="<%= curLanguageDir %>" disabled="<%= disabled %>" id="<%= HtmlUtil.escapeAttribute(id + StringPool.UNDERLINE + curLanguageId) %>" name="<%= HtmlUtil.escapeAttribute(fieldNamePrefix + name + StringPool.UNDERLINE + curLanguageId + fieldNameSuffix) %>" type="hidden" value="<%= languageValue %>" />

		<%
		}
		%>

		<div class="input-group-item input-group-item-shrink input-localized-content" role="menu">

			<%
			String normalizedDefaultLanguageId = StringUtil.replace(defaultLanguageId, '_', '-');
			%>

			<liferay-ui:icon-menu direction="left-side" id="<%= namespace + id + \"Menu\" %>" icon="<%= StringUtil.toLowerCase(normalizedDefaultLanguageId) %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>" triggerCssClass="input-localized-trigger" triggerLabel="<%= normalizedDefaultLanguageId %>" triggerType="button">
				<div id="<portlet:namespace /><%= id %>PaletteBoundingBox">
					<div class="input-localized-palette-container palette-container" id="<portlet:namespace /><%= id %>PaletteContentBox">

						<%
						LinkedHashSet<String> uniqueLanguageIds = new LinkedHashSet<String>();

						uniqueLanguageIds.add(defaultLanguageId);

						for (Locale availableLocale : availableLocales) {
							String curLanguageId = LocaleUtil.toLanguageId(availableLocale);

							uniqueLanguageIds.add(curLanguageId);
						}

						int index = 0;

						for (String curLanguageId : uniqueLanguageIds) {
							String linkCssClass = "dropdown-item palette-item";

							Locale curLocale = LocaleUtil.fromLanguageId(curLanguageId);

							if (errorLocales.contains(curLocale) || ((index == 0) && errorLocales.isEmpty())) {
								linkCssClass += " active";
							}

							String title = HtmlUtil.escapeAttribute(curLocale.getDisplayName(LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request)))) + " " + LanguageUtil.get(LocaleUtil.getDefault(), "translation");

							Map<String, Object> data = new HashMap<String, Object>();

							data.put("languageid", curLanguageId);

							Map<String, Object> iconData = new HashMap<>();
							iconData.put("index", index++);
							iconData.put("languageid", curLanguageId);
							iconData.put("value", curLanguageId);

							String translationStatus = LanguageUtil.get(request, "untranslated");
							String translationStatusCssClass = "warning";

							if (languageIds.contains(curLanguageId)) {
								translationStatus = LanguageUtil.get(request, "translated");
								translationStatusCssClass = "success";
							}

							if (defaultLanguageId.equals(curLanguageId)) {
								translationStatus = LanguageUtil.get(request, "default");
								translationStatusCssClass = "info";
							}
						%>

							<liferay-util:buffer
								var="linkContent"
							>
								<%= StringUtil.replace(curLanguageId, '_', '-') %>

								<span class="label label-<%= translationStatusCssClass %>"><%= translationStatus %></span>
							</liferay-util:buffer>

							<liferay-ui:icon
								alt="<%= title %>"
								data="<%= iconData %>"
								icon="<%= StringUtil.toLowerCase(StringUtil.replace(curLanguageId, '_', '-')) %>"
								iconCssClass="inline-item inline-item-before"
								linkCssClass="<%= linkCssClass %>"
								markupView="lexicon"
								message="<%= linkContent %>"
								url="javascript:;"
							>
							</liferay-ui:icon>

						<%
						}
						%>

					</div>
				</div>
			</liferay-ui:icon-menu>
		</div>
	</c:if>
</div>

<div class="form-text"><%= HtmlUtil.escape(helpMessage) %></div>

<c:if test="<%= Validator.isNotNull(maxLength) %>">
	<aui:script use="aui-char-counter">
		new A.CharCounter(
			{
				input: '#<portlet:namespace /><%= id + HtmlUtil.getAUICompatibleId(fieldSuffix) %>',
				maxLength: <%= maxLength %>
			}
		);
	</aui:script>
</c:if>

<c:if test="<%= Validator.isNotNull(inputAddon) %>">
	<aui:script sandbox="<%= true %>">
		var inputAddon = '<%= inputAddon.toString() %>';

		if (inputAddon.length > 40) {
			$('#<portlet:namespace /><%= id %>InputAddon').on(
				'mouseenter',
				function(event) {
					Liferay.Portal.ToolTip.show(event.currentTarget, inputAddon);
				}
			);
		}
	</aui:script>
</c:if>

<c:choose>
	<c:when test="<%= !availableLocales.isEmpty() && Validator.isNull(languageId) %>">

		<%
		String modules = "liferay-input-localized";

		if (type.equals("editor")) {
			Editor editor = InputEditorTag.getEditor(request, editorName);

			modules += StringPool.COMMA + StringUtil.merge(editor.getJavaScriptModules());
		}
		%>

		<aui:script use="<%= modules %>">
			var defaultLanguageId = '<%= defaultLanguageId %>';

			var available = {};

			var errors = {};

			<%
			for (Locale availableLocale : availableLocales) {
				String availableLanguageId = LocaleUtil.toLanguageId(availableLocale);
			%>

				available['<%= availableLanguageId %>'] = '<%= availableLocale.getDisplayName(locale) %>';

			<%
			}
			%>

			var availableLanguageIds = A.Array.dedupe(
				[defaultLanguageId].concat(A.Object.keys(available))
			);

			<%
			for (Locale errorLocale : errorLocales) {
				String errorLocaleId = LocaleUtil.toLanguageId(errorLocale);
			%>

				errors['<%= errorLocaleId %>'] = '<%= errorLocale.getDisplayName(locale) %>';

			<%
			}
			%>

			var errorLanguageIds = A.Array.dedupe(A.Object.keys(errors));
			var placeholder = '#<portlet:namespace /><%= id + HtmlUtil.getAUICompatibleId(fieldSuffix) %>';

			<c:if test='<%= type.equals("editor") %>'>
				placeholder = placeholder + 'Editor';
			</c:if>

			Liferay.InputLocalized.register(
				'<portlet:namespace /><%= id + HtmlUtil.getAUICompatibleId(fieldSuffix) %>',
				{
					boundingBox: '#<portlet:namespace /><%= id %>PaletteBoundingBox',
					columns: 20,
					contentBox: '#<portlet:namespace /><%= id %>PaletteContentBox',
					defaultLanguageId: defaultLanguageId,

					<c:if test='<%= type.equals("editor") %>'>
						editor: window['<portlet:namespace /><%= HtmlUtil.escapeJS(fieldName) + "Editor" %>'],
					</c:if>

					fieldPrefix: '<%= fieldPrefix %>',
					fieldPrefixSeparator: '<%= fieldPrefixSeparator %>',
					helpMessage: '<%= HtmlUtil.escapeJS(helpMessage) %>',
					id: '<%= id %>',
					inputPlaceholder: placeholder,
					inputBox: '#<portlet:namespace /><%= id %>BoundingBox',
					items: availableLanguageIds,
					itemsError: errorLanguageIds,
					lazy: <%= !type.equals("editor") %>,
					name: '<%= HtmlUtil.escapeJS(name) %>',
					namespace: '<portlet:namespace />',
					toggleSelection: false,
					translatedLanguages: '<%= StringUtil.merge(languageIds) %>'
				}
			);

			<c:if test="<%= autoFocus %>">
				Liferay.Util.focusFormField('#<portlet:namespace /><%= HtmlUtil.escapeJS(id + HtmlUtil.getAUICompatibleId(fieldSuffix)) %>');
			</c:if>
		</aui:script>
	</c:when>
	<c:otherwise>
		<c:if test="<%= autoFocus %>">
			<aui:script>
				Liferay.Util.focusFormField('#<portlet:namespace /><%= HtmlUtil.escapeJS(id + HtmlUtil.getAUICompatibleId(fieldSuffix)) %>');
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>