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
GroupedCPTypeDisplayContext groupedCPTypeDisplayContext = (GroupedCPTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("groupedCPTypeDisplayContext", groupedCPTypeDisplayContext);

CPDefinition cpDefinition = groupedCPTypeDisplayContext.getCPDefinition();

List<CPDefinition> entries = new ArrayList<>();

entries.add(cpDefinition);
%>

<liferay-ddm:template-renderer
	className="<%= GroupedCPType.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= groupedCPTypeDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= groupedCPTypeDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= entries %>"
>
	<div class="container-fluid product-detail">
		<div class="row">
			<div class="col-lg-6 col-md-7">
				<div class="row">
					<div class="col-lg-2 col-md-3 col-xs-2">

						<%
						for (CPAttachmentFileEntry cpAttachmentFileEntry : groupedCPTypeDisplayContext.getImages()) {
							String url = groupedCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay);
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
						CPAttachmentFileEntry cpAttachmentFileEntry = groupedCPTypeDisplayContext.getDefaultImage();
						%>

						<c:if test="<%= cpAttachmentFileEntry != null %>">
							<img class="center-block img-responsive" id="full-image" src="<%= groupedCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay) %>">
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
							<%= groupedCPTypeDisplayContext.renderOptions(renderRequest, renderResponse) %>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">

				<%
				for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : groupedCPTypeDisplayContext.getCPDefinitionGroupedEntry()) {
					CPDefinition curCPDefinition = cpDefinitionGroupedEntry.getCPDefinition();
				%>

					<div class="row">
						<div class="col-md-4">
							<img class="img-responsive" src="<%= curCPDefinition.getDefaultImageThumbnailSrc(themeDisplay) %>">
						</div>

						<div class="col-md-8">
							<h5>
								<%= curCPDefinition.getTitle(languageId) %>
							</h5>

							<h6>
								<liferay-ui:message arguments="<%= cpDefinitionGroupedEntry.getQuantity() %>" key="quantity-x" />
							</h6>
						</div>
					</div>

				<%
				}
				%>

			</div>
		</div>
	</div>

	<aui:script>
		$(document).ready(
			function() {
				$(".thumb").click(
					function() {
						$("#full-image").attr("src", $(this).attr("data-url"));
					});
			});
	</aui:script>
</liferay-ddm:template-renderer>