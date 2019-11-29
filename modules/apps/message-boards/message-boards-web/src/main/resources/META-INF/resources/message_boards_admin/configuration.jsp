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

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs2Names = "general,email-from,message-added-email,message-updated-email,thread-priorities,user-ranks";

	if (PortalUtil.isRSSFeedsEnabled()) {
		tabs2Names += ",rss";
	}
	%>

	<liferay-frontend:edit-form-body>
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
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input name="preferences--allowAnonymousPosting--" type="checkbox" value="<%= mbGroupServiceSettings.isAllowAnonymousPosting() %>" />

						<aui:input helpMessage="message-boards-message-subscribe-by-default-help" label="subscribe-by-default" name="preferences--subscribeByDefault--" type="checkbox" value="<%= subscribeByDefault %>" />

						<aui:select name="preferences--messageFormat--">

							<%
							for (int i = 0; i < MBMessageConstants.FORMATS.length; i++) {
							%>

								<c:if test="<%= com.liferay.message.boards.util.MBUtil.isValidMessageFormat(MBMessageConstants.FORMATS[i]) %>">
									<aui:option label='<%= LanguageUtil.get(request, "message-boards.message-formats." + MBMessageConstants.FORMATS[i]) %>' selected="<%= messageFormat.equals(MBMessageConstants.FORMATS[i]) %>" value="<%= MBMessageConstants.FORMATS[i] %>" />
								</c:if>

							<%
							}
							%>

						</aui:select>

						<aui:input label="enable-report-inappropriate-content" name="preferences--enableFlags--" type="checkbox" value="<%= enableFlags %>" />

						<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />

						<aui:input name="preferences--threadAsQuestionByDefault--" type="checkbox" value="<%= threadAsQuestionByDefault %>" />

						<aui:select label="show-recent-posts-from-last" name="preferences--recentPostsDateOffset--" value="<%= mbGroupServiceSettings.getRecentPostsDateOffset() %>">
							<aui:option label='<%= LanguageUtil.format(request, "x-hours", "24", false) %>' value="1" />
							<aui:option label='<%= LanguageUtil.format(request, "x-days", "7", false) %>' value="7" />
							<aui:option label='<%= LanguageUtil.format(request, "x-days", "30", false) %>' value="30" />
							<aui:option label='<%= LanguageUtil.format(request, "x-days", "365", false) %>' value="365" />
						</aui:select>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= mbGroupServiceSettings.getEmailFromName() %>" />

						<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= mbGroupServiceSettings.getEmailFromAddress() %>" />

						<aui:input label="html-format" name="preferences--emailHtmlFormat--" type="checkbox" value="<%= mbGroupServiceSettings.isEmailHtmlFormat() %>" />
					</liferay-frontend:fieldset>

					<liferay-frontend:fieldset
						collapsed="<%= true %>"
						collapsible="<%= true %>"
						label="definition-of-terms"
					>
						<dl>

							<%
							Map<String, String> emailDefinitionTerms = MBMailUtil.getEmailFromDefinitionTerms(renderRequest);

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
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<%
			Map<String, String> emailDefinitionTerms = MBMailUtil.getEmailDefinitionTerms(renderRequest, mbGroupServiceSettings.getEmailFromAddress(), mbGroupServiceSettings.getEmailFromName());
			%>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBody="<%= mbGroupServiceSettings.getEmailMessageAddedBodyXml() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= mbGroupServiceSettings.isEmailMessageAddedEnabled() %>"
						emailParam="emailMessageAdded"
						emailSubject="<%= mbGroupServiceSettings.getEmailMessageAddedSubjectXml() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBody="<%= mbGroupServiceSettings.getEmailMessageUpdatedBodyXml() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= mbGroupServiceSettings.isEmailMessageUpdatedEnabled() %>"
						emailParam="emailMessageUpdated"
						emailSubject="<%= mbGroupServiceSettings.getEmailMessageUpdatedSubjectXml() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="alert alert-info">
					<liferay-ui:message key="enter-the-name,-image,-and-priority-level-in-descending-order" />
				</div>

				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
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
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="alert alert-info">
					<liferay-ui:message key="enter-rank-and-minimum-post-pairs-per-line" />
				</div>

				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
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

										<aui:input name='<%= "ranks_" + LocaleUtil.toLanguageId(curLocale) %>' type="textarea" value="<%= StringUtil.merge(mbGroupServiceSettings.getRanks(LocaleUtil.toLanguageId(curLocale)), StringPool.NEW_LINE) %>" wrapperCssClass="hide" />

									<%
									}
									%>

									<aui:input cssClass="hide lfr-textarea-container" label="" name="ranks_temp" onChange='<%= renderResponse.getNamespace() + "onRanksChanged();" %>' title="ranks" type="textarea" />
								</td>
							</tr>
						</table>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
				<liferay-ui:section>
					<liferay-frontend:fieldset-group>
						<liferay-rss:rss-settings
							delta="<%= rssDelta %>"
							displayStyle="<%= rssDisplayStyle %>"
							enabled="<%= enableRSS %>"
							feedType="<%= rssFeedType %>"
						/>
					</liferay-frontend:fieldset-group>
				</liferay-ui:section>
			</c:if>
		</liferay-ui:tabs>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	var <portlet:namespace />ranksChanged = false;
	var <portlet:namespace />ranksLastLanguageId = '<%= currentLanguageId %>';

	function <portlet:namespace />onRanksChanged() {
		<portlet:namespace />ranksChanged = true;
	}

	function <portlet:namespace />updateRanksLanguage() {
		var form = document.<portlet:namespace />fm;

		var ranksTempTextarea = Liferay.Util.getFormElement(form, 'ranks_temp');

		if (ranksTempTextarea) {
			if (
				<portlet:namespace />ranksChanged &&
				<portlet:namespace />ranksLastLanguageId !=
					'<%= defaultLanguageId %>'
			) {
				var data = {};

				data['ranks_' + <portlet:namespace />ranksLastLanguageId] =
					ranksTempTextarea.value;

				Liferay.Util.setFormValues(form, data);

				<portlet:namespace />ranksChanged = false;
			}

			var selLanguageInput = Liferay.Util.getFormElement(
				form,
				'ranksLanguageId'
			);

			if (selLanguageInput) {
				var selLanguageId = selLanguageInput.value;

				if (selLanguageId) {
					<portlet:namespace />updateRanksLanguageTemps(selLanguageId);

					ranksTempTextarea.classList.remove('hide');

					<portlet:namespace />ranksLastLanguageId = selLanguageId;
				}
				else {
					ranksTempTextarea.classList.add('hide');
				}
			}
		}
	}

	function <portlet:namespace />updateRanksLanguageTemps(lang) {
		var form = document.<portlet:namespace />fm;

		if (lang != '<%= defaultLanguageId %>') {
			var defaultRanksInput = Liferay.Util.getFormElement(
				form,
				'ranks_<%= defaultLanguageId %>'
			);

			if (defaultRanksInput) {
				var defaultRanksValue = defaultRanksInput.value;
			}

			var ranksInput = Liferay.Util.getFormElement(form, 'ranks_' + lang);

			if (ranksInput) {
				var ranksValue = ranksInput.value;
			}

			var value = ranksValue || defaultRanksValue;

			Liferay.Util.setFormValues(form, {
				ranks_temp: value
			});
		}
	}

	var <portlet:namespace />prioritiesChanged = false;
	var <portlet:namespace />prioritiesLastLanguageId = '<%= currentLanguageId %>';

	function <portlet:namespace />onPrioritiesChanged() {
		<portlet:namespace />prioritiesChanged = true;
	}

	function <portlet:namespace />updatePrioritiesLanguage() {
		var form = document.<portlet:namespace />fm;

		if (
			<portlet:namespace />prioritiesChanged &&
			<portlet:namespace />prioritiesLastLanguageId !=
				'<%= defaultLanguageId %>'
		) {
			for (var i = 0; i < 10; i++) {
				var priorityImage = Liferay.Util.getFormElement(
					form,
					'priorityImage' + i + '_temp'
				).value;
				var priorityName = Liferay.Util.getFormElement(
					form,
					'priorityName' + i + '_temp'
				).value;
				var priorityValue = Liferay.Util.getFormElement(
					form,
					'priorityValue' + i + '_temp'
				).value;

				var data = {};

				data[
					'priorityImage' +
						i +
						'_' +
						<portlet:namespace />prioritiesLastLanguageId
				] = priorityImage;
				data[
					'priorityName' +
						i +
						'_' +
						<portlet:namespace />prioritiesLastLanguageId
				] = priorityName;
				data[
					'priorityValue' +
						i +
						'_' +
						<portlet:namespace />prioritiesLastLanguageId
				] = priorityValue;

				Liferay.Util.setFormValues(form, data);
			}

			<portlet:namespace />prioritiesChanged = false;
		}

		var selLanguageInput = Liferay.Util.getFormElement(
			form,
			'prioritiesLanguageId'
		);

		if (selLanguageInput) {
			var selLanguageId = selLanguageInput.value;

			if (selLanguageId) {
				<portlet:namespace />updatePrioritiesLanguageTemps(selLanguageId);
			}

			var localizedPrioritiesTable = document.getElementById(
				'<portlet:namespace />localized-priorities-table'
			);

			if (localizedPrioritiesTable) {
				if (selLanguageId) {
					localizedPrioritiesTable.classList.remove('hide');
				}
				else {
					localizedPrioritiesTable.classList.add('hide');
				}
			}

			<portlet:namespace />prioritiesLastLanguageId = selLanguageId;
		}
	}

	function <portlet:namespace />updatePrioritiesLanguageTemps(lang) {
		var form = document.<portlet:namespace />fm;

		if (lang != '<%= defaultLanguageId %>') {
			for (var i = 0; i < 10; i++) {
				var defaultImageInput = Liferay.Util.getFormElement(
					form,
					'priorityImage' + i + '_<%= defaultLanguageId %>'
				);
				var priorityImageInput = Liferay.Util.getFormElement(
					form,
					'priorityImage' + i + '_' + lang
				);

				if (defaultImageInput && priorityImageInput) {
					var defaultImage = defaultImageInput.value;
					var priorityImage = priorityImageInput.value;

					var image = priorityImage || defaultImage;
				}

				var defaultNameInput = Liferay.Util.getFormElement(
					form,
					'priorityName' + i + '_<%= defaultLanguageId %>'
				);
				var priorityNameInput = Liferay.Util.getFormElement(
					form,
					'priorityName' + i + '_' + lang
				);

				if (defaultNameInput && priorityNameInput) {
					var defaultName = defaultNameInput.value;
					var priorityName = priorityNameInput.value;

					var name = priorityName || defaultName;
				}

				var defaultValueInput = Liferay.Util.getFormElement(
					form,
					'priorityValue' + i + '_<%= defaultLanguageId %>'
				);
				var priorityValueInput = Liferay.Util.getFormElement(
					form,
					'priorityValue' + i + '_' + lang
				);

				if (defaultValueInput && priorityValueInput) {
					var defaultValue = defaultValueInput.value;
					var priorityValue = priorityValueInput.value;

					var value = priorityValue || defaultValue;
				}

				var data = {};

				if (name && image && value) {
					data['priorityName' + i + '_temp'] = name;
					data['priorityImage' + i + '_temp'] = image;
					data['priorityValue' + i + '_temp'] = value;
				}

				Liferay.Util.setFormValues(form, data);
			}
		}
	}

	<portlet:namespace />updatePrioritiesLanguageTemps(
		<portlet:namespace />prioritiesLastLanguageId
	);

	<portlet:namespace />updateRanksLanguageTemps(
		<portlet:namespace />ranksLastLanguageId
	);

	function <portlet:namespace />saveConfiguration() {
		<portlet:namespace />saveEmails();
		<portlet:namespace />updatePrioritiesLanguage();
		<portlet:namespace />updateRanksLanguage();

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveEmails() {
		var form = document.<portlet:namespace />fm;

		var emailMessageAdded = window['<portlet:namespace />emailMessageAdded'];

		if (emailMessageAdded) {
			Liferay.Util.setFormValues(form, {
				'preferences--emailMessageAddedBody--': emailMessageAdded.getHTML()
			});
		}

		var emailMessageUpdated =
			window['<portlet:namespace />emailMessageUpdated'];

		if (emailMessageUpdated) {
			Liferay.Util.setFormValues(form, {
				'preferences--emailMessageUpdatedBody--': emailMessageUpdated.getHTML()
			});
		}
	}
</script>