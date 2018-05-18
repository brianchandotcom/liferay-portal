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
CommercePaymentMethodsDisplayContext commercePaymentMethodsDisplayContext = (CommercePaymentMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePaymentMethod commercePaymentMethod = commercePaymentMethodsDisplayContext.getCommercePaymentMethod();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<liferay-ui:error exception="<%= CommercePaymentMethodNameException.class %>" message="please-enter-a-valid-name" />

<aui:model-context bean="<%= commercePaymentMethod %>" model="<%= CommercePaymentMethod.class %>" />

<aui:fieldset>
	<aui:input name="name" />

	<aui:input name="description" />

	<%
	String thumbnailSrc = StringPool.BLANK;

	if (commercePaymentMethod != null) {
		thumbnailSrc = commercePaymentMethod.getImageURL(themeDisplay);
	}
	%>

	<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
		<div class="row">
			<div class="col-md-4">
				<img class="w-100" src="<%= thumbnailSrc %>" />
			</div>
		</div>
	</c:if>

	<aui:input label="image" name="imageFile" type="file" />

	<aui:input name="priority" />

	<aui:input name="active" />
</aui:fieldset>