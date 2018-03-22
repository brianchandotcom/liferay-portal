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
ServletContext servletContext = (ServletContext)request.getAttribute("render.jsp-servletContext");
%>

<div class="commerce-starter-content row">
	<div class="carousel slide w100" data-interval="false" id="carousel-commerce-starter-lotus">
		<div class="carousel-inner" role="listbox">
			<div class="active carousel-item">
				<img src="<%= servletContext.getContextPath() %>/images/carousel_01.jpg" />
			</div>

			<div class="carousel-item">
				<img src="<%= servletContext.getContextPath() %>/images/carousel_02.jpg" />
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

	<div class="row" style="margin-top:50px;">
		<div class="col-md-6">
			<h3>Key Features Include</h3>

			<p>Vitae elit vel eros rhoncus auctor hac habitasse platea dictumst. Proin lobortis dia Nunc quis neque semper, mollis eros et Suspendisse ac laoreet orci, eu dapiho ncus auctor hac habitasse platea dictumst. Proin lobortis dia Nunc quis</p>
		</div>

		<div class="col-md-6">
			<p>
				Morbi vitae elit vel eros rhoncus auctor
				In hac habitasse platea dictumst. Proin lobortis dia
				Nunc quis neque semper, mollis eros et
				Suspendisse ac laoreet orci, eu dapibus metus. Etiam nec lobortis urna. Integer rhoncu
				Proin lobortis diam et consequat dignissim
			</p>
		</div>
	</div>
</div>

<style>
	#carousel-commerce-starter-lotus {
		position: relative;
	}

	#carousel-commerce-starter-lotus .carousel-item > img {
		border-radius: 4px;
		width: 100%;
	}

	#carousel-commerce-starter-lotus .carousel-control {
		background-color: #303140;
		padding: 6px 10px;
		border-radius: 50%;
		color: #FFF;
		opacity: 0.7;
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

	.commerce-starter-content {
		padding: 0 35px;
	}
</style>