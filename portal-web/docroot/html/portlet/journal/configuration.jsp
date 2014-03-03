<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs1Names = "email-from,web-content-added-email,web-content-review-email,web-content-updated-email";

	if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, JournalArticle.class.getName())) {
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
			<liferay-ui:email-notifications-settings
				emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleAddedBody", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_ADDED_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleAddedEnabled--", JournalUtil.getEmailArticleAddedEnabled(portletPreferences)) %>'
				emailParam="emailArticleAdded"
				emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleAddedSubject", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_ADDED_SUBJECT)) %>'
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notifications-settings
				emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleReviewBody", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_REVIEW_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleReviewEnabled--", JournalUtil.getEmailArticleReviewEnabled(portletPreferences)) %>'
				emailParam="emailArticleReview"
				emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleReviewSubject", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_REVIEW_SUBJECT)) %>'
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notifications-settings
				emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleUpdatedBody", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_UPDATED_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleUpdatedEnabled--", JournalUtil.getEmailArticleUpdatedEnabled(portletPreferences)) %>'
				emailParam="emailArticleUpdated"
				emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleUpdatedSubject", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_UPDATED_SUBJECT)) %>'
			/>
		</liferay-ui:section>

		<c:if test="<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, JournalArticle.class.getName()) %>">
			<liferay-ui:section>
				<liferay-ui:email-notifications-settings
					emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleApprovalDeniedBody", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_BODY)) %>'
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleApprovalDeniedEnabled--", JournalUtil.getEmailArticleApprovalDeniedEnabled(portletPreferences)) %>'
					emailParam="emailArticleApprovalDenied"
					emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleApprovalDeniedSubject", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT)) %>'
				/>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-ui:email-notifications-settings
					emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleApprovalGrantedBody", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_BODY)) %>'
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleApprovalGrantedEnabled--", JournalUtil.getEmailArticleApprovalGrantedEnabled(portletPreferences)) %>'
					emailParam="emailArticleApprovalGranted"
					emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleApprovalGrantedSubject", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT)) %>'
				/>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-ui:email-notifications-settings
					emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleApprovalRequestedBody", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY)) %>'
					emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailArticleApprovalRequestedEnabled--", JournalUtil.getEmailArticleApprovalRequestedEnabled(portletPreferences)) %>'
					emailParam="emailArticleApprovalRequested"
					emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailArticleApprovalRequestedSubject", ContentUtil.get(PropsValues.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT)) %>'
				/>
			</liferay-ui:section>
		</c:if>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		<portlet:namespace />saveEmails();

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailArticleAddedBody--'].value = window['<portlet:namespace />emailArticleAdded'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailArticleReviewBody--'].value = window['<portlet:namespace />emailArticleReview'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailArticleUpdatedBody--'].value = window['<portlet:namespace />emailArticleUpdated'].getHTML();
		}
		catch (e) {
		}

		<c:if test="<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, JournalArticle.class.getName()) %>">
			try {
				document.<portlet:namespace />fm['<portlet:namespace />preferences--emailArticleApprovalDeniedBody--'].value = window['<portlet:namespace />emailArticleApprovalDenied'].getHTML();
			}
			catch (e) {
			}

			try {
				document.<portlet:namespace />fm['<portlet:namespace />preferences--emailArticleApprovalGrantedBody--'].value = window['<portlet:namespace />emailArticleApprovalGranted'].getHTML();
			}
			catch (e) {
			}

			try {
				document.<portlet:namespace />fm['<portlet:namespace />preferences--emailArticleApprovalRequestedBody--'].value = window['<portlet:namespace />emailArticleApprovalRequested'].getHTML();
			}
			catch (e) {
			}
		</c:if>
	}
</aui:script>