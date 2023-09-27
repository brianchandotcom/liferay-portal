<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalArticle journalArticle = journalArticleTermsOfUseDisplayContext.getJournalArticle();
%>

<c:choose>
	<c:when test="<%= journalArticle != null %>">
		<liferay-asset:asset-display
			className="<%= JournalArticle.class.getName() %>"
			classPK="<%= journalArticle.getResourcePrimKey() %>"
			template="<%= AssetRenderer.TEMPLATE_FULL_CONTENT %>"
		/>
	</c:when>
	<c:otherwise>
		<p>
			<span>
				<liferay-ui:message key="placeholder-terms-of-use" />
			</span>
		</p>

		<p>
			<span>
				<liferay-ui:message key="you-must-configure-terms-of-use" />
			</span>
		</p>
	</c:otherwise>
</c:choose>