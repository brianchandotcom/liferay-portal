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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
blogsSettings = BlogsSettings.getInstance(scopeGroupId, request.getParameterMap());
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= BlogsConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:tabs
		names="email-from,entry-added-email,entry-updated-email"
		refresh="<%= false %>"
	>
		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
		<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= blogsSettings.getEmailFromName() %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= blogsSettings.getEmailFromAddress() %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms" label="definition-of-terms">
				<dl>

					<%
					Map<String, String> emailFromDefinitionTerms = BlogsUtil.getEmailFromDefinitionTerms(renderRequest, blogsSettings.getEmailFromAddress(), blogsSettings.getEmailFromName());

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
		Map<String, String> emailDefinitionTerms = BlogsUtil.getEmailDefinitionTerms(renderRequest, blogsSettings.getEmailFromAddress(), blogsSettings.getEmailFromName());
		%>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= blogsSettings.getEmailEntryAddedBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= blogsSettings.isEmailEntryAddedEnabled() %>"
				emailParam="emailEntryAdded"
				emailSubject="<%= blogsSettings.getEmailEntryAddedSubjectXml() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= blogsSettings.getEmailEntryUpdatedBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
					emailEnabled="<%= blogsSettings.isEmailEntryUpdatedEnabled() %>"
				emailParam="emailEntryUpdated"
				emailSubject="<%= blogsSettings.getEmailEntryUpdatedSubjectXml() %>"
			/>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>