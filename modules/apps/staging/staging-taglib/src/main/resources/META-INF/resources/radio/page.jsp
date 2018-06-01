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

<%@ include file="/radio/init.jsp" %>

<div class="custom-control <%= inlineString %> custom-radio">
	<label>
		<input
			<%= checkedString %>
			class="custom-control-input"
			data-qa-id="<%= dataQAID %>"
			<%= disabledString %>
			id="<%= HtmlUtil.escape(domId) %>"
			name="<%= HtmlUtil.escape(domName) %>"
			type="radio"
			value="<%= HtmlUtil.escape(value) %>"
		>

		<span class="custom-control-label">
			<span class="custom-control-label-text">
				<%= HtmlUtil.escape(label) %><%= separator %>

				<liferay-staging:popover
					id="<%= popoverName %>"
					text="<%= popoverText %>"
					title="<%= label %>"
				/>

				<c:if test="<%= Validator.isNotNull(description) %>">
					<span class="staging-taglib-checkbox-description"><%= HtmlUtil.escape(description) %></span>
				</c:if>
			</span>
		</span>
	</label>
</div>