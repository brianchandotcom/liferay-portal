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
CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPOption cpOption = cpOptionDisplayContext.getCPOption();

long cpOptionId = cpOptionDisplayContext.getCPOptionId();

boolean hasCustomAttributesAvailable = CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), CPOption.class.getName(), cpOptionId, null);
%>

<portlet:actionURL name="editProductOption" var="editProductOptionActionURL" />

<aui:form action="<%= editProductOptionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpOptionId" type="hidden" value="<%= String.valueOf(cpOptionId) %>" />

	<div class="lfr-form-content">

		<%
		List<DDMFormFieldType> ddmFormFieldTypes = cpOptionDisplayContext.getDDMFormFieldTypes();
		%>

		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="product-option-details"
		/>

		<aui:model-context bean="<%= cpOption %>" model="<%= CPOption.class %>" />

		<liferay-ui:error exception="<%= CPOptionKeyException.class %>" message="that-key-is-already-being-used" />

		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" name="name" wrapperCssClass="commerce-product-option-title" />

			<aui:input name="description" wrapperCssClass="commerce-product-option-description" />

			<aui:select label="option-field-type" name="DDMFormFieldTypeName" showEmptyOption="<%= true %>">

				<%
				for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
				%>

					<aui:option label="<%= ddmFormFieldType.getName() %>" selected="<%= (cpOption != null) && cpOption.getDDMFormFieldTypeName().equals(ddmFormFieldType.getName()) %>" value="<%= ddmFormFieldType.getName() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input label="use-in-faceted-navigation" name="facetable" />

			<aui:input name="required" />

			<aui:input name="skuContributor" />

			<aui:input helpMessage="key-help" name="key" />
		</aui:fieldset>

		<c:if test="<%= hasCustomAttributesAvailable %>">
			<aui:fieldset>
				<liferay-expando:custom-attribute-list
					className="<%= CPOption.class.getName() %>"
					classPK="<%= (cpOption != null) ? cpOption.getCPOptionId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</aui:fieldset>
		</c:if>

		<c:if test="<%= cpOption == null %>">
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
	</div>
</aui:form>