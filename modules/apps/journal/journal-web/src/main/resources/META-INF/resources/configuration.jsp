<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", journalGroupServiceConfiguration.emailFromAddress());
String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", journalGroupServiceConfiguration.emailFromName());
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= JournalConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	fluid="<%= true %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
	<liferay-ui:error key="emailArticleAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleApprovalDeniedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleApprovalDeniedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleApprovalGrantedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleApprovalGrantedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleApprovalRequestedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleApprovalRequestedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleExpiredBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleExpiredSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleReviewBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleReviewSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailArticleUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailArticleUpdatedSubject" message="please-enter-a-valid-subject" />

	<liferay-frontend:edit-form-body>
		<div class="sheet-row">
			<clay:tabs
				tabsItems="<%= journalDisplayContext.getConfigurationTabsItems() %>"
			>
				<clay:tabs-panel>
					<liferay-frontend:fieldset>
						<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" type="text" value="<%= emailFromName %>" />

						<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" type="text" value="<%= emailFromAddress %>" />
					</liferay-frontend:fieldset>
				</clay:tabs-panel>

				<%
				Map<String, String> emailDefinitionTerms = JournalUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName);
				%>

				<clay:tabs-panel>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleAddedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= journalGroupServiceConfiguration.emailArticleAddedEnabled() %>"
						emailParam="emailArticleAdded"
						emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleAddedSubject() %>"
					/>
				</clay:tabs-panel>

				<clay:tabs-panel>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleExpiredBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= journalGroupServiceConfiguration.emailArticleExpiredEnabled() %>"
						emailParam="emailArticleExpired"
						emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleExpiredSubject() %>"
					/>
				</clay:tabs-panel>

				<clay:tabs-panel>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleMovedFromFolderBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= journalGroupServiceConfiguration.emailArticleMovedFromFolderEnabled() %>"
						emailParam="emailArticleMovedFromFolder"
						emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleMovedFromFolderSubject() %>"
					/>
				</clay:tabs-panel>

				<clay:tabs-panel>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleMovedToFolderBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= journalGroupServiceConfiguration.emailArticleMovedToFolderEnabled() %>"
						emailParam="emailArticleMovedToFolder"
						emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleMovedToFolderSubject() %>"
					/>
				</clay:tabs-panel>

				<clay:tabs-panel>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleReviewBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= journalGroupServiceConfiguration.emailArticleReviewEnabled() %>"
						emailParam="emailArticleReview"
						emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleReviewSubject() %>"
					/>
				</clay:tabs-panel>

				<clay:tabs-panel>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleUpdatedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= journalGroupServiceConfiguration.emailArticleUpdatedEnabled() %>"
						emailParam="emailArticleUpdated"
						emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleUpdatedSubject() %>"
					/>
				</clay:tabs-panel>

				<c:if test="<%= JournalUtil.hasWorkflowDefinitionsLinks(themeDisplay) %>">
					<clay:tabs-panel>
						<liferay-frontend:email-notification-settings
							emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalDeniedBody() %>"
							emailDefinitionTerms="<%= emailDefinitionTerms %>"
							emailEnabled="<%= journalGroupServiceConfiguration.emailArticleApprovalDeniedEnabled() %>"
							emailParam="emailArticleApprovalDenied"
							emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalDeniedSubject() %>"
						/>
					</clay:tabs-panel>

					<clay:tabs-panel>
						<liferay-frontend:email-notification-settings
							emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalGrantedBody() %>"
							emailDefinitionTerms="<%= emailDefinitionTerms %>"
							emailEnabled="<%= journalGroupServiceConfiguration.emailArticleApprovalGrantedEnabled() %>"
							emailParam="emailArticleApprovalGranted"
							emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalGrantedSubject() %>"
						/>
					</clay:tabs-panel>

					<clay:tabs-panel>
						<liferay-frontend:email-notification-settings
							emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalRequestedBody() %>"
							emailDefinitionTerms='<%= JournalUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName, "requested") %>'
							emailEnabled="<%= journalGroupServiceConfiguration.emailArticleApprovalRequestedEnabled() %>"
							emailParam="emailArticleApprovalRequested"
							emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalRequestedSubject() %>"
						/>
					</clay:tabs-panel>
				</c:if>
			</clay:tabs>
		</div>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>