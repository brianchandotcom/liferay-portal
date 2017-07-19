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
CPSimpleCPTypeDisplayContext cpSimpleCPTypeDisplayContext = (CPSimpleCPTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
CPDefinition cpDefinition = cpSimpleCPTypeDisplayContext.getCPDefinition();
%>

<c:if test="<%= cpDefinition != null %>">
	<div class="container-fluid product-detail">
		<div class="row">
			<div class="col-lg-6 col-md-7">
				<div class="row">
					<div class="col-lg-2 col-md-3 col-xs-2">

						<%
						for (CPAttachmentFileEntry cpAttachmentFileEntry : cpSimpleCPTypeDisplayContext.getImages()) {
							String url = cpSimpleCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay);
						%>

							<div class="card thumb" data-url="<%= url %>">
								<img class="center-block img-responsive" src="<%= url %>">
							</div>

						<%
						}
						%>

					</div>

					<div class="col-lg-10 col-md-9 col-xs-10 full-image">

						<%
						CPAttachmentFileEntry cpAttachmentFileEntry = cpSimpleCPTypeDisplayContext.getDefaultImage();
						%>

						<c:if test="<%= cpAttachmentFileEntry != null %>">
							<img class="center-block img-responsive" id="full-image" src="<%= cpSimpleCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay) %>">
						</c:if>
					</div>
				</div>
			</div>

			<div class="col-lg-6 col-md-5">
				<h1><%= cpDefinition.getTitle(languageId) %></h1>
				<h4>Code: <%= cpDefinition.getBaseSKU() %></h4>

				<div class="row">
					<div class="col-md-12">
						<div class="options">
							<%= cpSimpleCPTypeDisplayContext.renderOptions(renderRequest, renderResponse) %>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-3">
						<aui:input cssClass="quantity" label="quantity" name="qt"></aui:input>
					</div>

					<div class="col-md-9">
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
					</div>

					<div class="col-md-9">
					</div>
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