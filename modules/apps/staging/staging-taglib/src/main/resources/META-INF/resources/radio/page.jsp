<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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