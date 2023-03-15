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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.osb.faro.constants.DocumentationConstants" %><%@
page import="com.liferay.osb.faro.constants.FaroChannelConstants" %><%@
page import="com.liferay.osb.faro.constants.FaroProjectConstants" %><%@
page import="com.liferay.osb.faro.contacts.model.constants.ContactsCardTemplateConstants" %><%@
page import="com.liferay.osb.faro.engine.client.constants.ActivityConstants" %><%@
page import="com.liferay.osb.faro.engine.client.constants.DataSourceConstants" %><%@
page import="com.liferay.osb.faro.engine.client.constants.FieldMappingConstants" %><%@
page import="com.liferay.osb.faro.engine.client.constants.LCPProjectConstants" %><%@
page import="com.liferay.osb.faro.engine.client.constants.SegmentConstants" %><%@
page import="com.liferay.osb.faro.engine.client.constants.TimeConstants" %><%@
page import="com.liferay.osb.faro.web.internal.constants.FaroConstants" %><%@
page import="com.liferay.osb.faro.web.internal.constants.FaroPaginationConstants" %><%@
page import="com.liferay.osb.faro.web.internal.constants.FaroPortletKeys" %><%@
page import="com.liferay.osb.faro.web.internal.constants.FaroPreferencesConstants" %><%@
page import="com.liferay.osb.faro.web.internal.constants.FaroSubscriptionConstants" %><%@
page import="com.liferay.osb.faro.web.internal.constants.FaroWebKeys" %><%@
page import="com.liferay.osb.faro.web.internal.constants.UserConstants" %><%@
page import="com.liferay.osb.faro.web.internal.util.JSONUtil" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
Map<String, Object> faroConstants = new HashMap<>();

faroConstants.put("activityActions", ActivityConstants.getActions());
faroConstants.put("applications", FaroConstants.getApplications());

String cerebroAssetsURL = (String)request.getAttribute(FaroWebKeys.CEREBRO_ASSETS_URL);
String cerebroTouchpointsURL = (String)request.getAttribute(FaroWebKeys.CEREBRO_TOUCHPOINTS_URL);

faroConstants.put("cerebroAssetsURL", cerebroAssetsURL);
faroConstants.put("cerebroTouchpointsURL", cerebroTouchpointsURL);

faroConstants.put("channelPermissionTypes", FaroChannelConstants.getPermissionTypes());
faroConstants.put("contactsCardTemplateTypes", ContactsCardTemplateConstants.getConstants());
faroConstants.put("dataSourceDisplayStatuses", DataSourceConstants.getDisplayStatuses());
faroConstants.put("dataSourceProgressStatuses", DataSourceConstants.getProgressStatuses());
faroConstants.put("dataSourceStatuses", DataSourceConstants.getStatuses());
faroConstants.put("dataSourceTypes", DataSourceConstants.getTypes());
faroConstants.put("documentationURLs", DocumentationConstants.getURLs());
faroConstants.put("entityTypes", FaroConstants.getTypes());
faroConstants.put("faroURL", System.getenv("FARO_URL"));
faroConstants.put("fieldContexts", FieldMappingConstants.getContexts());
faroConstants.put("fieldOwnerTypes", FieldMappingConstants.getOwnerTypes());
faroConstants.put("fieldTypes", FieldMappingConstants.getFieldTypes());
faroConstants.put("locale", locale.toString());
faroConstants.put("pagination", FaroPaginationConstants.getConstants());
faroConstants.put("pathThemeImages", themeDisplay.getPathThemeImages());
faroConstants.put("portletNamespace", PortalUtil.getPortletNamespace(FaroPortletKeys.FARO));
faroConstants.put("preferencesScopes", FaroPreferencesConstants.getScopes());
faroConstants.put("projectLocations", LCPProjectConstants.getLocations());
faroConstants.put("projectStates", FaroProjectConstants.getStates());
faroConstants.put("segmentStates", SegmentConstants.getSegmentStates());
faroConstants.put("segmentTypes", SegmentConstants.getSegmentTypes());
faroConstants.put("subscriptionPlans", FaroSubscriptionConstants.getFaroSubscriptionPlans());
faroConstants.put("subscriptionStatuses", FaroSubscriptionConstants.getStatuses());
faroConstants.put("timeIntervals", TimeConstants.getIntervals());
faroConstants.put("timeSpans", TimeConstants.getTimeSpans());

User currentUser = themeDisplay.getUser();

faroConstants.put("userName", currentUser.getFullName());

faroConstants.put("userRoleNames", UserConstants.getRoleNames());
faroConstants.put("userStatuses", UserConstants.getStatuses());
%>

<aui:script position="inline">
	window.faroConstants = <%= JSONUtil.writeValueAsString(faroConstants) %>;
</aui:script>