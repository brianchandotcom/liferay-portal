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

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "notifications"));
					});
			}
		}
	%>'
/>

<div class="manage-notifications-content portlet-configuration-setup">
	<div class="manage-notifications" id="<portlet:namespace />manageNotifications">
		<portlet:actionURL name="updateUserNotificationDelivery" var="updateUserNotificationDeliveryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<liferay-frontend:edit-form
			action="<%= updateUserNotificationDeliveryURL %>"
			fluid="<%= true %>"
			name="fm"
		>

			<%
			Map<String, List<UserNotificationDefinition>> userNotificationDefinitionsMap = TreeMapBuilder.<String, List<UserNotificationDefinition>>create(
				new PortletIdComparator(locale)
			).putAll(
				UserNotificationManagerUtil.getActiveUserNotificationDefinitions()
			).build();

			List<Long> userNotificationDeliveryIds = new ArrayList<Long>();
			%>

			<liferay-frontend:edit-form-body>
				<liferay-frontend:fieldset-group>

					<%
					boolean first = true;

					for (Map.Entry<String, List<UserNotificationDefinition>> entry : userNotificationDefinitionsMap.entrySet()) {
						Portlet portlet = PortletLocalServiceUtil.getPortletById(entry.getKey());
					%>

						<liferay-frontend:fieldset
							collapsed="<%= !first %>"
							collapsible="<%= true %>"
							label="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>"
						>
							<table class="table table-autofit table-condensed table-responsive">
								<tbody>

									<%
									List<UserNotificationDefinition> userNotificationDefinitions = entry.getValue();

									for (int i = 0; i < userNotificationDefinitions.size(); i++) {
										UserNotificationDefinition userNotificationDefinition = userNotificationDefinitions.get(i);

										Map<Integer, UserNotificationDeliveryType> userNotificationDeliveryTypesMap = userNotificationDefinition.getUserNotificationDeliveryTypes();
									%>

										<c:if test="<%= i == 0 %>">
											<tr>
												<th class="table-cell-expand">
													<liferay-ui:message key="receive-a-notification-when-someone" />
												</th>

												<%
												for (Map.Entry<Integer, UserNotificationDeliveryType> userNotificationDeliveryTypeEntry : userNotificationDeliveryTypesMap.entrySet()) {
													UserNotificationDeliveryType userNotificationDeliveryType = userNotificationDeliveryTypeEntry.getValue();
												%>

													<th class="lfr-<%= userNotificationDeliveryType.getName() %>-column">
														<liferay-ui:message key="<%= userNotificationDeliveryType.getName() %>" />
													</th>

												<%
												}
												%>

											</tr>
										</c:if>

										<tr>
											<td class="table-cell-expand">
												<liferay-ui:message key="<%= userNotificationDefinition.getDescription(locale) %>" />
											</td>

											<%
											for (Map.Entry<Integer, UserNotificationDeliveryType> userNotificationDeliveryTypeEntry : userNotificationDeliveryTypesMap.entrySet()) {
												UserNotificationDeliveryType userNotificationDeliveryType = userNotificationDeliveryTypeEntry.getValue();

												UserNotificationDelivery userNotificationDelivery = UserNotificationDeliveryLocalServiceUtil.getUserNotificationDelivery(themeDisplay.getUserId(), entry.getKey(), userNotificationDefinition.getClassNameId(), userNotificationDefinition.getNotificationType(), userNotificationDeliveryType.getType(), userNotificationDeliveryType.isDefault());

												if (userNotificationDeliveryType.isModifiable()) {
													userNotificationDeliveryIds.add(userNotificationDelivery.getUserNotificationDeliveryId());
												}
											%>

												<td class="lfr-<%= userNotificationDeliveryType.getName() %>-column">
													<div class="checkbox-container">
														<aui:input cssClass="notification-delivery" data-userNotificationDeliveryId="<%= String.valueOf(userNotificationDelivery.getUserNotificationDeliveryId()) %>" disabled="<%= !userNotificationDeliveryType.isModifiable() %>" inlineLabel="<%= Boolean.TRUE.toString() %>" label="" name="<%= String.valueOf(userNotificationDelivery.getUserNotificationDeliveryId()) %>" type="toggle-switch" value="<%= userNotificationDelivery.isDeliver() %>" />
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
						</liferay-frontend:fieldset>

					<%
						first = false;
					}
					%>

				</liferay-frontend:fieldset-group>
			</liferay-frontend:edit-form-body>

			<aui:input name="userNotificationDeliveryIds" type="hidden" value="<%= StringUtil.merge(userNotificationDeliveryIds) %>" />

			<liferay-frontend:edit-form-footer>
				<liferay-frontend:edit-form-buttons />
			</liferay-frontend:edit-form-footer>
		</liferay-frontend:edit-form>
	</div>
</div>