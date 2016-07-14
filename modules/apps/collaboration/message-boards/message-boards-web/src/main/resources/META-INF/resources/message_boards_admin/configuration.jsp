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

<%@ include file="/message_boards/init.jsp" %>

<%
Set<Locale> locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

mbGroupServiceSettings = MBGroupServiceSettings.getInstance(themeDisplay.getSiteGroupId(), request.getParameterMap());
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

	<div class="portlet-configuration-body-content">
		<liferay-ui:tabs
			names="<%= tabs2Names %>"
			refresh="<%= false %>"
			type="tabs nav-tabs-default"
		>
			<div class="container-fluid-1280">
				<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
				<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
				<liferay-ui:error key="emailMessageAddedBody" message="please-enter-a-valid-body" />
				<liferay-ui:error key="emailMessageAddedSubject" message="please-enter-a-valid-subject" />
				<liferay-ui:error key="emailMessageUpdatedBody" message="please-enter-a-valid-body" />
				<liferay-ui:error key="emailMessageUpdatedSubject" message="please-enter-a-valid-subject" />
				<liferay-ui:error key="userRank" message="please-enter-valid-user-ranks" />
			</div>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset>
							<aui:input name="preferences--allowAnonymousPosting--" type="checkbox" value="<%= mbGroupServiceSettings.isAllowAnonymousPosting() %>" />

							<aui:input helpMessage="message-boards-message-subscribe-by-default-help" label="subscribe-by-default" name="preferences--subscribeByDefault--" type="checkbox" value="<%= subscribeByDefault %>" />

							<aui:select name="preferences--messageFormat--">

								<%
								for (int i = 0; i < MBMessageConstants.FORMATS.length; i++) {
								%>

									<c:if test="<%= MBUtil.isValidMessageFormat(MBMessageConstants.FORMATS[i]) %>">
										<aui:option label='<%= LanguageUtil.get(request, "message-boards.message-formats." + MBMessageConstants.FORMATS[i]) %>' selected="<%= messageFormat.equals(MBMessageConstants.FORMATS[i]) %>" value="<%= MBMessageConstants.FORMATS[i] %>" />
									</c:if>

								<%
								}
								%>

							</aui:select>

							<aui:input name="preferences--enableFlags--" type="checkbox" value="<%= enableFlags %>" />

							<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />

							<aui:input name="preferences--threadAsQuestionByDefault--" type="checkbox" value="<%= threadAsQuestionByDefault %>" />

							<aui:select label="show-recent-posts-from-last" name="preferences--recentPostsDateOffset--" value="<%= mbGroupServiceSettings.getRecentPostsDateOffset() %>">
								<aui:option label='<%= LanguageUtil.format(request, "x-hours", "24", false) %>' value="1" />
								<aui:option label='<%= LanguageUtil.format(request, "x-days", "7", false) %>' value="7" />
								<aui:option label='<%= LanguageUtil.format(request, "x-days", "30", false) %>' value="30" />
								<aui:option label='<%= LanguageUtil.format(request, "x-days", "365", false) %>' value="365" />
							</aui:select>
						</aui:fieldset>
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset>
							<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= mbGroupServiceSettings.getEmailFromName() %>" />

							<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= mbGroupServiceSettings.getEmailFromAddress() %>" />

							<aui:input label="html-format" name="preferences--emailHtmlFormat--" type="checkbox" value="<%= mbGroupServiceSettings.isEmailHtmlFormat() %>" />
						</aui:fieldset>

						<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="definition-of-terms">
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
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<%
			Map<String, String> emailDefinitionTerms = MBUtil.getEmailDefinitionTerms(renderRequest, mbGroupServiceSettings.getEmailFromAddress(), mbGroupServiceSettings.getEmailFromName());
			%>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<liferay-frontend:email-notification-settings
							emailBody="<%= mbGroupServiceSettings.getEmailMessageAddedBodyXml() %>"
							emailDefinitionTerms="<%= emailDefinitionTerms %>"
							emailEnabled="<%= mbGroupServiceSettings.isEmailMessageAddedEnabled() %>"
							emailParam="emailMessageAdded"
							emailSubject="<%= mbGroupServiceSettings.getEmailMessageAddedSubjectXml() %>"
						/>
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<liferay-frontend:email-notification-settings
							emailBody="<%= mbGroupServiceSettings.getEmailMessageUpdatedBodyXml() %>"
							emailDefinitionTerms="<%= emailDefinitionTerms %>"
							emailEnabled="<%= mbGroupServiceSettings.isEmailMessageUpdatedEnabled() %>"
							emailParam="emailMessageUpdated"
							emailSubject="<%= mbGroupServiceSettings.getEmailMessageUpdatedSubjectXml() %>"
						/>
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<div class="alert alert-info">
						<liferay-ui:message key="enter-the-name,-image,-and-priority-level-in-descending-order" />
					</div>

					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset>
							<table class="lfr-table">
								<tr>
									<td>
										<aui:input name="defaultLanguage" type="resource" value="<%= defaultLocale.getDisplayName(defaultLocale) %>" />
									</td>
									<td>
										<aui:select label="localized-language" name="prioritiesLanguageId" onChange='<%= renderResponse.getNamespace() + "updatePrioritiesLanguage();" %>' showEmptyOption="<%= true %>">

											<%
											for (Locale curLocale : locales) {
												if (curLocale.equals(defaultLocale)) {
													continue;
												}
											%>

												<aui:option label="<%= curLocale.getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(curLocale)) %>" value="<%= LocaleUtil.toLanguageId(curLocale) %>" />

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
											priorities = mbGroupServiceSettings.getPriorities(defaultLanguageId);

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
										<table class="<%= (currentLocale.equals(defaultLocale) ? "hide" : "") + " lfr-table" %>" id="<portlet:namespace />localized-priorities-table">
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
										for (Locale curLocale : locales) {
											if (curLocale.equals(defaultLocale)) {
												continue;
											}

											String[] tempPriorities = mbGroupServiceSettings.getPriorities(LocaleUtil.toLanguageId(curLocale));

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

												<aui:input name='<%= "priorityName" + j + "_" + LocaleUtil.toLanguageId(curLocale) %>' type="hidden" value="<%= name %>" />
												<aui:input name='<%= "priorityImage" + j + "_" + LocaleUtil.toLanguageId(curLocale) %>' type="hidden" value="<%= image %>" />
												<aui:input name='<%= "priorityValue" + j + "_" + LocaleUtil.toLanguageId(curLocale) %>' type="hidden" value="<%= value %>" />

										<%
											}
										}
										%>

									</td>
								</tr>
							</table>
						</aui:fieldset>
					</aui:fieldset-group>
				</div>

				<aui:script>
					var <portlet:namespace />prioritiesChanged = false;
					var <portlet:namespace />prioritiesLastLanguageId = '<%= currentLanguageId %>';

					function <portlet:namespace />onPrioritiesChanged() {
						<portlet:namespace />prioritiesChanged = true;
					}

					function <portlet:namespace />updatePrioritiesLanguage() {
						var $ = AUI.$;

						var form = $(document.<portlet:namespace />fm);

						if (<portlet:namespace />prioritiesChanged && (<portlet:namespace />prioritiesLastLanguageId != '<%= defaultLanguageId %>')) {
							for (var i = 0; i < 10; i++) {
								var priorityImage = form.fm('priorityImage' + i + '_temp').val();
								var priorityName = form.fm('priorityName' + i + '_temp').val();
								var priorityValue = form.fm('priorityValue' + i + '_temp').val();

								form.fm('priorityName' + i + '_' + <portlet:namespace />prioritiesLastLanguageId).val(priorityName);
								form.fm('priorityImage' + i + '_' + <portlet:namespace />prioritiesLastLanguageId).val(priorityImage);
								form.fm('priorityValue' + i + '_' + <portlet:namespace />prioritiesLastLanguageId).val(priorityValue);
							}

							<portlet:namespace />prioritiesChanged = false;
						}

						var selLanguageId = form.fm('prioritiesLanguageId').val();

						if (selLanguageId) {
							<portlet:namespace />updatePrioritiesLanguageTemps(selLanguageId);
						}

						$('#<portlet:namespace />localized-priorities-table').toggleClass('hide', !selLanguageId);

						<portlet:namespace />prioritiesLastLanguageId = selLanguageId;
					}

					function <portlet:namespace />updatePrioritiesLanguageTemps(lang) {
						var form = AUI.$(document.<portlet:namespace />fm);

						if (lang != '<%= defaultLanguageId %>') {
							for (var i = 0; i < 10; i++) {
								var defaultImage = form.fm('priorityImage' + i + '_' + '<%= defaultLanguageId %>').val();
								var defaultName = form.fm('priorityName' + i + '_' + '<%= defaultLanguageId %>').val();
								var defaultValue = form.fm('priorityValue' + i + '_' + '<%= defaultLanguageId %>').val();

								var priorityImage = form.fm('priorityImage' + i + '_' + lang).val();
								var priorityName = form.fm('priorityName' + i + '_' + lang).val();
								var priorityValue = form.fm('priorityValue' + i + '_' + lang).val();

								var image = priorityImage || defaultImage;
								var name = priorityName || defaultName;
								var value = priorityValue || defaultValue;

								form.fm('priorityName' + i + '_temp').val(name);
								form.fm('priorityImage' + i + '_temp').val(image);
								form.fm('priorityValue' + i + '_temp').val(value);
							}
						}
					}

					<portlet:namespace />updatePrioritiesLanguageTemps(<portlet:namespace />prioritiesLastLanguageId);
				</aui:script>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<div class="alert alert-info">
						<liferay-ui:message key="enter-rank-and-minimum-post-pairs-per-line" />
					</div>

					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset>
							<table class="lfr-table">
								<tr>
									<td class="lfr-label">
										<aui:input name="defaultLanguage" type="resource" value="<%= defaultLocale.getDisplayName(defaultLocale) %>" />
									</td>
									<td class="lfr-label">
										<aui:select label="localized-language" name="ranksLanguageId" onChange='<%= renderResponse.getNamespace() + "updateRanksLanguage();" %>' showEmptyOption="<%= true %>">

											<%
											for (Locale curLocale : locales) {
												if (curLocale.equals(defaultLocale)) {
													continue;
												}
											%>

												<aui:option label="<%= curLocale.getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(curLocale)) %>" value="<%= LocaleUtil.toLanguageId(curLocale) %>" />

											<%
											}
											%>

										</aui:select>
									</td>
								</tr>
								<tr>
									<td>
										<aui:input cssClass="lfr-textarea-container" label="" name='<%= "ranks_" + defaultLanguageId %>' title="ranks" type="textarea" value="<%= StringUtil.merge(mbGroupServiceSettings.getRanks(defaultLanguageId), StringPool.NEW_LINE) %>" />
									</td>
									<td>

										<%
										for (Locale curLocale : locales) {
											if (curLocale.equals(defaultLocale)) {
												continue;
											}
										%>

											<aui:input name='<%= "ranks_" + LocaleUtil.toLanguageId(curLocale) %>' type="hidden" value="<%= StringUtil.merge(mbGroupServiceSettings.getRanks(LocaleUtil.toLanguageId(curLocale)), StringPool.NEW_LINE) %>" />

										<%
										}
										%>

										<aui:input cssClass="hide lfr-textarea-container" label="" name="ranks_temp" onChange='<%= renderResponse.getNamespace() + "onRanksChanged();" %>' title="ranks" type="textarea" />
									</td>
								</tr>
							</table>
						</aui:fieldset>
					</aui:fieldset-group>
				</div>

				<aui:script>
					var <portlet:namespace />ranksChanged = false;
					var <portlet:namespace />ranksLastLanguageId = '<%= currentLanguageId %>';

					function <portlet:namespace />onRanksChanged() {
						<portlet:namespace />ranksChanged = true;
					}

					function <portlet:namespace />updateRanksLanguage() {
						var form = AUI.$(document.<portlet:namespace />fm);

						if (<portlet:namespace />ranksChanged && (<portlet:namespace />ranksLastLanguageId != '<%= defaultLanguageId %>')) {
							var ranksValue = form.fm('ranks_temp').val();

							form.fm('ranks_' + <portlet:namespace />ranksLastLanguageId).val(ranksValue);

							<portlet:namespace />ranksChanged = false;
						}

						var selLanguageId = form.fm('ranksLanguageId').val();

						if (selLanguageId) {
							<portlet:namespace />updateRanksLanguageTemps(selLanguageId);
						}

						form.fm('ranks_temp').toggleClass('hide', !selLanguageId);

						<portlet:namespace />ranksLastLanguageId = selLanguageId;
					}

					function <portlet:namespace />updateRanksLanguageTemps(lang) {
						var form = AUI.$(document.<portlet:namespace />fm);

						if (lang != '<%= defaultLanguageId %>') {
							var ranksValue = form.fm('ranks_' + lang).val();

							var defaultRanksValue = form.fm('ranks_<%= defaultLanguageId %>').val();

							var value = ranksValue || defaultRanksValue;

							form.fm('ranks_temp').val(value);
						}
					}

					<portlet:namespace />updateRanksLanguageTemps(<portlet:namespace />ranksLastLanguageId);
				</aui:script>
			</liferay-ui:section>

			<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
				<liferay-ui:section>
					<div class="container-fluid-1280">
						<aui:fieldset-group markupView="lexicon">
							<liferay-ui:rss-settings
								delta="<%= rssDelta %>"
								displayStyle="<%= rssDisplayStyle %>"
								enabled="<%= enableRSS %>"
								feedType="<%= rssFeedType %>"
							/>
						</aui:fieldset-group>
					</div>
				</liferay-ui:section>
			</c:if>
		</liferay-ui:tabs>
	</div>

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
		var form = AUI.$(document.<portlet:namespace />fm);

		var emailMessageAdded = window['<portlet:namespace />emailMessageAdded'];

		if (emailMessageAdded) {
			form.fm('preferences--emailMessageAddedBody--').val(emailMessageAdded.getHTML());
		}

		var emailMessageUpdated = window['<portlet:namespace />emailMessageUpdated'];

		if (emailMessageUpdated) {
			form.fm('preferences--emailMessageUpdatedBody--').val(emailMessageUpdated.getHTML());
		}
	}
</aui:script>