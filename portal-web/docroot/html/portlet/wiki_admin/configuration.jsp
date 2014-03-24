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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
wikiSettings = WikiUtil.getWikiSettings(themeDisplay.getSiteGroupId(), request);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= WikiConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<%
	String tabs2Names = "display-settings,email-from,page-added-email,page-updated-email";

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
		<liferay-ui:error key="visibleNodesCount" message="please-specify-at-least-one-visible-node" />

		<liferay-ui:section>
			<%@ include file="/html/portlet/wiki_admin/display_settings.jspf" %>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= wikiSettings.getEmailFromName() %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= wikiSettings.getEmailFromAddress() %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="definition-of-terms">
				<legend>
					<liferay-ui:message key="definition-of-terms" />
				</legend>

				<dl>

					<%
					Map<String, String> definitionTerms = WikiUtil.getEmailFromDefinitionTerms(renderRequest, wikiSettings.getEmailFromAddress(), wikiSettings.getEmailFromName());

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
		Map<String, String> definitionTerms = WikiUtil.getEmailNotificationDefinitionTerms(renderRequest, wikiSettings.getEmailFromAddress(), wikiSettings.getEmailFromName());
		%>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= wikiSettings.getEmailPageAddedBody() %>"
				emailDefinitionTerms="<%= definitionTerms %>"
				emailEnabled="<%= wikiSettings.getEmailPageAddedEnabled() %>"
				emailParam="emailPageAdded"
				emailSubject="<%= wikiSettings.getEmailPageAddedSubject() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-ui:email-notification-settings
				emailBody="<%= wikiSettings.getEmailPageUpdatedBody() %>"
				emailDefinitionTerms="<%= definitionTerms %>"
				emailEnabled="<%= wikiSettings.getEmailPageUpdatedEnabled() %>"
				emailParam="emailPageUpdated"
				emailSubject="<%= wikiSettings.getEmailPageAddedSubject() %>"
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

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			<portlet:namespace />saveEmails();

			document.<portlet:namespace />fm.<portlet:namespace />visibleNodes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentVisibleNodes);

			document.<portlet:namespace />fm.<portlet:namespace />hiddenNodes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />availableVisibleNodes);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailPageAddedBody--'].value = window['<portlet:namespace />emailPageAdded'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailPageUpdatedBody--'].value = window['<portlet:namespace />emailPageUpdated'].getHTML();
		}
		catch (e) {
		}
	}
</aui:script>