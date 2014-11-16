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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", JournalUtil.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", JournalUtil.getEmailFromAddress(portletPreferences, company.getCompanyId()));
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs1Names = "email-from,web-content-added-email,web-content-review-email,web-content-updated-email";

	if (WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLinksCount(themeDisplay.getCompanyId(), scopeGroupId, JournalFolder.class.getName()) > 0) {
		tabs1Names = tabs1Names.concat(",web-content-approval-denied-email,web-content-approval-granted-email,web-content-approval-requested-email");
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		refresh="<%= false %>"
	>
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
		<liferay-ui:error key="emailArticleReviewBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailArticleReviewSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailArticleUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailArticleUpdatedSubject" message="please-enter-a-valid-subject" />

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" type="text" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" type="text" value="<%= emailFromAddress %>" />
			</aui:fieldset>
		</liferay-ui:section>

		<%
		Map<String, String> emailDefinitionTerms = JournalUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName);
		%>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleAddedBody", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_ADDED_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleAddedEnabled--", JournalUtil.getEmailArticleAddedEnabled(portletPreferences)) %>'
				emailParam="emailArticleAdded"
				emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleAddedSubject", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_ADDED_SUBJECT)) %>'
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleReviewBody", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_REVIEW_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleReviewEnabled--", JournalUtil.getEmailArticleReviewEnabled(portletPreferences)) %>'
				emailParam="emailArticleReview"
				emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleReviewSubject", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_REVIEW_SUBJECT)) %>'
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleUpdatedBody", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_UPDATED_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleUpdatedEnabled--", JournalUtil.getEmailArticleUpdatedEnabled(portletPreferences)) %>'
				emailParam="emailArticleUpdated"
				emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleUpdatedSubject", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_UPDATED_SUBJECT)) %>'
			/>
		</liferay-ui:section>

		<c:if test="<%= WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLinksCount(themeDisplay.getCompanyId(), scopeGroupId, JournalFolder.class.getName()) > 0 %>">
			<liferay-ui:section>
				<liferay-ui:email-notification-settings
					emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleApprovalDeniedBody", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_BODY)) %>'
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleApprovalDeniedEnabled--", JournalUtil.getEmailArticleApprovalDeniedEnabled(portletPreferences)) %>'
					emailParam="emailArticleApprovalDenied"
					emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleApprovalDeniedSubject", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT)) %>'
				/>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-ui:email-notification-settings
					emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleApprovalGrantedBody", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_BODY)) %>'
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleApprovalGrantedEnabled--", JournalUtil.getEmailArticleApprovalGrantedEnabled(portletPreferences)) %>'
					emailParam="emailArticleApprovalGranted"
					emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleApprovalGrantedSubject", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT)) %>'
				/>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-ui:email-notification-settings
					emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleApprovalRequestedBody", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY)) %>'
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleApprovalRequestedEnabled--", JournalUtil.getEmailArticleApprovalRequestedEnabled(portletPreferences)) %>'
					emailParam="emailArticleApprovalRequested"
					emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailArticleApprovalRequestedSubject", "preferences", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT)) %>'
				/>
			</liferay-ui:section>
		</c:if>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>