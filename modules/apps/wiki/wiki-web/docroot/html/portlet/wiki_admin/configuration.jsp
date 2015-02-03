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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
MailTemplatesHelper mailTemplatesHelper = new MailTemplatesHelper(wikiRequestHelper);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= WikiConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:tabs
		names="email-from,page-added-email,page-updated-email"
		refresh="<%= false %>"
	>
		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
		<liferay-ui:error key="emailPageAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailPageAddedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailPageUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailPageUpdatedSubject" message="please-enter-a-valid-subject" />

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= wikiSettings.getEmailFromName() %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= wikiSettings.getEmailFromAddress() %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms">
				<legend>
					<liferay-ui:message key="definition-of-terms" />
				</legend>

				<dl>

					<%
					Map<String, String> definitionTerms = mailTemplatesHelper.getEmailFromDefinitionTerms();

					for (Map.Entry<String, String> definitionTerm : definitionTerms.entrySet()) {
					%>

						<dt>
							<%= definitionTerm.getKey() %>
						</dt>
						<dd>
							<%= definitionTerm.getValue() %>
						</dd>

					<%
					}
					%>

				</dl>
			</aui:fieldset>
		</liferay-ui:section>

		<%
		Map<String, String> definitionTerms = mailTemplatesHelper.getEmailNotificationDefinitionTerms();
		%>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= wikiSettings.getEmailPageAddedBodyXml() %>"
				emailDefinitionTerms="<%= definitionTerms %>"
				emailEnabled="<%= wikiSettings.isEmailPageAddedEnabled() %>"
				emailParam="emailPageAdded"
				emailSubject="<%= wikiSettings.getEmailPageAddedSubjectXml() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= wikiSettings.getEmailPageUpdatedBodyXml() %>"
				emailDefinitionTerms="<%= definitionTerms %>"
				emailEnabled="<%= wikiSettings.isEmailPageUpdatedEnabled() %>"
				emailParam="emailPageUpdated"
				emailSubject="<%= wikiSettings.getEmailPageUpdatedSubjectXml() %>"
			/>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>