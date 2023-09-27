<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:field-wrapper helpMessage="terms-of-use-web-content-help" label="terms-of-use-web-content">
	<aui:fieldset>
		<aui:input label="group-id" name='<%= "settings--" + journalArticleTermsOfUseDisplayContext.getTermsOfUseJournalArticleGroupIdConfigurationProperty() + "--" %>' type="text" value="<%= String.valueOf(journalArticleTermsOfUseDisplayContext.getTermsOfUseJournalArticleGroupId()) %>" />

		<aui:input label="article-id" name='<%= "settings--" + journalArticleTermsOfUseDisplayContext.getTermsOfUseJournalArticleIdConfigurationProperty() + "--" %>' type="text" value="<%= journalArticleTermsOfUseDisplayContext.getTermsOfUseJournalArticleId() %>" />
	</aui:fieldset>
</aui:field-wrapper>