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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_ratings_score_page") + StringPool.UNDERLINE;

double score = GetterUtil.getDouble((String)request.getAttribute("liferay-ui:ratings-score:score"));

NumberFormat numberFormat = NumberFormat.getInstance();

numberFormat.setMaximumFractionDigits(1);
numberFormat.setMinimumFractionDigits(0);

String scoreString = numberFormat.format(score);
%>

<c:choose>
	<c:when test="<%= themeDisplay.isFacebook() %>">
		<%= scoreString %> Stars
	</c:when>
	<c:otherwise>
		<div class="score taglib-ratings" id="<%= randomNamespace %>averageRating">
			<div class="helper-clearfix" id="<%= randomNamespace %>averageRatingContent">

				<%
				for (int i = 1; i <= 5; i++) {
				%>

					<a class="rating-element <%= (i <= score) ? "rating-element-on" : StringPool.BLANK %>" href="javascript:;"></a>

				<%
				}
				%>

			</div>
		</div>

		<aui:script use="aui-rating">
			var ratingScore = new A.Rating(
				{
					boundingBox: '#<%= randomNamespace %>averageRating',
					defaultSelected: <%= MathUtil.format(score, 1, 1) %>,
					disabled: true,
					srcNode: '#<%= randomNamespace %>averageRatingContent'
				}
			).render();

			ratingScore.get('boundingBox').on(
				'mouseenter',
				function(event) {
					var el = A.Node.getDOMNode(event.currentTarget);

					Liferay.Portal.ToolTip.show(el, '<%= scoreString %> Stars');
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>