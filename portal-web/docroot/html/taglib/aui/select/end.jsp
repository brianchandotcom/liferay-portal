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

<%@ include file="/html/taglib/aui/select/init.jsp" %>

	</select>

	<c:if test="<%= Validator.isNotNull(suffix) %>">
		<span class="suffix">
			<liferay-ui:message key="<%= suffix %>" />
		</span>
	</c:if>

	<c:if test='<%= inlineLabel.equals("right") %>'>
		<label <%= AUIUtil.buildLabel("select", inlineField, true, namespace + id) %>>
			<liferay-ui:message key="<%= label %>" />

			<c:if test="<%= Validator.isNotNull(helpMessage) %>">
				<liferay-ui:icon-help message="<%= helpMessage %>" />
			</c:if>

			<c:if test="<%= changesContext %>">
				<span class="hide-accessible"><liferay-ui:message key="changing-the-value-of-this-field-reloads-the-page" />)</span>
			</c:if>
		</label>
	</c:if>
</div>

<aui:script sandbox="<%= true %>">
	var select = $('#<%= namespace + id %>');

	<c:if test="<%= BrowserSnifferUtil.isEdge(request) || BrowserSnifferUtil.isIe(request) %>">
		select.on(
			'keydown',
			function(event) {
				if (event.which == 27) {
					event.stopPropagation();
				}
			}
		);
	</c:if>

	<c:if test="<%= BrowserSnifferUtil.isIe(request) && (BrowserSnifferUtil.getMajorVersion(request) == 11.0) %>">
		select.on(
			'mousedown',
			function(event) {
				event.currentTarget.focus();
			}
		);
	</c:if>
</aui:script>