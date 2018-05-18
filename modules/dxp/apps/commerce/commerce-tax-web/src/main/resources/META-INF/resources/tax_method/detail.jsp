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

CommerceTaxMethodsDisplayContext commerceTaxMethodsDisplayContext = (CommerceTaxMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxMethod commerceTaxMethod = commerceTaxMethodsDisplayContext.getCommerceTaxMethod();

long commerceTaxMethodId = commerceTaxMethod.getCommerceTaxMethodId();
%>

<portlet:actionURL name="editCommerceTaxMethod" var="editCommerceTaxMethodActionURL" />

<aui:form action="<%= editCommerceTaxMethodActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceTaxMethod();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceTaxMethodId <= 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceTaxMethodId" type="hidden" value="<%= commerceTaxMethodId %>" />
	<aui:input name="engineKey" type="hidden" value="<%= commerceTaxMethod.getEngineKey() %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceTaxMethodNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= commerceTaxMethod %>" model="<%= CommerceTaxMethod.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="name" />

				<aui:input name="description" />

				<aui:input name="percentage" />

				<aui:input name="active" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceTaxMethod() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>