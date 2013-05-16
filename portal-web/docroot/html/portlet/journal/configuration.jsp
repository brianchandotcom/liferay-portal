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
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String portletResource = ParamUtil.getString(request, "portletResource");

PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);

String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", JournalUtil.getEmailFromName(portletSetup, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", JournalUtil.getEmailFromAddress(portletSetup, company.getCompanyId()));

boolean emailArticleAddedEnabled = ParamUtil.getBoolean(request, "preferences--emailArticleAddedEnabled--", JournalUtil.getEmailArticleAddedEnabled(portletSetup));
boolean emailArticleApprovalDeniedEnabled = ParamUtil.getBoolean(request, "preferences--emailArticleApprovalDeniedEnabled--", JournalUtil.getEmailArticleApprovalDeniedEnabled(portletSetup));
boolean emailArticleApprovalGrantedEnabled = ParamUtil.getBoolean(request, "preferences--emailArticleApprovalGrantedEnabled--", JournalUtil.getEmailArticleApprovalGrantedEnabled(portletSetup));
boolean emailArticleApprovalRequestedEnabled = ParamUtil.getBoolean(request, "preferences--emailArticleApprovalRequestedEnabled--", JournalUtil.getEmailArticleApprovalRequestedEnabled(portletSetup));
boolean emailArticleReviewEnabled = ParamUtil.getBoolean(request, "preferences--emailArticleReviewEnabled--", JournalUtil.getEmailArticleReviewEnabled(portletSetup));
boolean emailArticleUpdatedEnabled = ParamUtil.getBoolean(request, "preferences--emailArticleUpdatedEnabled--", JournalUtil.getEmailArticleUpdatedEnabled(portletSetup));

String emailParam = StringPool.BLANK;
String defaultEmailSubject = StringPool.BLANK;
String defaultEmailBody = StringPool.BLANK;

if (tabs2.equals("web-content-added-email")) {
	emailParam = "emailArticleAdded";
	defaultEmailSubject = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_ADDED_SUBJECT));
	defaultEmailBody = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_ADDED_BODY));
}
else if (tabs2.equals("web-content-approval-denied-email")) {
	emailParam = "emailArticleApprovalDenied";
	defaultEmailSubject = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_SUBJECT));
	defaultEmailBody = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_DENIED_BODY));
}
else if (tabs2.equals("web-content-approval-granted-email")) {
	emailParam = "emailArticleApprovalGranted";
	defaultEmailSubject = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_SUBJECT));
	defaultEmailBody = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_GRANTED_BODY));
}
else if (tabs2.equals("web-content-approval-requested-email")) {
	emailParam = "emailArticleApprovalRequested";
	defaultEmailSubject = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_SUBJECT));
	defaultEmailBody = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_APPROVAL_REQUESTED_BODY));
}
else if (tabs2.equals("web-content-review-email")) {
	emailParam = "emailArticleReview";
	defaultEmailSubject = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_REVIEW_SUBJECT));
	defaultEmailBody = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_REVIEW_BODY));
}
else if (tabs2.equals("web-content-updated-email")) {
	emailParam = "emailArticleUpdated";
	defaultEmailSubject = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_UPDATED_SUBJECT));
	defaultEmailBody = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_EMAIL_ARTICLE_UPDATED_BODY));
}

String subjectParam = emailParam + "Subject";
String bodyParam = emailParam + "Body";

String emailSubject = PrefsParamUtil.getString(preferences, request, subjectParam, defaultEmailSubject);
String emailBody = PrefsParamUtil.getString(preferences, request, bodyParam, defaultEmailBody);
%>

<liferay-portlet:renderURL portletConfiguration="true" var="portletURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<%
	String tabs1Names = "email-from,web-content-added-email,web-content-review-email,web-content-updated-email";

	if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, JournalArticle.class.getName())) {
		tabs1Names = tabs1Names.concat(",web-content-approval-denied-email,web-content-approval-granted-email,web-content-approval-requested-email");
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		param="tabs2"
		url="<%= portletURL %>"
	/>

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

	<c:choose>
		<c:when test='<%= tabs2.equals("email-from") %>'>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" type="text" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" type="text" value="<%= emailFromAddress %>" />
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.startsWith("web-content-added-") || tabs2.startsWith("web-content-approval-") || tabs2.startsWith("web-content-review-") || tabs2.startsWith("web-content-updated-") %>'>
			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("web-content-added-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleAddedEnabled--" type="checkbox" value="<%= emailArticleAddedEnabled %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-denied-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleApprovalDeniedEnabled--" type="checkbox" value="<%= emailArticleApprovalDeniedEnabled %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-granted-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleApprovalGrantedEnabled--" type="checkbox" value="<%= emailArticleApprovalGrantedEnabled %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-requested-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleApprovalRequestedEnabled--" type="checkbox" value="<%= emailArticleApprovalRequestedEnabled %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-review-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleReviewEnabled--" type="checkbox" value="<%= emailArticleReviewEnabled %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-updated-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleUpdatedEnabled--" type="checkbox" value="<%= emailArticleUpdatedEnabled %>" />
					</c:when>
				</c:choose>

				<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "preferences--" + subjectParam + "--" %>' value="<%= emailSubject %>" />

				<aui:field-wrapper label="body">
					<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

					<aui:input name='<%= "preferences--" + bodyParam + "--" %>' type="hidden" />
				</aui:field-wrapper>
			</aui:fieldset>

			<div class="definition-of-terms">
				<h4><liferay-ui:message key="definition-of-terms" /></h4>

				<dl>
					<dt>
						[$ARTICLE_ID$]
					</dt>
					<dd>
						<liferay-ui:message key="the-web-content-id" />
					</dd>
					<dt>
						[$ARTICLE_TITLE$]
					</dt>
					<dd>
						<liferay-ui:message key="the-web-content-title" />
					</dd>

					<c:if test='<%= tabs2.startsWith("web-content-added-") || tabs2.startsWith("web-content-approval-") || tabs2.startsWith("web-content-review-") || tabs2.startsWith("web-content-updated-") %>'>
						<dt>
							[$ARTICLE_URL$]
						</dt>
						<dd>
							<liferay-ui:message key="the-web-content-url" />
						</dd>
					</c:if>

					<dt>
						[$ARTICLE_VERSION$]
					</dt>
					<dd>
						<liferay-ui:message key="the-web-content-version" />
					</dd>
					<dt>
						[$FROM_ADDRESS$]
					</dt>
					<dd>
						<%= HtmlUtil.escape(emailFromAddress) %>
					</dd>
					<dt>
						[$FROM_NAME$]
					</dt>
					<dd>
						<%= HtmlUtil.escape(emailFromName) %>
					</dd>
					<dt>
						[$PORTAL_URL$]
					</dt>
					<dd>
						<%= company.getVirtualHostname() %>
					</dd>
					<dt>
						[$PORTLET_NAME$]
					</dt>
					<dd>
						<%= PortalUtil.getPortletTitle(renderResponse) %>
					</dd>
					<dt>
						[$TO_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-address-of-the-email-recipient" />
					</dd>
					<dt>
						[$TO_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-name-of-the-email-recipient" />
					</dd>
				</dl>
			</div>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(emailBody) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.startsWith("web-content-added-") || tabs2.startsWith("web-content-approval-") || tabs2.startsWith("web-content-review-") || tabs2.startsWith("web-content-updated-") %>'>
			document.<portlet:namespace />fm.<portlet:namespace /><%= bodyParam %>.value = window.<portlet:namespace />editor.getHTML();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.configuration.jsp";
%>