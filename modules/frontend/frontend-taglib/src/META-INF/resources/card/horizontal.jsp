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

<div class="col-lg-4">
	<div class="card-horizontal">
		<div class="card-row card-row-padded entry-display-style <%= showCheckbox ? "selectable" : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %> >
			<div class="card-col-field">
				<c:if test="<%= showCheckbox %>">
					<aui:input cssClass="<%= checkboxCSSClass %>" id="<%= checkboxId %>" label="" name="<%= checkboxName %>" title="" type="checkbox" value="<%= checkboxId %>" wrappedField="<%= true %>" />
				</c:if>
			</div>
			<div class="card-col-field">
				<span class="<%= imageCSSClass %> <%= image %>"></span>
			</div>
			<div class="card-col-content card-col-gutters">
				<h4>
					<c:choose>
						<c:when test="<%= Validator.isNull(url) %>">
							<%= HtmlUtil.escape(title) %>
						</c:when>
						<c:otherwise>
							<a href="<%= url %>">
								<%= HtmlUtil.escape(title) %>
							</a>
						</c:otherwise>
					</c:choose>
				</h4>
			</div>
			<div class="card-col-content card-col-gutters">
				<liferay-util:include page="<%= actionJsp %>" servletContext="<%= actionJspServletContext %>" />
			</div>
		</div>
	</div>
</div>