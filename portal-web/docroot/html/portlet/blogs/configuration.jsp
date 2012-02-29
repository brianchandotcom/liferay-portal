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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "emailFromName", BlogsUtil.getEmailFromName(preferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", BlogsUtil.getEmailFromAddress(preferences, company.getCompanyId()));

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

String emailSubject = PrefsParamUtil.getString(preferences, request, emailParam + "Subject_" + currentLanguageId, defaultEmailSubject);
String emailBody = PrefsParamUtil.getString(preferences, request, emailParam + "Body_" + currentLanguageId, defaultEmailBody);

String editorParam = emailParam + "Body_" + currentLanguageId;
String editorContent = emailBody;
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

	<liferay-ui:tabs
		names="email-from,entry-added-email,entry-updated-email,display-settings,rss"
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
						<aui:input label="enabled" name="preferences--emailEntryAddedEnabled--" type="checkbox" value="<%= BlogsUtil.getEmailEntryAddedEnabled(preferences) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("entry-updated-email") %>'>
						<aui:input label="enabled" name="preferences--emailEntryUpdatedEnabled--" type="checkbox" value="<%= BlogsUtil.getEmailEntryUpdatedEnabled(preferences) %>" />
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

				<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "preferences--" + emailParam + "Subject_" + currentLanguageId + "--" %>' value="<%= emailSubject %>" />

				<aui:field-wrapper label="body">
					<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

					<aui:input name='<%= "preferences--" + editorParam + "--" %>' type="hidden" />
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
			<div class="portlet-msg-info">
				<liferay-ui:message key="set-the-display-styles-used-to-display-blogs-when-viewed-via-as-a-regular-page-or-as-an-rss" />
			</div>

			<aui:fieldset>
				<aui:select label="maximum-items-to-display" name="preferences--pageDelta--">
					<aui:option label="1" selected="<%= pageDelta == 1 %>" />
					<aui:option label="2" selected="<%= pageDelta == 2 %>" />
					<aui:option label="3" selected="<%= pageDelta == 3 %>" />
					<aui:option label="4" selected="<%= pageDelta == 4 %>" />
					<aui:option label="5" selected="<%= pageDelta == 5 %>" />
					<aui:option label="10" selected="<%= pageDelta == 10 %>" />
					<aui:option label="15" selected="<%= pageDelta == 15 %>" />
					<aui:option label="20" selected="<%= pageDelta == 20 %>" />
					<aui:option label="25" selected="<%= pageDelta == 25 %>" />
					<aui:option label="30" selected="<%= pageDelta == 30 %>" />
					<aui:option label="40" selected="<%= pageDelta == 40 %>" />
					<aui:option label="50" selected="<%= pageDelta == 50 %>" />
					<aui:option label="60" selected="<%= pageDelta == 60 %>" />
					<aui:option label="70" selected="<%= pageDelta == 70 %>" />
					<aui:option label="80" selected="<%= pageDelta == 80 %>" />
					<aui:option label="90" selected="<%= pageDelta == 90 %>" />
					<aui:option label="100" selected="<%= pageDelta == 100 %>" />
				</aui:select>

				<aui:select label="display-style" name="preferences--pageDisplayStyle--">
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_FULL_CONTENT %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" selected="<%= pageDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" />
				</aui:select>

				<aui:input name="preferences--enableFlags--" type="checkbox" value="<%= enableFlags %>" />

				<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= enableRelatedAssets %>" />

				<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />

				<c:if test="<%= PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED %>">
					<aui:input name="preferences--enableComments--" type="checkbox" value="<%= enableComments %>" />

					<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= enableCommentRatings %>" />
				</c:if>

				<aui:fieldset>
					<aui:input name="preferences--enableSocialBookmarks--" type="checkbox" value="<%= enableSocialBookmarks %>" />

					<div class="social-boomarks-options" id="<portlet:namespace />socialBookmarksOptions">
						<aui:select label="display-style" name="preferences--socialBookmarksDisplayStyle--">
							<aui:option label="simple" selected='<%= socialBookmarksDisplayStyle.equals("simple") %>' />
							<aui:option label="vertical" selected='<%= socialBookmarksDisplayStyle.equals("vertical") %>' />
							<aui:option label="horizontal" selected='<%= socialBookmarksDisplayStyle.equals("horizontal") %>' />
						</aui:select>

						<aui:select label="display-position" name="preferences--socialBookmarksDisplayPosition--">
							<aui:option label="top" selected='<%= socialBookmarksDisplayPosition.equals("top") %>' />
							<aui:option label="bottom" selected='<%= socialBookmarksDisplayPosition.equals("bottom") %>' />
						</aui:select>
					</div>
				</aui:fieldset>
			</aui:fieldset>

		</c:when>
		<c:when test='<%= tabs2.equals("rss") %>'>
			<aui:fieldset>
				<aui:select label="maximum-items-to-display" name="preferences--rssDelta--">
					<aui:option label="1" selected="<%= rssDelta == 1 %>" />
					<aui:option label="2" selected="<%= rssDelta == 2 %>" />
					<aui:option label="3" selected="<%= rssDelta == 3 %>" />
					<aui:option label="4" selected="<%= rssDelta == 4 %>" />
					<aui:option label="5" selected="<%= rssDelta == 5 %>" />
					<aui:option label="10" selected="<%= rssDelta == 10 %>" />
					<aui:option label="15" selected="<%= rssDelta == 15 %>" />
					<aui:option label="20" selected="<%= rssDelta == 20 %>" />
					<aui:option label="25" selected="<%= rssDelta == 25 %>" />
					<aui:option label="30" selected="<%= rssDelta == 30 %>" />
					<aui:option label="40" selected="<%= rssDelta == 40 %>" />
					<aui:option label="50" selected="<%= rssDelta == 50 %>" />
					<aui:option label="60" selected="<%= rssDelta == 60 %>" />
					<aui:option label="70" selected="<%= rssDelta == 70 %>" />
					<aui:option label="80" selected="<%= rssDelta == 80 %>" />
					<aui:option label="90" selected="<%= rssDelta == 90 %>" />
					<aui:option label="100" selected="<%= rssDelta == 100 %>" />
				</aui:select>

				<aui:select label="display-style" name="preferences--rssDisplayStyle--">
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_FULL_CONTENT %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" />
					<aui:option label="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" />
				</aui:select>

				<aui:select label="format" name="preferences--rssFormat--">
					<aui:option label="RSS 1.0" selected='<%= rssFormat.equals("rss10") %>' value="rss10" />
					<aui:option label="RSS 2.0" selected='<%= rssFormat.equals("rss20") %>' value="rss20" />
					<aui:option label="Atom 1.0" selected='<%= rssFormat.equals("atom10") %>' value="atom10" />
				</aui:select>
			</aui:fieldset>
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

	function <portlet:namespace />updateLanguage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			<c:if test='<%= tabs2.startsWith("entry-") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= editorParam %>.value = window.<portlet:namespace />editor.getHTML();
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