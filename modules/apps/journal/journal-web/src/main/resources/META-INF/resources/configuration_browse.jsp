<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalConfigurationDisplayContext journalConfigurationDisplayContext = new JournalConfigurationDisplayContext(request, journalGroupServiceConfiguration, renderRequest, renderResponse);

String backURL = journalConfigurationDisplayContext.getBackURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

Map<String, String> emailDefinitionTerms = journalConfigurationDisplayContext.getEmailDefinitionTerms();
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<p class="small text-uppercase">
				<liferay-ui:message key="notifications" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= journalConfigurationDisplayContext.getVerticalNavItemList() %>"
			/>
		</clay:col>

		<clay:col
			lg="9"
		>
			<h1>
				<%= journalConfigurationDisplayContext.getTitle() %>
			</h1>

			<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
				<portlet:param name="serviceName" value="<%= JournalConstants.SERVICE_NAME %>" />
				<portlet:param name="settingsScope" value="group" />
			</liferay-portlet:actionURL>

			<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
				<clay:sheet
					cssClass="c-mb-4 c-mt-4 c-p-0"
					size="full"
				>
					<h2 class="c-pl-4 c-pr-4 c-pt-4 sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<liferay-ui:message key="email" />
							</clay:content-col>
						</clay:content-row>
					</h2>

					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
					<aui:input name="redirect" type="hidden" value="<%= journalConfigurationDisplayContext.getRedirect() %>" />

					<c:choose>
						<c:when test='<%= Objects.equals(journalConfigurationDisplayContext.getNavigation(), "web-content-added") %>'>
							<div class="c-px-1">
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
							<div class="c-px-1">
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
							<div class="c-px-1">
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
							<div class="c-px-1">
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
							<div class="c-px-1">
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
							<div class="c-px-1">
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
							<div class="c-px-1">
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
							<div class="c-px-1">
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
							<div class="c-px-1">
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
					<aui:button type="submit" />

					<aui:button href="<%= backURL %>" type="cancel" />
				</aui:button-row>
			</aui:form>
		</clay:col>
	</clay:row>
</clay:container-fluid>