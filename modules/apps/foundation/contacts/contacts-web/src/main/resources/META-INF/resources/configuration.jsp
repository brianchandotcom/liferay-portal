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

<div class="contacts-portlet">
	<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

	<liferay-frontend:edit-form
		action="<%= configurationActionURL %>"
		method="post"
		name="fm"
	>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

		<liferay-frontend:edit-form-body>
			<liferay-frontend:fieldset-group>
				<liferay-frontend:fieldset>
					<aui:select label="display-style" name="preferences--displayStyle--" value="<%= displayStyle %>">
						<aui:option label="<%= ContactsConstants.DISPLAY_STYLE_FULL_LABEL %>" value="<%= ContactsConstants.DISPLAY_STYLE_FULL %>" />
						<aui:option label="<%= ContactsConstants.DISPLAY_STYLE_BASIC_LABEL %>" value="<%= ContactsConstants.DISPLAY_STYLE_BASIC %>" />
						<aui:option label="<%= ContactsConstants.DISPLAY_STYLE_DETAIL_LABEL %>" value="<%= ContactsConstants.DISPLAY_STYLE_DETAIL %>" />
					</aui:select>

					<aui:row>
						<aui:col width="<%= 50 %>">
							<aui:input name="preferences--showAdditionalEmailAddresses--" type="checkbox" value="<%= showAdditionalEmailAddresses %>" />

							<aui:input name="preferences--showAddresses--" type="checkbox" value="<%= showAddresses %>" />

							<aui:input name="preferences--showComments--" type="checkbox" value="<%= showComments %>" />

							<aui:input name="preferences--showCompleteYourProfile--" type="checkbox" value="<%= showCompleteYourProfile %>" />

							<aui:input name="preferences--showInstantMessenger--" type="checkbox" value="<%= showInstantMessenger %>" />

							<aui:input name="preferences--showPhones--" type="checkbox" value="<%= showPhones %>" />

							<aui:input label="show-sms" name="preferences--showSMS--" type="checkbox" value="<%= showSMS %>" />
						</aui:col>

						<aui:col width="<%= 50 %>">
							<aui:input name="preferences--showSocialNetwork--" type="checkbox" value="<%= showSocialNetwork %>" />

							<aui:input label="show-icon" name="preferences--showIcon--" type="checkbox" value="<%= showIcon %>" />

							<aui:input name="preferences--showRecentActivity--" type="checkbox" value="<%= showRecentActivity %>" />

							<aui:input label="show-sites" name="preferences--showSites--" type="checkbox" value="<%= showSites %>" />

							<aui:input label="show-tags" name="preferences--showTags--" type="checkbox" value="<%= showTags %>" />

							<aui:input name="preferences--showUsersInformation--" type="checkbox" value="<%= showUsersInformation %>" />

							<aui:input name="preferences--showWebsites--" type="checkbox" value="<%= showWebsites %>" />
						</aui:col>
					</aui:row>
				</liferay-frontend:fieldset>
			</liferay-frontend:fieldset-group>
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<aui:button type="submit" />

			<aui:button type="cancel" />
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</div>