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

Company selCompany = (Company)request.getAttribute(WebKeys.SEL_COMPANY);

long companyId = BeanParamUtil.getLong(selCompany, request, "companyId");

VirtualHost virtualHost = null;

try {
	virtualHost = VirtualHostLocalServiceUtil.getVirtualHost(companyId, 0);
}
catch (Exception e) {
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((selCompany == null) ? LanguageUtil.get(request, "new-instance") : HtmlUtil.escape(selCompany.getWebId()));
%>

<portlet:actionURL name="/portal_instances/edit_instance" var="editInstanceURL" />

<aui:form action="<%= editInstanceURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCompany();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="companyId" type="hidden" value="<%= companyId %>" />

	<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />
	<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />
	<liferay-ui:error exception="<%= CompanyWebIdException.class %>" message="please-enter-a-valid-web-id" />

	<aui:model-context bean="<%= selCompany %>" model="<%= Company.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= selCompany != null %>">
					<aui:input name="id" type="resource" value="<%= String.valueOf(companyId) %>" />

					<aui:input name="web-id" type="resource" value="<%= selCompany.getWebId() %>" />
				</c:when>
				<c:otherwise>
					<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="webId">
						<aui:validator name="required" />
					</aui:input>
				</c:otherwise>
			</c:choose>

			<aui:input bean="<%= virtualHost %>" fieldParam="virtualHostname" label="virtual-host" model="<%= VirtualHost.class %>" name="hostname" />

			<aui:input label="mail-domain" name="mx" />

			<aui:input name="maxUsers" />

			<aui:input disabled="<%= (selCompany != null) && (selCompany.getCompanyId() == PortalInstancesLocalServiceUtil.getDefaultCompanyId()) %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="active" type="toggle-switch" value="<%= (selCompany != null) ? selCompany.isActive() : true %>" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCompany() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var cmd = form.querySelector(
				'#<portlet:namespace /><%= Constants.CMD %>'
			);

			if (cmd) {
				cmd.setAttribute(
					'value',
					'<%= (selCompany == null) ? Constants.ADD : Constants.UPDATE %>'
				);
			}

			submitForm(form);
		}
	}
</aui:script>