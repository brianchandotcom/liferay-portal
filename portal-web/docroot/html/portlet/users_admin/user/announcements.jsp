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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");

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

<h3><liferay-ui:message key="alerts-and-announcements" /></h3>

<liferay-ui:message key="select-the-delivery-options-for-alerts-and-announcements" />

<liferay-ui:search-container>
	<liferay-ui:search-container-results
		results="<%= deliveries %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.announcements.model.AnnouncementsDelivery"
		escapedModel="<%= true %>"
		keyProperty="deliveryId"
		modelVar="delivery"
	>
		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(pageContext, delivery.getType()) %>"
		/>
		<liferay-ui:search-container-column-jsp
			name="email"
			path="/html/portlet/users_admin/user/announcements_checkbox.jsp"
		/>
		<liferay-ui:search-container-column-jsp
			name="sms"
			path="/html/portlet/users_admin/user/announcements_checkbox.jsp"
		/>
		<liferay-ui:search-container-column-jsp
			name="website"
			path="/html/portlet/users_admin/user/announcements_checkbox.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>