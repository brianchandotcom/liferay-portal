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

InfoItemFieldValues infoItemFieldValues = (InfoItemFieldValues)request.getAttribute(InfoItemFieldValues.class.getName());

JournalArticle article = (JournalArticle)request.getAttribute(JournalWebKeys.JOURNAL_ARTICLES);

String title = article.getTitle();

TranslationInfoFieldChecker translationInfoFieldChecker = (TranslationInfoFieldChecker)request.getAttribute(TranslationInfoFieldChecker.class.getName());

List<String> availableSourceLanguageIds = (List)request.getAttribute(JournalWebConstants.AVAILABLE_SOURCE_LANGUAGE_IDS);
List<String> availableTargetLanguageIds = (List)request.getAttribute(JournalWebConstants.AVAILABLE_TARGET_LANGUAGE_IDS);

String sourceLanguageId = (String)request.getAttribute(JournalWebConstants.SOURCE_LANGUAGE_ID);
String targetLanguageId = (String)request.getAttribute(JournalWebConstants.TARGET_LANGUAGE_ID);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<aui:form cssClass="translate-article" name="translate_fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-metadata-type">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<div class="tbar-section text-left">

						<%
						Map<String, Object> data = HashMapBuilder.<String, Object>put(
							"currentUrl", currentURL
						).put(
							"sourceAvailableLanguages",
							availableSourceLanguageIds
						).put(
							"sourceLanguageId", sourceLanguageId
						).put(
							"targetAvailableLanguages",
							availableTargetLanguageIds
						).put(
							"targetLanguageId", targetLanguageId
						).build();
						%>

						<react:component
							data="<%= data %>"
							module="js/translate/TranslateLanguagesSelector"
						/>
					</div>
				</li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" id="saveDraftBtn" value='<%= LanguageUtil.get(request, "save-as-draft") %>' />

						<aui:button cssClass="btn-sm mr-3" id="submitBtnId" primary="<%= true %>" type="submit" value='<%= LanguageUtil.get(request, "publish") %>' />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="translate-body-form">

			<%
			String sourceLanguageIdTitle = LocaleUtil.toBCP47LanguageId(sourceLanguageId);
			String targetLanguageIdTitle = LocaleUtil.toBCP47LanguageId(targetLanguageId);
			%>

			<clay:row>
				<clay:col
					md="6"
				>
					<clay:icon
						symbol="<%= StringUtil.toLowerCase(sourceLanguageIdTitle) %>"
					/>

					<span class="ml-1"> <%= sourceLanguageIdTitle %> </span>

					<div class="separator"><!-- --></div>
				</clay:col>

				<clay:col
					md="6"
				>
					<clay:icon
						symbol="<%= StringUtil.toLowerCase(targetLanguageIdTitle) %>"
					/>

					<span class="ml-1"> <%= targetLanguageIdTitle %> </span>

					<div class="separator"><!-- --></div>
				</clay:col>
			</clay:row>

			<%
			Locale sourceLocale = LocaleUtil.fromLanguageId(sourceLanguageId);
			Locale targetLocale = LocaleUtil.fromLanguageId(targetLanguageId);

			for (InfoFieldValue<Object> infoFieldValue : infoItemFieldValues.getInfoFieldValues()) {
				InfoField infoField = infoFieldValue.getInfoField();

				if (translationInfoFieldChecker.isTranslatable(infoField)) {
					InfoLocalizedValue<String> labelInfoLocalizedValue = infoField.getLabelInfoLocalizedValue();

					String label = labelInfoLocalizedValue.getValue(sourceLocale);
			%>

					<clay:row>
						<clay:col
							md="6"
						>
							<aui:input label="<%= label %>" name="<%= label %>" readonly="true" value="<%= String.valueOf(infoFieldValue.getValue(sourceLocale)) %>" />
						</clay:col>

						<clay:col
							md="6"
						>
							<aui:input label="<%= label %>" name="<%= label %>" value="<%= String.valueOf(infoFieldValue.getValue(targetLocale)) %>" />
						</clay:col>
					</clay:row>

			<%
				}
			}
			%>

		</div>
	</clay:container-fluid>
</aui:form>