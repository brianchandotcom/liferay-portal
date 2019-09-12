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

<%@ include file="/html/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.dynamic.data.mapping.taglib#/html/start.jsp#pre" />

<div class="lfr-ddm-container" id="<%= randomNamespace %>">
	<c:if test="<%= ddmForm != null %>">
		<div class="input-group-item input-group-item-shrink input-localized-content <%= hideClass %>" role="menu" style="justify-content: flex-end;">

			<%
			String defaultLanguageId = null;

			if (defaultEditLocale == null) {
				defaultLanguageId = LocaleUtil.toLanguageId(ddmForm.getDefaultLocale());
			}
			else {
				defaultLanguageId = LocaleUtil.toLanguageId(defaultEditLocale);
			}

			String normalizedDefaultLanguageId = StringUtil.replace(defaultLanguageId, '_', '-');
			%>

			<liferay-ui:icon-menu
				direction="left-side"
				icon="<%= StringUtil.toLowerCase(normalizedDefaultLanguageId) %>"
				id="<%= fieldsNamespace + \"Menu\" %>"
				markupView="lexicon"
				message="<%= StringPool.BLANK %>"
				showWhenSingleIcon="<%= true %>"
				triggerCssClass="input-localized-trigger"
				triggerLabel="<%= normalizedDefaultLanguageId %>"
				triggerType="button"
			>
				<div id="<portlet:namespace /><%= fieldsNamespace %>PaletteBoundingBox">
					<div class="input-localized-palette-container palette-container" id="<portlet:namespace /><%= fieldsNamespace %>PaletteContentBox">

						<%
						LinkedHashSet<String> uniqueLanguageIds = new LinkedHashSet<String>();

						uniqueLanguageIds.add(defaultLanguageId);

						Set<Locale> availableLocales;

						if (defaultEditLocale == null) {
							availableLocales = ddmForm.getAvailableLocales();
						}
						else {
							availableLocales = LanguageUtil.getAvailableLocales(groupId);
						}

						for (Locale availableLocale : availableLocales) {
							String curLanguageId = LocaleUtil.toLanguageId(availableLocale);

							uniqueLanguageIds.add(curLanguageId);
						}

						int index = 0;

						for (String curLanguageId : uniqueLanguageIds) {
							String linkCssClass = "dropdown-item palette-item";

							Locale curLocale = LocaleUtil.fromLanguageId(curLanguageId);

							String title = HtmlUtil.escapeAttribute(curLocale.getDisplayName(LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request)))) + " " + LanguageUtil.get(LocaleUtil.getDefault(), "translation");

							Map<String, Object> data = new HashMap<String, Object>();

							data.put("languageid", curLanguageId);

							Map<String, Object> iconData = new HashMap<>();
							iconData.put("index", index++);
							iconData.put("languageid", curLanguageId);
							iconData.put("value", curLanguageId);
							%>

							<liferay-ui:icon
								alt="<%= title %>"
								data="<%= iconData %>"
								icon="<%= StringUtil.toLowerCase(StringUtil.replace(curLanguageId, '_', '-')) %>"
								iconCssClass="inline-item inline-item-before"
								linkCssClass="<%= linkCssClass %>"
								markupView="lexicon"
								message="<%= StringUtil.replace(curLanguageId, '_', '-') %>"
								onClick="event.preventDefault(); fireLocaleChanged(event);"
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

		<%
		request.setAttribute("checkRequired", checkRequired);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext = new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setFields(fields);
		ddmFormFieldRenderingContext.setHttpServletRequest(request);
		ddmFormFieldRenderingContext.setHttpServletResponse(response);
		ddmFormFieldRenderingContext.setLocale(requestedLocale);
		ddmFormFieldRenderingContext.setMode(mode);
		ddmFormFieldRenderingContext.setNamespace(fieldsNamespace);
		ddmFormFieldRenderingContext.setPortletNamespace(portletResponse.getNamespace());
		ddmFormFieldRenderingContext.setReadOnly(readOnly);
		ddmFormFieldRenderingContext.setShowEmptyFieldLabel(showEmptyFieldLabel);
		%>

		<%= DDMFormRendererUtil.render(ddmForm, ddmFormFieldRenderingContext) %>

		<aui:input name="<%= HtmlUtil.getAUICompatibleId(ddmFormValuesInputName) %>" type="hidden" />

		<aui:script use="aui-base,liferay-ddm-form">
			var Lang = A.Lang;

			var ddmFormDefinition = <%= DDMUtil.getDDMFormJSONString(ddmForm) %>;

			ddmFormDefinition.defaultLanguageId =
				'<%= LocaleUtil.toLanguageId(defaultLocale) %>';

			var liferayDDMForm = Liferay.component(
				'<portlet:namespace /><%= HtmlUtil.escapeJS(fieldsNamespace) %>ddmForm',
				new Liferay.DDM.Form({
					container: '#<%= randomNamespace %>',
					ddmFormValuesInput:
						'#<portlet:namespace /><%= HtmlUtil.getAUICompatibleId(ddmFormValuesInputName) %>',
					defaultEditLocale:
						'<%= (defaultEditLocale == null) ? StringPool.BLANK : HtmlUtil.escapeJS(defaultEditLocale.toString()) %>',
					documentLibrarySelectorURL: '<%= documentLibrarySelectorURL %>',
					definition: ddmFormDefinition,
					doAsGroupId: <%= scopeGroupId %>,
					fieldsNamespace: '<%= HtmlUtil.escapeJS(fieldsNamespace) %>',
					imageSelectorURL: '<%= imageSelectorURL %>',
					mode: '<%= HtmlUtil.escapeJS(mode) %>',
					p_l_id: <%= themeDisplay.getPlid() %>,
					portletNamespace: '<portlet:namespace />',
					repeatable: <%= repeatable %>,
					requestedLocale:
						'<%= (requestedLocale == null) ? StringPool.BLANK : HtmlUtil.escapeJS(requestedLocale.toString()) %>',
					synchronousFormSubmission: <%= synchronousFormSubmission %>,

					<c:if test="<%= ddmFormValues != null %>">
						values: <%= DDMUtil.getDDMFormValuesJSONString(ddmFormValues) %>
					</c:if>
				})
			);

			var onLocaleChange = function(event) {
				var languageId = event.item.getAttribute('data-value');

				languageId = languageId.replace('_', '-');

				var triggerContent = Lang.sub(
					'<span class="inline-item">{flag}</span><span class="btn-section">{languageId}</span>',
					{
						flag: Liferay.Util.getLexiconIconTpl(languageId.toLowerCase()),
						languageId: languageId
					}
				);

				var trigger = A.one('#<portlet:namespace /><%= fieldsNamespace %>Menu');

				trigger.setHTML(triggerContent);
			};

			Liferay.on('inputLocalized:localeChanged', onLocaleChange);

			window.fireLocaleChanged = function(event) {
				Liferay.fire('inputLocalized:localeChanged', {
					item: event.currentTarget
				});
			};

			var onDestroyPortlet = function(event) {
				if (event.portletId === '<%= portletDisplay.getId() %>') {
					liferayDDMForm.destroy();

					Liferay.detach('inputLocalized:localeChanged', onLocaleChange);
					Liferay.detach('destroyPortlet', onDestroyPortlet);
				}
			};

			Liferay.on('destroyPortlet', onDestroyPortlet);
		</aui:script>
	</c:if>

<liferay-util:dynamic-include key="com.liferay.dynamic.data.mapping.taglib#/html/start.jsp#post" />