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

<portlet:actionURL name="/portal_instances/add_instance" var="addInstanceURL" />

<div class="add-instance-alert-container"></div>

<clay:container-fluid>
	<liferay-frontend:edit-form
		action="<%= addInstanceURL %>"
		method="post"
		name="fm"
		onSubmit="event.preventDefault();"
		validateOnBlur="<%= false %>"
	>
		<div class="add-instance-content">
			<div class="px-4 py-2">
				<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />
				<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />
				<liferay-ui:error exception="<%= CompanyWebIdException.class %>" message="please-enter-a-valid-web-id" />

				<aui:model-context model="<%= Company.class %>" />

				<aui:input name="webId">
					<aui:validator name="required" />
				</aui:input>

				<aui:input fieldParam="virtualHostname" label="virtual-host" model="<%= VirtualHost.class %>" name="hostname" />

				<aui:input label="mail-domain" name="mx" />

				<aui:input name="maxUsers" />

				<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="active" type="toggle-switch" value="<%= true %>" />

				<%
				SiteInitializerRegistry siteInitializerRegistry = (SiteInitializerRegistry)request.getAttribute(PortalInstancesWebKeys.SITE_INITIALIZER_REGISTRY);

				List<SiteInitializer> siteInitializers = siteInitializerRegistry.getSiteInitializers(company.getCompanyId(), true);
				%>

				<c:if test="<%= !siteInitializers.isEmpty() %>">
					<aui:select label="virtual-instance-initializer" name="siteInitializerKey" showEmptyOption="<%= true %>">

						<%
						for (SiteInitializer siteInitializer : siteInitializers) {
						%>

							<aui:option label="<%= siteInitializer.getName(locale) %>" value="<%= siteInitializer.getKey() %>" />

						<%
						}
						%>

					</aui:select>
				</c:if>

				<liferay-frontend:edit-form-footer>
					<liferay-frontend:edit-form-buttons
						submitLabel="add"
					/>
				</liferay-frontend:edit-form-footer>
			</div>
		</div>

		<div class="add-instance-loading align-items-center d-none flex-column justify-content-center">
			<span aria-hidden="true" class="loading-animation mb-4"></span>

			<p class="text-3 text-center text-secondary"><liferay-ui:message key="the-creation-of-the-site-may-take-some-time-.closing-the-window-will-not-cancel-the-process" /></p>
		</div>
	</liferay-frontend:edit-form>
</clay:container-fluid>

<liferay-frontend:component
	module="js/AddInstance"
/>