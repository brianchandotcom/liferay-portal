<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceDiscountRuleType> commerceDiscountRuleTypes = commerceDiscountDisplayContext.getCommerceDiscountRuleTypes();
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-discount-rule") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input bean="<%= commerceDiscountDisplayContext.getCommerceDiscountRule() %>" model="<%= CommerceDiscountRule.class %>" name="name" required="<%= true %>" />

		<aui:select label="rule-type" name="type" required="<%= true %>">

			<%
			for (CommerceDiscountRuleType commerceDiscountRuleType : commerceDiscountRuleTypes) {
			%>

				<aui:option label="<%= commerceDiscountRuleType.getLabel(locale) %>" value="<%= commerceDiscountRuleType.getKey() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"commerceDiscountId", commerceDiscountDisplayContext.getCommerceDiscountId()
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
		module="{addCommerceDiscountRule} from commerce-pricing-web"
	/>
</commerce-ui:modal-content>