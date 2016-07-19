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

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs1Names = "email-from,entry-added-email,entry-updated-email";

	if (PortalUtil.isRSSFeedsEnabled()) {
		tabs1Names += ",rss";
	}
	%>

	<div class="portlet-configuration-body-content">
		<liferay-ui:tabs
			names="<%= tabs1Names %>"
			refresh="<%= false %>"
			type="tabs nav-tabs-default"
		>
			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
			<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
			<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
			<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset>
							<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= blogsGroupServiceSettings.getEmailFromName() %>" />

							<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= blogsGroupServiceSettings.getEmailFromAddress() %>" />
						</aui:fieldset>

						<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="definition-of-terms">
							<dl>

								<%
								Map<String, String> emailFromDefinitionTerms = BlogsUtil.getEmailFromDefinitionTerms(renderRequest, blogsGroupServiceSettings.getEmailFromAddress(), blogsGroupServiceSettings.getEmailFromName());

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
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<%
			Map<String, String> emailDefinitionTerms = BlogsUtil.getEmailDefinitionTerms(renderRequest, blogsGroupServiceSettings.getEmailFromAddress(), blogsGroupServiceSettings.getEmailFromName());
			%>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<liferay-frontend:email-notification-settings
							emailBody="<%= blogsGroupServiceSettings.getEmailEntryAddedBodyXml() %>"
							emailDefinitionTerms="<%= emailDefinitionTerms %>"
							emailEnabled="<%= blogsGroupServiceSettings.isEmailEntryAddedEnabled() %>"
							emailParam="emailEntryAdded"
							emailSubject="<%= blogsGroupServiceSettings.getEmailEntryAddedSubjectXml() %>"
						/>
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<aui:fieldset-group markupView="lexicon">
						<liferay-frontend:email-notification-settings
							emailBody="<%= blogsGroupServiceSettings.getEmailEntryUpdatedBodyXml() %>"
							emailDefinitionTerms="<%= emailDefinitionTerms %>"
							emailEnabled="<%= blogsGroupServiceSettings.isEmailEntryUpdatedEnabled() %>"
							emailParam="emailEntryUpdated"
							emailSubject="<%= blogsGroupServiceSettings.getEmailEntryUpdatedSubjectXml() %>"
						/>
					</aui:fieldset-group>
				</div>
			</liferay-ui:section>

			<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
				<liferay-ui:section>
					<div class="container-fluid-1280">
						<aui:fieldset-group markupView="lexicon">
							<liferay-ui:rss-settings
								delta="<%= GetterUtil.getInteger(blogsGroupServiceOverriddenConfiguration.rssDelta()) %>"
								displayStyle="<%= blogsGroupServiceOverriddenConfiguration.rssDisplayStyle() %>"
								enabled="<%= blogsGroupServiceOverriddenConfiguration.enableRss() %>"
								feedType="<%= blogsGroupServiceOverriddenConfiguration.rssFeedType() %>"
							/>
						</aui:fieldset-group>
					</div>
				</liferay-ui:section>
			</c:if>
		</liferay-ui:tabs>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>