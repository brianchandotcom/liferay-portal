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

<%@ include file="/social_activities/init.jsp" %>

<div class="taglib-social-activities">
	<c:if test="<%= feedEnabled && !activityDescriptors.isEmpty() %>">
		<div class="clearfix">
			<div class="pull-right">
				<liferay-rss:rss
					delta="<%= feedDelta %>"
					displayStyle="<%= feedDisplayStyle %>"
					feedType="<%= feedType %>"
					message="<%= feedURLMessage %>"
					name="<%= feedTitle %>"
					resourceURL="<%= feedResourceURL %>"
					url="<%= feedURL %>"
				/>
			</div>
		</div>
	</c:if>

	<%
	ServiceContext serviceContext = ServiceContextFactory.getInstance(request);

	boolean hasActivities = false;

	Date now = new Date();

	int daysBetween = -1;

	for (SocialActivityDescriptor activityDescriptor : activityDescriptors) {
		SocialActivityFeedEntry activityFeedEntry = activityDescriptor.interpret(selector, serviceContext);

		if (activityFeedEntry == null) {
			continue;
		}

		Date activityDate = new Date(activityDescriptor.getCreateDate());

		int curDaysBetween = DateUtil.getDaysBetween(activityDate, now, timeZone);
	%>

		<c:if test="<%= curDaysBetween > daysBetween %>">

			<%
			daysBetween = curDaysBetween;

			if (hasActivities) {
			%>

				</ul>

			<%
			}
			%>

			<ul class="list-unstyled">
				<li class="splitter">
					<c:choose>
						<c:when test="<%= curDaysBetween == 0 %>">
							<liferay-ui:message key="today" />
						</c:when>
						<c:when test="<%= curDaysBetween == 1 %>">
							<liferay-ui:message key="yesterday" />
						</c:when>
						<c:when test="<%= DateUtil.getYear(activityDate) == DateUtil.getYear(now) %>">
							<%= dateFormatDate.format(activityDescriptor.getCreateDate()) %>
						</c:when>
						<c:otherwise>
							<%= yearDateFormatDate.format(activityDescriptor.getCreateDate()) %>
						</c:otherwise>
					</c:choose>
				</li>
		</c:if>

		<li class="list-group-item">
			<div class="card card-horizontal">
				<div class="card-row card-row-padded">
					<div class="card-col-field">
						<liferay-ui:user-portrait
							userId="<%= activityDescriptor.getUserId() %>"
						/>
					</div>

					<div class="card-col-content card-col-gutters">
						<h5 class="text-default">
							<%= timeFormatDate.format(activityDescriptor.getCreateDate()) %>
						</h5>

						<%= activityFeedEntry.getTitle() %>

						<%= activityFeedEntry.getBody() %>
					</div>
				</div>
			</div>
		</li>

	<%
		if (!hasActivities) {
			hasActivities = true;
		}
	}
	%>

	<c:choose>
		<c:when test="<%= hasActivities %>">
			</ul>
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="there-are-no-recent-activities" />
		</c:otherwise>
	</c:choose>
</div>