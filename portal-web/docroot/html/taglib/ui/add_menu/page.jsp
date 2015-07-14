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

<%@ include file="/html/taglib/ui/add_menu/init.jsp" %>

<%
JSONArray actionsJSONArray = (JSONArray)request.getAttribute("liferay-ui:add-menu:actionsJSONArray");
%>

<c:if test="<%= actionsJSONArray.length() > 0 %>">
	<c:choose>
		<c:when test="<%= actionsJSONArray.length() == 1 %>">

			<%
				JSONObject action = actionsJSONArray.getJSONObject(0);
			%>

			<a class="btn btn-action btn-bottom-right btn-primary" data-placement="left" data-toggle="tooltip" href="<%= action.getString("href") %>" title="<%= action.getString("title") %>">
				<span class="icon-plus"></span>
			</a>
			<aui:script>
				$(document).ready(
					function() {
						$('[data-toggle="tooltip"]').tooltip();
					}
				);
			</aui:script>
		</c:when>
		<c:otherwise>
			<div class="btn-action-secondary btn-bottom-right dropdown">
				<button aria-expanded="false" class="btn btn-primary" data-toggle="dropdown" type="button"><span class="icon-plus"></span></button>
				<ul class="dropdown-menu dropdown-menu-left-side-bottom">

				<%
				for (int i = 0; i < actionsJSONArray.length(); i++) {
					JSONObject action = actionsJSONArray.getJSONObject(i);
				%>

					<li><a href="<%= action.getString("href") %>"><%= action.getString("title") %></a></li>

				<%
				}
				%>

				</ul>
			</div>
		</c:otherwise>
	</c:choose>
</c:if>