<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationDisplayContext commerceOrganizationDisplayContext = (CommerceOrganizationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

User selectedUser = commerceOrganizationDisplayContext.getSelectedUser();
%>

<div class="account-management">
	<section class="panel panel-secondary">
		<div class="panel-body">
			<div class="row">
				<div class="col-auto">
					<img alt="avatar" class="account-management__thumbnail img-fluid rounded-circle" src="<%= HtmlUtil.escapeAttribute(selectedUser.getPortraitURL(themeDisplay)) %>" />
				</div>

				<div class="col d-flex flex-col justify-content-center">
					<span class="account-management__name">
						<%= HtmlUtil.escape(selectedUser.getFullName()) %>
					</span>
					<span class="account-management__email">
						<%= HtmlUtil.escape(selectedUser.getEmailAddress()) %>
					</span>
				</div>
			</div>
		</div>
	</section>
</div>