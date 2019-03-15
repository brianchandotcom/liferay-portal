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

<%@ include file="/blogs_admin/init.jsp" %>

<%
blogsGroupServiceSettings = BlogsGroupServiceSettings.getInstance(scopeGroupId, request.getParameterMap());

BlogsGroupServiceOverriddenConfiguration blogsGroupServiceOverriddenConfiguration = ConfigurationProviderUtil.getConfiguration(BlogsGroupServiceOverriddenConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), new GroupServiceSettingsLocator(themeDisplay.getSiteGroupId(), BlogsConstants.SERVICE_NAME)));
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= BlogsConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs1Names = "email-from,entry-added-email,entry-updated-email";

	if (PortalUtil.isRSSFeedsEnabled()) {
		tabs1Names += ",rss";
	}
	%>

	<liferay-frontend:edit-form-body>
		<liferay-ui:tabs
			names="<%= tabs1Names %>"
			refresh="<%= false %>"
		>
			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
			<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= blogsGroupServiceSettings.getEmailFromName() %>" />

						<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= blogsGroupServiceSettings.getEmailFromAddress() %>" />
					</liferay-frontend:fieldset>

					<liferay-frontend:fieldset
						collapsed="<%= true %>"
						collapsible="<%= true %>"
						label="definition-of-terms"
					>
						<dl>

							<%
							Map<String, String> emailFromDefinitionTerms = BlogsUtil.getEmailFromDefinitionTerms(renderRequest, blogsGroupServiceSettings.getEmailFromAddress(), blogsGroupServiceSettings.getEmailFromName());

							for (Map.Entry<String, String> entry : emailFromDefinitionTerms.entrySet()) {
							%>

								<dt>
									<%= HtmlUtil.escape(entry.getKey()) %>
								</dt>
								<dd>
									<%= HtmlUtil.escape(entry.getValue()) %>
								</dd>

							<%
							}
							%>

						</dl>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<%
			Map<String, String> emailDefinitionTerms = BlogsUtil.getEmailDefinitionTerms(renderRequest, blogsGroupServiceSettings.getEmailFromAddress(), blogsGroupServiceSettings.getEmailFromName());
			%>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= blogsGroupServiceSettings.getEmailEntryAddedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= blogsGroupServiceSettings.isEmailEntryAddedEnabled() %>"
						emailParam="emailEntryAdded"
						emailSubjectLocalizedValuesMap="<%= blogsGroupServiceSettings.getEmailEntryAddedSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= blogsGroupServiceSettings.getEmailEntryUpdatedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= blogsGroupServiceSettings.isEmailEntryUpdatedEnabled() %>"
						emailParam="emailEntryUpdated"
						emailSubjectLocalizedValuesMap="<%= blogsGroupServiceSettings.getEmailEntryUpdatedSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
				<liferay-ui:section>
					<liferay-frontend:fieldset-group>
						<liferay-rss:rss-settings
							delta="<%= GetterUtil.getInteger(blogsGroupServiceOverriddenConfiguration.rssDelta()) %>"
							displayStyle="<%= blogsGroupServiceOverriddenConfiguration.rssDisplayStyle() %>"
							enabled="<%= blogsGroupServiceOverriddenConfiguration.enableRss() %>"
							feedType="<%= blogsGroupServiceOverriddenConfiguration.rssFeedType() %>"
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