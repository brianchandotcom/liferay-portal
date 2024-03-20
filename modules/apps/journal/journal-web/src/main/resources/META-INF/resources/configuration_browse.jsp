<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalConfigurationDisplayContext journalConfigurationDisplayContext = new JournalConfigurationDisplayContext(request, journalGroupServiceConfiguration, renderRequest, renderResponse);

Map<String, String> emailDefinitionTerms = journalConfigurationDisplayContext.getEmailDefinitionTerms();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(journalConfigurationDisplayContext.getBackURL());
portletDisplay.setURLBackTitle("web-content");
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="settings" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= journalConfigurationDisplayContext.getSettingsVerticalNavItemList() %>"
			/>

			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="notifications" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= journalConfigurationDisplayContext.getNotificationsVerticalNavItemList() %>"
			/>
		</clay:col>

		<clay:col
			lg="9"
		>
			<h2>
				<%= journalConfigurationDisplayContext.getTitle() %>
			</h2>

			<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
				<portlet:param name="navigation" value="<%= journalConfigurationDisplayContext.getNavigation() %>" />
				<portlet:param name="serviceName" value="<%= JournalConstants.SERVICE_NAME %>" />
				<portlet:param name="settingsScope" value="group" />
			</liferay-portlet:actionURL>

			<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
				<clay:sheet
					cssClass="c-mb-4 c-mt-4 c-p-0"
					size="full"
				>
					<h3 class="c-pl-4 c-pr-4 c-pt-4 sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= journalConfigurationDisplayContext.getSubtitle() %>
							</clay:content-col>
						</clay:content-row>
					</h3>

					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
					<aui:input name="redirect" type="hidden" value="<%= journalConfigurationDisplayContext.getRedirect() %>" />

					<liferay-ui:error embed="<%= false %>" key="emailArticleAddedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleAddedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleApprovalDeniedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleApprovalDeniedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleApprovalGrantedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleApprovalGrantedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleApprovalRequestedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleApprovalRequestedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleExpiredBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleExpiredSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleMovedFromFolderBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleMovedFromFolderSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleMovedToFolderBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleMovedToFolderSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleReviewBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleReviewSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleUpdatedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailArticleUpdatedSubject" message="please-enter-a-valid-subject" />

					<c:choose>
						<c:when test='<%= Objects.equals(journalConfigurationDisplayContext.getNavigation(), "structures") %>'>
							<div>
								<div class="inline-item my-5 p-5 w-100">
									<span aria-hidden="true" class="loading-animation"></span>
								</div>

								<react:component
									module="{HighlightedDDMStructuresConfiguration} from journal-web"
									props='<%=
										HashMapBuilder.<String, Object>put(
											"ddmStructures", journalConfigurationDisplayContext.getHighlightedDDMStructuresJSONArray()
										).put(
											"selectDDMStructureURL", journalConfigurationDisplayContext.getSelectDDMStructureURL()
										).build()
									%>'
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-added") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleAddedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleAddedEnabled() %>"
									emailParam="emailArticleAdded"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleAddedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-expired") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleExpiredBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleExpiredEnabled() %>"
									emailParam="emailArticleExpired"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleExpiredSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-moved-from-folder") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleMovedFromFolderBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleMovedFromFolderEnabled() %>"
									emailParam="emailArticleMovedFromFolder"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleMovedFromFolderSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-moved-to-folder") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleMovedToFolderBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleMovedToFolderEnabled() %>"
									emailParam="emailArticleMovedToFolder"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleMovedToFolderSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-review") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleReviewBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleReviewEnabled() %>"
									emailParam="emailArticleReview"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleReviewSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-updated") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleUpdatedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleUpdatedEnabled() %>"
									emailParam="emailArticleUpdated"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleUpdatedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= JournalUtil.hasWorkflowDefinitionsLinks(themeDisplay) && Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-approval-denied") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalDeniedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleApprovalDeniedEnabled() %>"
									emailParam="emailArticleApprovalDenied"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalDeniedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= JournalUtil.hasWorkflowDefinitionsLinks(themeDisplay) && Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-approval-granted") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalGrantedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleApprovalGrantedEnabled() %>"
									emailParam="emailArticleApprovalGranted"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalGrantedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= JournalUtil.hasWorkflowDefinitionsLinks(themeDisplay) && Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-approval-requested") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalRequestedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= journalGroupServiceConfiguration.emailArticleApprovalRequestedEnabled() %>"
									emailParam="emailArticleApprovalRequested"
									emailSubjectLocalizedValuesMap="<%= journalGroupServiceConfiguration.emailArticleApprovalRequestedSubject() %>"
								/>
							</div>
						</c:when>
						<c:otherwise>
							<div class="c-px-4">
								<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" type="text" value="<%= journalConfigurationDisplayContext.getEmailFromName() %>">
									<aui:validator errorMessage="please-enter-a-valid-name" name="required" />
								</aui:input>

								<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" type="text" value="<%= journalConfigurationDisplayContext.getEmailFromAddress() %>">
									<aui:validator errorMessage="please-enter-a-valid-email-address" name="required" />
									<aui:validator name="email" />
								</aui:input>
							</div>
						</c:otherwise>
					</c:choose>
				</clay:sheet>

				<aui:button-row>
					<clay:button
						cssClass="c-mr-2"
						label="save"
						type="submit"
					/>

					<clay:link
						displayType="secondary"
						href="<%= journalConfigurationDisplayContext.getBackURL() %>"
						label="cancel"
						type="button"
					/>
				</aui:button-row>
			</aui:form>
		</clay:col>
	</clay:row>
</clay:container-fluid>