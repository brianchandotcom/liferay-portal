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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
dlSettings = DLSettings.getInstance(themeDisplay.getSiteGroupId(), request.getParameterMap());
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<liferay-portlet:param name="serviceName" value="<%= DLConstants.SERVICE_NAME %>" />
	<liferay-portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:tabs
		names="email-from,document-added-email,document-updated-email"
		refresh="<%= false %>"
	>
		<liferay-ui:error key="emailFileEntryAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailFileEntryAddedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailFileEntryUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailFileEntryUpdatedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= dlSettings.getEmailFromName() %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= dlSettings.getEmailFromAddress() %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms" label="definition-of-terms">
				<dl>

					<%
					Map<String, String> emailDefinitionTerms = DLUtil.getEmailFromDefinitionTerms(renderRequest, dlSettings.getEmailFromAddress(), dlSettings.getEmailFromName());

					for (Map.Entry<String, String> entry : emailDefinitionTerms.entrySet()) {
					%>

						<dt>
							<%= entry.getKey() %>
						</dt>
						<dd>
							<%= entry.getValue() %>
						</dd>

					<%
					}
					%>

				</dl>
			</aui:fieldset>
		</liferay-ui:section>

		<%
		Map<String, String> emailDefinitionTerms = DLUtil.getEmailDefinitionTerms(renderRequest, dlSettings.getEmailFromAddress(), dlSettings.getEmailFromName());
		%>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= dlSettings.getEmailFileEntryAddedBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= dlSettings.isEmailFileEntryAddedEnabled() %>"
				emailParam="emailFileEntryAdded"
				emailSubject="<%= dlSettings.getEmailFileEntryAddedSubjectXml() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= dlSettings.getEmailFileEntryUpdatedBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= dlSettings.isEmailFileEntryUpdatedEnabled() %>"
				emailParam="emailFileEntryUpdated"
				emailSubject="<%= dlSettings.getEmailFileEntryUpdatedSubjectXml() %>"
			/>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>