<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
KBConfigurationDisplayContext kbConfigurationDisplayContext = new KBConfigurationDisplayContext(request, renderRequest, renderResponse);
kbGroupServiceConfiguration = ParameterMapUtil.setParameterMap(KBGroupServiceConfiguration.class, kbGroupServiceConfiguration, request.getParameterMap(), "preferences--", "--");

Map<String, String> emailDefinitionTerms = kbConfigurationDisplayContext.getEmailDefinitionTerms(kbGroupServiceConfiguration, portletDisplay, resourceBundle);

Map<String, String> emailSuggestionDefinitionTerms = kbConfigurationDisplayContext.getEmailSuggestionDefinitionTerms(resourceBundle);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(kbConfigurationDisplayContext.getBackURL());
portletDisplay.setURLBackTitle("knowledge-base");

String emailParam = StringPool.BLANK;
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
				<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
					<liferay-ui:message key="settings" />
				</p>

				<clay:vertical-nav
					verticalNavItems="<%= kbConfigurationDisplayContext.getSettingsVerticalNavItemList() %>"
				/>
			</c:if>

			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="notifications" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= kbConfigurationDisplayContext.getNotificationsVerticalNavItemList() %>"
			/>
		</clay:col>

		<clay:col
			lg="9"
		>
			<h2>
				<%= kbConfigurationDisplayContext.getTitle() %>
			</h2>

			<liferay-portlet:actionURL portletConfiguration="<%= true %>" varImpl="configurationActionURL">
				<portlet:param name="navigation" value="<%= kbConfigurationDisplayContext.getNavigation() %>" />
				<portlet:param name="serviceName" value="<%= KBConstants.SERVICE_NAME %>" />
				<portlet:param name="settingsScope" value="group" />
			</liferay-portlet:actionURL>

			<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
				<clay:sheet
					cssClass="c-mb-4 c-mt-4 c-p-0"
					size="full"
				>
					<h3 class="c-pl-4 c-pr-4 c-pt-4 sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= kbConfigurationDisplayContext.getSubtitle() %>
							</clay:content-col>
						</clay:content-row>
					</h3>

					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

					<liferay-ui:error embed="<%= false %>" key="emailKBArticleAddedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailKBArticleAddedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailKBArticleExpiredBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailKBArticleExpiredSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailKBArticleReviewBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailKBArticleReviewSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error embed="<%= false %>" key="emailKBArticleUpdatedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error embed="<%= false %>" key="emailKBArticleUpdatedSubject" message="please-enter-a-valid-subject" />

					<c:choose>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-added-email") %>'>

							<%
							emailParam = "emailKBArticleAdded";
							%>

							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleAddedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleAddedEnabled() %>"
									emailParam="<%= emailParam %>"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleAddedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-updated-email") %>'>

							<%
							emailParam = "emailKBArticleUpdated";
							%>

							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedEnabled() %>"
									emailParam="<%= emailParam %>"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-review-email") %>'>

							<%
							emailParam = "emailKBArticleReview";
							%>

							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleReviewBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleReviewEnabled() %>"
									emailParam="<%= emailParam %>"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleReviewSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-expired-email") %>'>

							<%
							emailParam = "emailKBArticleExpired";
							%>

							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleExpiredBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleExpiredEnabled() %>"
									emailParam="<%= emailParam %>"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleExpiredSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "suggestion-received-email") %>'>

							<%
							emailParam = "emailKBArticleSuggestionReceived";
							%>

							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedBody() %>"
									emailDefinitionTerms="<%= emailSuggestionDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedEnabled() %>"
									emailParam="<%= emailParam %>"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "suggestion-in-progress-email") %>'>

							<%
							emailParam = "emailKBArticleSuggestionInProgress";
							%>

							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressBody() %>"
									emailDefinitionTerms="<%= emailSuggestionDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressEnabled() %>"
									emailParam="<%= emailParam %>"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressSubject() %>"
								/>
							</div> </c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "suggestion-resolved-email") %>'>

							<%
							emailParam = "emailKBArticleSuggestionResolved";
							%>

							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedBody() %>"
									emailDefinitionTerms="<%= emailSuggestionDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedEnabled() %>"
									emailParam="<%= emailParam %>"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedSubject() %>"
								/>
							</div> </c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "rss") && PortalUtil.isRSSFeedsEnabled() %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-rss:rss-settings
									delta="<%= GetterUtil.getInteger(kbGroupServiceConfiguration.rssDelta()) %>"
									displayStyle="<%= kbGroupServiceConfiguration.rssDisplayStyle() %>"
									enabled="<%= kbGroupServiceConfiguration.enableRss() %>"
									feedType="<%= kbGroupServiceConfiguration.rssFeedType() %>"
								/>
							</div>
						</c:when>
						<c:otherwise>
							<div class="c-px-4">
								<liferay-frontend:fieldset>
									<aui:input label="name" name="preferences--emailFromName--" value="<%= kbGroupServiceConfiguration.emailFromName() %>" wrapperCssClass="lfr-input-text-container">
										<aui:validator errorMessage="please-enter-a-valid-name" name="required" />
									</aui:input>

									<aui:input label="address" name="preferences--emailFromAddress--" value="<%= kbGroupServiceConfiguration.emailFromAddress() %>" wrapperCssClass="lfr-input-text-container">
										<aui:validator errorMessage="please-enter-a-valid-email-address" name="required" />
										<aui:validator name="email" />
									</aui:input>
								</liferay-frontend:fieldset>
							</div>
						</c:otherwise>
					</c:choose>
				</clay:sheet>

				<%
				configurationActionURL.setParameter("emailParam", emailParam);
				%>

				<aui:button-row>
					<div class="c-gap-1 d-flex">
						<clay:button
							additionalProps='<%=
								HashMapBuilder.<String, Object>put(
									"emailParam", emailParam
								).build()
							%>'
							cssClass="c-mr-2"
							label="save"
							propsTransformer="admin/js/SaveConfigurationButtonPropsTransformer"
						/>

						<clay:link
							displayType="secondary"
							href="<%= kbConfigurationDisplayContext.getBackURL() %>"
							label="cancel"
							type="button"
						/>
					</div>
				</aui:button-row>
			</aui:form>
		</clay:col>
	</clay:row>
</clay:container-fluid>