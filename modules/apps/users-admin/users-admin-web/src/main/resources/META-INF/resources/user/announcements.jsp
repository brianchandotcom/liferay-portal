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
User selUser = (User)request.getAttribute(UsersAdminWebKeys.SELECTED_USER);

List<AnnouncementsDelivery> deliveries = null;

if (selUser != null) {
	deliveries = AnnouncementsDeliveryLocalServiceUtil.getUserDeliveries(selUser.getUserId());
}
else {
	deliveries = new ArrayList<AnnouncementsDelivery>(AnnouncementsEntryConstants.TYPES.length);

	for (String type : AnnouncementsEntryConstants.TYPES) {
		AnnouncementsDelivery delivery = new AnnouncementsDeliveryImpl();

		delivery.setType(type);
		delivery.setWebsite(true);

		deliveries.add(delivery);
	}
}
%>

<div class="sheet-section">
	<div class="text-muted"><liferay-ui:message key="select-the-delivery-options-for-alerts-and-announcements" /></div>
</div>

<liferay-ui:search-container>
	<liferay-ui:search-container-results
		results="<%= deliveries %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.announcements.kernel.model.AnnouncementsDelivery"
		escapedModel="<%= true %>"
		keyProperty="deliveryId"
		modelVar="delivery"
	>
		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(request, delivery.getType()) %>"
		/>

		<liferay-ui:search-container-column-jsp
			name="email"
			path="/user/announcements_checkbox.jsp"
		/>

		<liferay-ui:search-container-column-jsp
			name="sms"
			path="/user/announcements_checkbox.jsp"
		/>

		<liferay-ui:search-container-column-jsp
			name="website"
			path="/user/announcements_checkbox.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>