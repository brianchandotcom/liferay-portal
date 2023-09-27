<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	<div class="portlet-configuration-body-content">
		<liferay-ui:tabs
			names="email-from,delivery-email,notifications-email"
			refresh="<%= false %>"
		>
			<liferay-ui:error key="emailDeliveryBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailDeliverySubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
			<liferay-ui:error key="emailNotificationsBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailNotificationsSubject" message="please-enter-a-valid-subject" />

			<liferay-ui:section>
				<clay:container-fluid>
					<div class="sheet">
						<div class="panel-group panel-group-flush">
							<aui:fieldset>
								<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" type="text" value="<%= emailFromName %>" />

								<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" type="text" value="<%= emailFromAddress %>" />
							</aui:fieldset>
						</div>
					</div>
				</clay:container-fluid>
			</liferay-ui:section>

			<liferay-ui:section>
				<clay:container-fluid>
					<div class="sheet">
						<div class="panel-group panel-group-flush">
							<liferay-frontend:email-notification-settings
								emailBodyLocalizedValuesMap="<%= reportsGroupServiceEmailConfiguration.emailDeliveryBody() %>"
								emailDefinitionTerms="<%= emailDefinitionTerms %>"
								emailEnabled="<%= enabled %>"
								emailParam="emailDelivery"
								emailSubjectLocalizedValuesMap="<%= reportsGroupServiceEmailConfiguration.emailDeliverySubject() %>"
							/>
						</div>
					</div>
				</clay:container-fluid>
			</liferay-ui:section>

			<liferay-ui:section>
				<clay:container-fluid>
					<div class="sheet">
						<div class="panel-group panel-group-flush">
							<liferay-frontend:email-notification-settings
								emailBodyLocalizedValuesMap="<%= reportsGroupServiceEmailConfiguration.emailNotificationsBody() %>"
								emailDefinitionTerms="<%= emailDefinitionTerms %>"
								emailEnabled="<%= enabled %>"
								emailParam="emailNotifications"
								emailSubjectLocalizedValuesMap="<%= reportsGroupServiceEmailConfiguration.emailNotificationsSubject() %>"
							/>
						</div>
					</div>
				</clay:container-fluid>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>