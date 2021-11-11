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
ViewSXPBlueprintsDisplayContext viewSXPBlueprintsDisplayContext = (ViewSXPBlueprintsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_BLUEPRINTS_DISPLAY_CONTEXT);
%>

<clay:headless-data-set-display
	apiURL="<%= viewSXPBlueprintsDisplayContext.getAPIURL() %>"
	clayDataSetActionDropdownItems="<%= viewSXPBlueprintsDisplayContext.getClayDataSetActionDropdownItems() %>"
	formName="fm"
	id="<%= SXPBlueprintAdminClayDataSetDisplayNames.SXP_BLUEPRINTS %>"
	itemsPerPage="<%= 20 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= liferayPortletResponse.createRenderURL() %>"
	style="fluid"
/>