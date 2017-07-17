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
CPContentDisplayContext cpContentDisplayContext = (CPContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
CPDefinition cpDefinition = cpContentDisplayContext.getCPDefinition();
%>

<c:if test="<%= cpDefinition != null %>">
	<div class="product-detail row-fluid">
		<div class="col-md-7">
			<div class="row-fluid">
				<div class="col-md-3">

					<%
						for (CPAttachmentFileEntry cpAttachmentFileEntry : cpContentDisplayContext.getImages()) {
						String url = cpContentDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay);
					%>

						<div class="row-fluid">
							<div class="col-md-12 thumb" data-url="<%= url %>">
								<img src="<%= url %>">
							</div>
						</div>
					<% } %>
				</div>

				<div class="col-md-9 full-image">

					<%
						CPAttachmentFileEntry cpAttachmentFileEntry = cpContentDisplayContext.getDefaultImage();
					%>

					<c:if test="<%= cpAttachmentFileEntry != null %>">
						<img id="full-image" src="<%= cpContentDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay) %>">
					</c:if>
				</div>
			</div>
		</div>

		<div class="col-md-5">
			<h1><%= cpDefinition.getTitle(languageId) %></h1>
			<h4>Code: <%= cpDefinition.getBaseSKU() %></h4>

			<div class="row-fluid">
				<div class="col-md-12">
					<div class="options">
						<%= cpContentDisplayContext.renderOptions(renderRequest, renderResponse) %>
					</div>
				</div>
			</div>

			<div class="row-fluid">
				<div class="col-md-3">
					<aui:input cssClass="quantity" label="quantity" name="qt"></aui:input>
				</div>

				<div class="col-md-9">
				</div>
			</div>

			<div class="row-fluid">
				<div class="col-md-12">

				</div>

				<div class="col-md-9">
				</div>
			</div>
		</div>
	</div>

	<script>

		$(document).ready(
			function() {

				$(".thumb").click(
					function() {
					$("#full-image").attr("src",$(this).attr("data-url"));
				});
			});

	</script>
</c:if>

<style>
	.product-detail .thumb{
		margin-bottom: 30px;
		border: 3px solid #fff;
		cursor: pointer;
	}

	.product-detail .thumb:hover{
		margin-bottom: 30px;
		border: 3px solid #C9C9C9;
	}

	.product-detail .thumb img{
		width: 100%;
	}

	.product-detail .full-image img{
		width: 80%;
	}

	.product-detail .full-image{
		text-align: center;
	}

</style>