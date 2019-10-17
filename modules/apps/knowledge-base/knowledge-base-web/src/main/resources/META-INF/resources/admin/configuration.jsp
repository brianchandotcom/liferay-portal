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

<%@ include file="/admin/init.jsp" %>

<%
String tabsNames = "email-from,article-added-email,article-updated-email,suggestion-received-email,suggestion-in-progress-email,suggestion-resolved-email";

if (PortalUtil.isRSSFeedsEnabled()) {
	tabsNames += ",rss";
}

kbGroupServiceConfiguration = ParameterMapUtil.setParameterMap(KBGroupServiceConfiguration.class, kbGroupServiceConfiguration, request.getParameterMap(), "preferences--", "--");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= KBConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-ui:error key="emailKBArticleAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailKBArticleAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailKBArticleUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailKBArticleUpdatedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:tabs
			names="<%= tabsNames %>"
			refresh="<%= false %>"
		>
			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input label="name" name="preferences--emailFromName--" value="<%= kbGroupServiceConfiguration.emailFromName() %>" wrapperCssClass="lfr-input-text-container" />

						<aui:input label="address" name="preferences--emailFromAddress--" value="<%= kbGroupServiceConfiguration.emailFromAddress() %>" wrapperCssClass="lfr-input-text-container" />
					</liferay-frontend:fieldset>

					<liferay-frontend:fieldset
						collapsed="<%= true %>"
						collapsible="<%= true %>"
						label="definition-of-terms"
					>
						<dl>
							<dt>
								[$ARTICLE_USER_ADDRESS$]
							</dt>
							<dd>
								<liferay-ui:message key="the-email-address-of-the-user-who-added-the-article" />
							</dd>
							<dt>
								[$ARTICLE_USER_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-user-who-added-the-article" />
							</dd>
							<dt>
								[$CATEGORY_TITLE$]
							</dt>
							<dd>
								<liferay-ui:message key="category.kb" />
							</dd>
							<dt>
								[$COMPANY_ID$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-id-associated-with-the-article" />
							</dd>
							<dt>
								[$COMPANY_MX$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-mx-associated-with-the-article" />
							</dd>
							<dt>
								[$COMPANY_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-company-name-associated-with-the-article" />
							</dd>
							<dt>
								[$SITE_NAME$]
							</dt>
							<dd>
								<liferay-ui:message key="the-site-name-associated-with-the-article" />
							</dd>
						</dl>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<%
			Map<String, String> emailDefinitionTerms = new LinkedHashMap<String, String>();

			emailDefinitionTerms.put("[$ARTICLE_ATTACHMENTS$]", LanguageUtil.get(resourceBundle, "the-article-attachments-file-names"));
			emailDefinitionTerms.put("[$ARTICLE_CONTENT$]", LanguageUtil.get(resourceBundle, "the-article-content"));
			emailDefinitionTerms.put("[$ARTICLE_CONTENT_DIFF$]", LanguageUtil.get(resourceBundle, "the-article-content-diff"));
			emailDefinitionTerms.put("[$ARTICLE_TITLE$]", LanguageUtil.get(resourceBundle, "the-article-title"));
			emailDefinitionTerms.put("[$ARTICLE_TITLE_DIFF$]", LanguageUtil.get(resourceBundle, "the-article-title-diff"));
			emailDefinitionTerms.put("[$ARTICLE_URL$]", LanguageUtil.get(resourceBundle, "the-article-url"));
			emailDefinitionTerms.put("[$ARTICLE_USER_ADDRESS$]", LanguageUtil.get(resourceBundle, "the-email-address-of-the-user-who-added-the-article"));
			emailDefinitionTerms.put("[$ARTICLE_USER_NAME$]", LanguageUtil.get(resourceBundle, "the-user-who-added-the-article"));
			emailDefinitionTerms.put("[$ARTICLE_VERSION$]", LanguageUtil.get(resourceBundle, "the-article-version"));
			emailDefinitionTerms.put("[$CATEGORY_TITLE$]", LanguageUtil.get(resourceBundle, "category.kb"));
			emailDefinitionTerms.put("[$COMPANY_ID$]", LanguageUtil.get(resourceBundle, "the-company-id-associated-with-the-article"));
			emailDefinitionTerms.put("[$COMPANY_MX$]", LanguageUtil.get(resourceBundle, "the-company-mx-associated-with-the-article"));
			emailDefinitionTerms.put("[$COMPANY_NAME$]", LanguageUtil.get(resourceBundle, "the-company-name-associated-with-the-article"));
			emailDefinitionTerms.put("[$FROM_ADDRESS$]", HtmlUtil.escape(kbGroupServiceConfiguration.emailFromAddress()));
			emailDefinitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(kbGroupServiceConfiguration.emailFromName()));
			emailDefinitionTerms.put("[$PORTAL_URL$]", PortalUtil.getPortalURL(themeDisplay));
			emailDefinitionTerms.put("[$SITE_NAME$]", LanguageUtil.get(resourceBundle, "the-site-name-associated-with-the-article"));
			emailDefinitionTerms.put("[$TO_ADDRESS$]", LanguageUtil.get(resourceBundle, "the-address-of-the-email-recipient"));
			emailDefinitionTerms.put("[$TO_NAME$]", LanguageUtil.get(resourceBundle, "the-name-of-the-email-recipient"));
			%>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBody="<%= kbGroupServiceConfiguration.emailKBArticleAddedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleAddedEnabled() %>"
						emailParam="emailKBArticleAdded"
						emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleAddedSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBody="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedEnabled() %>"
						emailParam="emailKBArticleUpdated"
						emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<%
			emailDefinitionTerms = new LinkedHashMap<String, String>();

			emailDefinitionTerms.put("[$ARTICLE_CONTENT$]", LanguageUtil.get(resourceBundle, "the-article-content"));
			emailDefinitionTerms.put("[$ARTICLE_TITLE$]", LanguageUtil.get(resourceBundle, "the-article-title"));
			emailDefinitionTerms.put("[$ARTICLE_URL$]", LanguageUtil.get(resourceBundle, "the-article-url"));
			emailDefinitionTerms.put("[$COMMENT_CONTENT$]", LanguageUtil.get(resourceBundle, "the-comment-content"));
			emailDefinitionTerms.put("[$COMMENT_CREATE_DATE$]", LanguageUtil.get(resourceBundle, "the-comment-create-date"));
			emailDefinitionTerms.put("[$TO_ADDRESS$]", LanguageUtil.get(resourceBundle, "the-address-of-the-email-recipient"));
			emailDefinitionTerms.put("[$TO_NAME$]", LanguageUtil.get(resourceBundle, "the-name-of-the-email-recipient"));
			%>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedEnabled() %>"
						emailParam="emailKBArticleSuggestionReceived"
						emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressEnabled() %>"
						emailParam="emailKBArticleSuggestionInProgress"
						emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBody="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedEnabled() %>"
						emailParam="emailKBArticleSuggestionResolved"
						emailSubject="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
				<liferay-ui:section>
					<liferay-frontend:fieldset-group>
						<liferay-frontend:fieldset>
							<liferay-rss:rss-settings
								delta="<%= kbGroupServiceConfiguration.rssDelta() %>"
								displayStyle="<%= kbGroupServiceConfiguration.rssDisplayStyle() %>"
								enabled="<%= kbGroupServiceConfiguration.enableRSS() %>"
								feedType="<%= kbGroupServiceConfiguration.rssFeedType() %>"
							/>
						</liferay-frontend:fieldset>
					</liferay-frontend:fieldset-group>
				</liferay-ui:section>
			</c:if>
		</liferay-ui:tabs>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button onClick='<%= renderResponse.getNamespace() + "save();" %>' value="save" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />save() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var emailKBArticleAddedEditor = window.<portlet:namespace />emailKBArticleAdded.getHTML();
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
</aui:script>