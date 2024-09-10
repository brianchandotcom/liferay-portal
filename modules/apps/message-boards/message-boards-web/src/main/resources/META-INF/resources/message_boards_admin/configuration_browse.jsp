<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/message_boards/init.jsp" %>

<%
Set<Locale> locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

MBConfigurationDisplayContext mbConfigurationDisplayContext = new MBConfigurationDisplayContext(request, renderRequest, renderResponse);

mbGroupServiceSettings = MBGroupServiceSettings.getInstance(themeDisplay.getSiteGroupId(), request.getParameterMap());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(mbConfigurationDisplayContext.getBackURL());
portletDisplay.setURLBackTitle("message-boards");
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="settings" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= mbConfigurationDisplayContext.getSettingsVerticalNavItemList() %>"
			/>

			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="notifications" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= mbConfigurationDisplayContext.getNotificationsVerticalNavItemList() %>"
			/>
		</clay:col>

		<clay:col
			lg="9"
		>
			<h2>
				<%= mbConfigurationDisplayContext.getTitle() %>
			</h2>

			<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
				<portlet:param name="navigation" value="<%= mbConfigurationDisplayContext.getNavigation() %>" />
				<portlet:param name="serviceName" value="<%= MBConstants.SERVICE_NAME %>" />
				<portlet:param name="settingsScope" value="group" />
			</liferay-portlet:actionURL>

			<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
				<clay:sheet
					cssClass="c-my-4 c-p-0"
					size="full"
				>
					<h3 class="c-pt-4 c-px-4 sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= mbConfigurationDisplayContext.getSubtitle() %>
							</clay:content-col>
						</clay:content-row>
					</h3>

					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

					<liferay-ui:error embed="<%= false %>" key="emailMessageAddedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailMessageAddedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailMessageUpdatedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailMessageUpdatedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="userRank" message="please-enter-valid-user-ranks" />

					<%
					Map<String, String> emailDefinitionTerms = MBMailUtil.getEmailDefinitionTerms(renderRequest, mbGroupServiceSettings.getEmailFromAddress(), mbGroupServiceSettings.getEmailFromName());
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(mbConfigurationDisplayContext.getNavigation(), "general") %>'>
							<div class="c-px-4">
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
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(mbConfigurationDisplayContext.getNavigation(), "email-from") %>'>
							<div class="c-px-4 panel-group-flush">
								<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= mbGroupServiceSettings.getEmailFromName() %>" />

								<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= mbGroupServiceSettings.getEmailFromAddress() %>" />

								<aui:input label="html-format" name="preferences--emailHtmlFormat--" type="checkbox" value="<%= mbGroupServiceSettings.isEmailHtmlFormat() %>" />
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(mbConfigurationDisplayContext.getNavigation(), "message-added-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= mbGroupServiceSettings.getEmailMessageAddedBodyXml() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= mbGroupServiceSettings.isEmailMessageAddedEnabled() %>"
									emailParam="emailMessageAdded"
									emailSubject="<%= mbGroupServiceSettings.getEmailMessageAddedSubjectXml() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(mbConfigurationDisplayContext.getNavigation(), "message-updated-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= mbGroupServiceSettings.getEmailMessageUpdatedBodyXml() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= mbGroupServiceSettings.isEmailMessageUpdatedEnabled() %>"
									emailParam="emailMessageUpdated"
									emailSubject="<%= mbGroupServiceSettings.getEmailMessageUpdatedSubjectXml() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(mbConfigurationDisplayContext.getNavigation(), "thread-priorities") %>'>
							<div class="c-px-4">
								<div class="alert alert-info">
									<liferay-ui:message key="enter-the-name,-image,-and-priority-level-in-descending-order" />
								</div>

								<table class="lfr-table">
									<tr>
										<td>
											<aui:input name="defaultLanguage" type="resource" value="<%= defaultLocale.getDisplayName(defaultLocale) %>" />
										</td>
										<td>
											<clay:select
												additionalProps='<%=
													HashMapBuilder.<String, Object>put(
														"defaultLanguageId", defaultLanguageId
													).build()
												%>'
												id='<%= liferayPortletResponse.getNamespace() + "prioritiesLanguageId" %>'
												label="localized-language"
												name="prioritiesLanguageId"
												options="<%= mbConfigurationDisplayContext.getSelectOptions(locales) %>"
												propsTransformer="{UpdatePrioritiesLanguagePropsTransformer} from message-boards-web"
											/>
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
											<table class="<%= currentLocale.equals(defaultLocale) ? "hide" : "" %> lfr-table" id="<portlet:namespace />localized-priorities-table">
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
															<aui:input label="" name='<%= "priorityName" + i + "_temp" %>' onChange='<%= liferayPortletResponse.getNamespace() + "onPrioritiesChanged();" %>' size="15" title="priority-name" />
														</td>
														<td>
															<aui:input label="" name='<%= "priorityImage" + i + "_temp" %>' onChange='<%= liferayPortletResponse.getNamespace() + "onPrioritiesChanged();" %>' size="40" title="priority-image" />
														</td>
														<td>
															<aui:input label="" name='<%= "priorityValue" + i + "_temp" %>' onChange='<%= liferayPortletResponse.getNamespace() + "onPrioritiesChanged();" %>' size="4" title="priority-value" />
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
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(mbConfigurationDisplayContext.getNavigation(), "user-ranks") %>'>
							<div class="c-px-4">
								<div class="alert alert-info">
									<liferay-ui:message key="enter-rank-and-minimum-post-pairs-per-line" />
								</div>

								<table class="lfr-table">
									<tr>
										<td class="lfr-label">
											<aui:input name="defaultLanguage" type="resource" value="<%= defaultLocale.getDisplayName(defaultLocale) %>" />
										</td>
										<td class="lfr-label">
											<clay:select
												additionalProps='<%=
													HashMapBuilder.<String, Object>put(
														"defaultLanguageId", defaultLanguageId
													).build()
												%>'
												id='<%= liferayPortletResponse.getNamespace() + "ranksLanguageId" %>'
												label="localized-language"
												name="ranksLanguageId"
												options="<%= mbConfigurationDisplayContext.getSelectOptions(locales) %>"
												propsTransformer="{UpdateRanksLanguagePropsTransformer} from message-boards-web"
											/>
										</td>
									</tr>
									<tr>
										<td>
											<aui:input cssClass="lfr-textarea-container" id='<%= "ranks_" + defaultLanguageId %>' label="" name='<%= "ranks_" + defaultLanguageId %>' title="ranks" type="textarea" value="<%= StringUtil.merge(mbGroupServiceSettings.getRanks(defaultLanguageId), StringPool.NEW_LINE) %>" />
										</td>
										<td>

											<%
											for (Locale curLocale : locales) {
												if (curLocale.equals(defaultLocale)) {
													continue;
												}
											%>

												<aui:input id='<%= "ranks_" + LocaleUtil.toLanguageId(curLocale) %>' name='<%= "ranks_" + LocaleUtil.toLanguageId(curLocale) %>' type="textarea" value="<%= StringUtil.merge(mbGroupServiceSettings.getRanks(LocaleUtil.toLanguageId(curLocale)), StringPool.NEW_LINE) %>" wrapperCssClass="hide" />

											<%
											}
											%>

											<aui:input cssClass="hide lfr-textarea-container" label="" name="ranks_temp" onChange='<%= liferayPortletResponse.getNamespace() + "onRanksChanged();" %>' title="ranks" type="textarea" />
										</td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(mbConfigurationDisplayContext.getNavigation(), "rss") && PortalUtil.isRSSFeedsEnabled() %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-rss:rss-settings
									delta="<%= rssDelta %>"
									displayStyle="<%= rssDisplayStyle %>"
									enabled="<%= enableRSS %>"
									feedType="<%= rssFeedType %>"
								/>
							</div>
						</c:when>
					</c:choose>
				</clay:sheet>

				<clay:content-row>
					<clay:button
						additionalProps='<%=
							HashMapBuilder.<String, Object>put(
								"defaultLanguageId", defaultLanguageId
							).build()
						%>'
						cssClass="c-mr-2"
						label="save"
						propsTransformer="{SaveConfigurationButtonPropsTransformer} from message-boards-web"
					/>

					<clay:link
						displayType="secondary"
						href="<%= mbConfigurationDisplayContext.getBackURL() %>"
						label="cancel"
						type="button"
					/>
				</clay:content-row>
			</aui:form>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<aui:script>
	window.<portlet:namespace />ranksChanged = false;
	window.<portlet:namespace />ranksLastLanguageId = '<%= currentLanguageId %>';

	function <portlet:namespace />onRanksChanged() {
		<portlet:namespace />ranksChanged = true;
	}

	window.<portlet:namespace />prioritiesChanged = false;
	window.<portlet:namespace />prioritiesLastLanguageId =
		'<%= currentLanguageId %>';

	function <portlet:namespace />onPrioritiesChanged() {
		<portlet:namespace />prioritiesChanged = true;
	}
</aui:script>