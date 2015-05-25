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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
boolean termsOfUseRequired = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.TERMS_OF_USE_REQUIRED, PropsValues.TERMS_OF_USE_REQUIRED);
long termsOfUseGroupId = PrefsPropsUtil.getLong(company.getCompanyId(), PropsKeys.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID, PropsValues.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID);
String termsOfUseArticleId = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.TERMS_OF_USE_JOURNAL_ARTICLE_ID, PropsValues.TERMS_OF_USE_JOURNAL_ARTICLE_ID);
%>

<h3><liferay-ui:message key="terms-of-use" /></h3>

<aui:fieldset>
	<aui:input label="terms-of-use-required" name='<%= "settings--" + PropsKeys.TERMS_OF_USE_REQUIRED + "--" %>' type="checkbox" value="<%= termsOfUseRequired %>" />

	<aui:field-wrapper helpMessage="terms-of-use-web-content-help" label="terms-of-use-web-content">
		<aui:input label="group-id" name='<%= "settings--" + PropsKeys.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID + "--" %>' type="text" value="<%= termsOfUseGroupId %>" />

		<aui:input label="article-id" name='<%= "settings--" + PropsKeys.TERMS_OF_USE_JOURNAL_ARTICLE_ID + "--" %>' type="text" value="<%= termsOfUseArticleId %>" />
	</aui:field-wrapper>
</aui:fieldset>