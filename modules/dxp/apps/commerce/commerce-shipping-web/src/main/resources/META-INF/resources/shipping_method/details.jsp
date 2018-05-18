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
String redirect = ParamUtil.getString(request, "redirect");

CommerceShippingMethodsDisplayContext commerceShippingMethodsDisplayContext = (CommerceShippingMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingMethod commerceShippingMethod = commerceShippingMethodsDisplayContext.getCommerceShippingMethod();

long commerceShippingMethodId = commerceShippingMethod.getCommerceShippingMethodId();
%>

<portlet:actionURL name="editCommerceShippingMethod" var="editCommerceShippingMethodActionURL" />

<aui:form action="<%= editCommerceShippingMethodActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceShippingMethod();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingMethodId <= 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />
	<aui:input name="engineKey" type="hidden" value="<%= commerceShippingMethod.getEngineKey() %>" />

	<liferay-ui:error exception="<%= CommerceShippingMethodNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= commerceShippingMethod %>" model="<%= CommerceShippingMethod.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" name="name" />

			<aui:input name="description" />

			<%
			String thumbnailSrc = StringPool.BLANK;

			if (commerceShippingMethod != null) {
				thumbnailSrc = commerceShippingMethod.getImageURL(themeDisplay);
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
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceShippingMethod() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>