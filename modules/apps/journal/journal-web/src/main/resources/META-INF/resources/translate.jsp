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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

JournalTranslateDisplayContext journalTranslateDisplayContext = new JournalTranslateDisplayContext(liferayPortletRequest);

renderResponse.setTitle(journalTranslateDisplayContext.getTitle());
%>

<aui:form cssClass="translate-article" name="translate_fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

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

					<%= infoFieldSetLabel %>

				<%
				}

				for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
					String label = journalTranslateDisplayContext.getInfoFieldLabel(infoFieldValue.getInfoField());
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
							<aui:input dir='<%= LanguageUtil.get(journalTranslateDisplayContext.getTargetLocale(), "lang.dir") %>' label="<%= label %>" name="<%= label %>" value="<%= String.valueOf(infoFieldValue.getValue(journalTranslateDisplayContext.getTargetLocale())) %>" />
						</clay:col>
					</clay:row>

			<%
				}
			}
			%>

		</div>
	</clay:container-fluid>
</aui:form>