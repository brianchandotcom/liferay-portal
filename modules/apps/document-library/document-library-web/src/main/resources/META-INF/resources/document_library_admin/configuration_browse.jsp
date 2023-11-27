<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DLConfigurationDisplaycontext dlConfigurationDisplaycontext = new DLConfigurationDisplaycontext(request, renderRequest, renderResponse);
DLGroupServiceSettings dlGroupServiceSettings = DLGroupServiceSettings.getInstance(scopeGroupId, request.getParameterMap());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(dlConfigurationDisplaycontext.getBackURL());
portletDisplay.setURLBackTitle("documents-and-media");
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="notifications" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= dlConfigurationDisplaycontext.getNotificationsVerticalNavItemList() %>"
			/>
		</clay:col>

		<clay:col
			lg="9"
		>
			<h2>
				<%= dlConfigurationDisplaycontext.getTitle() %>
			</h2>

			<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
				<portlet:param name="navigation" value="<%= dlConfigurationDisplaycontext.getNavigation() %>" />
				<portlet:param name="serviceName" value="<%= DLConstants.SERVICE_NAME %>" />
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
								<%= dlConfigurationDisplaycontext.getSubtitle() %>
							</clay:content-col>
						</clay:content-row>
					</h3>

					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
					<aui:input name="redirect" type="hidden" value="<%= configurationActionURL %>" />

					<liferay-ui:error embed="<%= false %>" key="emailEntryAddedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />

					<%
					Map<String, String> emailDefinitionTerms = DLUtil.getEmailDefinitionTerms(renderRequest, dlGroupServiceSettings.getEmailFromAddress(), dlGroupServiceSettings.getEmailFromName());
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(dlConfigurationDisplaycontext.getNavigation(), "document-added-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= dlGroupServiceSettings.getEmailFileEntryAddedBodyXml() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= dlGroupServiceSettings.isEmailFileEntryAddedEnabled() %>"
									emailParam="emailFileEntryAdded"
									emailSubject="<%= dlGroupServiceSettings.getEmailFileEntryAddedSubjectXml() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(dlConfigurationDisplaycontext.getNavigation(), "document-updated-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= dlGroupServiceSettings.getEmailFileEntryUpdatedBodyXml() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= dlGroupServiceSettings.isEmailFileEntryUpdatedEnabled() %>"
									emailParam="emailFileEntryUpdated"
									emailSubject="<%= dlGroupServiceSettings.getEmailFileEntryUpdatedSubjectXml() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(dlConfigurationDisplaycontext.getNavigation(), "document-needs-review-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= dlGroupServiceSettings.getEmailFileEntryReviewBodyXml() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= dlGroupServiceSettings.isEmailFileEntryReviewEnabled() %>"
									emailParam="emailFileEntryReview"
									emailSubject="<%= dlGroupServiceSettings.getEmailFileEntryReviewSubjectXml() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(dlConfigurationDisplaycontext.getNavigation(), "document-expired-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= dlGroupServiceSettings.getEmailFileEntryExpiredBodyXml() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= dlGroupServiceSettings.isEmailFileEntryExpiredEnabled() %>"
									emailParam="emailFileEntryExpired"
									emailSubject="<%= dlGroupServiceSettings.getEmailFileEntryExpiredSubjectXml() %>"
								/>
							</div>
						</c:when>
						<c:otherwise>
							<div class="c-px-4">
								<liferay-frontend:fieldset>
									<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= dlGroupServiceSettings.getEmailFromName() %>">
										<aui:validator errorMessage="please-enter-a-valid-name" name="required" />
									</aui:input>

									<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= dlGroupServiceSettings.getEmailFromAddress() %>">
										<aui:validator errorMessage="please-enter-a-valid-email-address" name="required" />
										<aui:validator name="email" />
									</aui:input>
								</liferay-frontend:fieldset>
							</div>
						</c:otherwise>
					</c:choose>
				</clay:sheet>

				<aui:button-row>
					<aui:button cssClass="c-mr-2" type="submit" />

					<aui:button href="<%= dlConfigurationDisplaycontext.getBackURL() %>" type="cancel" />
				</aui:button-row>
			</aui:form>
		</clay:col>
	</clay:row>
</clay:container-fluid>