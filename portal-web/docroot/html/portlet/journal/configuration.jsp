<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

String emailFromName = ParamUtil.getString(request, "emailFromName", JournalUtil.getEmailFromName(portletSetup, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", JournalUtil.getEmailFromAddress(portletSetup, company.getCompanyId()));

String emailArticleAddedSubject = ParamUtil.getString(request, "emailArticleAddedSubject", JournalUtil.getEmailArticleAddedSubject(portletSetup));
String emailArticleAddedBody = ParamUtil.getString(request, "emailArticleAddedBody", JournalUtil.getEmailArticleAddedBody(portletSetup));

String emailArticleApprovalDeniedSubject = ParamUtil.getString(request, "emailArticleApprovalDeniedSubject", JournalUtil.getEmailArticleApprovalDeniedSubject(portletSetup));
String emailArticleApprovalDeniedBody = ParamUtil.getString(request, "emailArticleApprovalDeniedBody", JournalUtil.getEmailArticleApprovalDeniedBody(portletSetup));

String emailArticleApprovalGrantedSubject = ParamUtil.getString(request, "emailArticleApprovalGrantedSubject", JournalUtil.getEmailArticleApprovalGrantedSubject(portletSetup));
String emailArticleApprovalGrantedBody = ParamUtil.getString(request, "emailArticleApprovalGrantedBody", JournalUtil.getEmailArticleApprovalGrantedBody(portletSetup));

String emailArticleApprovalRequestedSubject = ParamUtil.getString(request, "emailArticleApprovalRequestedSubject", JournalUtil.getEmailArticleApprovalRequestedSubject(portletSetup));
String emailArticleApprovalRequestedBody = ParamUtil.getString(request, "emailArticleApprovalRequestedBody", JournalUtil.getEmailArticleApprovalRequestedBody(portletSetup));

String emailArticleReviewSubject = ParamUtil.getString(request, "emailArticleReviewSubject", JournalUtil.getEmailArticleReviewSubject(portletSetup));
String emailArticleReviewBody = ParamUtil.getString(request, "emailArticleReviewBody", JournalUtil.getEmailArticleReviewBody(portletSetup));

String emailArticleUpdatedSubject = ParamUtil.getString(request, "emailArticleUpdatedSubject", JournalUtil.getEmailArticleUpdatedSubject(portletSetup));
String emailArticleUpdatedBody = ParamUtil.getString(request, "emailArticleUpdatedBody", JournalUtil.getEmailArticleUpdatedBody(portletSetup));

String editorParam = StringPool.BLANK;
String editorContent = StringPool.BLANK;

if (tabs2.equals("web-content-added-email")) {
	editorParam = "emailArticleAddedBody";
	editorContent = emailArticleAddedBody;
}
else if (tabs2.equals("web-content-approval-denied-email")) {
	editorParam = "emailArticleApprovalDeniedBody";
	editorContent = emailArticleApprovalDeniedBody;
}
else if (tabs2.equals("web-content-approval-granted-email")) {
	editorParam = "emailArticleApprovalGrantedBody";
	editorContent = emailArticleApprovalGrantedBody;
}
else if (tabs2.equals("web-content-approval-requested-email")) {
	editorParam = "emailArticleApprovalRequestedBody";
	editorContent = emailArticleApprovalRequestedBody;
}
else if (tabs2.equals("web-content-review-email")) {
	editorParam = "emailArticleReviewBody";
	editorContent = emailArticleReviewBody;
}
else if (tabs2.equals("web-content-updated-email")) {
	editorParam = "emailArticleUpdatedBody";
	editorContent = emailArticleUpdatedBody;
}
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
						<aui:input label="enabled" name="preferences--emailArticleAddedEnabled--" type="checkbox" value="<%= JournalUtil.getEmailArticleAddedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-denied-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleApprovalDeniedEnabled--" type="checkbox" value="<%= JournalUtil.getEmailArticleApprovalDeniedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-granted-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleApprovalGrantedEnabled--" type="checkbox" value="<%= JournalUtil.getEmailArticleApprovalGrantedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-requested-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleApprovalRequestedEnabled--" type="checkbox" value="<%= JournalUtil.getEmailArticleApprovalRequestedEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-review-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleReviewEnabled--" type="checkbox" value="<%= JournalUtil.getEmailArticleReviewEnabled(portletSetup) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-updated-email") %>'>
						<aui:input label="enabled" name="preferences--emailArticleUpdatedEnabled--" type="checkbox" value="<%= JournalUtil.getEmailArticleUpdatedEnabled(portletSetup) %>" />
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test='<%= tabs2.equals("web-content-added-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailArticleAddedSubject--" type="text" value="<%= emailArticleAddedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-denied-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailArticleApprovalDeniedSubject--" type="text" value="<%= emailArticleApprovalDeniedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-granted-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailArticleApprovalGrantedSubject--" type="text" value="<%= emailArticleApprovalGrantedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-approval-requested-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailArticleApprovalRequestedSubject--" type="text" value="<%= emailArticleApprovalRequestedSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-review-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailArticleReviewSubject--" type="text" value="<%= emailArticleReviewSubject %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("web-content-updated-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailArticleUpdatedSubject--" type="text" value="<%= emailArticleUpdatedSubject %>" />
					</c:when>
				</c:choose>

				<aui:field-wrapper label="body">
					<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

					<aui:input name='<%= "preferences--" + editorParam + "--" %>' type="hidden" />
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
		return "<%= UnicodeFormatter.toString(editorContent) %>";
	}

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.startsWith("web-content-added-") || tabs2.startsWith("web-content-approval-") || tabs2.startsWith("web-content-review-") || tabs2.startsWith("web-content-updated-") %>'>
			document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.configuration.jsp";
%>