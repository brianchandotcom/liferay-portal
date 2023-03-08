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

<%@ include file="/fragment/renderer/dynamic_field/init.jsp" %>

<div class="<%= namespace %>dynamic-field" id="<%= uuid %>">
	<c:if test="<%= !Validator.isBlank(label) %>">
		<<%= labelElementType %> class="node-label"><%= HtmlUtil.escape(label) %>:</<%= labelElementType %>>
	</c:if>
	<<%= valueElementType %> class="node-value"><%= HtmlUtil.escape(fieldValue) %></<%= valueElementType %>>
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"elementId", uuid
		).put(
			"field", field
		).put(
			"namespace", namespace
		).build()
	%>'
	module="fragment/renderer/dynamic_field/js/CPInstanceChangeHandler"
/>