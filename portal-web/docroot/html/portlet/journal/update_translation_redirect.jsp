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
String cmd = ParamUtil.getString(request, Constants.CMD);

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

String toLanguageId = ParamUtil.getString(request, "toLanguageId");

String toLanguageDisplayName = StringPool.BLANK;

if (cmd.equals(Constants.TRANSLATE)) {
	Locale toLocale = LocaleUtil.fromLanguageId(toLanguageId);

	toLanguageDisplayName = toLocale.getDisplayName(locale);
}
%>

<aui:script use="aui-base">
	var openingWindow = Liferay.Util.getOpener();

	openingWindow.<portlet:namespace />postProcessTranslation('<%= System.currentTimeMillis() %>', '<%= HtmlUtil.escapeJS(cmd) %>', '<%= article.getVersion() %>', '<%= HtmlUtil.escapeJS(toLanguageId) %>', '<%= toLanguageDisplayName %>', '<%= WorkflowConstants.toLabel(article.getStatus()) %>');

	Liferay.fire(
		'closeWindow',
		{
			id: '<%= HtmlUtil.escapeJS(renderResponse.getNamespace() + toLanguageId) %>'
		}
	);
</aui:script>