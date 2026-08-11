<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/revamp/init.jsp" %>

<liferay-staging:defineObjects />

<%
if (liveGroup == null) {
	liveGroup = group;
	liveGroupId = groupId;
}

String publishBackURL = ParamUtil.getString(request, "backURL", themeDisplay.getURLCurrent());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(publishBackURL);

PublishSchedulerDisplayContext publishSchedulerDisplayContext = new PublishSchedulerDisplayContext(liveGroup, locale);

long scheduledPublishProcessId = ParamUtil.getLong(request, "scheduledPublishProcessId");

renderResponse.setTitle(publishSchedulerDisplayContext.getTitle(scheduledPublishProcessId));

String scheduledPublishBackURL = HttpComponentsUtil.setParameter(publishBackURL, liferayPortletResponse.getNamespace() + "tabs1", "scheduled");

Date lastPublishDate = null;

Group publishSourceGroup = (stagingGroup != null) ? stagingGroup : group;

LayoutSet layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(publishSourceGroup.getGroupId(), false);

if (layoutSet != null) {
	lastPublishDate = ExportImportDateUtil.getLastPublishDate(layoutSet);
}
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<div class="sheet">
		<span aria-hidden="true" class="loading-animation mb-9 mt-8"></span>
	</div>

	<react:component
		module="{NewPublish} from exportimport-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"backURL", publishBackURL
			).put(
				"commentsAndRatingsEnabled", publishSchedulerDisplayContext.isCommentsAndRatingsEnabled()
			).put(
				"defaultScheduled", ParamUtil.getBoolean(request, "scheduled")
			).put(
				"lastPublishDate", (lastPublishDate == null) ? null : String.valueOf(lastPublishDate.toInstant())
			).put(
				"lookAndFeelEnabled", publishSchedulerDisplayContext.isLookAndFeelEnabled()
			).put(
				"pageTreeModalConfiguration",
				HashMapBuilder.<String, Object>put(
					"groupId", publishSourceGroup.getGroupId()
				).put(
					"pageSize", PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN
				).put(
					"privateLayoutsAvailable", publishSourceGroup.isPrivateLayoutsEnabled() && publishSourceGroup.hasPrivateLayouts()
				).build()
			).put(
				"publishPreviewAPIURL", publishSchedulerDisplayContext.getPublishPreviewAPIURL()
			).put(
				"publishProcessAPIURL", publishSchedulerDisplayContext.getPublishProcessesAPIURL()
			).put(
				"scheduledBackURL", scheduledPublishBackURL
			).put(
				"scheduledPublishProcessesAPIURL", publishSchedulerDisplayContext.getScheduledPublishProcessesAPIURL()
			).put(
				"scheduledPublishProcessId", (scheduledPublishProcessId > 0) ? scheduledPublishProcessId : null
			).put(
				"timeZoneId", timeZone.getID()
			).put(
				"timeZones", publishSchedulerDisplayContext.getTimeZonesJSONArray()
			).build()
		%>'
	/>
</clay:container-fluid>