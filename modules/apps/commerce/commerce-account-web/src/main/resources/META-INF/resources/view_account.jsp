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
CommerceAccountDisplayContext commerceAccountDisplayContext = (CommerceAccountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAccount commerceAccount = commerceAccountDisplayContext.getCurrentCommerceAccount();
CommerceAddress billingAddress = commerceAccountDisplayContext.getDefaultBillingCommerceAddress();
CommerceAddress shippingAddress = commerceAccountDisplayContext.getDefaultShippingCommerceAddress();
PortletURL portletURL = commerceAccountDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "viewCommerceAccount");
%>

<portlet:renderURL var="editCommerceAccountURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceAccount" />
	<portlet:param name="commerceAccountId" value="<%= String.valueOf(commerceAccount.getCommerceAccountId()) %>" />
	<portlet:param name='<%= PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL" %>' value="<%= portletURL.toString() %>" />
</portlet:renderURL>

<div class="account-management">
	<section class="panel panel-secondary">
		<div class="panel-body">
			<div class="row">
				<div class="col-auto">
					<img alt="avatar" class="account-management__thumbnail img-fluid rounded-circle" src="<%= commerceAccountDisplayContext.getLogo(commerceAccount) %>" />
				</div>

				<div class="col d-flex flex-col justify-content-center">
					<span class="account-management__name">
						<%= HtmlUtil.escape(commerceAccount.getName()) %>
					</span>
					<span class="account-management__email">
						<%= HtmlUtil.escape(commerceAccount.getEmail()) %>
					</span>
				</div>

				<c:if test="<%= (billingAddress != null) || (shippingAddress != null) %>">
					<div class="align-items-center col d-flex">
						<c:if test="<%= billingAddress != null %>">
							<div class="account-management__info-wrapper">
								<span class="account-management__label">
									<liferay-ui:message key="billing-address" />
								</span>
								<span class="account-management__value">
									<%= HtmlUtil.escape(billingAddress.getStreet1()) %><br />
									<%= HtmlUtil.escape(billingAddress.getCity() + StringPool.SPACE + billingAddress.getZip()) %>
								</span>
							</div>
						</c:if>

						<c:if test="<%= shippingAddress != null %>">
							<div class="account-management__info-wrapper">
								<span class="account-management__label">
									<liferay-ui:message key="shipping-address" />
								</span>
								<span class="account-management__value">
									<%= HtmlUtil.escape(shippingAddress.getStreet1()) %><br />
									<%= HtmlUtil.escape(shippingAddress.getCity() + StringPool.SPACE + shippingAddress.getZip()) %>
								</span>
							</div>
						</c:if>
					</div>
				</c:if>

				<c:if test="<%= commerceAccountDisplayContext.hasCommerceAccountModelPermissions(commerceAccount.getCommerceAccountId(), ActionKeys.UPDATE) %>">
					<div class="align-items-center col-auto d-flex">
						<div class="account-management__action">
							<aui:button cssClass="btn-lg btn-secondary" href="<%= editCommerceAccountURL %>" value='<%= LanguageUtil.get(request, "edit-account") %>' />
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</section>

	<section class="mb-5 mt-1 panel panel-secondary">
		<div class="panel-body">

			<%
			String taxId = commerceAccount.getTaxId();
			%>

			<c:if test='<%= taxId != "" %>'>
				<div class="account-management__info-wrapper">
					<span class="account-management__label">
						<liferay-ui:message key="vat-number" />
					</span>
					<span class="account-management__value">
						<%= taxId %>
					</span>
				</div>
			</c:if>

			<div class="account-management__info-wrapper">
				<span class="account-management__label">
					<liferay-ui:message key="customer-id" />
				</span>
				<span class="account-management__value">
					<%= commerceAccount.getCommerceAccountId() %>
				</span>
			</div>
		</div>
	</section>

	<liferay-frontend:screen-navigation
		containerWrapperCssClass="mt-1"
		context="<%= commerceAccount %>"
		key="<%= CommerceAccountScreenNavigationConstants.SCREEN_NAVIGATION_KEY %>"
		portletURL="<%= portletURL %>"
	/>
</div>