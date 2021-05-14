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
CommerceOrganizationDisplayContext commerceOrganizationDisplayContext = (CommerceOrganizationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationDisplayContext.getOrganization();
%>

<portlet:actionURL name="/commerce_organization/edit_commerce_organization" var="editCommerceOrganizationActionURL" />

<div class="account-management">
	<aui:form action="<%= editCommerceOrganizationActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (organization == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="organizationId" type="hidden" value="<%= (organization == null) ? 0 : organization.getOrganizationId() %>" />

		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="details"
		/>

		<aui:model-context bean="<%= organization %>" model="<%= Organization.class %>" />

		<div class="container row">
			<div class="col-lg-4 account-management__thumbnail-container">
				<aui:fieldset>
					<c:if test="<%= organization != null %>">

						<%
						long logoId = organization.getLogoId();

						UserFileUploadsConfiguration userFileUploadsConfiguration = commerceOrganizationDisplayContext.getUserFileUploadsConfiguration();
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
				</aui:fieldset>
			</div>

			<div class="col-lg-4">
				<aui:input name="name" />
			</div>
		</div>

		<div class="commerce-cta is-visible">
			<c:if test="<%= Validator.isNotNull(backURL) %>">
				<aui:button cssClass="btn-lg mr-3" href="<%= backURL %>" value="cancel" />
			</c:if>

			<aui:button cssClass="btn-lg" primary="<%= true %>" type="submit" />
		</div>
	</aui:form>
</div>