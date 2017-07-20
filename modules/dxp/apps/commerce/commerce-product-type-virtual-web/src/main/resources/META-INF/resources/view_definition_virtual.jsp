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
VirtualCPTypeDisplayContext virtualCPTypeDisplayContext = (VirtualCPTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("virtualCPTypeDisplayContext", virtualCPTypeDisplayContext);

CPDefinition cpDefinition = virtualCPTypeDisplayContext.getCPDefinition();

List<CPDefinition> cpDefinitions = new ArrayList<>();

cpDefinitions.add(cpDefinition);
%>

<liferay-ddm:template-renderer
	className="<%= VirtualCPType.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= virtualCPTypeDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= virtualCPTypeDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= cpDefinitions %>"
>

	<%
	CPDefinitionVirtualSetting cpDefinitionVirtualSetting = virtualCPTypeDisplayContext.getCPDefinitionVirtualSetting();
	%>

	<div class="container-fluid product-detail">
		<div class="row">
			<div class="col-lg-6 col-md-7">
				<div class="row">
					<div class="col-lg-2 col-md-3 col-xs-2">

						<%
						for (CPAttachmentFileEntry cpAttachmentFileEntry : virtualCPTypeDisplayContext.getImages()) {
							String url = virtualCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay);
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
						CPAttachmentFileEntry cpAttachmentFileEntry = virtualCPTypeDisplayContext.getDefaultImage();
						%>

						<c:if test="<%= cpAttachmentFileEntry != null %>">
							<img class="center-block img-responsive" id="full-image" src="<%= virtualCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay) %>">
						</c:if>
					</div>
				</div>
			</div>

			<div class="col-lg-6 col-md-5">
				<h1><%= cpDefinition.getTitle(languageId) %></h1>
				<h4>Code: <%= cpDefinition.getBaseSKU() %></h4>
				<div class="row-fluid">
					<div class="col-md-12">
						<c:if test="<%= virtualCPTypeDisplayContext.hasSampleURL() %>">
							<a href="<%= virtualCPTypeDisplayContext.getSampleURL(themeDisplay) %>">
								<liferay-ui:message key="sample" />
							</a>
						</c:if>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="options">
							<%= virtualCPTypeDisplayContext.renderOptions(renderRequest, renderResponse) %>
						</div>
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
						$("#full-image").attr("src", $(this).attr("data-url"));
					});
			});
	</aui:script>
</liferay-ddm:template-renderer>