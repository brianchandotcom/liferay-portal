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
int maxRestartAttempts = PrefsParamUtil.getInteger(portletPreferences, request, "maxRestartAttempts");

String notificationEmailBody = PrefsParamUtil.getString(portletPreferences, request, "notificationEmailBody", ContentUtil.get(PortletPropsValues.class.getClassLoader(), PortletPropsValues.SPI_NOTIFICATION_EMAIL_BODY));
String notificationEmailFromAddress = PrefsParamUtil.getString(portletPreferences, request, "notificationEmailFromAddress", PortletPropsValues.SPI_NOTIFICATION_EMAIL_FROM_ADDRESS);
String notificationEmailFromName = PrefsParamUtil.getString(portletPreferences, request, "notificationEmailFromName", PortletPropsValues.SPI_NOTIFICATION_EMAIL_FROM_NAME);
String notificationEmailSubject = PrefsParamUtil.getString(portletPreferences, request, "notificationEmailSubject", ContentUtil.get(PortletPropsValues.class.getClassLoader(), PortletPropsValues.SPI_NOTIFICATION_EMAIL_SUBJECT));
String notificationRecipients = PrefsParamUtil.getString(portletPreferences, request, "notificationRecipients", StringPool.BLANK);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<h2>
		<liferay-ui:message key="recovery-options" />
	</h2>

	<liferay-ui:error key="maxRestartAttempts" message="please-enter-a-valid-integer-for-max-restart-attempts" />

	<liferay-ui:panel
		collapsible="<%= true %>"
		extended="<%= true %>"
		persistState="<%= true %>"
		title="notification-options"
	>
		<aui:fieldset>
			<aui:input cssClass="lfr-input-text-container" helpMessage="notification-email-from-address-help" label="notification-email-from-address" name="preferences--notificationEmailFromAddress--" style="min-width: 100px;" value="<%= notificationEmailFromAddress %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="notification-email-from-name-help" label="notification-email-from-name" name="preferences--notificationEmailFromName--" style="min-width: 100px;" value="<%= notificationEmailFromName %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="notification-recipients-help" label="notification-recipients" name="preferences--notificationRecipients--" style="min-width: 400px;" value="<%= notificationRecipients %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="notification-email-subject-help" label="notification-email-subject" name="preferences--notificationEmailSubject--" style="min-width: 400px;" value="<%= notificationEmailSubject %>" />

			<aui:input cssClass="lfr-textarea-container" helpMessage="notification-email-body-help" label="notification-email-body" name="preferences--notificationEmailBody--" style="min-height: 100px; min-width: 400px;" type="textarea" value="<%= notificationEmailBody %>" />
		</aui:fieldset>
	</liferay-ui:panel>

	<liferay-ui:panel
		collapsible="<%= true %>"
		extended="<%= true %>"
		persistState="<%= true %>"
		title="restart-options"
	>
		<aui:fieldset>
			<aui:input cssClass="lfr-input-text-container" helpMessage="maximum-restart-attempts-help" label="maximum-restart-attempts" name="preferences--maxRestartAttempts--" value="<%= maxRestartAttempts %>">
				<aui:validator name="min">"0"</aui:validator>
			</aui:input>
		</aui:fieldset>
	</liferay-ui:panel>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>