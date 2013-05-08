<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.ratings.model.RatingsEntry" %>
<%@ page import="com.liferay.portlet.ratings.model.RatingsStats" %>
<%@ page import="com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:ratings:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ratings:classPK"));
int numberOfStars = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:ratings:numberOfStars"));
RatingsEntry ratingsEntry = (RatingsEntry)request.getAttribute("liferay-ui:ratings:ratingsEntry");
RatingsStats ratingsStats = (RatingsStats)request.getAttribute("liferay-ui:ratings:ratingsStats");
boolean setRatingsEntry = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ratings:setRatingsEntry"));
boolean setRatingsStats = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ratings:setRatingsStats"));
String type = GetterUtil.getString((String)request.getAttribute("liferay-ui:ratings:type"));
String url = (String)request.getAttribute("liferay-ui:ratings:url");

if (numberOfStars < 1) {
	numberOfStars = 1;
}

if (!setRatingsEntry) {
	ratingsEntry = RatingsEntryLocalServiceUtil.fetchEntry(themeDisplay.getUserId(), className, classPK);
}

if (!setRatingsStats) {
	ratingsStats = RatingsStatsLocalServiceUtil.getStats(className, classPK);
}

if (Validator.isNull(url)) {
	url = themeDisplay.getPathMain() + "/ratings/rate_entry";
}

double yourScore = 0.0;

if (ratingsEntry != null) {
	yourScore = ratingsEntry.getScore();
}
%>

<c:if test="<%= !themeDisplay.isFacebook() %>">
	<div class="taglib-ratings <%= type %>" id="<%= randomNamespace %>ratingContainer">
		<c:choose>
			<c:when test='<%= type.equals("stars") %>'>
				<c:choose>
					<c:when test="<%= themeDisplay.isSignedIn() && !TrashUtil.isInTrash(className, classPK) %>">
						<div class="liferay-rating-vote" id="<%= randomNamespace %>ratingStar">
							<div id="<%= randomNamespace %>ratingStarContent">
								<div class="rating-label-element"><liferay-ui:message key="your-rating" /></div>

								<%
								for (int i = 1; i <= numberOfStars; i++) {
								%>

									<a class="rating-element <%= (i <= yourScore) ? "rating-element-on" : StringPool.BLANK %>" href="javascript:;"></a>

									<aui:input checked="<%= i == yourScore %>" label='<%= (yourScore == i) ? LanguageUtil.format(pageContext, "you-have-rated-this-x-stars-out-of-x", new Object[] {i, numberOfStars}) : LanguageUtil.format(pageContext, "rate-this-x-stars-out-of-x", new Object[] {i, numberOfStars}) %>' name="rating" type="radio" value="<%= i %>" />

								<%
								}
								%>

							</div>
						</div>
					</c:when>
				</c:choose>

				<div class="liferay-rating-score" id="<%= randomNamespace %>ratingScore">
					<div id="<%= randomNamespace %>ratingScoreContent">
						<div class="rating-label-element">
							<liferay-ui:message key="average" />

							(<%= ratingsStats.getTotalEntries() %> <%= LanguageUtil.get(pageContext, (ratingsStats.getTotalEntries() == 1) ? "vote" : "votes") %>)
						</div>

						<%
						for (int i = 1; i <= numberOfStars; i++) {
						%>

							<img alt="<%= (i == 1) ? LanguageUtil.format(pageContext, "the-average-rating-is-x-stars-out-of-x", new Object[] {ratingsStats.getAverageScore(), numberOfStars}) : StringPool.BLANK %>" class="rating-element <%= (i <= ratingsStats.getAverageScore()) ? "rating-element-on" : StringPool.BLANK %>" src="<%= themeDisplay.getPathThemeImages() %>/spacer.png" title="<%= TrashUtil.isInTrash(className, classPK) ? LanguageUtil.get(pageContext, "ratings-are-disabled-because-this-entry-is-in-the-recycle-bin") : StringPool.BLANK %>" />

						<%
						}
						%>

					</div>
				</div>
			</c:when>
			<c:when test='<%= type.equals("thumbs") %>'>
				<c:choose>
					<c:when test="<%= themeDisplay.isSignedIn() %>">
						<div class="thumbrating liferay-rating-vote" id="<%= randomNamespace %>ratingThumb">
							<div class="helper-clearfix rating-content thumbrating-content" id="<%= randomNamespace %>ratingThumbContent">
								<div class="rating-label-element">
									<c:choose>
										<c:when test="<%= (ratingsStats.getAverageScore() * ratingsStats.getTotalEntries() == 0) %>">
											0
										</c:when>
										<c:otherwise>
											<%= (ratingsStats.getAverageScore() > 0) ? "+" : StringPool.BLANK %><%= (int)(ratingsStats.getAverageScore() * ratingsStats.getTotalEntries()) %>
										</c:otherwise>
									</c:choose>

									(<%= ratingsStats.getTotalEntries() %> <%= LanguageUtil.get(pageContext, (ratingsStats.getTotalEntries() == 1) ? "vote" : "votes") %>)
								</div>

								<c:choose>
									<c:when test="<%= TrashUtil.isInTrash(className, classPK) %>">
										<span class="rating-element rating-element-<%= (yourScore > 0) ? "on" : "off" %> rating-thumb-up" title="<liferay-ui:message key="ratings-are-disabled-because-this-entry-is-in-the-recycle-bin" />"></span>

										<span class="rating-element rating-element-<%= (yourScore < 0) ? "on" : "off" %> rating-thumb-down" title="<liferay-ui:message key="ratings-are-disabled-because-this-entry-is-in-the-recycle-bin" />"></span>
									</c:when>
									<c:otherwise>
										<a class="rating-element rating-element-<%= (yourScore > 0) ? "on" : "off" %> rating-thumb-up" href="javascript:;"></a>

										<a class="rating-element rating-element-<%= (yourScore < 0) ? "on" : "off" %> rating-thumb-down" href="javascript:;"></a>

										<aui:input label='<%= (yourScore > 0) ? "you-have-rated-this-as-good" : "rate-this-as-good" %>' name="ratingThumb" type="radio" value="up" />

										<aui:input label='<%= (yourScore < 0) ? "you-have-rated-this-as-bad" : "rate-this-as-bad" %>' name="ratingThumb" type="radio" value="down" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<a href="<%= themeDisplay.getURLSignIn() %>"><liferay-ui:message key="sign-in-to-vote" /></a>
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
	</div>

	<c:if test="<%= !TrashUtil.isInTrash(className, classPK) %>">
		<aui:script use="liferay-ratings">
			Liferay.Ratings.register(
				{
					averageScore: <%= ratingsStats.getAverageScore() %>,
					className: '<%= className %>',
					classPK: '<%= classPK %>',
					containerId: '<%= randomNamespace %>ratingContainer',
					namespace: '<%= randomNamespace %>',
					size: <%= numberOfStars %>,
					totalEntries: <%= ratingsStats.getTotalEntries() %>,
					totalScore: <%= ratingsStats.getTotalScore() %>,
					type: '<%= type %>',
					uri: '<%= url %>',
					yourScore: <%= yourScore %>
				}
			);
		</aui:script>
	</c:if>
</c:if>