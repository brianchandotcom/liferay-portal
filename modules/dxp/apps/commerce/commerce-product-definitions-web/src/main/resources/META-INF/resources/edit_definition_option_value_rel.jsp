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
CPDefinitionOptionValueRelDisplayContext cpDefinitionOptionValueRelDisplayContext = (CPDefinitionOptionValueRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long cpDefinitionOptionRelId = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionRelId();

CPDefinitionOptionValueRel cpDefinitionOptionValueRel = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionValueRel();

long cpDefinitionOptionValueRelId = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionValueRelId();
%>

<portlet:actionURL name="editProductDefinitionOptionValueRel" var="editProductDefinitionOptionValueRelActionURL" />

<aui:form action="<%= editProductDefinitionOptionValueRelActionURL %>" cssClass="container-fluid-1280" method="post" name="cpDefinitionOptionValueRelfm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDefinitionOptionValueRel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionOptionRelId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionRelId) %>" />
	<aui:input name="cpDefinitionOptionValueRelId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionValueRelId) %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= cpDefinitionOptionValueRel %>" model="<%= CPDefinitionOptionValueRel.class %>" />

		<liferay-ui:error exception="<%= CPDefinitionOptionValueRelKeyException.class %>" message="please-enter-a-unique-key" />

		<aui:fieldset>
			<aui:input id="optionValueTitle" name="title" />

			<aui:input name="priority" />

			<aui:input helpMessage="key-help" name="key" />

			<c:if test="<%= cpDefinitionOptionValueRelDisplayContext.hasCustomAttributesAvailable() %>">
				<liferay-expando:custom-attribute-list
					className="<%= CPDefinitionOptionValueRel.class.getName() %>"
					classPK="<%= (cpDefinitionOptionValueRel != null) ? cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</c:if>
		</aui:fieldset>

		<c:if test="<%= cpDefinitionOptionValueRel == null %>">
			<aui:script sandbox="<%= true %>">
				var form = $(document.<portlet:namespace />cpDefinitionOptionValueRelfm);

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
	</div>
</aui:form>