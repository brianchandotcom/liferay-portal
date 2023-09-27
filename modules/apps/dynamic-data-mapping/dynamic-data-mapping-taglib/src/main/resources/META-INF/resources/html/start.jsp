<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.dynamic.data.mapping.taglib#/html/start.jsp#pre" />

<div class="lfr-ddm-container" id="<%= randomNamespace %>">
	<c:if test="<%= ddmForm != null %>">
		<div class="input-group-item input-group-item-shrink input-localized-content <%= hideCssClass %>" role="menu" style="justify-content: flex-end;">

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
				id='<%= fieldsNamespace + "Menu" %>'
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

						Set<Locale> availableLocales = null;

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

							String translationStatusCssClass = "warning";
							String translationStatusMessage = LanguageUtil.get(request, "untranslated");

							if (ddmFormValues != null) {
								Set<Locale> ddmFormValuesAvailableLocales = ddmFormValues.getAvailableLocales();

								if (ddmFormValuesAvailableLocales.contains(curLocale)) {
									translationStatusCssClass = "success";
									translationStatusMessage = LanguageUtil.get(request, "translated");
								}
							}

							if (curLanguageId.equals(defaultLanguageId)) {
								linkCssClass += " active";
								translationStatusCssClass = "info";
								translationStatusMessage = LanguageUtil.get(request, "default");
							}
						%>

						<liferay-util:buffer
							var="messageBuffer"
						>
							<%= StringUtil.replace(curLanguageId, '_', '-') %>

							<span class="label label-<%= translationStatusCssClass %>"><%= translationStatusMessage %></span>
						</liferay-util:buffer>

							<c:if test="<%= showLanguageSelector %>">
								<liferay-ui:icon
									alt='<%= HtmlUtil.escapeAttribute(curLocale.getDisplayName(LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request)))) + " " + LanguageUtil.get(LocaleUtil.getDefault(), "translation") %>'
									data='<%=
										HashMapBuilder.<String, Object>put(
											"index", index++
										).put(
											"languageid", curLanguageId
										).put(
											"value", curLanguageId
										).build()
									%>'
									icon="<%= StringUtil.toLowerCase(StringUtil.replace(curLanguageId, '_', '-')) %>"
									iconCssClass="inline-item inline-item-before"
									linkCssClass="<%= linkCssClass %>"
									markupView="lexicon"
									message="<%= messageBuffer %>"
									onClick="event.preventDefault(); fireLocaleChanged(event);"
									url="javascript:void(0);"
								>
								</liferay-ui:icon>
							</c:if>

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
		ddmFormFieldRenderingContext.setPortletNamespace(liferayPortletResponse.getNamespace());
		ddmFormFieldRenderingContext.setReadOnly(readOnly);
		ddmFormFieldRenderingContext.setShowEmptyFieldLabel(showEmptyFieldLabel);
		%>

		<%= DDMFormRendererUtil.render(ddmForm, ddmFormFieldRenderingContext) %>

		<aui:input name="<%= HtmlUtil.getAUICompatibleId(ddmFormValuesInputName) %>" type="hidden" />

		<aui:script use="aui-base,liferay-ddm-form">
			var Lang = A.Lang;

			var ddmFormDefinition = <%= DDMUtil.getDDMFormJSONString(ddmForm) %>;

			<%
			if (defaultLocale != null) {
			%>

				ddmFormDefinition.defaultLanguageId =
					'<%= LocaleUtil.toLanguageId(defaultLocale) %>';

			<%
			}

			Group group = themeDisplay.getScopeGroup();
			%>

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
					layoutSelectorURL: '<%= layoutSelectorURL %>',
					isPrivateLayoutsEnabled: <%= group.isPrivateLayoutsEnabled() %>,
					mode: '<%= HtmlUtil.escapeJS(mode) %>',
					p_l_id: <%= themeDisplay.getPlid() %>,
					portletId: '<%= themeDisplay.getPpid() %>',
					portletNamespace: '<portlet:namespace />',
					repeatable: <%= repeatable %>,
					requestedLocale:
						'<%= (requestedLocale == null) ? StringPool.BLANK : HtmlUtil.escapeJS(requestedLocale.toString()) %>',
					synchronousFormSubmission: <%= synchronousFormSubmission %>,

					<c:if test="<%= ddmFormValues != null %>">
						values: <%= DDMUtil.getDDMFormValuesJSONString(ddmFormValues) %>,
					</c:if>
				})
			);

			var onLocaleChange = function (event) {
				var languageId = event.item.getAttribute('data-value');

				var childrenItems = A.all(
					'#<portlet:namespace /><%= fieldsNamespace %>PaletteContentBox a'
				);

				const triggerMenu = A.one(
					'#<portlet:namespace /><%= fieldsNamespace %>Menu'
				);

				const listContainer = triggerMenu.getData('menuListContainer');

				if (childrenItems._nodes && !childrenItems._nodes.length && listContainer) {
					childrenItems = listContainer.all(childrenItems._query);
				}

				childrenItems.each((item) => {
					if (item.hasClass('active')) {
						item.removeClass('active');
					}

					var languageIdActive = item.getAttribute('data-value');

					if (languageId === languageIdActive) {
						item.addClass('active');
					}
				});

				languageId = languageId.replaceAll('_', '-');

				const triggerContent = Lang.sub(
					'<span class="inline-item">{flag}</span><span class="btn-section">{languageId}</span>',
					{
						flag: Liferay.Util.getLexiconIconTpl(languageId.toLowerCase()),
						languageId: languageId,
					}
				);

				triggerMenu.setData('menuListContainer', listContainer);
				triggerMenu.setHTML(triggerContent);
			};

			Liferay.on('inputLocalized:localeChanged', onLocaleChange);

			window.fireLocaleChanged = function (event) {
				Liferay.fire('inputLocalized:localeChanged', {
					item: event.currentTarget,
				});
			};

			var onDestroyPortlet = function (event) {
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