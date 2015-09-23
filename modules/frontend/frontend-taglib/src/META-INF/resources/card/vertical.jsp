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

<div class="<%= cssClass %> <%= showCheckbox ? "selectable" : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %>>
	<div class="<%= showCheckbox ? "checkbox checkbox-default toggle-card-dm" : StringPool.BLANK %>">
		<c:choose>
			<c:when test="<%= (rowChecker != null) && (resultRow != null) %>">
				<%= rowChecker.getRowCheckBox(request, rowChecker.isChecked(resultRow.getObject()), rowChecker.isDisabled(resultRow.getObject()), resultRow.getPrimaryKey()) %>
			</c:when>
			<c:when test="<%= showCheckbox %>">
				<aui:input checked="<%= checkboxChecked %>" cssClass="<%= checkboxCSSClass %>" data="<%= checkboxData %>" disabled="<%= checkboxDisabled %>" id="<%= checkboxId %>" label="" name="<%= checkboxName %>" title='<%= LanguageUtil.format(request, "select-x", new Object[] {HtmlUtil.escape(title)}) %>' type="checkbox" useNamespace="<%= false %>" value="<%= checkboxValue %>" wrappedField="<%= true %>" />
			</c:when>
		</c:choose>

		<div class="card card-dm <%= showCheckbox ? "toggle-card-container" : StringPool.BLANK %>">
			<div class="aspect-ratio">
				<aui:a href="<%= url %>">
					<img alt="" class="<%= imageCSSClass %>" src="<%= imageUrl %>" />
				</aui:a>

				<c:if test="<%= Validator.isNotNull(smallImageUrl) %>">
					<div class="sticker sticker-bottom <%= smallImageCSSClass %>">
						<img alt="thumbnail" class="img-responsive" src="<%= smallImageUrl %>">
					</div>
				</c:if>
			</div>

			<c:if test="<%= Validator.isNotNull(actionJsp) || Validator.isNotNull(header) || Validator.isNotNull(title) || Validator.isNotNull(subtitle) || Validator.isNotNull(footer) %>">
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
							<aui:a href="<%= url %>">
								<div class="card-dm-text-large"><%= title %></div>
							</aui:a>
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
			</c:if>
		</div>
	</div>
</div>