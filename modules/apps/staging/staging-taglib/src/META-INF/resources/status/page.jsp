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

<%@ include file="/status/init.jsp" %>

<c:if test="<%= themeDisplayScopeGroup.isStagingGroup() %>">
	<c:choose>
		<c:when test="<%= stagedPortlet %>">
			<span class="label <%= cssClass %>">
				<liferay-ui:message key='<%= LanguageUtil.get(request, published ? "published" : "unpublished") %>' />
			</span>
		</c:when>
		<c:otherwise>
			<span class="label label-warning">
				<liferay-ui:message key="not-staged" />
			</span>
		</c:otherwise>
	</c:choose>
</c:if>