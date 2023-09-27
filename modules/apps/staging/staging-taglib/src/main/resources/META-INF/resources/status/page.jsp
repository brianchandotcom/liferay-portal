<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/status/init.jsp" %>

<c:if test="<%= themeDisplayScopeGroup.isStagingGroup() %>">
	<c:choose>
		<c:when test="<%= stagedPortlet %>">
			<span class="label <%= cssClass %>">
				<liferay-ui:message key='<%= LanguageUtil.get(request, published ? "published" : "unpublished") %>' />
			</span>
		</c:when>
		<c:otherwise>
			<clay:label
				displayType="warning"
				label="not-staged"
			/>
		</c:otherwise>
	</c:choose>
</c:if>