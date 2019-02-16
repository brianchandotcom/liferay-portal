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

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<aui:form method="post" name="viewUADSummaryFm">
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
						message="user-successfully-deactivated"
					/>
				</c:if>
			</div>

			<div class="sheet-text">
				<liferay-ui:message key="summary-page-step-two-description" />
			</div>

			<div class="sheet-text">
				<portlet:renderURL var="reviewUADDataURL">
					<portlet:param name="mvcRenderCommandName" value="/review_uad_data" />
					<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
				</portlet:renderURL>

				<aui:button cssClass="btn-sm" disabled="<%= step != 2 %>" onClick="<%= reviewUADDataURL %>" value="review" />

				<c:if test="<%= step > 2 %>">
					<liferay-ui:icon
						cssClass="text-success"
						iconCssClass="icon-ok-sign"
						label="<%= true %>"
						message="application-data-successfully-reviewed"
					/>
				</c:if>
			</div>

			<div class="sheet-text">
				<liferay-ui:message key="summary-page-step-three-description" />
			</div>

			<div class="sheet-text">
				<portlet:actionURL name="/delete_user" var="deleteUserURL" />

				<aui:button cssClass="btn-sm" disabled="<%= step != 3 %>" onClick='<%= renderResponse.getNamespace() + "confirmAction('viewUADSummaryFm', '" + deleteUserURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-user") + "')" %>' value="delete-user" />
			</div>
		</div>
	</aui:form>
</div>

<%@ include file="/action/confirm_action_js.jspf" %>