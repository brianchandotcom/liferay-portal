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
CommerceOrganizationDetailDisplayContext commerceOrganizationDetailDisplayContext = (CommerceOrganizationDetailDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationDetailDisplayContext.getCurrentOrganization();

commerceOrganizationDetailDisplayContext.setBreadcrumbs(organization);
%>

<div class="product-detail-header">
	<div class="container-fluid-1280">
		<div class="col-md-12">
			<div>
				<liferay-ui:breadcrumb
					showCurrentGroup="<%= false %>"
					showGuestGroup="<%= false %>"
					showLayout="<%= false %>"
					showPortletBreadcrumb="<%= true %>"
				/>
			</div>
		</div>
	</div>
</div>

<c:choose>
	<c:when test="<%= organization == null %>">
		<div class="alert alert-warning" role="alert">
			<span class="alert-indicator">
				<svg aria-hidden="true" class="lexicon-icon lexicon-icon-warning-full">
					<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#warning-full"></use>
				</svg>
			</span>

			<liferay-ui:message key="organization-not-configured-for-this-site" />
		</div>
	</c:when>
	<c:otherwise>
		<liferay-frontend:screen-navigation
			containerCssClass="col-md-10"
			context="<%= commerceOrganizationDetailDisplayContext.getCurrentOrganization() %>"
			key="<%= CommerceOrganizationScreenNavigationConstants.SCREEN_NAVIGATION_KEY %>"
			navCssClass="col-md-2"
			portletURL="<%= currentURLObj %>"
		/>
	</c:otherwise>
</c:choose>