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

<%@ include file="/html/taglib/ui/ratings/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:ratings:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ratings:classPK"));
int numberOfStars = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:ratings:numberOfStars"));
RatingsEntry ratingsEntry = (RatingsEntry)request.getAttribute("liferay-ui:ratings:ratingsEntry");
RatingsStats ratingsStats = (RatingsStats)request.getAttribute("liferay-ui:ratings:ratingsStats");
boolean round = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ratings:round"), true);
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
	url = themeDisplay.getPathMain() + "/portal/rate_entry";
}

double averageScore = 0.0;

if (ratingsStats != null) {
	averageScore = ratingsStats.getAverageScore();
}

int averageIndex = (int)Math.round(averageScore * numberOfStars);

if (!round) {
	averageIndex = (int)Math.floor(averageScore * numberOfStars);
}

double yourScore = -1.0;

if (ratingsEntry != null) {
	yourScore = ratingsEntry.getScore();
}
%>

<c:if test="<%= !themeDisplay.isFacebook() %>">
	<div class="taglib-ratings <%= type %>" id="<%= randomNamespace %>ratingContainer">
		<c:choose>
			<c:when test='<%= type.equals("stars") %>'>
				<c:if test="<%= themeDisplay.isSignedIn() && !TrashUtil.isInTrash(className, classPK) %>">
					<div class="liferay-rating-vote" id="<%= randomNamespace %>ratingStar">
						<div id="<%= randomNamespace %>ratingStarContent">
							<div class="rating-label"><liferay-ui:message key="your-rating" /></div>

							<liferay-util:whitespace-remover>

								<%
								double yourScoreStars = (yourScore != -1.0) ? yourScore * numberOfStars : 0.0;

								for (int i = 1; i <= numberOfStars; i++) {
									String ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");
								%>

									<a class="rating-element <%= (i <= yourScoreStars) ? "icon-star" : "icon-star-empty" %>" href="javascript:;"></a>

									<div class="rating-input-container">
										<label for="<%= ratingId %>"><liferay-ui:message arguments="<%= new Object[] {i, numberOfStars} %>" key='<%= (yourScoreStars == i) ? "you-have-rated-this-x-stars-out-of-x" : "rate-this-x-stars-out-of-x" %>' translateArguments="<%= false %>" /></label>

										<input checked="<%= i == yourScoreStars %>" class="rating-input" id="<%= ratingId %>" name="<portlet:namespace />rating" type="radio" value="<%= i %>">
									</div>

								<%
								}
								%>

							</liferay-util:whitespace-remover>
						</div>
					</div>
				</c:if>

				<div class="liferay-rating-score" id="<%= randomNamespace %>ratingScore">
					<div id="<%= randomNamespace %>ratingScoreContent">
						<div class="rating-label">
							<liferay-ui:message key="average" />

							(<%= ratingsStats.getTotalEntries() %> <liferay-ui:message key='<%= (ratingsStats.getTotalEntries() == 1) ? "vote" : "votes" %>' />)
						</div>

						<liferay-util:whitespace-remover>

							<%
							for (int i = 1; i <= numberOfStars; i++) {
							%>

								<span class="rating-element <%= (i <= averageIndex) ? "icon-star" : "icon-star-empty" %>" title="<%= TrashUtil.isInTrash(className, classPK) ? LanguageUtil.get(request, "ratings-are-disabled-because-this-entry-is-in-the-recycle-bin") : ((i == 1) ? LanguageUtil.format(request, "the-average-rating-is-x-stars-out-of-x", new Object[] {averageScore * numberOfStars, numberOfStars}, false) : StringPool.BLANK) %>"></span>

							<%
							}
							%>

						</liferay-util:whitespace-remover>
					</div>
				</div>
			</c:when>
			<c:when test='<%= type.equals("like") || type.equals("thumbs") %>'>

				<%
				String ratingIdPrefix = "ratingThumb";

				if (type.equals("like")) {
					ratingIdPrefix = "ratingLike";
				}
				%>

				<div class="liferay-rating-vote thumbrating" id="<%= randomNamespace + ratingIdPrefix %>">
					<div class="helper-clearfix rating-content thumbrating-content" id="<%= randomNamespace + ratingIdPrefix %>Content">
						<liferay-util:whitespace-remover>
							<div class="rating-label">
								<%= (averageScore > 0.5) ? "+" : StringPool.BLANK %><%= (int)(ratingsStats.getTotalScore() - (ratingsStats.getTotalEntries() - ratingsStats.getTotalScore())) %>

								<c:if test='<%= type.equals("thumbs") %>'>
									<%= StringPool.SPACE %>(<%= ratingsStats.getTotalEntries() %> <liferay-ui:message key='<%= (ratingsStats.getTotalEntries() == 1) ? "vote" : "votes" %>' />)
								</c:if>
							</div>

							<c:choose>
								<c:when test="<%= themeDisplay.isSignedIn() %>">
									<c:choose>
										<c:when test="<%= TrashUtil.isInTrash(className, classPK) %>">
											<span class="rating-element rating-thumb-up rating-<%= (yourScore > 0) ? "on" : "off" %>" title="<liferay-ui:message key="ratings-are-disabled-because-this-entry-is-in-the-recycle-bin" />"></span>

											<c:if test='<%= type.equals("thumbs") %>'>
												<span class="rating-element rating-thumb-down rating-<%= (yourScore == 0) ? "on" : "off" %>" title="<liferay-ui:message key="ratings-are-disabled-because-this-entry-is-in-the-recycle-bin" />"></span>
											</c:if>
										</c:when>
										<c:otherwise>
											<a class="icon-thumbs-up rating-element rating-thumb-up rating-<%= (yourScore > 0) ? "on" : "off" %>" href="javascript:;"></a>

											<c:if test='<%= type.equals("thumbs") %>'>
												<a class="icon-thumbs-down rating-element rating-thumb-down rating-<%= (yourScore == 0) ? "on" : "off" %>" href="javascript:;"></a>
											</c:if>

											<div class="rating-input-container">

												<%
												String ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");

												String positiveRatingMessage = null;

												if (type.equals("thumbs")) {
													positiveRatingMessage = (yourScore > 0) ? "you-have-rated-this-as-good" : "rate-this-as-good";
												}
												else {
													positiveRatingMessage = (yourScore > 0) ? "unlike-this" : "like-this";
												}
												%>

												<label for="<%= ratingId %>"><liferay-ui:message key="<%= positiveRatingMessage %>" /></label>

												<input class="rating-input" id="<%= ratingId %>" name="<portlet:namespace /><%= ratingIdPrefix %>" type="radio" value="up">

												<c:if test='<%= type.equals("thumbs") %>'>

													<%
													ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");
													%>

													<label for="<%= ratingId %>"><liferay-ui:message key='<%= (yourScore == 0) ? "you-have-rated-this-as-bad" : "rate-this-as-bad" %>' /></label>

													<input class="rating-input" id="<%= ratingId %>" name="<portlet:namespace /><%= ratingIdPrefix %>" type="radio" value="down">
												</c:if>
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
							</c:choose>
						</liferay-util:whitespace-remover>
					</div>
				</div>
			</c:when>
		</c:choose>
	</div>

	<c:if test="<%= !TrashUtil.isInTrash(className, classPK) %>">
		<aui:script use="liferay-ratings">
			Liferay.Ratings.register(
				{
					averageScore: <%= MathUtil.format(averageScore, 1, 1) %>,
					className: '<%= HtmlUtil.escapeJS(className) %>',
					classPK: '<%= classPK %>',
					containerId: '<%= randomNamespace %>ratingContainer',
					namespace: '<%= randomNamespace %>',
					round: <%= round %>,
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