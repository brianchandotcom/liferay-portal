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

<%@ include file="/init.jsp" %>

<%
CPOptionValue cpOptionValue = (CPOptionValue)request.getAttribute(CPWebKeys.CP_OPTION_VALUE);
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="product-option-value-details" />

<aui:model-context bean="<%= cpOptionValue %>" model="<%= CPOptionValue.class %>" />

<aui:fieldset>
	<aui:input name="title" />

	<aui:input name="priority" />

	<aui:input helpMessage="key-field-help" name="key" />
</aui:fieldset>

<c:if test="<%= cpOptionValue == null %>">
	<aui:script sandbox="<%= true %>">
		var form = $(document.<portlet:namespace />fm);

		var keyInput = form.fm('key');
		var titleInput = form.fm('title');

		var onTitleInput = _.debounce(
			function(event) {
				keyInput.val(titleInput.val());
			},
			200
		);

		titleInput.on('input', onTitleInput);
	</aui:script>
</c:if>