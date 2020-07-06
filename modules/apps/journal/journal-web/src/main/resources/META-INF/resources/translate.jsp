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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

JournalArticle article = (JournalArticle)request.getAttribute(JournalWebKeys.JOURNAL_ARTICLES);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);

JournalTranslateDisplayContext journalTranslateDisplayContext = new JournalTranslateDisplayContext(liferayPortletRequest);

renderResponse.setTitle(journalTranslateDisplayContext.getTitle());
%>

<portlet:actionURL name="/journal/update_translation" var="updateTranslationURL">
	<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
	<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
	<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
</portlet:actionURL>

<aui:form action="<%= updateTranslationURL %>" cssClass="translate-article" name="translate_fm" onSubmit='<%= "event.preventDefault();" + liferayPortletResponse.getNamespace() + "translateFields();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="targetLanguageId" type="hidden" value="<%= journalTranslateDisplayContext.getTargetLanguageId() %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<nav class="component-tbar subnav-tbar-light tbar">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<div class="tbar-section text-left">
						<react:component
							data="<%= journalTranslateDisplayContext.getTranslateLanguagesSelectorData() %>"
							module="js/translate/TranslateLanguagesSelector"
						/>
					</div>
				</li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" id="saveDraftBtn" primary="<%= false %>" type="submit" value="<%= journalEditArticleDisplayContext.getSaveButtonLabel() %>" />

						<aui:button cssClass="btn-sm" disabled="<%= journalEditArticleDisplayContext.isPending() %>" id="submitBtnId" primary="<%= true %>" type="submit" value="<%= journalEditArticleDisplayContext.getPublishButtonLabel() %>" />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="translate-body-form">
			<clay:row>
				<clay:col
					md="6"
				>

					<%
					String sourceLanguageIdTitle = journalTranslateDisplayContext.getLanguageIdTitle(journalTranslateDisplayContext.getSourceLanguageId());
					%>

					<clay:icon
						symbol="<%= StringUtil.toLowerCase(sourceLanguageIdTitle) %>"
					/>

					<span class="ml-1"> <%= sourceLanguageIdTitle %> </span>

					<div class="separator"><!-- --></div>
				</clay:col>

				<clay:col
					md="6"
				>

					<%
					String targetLanguageIdTitle = journalTranslateDisplayContext.getLanguageIdTitle(journalTranslateDisplayContext.getTargetLanguageId());
					%>

					<clay:icon
						symbol="<%= StringUtil.toLowerCase(targetLanguageIdTitle) %>"
					/>

					<span class="ml-1"> <%= targetLanguageIdTitle %> </span>

					<div class="separator"><!-- --></div>
				</clay:col>
			</clay:row>

			<%
			for (InfoFieldSetEntry infoFieldSetEntry : journalTranslateDisplayContext.getInfoFieldSetEntries()) {
				List<InfoFieldValue<Object>> infoFieldValues = journalTranslateDisplayContext.getInfoFieldValues(infoFieldSetEntry);

				if (ListUtil.isEmpty(infoFieldValues)) {
					continue;
				}

				String infoFieldSetLabel = journalTranslateDisplayContext.getInfoFieldSetLabel(infoFieldSetEntry, locale);

				if (Validator.isNotNull(infoFieldSetLabel)) {
			%>

					<clay:row>
						<clay:col
							md="6"
						>
							<div class="fieldset-title">
								<%= infoFieldSetLabel %>
							</div>
						</clay:col>

						<clay:col
							md="6"
						>
							<div class="fieldset-title">
								<%= infoFieldSetLabel %>
							</div>
						</clay:col>
					</clay:row>

				<%
				}

				for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
					InfoField infoField = infoFieldValue.getInfoField();

					String label = journalTranslateDisplayContext.getInfoFieldLabel(infoField);

					String id = "infoField--" + infoField.getName();
				%>

					<clay:row>
						<clay:col
							md="6"
						>
							<aui:input dir='<%= LanguageUtil.get(journalTranslateDisplayContext.getSourceLocale(), "lang.dir") %>' label="<%= label %>" name="<%= label %>" readonly="true" value="<%= String.valueOf(infoFieldValue.getValue(journalTranslateDisplayContext.getSourceLocale())) %>" />
						</clay:col>

						<clay:col
							md="6"
						>
							<aui:input dir='<%= LanguageUtil.get(journalTranslateDisplayContext.getTargetLocale(), "lang.dir") %>' label="<%= label %>" name="<%= id %>" value="<%= String.valueOf(infoFieldValue.getValue(journalTranslateDisplayContext.getTargetLocale())) %>" />
						</clay:col>
					</clay:row>

			<%
				}
			}
			%>

		</div>
	</clay:container-fluid>
</aui:form>

<aui:script>
	function <portlet:namespace />translateFields() {
		var form = document.getElementById('<portlet:namespace />translate_fm');

		submitForm(form);
	}
</aui:script>