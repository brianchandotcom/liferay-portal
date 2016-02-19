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

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="notifications" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<div class="container-fluid-1280 manage-notifications-content">
	<div class="manage-notifications" id="<portlet:namespace />manageNotifications">
		<portlet:actionURL name="updateUserNotificationDelivery" var="updateUserNotificationDeliveryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<aui:form action="<%= updateUserNotificationDeliveryURL %>" name="fm">

			<%
			Map<String, List<UserNotificationDefinition>> userNotificationDefinitionsMap = new TreeMap<String, List<UserNotificationDefinition>>(new PortletIdComparator(locale));

			userNotificationDefinitionsMap.putAll(UserNotificationManagerUtil.getUserNotificationDefinitions());

			List<Long> userNotificationDeliveryIds = new ArrayList<Long>();
			%>

			<aui:fieldset-group markupView="lexicon">

				<%
				boolean first = true;

				for (Map.Entry<String, List<UserNotificationDefinition>> entry : userNotificationDefinitionsMap.entrySet()) {
					Portlet portlet = PortletLocalServiceUtil.getPortletById(entry.getKey());
				%>

					<aui:fieldset collapsed="<%= !first %>" collapsible="<%= true %>" label="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>">

						<table class="table table-condensed table-list">
							<tbody>

							<%
							List<UserNotificationDefinition> userNotificationDefinitions = entry.getValue();

							for (int i = 0; i < userNotificationDefinitions.size(); i++) {
								UserNotificationDefinition userNotificationDefinition = userNotificationDefinitions.get(i);

								Map<Integer, UserNotificationDeliveryType> userNotificationDeliveryTypesMap = userNotificationDefinition.getUserNotificationDeliveryTypes();
							%>

								<c:if test="<%= i == 0 %>">
									<tr>
										<th class="table-cell-content">
											<liferay-ui:message key="receive-a-notification-when-someone" />
										</th>

										<%
										for (Map.Entry<Integer, UserNotificationDeliveryType> userNotificationDeliveryTypeEntry : userNotificationDeliveryTypesMap.entrySet()) {
											UserNotificationDeliveryType userNotificationDeliveryType = userNotificationDeliveryTypeEntry.getValue();
										%>

											<th class="table-cell-field">
												<%= LanguageUtil.get(request, userNotificationDeliveryType.getName()) %>
											</th>

										<%
										}
										%>

									</tr>
								</c:if>

								<tr>
									<td class="table-cell-content">
										<liferay-ui:message key="<%= userNotificationDefinition.getDescription(locale) %>" />
									</td>

									<%
									for (Map.Entry<Integer, UserNotificationDeliveryType> userNotificationDeliveryTypeEntry : userNotificationDeliveryTypesMap.entrySet()) {
										UserNotificationDeliveryType userNotificationDeliveryType = userNotificationDeliveryTypeEntry.getValue();

										UserNotificationDelivery userNotificationDelivery = UserNotificationDeliveryLocalServiceUtil.getUserNotificationDelivery(themeDisplay.getUserId(), entry.getKey(), userNotificationDefinition.getClassNameId(), userNotificationDefinition.getNotificationType(), userNotificationDeliveryType.getType(), userNotificationDeliveryType.isDefault());

										userNotificationDeliveryIds.add(userNotificationDelivery.getUserNotificationDeliveryId());
									%>

										<td class="table-cell-field">
											<div class="checkbox-container">
												<aui:input cssClass="notification-delivery" data-userNotificationDeliveryId="<%= String.valueOf(userNotificationDelivery.getUserNotificationDeliveryId()) %>" disabled="<%= !userNotificationDeliveryType.isModifiable() %>" inlineLabel="true" label="" name="<%= String.valueOf(userNotificationDelivery.getUserNotificationDeliveryId()) %>" type="toggle-switch" value="<%= userNotificationDelivery.isDeliver() %>" />
											</div>
										</td>

									<%
									}
									%>

								</tr>

							<%
							}
							%>

							</tbody>
						</table>
					</aui:fieldset>

				<%
					first = false;
				}
				%>

			</aui:fieldset-group>

			<aui:input name="userNotificationDeliveryIds" type="hidden" value="<%= StringUtil.merge(userNotificationDeliveryIds) %>" />

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />

				<aui:button cssClass="btn-lg" type="cancel" />
			</aui:button-row>
		</aui:form>
	</div>
</div>