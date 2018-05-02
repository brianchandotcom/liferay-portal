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

<%@ page import="com.liferay.social.kernel.model.SocialActivity" %><%@
page import="com.liferay.social.kernel.model.SocialActivityFeedEntry" %><%@
page import="com.liferay.social.kernel.service.SocialActivityLocalServiceUtil" %><%@
page import="com.liferay.social.kernel.util.SocialActivityDescriptor" %>

<%
List<SocialActivityDescriptor> activityDescriptors = (List<SocialActivityDescriptor>)request.getAttribute("liferay-social-activities:social-activities:activityDescriptors");
String className = (String)request.getAttribute("liferay-social-activities:social-activities:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-social-activities:social-activities:classPK"));
int feedDelta = GetterUtil.getInteger((String)request.getAttribute("liferay-social-activities:social-activities:feedDelta"));
String feedDisplayStyle = (String)request.getAttribute("liferay-social-activities:social-activities:feedDisplayStyle");
boolean feedEnabled = !PortalUtil.isRSSFeedsEnabled() ? false : GetterUtil.getBoolean((String)request.getAttribute("liferay-social-activities:social-activities:feedEnabled"));
ResourceURL feedResourceURL = (ResourceURL)request.getAttribute("liferay-social-activities:social-activities:feedResourceURL");
String feedTitle = (String)request.getAttribute("liferay-social-activities:social-activities:feedTitle");
String feedType = (String)request.getAttribute("liferay-social-activities:social-activities:feedType");
String feedURL = (String)request.getAttribute("liferay-social-activities:social-activities:feedURL");
String feedURLMessage = (String)request.getAttribute("liferay-social-activities:social-activities:feedURLMessage");

if (activityDescriptors == null) {
	List<SocialActivity> activities = SocialActivityLocalServiceUtil.getActivities(0, className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

	activityDescriptors = new ArrayList<SocialActivityDescriptor>(activities.size());

	for (SocialActivity activity : activities) {
		activityDescriptors.add(new SocialActivityDescriptor(activity));
	}
}

String selector = StringPool.BLANK;

Format dateFormatDate = FastDateFormatFactoryUtil.getSimpleDateFormat("MMMM d", locale, timeZone);
Format yearDateFormatDate = FastDateFormatFactoryUtil.getSimpleDateFormat("MMMM d, yyyy", locale, timeZone);

Format timeFormatDate = FastDateFormatFactoryUtil.getTime(locale, timeZone);
%>

<%@ include file="/social_activities/init-ext.jsp" %>