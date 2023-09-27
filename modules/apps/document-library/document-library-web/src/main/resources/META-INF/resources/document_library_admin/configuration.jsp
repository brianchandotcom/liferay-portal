<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
DLGroupServiceSettings dlGroupServiceSettings = dlRequestHelper.getDLGroupServiceSettings();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<liferay-portlet:param name="serviceName" value="<%= DLConstants.SERVICE_NAME %>" />
	<liferay-portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:tabs
			names="email-from,document-added-email,document-updated-email,document-needs-review-email,document-expired-email"
			refresh="<%= false %>"
		>
			<liferay-ui:error key="emailFileEntryAddedBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailFileEntryAddedSubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailFileEntryUpdatedBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailFileEntryUpdatedSubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

			<liferay-ui:section>
				<liferay-frontend:fieldset>
					<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= dlGroupServiceSettings.getEmailFromName() %>" />

					<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= dlGroupServiceSettings.getEmailFromAddress() %>" />
				</liferay-frontend:fieldset>

				<liferay-frontend:fieldset
					collapsible="<%= true %>"
					label="definition-of-terms"
				>
					<dl>

						<%
						Map<String, String> emailDefinitionTerms = DLUtil.getEmailFromDefinitionTerms(renderRequest, dlGroupServiceSettings.getEmailFromAddress(), dlGroupServiceSettings.getEmailFromName());

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
				</liferay-frontend:fieldset>
			</liferay-ui:section>

			<%
			Map<String, String> emailDefinitionTerms = DLUtil.getEmailDefinitionTerms(renderRequest, dlGroupServiceSettings.getEmailFromAddress(), dlGroupServiceSettings.getEmailFromName());
			%>

			<liferay-ui:section>
				<liferay-frontend:email-notification-settings
					emailBody="<%= dlGroupServiceSettings.getEmailFileEntryAddedBodyXml() %>"
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled="<%= dlGroupServiceSettings.isEmailFileEntryAddedEnabled() %>"
					emailParam="emailFileEntryAdded"
					emailSubject="<%= dlGroupServiceSettings.getEmailFileEntryAddedSubjectXml() %>"
				/>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:email-notification-settings
					emailBody="<%= dlGroupServiceSettings.getEmailFileEntryUpdatedBodyXml() %>"
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled="<%= dlGroupServiceSettings.isEmailFileEntryUpdatedEnabled() %>"
					emailParam="emailFileEntryUpdated"
					emailSubject="<%= dlGroupServiceSettings.getEmailFileEntryUpdatedSubjectXml() %>"
				/>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:email-notification-settings
					emailBody="<%= dlGroupServiceSettings.getEmailFileEntryReviewBodyXml() %>"
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled="<%= dlGroupServiceSettings.isEmailFileEntryReviewEnabled() %>"
					emailParam="emailFileEntryReview"
					emailSubject="<%= dlGroupServiceSettings.getEmailFileEntryReviewSubjectXml() %>"
				/>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:email-notification-settings
					emailBody="<%= dlGroupServiceSettings.getEmailFileEntryExpiredBodyXml() %>"
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled="<%= dlGroupServiceSettings.isEmailFileEntryExpiredEnabled() %>"
					emailParam="emailFileEntryExpired"
					emailSubject="<%= dlGroupServiceSettings.getEmailFileEntryExpiredSubjectXml() %>"
				/>
			</liferay-ui:section> </liferay-ui:tabs>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>