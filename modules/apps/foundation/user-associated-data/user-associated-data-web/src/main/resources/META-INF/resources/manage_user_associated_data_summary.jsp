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
User selUser = PortalUtil.getSelectedUser(request);

selUserId = selUser.getUserId();

int step = (int)request.getAttribute(UserAssociatedDataWebKeys.MANAGE_USER_ASSOCIATED_DATA_SUMMARY_STEP);
%>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="sheet sheet-lg">
		<div class="sheet-header">
			<h2 class="sheet-title"><liferay-ui:message key="personal-data-erasure" /></h2>
		</div>

		<div class="sheet-section">
			<h3 class="sheet-subtitle"><liferay-ui:message key="summary-page-step-one-title" /></h3>

			<div class="sheet-text">
				<div>
					<liferay-ui:message key="summary-page-step-one-description" />
				</div>

				<div>
					<portlet:actionURL name="/user_associated_data/deactivate_user" var="deactivateUserURL">
						<portlet:param name="redirect" value="<%= currentURLObj.toString() %>" />
						<portlet:param name="selUserId" value="<%= String.valueOf(selUserId) %>" />
					</portlet:actionURL>

					<aui:button
						disabled="<%= step != 1 %>"
						onClick='<%= renderResponse.getNamespace() + "confirmAction('" + deactivateUserURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-deactivate-the-user") + "')" %>'
						value="deactivate-user"
					/>

					<c:if test="<%= step > 1 %>">
						<liferay-ui:icon iconCssClass="icon-ok-sign" label="<%= true %>" message="user-was-successfully-deactivated" />
					</c:if>
				</div>
			</div>
		</div>

		<div class="sheet-section">
			<h3 class="sheet-subtitle"><liferay-ui:message key="summary-page-step-two-title" /></h3>

			<div class="sheet-text">
				<div>
					<liferay-ui:message key="summary-page-step-two-description" />

					<c:if test="<%= step == 2 %>">

						<%
						Group selUserGroup = selUser.getGroup();

						int selUserPublicLayoutsPageCount = selUser.getPublicLayoutsPageCount();
						int selUserPrivateLayoutsPageCount = selUser.getPrivateLayoutsPageCount();
						%>

						<c:if test="<%= selUserPublicLayoutsPageCount > 0 %>">
							<liferay-ui:icon
								label="<%= true %>"
								message="open-profile-pages"
								method="get"
								target="_blank"
								url="<%= selUserGroup.getDisplayURL(themeDisplay, false) %>"
							/>
						</c:if>

						<c:if test="<%= selUserPrivateLayoutsPageCount > 0 %>">
							<liferay-ui:icon
								label="<%= true %>"
								message="open-dashboard-pages"
								method="get"
								target="_blank"
								url="<%= selUserGroup.getDisplayURL(themeDisplay, true) %>"
							/>
						</c:if>
					</c:if>
				</div>

				<div>
					<portlet:actionURL name="/user_associated_data/forget_personal_site" var="forgetPersonalSiteURL">
						<portlet:param name="redirect" value="<%= currentURLObj.toString() %>" />
						<portlet:param name="selUserId" value="<%= String.valueOf(selUserId) %>" />
					</portlet:actionURL>

					<aui:button
						disabled="<%= step != 2 %>"
						onClick='<%= renderResponse.getNamespace() + "confirmAction('" + forgetPersonalSiteURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-forget-the-users-personal-site") + "')" %>'
						value="delete-personal-site"
					/>

					<c:if test="<%= step > 2 %>">
						<liferay-ui:icon iconCssClass="icon-ok-sign" label="<%= true %>" message="personal-site-was-successfully-forgotten" />
					</c:if>
				</div>
			</div>
		</div>

		<div class="sheet-section">
			<h3 class="sheet-subtitle"><liferay-ui:message key="summary-page-step-three-title" /></h3>

			<div class="sheet-text">
				<div>
					<liferay-ui:message key="summary-page-step-three-description" />
				</div>

				<div>
					<portlet:renderURL var="manageUserAssociatedDataEntitiesURL">
						<portlet:param name="mvcRenderCommandName" value="/user_associated_data/manage_user_associated_data_entity_sets" />
						<portlet:param name="selUserId" value="<%= String.valueOf(selUserId) %>" />
					</portlet:renderURL>

					<aui:button disabled="<%= step != 3 %>" onClick="<%= manageUserAssociatedDataEntitiesURL %>" value="review" />

					<c:if test="<%= step > 3 %>">
						<liferay-ui:icon iconCssClass="icon-ok-sign" label="<%= true %>" message="all-ambiguous-data-was-forgotten" />
					</c:if>
				</div>
			</div>
		</div>

		<div class="sheet-section">
			<h3 class="sheet-subtitle"><liferay-ui:message key="summary-page-step-four-title" /></h3>

			<div class="sheet-text">
				<div>
					<liferay-ui:message key="summary-page-step-four-description" />
				</div>

				<div>
					<portlet:actionURL name="/user_associated_data/delete_remaining_user_associated_data" var="deleteRemainingUserAssociatedDataURL">
						<portlet:param name="redirect" value="<%= currentURLObj.toString() %>" />
						<portlet:param name="selUserId" value="<%= String.valueOf(selUserId) %>" />
					</portlet:actionURL>

					<aui:button
						disabled="<%= step != 4 %>"
						onClick='<%= renderResponse.getNamespace() + "confirmAction('" + deleteRemainingUserAssociatedDataURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-anonymize-the-users-personal-data") + "')" %>'
						value="anonymize-data"
					/>

					<c:if test="<%= step > 4 %>">
						<liferay-ui:icon iconCssClass="icon-ok-sign" label="<%= true %>" message="all-data-was-anonymized" />
					</c:if>
				</div>
			</div>
		</div>

		<div class="sheet-section">
			<h3 class="sheet-subtitle"><liferay-ui:message key="summary-page-step-five-title" /></h3>

			<div class="sheet-text">
				<div>
					<liferay-ui:message key="summary-page-step-five-description" />
				</div>

				<div>
					<portlet:actionURL name="/user_associated_data/delete_user" var="deleteUserURL">
						<portlet:param name="selUserId" value="<%= String.valueOf(selUserId) %>" />
					</portlet:actionURL>

					<aui:button
						disabled="<%= step != 5 %>"
						onClick='<%= renderResponse.getNamespace() + "confirmAction('" + deleteUserURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-user") + "')" %>'
						value="delete-user"
					/>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/action/confirm_action_js.jspf" %>