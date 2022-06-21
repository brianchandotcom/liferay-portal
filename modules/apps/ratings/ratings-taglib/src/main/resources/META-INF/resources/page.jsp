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

<%
String type = GetterUtil.getString((String)request.getAttribute("liferay-ratings:ratings:type"));
%>

<liferay-util:html-top
	outputKey="com.liferay.ratings.taglib.servlet.taglib#/page.jsp"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="ratings">
	<c:choose>
		<c:when test="<%= type.equals(RatingsType.LIKE.getValue()) %>">
			<div class="ratings-like">
				<clay:button
					borderless="<%= true %>"
					disabled="<%= true %>"
					displayType="secondary"
					small="<%= true %>"
				>
					<clay:icon
						symbol="heart"
					/>
				</clay:button>
			</div>
		</c:when>
		<c:when test="<%= type.equals(RatingsType.THUMBS.getValue()) %>">
			<div class="ratings-thumbs">
				<clay:button
					borderless="<%= true %>"
					disabled="<%= true %>"
					displayType="secondary"
					small="<%= true %>"
				>
					<clay:icon
						symbol="thumbs-up"
					/>
				</clay:button>

				<clay:button
					borderless="<%= true %>"
					disabled="<%= true %>"
					displayType="secondary"
					icon="thumbs-down"
					small="<%= true %>"
				>
					<clay:icon
						symbol="thumbs-down"
					/>
				</clay:button>
			</div>
		</c:when>
		<c:when test="<%= type.equals(RatingsType.STARS.getValue()) %>">
			<clay:content-row
				cssClass="ratings-stars"
				verticalAlign="center"
			>
				<clay:content-col>
					<div class="dropdown">
						<clay:button
							borderless="<%= true %>"
							cssClass="dropdown-toggle"
							disabled="<%= true %>"
							displayType="secondary"
							small="<%= true %>"
						>
							<span class="inline-item inline-item-before">
								<clay:icon
									symbol="star-o"
								/>
							</span>
							<span class="inline-item ratings-stars-button-text">-</span>
						</clay:button>
					</div>
				</clay:content-col>

				<clay:content-col>
					<clay:icon
						cssClass="ratings-stars-average-icon"
						symbol="star"
					/>
				</clay:content-col>
			</clay:content-row>
		</c:when>
		<c:when test="<%= type.equals(RatingsType.STACKED_STARS.getValue()) %>">
			<div class="ratings-stacked-stars ratings-stars">
				<div class="disabled ratings-stars-average">
					<span class="inline-item inline-item-before">
						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>

						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>

						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>

						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>

						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>
					</span>
				</div>

				<div class="disabled ratings-stars-average">
					<span class="inline-item inline-item-before">
						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>

						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>

						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>

						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>

						<clay:icon
							cssClass="ratings-stars-average-icon"
							symbol="star"
						/>
					</span>
				</div>
			</div>
		</c:when>
	</c:choose>

	<react:component
		module="js/Ratings"
		props='<%= (Map<String, Object>)request.getAttribute("liferay-ratings:ratings:data") %>'
	/>
</div>