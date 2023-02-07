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
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);

String viewMode = ParamUtil.getString(request, "viewMode");
%>

<c:if test="<%= !viewMode.equals(Constants.PRINT) %>">

	<%
	String languageId = LanguageUtil.getLanguageId(request);

	String[] availableLocales = articleDisplay.getAvailableLocales();

	if (ArrayUtil.isNotEmpty(availableLocales) && !ArrayUtil.contains(availableLocales, languageId)) {
		if (article != null) {
			languageId = article.getDefaultLanguageId();
		}
		else {
			languageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

			if (!ArrayUtil.contains(availableLocales, languageId)) {
				languageId = availableLocales[0];
			}
		}
	}
	%>

	<c:if test="<%= availableLocales.length > 1 %>">
		<div class="locale-actions user-tool-asset-addon-entry">
			<liferay-ui:language
				formAction="<%= currentURL %>"
				languageId="<%= languageId %>"
				languageIds="<%= availableLocales %>"
			/>
		</div>
	</c:if>
</c:if>