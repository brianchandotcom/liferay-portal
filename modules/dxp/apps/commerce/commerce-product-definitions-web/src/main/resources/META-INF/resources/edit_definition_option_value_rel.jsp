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

		<liferay-ui:error exception="<%= CPDefinitionOptionValueRelKeyException.class %>" message="that-key-is-already-being-used" />

		<aui:fieldset>
			<aui:input id="optionValueName" name="name" />

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
	</div>
</aui:form>