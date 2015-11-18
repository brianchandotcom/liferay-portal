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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = (String)request.getAttribute("edit_article.jsp-redirect");

String portletResource = ParamUtil.getString(request, "portletResource");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

long folderId = BeanParamUtil.getLong(article, request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
long classNameId = ParamUtil.getLong(request, "classNameId");
String classPK = ParamUtil.getString(request, "classPK");

String articleId = BeanParamUtil.getString(article, request, "articleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");
String instanceIdKey = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

String structureId = StringPool.BLANK;

long ddmStructureGroupId = groupId;
String ddmStructureName = LanguageUtil.get(pageContext, "default");
String ddmStructureDescription = StringPool.BLANK;

DDMStructure ddmStructure = (DDMStructure)request.getAttribute("edit_article.jsp-structure");

if (ddmStructure != null) {
	structureId = ddmStructure.getStructureKey();

	ddmStructureGroupId = ddmStructure.getGroupId();
	ddmStructureName = ddmStructure.getName(locale);
	ddmStructureDescription = ddmStructure.getDescription(locale);
}

List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

if (ddmStructure != null) {
	ddmTemplates.addAll(DDMTemplateServiceUtil.getTemplates(ddmStructureGroupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));

	if (groupId != ddmStructureGroupId) {
		ddmTemplates.addAll(DDMTemplateServiceUtil.getTemplates(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));
	}
}

String templateId = StringPool.BLANK;

DDMTemplate ddmTemplate = (DDMTemplate)request.getAttribute("edit_article.jsp-template");

if (ddmTemplate != null) {
	templateId = ddmTemplate.getTemplateKey();
}
else if (!ddmTemplates.isEmpty()) {
	ddmTemplate = ddmTemplates.get(0);

	templateId = ddmTemplate.getTemplateKey();
}

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");
String toLanguageId = (String)request.getAttribute("edit_article.jsp-toLanguageId");

String content = ParamUtil.getString(request, "articleContent");

boolean preselectCurrentLayout = false;

boolean changeStructure = GetterUtil.getBoolean(request.getAttribute("edit_article.jsp-changeStructure"));

if (article != null) {
	if (Validator.isNull(content)) {
		content = article.getContent();

		Map<String, String> tokens = new HashMap<String, String>();

		tokens.put("article_group_id", String.valueOf(groupId));
		tokens.put("structure_id", structureId);

		if (Validator.isNotNull(toLanguageId)) {
			content = JournalArticleImpl.getContentByLocale(content, toLanguageId, tokens);
		}
		else {
			content = JournalArticleImpl.getContentByLocale(content, defaultLanguageId, tokens);
		}
	}
}
else {
	UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

	long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

	if (refererPlid > 0) {
		Layout refererLayout = LayoutLocalServiceUtil.getLayout(refererPlid);

		typeSettingsProperties = refererLayout.getTypeSettingsProperties();

		String defaultAssetPublisherPortletId = typeSettingsProperties.getProperty(LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		if (Validator.isNotNull(defaultAssetPublisherPortletId)) {
			preselectCurrentLayout = true;
		}
	}
}

Document contentDoc = null;

String[] availableLocales = null;

if (Validator.isNotNull(content)) {
	try {
		contentDoc = SAXReaderUtil.read(content);

		Element contentEl = contentDoc.getRootElement();

		availableLocales = StringUtil.split(contentEl.attributeValue("available-locales"));

		if (!ArrayUtil.contains(availableLocales, defaultLanguageId)) {
			availableLocales = ArrayUtil.append(availableLocales, defaultLanguageId);
		}

		if (ddmStructure == null) {
			content = contentDoc.getRootElement().element("static-content").getText();
		}
	}
	catch (Exception e) {
		contentDoc = null;
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="content" />

<aui:model-context bean="<%= article %>" defaultLanguageId="<%= defaultLanguageId %>" model="<%= JournalArticle.class %>" />

<portlet:renderURL var="editArticleRenderPopUpURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="structureId" value="<%= structureId %>" />
</portlet:renderURL>

<portlet:renderURL var="updateDefaultLanguageURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="portletResource" value="<%= portletResource %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
	<portlet:param name="classPK" value="<%= classPK %>" />
	<portlet:param name="structureId" value="<%= structureId %>" />
	<portlet:param name="templateId" value="<%= templateId %>" />
</portlet:renderURL>

<div class="journal-article-body" id="<portlet:namespace />journalArticleBody">
	<div class="journal-article-body-content">
		<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />
		<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
		<liferay-ui:error exception="<%= ArticleTitleException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= ArticleVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing-please-copy-your-changes-and-try-again" />
		<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />
		<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />

		<liferay-ui:error exception="<%= LocaleException.class %>">

			<%
			LocaleException le = (LocaleException)errorException;
			%>

			<c:if test="<%= le.getType() == LocaleException.TYPE_CONTENT %>">
				<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
			</c:if>
		</liferay-ui:error>

		<div class="journal-article-header-edit" id="<portlet:namespace />articleHeaderEdit">
			<div class="journal-article-header-id">
				<c:if test="<%= (article == null) || article.isNew() %>">
					<c:choose>
						<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID || (classNameId != JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
							<aui:input name="newArticleId" type="hidden" />
							<aui:input name="autoArticleId" type="hidden" value="<%= true %>" />
						</c:when>
						<c:otherwise>
							<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" cssClass="lfr-input-text-container" field="articleId" fieldParam="newArticleId" label="id" name="newArticleId" value="<%= newArticleId %>" />

							<aui:input label="autogenerate-id" name="autoArticleId" type="checkbox" />
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>

			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<div class="article-structure-template-toolbar journal-metadata">
					<span class="alert alert-block hide structure-message" id="<portlet:namespace />structureMessage">
						<liferay-ui:message key="this-structure-has-not-been-saved" />

						<liferay-ui:message arguments='<%= new Object[] {"journal-save-structure-trigger", "#"} %>' key="click-here-to-save-it-now" />
					</span>

					<aui:row>
						<aui:col cssClass="article-structure" width="<%= 50 %>">
							<span class="article-structure-label"><liferay-ui:message key="structure" />:</span>

							<aui:fieldset cssClass="article-structure-toolbar">
								<div class="journal-form-presentation-label">
									<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
									<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
									<aui:input name="structureName" type="hidden" value="<%= ddmStructureName %>" />
									<aui:input name="structureDescription" type="hidden" value="<%= ddmStructureDescription %>" />

									<span class="structure-name-label" id="<portlet:namespace />structureNameLabel">
										<%= HtmlUtil.escape(ddmStructureName) %>
									</span>

									<c:if test="<%= (ddmStructure != null) && DDMStructurePermission.contains(permissionChecker, ddmStructure, PortletKeys.JOURNAL, ActionKeys.UPDATE) %>">
										<liferay-ui:icon id="editDDMStructure" image="edit" url="javascript:;" />
									</c:if>

									<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
										<liferay-ui:icon
											iconCssClass="icon-search"
											label="<%= true %>"
											linkCssClass="btn"
											message="select"
											url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
										/>

										<c:if test="<%= Validator.isNotNull(structureId) %>">
											<span class="default-link">(<a href="javascript:;" id="<portlet:namespace />loadDefaultStructure"><liferay-ui:message key="use-default" /></a>)</span>
										</c:if>
									</c:if>
								</div>
							</aui:fieldset>
						</aui:col>

						<aui:col cssClass="article-template" width="<%= 50 %>">
							<span class="article-template-label"><liferay-ui:message key="template" />:</span>

							<aui:fieldset cssClass="article-template-toolbar">
								<div class="journal-form-presentation-label">
									<aui:input name="templateId" type="hidden" value="<%= templateId %>" />

									<span class="template-name-label" id="<portlet:namespace />templateNameLabel">
										<%= (ddmTemplate != null) ? HtmlUtil.escape(ddmTemplate.getName(locale)) : LanguageUtil.get(pageContext, "none") %>
									</span>

									<c:if test="<%= ddmTemplate != null %>">
										<c:if test="<%= ddmTemplate.isSmallImage() %>">
											<img alt="" class="article-template-image" id="<portlet:namespace />templateImage" src="<%= HtmlUtil.escapeAttribute(_getTemplateImage(themeDisplay, ddmTemplate)) %>" />
										</c:if>

										<c:if test="<%= DDMTemplatePermission.contains(permissionChecker, ddmTemplate, PortletKeys.JOURNAL, ActionKeys.UPDATE) %>">
											<liferay-ui:icon id="editDDMTemplate" image="edit" url="javascript:;" />
										</c:if>
									</c:if>

									<c:if test="<%= ddmStructure != null %>">
										<liferay-ui:icon
											iconCssClass="icon-search"
											label="<%= true %>"
											linkCssClass="btn"
											message="select"
											url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMTemplateSelector();" %>'
										/>
									</c:if>
								</div>
							</aui:fieldset>
						</aui:col>
					</aui:row>
				</div>
			</c:if>

			<div class="article-translation-toolbar journal-metadata">
				<div class="alert alert-info hide" id="<portlet:namespace />translationsMessage">
					<liferay-ui:message key="the-changes-in-your-translations-will-be-available-once-the-content-is-published" />
				</div>

				<div>
					<c:choose>
						<c:when test="<%= Validator.isNull(toLanguageId) %>">
							<span for="<portlet:namespace />defaultLanguageId"><liferay-ui:message key="web-content-default-language" /></span>:

							<span class="lfr-translation-manager-selector nobr">
								<span class="article-default-language lfr-token lfr-token-primary" id="<portlet:namespace />textLanguageId">
									<img alt="<liferay-ui:message key="default-language" />" src='<%= HtmlUtil.escapeAttribute(themeDisplay.getPathThemeImages() + "/language/" + defaultLanguageId + ".png") %>' />

									<%= LocaleUtil.fromLanguageId(defaultLanguageId).getDisplayName(locale) %>
								</span>

								<a href="javascript:;" id="<portlet:namespace />changeLanguageId"><liferay-ui:message key="change" /></a>

								<aui:select cssClass="hide" id="defaultLocale" inlineField="<%= true %>" label="" name="defaultLanguageId" title="default-language">

									<%
									Locale[] locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

									for (int i = 0; i < locales.length; i++) {
									%>

										<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= defaultLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

									<%
									}
									%>

								</aui:select>

								<liferay-ui:icon-help message="default-language-help" />
							</span>

							<c:if test="<%= (article != null) && !article.isNew() %>">
								<span class="lfr-translation-manager-add-menu">
									<liferay-ui:icon-menu
										cssClass="add-translations-menu"
										direction="down"
										icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>'
										message='<%= LanguageUtil.get(pageContext, "add-translation") %>'
										showArrow="<%= true %>"
										showWhenSingleIcon="<%= true %>"
									>

										<%
										Locale[] locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

										for (int i = 0; i < locales.length; i++) {
											if (ArrayUtil.contains(article.getAvailableLanguageIds(), LocaleUtil.toLanguageId(locales[i]))) {
												continue;
											}

											String taglibEditArticleURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", LocaleUtil.toLanguageId(locales[i]));
											String taglibEditURL = "javascript:Liferay.Util.openWindow({cache: false, id: '" + renderResponse.getNamespace() + LocaleUtil.toLanguageId(locales[i]) + "', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(pageContext, "web-content-translation")) + "', uri: '" + HtmlUtil.escapeJS(taglibEditArticleURL) + "'});";
										%>

											<liferay-ui:icon
												image='<%= "../language/" + LocaleUtil.toLanguageId(locales[i]) %>'
												message="<%= locales[i].getDisplayName(locale) %>"
												url="<%= taglibEditURL %>"
											/>

										<%
										}
										%>

									</liferay-ui:icon-menu>
								</span>
							</c:if>
						</c:when>
						<c:otherwise>
							<aui:input id="defaultLocale" name="defaultLanguageId" type="hidden" value="<%= defaultLanguageId %>" />
						</c:otherwise>
					</c:choose>
				</div>

				<c:if test="<%= article != null %>">

					<%
					String[] translations = article.getAvailableLanguageIds();
					%>

					<div class='<%= (Validator.isNull(toLanguageId) && ((translations.length > 1) || (translations.length == 1 && !translations[0].equals(defaultLanguageId)))) ? "contains-translations" :"" %>' id="<portlet:namespace />availableTranslationContainer">
						<c:choose>
							<c:when test="<%= Validator.isNotNull(toLanguageId) %>">
								<liferay-util:buffer var="languageLabel">
									<%= LocaleUtil.fromLanguageId(toLanguageId).getDisplayName(locale) %>

									<img alt="" src='<%= HtmlUtil.escapeAttribute(themeDisplay.getPathThemeImages() + "/language/" + toLanguageId + ".png") %>' />
								</liferay-util:buffer>

								<%= LanguageUtil.format(pageContext, "translating-web-content-to-x", languageLabel) %>

								<aui:input name="toLanguageId" type="hidden" value="<%= toLanguageId %>" />
							</c:when>
							<c:otherwise>
								<span class='available-translations<%= ((translations.length > 1) || (translations.length == 1 && !translations[0].equals(defaultLanguageId))) ? "" : " hide" %>' id="<portlet:namespace />availableTranslationsLinks">
									<label><liferay-ui:message key="available-translations" /></label>

										<%
										for (int i = 0; i < translations.length; i++) {
											if (translations[i].equals(defaultLanguageId)) {
												continue;
											}

											String editTranslationURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", translations[i]);
										%>

										<a class="journal-article-translation-<%= translations[i] %> lfr-token" href="javascript:;" onClick="Liferay.Util.openWindow({cache: false, id: '<portlet:namespace /><%= translations[i] %>', title: '<%= UnicodeLanguageUtil.get(pageContext, "web-content-translation") %>', uri: '<%= editTranslationURL %>'});">
											<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/language/" + translations[i] + ".png" %>' />

											<%= LocaleUtil.fromLanguageId(translations[i]).getDisplayName(locale) %>
										</a>

									<%
									}
									%>

								</span>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>
			</div>
		</div>

		<div class="journal-article-general-fields">
			<aui:input autoFocus="<%= (((article != null) && !article.isNew()) && !PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID && windowState.equals(WindowState.MAXIMIZED)) %>" defaultLanguageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" ignoreRequestValue="<%= changeStructure %>" languageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" name="title">
				<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
					<aui:validator name="required" />
				</c:if>
			</aui:input>
		</div>

		<div class="journal-article-container" id="<portlet:namespace />journalArticleContainer">
			<c:choose>
				<c:when test="<%= ddmStructure == null %>">
					<div id="<portlet:namespace />structureTreeWrapper">
						<ul class="structure-tree" id="<portlet:namespace />structureTree">
							<li class="structure-field" dataName="<liferay-ui:message key="content" />" dataType="text_area">
								<span class="journal-article-close"></span>

								<span class="folder">
									<div class="field-container">
										<div class="journal-article-move-handler"></div>

										<label class="journal-article-field-label" for="">
											<span><liferay-ui:message key="content" /></span>
										</label>

										<div class="journal-article-component-container">
											<liferay-ui:input-editor contentsLanguageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" name="articleContent" toolbarSet="liferay-article" width="100%" />
										</div>

										<aui:input cssClass="journal-article-localized-checkbox" label="localizable" name="localized" type="hidden" value="<%= true %>" />

										<div class="alert alert-error journal-article-required-message">
											<liferay-ui:message key="this-field-is-required" />
										</div>

										<div class="journal-article-buttons">
											<aui:input cssClass="journal-article-variable-name" id="TextAreaFieldvariableName" inlineField="<%= true %>" label="variable-name" name="variableName" size="25" type="text" value="content" />

											<aui:button cssClass="edit-button" value="edit-options" />

											<aui:button cssClass="repeatable-button hide" value="repeat" />
										</div>
									</div>

									<ul class="folder-droppable"></ul>
								</span>
							</li>
						</ul>
					</div>
				</c:when>
				<c:otherwise>

					<%
					Fields ddmFields = null;

					if ((article != null) && Validator.isNotNull(article.getStructureId()) && Validator.isNotNull(content)) {
						ddmFields = JournalConverterUtil.getDDMFields(ddmStructure, content);
					}

					String requestedLanguageId = defaultLanguageId;

					if (Validator.isNotNull(toLanguageId)) {
						requestedLanguageId = toLanguageId;
					}
					%>

					<liferay-ddm:html
						checkRequired="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>"
						classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
						classPK="<%= ddmStructure.getStructureId() %>"
						fields="<%= ddmFields %>"
						repeatable="<%= Validator.isNull(toLanguageId) %>"
						requestedLocale="<%= LocaleUtil.fromLanguageId(requestedLanguageId) %>"
					/>

				</c:otherwise>
			</c:choose>

			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<aui:input label="searchable" name="indexable" />
			</c:if>
		</div>
	</div>

	<c:if test="<%= Validator.isNotNull(toLanguageId) %>">
		<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
	</c:if>
</div>

<aui:script use="aui-base">
	var field = A.one('#<portlet:namespace />articleContent');

	if (field) {
		var form = field.get('form');

		if (form) {
			var handler = Liferay.on(
				'submitForm',
				function(event) {
					if (event.form.compareTo(form)) {
						field.val(window['<portlet:namespace />articleContent'].getHTML());
					}
				}
			);
		}
	}
</aui:script>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	Liferay.provide(
		window,
		'<portlet:namespace />postProcessTranslation',
		function(formDate, cmd, newVersion, newLanguageId, newLanguage, newStatusMessage) {
			var A = AUI();

			document.<portlet:namespace />fm1.<portlet:namespace />formDate.value = formDate;
			document.<portlet:namespace />fm1.<portlet:namespace />version.value = newVersion;

			var taglibWorkflowStatus = A.one('#<portlet:namespace />journalArticleWrapper .taglib-workflow-status');

			var statusNode = taglibWorkflowStatus.one('.workflow-status strong');

			statusNode.html(newStatusMessage);

			var versionNode = taglibWorkflowStatus.one('.workflow-version strong');

			versionNode.html(newVersion);

			var availableTranslationContainer = A.one('#<portlet:namespace />availableTranslationContainer');

			var translationLink = availableTranslationContainer.one('.journal-article-translation-' + newLanguageId);

			if (cmd == '<%= Constants.DELETE_TRANSLATION %>') {
				var availableLocales = A.one('#<portlet:namespace />availableLocales' + newLanguageId);

				if (availableLocales) {
					availableLocales.remove();
				}

				if (translationLink) {
					translationLink.remove();
				}
			}
			else if (!translationLink) {
				var availableTranslationsLinks = A.one('#<portlet:namespace />availableTranslationsLinks');
				var translationsMessage = A.one('#<portlet:namespace />translationsMessage');

				statusNode.replaceClass('workflow-status-approved', 'workflow-status-draft');

				statusNode.html('<%= UnicodeLanguageUtil.get(pageContext, "draft") %>');

				availableTranslationContainer.addClass('contains-translations');

				availableTranslationsLinks.show();
				translationsMessage.show();

				var TPL_TRANSLATION = '<a class="journal-article-translation-{newLanguageId} lfr-token" href="javascript:;"><img alt="" src="<%= themeDisplay.getPathThemeImages() %>/language/{newLanguageId}.png" />{newLanguage}</a>';

				var translationLinkTpl = A.Lang.sub(
					TPL_TRANSLATION,
					{
						newLanguageId: newLanguageId,
						newLanguage: newLanguage
					}
				);

				translationLink = A.Node.create(translationLinkTpl);

				var editTranslationURL = '<%= editArticleRenderPopUpURL %>&<portlet:namespace />toLanguageId=' + newLanguageId;

				translationLink.on(
					'click',
					function(event) {
						Liferay.Util.openWindow(
							{
								id: '<portlet:namespace />' + newLanguageId,
								title: '<%= UnicodeLanguageUtil.get(pageContext, "web-content-translation") %>',
								uri: editTranslationURL
							}
						);
					}
				);

				availableTranslationsLinks.append(translationLink);

				var languageInput = A.Node.create('<input name="<portlet:namespace />available_locales" type="hidden" value="' + newLanguageId + '" />');

				A.one('#<portlet:namespace />fm1').append(languageInput);
			}
		},
		['aui-base']
	);

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoArticleIdCheckbox','<portlet:namespace />newArticleId', true);
</aui:script>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classPK: <%= (ddmStructure != null) ? ddmStructure.getPrimaryKey() : 0 %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectStructure',
				groupId: <%= groupId %>,
				refererPortletName: '<%= PortletKeys.JOURNAL_CONTENT %>',
				showGlobalScope: true,
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
			},
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates") %>') && (document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value != event.ddmstructureid)) {
					document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value = event.ddmstructureid;
					document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = event.ddmstructurekey;
					document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = "";
					document.<portlet:namespace />fm1.<portlet:namespace />changeStructure.value = true;

					submitForm(document.<portlet:namespace />fm1, null, false, false);
				}
			}
		);
	}

	function <portlet:namespace />openDDMTemplateSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
				classPK: <%= (ddmStructure != null) ? ddmStructure.getPrimaryKey() : 0 %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectTemplate',
				groupId: <%= groupId %>,
				refererPortletName: '<%= PortletKeys.JOURNAL_CONTENT %>',
				showGlobalScope: true,
				struts_action: '/dynamic_data_mapping/select_template',
				templateId: <%= (ddmTemplate != null) ? ddmTemplate.getTemplateId() : 0 %>,
				title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>'
			},
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-template-will-delete-all-unsaved-content") %>')) {
					document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value = event.ddmtemplateid;

					submitForm(document.<portlet:namespace />fm1, null, false, false);
				}
			}
		);
	}
</aui:script>

<aui:script use="aui-base,aui-dialog-iframe-deprecated,liferay-portlet-journal">
	var changeLink = A.one('#<portlet:namespace />changeLanguageId');
	var languageSelector = A.one('#<portlet:namespace />defaultLocale');
	var textLanguageId = A.one('#<portlet:namespace />textLanguageId');

	if (changeLink) {
		changeLink.on(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "changing-the-default-language-will-delete-all-unsaved-content") %>')) {
					languageSelector.show();
					languageSelector.focus();

					changeLink.hide();
					textLanguageId.hide();
				}
			}
		);
	}

	Liferay.Portlet.Journal.PROXY = {};
	Liferay.Portlet.Journal.PROXY.instanceIdKey = '<%= instanceIdKey %>';
	Liferay.Portlet.Journal.PROXY.pathThemeCss = '<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>';
	Liferay.Portlet.Journal.PROXY.portletNamespace = '<portlet:namespace />';

	window.<portlet:namespace />journalPortlet = new Liferay.Portlet.Journal(Liferay.Portlet.Journal.PROXY.portletNamespace, '<%= (article != null) ? HtmlUtil.escape(articleId) : StringPool.BLANK %>');

	var defaultLocaleSelector = A.one('#<portlet:namespace/>defaultLocale');

	if (defaultLocaleSelector) {
		defaultLocaleSelector.on(
			'change',
			function(event) {
				var defaultLanguageId = defaultLocaleSelector.get('value');

				var url = '<%= updateDefaultLanguageURL %>' + '&<portlet:namespace />defaultLanguageId=' + defaultLanguageId;

				window.location.href = url;
			}
		);
	}

	var editDDMTemplate = A.one('#<portlet:namespace />editDDMTemplate');

	if (editDDMTemplate) {
		var windowId = A.guid();

		editDDMTemplate.on(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "editing-the-current-template-will-delete-all-unsaved-content") %>')) {
					Liferay.Util.openWindow(
						{
							id: windowId,
							title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>',

							<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
								<portlet:param name="closeRedirect" value="<%= currentURL %>" />
								<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
								<portlet:param name="refererPortletName" value="<%= PortletKeys.JOURNAL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
								<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
								<portlet:param name="templateId" value="<%= (ddmTemplate != null) ? String.valueOf(ddmTemplate.getTemplateId()) : StringPool.BLANK %>" />
							</liferay-portlet:renderURL>

							uri: '<%= editTemplateURL %>'
						}
					);
				}
			}
		);
	}

	<c:if test="<%= (ddmStructure != null) && DDMStructurePermission.contains(permissionChecker, ddmStructure, PortletKeys.JOURNAL, ActionKeys.UPDATE) %>">
		var editDDMStructure = A.one('#<portlet:namespace />editDDMStructure');

		if (editDDMStructure) {
			var windowId = A.guid();

			editDDMStructure.on(
				'click',
				function(event) {
					if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "editing-the-current-structure-will-delete-all-unsaved-content") %>')) {
						Liferay.Util.openWindow(
							{
								id: windowId,
								refreshWindow: window,
								title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>',

								<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editStructureURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
									<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
									<portlet:param name="closeRedirect" value="<%= currentURL %>" />
									<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
									<portlet:param name="refererPortletName" value="<%= PortletKeys.JOURNAL %>" />
									<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
									<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
									<portlet:param name="classPK" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
								</liferay-portlet:renderURL>

								uri: '<%= editStructureURL %>'
							}
						);
					}
				}
			);
		}
	</c:if>
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content.jsp";

private String _getTemplateImage(ThemeDisplay themeDisplay, DDMTemplate ddmTemplate) {
	String imageURL = null;

	if (ddmTemplate.isSmallImage()) {
		if (Validator.isNotNull(ddmTemplate.getSmallImageURL())) {
			imageURL = ddmTemplate.getSmallImageURL();
		}
		else {
			imageURL = themeDisplay.getPathImage() + "/journal/template?img_id=" + ddmTemplate.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(ddmTemplate.getSmallImageId());
		}
	}

	return imageURL;
}
%>