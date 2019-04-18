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
SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request);
%>

<div class="lfr-search-container-wrapper" id="<portlet:namespace/>layoutTypes">
	<c:if test="<%= selectLayoutPageTemplateEntryDisplayContext.getPrimaryTypesCount() > 0 %>">
		<h6 class="sheet-subtitle">
			<liferay-ui:message key="main-types" />
		</h6>

		<ul class="card-page card-page-equal-height">

			<%
			for (String primaryType : selectLayoutPageTemplateEntryDisplayContext.getPrimaryTypes()) {
				SelectBasicPagesVerticalCard selectBasicPagesVerticalCard = new SelectBasicPagesVerticalCard(primaryType, renderRequest, renderResponse);
			%>

				<li class="card-page-item col-md-4 col-sm-6">
					<div class="add-layout-action-option card card-interactive card-interactive-primary card-type-template template-card " <%= AUIUtil.buildData(selectBasicPagesVerticalCard.getDataLink()) %> tabindex="0">
						<div class="aspect-ratio">
							<div class="aspect-ratio-item-center-middle aspect-ratio-item-flush layout-type-img">
								<img src="<%= selectBasicPagesVerticalCard.getImageSrc() %>" />
							</div>
						</div>

						<div class="card-body">
							<div class="card-title">
								<%= selectBasicPagesVerticalCard.getTitle() %>
							</div>

							<div class="card-text">
								<%= selectBasicPagesVerticalCard.getSubtitle() %>
							</div>
						</div>
					</div>
				</li>

			<%
			}
			%>

		</ul>
	</c:if>

	<c:if test="<%= selectLayoutPageTemplateEntryDisplayContext.getTypesCount() > 0 %>">
		<h6 class="sheet-subtitle">
			<liferay-ui:message key="other" />
		</h6>

		<ul class="card-page card-page-equal-height">

			<%
			for (String type : selectLayoutPageTemplateEntryDisplayContext.getTypes()) {
			%>

				<li class="card-page-item col-md-4 col-sm-6">
					<clay:horizontal-card
						horizontalCard="<%= new SelectBasicPagesHorizontalCard(type, renderRequest, renderResponse) %>"
					/>
				</li>

			<%
			}
			%>

		</ul>
	</c:if>
</div>