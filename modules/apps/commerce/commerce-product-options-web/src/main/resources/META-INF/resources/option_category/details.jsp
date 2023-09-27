<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPOptionCategory cpOptionCategory = (CPOptionCategory)request.getAttribute(CPWebKeys.CP_OPTION_CATEGORY);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= cpOptionCategory %>" model="<%= CPOptionCategory.class %>" />

<liferay-ui:error exception="<%= CPOptionCategoryKeyException.class %>" message="that-key-is-already-being-used" />

<commerce-ui:panel
	elementClasses="mt-4"
>
	<aui:fieldset>
		<aui:input name="title" />

		<aui:input name="description" />

		<aui:input name="priority" />

		<aui:input helpMessage="key-help" name="key" />
	</aui:fieldset>
</commerce-ui:panel>

<c:if test="<%= cpOptionCategory == null %>">
	<aui:script require="frontend-js-web/index as frontendJsWeb">
		var {debounce} = frontendJsWeb;

		var form = document.getElementById('<portlet:namespace />fm');

		var keyInput = form.querySelector('#<portlet:namespace />key');
		var titleInput = form.querySelector('#<portlet:namespace />title');

		var handleOnTitleInput = function () {
			keyInput.value = titleInput.value;
		};

		titleInput.addEventListener('input', debounce(handleOnTitleInput, 200));
	</aui:script>
</c:if>