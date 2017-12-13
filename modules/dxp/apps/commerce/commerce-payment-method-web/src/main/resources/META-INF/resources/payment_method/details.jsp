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
CommercePaymentMethodsDisplayContext commercePaymentMethodsDisplayContext = (CommercePaymentMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePaymentMethod commercePaymentMethod = commercePaymentMethodsDisplayContext.getCommercePaymentMethod();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<liferay-ui:error exception="<%= CommercePaymentMethodNameException.class %>" message="please-enter-a-valid-name" />

<aui:model-context bean="<%= commercePaymentMethod %>" model="<%= CommercePaymentMethod.class %>" />

<aui:fieldset>
	<aui:input name="name" />

	<aui:input name="description" />

	<%
	String thumbnailSrc = StringPool.BLANK;

	if (commercePaymentMethod != null) {
		thumbnailSrc = commercePaymentMethod.getPaymentMethodImageURL(themeDisplay);
	}
	%>

	<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
		<div class="row">
			<div class="col-md-4">
				<img class="w-100" src="<%= thumbnailSrc %>" />
			</div>
		</div>
	</c:if>

	<aui:input label="" name="imageFile" type="file" />

	<aui:input name="priority" />

	<aui:input name="active" />
</aui:fieldset>