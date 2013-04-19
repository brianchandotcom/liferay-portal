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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "display-settings");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", BlogsUtil.getEmailFromName(preferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", BlogsUtil.getEmailFromAddress(preferences, company.getCompanyId()));

boolean emailEntryAddedEnabled = ParamUtil.getBoolean(request, "preferences--emailEntryAddedEnabled--", BlogsUtil.getEmailEntryAddedEnabled(preferences));
boolean emailEntryUpdatedEnabled = ParamUtil.getBoolean(request, "preferences--emailEntryUpdatedEnabled--", BlogsUtil.getEmailEntryUpdatedEnabled(preferences));

String emailParam = StringPool.BLANK;
String defaultEmailSubject = StringPool.BLANK;
String defaultEmailBody = StringPool.BLANK;

if (tabs2.equals("entry-added-email")) {
	emailParam = "emailEntryAdded";
	defaultEmailSubject = ContentUtil.get(PropsUtil.get(PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT));
	defaultEmailBody = ContentUtil.get(PropsUtil.get(PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_BODY));
}
else if (tabs2.equals("entry-updated-email")) {
	emailParam = "emailEntryUpdated";
	defaultEmailSubject = ContentUtil.get(PropsUtil.get(PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT));
	defaultEmailBody = ContentUtil.get(PropsUtil.get(PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_BODY));
}

String currentLanguageId = LanguageUtil.getLanguageId(request);

String subjectParam = emailParam + "Subject_" + currentLanguageId;
String bodyParam = emailParam + "Body_" + currentLanguageId;

String emailSubject = PrefsParamUtil.getString(preferences, request, subjectParam, defaultEmailSubject);
String emailBody = PrefsParamUtil.getString(preferences, request, bodyParam, defaultEmailBody);

String[] socialBookmarksTypesArray = StringUtil.split(preferences.getValue("socialBookmarksTypes", PropsUtil.get(PropsKeys.SOCIAL_BOOKMARK_TYPES)));
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
	String tabs2Names = "display-settings,email-from,entry-added-email,entry-updated-email";

	if (PortalUtil.isRSSFeedsEnabled()) {
		tabs2Names += ",rss";
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		param="tabs2"
		url="<%= portletURL %>"
	/>

	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
	<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailEntryAddedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailEntryUpdatedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />

	<c:choose>
		<c:when test='<%= tabs2.equals("email-from") %>'>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= emailFromAddress %>" />
			</aui:fieldset>

			<div class="definition-of-terms">
				<h4><liferay-ui:message key="definition-of-terms" /></h4>

				<dl>
					<dt>
						[$BLOGS_ENTRY_STATUS_BY_USER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-user-who-updated-the-blog-entry" />
					</dd>
					<dt>
						[$BLOGS_ENTRY_USER_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-email-address-of-the-user-who-added-the-blog-entry" />
					</dd>
					<dt>
						[$BLOGS_ENTRY_USER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-user-who-added-the-blog-entry" />
					</dd>
					<dt>
						[$COMPANY_ID$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-id-associated-with-the-blog" />
					</dd>
					<dt>
						[$COMPANY_MX$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-mx-associated-with-the-blog" />
					</dd>
					<dt>
						[$COMPANY_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-name-associated-with-the-blog" />
					</dd>
					<dt>
						[$PORTLET_NAME$]
					</dt>
					<dd>
						<%= PortalUtil.getPortletTitle(renderResponse) %>
					</dd>
					<dt>
						[$SITE_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-site-name-associated-with-the-blog" />
					</dd>
				</dl>
			</div>
		</c:when>
		<c:when test='<%= tabs2.startsWith("entry-") %>'>
			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("entry-added-email") %>'>
						<aui:input label="enabled" name="preferences--emailEntryAddedEnabled--" type="checkbox" value="<%= emailEntryAddedEnabled %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("entry-updated-email") %>'>
						<aui:input label="enabled" name="preferences--emailEntryUpdatedEnabled--" type="checkbox" value="<%= emailEntryUpdatedEnabled %>" />
					</c:when>
				</c:choose>

				<aui:select label="language" name="languageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage(this);" %>'>

					<%
					Locale[] locales = LanguageUtil.getAvailableLocales();

					for (int i = 0; i < locales.length; i++) {
						String style = StringPool.BLANK;

						if (Validator.isNotNull(preferences.getValue(emailParam + "Subject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
							Validator.isNotNull(preferences.getValue(emailParam + "Body_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

							style = "font-weight: bold;";
						}
					%>

						<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

					<%
					}
					%>

				</aui:select>

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
						[$BLOGS_ENTRY_USER_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-email-address-of-the-user-who-added-the-blog-entry" />
					</dd>
					<dt>
						[$BLOGS_ENTRY_USER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-user-who-added-the-blog-entry" />
					</dd>
					<dt>
						[$BLOGS_ENTRY_URL$]
					</dt>
					<dd>
						<liferay-ui:message key="the-blog-entry-url" />
					</dd>
					<dt>
						[$COMPANY_ID$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-id-associated-with-the-blog" />
					</dd>
					<dt>
						[$COMPANY_MX$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-mx-associated-with-the-blog" />
					</dd>
					<dt>
						[$COMPANY_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-name-associated-with-the-blog" />
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
						[$SITE_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-site-name-associated-with-the-blog" />
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
		<c:when test='<%= tabs2.equals("display-settings") %>'>
			<%@ include file="/html/portlet/blogs/display_settings.jspf" %>
		</c:when>
		<c:when test='<%= tabs2.equals("rss") %>'>
			<liferay-ui:rss-settings
				delta="<%= rssDelta %>"
				displayStyle="<%= rssDisplayStyle %>"
				enabled="<%= enableRSS %>"
				feedType="<%= rssFeedType %>"
			/>
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

	function <portlet:namespace />updateLanguage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			<c:if test='<%= tabs2.startsWith("entry-") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= bodyParam %>.value = window.<portlet:namespace />editor.getHTML();
			</c:if>

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.Util.toggleBoxes('<portlet:namespace />enableSocialBookmarksCheckbox','<portlet:namespace />socialBookmarksOptions');
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.blogs.configuration.jsp";
%>