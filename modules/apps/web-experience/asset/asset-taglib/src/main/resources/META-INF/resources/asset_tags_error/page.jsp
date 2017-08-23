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

<%@ include file="/html/taglib/ui/asset_tags_error/init.jsp" %>

<liferay-ui:error exception="<%= AssetTagException.class %>">

	<%
	AssetTagException ate = (AssetTagException)errorException;
	%>

	<c:choose>
		<c:when test="<%= ate.getType() == AssetTagException.AT_LEAST_ONE_TAG %>">
			<liferay-ui:message key="please-enter-at-least-one-tag" />
		</c:when>
		<c:when test="<%= ate.getType() == AssetTagException.INVALID_CHARACTER %>">
			<liferay-ui:message key="one-or-more-tags-contains-invalid-characters" />
		</c:when>
	</c:choose>
</liferay-ui:error>