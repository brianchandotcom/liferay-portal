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

String sourceLanguageId = (String)request.getAttribute(JournalWebConstants.SOURCE_LANGUAGE_ID);
String targetLanguageId = (String)request.getAttribute(JournalWebConstants.TARGET_LANGUAGE_ID);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(article.getTitle());

JournalTranslateDisplayContext journalTranslateDisplayContext = new JournalTranslateDisplayContext(liferayPortletRequest);
%>

<aui:form cssClass="translate-article" name="translate_fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<nav class="component-tbar subnav-tbar-light tbar">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<div class="tbar-section text-left">

						<%
						Map<String, Object> data = HashMapBuilder.<String, Object>put(
							"currentUrl", currentURL
						).put(
							"sourceAvailableLanguages",
							request.getAttribute(JournalWebConstants.AVAILABLE_SOURCE_LANGUAGE_IDS)
						).put(
							"sourceLanguageId", sourceLanguageId
						).put(
							"targetAvailableLanguages",
							request.getAttribute(JournalWebConstants.AVAILABLE_TARGET_LANGUAGE_IDS)
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

						<aui:button cssClass="btn-sm" id="submitBtnId" primary="<%= true %>" type="submit" value='<%= LanguageUtil.get(request, "publish") %>' />
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
					String sourceLanguageIdTitle = StringUtil.replace(sourceLanguageId, CharPool.UNDERLINE, CharPool.DASH);
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
					String targetLanguageIdTitle = StringUtil.replace(targetLanguageId, CharPool.UNDERLINE, CharPool.DASH);
					%>

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

			InfoForm infoForm = (InfoForm)request.getAttribute(InfoForm.class.getName());
			InfoItemFieldValues infoItemFieldValues = (InfoItemFieldValues)request.getAttribute(InfoItemFieldValues.class.getName());

			for (InfoFieldSetEntry infoFieldSetEntry : infoForm.getInfoFieldSetEntries()) {
				List<InfoFieldValue<Object>> infoFieldValues = journalTranslateDisplayContext.getInfoFieldValues(infoFieldSetEntry, infoItemFieldValues);

				if (ListUtil.isEmpty(infoFieldValues)) {
					continue;
				}

				String infoFieldSetLabel = journalTranslateDisplayContext.getInfoFieldSetLabel(infoFieldSetEntry, locale);

				if (Validator.isNotNull(infoFieldSetLabel)) {
			%>

					<%= infoFieldSetLabel %>

				<%
				}

				for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
					InfoField infoField = infoFieldValue.getInfoField();

					InfoLocalizedValue<String> labelInfoLocalizedValue = infoField.getLabelInfoLocalizedValue();

					String label = labelInfoLocalizedValue.getValue(sourceLocale);
				%>

					<clay:row>
						<clay:col
							md="6"
						>
							<aui:input dir='<%= LanguageUtil.get(sourceLocale, "lang.dir") %>' label="<%= label %>" name="<%= label %>" readonly="true" value="<%= String.valueOf(infoFieldValue.getValue(sourceLocale)) %>" />
						</clay:col>

						<clay:col
							md="6"
						>
							<aui:input dir='<%= LanguageUtil.get(targetLocale, "lang.dir") %>' label="<%= label %>" name="<%= label %>" value="<%= String.valueOf(infoFieldValue.getValue(targetLocale)) %>" />
						</clay:col>
					</clay:row>

			<%
				}
			}
			%>

		</div>
	</clay:container-fluid>
</aui:form>