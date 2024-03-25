<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL editCommerceDiscountPortletURL = commerceDiscountDisplayContext.getEditCommerceDiscountRenderURL();
%>

<portlet:actionURL name="/commerce_discount/edit_commerce_discount" var="editCommerceDiscountActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-discount") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>' useNamespace="<%= false %>">
		<aui:input bean="<%= commerceDiscountDisplayContext.getCommerceDiscount() %>" label="name" model="<%= CommerceDiscount.class %>" name="title" required="<%= true %>" />

		<aui:select label="type" name="commerceDiscountType" required="<%= true %>">

			<%
			for (String commerceDiscountType : CommerceDiscountConstants.TYPES) {
			%>

				<aui:option label="<%= commerceDiscountType %>" value="<%= commerceDiscountDisplayContext.getUsePercentage(commerceDiscountType) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="apply-to" name="commerceDiscountTarget" required="<%= true %>">

			<%
			for (CommerceDiscountTarget commerceDiscountTarget : commerceDiscountDisplayContext.getCommerceDiscountTargets()) {
			%>

				<aui:option label="<%= commerceDiscountTarget.getLabel(locale) %>" value="<%= commerceDiscountTarget.getKey() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"editCommerceDiscountRenderURL", String.valueOf(editCommerceDiscountPortletURL)
			).put(
				"level", CommerceDiscountConstants.LEVEL_L1
			).put(
				"limitationType", CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
		module="{addCommerceDiscount} from commerce-pricing-web"
	/>
</commerce-ui:modal-content>