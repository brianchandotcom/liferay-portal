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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "general");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", MBUtil.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", MBUtil.getEmailFromAddress(portletPreferences, company.getCompanyId()));

boolean emailMessageAddedEnabled = ParamUtil.getBoolean(request, "preferences--emailMessageAddedEnabled--", MBUtil.getEmailMessageAddedEnabled(portletPreferences));
boolean emailMessageUpdatedEnabled = ParamUtil.getBoolean(request, "preferences--emailMessageUpdatedEnabled--", MBUtil.getEmailMessageUpdatedEnabled(portletPreferences));

String emailParam = StringPool.BLANK;
String defaultEmailSubject = StringPool.BLANK;
String defaultEmailBody = StringPool.BLANK;
String defaultEmailSignature = StringPool.BLANK;

if (tabs2.equals("message-added-email")) {
	emailParam = "emailMessageAdded";
	defaultEmailSubject = ContentUtil.get(PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT);
	defaultEmailBody = ContentUtil.get(PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);
	defaultEmailSignature = ContentUtil.get(PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SIGNATURE);
}
else if (tabs2.equals("message-updated-email")) {
	emailParam = "emailMessageUpdated";
	defaultEmailSubject = ContentUtil.get(PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT);
	defaultEmailBody = ContentUtil.get(PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);
	defaultEmailSignature = ContentUtil.get(PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SIGNATURE);
}

String emailSubjectParam = emailParam + "Subject";
String emailBodyParam = emailParam + "Body";
String emailSignatureParam = emailParam + "Signature";

String emailSubject = PrefsParamUtil.getString(portletPreferences, request, emailSubjectParam, defaultEmailSubject);
String emailBody = PrefsParamUtil.getString(portletPreferences, request, emailBodyParam, defaultEmailBody);
String emailSignature = PrefsParamUtil.getString(portletPreferences, request, emailSignatureParam, defaultEmailSignature);
%>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="portletURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<%
	String tabs2Names = "general,email-from,message-added-email,message-updated-email,thread-priorities,user-ranks";

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
	<liferay-ui:error key="emailMessageAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailMessageAddedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailMessageAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailMessageUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailMessageUpdatedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailMessageUpdatedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="userRank" message="please-enter-valid-user-ranks" />

	<c:choose>
		<c:when test='<%= tabs2.equals("general") %>'>
			<aui:fieldset>
				<aui:input name="preferences--allowAnonymousPosting--" type="checkbox" value="<%= MBUtil.isAllowAnonymousPosting(portletPreferences) %>" />

				<aui:input helpMessage="message-boards-message-subscribe-by-default-help" label="subscribe-by-default" name="preferences--subscribeByDefault--" type="checkbox" value="<%= subscribeByDefault %>" />

				<aui:select name="preferences--messageFormat--">

					<%
					for (int i = 0; i < MBMessageConstants.FORMATS.length; i++) {
					%>

						<c:if test="<%= MBUtil.isValidMessageFormat(MBMessageConstants.FORMATS[i]) %>">
							<aui:option label='<%= LanguageUtil.get(pageContext,"message-boards.message-formats." + MBMessageConstants.FORMATS[i]) %>' selected="<%= messageFormat.equals(MBMessageConstants.FORMATS[i]) %>" value="<%= MBMessageConstants.FORMATS[i] %>" />
						</c:if>

					<%
					}
					%>

				</aui:select>

				<aui:input name="preferences--enableFlags--" type="checkbox" value="<%= enableFlags %>" />

				<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />

				<aui:input name="preferences--threadAsQuestionByDefault--" type="checkbox" value="<%= threadAsQuestionByDefault %>" />

				<aui:select label="show-recent-posts-from-last" name="preferences--recentPostsDateOffset--">
					<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "24", false) %>' selected='<%= recentPostsDateOffset.equals("1") %>' value="1" />
					<aui:option label='<%= LanguageUtil.format(pageContext, "x-days", "7", false) %>' selected='<%= recentPostsDateOffset.equals("7") %>' value="7" />
					<aui:option label='<%= LanguageUtil.format(pageContext, "x-days", "30", false) %>' selected='<%= recentPostsDateOffset.equals("30") %>' value="30" />
					<aui:option label='<%= LanguageUtil.format(pageContext, "x-days", "365", false) %>' selected='<%= recentPostsDateOffset.equals("365") %>' value="365" />
				</aui:select>
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("email-from") %>'>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= emailFromAddress %>" />

				<aui:input label="html-format" name="preferences--emailHtmlFormat--" type="checkbox" value="<%= MBUtil.getEmailHtmlFormat(portletPreferences) %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms">
				<legend>
					<liferay-ui:message key="definition-of-terms" />
				</legend>

				<dl>
					<dt>
						[$COMPANY_ID$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-id-associated-with-the-message-board" />
					</dd>
					<dt>
						[$COMPANY_MX$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-mx-associated-with-the-message-board" />
					</dd>
					<dt>
						[$COMPANY_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-name-associated-with-the-message-board" />
					</dd>

					<c:if test="<%= PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED %>">
						<dt>
							[$MAILING_LIST_ADDRESS$]
						</dt>
						<dd>
							<liferay-ui:message key="the-email-address-of-the-mailing-list" />
						</dd>
					</c:if>

					<dt>
						[$MESSAGE_USER_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-email-address-of-the-user-who-added-the-message" />
					</dd>
					<dt>
						[$MESSAGE_USER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-user-who-added-the-message" />
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
						<liferay-ui:message key="the-site-name-associated-with-the-message-board" />
					</dd>
				</dl>
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.startsWith("message-") %>'>
			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("message-added-email") %>'>
						<aui:input label="enabled" name="preferences--emailMessageAddedEnabled--" type="checkbox" value="<%= emailMessageAddedEnabled %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("message-updated-email") %>'>
						<aui:input label="enabled" name="preferences--emailMessageUpdatedEnabled--" type="checkbox" value="<%= emailMessageUpdatedEnabled %>" />
					</c:when>
				</c:choose>

				<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "preferences--" + emailSubjectParam + "--" %>' value="<%= emailSubject %>" />

				<aui:input cssClass="lfr-textarea-container" label="body" name='<%= "preferences--" + emailBodyParam + "--" %>' type="textarea" value="<%= emailBody %>" warp="soft" />

				<aui:input cssClass="lfr-textarea-container" label="signature" name='<%= "preferences--" + emailSignatureParam + "--" %>' type="textarea" value="<%= emailSignature %>" wrap="soft" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms">
				<legend>
					<liferay-ui:message key="definition-of-terms" />
				</legend>

				<dl>
					<dt>
						[$CATEGORY_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-category-in-which-the-message-has-been-posted" />
					</dd>
					<dt>
						[$COMPANY_ID$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-id-associated-with-the-message-board" />
					</dd>
					<dt>
						[$COMPANY_MX$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-mx-associated-with-the-message-board" />
					</dd>
					<dt>
						[$COMPANY_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-name-associated-with-the-message-board" />
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

					<c:if test="<%= PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED %>">
						<dt>
							[$MAILING_LIST_ADDRESS$]
						</dt>
						<dd>
							<liferay-ui:message key="the-email-address-of-the-mailing-list" />
						</dd>
					</c:if>

					<dt>
						[$MESSAGE_BODY$]
					</dt>
					<dd>
						<liferay-ui:message key="the-message-body" />
					</dd>
					<dt>
						[$MESSAGE_ID$]
					</dt>
					<dd>
						<liferay-ui:message key="the-message-id" />
					</dd>
					<dt>
						[$MESSAGE_SUBJECT$]
					</dt>
					<dd>
						<liferay-ui:message key="the-message-subject" />
					</dd>
					<dt>
						[$MESSAGE_URL$]
					</dt>
					<dd>
						<liferay-ui:message key="the-message-url" />
					</dd>
					<dt>
						[$MESSAGE_USER_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-email-address-of-the-user-who-added-the-message" />
					</dd>
					<dt>
						[$MESSAGE_USER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-user-who-added-the-message" />
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
						<liferay-ui:message key="the-site-name-associated-with-the-message-board" />
					</dd>

					<c:if test="<%= !PropsValues.MESSAGE_BOARDS_EMAIL_BULK %>">
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
					</c:if>
				</dl>
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("thread-priorities") %>'>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-the-name,-image,-and-priority-level-in-descending-order" />
			</div>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<aui:field-wrapper label="default-language">
						<liferay-ui:input-resource url="<%= defaultLocale.getDisplayName(defaultLocale) %>" />
					</aui:field-wrapper>
				</td>
				<td>
					<aui:select label="localized-language" name="languageId" onClick='<%= renderResponse.getNamespace() + "updateLanguage();" %>' showEmptyOption="<%= true %>">

						<%
						for (int i = 0; i < locales.length; i++) {
							if (locales[i].equals(defaultLocale)) {
								continue;
							}
						%>

							<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

						<%
						}
						%>

					</aui:select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<table class="lfr-table">
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="name" />
						</td>
						<td class="lfr-label">
							<liferay-ui:message key="image" />
						</td>
						<td class="lfr-label">
							<liferay-ui:message key="priority" />
						</td>
					</tr>

					<%
					priorities = LocalizationUtil.getPreferencesValues(portletPreferences, "priorities", defaultLanguageId);

					for (int i = 0; i < 10; i++) {
						String name = StringPool.BLANK;
						String image = StringPool.BLANK;
						String value = StringPool.BLANK;

						if (priorities.length > i) {
							String[] priority = StringUtil.split(priorities[i]);

							try {
								name = priority[0];
								image = priority[1];
								value = priority[2];
							}
							catch (Exception e) {
							}

							if (Validator.isNull(name) && Validator.isNull(image)) {
								value = StringPool.BLANK;
							}
						}
					%>

						<tr>
							<td>
								<aui:input label="" name='<%= "priorityName" + i + "_" + defaultLanguageId %>' size="15" value="<%= name %>" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityImage" + i + "_" + defaultLanguageId %>' size="40" value="<%= image %>" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityValue" + i + "_" + defaultLanguageId %>' size="4" value="<%= value %>" />
							</td>
						</tr>

					<%
					}
					%>

					</table>
				</td>
				<td>
					<table class='<%= (currentLocale.equals(defaultLocale) ? "hide" : "") + " lfr-table" %>' id="<portlet:namespace />localized-priorities-table">
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="name" />
						</td>
						<td class="lfr-label">
							<liferay-ui:message key="image" />
						</td>
						<td class="lfr-label">
							<liferay-ui:message key="priority" />
						</td>
					</tr>

					<%
					for (int i = 0; i < 10; i++) {
					%>

						<tr>
							<td>
								<aui:input label="" name='<%= "priorityName" + i + "_temp" %>' onChange='<%= renderResponse.getNamespace() + "onChanged();" %>' size="15" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityImage" + i + "_temp" %>' onChange='<%= renderResponse.getNamespace() + "onChanged();" %>' size="40" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityValue" + i + "_temp" %>' onChange='<%= renderResponse.getNamespace() + "onChanged();" %>' size="4" />
							</td>
						</tr>

					<%
					}
					%>

					</table>

					<%
					for (int i = 0; i < locales.length; i++) {
						if (locales[i].equals(defaultLocale)) {
							continue;
						}

						String[] tempPriorities = LocalizationUtil.getPreferencesValues(portletPreferences, "priorities", LocaleUtil.toLanguageId(locales[i]));

						for (int j = 0; j < 10; j++) {
							String name = StringPool.BLANK;
							String image = StringPool.BLANK;
							String value = StringPool.BLANK;

							if (tempPriorities.length > j) {
								String[] priority = StringUtil.split(tempPriorities[j]);

								try {
									name = priority[0];
									image = priority[1];
									value = priority[2];
								}
								catch (Exception e) {
								}

								if (Validator.isNull(name) && Validator.isNull(image)) {
									value = StringPool.BLANK;
								}
							}
					%>

							<aui:input name='<%= "priorityName" + j + "_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= name %>" />
							<aui:input name='<%= "priorityImage" + j + "_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= image %>" />
							<aui:input name='<%= "priorityValue" + j + "_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= value %>" />

					<%
						}
					}
					%>

				</td>
			</tr>
			</table>

			<br />

			<aui:script>
				var changed = false;
				var lastLanguageId = "<%= currentLanguageId %>";

				function <portlet:namespace />onChanged() {
					changed = true;
				}

				Liferay.provide(
					window,
					'<portlet:namespace />updateLanguage',
					function() {
						var A = AUI();

						if (lastLanguageId != '<%= defaultLanguageId %>') {
							if (changed) {
								for (var i = 0; i < 10; i++) {
									var priorityName = A.one('#<portlet:namespace />priorityName' + i + '_temp').val();
									var priorityImage = A.one('#<portlet:namespace />priorityImage' + i + '_temp').val();
									var priorityValue = A.one('#<portlet:namespace />priorityValue' + i + '_temp').val();

									A.one('#<portlet:namespace />priorityName' + i + '_' + lastLanguageId).val(priorityName);
									A.one('#<portlet:namespace />priorityImage' + i + '_' + lastLanguageId).val(priorityImage);
									A.one('#<portlet:namespace />priorityValue' + i + '_' + lastLanguageId).val(priorityValue);
								}

								changed = false;
							}
						}

						var selLanguageId = A.one(document.<portlet:namespace />fm.<portlet:namespace />languageId).val();

						var localizedPriorityTable = A.one('#<portlet:namespace />localized-priorities-table');

						if ((selLanguageId != '') && (selLanguageId != 'null')) {
							<portlet:namespace />updateLanguageTemps(selLanguageId);

							localizedPriorityTable.show();
						}
						else {
							localizedPriorityTable.hide();
						}

						lastLanguageId = selLanguageId;
					},
					['aui-base']
				);

				Liferay.provide(
					window,
					'<portlet:namespace />updateLanguageTemps',
					function(lang) {
						var A = AUI();

						if (lang != '<%= defaultLanguageId %>') {
							for (var i = 0; i < 10; i++) {
								var defaultName = A.one('#<portlet:namespace />priorityName' + i + '_' + '<%= defaultLanguageId %>').val();
								var defaultImage = A.one('#<portlet:namespace />priorityImage' + i + '_' + '<%= defaultLanguageId %>').val();
								var defaultValue = A.one('#<portlet:namespace />priorityValue' + i + '_' + '<%= defaultLanguageId %>').val();

								var priorityName = A.one('#<portlet:namespace />priorityName' + i + '_' + lang).val();
								var priorityImage = A.one('#<portlet:namespace />priorityImage' + i + '_' + lang).val();
								var priorityValue = A.one('#<portlet:namespace />priorityValue' + i + '_' + lang).val();

								var name = priorityName || defaultName;
								var image = priorityImage || defaultImage;
								var value = priorityValue || defaultValue;

								A.one('#<portlet:namespace />priorityName' + i + '_temp').val(name);
								A.one('#<portlet:namespace />priorityImage' + i + '_temp').val(image);
								A.one('#<portlet:namespace />priorityValue' + i + '_temp').val(value);
							}
						}
					},
					['aui-base']
				);

				<portlet:namespace />updateLanguageTemps(lastLanguageId);
			</aui:script>
		</c:when>
		<c:when test='<%= tabs2.equals("user-ranks") %>'>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-rank-and-minimum-post-pairs-per-line" />
			</div>

			<aui:fieldset>
				<table class="lfr-table">
				<tr>
					<td class="lfr-label">
						<aui:field-wrapper label="default-language">
							<liferay-ui:input-resource url="<%= defaultLocale.getDisplayName(defaultLocale) %>" />
						</aui:field-wrapper>
					</td>
					<td class="lfr-label">
						<aui:select label="localized-language" name="languageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage();" %>' showEmptyOption="<%= true %>">

							<%
							for (int i = 0; i < locales.length; i++) {
								if (locales[i].equals(defaultLocale)) {
									continue;
								}
							%>

								<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

							<%
							}
							%>

						</aui:select>
					</td>
				</tr>
				<tr>
					<td>
						<aui:input cssClass="lfr-textarea-container" label="" name='<%= "ranks_" + defaultLanguageId %>' type="textarea" value='<%= StringUtil.merge(LocalizationUtil.getPreferencesValues(portletPreferences, "ranks", defaultLanguageId), StringPool.NEW_LINE) %>' />
					</td>
					<td>

						<%
						for (int i = 0; i < locales.length; i++) {
							if (locales[i].equals(defaultLocale)) {
								continue;
							}
						%>

							<aui:input name='<%= "ranks_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value='<%= StringUtil.merge(LocalizationUtil.getPreferencesValues(portletPreferences, "ranks", LocaleUtil.toLanguageId(locales[i]), false), StringPool.NEW_LINE) %>' />

						<%
						}
						%>

						<aui:input cssClass="lfr-textarea-container" label="" name="ranks_temp" onChange='<%= renderResponse.getNamespace() + "onRanksChanged();" %>' type="textarea" />
					</td>
				</tr>
				</table>
			</aui:fieldset>

			<aui:script>
				var ranksChanged = false;
				var lastLanguageId = '<%= currentLanguageId %>';

				function <portlet:namespace />onRanksChanged() {
					ranksChanged = true;
				}

				Liferay.provide(
					window,
					'<portlet:namespace />updateLanguage',
					function() {
						var A = AUI();

						if (lastLanguageId != '<%= defaultLanguageId %>') {
							if (ranksChanged) {
								var ranksValue = A.one('#<portlet:namespace />ranks_temp').val();

								if (ranksValue == null) {
									ranksValue = '';
								}

								A.one('#<portlet:namespace />ranks_' + lastLanguageId).val(ranksValue);

								ranksChanged = false;
							}
						}

						var selLanguageId = A.one(document.<portlet:namespace />fm.<portlet:namespace />languageId).val();

						var ranksTemp = A.one('#<portlet:namespace />ranks_temp');

						if ((selLanguageId != '') && (selLanguageId != 'null')) {
							<portlet:namespace />updateLanguageTemps(selLanguageId);

							ranksTemp.show();
						}
						else {
							ranksTemp.hide();
						}

						lastLanguageId = selLanguageId;
					},
					['aui-base']
				);

				Liferay.provide(
					window,
					'<portlet:namespace />updateLanguageTemps',
					function(lang) {
						var A = AUI();

						if (lang != '<%= defaultLanguageId %>') {
							var ranksValue = A.one('#<portlet:namespace />ranks_' + lang).val();
							var defaultRanksValue = A.one('#<portlet:namespace />ranks_<%= defaultLanguageId %>').val();

							var value = ranksValue || defaultRanksValue;

							A.one('#<portlet:namespace />ranks_temp').val(value);
						}
					},
					['aui-base']
				);

				<portlet:namespace />updateLanguageTemps(lastLanguageId);
			</aui:script>
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
	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("user-ranks") || tabs2.equals("thread-priorities") %>'>
			<portlet:namespace />updateLanguage();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>