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

<%@ include file="/availability_label/init.jsp" %>

<span class="<%= Validator.isNull(label) ? "invisible" + StringPool.SPACE : StringPool.BLANK %>label label-<%= labelType %> m-0 <%= namespace %>availability-label">
	<span class="label-item label-item-expand"><%= label %></span>
</span>

<c:if test="<%= Validator.isNotNull(namespace) %>">
	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"namespace", namespace
			).build()
		%>'
		module="availability_label/js/AvailabilityCPInstanceChangeHandler"
	/>
</c:if>