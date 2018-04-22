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
CommerceUserSegmentDisplayContext commerceUserSegmentDisplayContext = (CommerceUserSegmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceUserSegmentEntry commerceUserSegmentEntry = commerceUserSegmentDisplayContext.getCommerceUserSegmentEntry();
long commerceUserSegmentEntryId = commerceUserSegmentDisplayContext.getCommerceUserSegmentEntryId();
%>

<portlet:actionURL name="editCommerceUserSegmentEntry" var="editCommerceUserSegmentEntryActionURL" />

<aui:form action="<%= editCommerceUserSegmentEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceUserSegmentEntry();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceUserSegmentEntryId" type="hidden" value="<%= commerceUserSegmentEntryId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceUserSegmentEntry %>" model="<%= CommerceUserSegmentEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="name" />

				<aui:input disabled="<%= commerceUserSegmentEntry.isSystem() %>" helpMessage="key-help" name="key" />

				<aui:input name="priority" />

				<aui:input disabled="<%= commerceUserSegmentEntry.isSystem() %>" name="active" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceUserSegmentEntry() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<c:if test="<%= commerceUserSegmentEntry == null %>">
	<aui:script sandbox="<%= true %>">
		var form = $(document.<portlet:namespace />fm);

		var keyInput = form.fm('key');
		var nameInput = form.fm('name');

		var onNameInput = _.debounce(
			function(event) {
				keyInput.val(nameInput.val());
			},
			200
		);

		nameInput.on('input', onNameInput);
	</aui:script>
</c:if>