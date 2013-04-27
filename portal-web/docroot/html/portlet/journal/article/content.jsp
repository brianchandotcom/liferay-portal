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

long classNameId = ParamUtil.getLong(request, "classNameId");
String classPK = ParamUtil.getString(request, "classPK");

String articleId = BeanParamUtil.getString(article, request, "articleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");
String instanceIdKey = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

String structureId = BeanParamUtil.getString(article, request, "structureId");

long ddmStructureGroupId = groupId;
String ddmStructureName = LanguageUtil.get(pageContext, "default");
String ddmStructureDescription = StringPool.BLANK;
String ddmStructureXSD = StringPool.BLANK;

DDMStructure ddmStructure = (DDMStructure)request.getAttribute("edit_article.jsp-structure");

if (ddmStructure != null) {
	structureId = ddmStructure.getStructureKey();

	ddmStructureGroupId = ddmStructure.getGroupId();
	ddmStructureName = ddmStructure.getName(locale);
	ddmStructureDescription = ddmStructure.getDescription(locale);
	ddmStructureXSD = ddmStructure.getXsd();
}

List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

if (ddmStructure != null) {
	ddmTemplates.addAll(DDMTemplateServiceUtil.getTemplates(ddmStructureGroupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));

	if (groupId != ddmStructureGroupId) {
		ddmTemplates.addAll(DDMTemplateServiceUtil.getTemplates(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));
	}
}

String templateId = BeanParamUtil.getString(article, request, "templateId");

DDMTemplate ddmTemplate = (DDMTemplate)request.getAttribute("edit_article.jsp-template");

if (ddmTemplate != null) {
	templateId = ddmTemplate.getTemplateKey();
}

if ((ddmStructure == null) && (ddmTemplate == null) && Validator.isNotNull(templateId)) {
	try {
		ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(groupId, PortalUtil.getClassNameId(DDMStructure.class), templateId, true);
	}
	catch (NoSuchTemplateException nste) {
	}

	if (ddmTemplate != null) {
		ddmStructure = DDMStructureLocalServiceUtil.getStructure(ddmTemplate.getClassPK());

		ddmStructureName = ddmStructure.getName(locale);

		ddmTemplates = DDMTemplateLocalServiceUtil.getTemplates(ddmStructureGroupId, PortalUtil.getClassNameId(DDMStructure.class), ddmTemplate.getClassPK());
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
</portlet:renderURL>

<portlet:renderURL var="updateDefaultLanguageURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="portletResource" value="<%= portletResource %>" />
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

		<liferay-ui:error exception="<%= LocaleException.class %>">

			<%
			LocaleException le = (LocaleException)errorException;
			%>

			<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
		</liferay-ui:error>

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
					<span class="portlet-msg-alert structure-message aui-helper-hidden" id="<portlet:namespace />structureMessage">
						<liferay-ui:message key="this-structure-has-not-been-saved" />

						<liferay-ui:message arguments='<%= new Object[] {"journal-save-structure-trigger", "#"} %>' key="click-here-to-save-it-now" />
					</span>

					<aui:layout>
						<aui:column columnWidth="50" cssClass="article-structure">
							<label class="article-structure-label"><liferay-ui:message key="structure" />:</label>

							<aui:fieldset cssClass="article-structure-toolbar">
								<div class="journal-form-presentation-label">
									<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
									<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
									<aui:input name="structureName" type="hidden" value="<%= ddmStructureName %>" />
									<aui:input name="structureDescription" type="hidden" value="<%= ddmStructureDescription %>" />
									<aui:input name="structureXSD" type="hidden" value="<%= ddmStructureXSD %>" />

									<span class="structure-name-label" id="<portlet:namespace />structureNameLabel">
										<%= HtmlUtil.escape(ddmStructureName) %>
									</span>

									<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
										<liferay-ui:icon
											image="add"
											label="<%= true %>"
											message="select"
											url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
										/>

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

						<aui:column columnWidth="50" cssClass="article-template">
							<label class="article-template-label"><liferay-ui:message key="template" />:</label>

							<aui:fieldset cssClass="article-template-toolbar">
								<div class="journal-form-presentation-label">
									<c:choose>
										<c:when test="<%= ddmTemplates.isEmpty() %>">
											<aui:input name="templateId" type="hidden" value="<%= templateId %>" />

											<div id="selectTemplateMessage"></div>

											<span class="template-name-label">
												<liferay-ui:message key="none" />
											</span>

											<c:if test="<%= ddmStructure != null %>">

												<%
												StringBundler sb = new StringBundler(5);

												sb.append("javascript:");
												sb.append(renderResponse.getNamespace());
												sb.append("openDDMTemplateSelector('");
												sb.append(ddmStructure.getStructureId());
												sb.append("');");
												%>

												<liferay-ui:icon
													image="add"
													label="<%= true %>"
													message="select"
													url="<%= sb.toString() %>"
												/>
											</c:if>
										</c:when>
										<c:when test="<%= ddmTemplates.size() == 1 %>">

											<%
											DDMTemplate curDDMTemplate = ddmTemplates.get(0);

											templateId = curDDMTemplate.getTemplateKey();
											%>

											<aui:input name="templateId" type="hidden" value="<%= templateId %>" />

											<span class="template-name-label">
												<%= HtmlUtil.escape(curDDMTemplate.getName(locale)) %>
											</span>

											<c:if test="<%= DDMTemplatePermission.contains(permissionChecker, curDDMTemplate, ActionKeys.UPDATE) %>">
												<c:if test="<%= curDDMTemplate.isSmallImage() %>">
													<img class="article-template-image" id="<portlet:namespace />templateImage" src="<%= _getTemplateImage(themeDisplay, curDDMTemplate) %>" />
												</c:if>

												<liferay-ui:icon id="editDDMTemplate" image="edit" url="javascript:;" />
											</c:if>
										</c:when>
										<c:otherwise>
											<aui:select inlineField="<%= true %>" label="" name="templateId">

												<%
												for (DDMTemplate curDDMTemplate : ddmTemplates) {
													String imageURL = _getTemplateImage(themeDisplay, curDDMTemplate);
												%>

													<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
														<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
														<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
														<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
														<portlet:param name="templateId" value="<%= String.valueOf(curDDMTemplate.getTemplateId()) %>" />
													</liferay-portlet:renderURL>

													<aui:option
														data-img="<%= imageURL != null ? imageURL : StringPool.BLANK %>"
														data-url="<%= editTemplateURL %>"
														label="<%= HtmlUtil.escape(curDDMTemplate.getName(locale)) %>"
														selected="<%= templateId.equals(curDDMTemplate.getTemplateKey()) %>"
														value="<%= curDDMTemplate.getTemplateKey() %>"
													/>

												<%
												}
												%>

											</aui:select>

											<img border="0" class="aui-helper-hidden article-template-image" hspace="0" id="<portlet:namespace />templateImage" src="" vspace="0" />

											<liferay-ui:icon id="editTemplateLink" image="edit" url="javascript:;" />
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
								<span class="article-default-language lfr-token lfr-token-primary" id="<portlet:namespace />textLanguageId">
									<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/language/" + defaultLanguageId + ".png" %>' />

									<%= LocaleUtil.fromLanguageId(defaultLanguageId).getDisplayName(locale) %>
								</span>

								<liferay-ui:icon-help message="default-language-help" />

								<a href="javascript:;" id="<portlet:namespace />changeLanguageId"><liferay-ui:message key="change" /></a>

								<aui:select id="defaultLocale" inlineField="<%= true %>" inputCssClass="aui-helper-hidden" label="" name="defaultLanguageId">

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

							<c:if test="<%= article != null %>">
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
											String taglibEditURL = "javascript:Liferay.Util.openWindow({cache: false, id: '" + renderResponse.getNamespace() + LocaleUtil.toLanguageId(locales[i]) + "', title: '" + UnicodeLanguageUtil.get(pageContext, "web-content-translation") + "', uri: '" + taglibEditArticleURL + "'});";
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
											if (translations[i].equals(defaultLanguageId)) {
												continue;
											}

											String editTranslationURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", translations[i]);
										%>

										<a class="lfr-token journal-article-translation-<%= translations[i] %>" href="javascript:;" onClick="Liferay.Util.openWindow({cache: false, id: '<portlet:namespace /><%= translations[i] %>', title: '<%= UnicodeLanguageUtil.get(pageContext, "web-content-translation") %>', uri: '<%= editTranslationURL %>'});">
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
			<aui:input defaultLanguageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" languageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" name="title">
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
											<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" name='<%= renderResponse.getNamespace() + "structure_el_TextAreaField_content" %>' toolbarSet="liferay-article" width="100%" />
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
		function(formDate, cmd, newVersion, newLanguageId, newLanguage, newStatusMessage) {
			var A = AUI();

			document.<portlet:namespace />fm1.<portlet:namespace />formDate.value = formDate;

			var availableTranslationContainer = A.one('#<portlet:namespace />availableTranslationContainer');
			var availableTranslationsLinks = A.one('#<portlet:namespace />availableTranslationsLinks');

			var chooseLanguageText = A.one('#<portlet:namespace />chooseLanguageText');
			var translationsMessage = A.one('#<portlet:namespace />translationsMessage');

			var taglibWorkflowStatus = A.one('#<portlet:namespace />journalArticleWrapper .taglib-workflow-status');
			var statusNode = taglibWorkflowStatus.one('.workflow-status strong');

			statusNode.html(newStatusMessage);

			var versionNode = taglibWorkflowStatus.one('.workflow-version strong');

			document.<portlet:namespace />fm1.<portlet:namespace />version.value = newVersion;

			versionNode.html(newVersion);

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
				statusNode.removeClass('workflow-status-approved');
				statusNode.addClass('workflow-status-draft');
				statusNode.html('<%= UnicodeLanguageUtil.get(pageContext, "draft") %>');

				availableTranslationContainer.addClass('contains-translations');
				availableTranslationsLinks.show();
				translationsMessage.show();

				var TPL_TRANSLATION = '<a class="lfr-token journal-article-translation-{newLanguageId}" href="javascript:;"><img alt="" src="<%= themeDisplay.getPathThemeImages() %>/language/{newLanguageId}.png" />{newLanguage}</a>';

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
				availableFields: 'Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE',
				classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
				classPK: <%= (ddmStructure != null) ? ddmStructure.getPrimaryKey() : 0 %>,
				ddmResource: '<%= ddmResource %>',
				dialog: {
					modal: true,
					width: '80%'
				},
				eventName: '<portlet:namespace />selectStructure',
				groupId: <%= groupId %>,
				storageType: '<%= PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE %>',
				structureName: 'structure',
				structureType: 'com.liferay.portlet.journal.model.JournalArticle',
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
			},
			function(event){
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates") %>') && (document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value != event.ddmstructureid)) {
					document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value = event.ddmstructureid;
					document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = "";

					submitForm(document.<portlet:namespace />fm1, null, false, false);
				}
			}
		);
	}

	function <portlet:namespace />openDDMTemplateSelector(ddmStructureId) {
		Liferay.Util.openDDMPortlet(
			{
				availableFields: 'Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE',
				classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
				classPK: ddmStructureId,
				ddmResource: '<%= ddmResource %>',
				ddmResourceActionId: '<%= ActionKeys.ADD_TEMPLATE %>',
				dialog: {
					modal: true,
					width: '80%'
				},
				eventName: '<portlet:namespace />selectTemplate',
				groupId: <%= groupId %>,
				storageType: '<%= PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE %>',
				structureName: 'structure',
				structureType: 'com.liferay.portlet.journal.model.JournalArticle',
				struts_action: '/dynamic_data_mapping/select_template',
				templateType: '<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>'
			},
			function(event) {
				document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value = event.ddmtemplateid;

				submitForm(document.<portlet:namespace />fm1, null, false, false);
			}
		);
	}
</aui:script>

<aui:script use="aui-base,aui-dialog-iframe,liferay-portlet-journal">
	var editDDMTemplate = A.one('#<portlet:namespace />editDDMTemplate');

	if (editDDMTemplate) {
		var windowId = A.guid();

		editDDMTemplate.on(
			'click',
			function (event) {
				Liferay.Util.openWindow(
					{
						dialog: {
							constrain: true,
							width: 820
						},
						id: windowId,
						title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>',

						<%
						DDMTemplate curDDMTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(groupId, PortalUtil.getClassNameId(DDMStructure.class), templateId);
						%>

						<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
							<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
							<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
							<portlet:param name="templateId" value="<%= (curDDMTemplate != null) ? String.valueOf(curDDMTemplate.getTemplateId()) : StringPool.BLANK %>" />
						</liferay-portlet:renderURL>

						uri: '<%= editTemplateURL %>'
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

			if (imageURL) {
				templateImage.attr('src', imageURL);

				templateImage.show();
			}
			else {
				templateImage.hide();
			}
		}

		changeTemplate();

		if (editTemplateLink) {
			templateIdSelector.on('change', changeTemplate);

			var windowId = A.guid();

			editTemplateLink.on(
				'click',
				function(event) {
					var selectedOption = templateIdSelector.one(':selected');
					var editTemplateURL = selectedOption.attr('data-url');

					Liferay.Util.openWindow(
					{
						dialog: {
							constrain: true,
							width: 820
						},
						id: windowId,
						title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>',
						uri: editTemplateURL
						}
					);
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
	Liferay.Portlet.Journal.PROXY.editorURL = '<%= HtmlUtil.escapeJS(editorURL) %>';
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