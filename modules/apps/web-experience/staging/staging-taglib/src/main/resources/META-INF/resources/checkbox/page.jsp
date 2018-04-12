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

<%@ include file="/checkbox/init.jsp" %>

<div class="custom-checkbox custom-control">
	<label>
		<input class="custom-control-input" id="<%= HtmlUtil.escape(domId) %>" <%= disabledString %> <%= checkedString %> type="checkbox" name="<%= HtmlUtil.escape(domName) %>">
		<span class="custom-control-label">
			<span class="custom-control-label-text">
				<%= HtmlUtil.escape(label) %><%= separator %>
				<c:if test="<%= Validator.isNotNull(description) %>">
					<span class="staging-checkbox-description"><%= HtmlUtil.escape(description) %></span>
				</c:if>

				<c:if test="<%= Validator.isNotNull(warning) %>">
					<span class="staging-checkbox-warning"><%= HtmlUtil.escape(warning) %></span>
				</c:if>

				<c:if test="<%= Validator.isNotNull(warning) %>">
					<span class="staging-checkbox-suggestion"><%= HtmlUtil.escape(suggestion) %></span>
				</c:if>
			</span>
		</span>
	</label>
</div>