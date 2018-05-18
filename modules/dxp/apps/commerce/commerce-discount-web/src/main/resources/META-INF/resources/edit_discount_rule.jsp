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
CommerceDiscountRuleDisplayContext commerceDiscountRuleDisplayContext = (CommerceDiscountRuleDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceDiscount commerceDiscount = commerceDiscountRuleDisplayContext.getCommerceDiscount();
long commerceDiscountId = commerceDiscountRuleDisplayContext.getCommerceDiscountId();
CommerceDiscountRule commerceDiscountRule = commerceDiscountRuleDisplayContext.getCommerceDiscountRule();
long commerceDiscountRuleId = commerceDiscountRuleDisplayContext.getCommerceDiscountRuleId();
List<CommerceDiscountRuleType> commerceDiscountRuleTypes = commerceDiscountRuleDisplayContext.getCommerceDiscountRuleTypes();

String type = BeanParamUtil.getString(commerceDiscountRule, request, "type");

PortletURL portletURL = commerceDiscountRuleDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCommerceDiscountRule");

String title = LanguageUtil.get(request, (commerceDiscountRule == null) ? "add-discount-rule" : "edit-discount-rule");

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "discount"), String.valueOf(renderResponse.createRenderURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, commerceDiscount.getTitle(), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, CommerceDiscountScreenNavigationConstants.CATEGORY_KEY_COMMERCE_DISCOUNT_RULES), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceDiscountRule" var="editCommerceDiscountRuleActionURL" />

<aui:form action="<%= editCommerceDiscountRuleActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceDiscountRule();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceDiscountRule == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="addTypeSettings" type="hidden" />
	<aui:input name="commerceDiscountId" type="hidden" value="<%= commerceDiscountId %>" />
	<aui:input name="commerceDiscountRuleId" type="hidden" value="<%= commerceDiscountRuleId %>" />
	<aui:input name="deleteTypeSettings" type="hidden" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceDiscountRuleTypeException.class %>" message="please-select-a-valid-discount-rule-type" />

		<aui:model-context bean="<%= commerceDiscountRule %>" model="<%= CommerceDiscountRule.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:select name="type" onChange='<%= renderResponse.getNamespace() + "selectType();" %>' showEmptyOption="<%= true %>">

					<%
					for (CommerceDiscountRuleType commerceDiscountRuleType : commerceDiscountRuleTypes) {
						String commerceDiscountRuleTypeKey = commerceDiscountRuleType.getKey();
					%>

						<aui:option label="<%= commerceDiscountRuleType.getLabel(locale) %>" selected="<%= (commerceDiscountRule != null) && commerceDiscountRuleTypeKey.equals(type) %>" value="<%= commerceDiscountRuleTypeKey %>" />

					<%
					}
					%>

				</aui:select>

				<%
				CommerceDiscountRuleTypeJSPContributor commerceDiscountRuleTypeJSPContributor = commerceDiscountRuleDisplayContext.getCommerceDiscountRuleTypeJSPContributor(type);
				%>

				<c:if test="<%= commerceDiscountRuleTypeJSPContributor != null %>">

					<%
					commerceDiscountRuleTypeJSPContributor.render(commerceDiscountId, commerceDiscountRuleId, request, response);
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
	function <portlet:namespace />saveCommerceDiscountRule() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectType',
		function() {
			var A = AUI();

			var type = A.one('#<portlet:namespace />type').val();

			var portletURL = new Liferay.PortletURL.createURL('<%= currentURLObj %>');

			portletURL.setParameter('type', type);

			window.location.replace(portletURL.toString());
		},
		['liferay-portlet-url']
	);
</aui:script>