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
boolean enabled = true;
String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", reportsGroupServiceEmailConfiguration.emailFromName());
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", reportsGroupServiceEmailConfiguration.emailFromAddress());

Map<String, String> emailDefinitionTerms = EmailConfigurationUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= ReportsEngineConsoleConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs1Names = "email-from,delivery-email,notifications-email";
	%>

	<div class="portlet-configuration-body-content">
		<liferay-ui:tabs
			names="<%= tabs1Names %>"
			refresh="<%= false %>"
			type="tabs nav-tabs-default"
		>
			<liferay-ui:error key="emailDeliveryBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailDeliverySubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
			<liferay-ui:error key="emailNotificationsBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailNotificationsSubject" message="please-enter-a-valid-subject" />

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset>
							<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" type="text" value="<%= emailFromName %>" />

							<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" type="text" value="<%= emailFromAddress %>" />
						</aui:fieldset>
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<liferay-frontend:email-notification-settings
							emailBodyLocalizedValuesMap="<%= reportsGroupServiceEmailConfiguration.emailDeliveryBody() %>"
							emailDefinitionTerms="<%= emailDefinitionTerms %>"
							emailEnabled="<%= enabled %>"
							emailParam="emailDelivery"
							emailSubjectLocalizedValuesMap="<%= reportsGroupServiceEmailConfiguration.emailDeliverySubject() %>"
						/>
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<liferay-frontend:email-notification-settings
							emailBodyLocalizedValuesMap="<%= reportsGroupServiceEmailConfiguration.emailNotificationsBody() %>"
							emailDefinitionTerms="<%= emailDefinitionTerms %>"
							emailEnabled="<%= enabled %>"
							emailParam="emailNotifications"
							emailSubjectLocalizedValuesMap="<%= reportsGroupServiceEmailConfiguration.emailNotificationsSubject() %>"
						/>
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>