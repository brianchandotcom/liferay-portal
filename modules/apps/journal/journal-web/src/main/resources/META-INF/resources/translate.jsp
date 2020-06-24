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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<aui:form name="translate_fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-metadata-type">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<div class="tbar-section text-left">
						<h2 class="h4 text-truncate-inline" title="<%= HtmlUtil.escapeAttribute(title) %>">
							<span class="text-truncate"><%= HtmlUtil.escape(title) %></span>
						</h4>
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
		<div class="bg-white p-4">

			<%
			for (InfoFieldValue<Object> infoFieldValue : infoItemFieldValues.getInfoFieldValues()) {
				InfoField infoField = infoFieldValue.getInfoField();

				if (translationInfoFieldChecker.isTranslatable(infoField)) {
					InfoLocalizedValue<String> labelInfoLocalizedValue = infoField.getLabelInfoLocalizedValue();

					String label = labelInfoLocalizedValue.getValue(locale);
			%>

				<clay:row>
					<clay:col
						md="6"
					>
						<aui:input label="<%= label %>" name="<%= label %>" readonly="true" value="<%= String.valueOf(infoFieldValue.getValue(locale)) %>" />
					</clay:col>

					<clay:col
						md="6"
					>
						<aui:input label="<%= label %>" name="<%= label %>" value="" />
					</clay:col>
				</clay:row>

			<%
				}
			}
			%>

		</div>
	</clay:container-fluid>
</aui:form>