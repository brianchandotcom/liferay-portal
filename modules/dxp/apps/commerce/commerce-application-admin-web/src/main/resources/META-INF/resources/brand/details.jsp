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
CommerceApplicationAdminDisplayContext commerceApplicationAdminDisplayContext = (CommerceApplicationAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceApplicationBrand commerceApplicationBrand = commerceApplicationAdminDisplayContext.getCommerceApplicationBrand();
%>

<portlet:actionURL name="editCommerceApplicationBrand" var="editCommerceApplicationBrandActionURL" />

<div class="container-fluid container-fluid-max-xl entry-body">
	<aui:form action="<%= editCommerceApplicationBrandActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceApplicationBrand == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceApplicationBrandId" type="hidden" value="<%= commerceApplicationAdminDisplayContext.getCommerceApplicationBrandId() %>" />

		<aui:model-context bean="<%= commerceApplicationBrand %>" model="<%= CommerceApplicationBrand.class %>" />

		<div class="lfr-form-content">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<div class="row">
						<div class="col-md-6">
							<aui:input autoFocus="<%= true %>" name="name" />
						</div>

						<div class="col-md-5">
							<div align="middle">
								<c:if test="<%= commerceApplicationBrand != null %>">

									<%
									long logoId = commerceApplicationBrand.getLogoId();

									UserFileUploadsConfiguration userFileUploadsConfiguration = commerceApplicationAdminDisplayContext.getUserFileUploadsConfiguration();
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
						</div>
					</div>
				</aui:fieldset>

				<aui:fieldset>
					<aui:button-row>
						<aui:button cssClass="btn-lg" type="submit" value="save" />

						<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
					</aui:button-row>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</aui:form>
</div>