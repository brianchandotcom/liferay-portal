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
int step = (int)request.getAttribute(UADWebKeys.VIEW_UAD_SUMMARY_STEP);

portletDisplay.setShowBackIcon(true);

LiferayPortletURL usersAdminURL = liferayPortletResponse.createLiferayPortletURL(UsersAdminPortletKeys.USERS_ADMIN, PortletRequest.RENDER_PHASE);

portletDisplay.setURLBack(usersAdminURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "personal-data-erasure")));
%>

<aui:form class="container-fluid container-fluid-max-xl container-form-lg" method="post" name="viewUADSummaryFm">
	<aui:input name="redirect" type="hidden" value="<%= currentURLObj.toString() %>" />
	<aui:input name="p_u_i_d" type="hidden" value="<%= String.valueOf(selectedUser.getUserId()) %>" />

	<div class="sheet sheet-lg">
		<div class="sheet-header">
			<h2><liferay-ui:message key="personal-data-erasure" /></h2>
		</div>

		<div class="sheet-text">
			<liferay-ui:message key="summary-page-step-one-description" />
		</div>

		<div class="sheet-text">
			<portlet:actionURL name="/deactivate_user" var="deactivateUserURL" />

			<aui:button cssClass="btn-sm" disabled="<%= step != 1 %>" onClick='<%= renderResponse.getNamespace() + "confirmAction('viewUADSummaryFm', '" + deactivateUserURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-deactivate-the-user") + "')" %>' value="deactivate-user" />

			<c:if test="<%= step > 1 %>">
				<liferay-ui:icon
					cssClass="text-success"
					iconCssClass="icon-ok-sign"
					label="<%= true %>"
					message="user-was-successfully-deactivated"
				/>
			</c:if>
		</div>

		<div class="sheet-text">
			<liferay-ui:message key="summary-page-step-two-description" />

			<c:if test="<%= step == 2 %>">

				<%
				Group selectedUserGroup = selectedUser.getGroup();

				int selectedUserPublicLayoutsPageCount = selectedUser.getPublicLayoutsPageCount();
				int selectedUserPrivateLayoutsPageCount = selectedUser.getPrivateLayoutsPageCount();
				%>

				<c:if test="<%= selectedUserPublicLayoutsPageCount > 0 %>">
					<liferay-ui:icon
						label="<%= true %>"
						message="open-profile-pages"
						method="get"
						target="_blank"
						url="<%= selectedUserGroup.getDisplayURL(themeDisplay, false) %>"
					/>
				</c:if>

				<c:if test="<%= selectedUserPrivateLayoutsPageCount > 0 %>">
					<liferay-ui:icon
						label="<%= true %>"
						message="open-dashboard-pages"
						method="get"
						target="_blank"
						url="<%= selectedUserGroup.getDisplayURL(themeDisplay, true) %>"
					/>
				</c:if>
			</c:if>
		</div>

		<div class="sheet-text">
			<portlet:actionURL name="/forget_personal_site" var="forgetPersonalSiteURL" />

			<aui:button cssClass="btn-sm" disabled="<%= step != 2 %>" onClick='<%= renderResponse.getNamespace() + "confirmAction('viewUADSummaryFm', '" + forgetPersonalSiteURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-forget-the-users-personal-site") + "')" %>' value="delete-personal-site" />

			<c:if test="<%= step > 2 %>">
				<liferay-ui:icon
					cssClass="text-success"
					iconCssClass="icon-ok-sign"
					label="<%= true %>"
					message="personal-site-was-successfully-forgotten"
				/>
			</c:if>
		</div>

		<div class="sheet-text">
			<liferay-ui:message key="summary-page-step-three-description" />
		</div>

		<div class="sheet-text">
			<portlet:renderURL var="viewUADEntitiesURL">
				<portlet:param name="mvcRenderCommandName" value="/view_uad_applications_summary" />
				<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
			</portlet:renderURL>

			<aui:button cssClass="btn-sm" disabled="<%= step != 3 %>" onClick="<%= viewUADEntitiesURL %>" value="review" />

			<c:if test="<%= step > 3 %>">
				<liferay-ui:icon
					cssClass="text-success"
					iconCssClass="icon-ok-sign"
					label="<%= true %>"
					message="all-ambiguous-data-was-forgotten"
				/>
			</c:if>
		</div>

		<div class="sheet-text">
			<liferay-ui:message key="summary-page-step-four-description" />
		</div>

		<div class="sheet-text">
			<portlet:actionURL name="/delete_remaining_uad" var="deleteURL" />

			<aui:button cssClass="btn-sm" disabled="<%= step != 4 %>" onClick='<%= renderResponse.getNamespace() + "confirmAction('viewUADSummaryFm', '" + deleteURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-anonymize-the-users-personal-data") + "')" %>' value="anonymize-data" />

			<c:if test="<%= step > 4 %>">
				<liferay-ui:icon
					cssClass="text-success"
					iconCssClass="icon-ok-sign"
					label="<%= true %>"
					message="all-data-was-anonymized"
				/>
			</c:if>
		</div>

		<div class="sheet-text">
			<liferay-ui:message key="summary-page-step-five-description" />
		</div>

		<div class="sheet-text">
			<portlet:actionURL name="/delete_user" var="deleteUserURL" />

			<aui:button cssClass="btn-sm" disabled="<%= step != 5 %>" onClick='<%= renderResponse.getNamespace() + "confirmAction('viewUADSummaryFm', '" + deleteUserURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-user") + "')" %>' value="delete-user" />
		</div>
	</div>
</aui:form>

<%@ include file="/action/confirm_action_js.jspf" %>