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
Boolean inTrash = (Boolean)request.getAttribute("liferay-ui:ratings:inTrash");
int numberOfStars = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:ratings:numberOfStars"));
RatingsEntry ratingsEntry = (RatingsEntry)request.getAttribute("liferay-ui:ratings:ratingsEntry");
RatingsStats ratingsStats = (RatingsStats)request.getAttribute("liferay-ui:ratings:ratingsStats");
boolean round = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ratings:round"), true);
boolean setRatingsEntry = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ratings:setRatingsEntry"));
boolean setRatingsStats = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ratings:setRatingsStats"));
String type = GetterUtil.getString((String)request.getAttribute("liferay-ui:ratings:type"));
String url = (String)request.getAttribute("liferay-ui:ratings:url");

if (inTrash == null) {
	inTrash = TrashUtil.isInTrash(className, classPK);
}

if (numberOfStars < 1) {
	numberOfStars = 1;
}

if (!setRatingsStats) {
	ratingsStats = RatingsStatsLocalServiceUtil.fetchStats(className, classPK);
}

if (!setRatingsEntry && (ratingsStats != null)) {
	ratingsEntry = RatingsEntryLocalServiceUtil.fetchEntry(themeDisplay.getUserId(), className, classPK);
}

if (Validator.isNull(url)) {
	url = themeDisplay.getPathMain() + "/portal/rate_entry";
}

double averageScore = 0.0;
int totalEntries = 0;
double totalScore = 0.0;

if (ratingsStats != null) {
	averageScore = ratingsStats.getAverageScore() * numberOfStars;
	totalEntries = ratingsStats.getTotalEntries();
	totalScore = ratingsStats.getTotalScore();
}

double formattedAverageScore = MathUtil.format(averageScore, 1, 1);

int averageIndex = (int)Math.round(formattedAverageScore);

if (!round) {
	averageIndex = (int)Math.floor(formattedAverageScore);
}

double yourScore = -1.0;

if (ratingsEntry != null) {
	yourScore = ratingsEntry.getScore();
}

boolean enabled = false;

if (!inTrash) {
	Group group = themeDisplay.getSiteGroup();

	if (!group.isStagingGroup() && !group.isStagedRemotely()) {
		enabled = true;
	}
}
%>

<div class="taglib-ratings <%= type %>" id="<%= randomNamespace %>ratingContainer">
	<c:choose>
		<c:when test="<%= type.equals(RatingsType.STACKED_STARS.getValue()) %>">
			<div class="liferay-rating-score" id="<%= randomNamespace %>ratingScore">
				<div id="<%= randomNamespace %>ratingScoreContent">
					<liferay-util:whitespace-remover>

						<%
						for (int i = 1; i <= numberOfStars; i++) {
							String message = StringPool.BLANK;

							if (inTrash) {
								message = LanguageUtil.get(resourceBundle, "ratings-are-disabled-because-this-entry-is-in-the-recycle-bin");
							}
							else if (!enabled) {
								message = LanguageUtil.get(resourceBundle, "ratings-are-disabled-in-staging");
							}
							else if (i == 1) {
								message = LanguageUtil.format(request, ((formattedAverageScore == 1.0) ? "the-average-rating-is-x-star-out-of-x" : "the-average-rating-is-x-stars-out-of-x"), new Object[] {formattedAverageScore, numberOfStars}, false);
							}
						%>

							<span class="rating-element <%= (i <= averageIndex) ? "icon-star-on" : "icon-star-off" %>" title="<%= message %>">
								<svg aria-hidden="true" class="lexicon-icon lexicon-icon-star" role="img">
									<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#star" />
								</svg>

								<svg aria-hidden="true" class="lexicon-icon lexicon-icon-star-o" role="img">
									<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#star-o" />
								</svg>
							</span>

						<%
						}
						%>

					</liferay-util:whitespace-remover>

					<div class="rating-label">
						(<%= totalEntries %> <liferay-ui:message key='<%= (totalEntries == 1) ? "vote" : "votes" %>' />)
					</div>
				</div>
			</div>

			<c:if test="<%= themeDisplay.isSignedIn() && enabled %>">
				<div class="liferay-rating-vote" id="<%= randomNamespace %>ratingStar">
					<div id="<%= randomNamespace %>ratingStarContent">
						<liferay-util:whitespace-remover>

							<%
							double yourScoreStars = (yourScore != -1.0) ? yourScore * numberOfStars : 0.0;

							for (int i = 1; i <= numberOfStars; i++) {
								String ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");
							%>

								<a class="btn btn-unstyled rating-element <%= (i <= yourScoreStars) ? "icon-star-on" : "icon-star-off" %>" href="javascript:;">
									<svg aria-hidden="true" class="lexicon-icon lexicon-icon-star" role="img">
										<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#star" />
									</svg>

									<svg aria-hidden="true" class="lexicon-icon lexicon-icon-star-o" role="img">
										<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#star-o" />
									</svg>
								</a>

								<div class="rating-input-container">
									<label for="<%= ratingId %>"><liferay-ui:message arguments="<%= new Object[] {i, numberOfStars} %>" key='<%= (yourScoreStars == i) ? ((i == 1) ? "you-have-rated-this-x-star-out-of-x" : "you-have-rated-this-x-stars-out-of-x") : ((i == 1) ? "rate-this-x-star-out-of-x" : "rate-this-x-stars-out-of-x") %>' translateArguments="<%= false %>" /></label>

									<input checked="<%= i == yourScoreStars %>" class="rating-input" id="<%= ratingId %>" name="<portlet:namespace />rating" type="radio" value="<%= i %>" />
								</div>

							<%
							}
							%>

						</liferay-util:whitespace-remover>
					</div>
				</div>
			</c:if>
		</c:when>
		<c:when test="<%= type.equals(RatingsType.STARS.getValue()) %>">
			<c:if test="<%= themeDisplay.isSignedIn() && enabled %>">
				<div class="liferay-rating-vote" id="<%= randomNamespace %>ratingStar">
					<div id="<%= randomNamespace %>ratingStarContent">
						<div class="rating-label"><liferay-ui:message key="your-rating" /></div>

						<liferay-util:whitespace-remover>

							<%
							double yourScoreStars = (yourScore != -1.0) ? yourScore * numberOfStars : 0.0;

							for (int i = 1; i <= numberOfStars; i++) {
								String ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");
							%>

								<a class="btn btn-unstyled rating-element <%= (i <= yourScoreStars) ? "icon-star-on" : "icon-star-off" %>" href="javascript:;">
									<svg aria-hidden="true" class="lexicon-icon lexicon-icon-star" role="img">
										<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#star" />
									</svg>

									<svg aria-hidden="true" class="lexicon-icon lexicon-icon-star-o" role="img">
										<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#star-o" />
									</svg>
								</a>

								<div class="rating-input-container">
									<label for="<%= ratingId %>"><liferay-ui:message arguments="<%= new Object[] {i, numberOfStars} %>" key='<%= (yourScoreStars == i) ? ((i == 1) ? "you-have-rated-this-x-star-out-of-x" : "you-have-rated-this-x-stars-out-of-x") : ((i == 1) ? "rate-this-x-star-out-of-x" : "rate-this-x-stars-out-of-x") %>' translateArguments="<%= false %>" /></label>

									<input checked="<%= i == yourScoreStars %>" class="rating-input" id="<%= ratingId %>" name="<portlet:namespace />rating" type="radio" value="<%= i %>" />
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

						(<%= totalEntries %> <liferay-ui:message key='<%= (totalEntries == 1) ? "vote" : "votes" %>' />)
					</div>

					<liferay-util:whitespace-remover>

						<%
						for (int i = 1; i <= numberOfStars; i++) {
							String message = StringPool.BLANK;

							if (inTrash) {
								message = LanguageUtil.get(resourceBundle, "ratings-are-disabled-because-this-entry-is-in-the-recycle-bin");
							}
							else if (!enabled) {
								message = LanguageUtil.get(resourceBundle, "ratings-are-disabled-in-staging");
							}
							else if (i == 1) {
								message = LanguageUtil.format(request, ((formattedAverageScore == 1.0) ? "the-average-rating-is-x-star-out-of-x" : "the-average-rating-is-x-stars-out-of-x"), new Object[] {formattedAverageScore, numberOfStars}, false);
							}
						%>

							<span class="rating-element <%= (i <= averageIndex) ? "icon-star-on" : "icon-star-off" %>" title="<%= message %>">
								<svg aria-hidden="true" class="lexicon-icon lexicon-icon-star" role="img">
									<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#star" />
								</svg>

								<svg aria-hidden="true" class="lexicon-icon lexicon-icon-star-o" role="img">
									<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#star-o" />
								</svg>
							</span>

						<%
						}
						%>

					</liferay-util:whitespace-remover>
				</div>
			</div>
		</c:when>
		<c:when test="<%= type.equals(RatingsType.LIKE.getValue()) || type.equals(RatingsType.THUMBS.getValue()) %>">

			<%
			String ratingIdPrefix = "ratingThumb";

			if (type.equals(RatingsType.LIKE.getValue())) {
				ratingIdPrefix = "ratingLike";
			}
			%>

			<div class="liferay-rating-vote thumbrating" id="<%= randomNamespace + ratingIdPrefix %>">
				<div class="helper-clearfix rating-content thumbrating-content" id="<%= randomNamespace + ratingIdPrefix %>Content">
					<liferay-util:whitespace-remover>

						<%
						int positiveVotes = (int)Math.round(totalScore);

						int negativeVotes = totalEntries - positiveVotes;

						boolean thumbUp = (yourScore != -1.0) && (yourScore >= 0.5);
						boolean thumbDown = (yourScore != -1.0) && (yourScore < 0.5);
						%>

						<c:choose>
							<c:when test="<%= !themeDisplay.isSignedIn() || !enabled %>">

								<%
								String thumbsTitle = StringPool.BLANK;

								if (inTrash) {
									thumbsTitle = LanguageUtil.get(resourceBundle, "ratings-are-disabled-because-this-entry-is-in-the-recycle-bin");
								}
								else if (!enabled) {
									thumbsTitle = LanguageUtil.get(resourceBundle, "ratings-are-disabled-in-staging");
								}
								%>

								<span class="btn-sm rating-element rating-thumb-up rating-<%= thumbUp ? "on" : "off" %>" title="<%= thumbsTitle %>">
									<span class="inline-item inline-item-before">
										<svg aria-hidden="true" class="lexicon-icon lexicon-icon-thumbs-up" role="img">
											<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#thumbs-up" />
										</svg>
									</span>

									<%= positiveVotes %>
								</span>

								<c:if test="<%= type.equals(RatingsType.THUMBS.getValue()) %>">
									<span class="btn-sm rating-element rating-thumb-down rating-<%= thumbDown ? "on" : "off" %>" title="<%= thumbsTitle %>">
										<span class="inline-item inline-item-before">
											<svg aria-hidden="true" class="lexicon-icon lexicon-icon-thumbs-down" role="img">
												<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#thumbs-down" />
											</svg>
										</span>

										<%= negativeVotes %>
									</span>
								</c:if>
							</c:when>
							<c:otherwise>

								<%
								String positiveRatingMessage = null;

								if (type.equals(RatingsType.THUMBS.getValue())) {
									positiveRatingMessage = (thumbUp) ? "you-have-rated-this-as-good" : "rate-this-as-good";
								}
								else {
									positiveRatingMessage = (thumbUp) ? "unlike-this" : "like-this";
								}
								%>

								<a class="btn btn-outline-borderless btn-outline-secondary btn-sm rating-element rating-thumb-up rating-<%= thumbUp ? "on" : "off" %>" href="javascript:;" title="<liferay-ui:message key="<%= positiveRatingMessage %>" />">
									<span class="inline-item inline-item-before">
										<svg aria-hidden="true" class="lexicon-icon lexicon-icon-thumbs-up" role="img">
											<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#thumbs-up" />
										</svg>
									</span>
									<span class="votes"><%= positiveVotes %></span>
								</a>

								<c:if test="<%= type.equals(RatingsType.THUMBS.getValue()) %>">
									<a class="btn btn-outline-borderless btn-outline-secondary btn-sm rating-element rating-thumb-down rating-<%= thumbDown ? "on" : "off" %>" href="javascript:;" title="<liferay-ui:message key='<%= thumbDown ? "you-have-rated-this-as-bad" : "rate-this-as-bad" %>' />">
										<span class="inline-item inline-item-before">
											<svg aria-hidden="true" class="lexicon-icon lexicon-icon-thumbs-down" role="img">
												<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#thumbs-down" />
											</svg>
										</span>
										<span class="votes"><%= negativeVotes %></span>
									</a>
								</c:if>

								<div class="rating-input-container">

									<%
									String ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");
									%>

									<input class="rating-input" id="<%= ratingId %>" name="<portlet:namespace /><%= ratingIdPrefix %>" type="radio" value="up" />

									<c:if test="<%= type.equals(RatingsType.THUMBS.getValue()) %>">

										<%
										ratingId = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_page_rating");
										%>

										<input class="rating-input" id="<%= ratingId %>" name="<portlet:namespace /><%= ratingIdPrefix %>" type="radio" value="down" />
									</c:if>
								</div>
							</c:otherwise>
						</c:choose>
					</liferay-util:whitespace-remover>
				</div>
			</div>
		</c:when>
	</c:choose>
</div>

<c:if test="<%= enabled %>">
	<aui:script position="inline" use="liferay-ratings">
		Liferay.Ratings.register(
			{
				averageScore: <%= formattedAverageScore %>,
				className: '<%= HtmlUtil.escapeJS(className) %>',
				classPK: '<%= classPK %>',
				containerId: '<%= randomNamespace %>ratingContainer',
				namespace: '<%= randomNamespace %>',
				round: <%= round %>,
				size: <%= numberOfStars %>,
				totalEntries: <%= totalEntries %>,
				totalScore: <%= totalScore %>,
				type: '<%= type %>',
				uri: '<%= url %>',
				yourScore: <%= yourScore %>
			}
		);
	</aui:script>
</c:if>