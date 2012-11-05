<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
String redirect = ParamUtil.getString(request, "redirect");

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

String backURL = ParamUtil.getString(request, "backURL");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long classNameId = BeanParamUtil.getLong(article, request, "classNameId");

String toLanguageId = ParamUtil.getString(request, "toLanguageId");

if ((article != null) && Validator.isNotNull(toLanguageId)) {
	redirect = null;
}

boolean localizeTitle = true;
String title = "new-web-content";

if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
	title = "structure-default-values";
}
else if (article != null) {
	localizeTitle = false;

	if (Validator.isNotNull(toLanguageId)) {
		title = article.getTitle(toLanguageId);
	}
	else {
		title = article.getTitle(locale);
	}
}
%>

<liferay-ui:header
	backURL="<%= article == null ? redirect : backURL %>"
	localizeTitle="<%= localizeTitle %>"
	title="<%= title %>"
/>