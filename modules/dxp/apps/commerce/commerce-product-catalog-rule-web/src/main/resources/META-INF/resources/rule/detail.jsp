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
CPCatalogRuleDisplayContext cpCatalogRuleDisplayContext = (CPCatalogRuleDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPRule cpRule = cpCatalogRuleDisplayContext.getCPRule();
long cpRuleId = cpCatalogRuleDisplayContext.getCPRuleId();
List<CPRuleType> cpRuleTypes = cpCatalogRuleDisplayContext.getCPRuleTypes();

String name = BeanParamUtil.getString(cpRule, request, "name");
String type = BeanParamUtil.getString(cpRule, request, "type");
%>

<portlet:actionURL name="editCPRule" var="editCPRuleActionURL" />

<aui:form action="<%= editCPRuleActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCPRule();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpRule == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpRuleId" type="hidden" value="<%= cpRuleId %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CPRuleTypeException.class %>" message="please-select-a-valid-catalog-rule-type" />

		<aui:model-context bean="<%= cpRule %>" model="<%= CPRule.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="name" value="<%= name %>" />

				<aui:input name="active" />

				<aui:select name="type" onChange='<%= renderResponse.getNamespace() + "selectType();" %>' showEmptyOption="<%= true %>">

					<%
					for (CPRuleType cpRuleType : cpRuleTypes) {
						String cpRuleTypeKey = cpRuleType.getKey();
					%>

						<aui:option label="<%= cpRuleType.getLabel(locale) %>" selected="<%= (cpRule != null) && cpRuleTypeKey.equals(type) %>" value="<%= cpRuleTypeKey %>" />

					<%
					}
					%>

				</aui:select>

				<%
				CPRuleTypeJSPContributor cpRuleTypeJSPContributor = cpCatalogRuleDisplayContext.getCPRuleTypeJSPContributor(type);
				%>

				<c:if test="<%= cpRuleTypeJSPContributor != null %>">

					<%
					cpRuleTypeJSPContributor.render(cpRuleId, request, response);
					%>

				</c:if>
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCPRule() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectType',
		function() {
			var A = AUI();

			var name = A.one('#<portlet:namespace />name').val();
			var type = A.one('#<portlet:namespace />type').val();

			var portletURL = new Liferay.PortletURL.createURL('<%= currentURLObj %>');

			portletURL.setParameter('name', name);
			portletURL.setParameter('type', type);

			window.location.replace(portletURL.toString());
		},
		['liferay-portlet-url']
	);
</aui:script>