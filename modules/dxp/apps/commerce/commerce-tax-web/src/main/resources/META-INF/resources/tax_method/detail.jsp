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