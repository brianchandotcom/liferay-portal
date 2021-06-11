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

<%@ include file="/info/item/renderer/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
ObjectEntry objectEntry = (ObjectEntry)request.getAttribute(ObjectWebKeys.OBJECT_ENTRY);
Map<String, Serializable> objectValues = (Map<String, Serializable>)request.getAttribute(ObjectWebKeys.OBJECT_VALUES);
%>

<h3>
	<%= objectDefinition.getName() %> <%= objectEntry.getObjectEntryId() %>
</h3>

<p>
	<ul>

		<%
		for (Map.Entry<String, Serializable> entry : objectValues.entrySet()) {
		%>

			<li>
				<b><%= entry.getKey() %></b>: <%= entry.getValue() %>
			</li>

		<%
		}
		%>

	</ul>
</p>