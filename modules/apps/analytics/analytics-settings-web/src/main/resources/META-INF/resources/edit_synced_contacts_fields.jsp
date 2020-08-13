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

<%@ include file="/init.jsp" %>

<%
AnalyticsConfiguration analyticsConfiguration = (AnalyticsConfiguration)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);

String[] syncedContactFieldNames = new String[0];
String[] syncedUserFieldNames = new String[0];

if (analyticsConfiguration != null) {
	syncedContactFieldNames = analyticsConfiguration.syncedContactFieldNames();
	syncedUserFieldNames = analyticsConfiguration.syncedUserFieldNames();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/view_configuration_screen");
portletURL.setParameter("configurationScreenKey", "synced-contact-data");

String redirect = portletURL.toString();
%>

<portlet:actionURL name="/analytics_settings/edit_synced_contacts_fields" var="editSyncedContactsFieldsURL" />

<clay:sheet
	cssClass="portlet-analytics-settings"
>
	<h2>
		<liferay-ui:message key="sync-data-fields" />
	</h2>

	<p class="mt-3 text-secondary">
		<liferay-ui:message key="sync-data-fields-help" />
	</p>

	<aui:form action="<%= editSyncedContactsFieldsURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:tabs
			names='<%= LanguageUtil.format(resourceBundle, "contact-x", syncedContactFieldNames.length, false) + "," + LanguageUtil.format(resourceBundle, "user-x", syncedUserFieldNames.length, false) %>'
			refresh="<%= false %>"
		>
			<liferay-ui:section>

				<%
				FieldDisplayContext fieldDisplayContext = new FieldDisplayContext("/analytics_settings/edit_synced_contact_fields", renderRequest, renderResponse);
				%>

				<clay:management-toolbar
					displayContext="<%= new FieldManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, fieldDisplayContext) %>"
				/>

				<liferay-ui:search-container
					id="selectContactFields"
					searchContainer="<%= fieldDisplayContext.getFieldSearch() %>"
					var="contactFieldsSearchContainer"
				>
					<liferay-ui:search-container-row
						className="com.liferay.analytics.settings.web.internal.model.Field"
						escapedModel="<%= true %>"
						keyProperty="name"
						modelVar="contactField"
					>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="attribute"
							value="<%= HtmlUtil.escape(contactField.getName()) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="category"
							value="<%= HtmlUtil.escape(contactField.getCategory()) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="data-type"
							value="<%= HtmlUtil.escape(contactField.getDataType()) %>"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
						paginate="<%= false %>"
						searchResultCssClass="show-quick-actions-on-hover table table-autofit"
					/>
				</liferay-ui:search-container>
			</liferay-ui:section>

			<liferay-ui:section>

				<%
				FieldDisplayContext fieldDisplayContext = new FieldDisplayContext("/analytics_settings/edit_synced_user_fields", renderRequest, renderResponse);
				%>

				<clay:management-toolbar
					displayContext="<%= new FieldManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, fieldDisplayContext) %>"
				/>

				<liferay-ui:search-container
					id="selectUserFields"
					searchContainer="<%= fieldDisplayContext.getFieldSearch() %>"
					var="userFieldsSearchContainer"
				>
					<liferay-ui:search-container-row
						className="com.liferay.analytics.settings.web.internal.model.Field"
						escapedModel="<%= true %>"
						keyProperty="name"
						modelVar="userField"
					>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="attribute"
							value="<%= HtmlUtil.escape(userField.getName()) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="category"
							value="<%= HtmlUtil.escape(userField.getCategory()) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="data-type"
							value="<%= HtmlUtil.escape(userField.getDataType()) %>"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
						paginate="<%= false %>"
						searchResultCssClass="show-quick-actions-on-hover table table-autofit"
					/>
				</liferay-ui:search-container>
			</liferay-ui:section>
		</liferay-ui:tabs>

		<aui:button-row>
			<aui:button type="submit" value="save" />
			<aui:button href="<%= redirect %>" type="cancel" value="cancel" />
		</aui:button-row>
	</aui:form>
</clay:sheet>