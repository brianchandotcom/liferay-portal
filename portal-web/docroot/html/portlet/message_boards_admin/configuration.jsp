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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
mbSettings = MBSettings.getInstance(themeDisplay.getSiteGroupId(), request.getParameterMap());
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= MBConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs2Names = "general,email-from,message-added-email,message-updated-email,thread-priorities,user-ranks";

	if (PortalUtil.isRSSFeedsEnabled()) {
		tabs2Names += ",rss";
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		refresh="<%= false %>"
	>
		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
		<liferay-ui:error key="emailMessageAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailMessageAddedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailMessageUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailMessageUpdatedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="userRank" message="please-enter-valid-user-ranks" />

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input name="preferences--allowAnonymousPosting--" type="checkbox" value="<%= mbSettings.isAllowAnonymousPosting() %>" />

				<aui:input helpMessage="message-boards-message-subscribe-by-default-help" label="subscribe-by-default" name="preferences--subscribeByDefault--" type="checkbox" value="<%= subscribeByDefault %>" />

				<aui:select name="preferences--messageFormat--">

					<%
					for (int i = 0; i < MBMessageConstants.FORMATS.length; i++) {
					%>

						<c:if test="<%= MBUtil.isValidMessageFormat(MBMessageConstants.FORMATS[i]) %>">
							<aui:option label='<%= LanguageUtil.get(request,"message-boards.message-formats." + MBMessageConstants.FORMATS[i]) %>' selected="<%= messageFormat.equals(MBMessageConstants.FORMATS[i]) %>" value="<%= MBMessageConstants.FORMATS[i] %>" />
						</c:if>

					<%
					}
					%>

				</aui:select>

				<aui:input name="preferences--enableFlags--" type="checkbox" value="<%= enableFlags %>" />

				<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />

				<aui:input name="preferences--threadAsQuestionByDefault--" type="checkbox" value="<%= threadAsQuestionByDefault %>" />

				<aui:select label="show-recent-posts-from-last" name="preferences--recentPostsDateOffset--" value="<%= recentPostsDateOffset %>">
					<aui:option label='<%= LanguageUtil.format(request, "x-hours", "24", false) %>' value="1" />
					<aui:option label='<%= LanguageUtil.format(request, "x-days", "7", false) %>' value="7" />
					<aui:option label='<%= LanguageUtil.format(request, "x-days", "30", false) %>' value="30" />
					<aui:option label='<%= LanguageUtil.format(request, "x-days", "365", false) %>' value="365" />
				</aui:select>
			</aui:fieldset>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= mbSettings.getEmailFromName() %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= mbSettings.getEmailFromAddress() %>" />

				<aui:input label="html-format" name="preferences--emailHtmlFormat--" type="checkbox" value="<%= mbSettings.isEmailHtmlFormat() %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms" label="definition-of-terms">
				<dl>

					<%
					Map<String, String> emailDefinitionTerms = MBUtil.getEmailFromDefinitionTerms(renderRequest);

					for (Map.Entry<String, String> entry : emailDefinitionTerms.entrySet()) {
					%>

						<dt>
							<%= entry.getKey() %>
						</dt>
						<dd>
							<%= entry.getValue() %>
						</dd>

					<%
					}
					%>

				</dl>
			</aui:fieldset>
		</liferay-ui:section>

		<%
		Map<String, String> emailDefinitionTerms = MBUtil.getEmailDefinitionTerms(renderRequest, mbSettings.getEmailFromAddress(), mbSettings.getEmailFromName());
		%>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= mbSettings.getEmailMessageAddedBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= mbSettings.isEmailMessageAddedEnabled() %>"
				emailParam="emailMessageAdded"
				emailSubject="<%= mbSettings.getEmailMessageAddedSubjectXml() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= mbSettings.getEmailMessageUpdatedBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= mbSettings.isEmailMessageUpdatedEnabled() %>"
				emailParam="emailMessageUpdated"
				emailSubject="<%= mbSettings.getEmailMessageUpdatedSubjectXml() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-the-name,-image,-and-priority-level-in-descending-order" />
			</div>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<aui:input name="defaultLanguage" type="resource" value="<%= defaultLocale.getDisplayName(defaultLocale) %>" />
				</td>
				<td>
					<aui:select label="localized-language" name="prioritiesLanguageId" onClick='<%= renderResponse.getNamespace() + "updatePrioritiesLanguage();" %>' showEmptyOption="<%= true %>">

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
					priorities = mbSettings.getPriorities(defaultLanguageId);

					for (int i = 0; i < 10; i++) {
						String name = StringPool.BLANK;
						String image = StringPool.BLANK;
						String value = StringPool.BLANK;

						if (priorities.length > i) {
							String[] priority = StringUtil.split(priorities[i], StringPool.PIPE);

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
								<aui:input label="" name='<%= "priorityName" + i + "_" + defaultLanguageId %>' size="15" title="priority-name" value="<%= name %>" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityImage" + i + "_" + defaultLanguageId %>' size="40" title="priority-image" value="<%= image %>" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityValue" + i + "_" + defaultLanguageId %>' size="4" title="priority-value" value="<%= value %>" />
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
								<aui:input label="" name='<%= "priorityName" + i + "_temp" %>' onChange='<%= renderResponse.getNamespace() + "onPrioritiesChanged();" %>' size="15" title="priority-name" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityImage" + i + "_temp" %>' onChange='<%= renderResponse.getNamespace() + "onPrioritiesChanged();" %>' size="40" title="priority-image" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityValue" + i + "_temp" %>' onChange='<%= renderResponse.getNamespace() + "onPrioritiesChanged();" %>' size="4" title="priority-value" />
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

						String[] tempPriorities = mbSettings.getPriorities(LocaleUtil.toLanguageId(locales[i]));

						for (int j = 0; j < 10; j++) {
							String name = StringPool.BLANK;
							String image = StringPool.BLANK;
							String value = StringPool.BLANK;

							if (tempPriorities.length > j) {
								String[] priority = StringUtil.split(tempPriorities[j], StringPool.PIPE);

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
				var prioritiesChanged = false;
				var prioritiesLastLanguageId = '<%= currentLanguageId %>';

				function <portlet:namespace />onPrioritiesChanged() {
					prioritiesChanged = true;
				}

				Liferay.provide(
					window,
					'<portlet:namespace />updatePrioritiesLanguage',
					function() {
						var A = AUI();

						if (prioritiesLastLanguageId != '<%= defaultLanguageId %>') {
							if (prioritiesChanged) {
								for (var i = 0; i < 10; i++) {
									var priorityName = A.one('#<portlet:namespace />priorityName' + i + '_temp').val();
									var priorityImage = A.one('#<portlet:namespace />priorityImage' + i + '_temp').val();
									var priorityValue = A.one('#<portlet:namespace />priorityValue' + i + '_temp').val();

									A.one('#<portlet:namespace />priorityName' + i + '_' + prioritiesLastLanguageId).val(priorityName);
									A.one('#<portlet:namespace />priorityImage' + i + '_' + prioritiesLastLanguageId).val(priorityImage);
									A.one('#<portlet:namespace />priorityValue' + i + '_' + prioritiesLastLanguageId).val(priorityValue);
								}

								prioritiesChanged = false;
							}
						}

						var selLanguageId = A.one(document.<portlet:namespace />fm.<portlet:namespace />prioritiesLanguageId).val();

						var localizedPriorityTable = A.one('#<portlet:namespace />localized-priorities-table');

						if ((selLanguageId != '') && (selLanguageId != 'null')) {
							<portlet:namespace />updatePrioritiesLanguageTemps(selLanguageId);

							localizedPriorityTable.show();
						}
						else {
							localizedPriorityTable.hide();
						}

						prioritiesLastLanguageId = selLanguageId;
					},
					['aui-base']
				);

				Liferay.provide(
					window,
					'<portlet:namespace />updatePrioritiesLanguageTemps',
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

				<portlet:namespace />updatePrioritiesLanguageTemps(prioritiesLastLanguageId);
			</aui:script>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="enter-rank-and-minimum-post-pairs-per-line" />
			</div>

			<aui:fieldset>
				<table class="lfr-table">
				<tr>
					<td class="lfr-label">
						<aui:input name="defaultLanguage" type="resource" value="<%= defaultLocale.getDisplayName(defaultLocale) %>" />
					</td>
					<td class="lfr-label">
						<aui:select label="localized-language" name="ranksLanguageId" onChange='<%= renderResponse.getNamespace() + "updateRanksLanguage();" %>' showEmptyOption="<%= true %>">

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
						<aui:input cssClass="lfr-textarea-container" label="" name='<%= "ranks_" + defaultLanguageId %>' title="ranks" type="textarea" value="<%= StringUtil.merge(mbSettings.getRanks(defaultLanguageId), StringPool.NEW_LINE) %>" />
					</td>
					<td>

						<%
						for (int i = 0; i < locales.length; i++) {
							if (locales[i].equals(defaultLocale)) {
								continue;
							}
						%>

							<aui:input name='<%= "ranks_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= StringUtil.merge(mbSettings.getRanks(LocaleUtil.toLanguageId(locales[i])), StringPool.NEW_LINE) %>" />

						<%
						}
						%>

						<aui:input cssClass="lfr-textarea-container" label="" name="ranks_temp" onChange='<%= renderResponse.getNamespace() + "onRanksChanged();" %>' title="ranks" type="textarea" />
					</td>
				</tr>
				</table>
			</aui:fieldset>

			<aui:script>
				var ranksChanged = false;
				var ranksLastLanguageId = '<%= currentLanguageId %>';

				function <portlet:namespace />onRanksChanged() {
					ranksChanged = true;
				}

				Liferay.provide(
					window,
					'<portlet:namespace />updateRanksLanguage',
					function() {
						var A = AUI();

						if (ranksLastLanguageId != '<%= defaultLanguageId %>') {
							if (ranksChanged) {
								var ranksValue = A.one('#<portlet:namespace />ranks_temp').val();

								if (ranksValue == null) {
									ranksValue = '';
								}

								A.one('#<portlet:namespace />ranks_' + ranksLastLanguageId).val(ranksValue);

								ranksChanged = false;
							}
						}

						var selLanguageId = A.one(document.<portlet:namespace />fm.<portlet:namespace />ranksLanguageId).val();

						var ranksTemp = A.one('#<portlet:namespace />ranks_temp');

						if ((selLanguageId != '') && (selLanguageId != 'null')) {
							<portlet:namespace />updateRanksLanguageTemps(selLanguageId);

							ranksTemp.show();
						}
						else {
							ranksTemp.hide();
						}

						ranksLastLanguageId = selLanguageId;
					},
					['aui-base']
				);

				Liferay.provide(
					window,
					'<portlet:namespace />updateRanksLanguageTemps',
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

				<portlet:namespace />updateRanksLanguageTemps(ranksLastLanguageId);
			</aui:script>
		</liferay-ui:section>

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-ui:section>
				<liferay-ui:rss-settings
					delta="<%= rssDelta %>"
					displayStyle="<%= rssDisplayStyle %>"
					enabled="<%= enableRSS %>"
					feedType="<%= rssFeedType %>"
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
		<portlet:namespace />updatePrioritiesLanguage();
		<portlet:namespace />updateRanksLanguage();

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailMessageAddedBody--'].value = window['<portlet:namespace />emailMessageAdded'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailMessageUpdatedBody--'].value = window['<portlet:namespace />emailMessageUpdated'].getHTML();
		}
		catch (e) {
		}
	}
</aui:script>