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
kbGroupServiceConfiguration = ParameterMapUtil.setParameterMap(KBGroupServiceConfiguration.class, kbGroupServiceConfiguration, request.getParameterMap(), "preferences--", "--");

String tabs2 = ParamUtil.getString(request, "tabs2", "email-from");

String editorParam = StringPool.BLANK;
String editorBody = StringPool.BLANK;

if (tabs2.equals("article-added-email")) {
	editorParam = "emailKBArticleAddedBody";
	editorBody = kbGroupServiceConfiguration.emailKBArticleAddedBody();
}
else if (tabs2.equals("article-updated-email")) {
	editorParam = "emailKBArticleUpdatedBody";
	editorBody = kbGroupServiceConfiguration.emailKBArticleUpdatedBody();
}
else if (tabs2.equals("suggestion-in-progress-email")) {
	editorParam = "emailKBArticleSuggestionInProgressBody";
	editorBody = kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressBody();
}
else if (tabs2.equals("suggestion-received-email")) {
	editorParam = "emailKBArticleSuggestionReceivedBody";
	editorBody = kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedBody();
}
else if (tabs2.equals("suggestion-resolved-email")) {
	editorParam = "emailKBArticleSuggestionResolvedBody";
	editorBody = kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedBody();
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= KBConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
</liferay-portlet:renderURL>

<%
String tabs2Names = "email-from,article-added-email,article-updated-email,suggestion-received-email,suggestion-in-progress-email,suggestion-resolved-email";

if (PortalUtil.isRSSFeedsEnabled()) {
	tabs2Names += ",rss";
}
%>

<liferay-ui:tabs
	names="<%= tabs2Names %>"
	param="tabs2"
	url="<%= configurationRenderURL %>"
/>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />

	<liferay-ui:error key="emailKBArticleAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailKBArticleAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailKBArticleUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailKBArticleUpdatedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

	<aui:fieldset>
		<c:choose>
			<c:when test='<%= tabs2.equals("email-from") %>'>
				<aui:input label="name" name="preferences--emailFromName--" value="<%= kbGroupServiceConfiguration.emailFromName() %>" wrapperCssClass="lfr-input-text-container" />

				<aui:input label="address" name="preferences--emailFromAddress--" value="<%= kbGroupServiceConfiguration.emailFromAddress() %>" wrapperCssClass="lfr-input-text-container" />

				<div class="definition-of-terms">
					<h4><liferay-ui:message key="definition-of-terms" /></h4>

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
				</div>
			</c:when>
			<c:when test='<%= tabs2.startsWith("article-") %>'>
				<c:choose>
					<c:when test='<%= tabs2.equals("article-added-email") %>'>
						<aui:input label="enabled" name="preferences--emailKBArticleAddedEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleAddedEnabled() %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("article-updated-email") %>'>
						<aui:input label="enabled" name="preferences--emailKBArticleUpdatedEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedEnabled() %>" />
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test='<%= tabs2.equals("article-added-email") %>'>
						<aui:input label="subject" name="preferences--emailKBArticleAddedSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleAddedSubject() %>" wrapperCssClass="lfr-input-text-container" />
					</c:when>
					<c:when test='<%= tabs2.equals("article-updated-email") %>'>
						<aui:input label="subject" name="preferences--emailKBArticleUpdatedSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleUpdatedSubject() %>" wrapperCssClass="lfr-input-text-container" />
					</c:when>
				</c:choose>

				<aui:input label="body" name='<%= "preferences--".concat(editorParam).concat("--") %>' type="textarea" value="<%= editorBody %>" wrapperCssClass="lfr-textarea-container" />

				<div class="definition-of-terms">
					<h4><liferay-ui:message key="definition-of-terms" /></h4>

					<dl>
						<dt>
							[$ARTICLE_ATTACHMENTS$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-attachments-file-names" />
						</dd>
						<dt>
							[$ARTICLE_CONTENT$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-content" />
						</dd>
						<dt>
							[$ARTICLE_CONTENT_DIFF$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-content-diff" />
						</dd>
						<dt>
							[$ARTICLE_TITLE$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-title" />
						</dd>
						<dt>
							[$ARTICLE_TITLE_DIFF$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-title-diff" />
						</dd>
						<dt>
							[$ARTICLE_URL$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-url" />
						</dd>
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
							[$ARTICLE_VERSION$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-version" />
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
							[$FROM_ADDRESS$]
						</dt>
						<dd>
							<%= HtmlUtil.escape(kbGroupServiceConfiguration.emailFromAddress()) %>
						</dd>
						<dt>
							[$FROM_NAME$]
						</dt>
						<dd>
							<%= HtmlUtil.escape(kbGroupServiceConfiguration.emailFromName()) %>
						</dd>
						<dt>
							[$PORTAL_URL$]
						</dt>
						<dd>
							<%= PortalUtil.getPortalURL(themeDisplay) %>
						</dd>
						<dt>
							[$SITE_NAME$]
						</dt>
						<dd>
							<liferay-ui:message key="the-site-name-associated-with-the-article" />
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
			<c:when test='<%= tabs2.startsWith("suggestion-") %>'>
				<c:choose>
					<c:when test='<%= tabs2.equals("suggestion-in-progress-email") %>'>
						<aui:input label="enabled" name="preferences--emailKBArticleSuggestionInProgressEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressEnabled() %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("suggestion-received-email") %>'>
						<aui:input label="enabled" name="preferences--emailKBArticleSuggestionReceivedEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedEnabled() %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("suggestion-resolved-email") %>'>
						<aui:input label="enabled" name="preferences--emailKBArticleSuggestionResolvedEnabled--" type="checkbox" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedEnabled() %>" />
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test='<%= tabs2.equals("suggestion-in-progress-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailKBArticleSuggestionInProgressSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionInProgressSubject() %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("suggestion-received-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailKBArticleSuggestionReceivedSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionReceivedSubject() %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("suggestion-resolved-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject" name="preferences--emailKBArticleSuggestionResolvedSubject--" value="<%= kbGroupServiceConfiguration.emailKBArticleSuggestionResolvedSubject() %>" />
					</c:when>
				</c:choose>

				<aui:input cssClass="lfr-textarea-container" label="body" name='<%= "preferences--".concat(editorParam).concat("--") %>' type="textarea" value="<%= editorBody %>" />

				<div class="definition-of-terms">
					<h4><liferay-ui:message key="definition-of-terms" /></h4>

					<dl>
						<dt>
							[$ARTICLE_CONTENT$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-content" />
						</dd>
						<dt>
							[$ARTICLE_TITLE$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-title" />
						</dd>
						<dt>
							[$ARTICLE_URL$]
						</dt>
						<dd>
							<liferay-ui:message key="the-article-url" />
						</dd>
						<dt>
							[$COMMENT_CONTENT$]
						</dt>
						<dd>
							<liferay-ui:message key="the-comment-content" />
						</dd>
						<dt>
							[$COMMENT_CREATE_DATE$]
						</dt>
						<dd>
							<liferay-ui:message key="the-comment-create-date" />
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
			<c:when test='<%= tabs2.equals("rss") %>'>
				<liferay-ui:rss-settings
					delta="<%= kbGroupServiceConfiguration.rssDelta() %>"
					displayStyle="<%= kbGroupServiceConfiguration.rssDisplayStyle() %>"
					enabled="<%= kbGroupServiceConfiguration.enableRSS() %>"
					feedType="<%= kbGroupServiceConfiguration.rssFeedType() %>"
				/>
			</c:when>
		</c:choose>

		<aui:button-row cssClass="kb-submit-buttons">
			<aui:button type="submit" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>