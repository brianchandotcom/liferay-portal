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
CommerceStarter lotusCommerceStarter = (CommerceStarter)request.getAttribute("render.jsp-commerceStarter");
%>

<div class="row">
	<div class="col-md-6">
		<div class="carousel slide w100" data-interval="false" id="carousel-commerce-starter-lotus">
			<div class="carousel-inner" role="listbox">
				<div class="active carousel-item">
					<img src="/o/commerce-starter-lotus/images/carousel_01.png" />
				</div>

				<div class="carousel-item">
					<img src="/o/commerce-starter-lotus/images/carousel_01.png" />
				</div>

				<div class="carousel-item">
					<img src="/o/commerce-starter-lotus/images/carousel_01.png" />
				</div>
			</div>

			<a class="carousel-control carousel-control-left left" data-slide="prev" href="#carousel-commerce-starter-lotus" role="button">
				<liferay-ui:icon
					cssClass="commerce-wizard-icon-angle-left"
					icon="angle-left"
					markupView="lexicon"
				/>

				<span class="sr-only">Previous</span>
			</a>

			<a class="carousel-control carousel-control-right right" data-slide="next" href="#carousel-commerce-starter-lotus" role="button">
				<liferay-ui:icon
					cssClass="commerce-wizard-icon-angle-right"
					icon="angle-right"
					markupView="lexicon"
				/>

				<span class="sr-only">Next</span>
			</a>
		</div>
	</div>

	<div class="col-md-6">
		<aui:button name='<%= lotusCommerceStarter.getKey() + "ApplyButton" %>' primary="<%= true %>" value="apply" />
	</div>
</div>

<style>
	#carousel-commerce-starter-lotus {
		position: relative;
	}

	#carousel-commerce-starter-lotus .carousel-item > img {
		width: 100%;
	}

	#carousel-commerce-starter-lotus .carousel-control {
		color: #000000;
		position: absolute;
	}

	#carousel-commerce-starter-lotus .carousel-control-left {
		left: 5%;
		top: 50%;
	}

	#carousel-commerce-starter-lotus .carousel-control-right {
		right: 5%;
		top: 50%;
	}
</style>

<aui:script>
	var applyButton = AUI.$('#<%= renderResponse.getNamespace() + lotusCommerceStarter.getKey() + "ApplyButton" %>');

	applyButton.on(
		'click',
		function(event) {
			event.preventDefault();

			if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-continue-all-contents-will-be-deleted") %>')) {
				AUI.$('#<portlet:namespace />commerceStarterKey').val('<%= lotusCommerceStarter.getKey() %>');

				var form = AUI.$(document.<portlet:namespace />fm);

				submitForm(form);
			}
		}
	);
</aui:script>