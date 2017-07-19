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
CPGroupedCPTypeDisplayContext cpGroupedCPTypeDisplayContext = (CPGroupedCPTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("cpGroupedCPTypeDisplayContext", cpGroupedCPTypeDisplayContext);

CPDefinition cpDefinition = cpGroupedCPTypeDisplayContext.getCPDefinition();

List<CPDefinition> entries = new ArrayList<>();

entries.add(cpDefinition);

List<CPDefinition> cpDefinitions = cpGroupedCPTypeDisplayContext.getCPDefinitions();
%>

<liferay-ddm:template-renderer
	className="<%= GroupedCPTypeRenderer.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= cpGroupedCPTypeDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpGroupedCPTypeDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= entries %>"
>
	<c:if test="<%= cpDefinition != null %>">
		<div class="product-detail row-fluid">
			<div class="col-md-7">
				<div class="row-fluid">
					<div class="col-md-3">

						<%
						for (CPAttachmentFileEntry cpAttachmentFileEntry : cpGroupedCPTypeDisplayContext.getImages()) {
							String url = cpGroupedCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay);
						%>

						<div class="row-fluid">
							<div class="col-md-12 thumb" data-url="<%= url %>">
								<img src="<%= url %>">
							</div>
						</div>

						<%
						}
						%>

					</div>

					<div class="col-md-9 full-image">

						<%
						CPAttachmentFileEntry cpAttachmentFileEntry = cpGroupedCPTypeDisplayContext.getDefaultImage();
						%>

						<c:if test="<%= cpAttachmentFileEntry != null %>">
							<img id="full-image" src="<%= cpGroupedCPTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay) %>">
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
							<%= cpGroupedCPTypeDisplayContext.renderOptions(renderRequest, renderResponse) %>
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

					<%
					for (CPDefinition curCPDefinition : cpDefinitions) {
						CPDefinitionGroupedEntry cpDefinitionGroupedEntry = cpGroupedCPTypeDisplayContext.getCPDefinitionGroupedEntry(cpDefinition.getCPDefinitionId(), curCPDefinition.getCPDefinitionId());
					%>

					<c:choose>
						<c:when test="<%= curCPDefinition.isAvailableIndividually() %>">
						<div class="row">
							<div class="col-md-4 grouped-thumbnails">
								<img src="<%= curCPDefinition.getDefaultImageThumbnailSrc(themeDisplay) %>">
							</div>

							<div class="col-md-8">
								<h5>
									<a href="<%= cpGroupedCPTypeDisplayContext.getCPDefinitionURL(curCPDefinition, themeDisplay) %>"><%= curCPDefinition.getTitle(languageId) %></a>
								</h5>

								<h6>
									<liferay-ui:message arguments="<%= cpDefinitionGroupedEntry.getQuantity() %>" key="quantity-x" />
								</h6>
							</div>
						</div>
						</c:when>
						<c:otherwise>
							<div class="row">
								<div class="col-md-4 grouped-thumbnails">
									<img src="<%= curCPDefinition.getDefaultImageThumbnailSrc(themeDisplay) %>">
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
						</c:otherwise>
					</c:choose>

					<%
					}
					%>

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
</liferay-ddm:template-renderer>

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

	.product-detail .thumb img,
	.product-detail .grouped-thumbnails img{
		width: 100%;
	}

	.product-detail .full-image img{
		width: 80%;
	}

	.product-detail .full-image{
		text-align: center;
	}

</style>