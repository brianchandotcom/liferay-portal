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

<%@ include file="/wiki/init.jsp" %>

<%
MailTemplatesHelper mailTemplatesHelper = new MailTemplatesHelper(wikiRequestHelper);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= WikiConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs2Names = "email-from,page-added-email,page-updated-email";

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
		<liferay-ui:error key="emailPageAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailPageAddedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailPageUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailPageUpdatedSubject" message="please-enter-a-valid-subject" />

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= wikiGroupServiceOverriddenConfiguration.emailFromName() %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= wikiGroupServiceOverriddenConfiguration.emailFromAddress() %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms" label="definition-of-terms">
				<dl>

					<%
					Map<String, String> definitionTerms = mailTemplatesHelper.getEmailFromDefinitionTerms();

					for (Map.Entry<String, String> definitionTerm : definitionTerms.entrySet()) {
					%>

						<dt>
							<%= definitionTerm.getKey() %>
						</dt>
						<dd>
							<%= definitionTerm.getValue() %>
						</dd>

					<%
					}
					%>

				</dl>
			</aui:fieldset>
		</liferay-ui:section>

		<%
		Map<String, String> definitionTerms = mailTemplatesHelper.getEmailNotificationDefinitionTerms();
		%>

		<liferay-ui:section>
			<liferay-frontend:email-notification-settings
				emailBody="<%= wikiGroupServiceOverriddenConfiguration.emailPageAddedBodyXml() %>"
				emailDefinitionTerms="<%= definitionTerms %>"
				emailEnabled="<%= wikiGroupServiceOverriddenConfiguration.emailPageAddedEnabled() %>"
				emailParam="emailPageAdded"
				emailSubject="<%= wikiGroupServiceOverriddenConfiguration.emailPageAddedSubjectXml() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-frontend:email-notification-settings
				emailBody="<%= wikiGroupServiceOverriddenConfiguration.emailPageUpdatedBodyXml() %>"
				emailDefinitionTerms="<%= definitionTerms %>"
				emailEnabled="<%= wikiGroupServiceOverriddenConfiguration.emailPageUpdatedEnabled() %>"
				emailParam="emailPageUpdated"
				emailSubject="<%= wikiGroupServiceOverriddenConfiguration.emailPageUpdatedSubjectXml() %>"
			/>
		</liferay-ui:section>

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-ui:section>
				<liferay-ui:rss-settings
					delta="<%= GetterUtil.getInteger(wikiGroupServiceOverriddenConfiguration.rssDelta()) %>"
					displayStyle="<%= wikiGroupServiceOverriddenConfiguration.rssDisplayStyle() %>"
					enabled="<%= wikiGroupServiceOverriddenConfiguration.enableRss() %>"
					feedType="<%= wikiGroupServiceOverriddenConfiguration.rssFeedType() %>"
				/>
			</liferay-ui:section>
		</c:if>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>