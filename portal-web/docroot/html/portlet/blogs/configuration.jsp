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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", BlogsUtil.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", BlogsUtil.getEmailFromAddress(portletPreferences, company.getCompanyId()));
String socialBookmarkTypes = portletPreferences.getValue("socialBookmarksTypes", PropsUtil.get(PropsKeys.SOCIAL_BOOKMARK_TYPES));
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs2Names = "display-settings,email-from,entry-added-email,entry-updated-email";

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
		<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />

		<liferay-ui:section>
			<%@ include file="/html/portlet/blogs/display_settings.jspf" %>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= emailFromAddress %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms" label="definition-of-terms">
				<dl>

					<%
					Map<String, String> emailFromDefinitionTerms = BlogsUtil.getEmailFromDefinitionTerms(renderRequest, emailFromAddress, emailFromName);

					for (Map.Entry<String, String> entry : emailFromDefinitionTerms.entrySet()) {
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
		Map<String, String> emailDefinitionTerms = BlogsUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName);
		%>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailEntryAddedBody", ContentUtil.get(PropsValues.BLOGS_EMAIL_ENTRY_ADDED_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailEntryAddedEnabled--", BlogsUtil.getEmailEntryAddedEnabled(portletPreferences)) %>'
				emailParam="emailEntryAdded"
				emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailEntryAddedSubject", ContentUtil.get(PropsValues.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT)) %>'
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailEntryUpdatedBody", ContentUtil.get(PropsValues.BLOGS_EMAIL_ENTRY_UPDATED_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailEntryUpdatedEnabled--", BlogsUtil.getEmailEntryUpdatedEnabled(portletPreferences)) %>'
				emailParam="emailEntryUpdated"
				emailSubject='<%= LocalizationUtil.getLocalizationXmlFromPreferences(portletPreferences, renderRequest, "emailEntryUpdatedSubject", ContentUtil.get(PropsValues.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT)) %>'
			/>
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