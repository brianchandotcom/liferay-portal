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

<%@ include file="/html/taglib/ui/ratings/init.jsp" %>

<%
String portletNamespace = portletDisplay.getNamespace();
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

boolean isInTrash = TrashUtil.isInTrash(className, classPK);

String isInTrashMessage = LanguageUtil.get(pageContext, "ratings-are-disabled-because-this-entry-is-in-the-recycle-bin");

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
	url = themeDisplay.getPathMain() + "/portal/rate_entry";
}

double yourScore = 0.0;

if (ratingsEntry != null) {
	yourScore = ratingsEntry.getScore();
}

double averageScore = ratingsStats.getAverageScore();

int totalEntries = ratingsStats.getTotalEntries();
%>

<c:if test="<%= !themeDisplay.isFacebook() %>">
	<div class="taglib-ratings <%= type %>" id="<%= randomNamespace %>ratingContainer">
		<c:choose>
			<c:when test='<%= type.equals("stars") %>'>
				<c:choose>
					<c:when test="<%= themeDisplay.isSignedIn() && !isInTrash %>">
						<div class="liferay-rating-vote" id="<%= randomNamespace %>ratingStar">
							<div id="<%= randomNamespace %>ratingStarContent">
								<div class="rating-label"><liferay-ui:message key="your-rating" /></div>

								<%
								StringBundler sb = new StringBundler(19 * numberOfStars);

								for (int i = 1; i <= numberOfStars; i++) {
									sb.append("<a class=\"rating-element ");

									String yourStarCssClass = "icon-star-empty";

									if (i <= yourScore) {
										yourStarCssClass = "icon-star";
									}

									sb.append(yourStarCssClass);
									sb.append("\" href=\"javascript:;\"></a>");
									sb.append("<div class=\"rating-input-container\">");
									sb.append("<label for=\"");

									String ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");

									sb.append(ratingId);
									sb.append("\">");

									String label = LanguageUtil.format(pageContext, (yourScore == i) ? "you-have-rated-this-x-stars-out-of-x" : "rate-this-x-stars-out-of-x", new Object[] {i, numberOfStars}, false);

									sb.append(label);

									sb.append("</label>");
									sb.append("<input checked=\"");
									sb.append(i == yourScore);
									sb.append("\" class=\"rating-input\" id=\"");
									sb.append(ratingId);
									sb.append("\" name=\"");
									sb.append(portletNamespace);
									sb.append("rating\" type=\"radio\" value=\"");
									sb.append(i);
									sb.append("\">");
									sb.append("</div>");
								}

								String starsRating = sb.toString();
								%>

								<%= starsRating %>
							</div>
						</div>
					</c:when>
				</c:choose>

				<div class="liferay-rating-score" id="<%= randomNamespace %>ratingScore">
					<div id="<%= randomNamespace %>ratingScoreContent">
						<div class="rating-label">
							<liferay-ui:message key="average" />

							(<%= totalEntries %> <liferay-ui:message key='<%= (totalEntries == 1) ? "vote" : "votes" %>' />)
						</div>

						<%
						StringBundler sb = new StringBundler(5 * numberOfStars);

						for (int i = 1; i <= numberOfStars; i++) {
							sb.append("<span class=\"rating-element ");

							String averageStarCssClass = "icon-star-empty";

							if (i <= averageScore) {
								averageStarCssClass = "icon-star";
							}

							sb.append(averageStarCssClass);
							sb.append("\" title=\"");

							String title = StringPool.BLANK;

							if (isInTrash) {
								title = isInTrashMessage;
							}
							else if (i == 1) {
								title = LanguageUtil.format(pageContext, "the-average-rating-is-x-stars-out-of-x", new Object[] {averageScore, numberOfStars}, false);
							}

							sb.append(title);
							sb.append("\"></span>");
						}

						String averageStarsRating = sb.toString();
						%>

						<%= averageStarsRating %>
					</div>
				</div>
			</c:when>
			<c:when test='<%= type.equals("thumbs") %>'>
				<c:choose>
					<c:when test="<%= themeDisplay.isSignedIn() %>">
						<div class="thumbrating liferay-rating-vote" id="<%= randomNamespace %>ratingThumb">
							<div class="helper-clearfix rating-content thumbrating-content" id="<%= randomNamespace %>ratingThumbContent">

								<%
								StringBundler sb = new StringBundler();

								sb.append("<div class=\"rating-label\">");

								String totalScore = "0";

								if (averageScore * totalEntries != 0) {
									if (averageScore > 0) {
										totalScore = "+";
									}
									else {
										totalScore = StringPool.BLANK;
									}

									totalScore += (int)(averageScore * totalEntries);
								}

								sb.append(totalScore);
								sb.append(" (");
								sb.append(totalEntries);
								sb.append(StringPool.SPACE);

								String ratingLabel = LanguageUtil.get(pageContext, "votes");

								if (totalEntries == 1) {
									ratingLabel = LanguageUtil.get(pageContext, "vote");
								}

								sb.append(ratingLabel);
								sb.append(")");
								sb.append("</div>");

								if (isInTrash) {
									sb.append("<span class=\"rating-element rating-");
								}
								else {
									sb.append("<a class=\"rating-element rating-");
								}

								String thumbsUpCssClass = "rating-element rating-";

								if (yourScore > 0) {
									thumbsUpCssClass += "on";
								}
								else {
									thumbsUpCssClass += "off";
								}

								thumbsUpCssClass += " rating-thumb-up icon-thumbs-up";

								sb.append(thumbsUpCssClass);

								if (isInTrash) {
									sb.append("\" title=\"");
									sb.append(isInTrashMessage);
									sb.append("\"></span>");
								}
								else {
									sb.append("\" href=\"javascript:;\"></a>");
								}

								if (isInTrash) {
									sb.append("<span class=\"rating-element rating-");
								}
								else {
									sb.append("<a class=\"rating-element rating-");
								}

								String thumbsDownCssClass = "rating-element rating-";

								if (yourScore < 0) {
									thumbsDownCssClass += "on";
								}
								else {
									thumbsDownCssClass += "off";
								}

								thumbsDownCssClass += " rating-thumb-down icon-thumbs-down";

								sb.append(thumbsDownCssClass);

								if (isInTrash) {
									sb.append("\" title=\"");
									sb.append(isInTrashMessage);
									sb.append("\"></span>");
								}
								else {
									sb.append("\" href=\"javascript:;\"></a>");
								}

								if (!isInTrash) {
									sb.append("<div class=\"rating-input-container\">");
									sb.append("<label for=\"");

									String ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");

									sb.append(ratingId);
									sb.append("\">");

									String goodRatingLabel = LanguageUtil.get(pageContext, "rate-this-as-good");

									if (yourScore > 0) {
										goodRatingLabel = LanguageUtil.get(pageContext, "you-have-rated-this-as-good");
									}

									sb.append(goodRatingLabel);
									sb.append("</label>");
									sb.append("<input class=\"rating-input\" id=\"");
									sb.append(ratingId);
									sb.append("\" name=\"");
									sb.append(portletNamespace);
									sb.append("ratingThumb\" type=\"radio\" value=\"up\">");
									sb.append("<label for=\"");

									ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");

									sb.append(ratingId);
									sb.append("\">");

									String badRatingLabel = LanguageUtil.get(pageContext, "rate-this-as-bad");

									if (yourScore > 0) {
										badRatingLabel = LanguageUtil.get(pageContext, "you-have-rated-this-as-bad");
									}

									sb.append(badRatingLabel);
									sb.append("</label>");
									sb.append("<input class=\"rating-input\" id=\"");
									sb.append(ratingId);
									sb.append("\" name=\"");
									sb.append(portletNamespace);
									sb.append("ratingThumb\" type=\"radio\" value=\"down\">");
									sb.append("</div>");
								}

								String thumbRating = sb.toString();
								%>

								<%= thumbRating %>
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

	<c:if test="<%= !isInTrash %>">
		<aui:script use="liferay-ratings">
			Liferay.Ratings.register(
				{
					averageScore: <%= averageScore %>,
					className: '<%= HtmlUtil.escapeJS(className) %>',
					classPK: '<%= classPK %>',
					containerId: '<%= randomNamespace %>ratingContainer',
					namespace: '<%= randomNamespace %>',
					size: <%= numberOfStars %>,
					totalEntries: <%= totalEntries %>,
					totalScore: <%= ratingsStats.getTotalScore() %>,
					type: '<%= type %>',
					uri: '<%= url %>',
					yourScore: <%= yourScore %>
				}
			);
		</aui:script>
	</c:if>
</c:if>