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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(kbConfigurationDisplayContext.getBackURL());
portletDisplay.setURLBackTitle("knowledge-base");
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

			<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
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

					<%
					Map<String, String> emailDefinitionTerms = LinkedHashMapBuilder.put(
						"[$ARTICLE_ATTACHMENTS$]", LanguageUtil.get(resourceBundle, "the-article-attachments-file-names")
					).put(
						"[$ARTICLE_CONTENT$]", LanguageUtil.get(resourceBundle, "the-article-content")
					).put(
						"[$ARTICLE_CONTENT_DIFF$]", LanguageUtil.get(resourceBundle, "the-article-content-diff")
					).put(
						"[$ARTICLE_TITLE$]", LanguageUtil.get(resourceBundle, "the-article-title")
					).put(
						"[$ARTICLE_TITLE_DIFF$]", LanguageUtil.get(resourceBundle, "the-article-title-diff")
					).put(
						"[$ARTICLE_URL$]", LanguageUtil.get(resourceBundle, "the-article-url")
					).put(
						"[$ARTICLE_USER_ADDRESS$]", LanguageUtil.get(resourceBundle, "the-email-address-of-the-user-who-added-the-article")
					).put(
						"[$ARTICLE_USER_NAME$]", LanguageUtil.get(resourceBundle, "the-user-who-added-the-article")
					).put(
						"[$ARTICLE_VERSION$]", LanguageUtil.get(resourceBundle, "the-article-version")
					).put(
						"[$CATEGORY_TITLE$]", LanguageUtil.get(resourceBundle, "category.kb")
					).put(
						"[$COMPANY_ID$]", LanguageUtil.get(resourceBundle, "the-company-id-associated-with-the-article")
					).put(
						"[$COMPANY_MX$]", LanguageUtil.get(resourceBundle, "the-company-mx-associated-with-the-article")
					).put(
						"[$COMPANY_NAME$]", LanguageUtil.get(resourceBundle, "the-company-name-associated-with-the-article")
					).put(
						"[$FROM_ADDRESS$]", HtmlUtil.escape(kbGroupServiceConfiguration.emailFromAddress())
					).put(
						"[$FROM_NAME$]", HtmlUtil.escape(kbGroupServiceConfiguration.emailFromName())
					).put(
						"[$PORTAL_URL$]", PortalUtil.getPortalURL(themeDisplay)
					).put(
						"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle())
					).put(
						"[$SITE_NAME$]", LanguageUtil.get(resourceBundle, "the-site-name-associated-with-the-article")
					).put(
						"[$TO_ADDRESS$]", LanguageUtil.get(resourceBundle, "the-address-of-the-email-recipient")
					).put(
						"[$TO_NAME$]", LanguageUtil.get(resourceBundle, "the-name-of-the-email-recipient")
					).build();

					if (Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-added-email") || Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-updated-email") || Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-review-email")) {
						emailDefinitionTerms = LinkedHashMapBuilder.put(
							"[$ARTICLE_CONTENT$]", LanguageUtil.get(resourceBundle, "the-article-content")
						).put(
							"[$ARTICLE_TITLE$]", LanguageUtil.get(resourceBundle, "the-article-title")
						).put(
							"[$ARTICLE_URL$]", LanguageUtil.get(resourceBundle, "the-article-url")
						).put(
							"[$COMMENT_CONTENT$]", LanguageUtil.get(resourceBundle, "the-comment-content")
						).put(
							"[$COMMENT_CREATE_DATE$]", LanguageUtil.get(resourceBundle, "the-comment-create-date")
						).put(
							"[$TO_ADDRESS$]", LanguageUtil.get(resourceBundle, "the-address-of-the-email-recipient")
						).put(
							"[$TO_NAME$]", LanguageUtil.get(resourceBundle, "the-name-of-the-email-recipient")
						).build();
					}
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-added-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleAddedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleAddedEnabled() %>"
									emailParam="emailKBArticleAdded"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleAddedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-updated-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedEnabled() %>"
									emailParam="emailKBArticleUpdated"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-review-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleReviewBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleReviewEnabled() %>"
									emailParam="emailKBArticleReview"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleReviewSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "article-expired-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleExpiredBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleExpiredEnabled() %>"
									emailParam="emailKBArticleExpired"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleExpiredSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "suggestion-received-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedEnabled() %>"
									emailParam="emailKBArticleSuggestionReceived"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "suggestion-in-progress-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressEnabled() %>"
									emailParam="emailKBArticleSuggestionInProgress"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(kbConfigurationDisplayContext.getNavigation(), "suggestion-resolved-email") %>'>
							<div class="c-px-4 panel-group-flush">
								<liferay-frontend:email-notification-settings
									emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedEnabled() %>"
									emailParam="emailKBArticleSuggestionResolved"
									emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedSubject() %>"
								/>
							</div>
						</c:when>
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

				<aui:button-row>
					<clay:button
						cssClass="c-mr-2"
						label="save"
						onClick='<%= liferayPortletResponse.getNamespace() + "save();" %>'
						type="submit"
					/>

					<clay:link
						displayType="secondary"
						href="<%= kbConfigurationDisplayContext.getBackURL() %>"
						label="cancel"
						type="button"
					/>
				</aui:button-row>
			</aui:form>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<script>
	function <portlet:namespace />save() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var emailKBArticleAddedEditor = window.<portlet:namespace />emailKBArticleAdded.getHTML();
			var emailKBArticleExpiredEditor = window.<portlet:namespace />emailKBArticleExpired.getHTML();
			var emailKBArticleReviewEditor = window.<portlet:namespace />emailKBArticleReview.getHTML();
			var emailKBArticleSuggestionInProgressEditor = window.<portlet:namespace />emailKBArticleSuggestionInProgress.getHTML();
			var emailKBArticleSuggestionReceivedEditor = window.<portlet:namespace />emailKBArticleSuggestionReceived.getHTML();
			var emailKBArticleSuggestionResolvedEditor = window.<portlet:namespace />emailKBArticleSuggestionResolved.getHTML();
			var emailKBArticleUpdatedEditor = window.<portlet:namespace />emailKBArticleUpdated.getHTML();

			document.getElementById(
				'<portlet:namespace />emailKBArticleAddedBody'
			).value = emailKBArticleAddedEditor;
			document.getElementById(
				'<portlet:namespace />emailKBArticleUpdatedBody'
			).value = emailKBArticleUpdatedEditor;
			document.getElementById(
				'<portlet:namespace />emailKBArticleReviewBody'
			).value = emailKBArticleReviewEditor;
			document.getElementById(
				'<portlet:namespace />emailKBArticleExpiredBody'
			).value = emailKBArticleExpiredEditor;
			document.getElementById(
				'<portlet:namespace />emailKBArticleSuggestionReceivedBody'
			).value = emailKBArticleSuggestionReceivedEditor;
			document.getElementById(
				'<portlet:namespace />emailKBArticleSuggestionInProgressBody'
			).value = emailKBArticleSuggestionInProgressEditor;
			document.getElementById(
				'<portlet:namespace />emailKBArticleSuggestionResolvedBody'
			).value = emailKBArticleSuggestionResolvedEditor;

			submitForm(form);
		}
	}
</script>