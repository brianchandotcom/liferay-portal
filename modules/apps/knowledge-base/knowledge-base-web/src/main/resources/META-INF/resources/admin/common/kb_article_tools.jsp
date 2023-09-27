<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);
%>

<div class="kb-article-tools">
	<c:if test="<%= kbGroupServiceConfiguration.sourceURLEnabled() && Validator.isUrl(kbArticle.getSourceURL()) %>">
		<a href="<%= HtmlUtil.escapeAttribute(kbArticle.getSourceURL()) %>" target="_blank">
			<clay:label
				cssClass="kb-article-source-url"
				displayType="success"
				label="<%= HtmlUtil.escape(kbGroupServiceConfiguration.sourceURLEditMessageKey()) %>"
			/>
		</a>
	</c:if>
</div>