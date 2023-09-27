<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.social.kernel.model.SocialActivity" %><%@
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

Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(LanguageUtil.get(request, "mmm-d"), locale, timeZone);
Format yearDateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(LanguageUtil.get(request, "mmmm-d-yyyy"), locale, timeZone);

Format timeFormat = FastDateFormatFactoryUtil.getTime(locale, timeZone);
%>

<%@ include file="/social_activities/init-ext.jsp" %>