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
CPOptionCategory cpOptionCategory = (CPOptionCategory)request.getAttribute(CPWebKeys.CP_OPTION_CATEGORY);
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpOptionCategory %>" model="<%= CPOptionCategory.class %>" />

<liferay-ui:error exception="<%= CPOptionCategoryKeyException.class %>" message="please-enter-a-unique-key" />

<aui:fieldset>
	<aui:input autoFocus="<%= true %>" name="title" />

	<aui:input name="description" />

	<aui:input name="priority" />

	<aui:input helpMessage="key-help" name="key" />
</aui:fieldset>

<c:if test="<%= cpOptionCategory == null %>">
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