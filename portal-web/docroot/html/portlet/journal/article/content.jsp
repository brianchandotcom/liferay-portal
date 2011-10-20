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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = (String)request.getAttribute("edit_article.jsp-redirect");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

long classNameId = ParamUtil.getLong(request, "classNameId");
String classPK = ParamUtil.getString(request, "classPK");

String articleId = BeanParamUtil.getString(article, request, "articleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");
String instanceIdKey = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

String structureId = BeanParamUtil.getString(article, request, "structureId");

String parentStructureId = StringPool.BLANK;
long structureGroupId = groupId;
String structureName = LanguageUtil.get(pageContext, "default");
String structureDescription = StringPool.BLANK;
String structureXSD = StringPool.BLANK;

JournalStructure structure = (JournalStructure)request.getAttribute("edit_article.jsp-structure");

if (structure != null) {
	structureGroupId = structure.getGroupId();
	parentStructureId = structure.getParentStructureId();
	structureName = structure.getName(locale);
	structureDescription = structure.getDescription(locale);
	structureXSD = structure.getMergedXsd();
}

List<JournalTemplate> templates = new ArrayList();

if (structure != null) {
	templates = JournalTemplateServiceUtil.getStructureTemplates(structureGroupId, structureId);
}

String templateId = BeanParamUtil.getString(article, request, "templateId");

if ((structure == null) && Validator.isNotNull(templateId)) {
	JournalTemplate template = null;

	try {
		template = JournalTemplateLocalServiceUtil.getTemplate(groupId, templateId);
	}
	catch (NoSuchTemplateException nste1) {
		if (groupId != themeDisplay.getCompanyGroupId()) {
			try {
				template = JournalTemplateLocalServiceUtil.getTemplate(themeDisplay.getCompanyGroupId(), templateId);

				structureGroupId = themeDisplay.getCompanyGroupId();
			}
			catch (NoSuchTemplateException nste2) {
			}
		}
	}

	if (template != null) {
		structureId = template.getStructureId();

		structure = JournalStructureLocalServiceUtil.getStructure(structureGroupId, structureId);

		structureName = structure.getName(locale);

		templates = JournalTemplateLocalServiceUtil.getStructureTemplates(structureGroupId, structureId);
	}
}

String languageId = (String)request.getAttribute("edit_article.jsp-languageId");
String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");
String toLanguageId = (String)request.getAttribute("edit_article.jsp-toLanguageId");

String content = null;

boolean preselectCurrentLayout = false;

if (article != null) {
	content = ParamUtil.getString(request, "content");

	if (Validator.isNull(content)) {
		content = article.getContent();
	}

	if (Validator.isNotNull(toLanguageId)) {
		content = JournalArticleImpl.getContentByLocale(content, Validator.isNotNull(structureId), toLanguageId);
	}
	else {
		content = JournalArticleImpl.getContentByLocale(content, Validator.isNotNull(structureId), defaultLanguageId);
	}
}
else {
	content = ParamUtil.getString(request, "content");

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

		if (structure == null) {
			content = contentDoc.getRootElement().element("static-content").getText();
		}
	}
	catch (Exception e) {
		contentDoc = null;
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="content" />

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<portlet:renderURL var="editArticleRenderPopUpURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</portlet:renderURL>

<portlet:renderURL var="updateDefaultLanguageURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
	<portlet:param name="classPK" value="<%= classPK %>" />
	<portlet:param name="structureId" value="<%= structureId %>" />
	<portlet:param name="templateId" value="<%= templateId %>" />
</portlet:renderURL>

<table class="lfr-table" id="<portlet:namespace />journalArticleWrapper" width="100%">
<tr>
	<td class="lfr-top">
		<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />
		<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
		<liferay-ui:error exception="<%= ArticleTitleException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= ArticleVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing-please-copy-your-changes-and-try-again" />
		<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />

		<table class="lfr-table journal-article-header-edit" id="<portlet:namespace />articleHeaderEdit">
		<tr>
			<td>
				<c:if test="<%= (article == null) || article.isNew() %>">
					<c:choose>
						<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID %>">
							<aui:input name="newArticleId" type="hidden" />
							<aui:input name="autoArticleId" type="hidden" value="<%= true %>" />
						</c:when>
						<c:otherwise>
							<aui:input cssClass="lfr-input-text-container" field="articleId" fieldParam="newArticleId" label="id" name="newArticleId" value="<%= newArticleId %>" />

							<aui:input label="autogenerate-id" name="autoArticleId" type="checkbox" />
						</c:otherwise>
					</c:choose>
				</c:if>
			</td>
		</tr>

		<c:if test="<%= Validator.isNull(toLanguageId) %>">
			<tr>
				<td class="article-structure-template-toolbar journal-metadata">
					<span id="<portlet:namespace />structureMessage" class="portlet-msg-alert structure-message aui-helper-hidden">
						<liferay-ui:message key="this-structure-has-not-been-saved" />

						<liferay-ui:message key="click-here-to-save-it-now" arguments='<%= new Object[] {"journal-save-structure-trigger", "#"} %>' />
					</span>

					<aui:layout>
						<aui:column columnWidth="50" cssClass="article-structure">
							<label class="article-structure-label"><liferay-ui:message key="structure" />:</label>

							<aui:fieldset cssClass="article-structure-toolbar">
								<div class="journal-form-presentation-label">
									<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
									<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
									<aui:input name="structureName" type="hidden" value="<%= structureName %>" />
									<aui:input name="structureDescription" type="hidden" value="<%= structureDescription %>" />
									<aui:input name="structureXSD" type="hidden" value="<%= JS.encodeURIComponent(structureXSD) %>" />

									<span id="<portlet:namespace />structureNameLabel" class="structure-name-label">
										<%= HtmlUtil.escape(structureName) %>
									</span>

									<c:if test="<%= classNameId == 0 %>">
										<liferay-ui:icon id="editStructureLink" image="edit" url="javascript:;" />

										<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="changeStructureURL">
											<portlet:param name="struts_action" value="/journal/select_structure" />
											<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
										</portlet:renderURL>

										<span class="structure-links">
											<liferay-ui:icon id="changeStructureButton" image="configuration" message="change" url="<%= changeStructureURL %>" />
										</span>

										<c:if test="<%= Validator.isNotNull(structureId) %>">
											<span class="default-link">(<a href="javascript:;" id="<portlet:namespace />loadDefaultStructure"><liferay-ui:message key="use-default" /></a>)</span>
										</c:if>

										<span class="structure-controls">
											<span class="structure-buttons">
												<aui:button cssClass="save-structure-button aui-helper-hidden" name="saveStructureButton" value="save" />

												<aui:button cssClass="edit-structure-button aui-helper-hidden" name="editStructureButton" value="stop-editing" />
											</span>
										</span>
									</c:if>
								</div>
							</aui:fieldset>
						</aui:column>

						<aui:column cssClass="article-template" columnWidth="50">
							<label class="article-template-label"><liferay-ui:message key="template" />:</label>

							<aui:fieldset cssClass="article-template-toolbar">
								<div class="journal-form-presentation-label">
									<c:choose>
										<c:when test="<%= templates.isEmpty() %>">
											<aui:input name="templateId" type="hidden" value="<%= templateId %>" />

											<div id="selectTemplateMessage"></div>

											<span class="template-name-label">
												<liferay-ui:message key="none" />
											</span>

											<liferay-ui:icon id="selectTemplateLink" image="configuration" message="choose" url="javascript:;" />
										</c:when>
										<c:when test="<%= templates.size() == 1 %>">

											<%
											JournalTemplate template = templates.get(0);
											%>

											<aui:input name="templateId" type="hidden" value="<%= template.getTemplateId() %>" />

											<span class="template-name-label">
												<%= HtmlUtil.escape(template.getName(locale)) %>
											</span>

											<c:if test="<%= template.isSmallImage() %>">
												<img class="article-template-image" id="<portlet:namespace />templateImage" src="<%= _getTemplateImage(themeDisplay, template) %>" />
											</c:if>

											<portlet:renderURL var="templateURL">
												<portlet:param name="struts_action" value="/journal/edit_template" />
												<portlet:param name="redirect" value="<%= currentURL %>" />
												<portlet:param name="groupId" value="<%= String.valueOf(template.getGroupId()) %>" />
												<portlet:param name="templateId" value="<%= template.getTemplateId() %>" />
											</portlet:renderURL>

											<liferay-ui:icon url="<%= templateURL %>" image="edit" />
										</c:when>
										<c:otherwise>
											<aui:select inlineField="<%= true %>" label="" name="templateId">

												<%
												for (JournalTemplate template : templates) {
													String imageURL = _getTemplateImage(themeDisplay, template);
												%>

													<portlet:renderURL var="templateURL">
														<portlet:param name="struts_action" value="/journal/edit_template" />
														<portlet:param name="redirect" value="<%= currentURL %>" />
														<portlet:param name="groupId" value="<%= String.valueOf(template.getGroupId()) %>" />
														<portlet:param name="templateId" value="<%= template.getTemplateId() %>" />
													</portlet:renderURL>

													<aui:option
														data-img="<%= imageURL != null ? imageURL : StringPool.BLANK %>"
														data-url="<%= templateURL %>"
														label="<%= HtmlUtil.escape(template.getName(locale)) %>"
														selected="<%= templateId.equals(template.getTemplateId()) %>"
														value="<%= template.getTemplateId() %>"
													/>

												<%
												}
												%>

											</aui:select>

											<img border="0" class="aui-helper-hidden article-template-image" hspace="0" id="<portlet:namespace />templateImage" src="" vspace="0" />

											<liferay-ui:icon id="editTemplateLink" url="javascript:;" image="edit" />
										</c:otherwise>
									</c:choose>
								</div>
							</aui:fieldset>
						</aui:column>
					</aui:layout>
				</td>
			</tr>
		</c:if>

		<tr>
			<td class="article-translation-toolbar journal-metadata">
				<div class="portlet-msg-info aui-helper-hidden" id="<portlet:namespace />translationsMessage">
					<liferay-ui:message key="the-changes-in-your-translations-will-be-available-once-the-content-is-published" />
				</div>

				<div>
					<c:choose>
						<c:when test="<%= Validator.isNull(toLanguageId) %>">
							<label for="<portlet:namespace />defaultLanguageId"><liferay-ui:message key="web-content-default-language" /></label>:

							<span class="lfr-translation-manager-selector nobr">
								<span class="article-default-language journal-article-default-translation" id="<portlet:namespace />textLanguageId">
									<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/language/" + defaultLanguageId + ".png" %>' />

									<%= LocaleUtil.fromLanguageId(defaultLanguageId).getDisplayName(locale) %>
								</span>

								<liferay-ui:icon-help message="default-language-help" />

								<a href="javascript:;" id="<portlet:namespace />changeLanguageId"><liferay-ui:message key="change" /></a>

								<aui:select inputCssClass="aui-helper-hidden" id="defaultLocale" inlineField="<%= true %>" label="" name="defaultLanguageId">

									<%
									Locale[] locales = LanguageUtil.getAvailableLocales();

									for (int i = 0; i < locales.length; i++) {
									%>

										<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= defaultLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

									<%
									}
									%>

								</aui:select>
							</span>

							<c:if test="<%= Validator.isNotNull(articleId) %>">
								<span class="lfr-translation-manager-add-menu">
									<liferay-ui:icon-menu
										align="left"
										cssClass="add-translations-menu"
										direction="down"
										icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>'
										message='<%= LanguageUtil.get(pageContext, "add-translation") %>'
										showArrow="<%= true %>"
										showWhenSingleIcon="<%= true %>"
									>

										<%
										Locale[] locales = LanguageUtil.getAvailableLocales();

										for (int i = 0; i < locales.length; i++) {
											if (ArrayUtil.contains(article.getAvailableLocales(), LocaleUtil.toLanguageId(locales[i]))) {
												continue;
											}

											String taglibEditArticleURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", LocaleUtil.toLanguageId(locales[i]));
											String taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + LocaleUtil.toLanguageId(locales[i]) + "', title: '" + LanguageUtil.get(pageContext, "web-content-translation") + "', uri: '" + taglibEditArticleURL + "'});";
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

				<c:if test="<%= Validator.isNotNull(articleId) %>">

					<%
					String[] translations = article.getAvailableLocales();
					%>

					<div class='<%= (Validator.isNull(toLanguageId) && (translations.length > 1)) ? "contains-translations" :"" %>' id="<portlet:namespace />availableTranslationContainer">
						<c:choose>
							<c:when test="<%= Validator.isNotNull(toLanguageId) %>">
								<liferay-util:buffer var="languageLabel">
									<%= LocaleUtil.fromLanguageId(toLanguageId).getDisplayName(locale) %>

									<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/language/" + toLanguageId + ".png" %>' />
								</liferay-util:buffer>

								<%= LanguageUtil.format(pageContext, "translating-web-content-to-x", languageLabel) %>

								<aui:input name="toLanguageId" type="hidden" value="<%= toLanguageId %>" />
							</c:when>
							<c:otherwise>
								<span class='available-translations<%= (translations.length > 1) ? "" : " aui-helper-hidden" %>' id="<portlet:namespace />availableTranslationsLinks">
									<label><liferay-ui:message key="available-translations" /></label>

										<%
										for (int i = 0; i < translations.length; i++) {
											if (translations[i].equals(defaultLanguageId)){
												continue;
											}

											String editTranslationURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", translations[i]);
										%>

										<a class="journal-article-translation journal-article-translation-<%= translations[i] %>" href="javascript:;" onClick="Liferay.Util.openWindow({id: '<portlet:namespace /><%= translations[i] %>', title: '<%= LanguageUtil.get(pageContext, "web-content-translation") %>', uri: '<%= editTranslationURL %>'});">
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
			</td>
		</tr>
		</table>

		<div class="journal-article-general-fields">
			<aui:input languageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" name="title" />
		</div>

		<div class="journal-article-container" id="<portlet:namespace />journalArticleContainer">
			<c:choose>
				<c:when test="<%= structure == null %>">
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
											<liferay-ui:input-editor name='<%= renderResponse.getNamespace() + "structure_el_TextAreaField_content" %>' editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" toolbarSet="liferay-article" width="100%" />
										</div>

										<aui:input cssClass="journal-article-localized-checkbox" label="localizable" name="localized" type="hidden" value="<%= true %>" />

										<div class="journal-article-required-message portlet-msg-error">
											<liferay-ui:message key="this-field-is-required" />
										</div>

										<div class="journal-article-buttons">
											<aui:input cssClass="journal-article-variable-name" id="TextAreaFieldvariableName" inlineField="<%= true %>" label="variable-name" name="variableName" size="25" type="text" value="content" />

											<aui:button cssClass="edit-button" value="edit-options" />

											<aui:button cssClass="repeatable-button aui-helper-hidden" value="repeat" />
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
					Document xsdDoc = SAXReaderUtil.read(structure.getMergedXsd());

					if (contentDoc != null) {
						if ((availableLocales != null) && (availableLocales.length > 0)) {
							for (int i = 0; i < availableLocales.length ; i++) {
					%>

								<input name="<portlet:namespace />available_locales" type="hidden" value="<%= HtmlUtil.escapeAttribute(availableLocales[i]) %>" />

					<%
							}
						}

						if (Validator.isNotNull(toLanguageId)) {
					%>

							<input name="<portlet:namespace />available_locales" type="hidden" value="<%= languageId %>" />

					<%
						}
					}
					else {
						contentDoc = SAXReaderUtil.createDocument(SAXReaderUtil.createElement("root"));
					%>

						<input name="<portlet:namespace />available_locales" type="hidden" value="<%= HtmlUtil.escapeAttribute(defaultLanguageId) %>" />

					<%
					}
					%>

					<div class="structure-tree-wrapper" id="<portlet:namespace />structureTreeWrapper">
						<ul class="structure-tree" id="<portlet:namespace />structureTree">
							<% _format(groupId, contentDoc.getRootElement(), xsdDoc.getRootElement(), new IntegerWrapper(0), new Integer(-1), true, pageContext, request); %>
						</ul>
					</div>
				</c:otherwise>
			</c:choose>

			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<aui:input inlineLabel="left" label="searchable" name="indexable" />
			</c:if>
		</div>
	</td>

	<c:choose>
		<c:when test="<%= Validator.isNull(toLanguageId) %>">
			<td class="lfr-top">
				<%@ include file="/html/portlet/journal/edit_article_extra.jspf" %>
			</td>
		</c:when>
		<c:otherwise>
			<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
		</c:otherwise>
	</c:choose>
</tr>
</table>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	Liferay.provide(
		window,
		'<portlet:namespace />postProcessTranslation',
		function(cmd, newVersion, newLanguageId, newLanguage) {
			var A = AUI();

			var availableTranslationContainer = A.one('#<portlet:namespace />availableTranslationContainer');
			var availableTranslationsLinks = A.one('#<portlet:namespace />availableTranslationsLinks');

			var chooseLanguageText = A.one('#<portlet:namespace />chooseLanguageText');
			var translationsMessage = A.one('#<portlet:namespace />translationsMessage');

			var taglibWorkflowStatus = A.one('#<portlet:namespace />journalArticleWrapper .taglib-workflow-status');
			var statusNode = taglibWorkflowStatus.one('.workflow-status strong');
			var versionNode = taglibWorkflowStatus.one('.workflow-version strong');

			document.<portlet:namespace />fm1.<portlet:namespace />version.value = newVersion;

			versionNode.html(newVersion);

			statusNode.removeClass('workflow-status-approved');
			statusNode.addClass('workflow-status-draft');
			statusNode.html('<%= LanguageUtil.get(pageContext, "draft") %>');

			availableTranslationContainer.addClass('contains-translations');
			availableTranslationsLinks.show();
			translationsMessage.show();

			var translationLink = availableTranslationContainer.one('.journal-article-translation-' + newLanguageId);

			if (cmd == '<%= Constants.DELETE_TRANSLATION %>') {
				translationLink.hide();
			}
			else if (!translationLink) {
				var TPL_TRANSLATION = '<a class="journal-article-translation journal-article-translation-{newLanguageId}" href="javascript:;"><img alt="" src="<%= themeDisplay.getPathThemeImages() %>/language/{newLanguageId}.png" />{newLanguage}</a>';

				translationLinkTpl = A.Lang.sub(
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
								title: '<%= LanguageUtil.get(pageContext, "web-content-translation") %>',
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

<aui:script use="aui-base,aui-dialog-iframe,liferay-portlet-journal">
	var selectTemplateLink = A.one('#<portlet:namespace />selectTemplateLink');

	if (selectTemplateLink) {
		selectTemplateLink.on(
			'click',
			function() {
				Liferay.Util.openWindow(
					{
						dialog: {
							stack: false,
							width:680
						},
						title: '<liferay-ui:message key="template" />',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_template" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="structureId" value="<%= String.valueOf(structureId) %>" /></portlet:renderURL>'
					}
				);
			}
		);
	}

	var templateIdSelector = A.one('select#<portlet:namespace />templateId');

	if (templateIdSelector) {
		var editTemplateLink = A.one('#<portlet:namespace />editTemplateLink');
		var templateImage = A.one('#<portlet:namespace />templateImage');

		var changeTemplate = function() {
			var selectedOption = templateIdSelector.one(':selected');

			var imageURL = selectedOption.attr('data-img');
			var templateURL = selectedOption.attr('data-url');

			if (imageURL) {
				templateImage.attr('src', imageURL);

				templateImage.show();
			}
			else {
				templateImage.hide();
			}

			editTemplateLink.attr('href', templateURL);
		}

		changeTemplate();

		if (editTemplateLink) {
			templateIdSelector.on('change', changeTemplate);

			editTemplateLink.on(
				'click',
				function(event) {
					var selectedOption = templateIdSelector.one(':selected');

					window.location = selectedOption.attr('data-url');
				}
			);
		}
	}

	<%
	String doAsUserId = themeDisplay.getDoAsUserId();

	if (Validator.isNull(doAsUserId)) {
		doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
	}
	%>

	<portlet:resourceURL var="editorURL">
		<portlet:param name="editorImpl" value="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />
		<portlet:param name="name" value="LIFERAY_NAME" />
		<portlet:param name="skipEditorLoading" value="LIFERAY_SKIP_EDITOR" />
		<portlet:param name="struts_action" value="/journal/edit_article" />
		<portlet:param name="toolbarSet" value="liferay-article" />
	</portlet:resourceURL>

	Liferay.Portlet.Journal.PROXY = {};
	Liferay.Portlet.Journal.PROXY.doAsUserId = '<%= HttpUtil.encodeURL(doAsUserId) %>';
	Liferay.Portlet.Journal.PROXY.editorImpl = '<%= EditorUtil.getEditorValue(request, EDITOR_WYSIWYG_IMPL_KEY) %>';
	Liferay.Portlet.Journal.PROXY.editorURL = '<%= editorURL %>';
	Liferay.Portlet.Journal.PROXY.instanceIdKey = '<%= instanceIdKey %>';
	Liferay.Portlet.Journal.PROXY.pathThemeCss = '<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>';
	Liferay.Portlet.Journal.PROXY.portletNamespace = '<portlet:namespace />';

	new Liferay.Portlet.Journal(Liferay.Portlet.Journal.PROXY.portletNamespace, '<%= HtmlUtil.escape(articleId) %>');

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
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content.jsp";

private String _getTemplateImage(ThemeDisplay themeDisplay, JournalTemplate template) {
	String imageURL = null;

	if (template.isSmallImage()) {
		if (Validator.isNotNull(template.getSmallImageURL())) {
			imageURL = template.getSmallImageURL();
		}
		else {
			imageURL = themeDisplay.getPathImage() + "/journal/template?img_id=" + template.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(template.getSmallImageId());
		}
	}

	return imageURL;
}

private void _format(long groupId, Element contentParentElement, Element xsdParentElement, IntegerWrapper count, Integer depth, boolean repeatablePrototype, PageContext pageContext, HttpServletRequest request) throws Exception {
	depth = new Integer(depth.intValue() + 1);

	String languageId = LanguageUtil.getLanguageId(request);

	String toLanguageId = ParamUtil.getString(request, "toLanguageId");

	List<Element> xsdElements = xsdParentElement.elements();

	for (Element xsdElement : xsdElements) {
		String nodeName = xsdElement.getName();

		if (nodeName.equals("meta-data") || nodeName.equals("entry")) {
			continue;
		}

		String elName = xsdElement.attributeValue("name", StringPool.BLANK);
		String elType = xsdElement.attributeValue("type", StringPool.BLANK);
		String elIndexType = xsdElement.attributeValue("index-type", StringPool.BLANK);
		String repeatable = xsdElement.attributeValue("repeatable");

		boolean elRepeatable = GetterUtil.getBoolean(repeatable);

		if (Validator.isNotNull(toLanguageId)) {
			elRepeatable = false;
		}

		String elParentStructureId = xsdElement.attributeValue("parent-structure-id");

		Map<String, String> elMetaData = _getMetaData(xsdElement, elName);

		List<Element> elSiblings = null;

		List<Element> contentElements = contentParentElement.elements();

		for (Element contentElement : contentElements) {
			if (elName.equals(contentElement.attributeValue("name", StringPool.BLANK))) {
				elSiblings = _getSiblings(contentParentElement, elName);

				break;
			}
		}

		if (elSiblings == null) {
			elSiblings = new ArrayList<Element>();

			Element contentElement = SAXReaderUtil.createElement("dynamic-element");

			contentElement.addAttribute("instance-id", PwdGenerator.getPassword());
			contentElement.addAttribute("name", elName);
			contentElement.addAttribute("type", elType);
			contentElement.addAttribute("index-type", elIndexType);

			contentElement.add(SAXReaderUtil.createElement("dynamic-content"));

			elSiblings.add(contentElement);
		}

		for (int siblingIndex = 0; siblingIndex < elSiblings.size(); siblingIndex++) {
			Element contentElement = elSiblings.get(siblingIndex);

			String elInstanceId = contentElement.attributeValue("instance-id");

			String elContent = GetterUtil.getString(contentElement.elementText("dynamic-content"));

			if (!elType.equals("document_library") && !elType.equals("image_gallery") && !elType.equals("text") && !elType.equals("text_area") && !elType.equals("text_box")) {
				elContent = HtmlUtil.toInputSafe(elContent);
			}

			if (elType.equals("list") || elType.equals("multi-list") || elType.equals("text") || elType.equals("text_box")) {
				elContent = HtmlUtil.unescapeCDATA(elContent);
			}

			String elLanguageId = StringPool.BLANK;

			Element dynamicContentEl = contentElement.element("dynamic-content");

			if (dynamicContentEl != null) {
				elLanguageId = dynamicContentEl.attributeValue("language-id", StringPool.BLANK);

				if (Validator.isNotNull(toLanguageId) && Validator.isNull(elLanguageId)) {
					continue;
				}
			}
			else {
				elLanguageId = languageId;
			}

			if (!_hasRepeatedParent(contentElement)) {
				repeatablePrototype = (siblingIndex == 0);
			}

			request.setAttribute(WebKeys.JOURNAL_ARTICLE_GROUP_ID, String.valueOf(groupId));

			request.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT_EL, contentElement);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL, xsdElement);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_CONTENT, elContent);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT, count);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH, depth);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INSTANCE_ID, elInstanceId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_LANGUAGE_ID, elLanguageId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_META_DATA, elMetaData);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_NAME, elName);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_PARENT_ID, elParentStructureId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE, String.valueOf(elRepeatable));
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE_PROTOTYPE, String.valueOf(repeatablePrototype));
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_TYPE, elType);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INDEX_TYPE, elIndexType);

			pageContext.include("/html/portlet/journal/edit_article_content_xsd_el.jsp");

			count.increment();

			if (!elType.equals("list") && !elType.equals("multi-list") && !contentElement.elements().isEmpty()) {
				pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_top.jsp");

				_format(groupId, contentElement, xsdElement, count, depth, repeatablePrototype, pageContext, request);

				request.setAttribute(WebKeys.JOURNAL_STRUCTURE_CLOSE_DROPPABLE_TAG, Boolean.TRUE.toString());

				pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_bottom.jsp");
			}

			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_CLOSE_DROPPABLE_TAG, Boolean.FALSE.toString());

			pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_bottom.jsp");
		}
	}
}

private Map<String, String> _getMetaData(Element xsdElement, String elName) {
	Map<String, String> elMetaData = new HashMap<String, String>();

	Element metaData = xsdElement.element("meta-data");

	if (Validator.isNotNull(metaData)) {
		List<Element> elMetaDataements = metaData.elements();

		for (Element elMetaDataement : elMetaDataements) {
			String name = elMetaDataement.attributeValue("name");
			String content = elMetaDataement.getText().trim();

			elMetaData.put(name, content);
		}
	}
	else {
		elMetaData.put("label", elName);
	}

	return elMetaData;
}

private List<Element> _getSiblings(Element element, String name) {
	List<Element> elements = new ArrayList<Element>();

	Iterator<Element> itr = element.elements().iterator();

	while (itr.hasNext()) {
		Element curElement = itr.next();

		if (name.equals(curElement.attributeValue("name", StringPool.BLANK))) {
			elements.add(curElement);
		}
	}

	return elements;
}

private boolean _hasRepeatedParent(Element element) {
	Element parentElement = element.getParent();

	while (parentElement != null) {
		Element parentParentElement = parentElement.getParent();

		if (parentParentElement != null) {
			List<Element> parentSiblings = _getSiblings(parentParentElement, parentElement.attributeValue("name", StringPool.BLANK));

			if (parentSiblings.indexOf(parentElement) > 0) {
				return true;
			}
		}

		parentElement = parentParentElement;
	}

	return false;
}
%>