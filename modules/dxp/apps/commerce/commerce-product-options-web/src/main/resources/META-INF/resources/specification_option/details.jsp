<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPSpecificationOption cpSpecificationOption = (CPSpecificationOption)request.getAttribute(CPWebKeys.CP_SPECIFICATION_OPTION);
CPSpecificationOptionDisplayContext cpSpecificationOptionDisplayContext = (CPSpecificationOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long cpOptionCategoryId = BeanParamUtil.getLong(cpSpecificationOption, request, "CPOptionCategoryId");

List<CPOptionCategory> cpOptionCategories = cpSpecificationOptionDisplayContext.getCPOptionCategories();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= cpSpecificationOption %>" model="<%= CPSpecificationOption.class %>" />

<liferay-ui:error exception="<%= CPSpecificationOptionKeyException.MustNotBeDuplicate.class %>" message="that-key-is-already-being-used" />
<liferay-ui:error exception="<%= CPSpecificationOptionKeyException.MustNotBeNull.class %>" message="please-enter-a-valid-key" />

<aui:fieldset>
	<aui:input autoFocus="<%= true %>" name="title" />

	<aui:input name="description" />

	<aui:input label="use-in-faceted-navigation" name="facetable" />

	<aui:select label="specification-group" name="CPOptionCategoryId" showEmptyOption="<%= true %>">

		<%
		for (CPOptionCategory cpOptionCategory : cpOptionCategories) {
		%>

			<aui:option label="<%= cpOptionCategory.getTitle(locale) %>" selected="<%= cpOptionCategoryId == cpOptionCategory.getCPOptionCategoryId() %>" value="<%= cpOptionCategory.getCPOptionCategoryId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:input helpMessage="key-help" name="key" />
</aui:fieldset>

<c:if test="<%= cpSpecificationOption == null %>">
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