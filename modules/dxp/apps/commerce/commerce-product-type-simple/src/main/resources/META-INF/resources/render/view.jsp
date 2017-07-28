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
CPTypeDisplayContext cpTypeDisplayContext = (CPTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("simpleCPTypeDisplayContext", cpTypeDisplayContext);

CPDefinition cpDefinition = cpTypeDisplayContext.getCPDefinition();
%>

<liferay-ddm:template-renderer
	className="<%= SimpleCPType.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= cpTypeDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpTypeDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= Collections.singletonList(cpDefinition) %>"
>
	<div class="container-fluid product-detail" data-cp-definition-id="<%= cpDefinition.getCPDefinitionId() %>">
		<div class="row">
			<div class="col-lg-6 col-md-7">
				<div class="row">
					<div class="col-lg-2 col-md-3 col-xs-2">

						<%
						for (CPAttachmentFileEntry cpAttachmentFileEntry : cpTypeDisplayContext.getImages()) {
							String url = cpTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay);
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
						CPAttachmentFileEntry cpAttachmentFileEntry = cpTypeDisplayContext.getDefaultImage();
						%>

						<c:if test="<%= cpAttachmentFileEntry != null %>">
							<img class="center-block img-responsive" id="full-image" src="<%= cpTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay) %>">
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
							<%= cpTypeDisplayContext.renderOptions(renderRequest, renderResponse) %>

							<aui:script>
								Liferay.on(
									'<%= cpTypeDisplayContext.getCPDefinitionId() %>DDMForm:render',
									function(event) {
										var form = event.form;

										form.after(
											"*:valueChange",
											function(valueChangeEvent) {
												console.log("test", valueChangeEvent);
											}
										);
									}
								);
							</aui:script>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_cart#" />
					</div>
				</div>
			</div>
		</div>
	</div>

	<aui:script>
		$(document).ready(
			function() {
				$(".thumb").click(
					function() {
						$("#full-image").attr("src",$(this).attr("data-url"));
				});
			});
	</aui:script>
</liferay-ddm:template-renderer>