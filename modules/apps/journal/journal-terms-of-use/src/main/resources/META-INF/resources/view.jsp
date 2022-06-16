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