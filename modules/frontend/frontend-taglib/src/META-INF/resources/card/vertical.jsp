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

<%@ include file="/card/init.jsp" %>

<div class="col-lg-4 entry-display-style <%= showCheckbox ? "selectable" : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %>>
	<div class="checkbox checkbox-default toggle-card-dm">
		<aui:input cssClass="<%= checkboxCSSClass %>" id="<%= checkboxId %>" label="" name="<%= checkboxName %>" title="" type="checkbox" value="<%= checkboxId %>" wrappedField="<%= true %>" />

		<div class="card card-dm toggle-card-container">
			<div class="aspect-ratio">
				<c:choose>
					<c:when test="<%= Validator.isNotNull(url) %>">
						<a href="<%= url %>">
							<img alt="" src="<%= image %>" />
						</a>
					</c:when>
					<c:otherwise>
						<img alt="" class="<%= imageCSSClass %>" src="<%= image %>" />
					</c:otherwise>
				</c:choose>

				<div class="sticker sticker-bottom <%= smallImageCSSClass %>">
					<img alt="thumbnail" class="img-responsive" src="<%= smallImageUrl %>">
				</div>
			</div>
			<div class="card-footer">
				<div class="card-dm-more-options">
					<liferay-util:include page="<%= actionJsp %>" servletContext="<%= actionJspServletContext %>" />
				</div>

				<div class="card-dm-details">
					<c:if test="<%= Validator.isNotNull(header) %>">
						<div class="card-dm-text-small">
							<%= header %>
						</div>
					</c:if>

					<c:if test="<%= Validator.isNotNull(title) %>">
						<c:choose>
							<c:when test="<%= Validator.isNotNull(url) %>">
								<a href="<%= url %>" title="">
									<div class="card-dm-text-large"><%= title %></div>
								</a>
							</c:when>
							<c:otherwise>
								<div class="card-dm-text-large"><%= title %></div>
							</c:otherwise>
						</c:choose>
					</c:if>

					<c:if test="<%= Validator.isNotNull(subtitle) %>">
						<div class="card-dm-text">
							<%= subtitle %>
						</div>
					</c:if>

					<c:if test="<%= Validator.isNotNull(footer) %>">
						<div class="card-dm-text-small">
							<%= footer %>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>