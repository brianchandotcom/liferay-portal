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
CommerceAccountAdminDisplayContext commerceAccountAdminDisplayContext = (CommerceAccountAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAccount commerceAccount = commerceAccountAdminDisplayContext.getCommerceAccount();
long commerceAccountId = commerceAccountAdminDisplayContext.getCommerceAccountId();
%>

<portlet:actionURL name="/commerce_account_admin/edit_commerce_account" var="editCommerceAccountActionURL" />

<aui:form action="<%= editCommerceAccountActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceAccount == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceAccountId" type="hidden" value="<%= commerceAccountId %>" />

	<aui:model-context bean="<%= commerceAccount %>" model="<%= CommerceAccount.class %>" />

	<liferay-ui:error exception="<%= CommerceAccountNameException.class %>" message="please-enter-a-valid-name" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<div class="row">
					<div class="col-md-6">
						<aui:input autoFocus="<%= true %>" name="name" />

						<aui:input name="email" />

						<aui:select disabled="<%= commerceAccount != null %>" name="type">

							<%
							for (int type : CommerceAccountConstants.ACCOUNT_TYPES) {
							%>

								<aui:option label="<%= CommerceAccountConstants.getAccountTypeLabel(type) %>" selected="<%= (commerceAccount != null) && (commerceAccount.getType() == type) %>" value="<%= type %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input label="vat-number" name="taxId" />

						<aui:input name="active" value="<%= (commerceAccount == null) ? true : commerceAccount.isActive() %>" />
					</div>

					<div class="col-md-5">
						<div align="middle">
							<c:if test="<%= commerceAccount != null %>">

								<%
								long logoId = commerceAccount.getLogoId();

								UserFileUploadsConfiguration userFileUploadsConfiguration = commerceAccountAdminDisplayContext.getUserFileUploadsConfiguration();
								%>

								<liferay-ui:logo-selector
									currentLogoURL='<%= themeDisplay.getPathImage() + "/organization_logo?img_id=" + logoId + "&t=" + WebServerServletTokenUtil.getToken(logoId) %>'
									defaultLogo="<%= logoId == 0 %>"
									defaultLogoURL='<%= themeDisplay.getPathImage() + "/organization_logo?img_id=0" %>'
									logoDisplaySelector=".organization-logo"
									maxFileSize="<%= userFileUploadsConfiguration.imageMaxSize() %>"
									tempImageFileName="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>"
								/>
							</c:if>
						</div>

						<c:if test="<%= commerceAccount != null %>">
							<aui:select label="default-billing" name="defaultBillingAddressId" showEmptyOption="<%= true %>">

								<%
								for (CommerceAddress billingCommerceAddress : commerceAccountAdminDisplayContext.getBillingCommerceAddresses()) {
								%>

									<aui:option label="<%= HtmlUtil.escape(billingCommerceAddress.getName()) %>" selected="<%= billingCommerceAddress.getCommerceAddressId() == commerceAccount.getDefaultBillingAddressId() %>" value="<%= billingCommerceAddress.getCommerceAddressId() %>" />

								<%
								}
								%>

							</aui:select>

							<aui:select label="default-shipping" name="defaultShippingAddressId" showEmptyOption="<%= true %>">

								<%
								for (CommerceAddress shippingCommerceAddress : commerceAccountAdminDisplayContext.getShippingCommerceAddresses()) {
								%>

									<aui:option label="<%= HtmlUtil.escape(shippingCommerceAddress.getName()) %>" selected="<%= shippingCommerceAddress.getCommerceAddressId() == commerceAccount.getDefaultShippingAddressId() %>" value="<%= shippingCommerceAddress.getCommerceAddressId() %>" />

								<%
								}
								%>

							</aui:select>
						</c:if>
					</div>
				</div>
			</aui:fieldset>

			<c:if test="<%= commerceAccountAdminDisplayContext.hasCustomAttributesAvailable() %>">
				<aui:fieldset collapsible="<%= true %>" label="custom-attribute">
					<liferay-expando:custom-attribute-list
						className="<%= AccountEntry.class.getName() %>"
						classPK="<%= commerceAccountId %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</c:if>

			<aui:fieldset>
				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" value="save" />

					<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</aui:fieldset-group>
	</div>
</aui:form>