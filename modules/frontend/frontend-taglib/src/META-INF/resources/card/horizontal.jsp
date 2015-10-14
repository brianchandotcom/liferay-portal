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

<div class="card-horizontal <%= Validator.isNotNull(cssClass) ? cssClass : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %> >
	<div class="card-row card-row-padded <%= showCheckbox ? "selectable" : StringPool.BLANK %>">
		<c:if test="<%= (((rowChecker != null) && (resultRow != null)) || showCheckbox) %>">
			<div class="card-col-field checkbox-default">
				<c:choose>
					<c:when test="<%= (rowChecker != null) && (resultRow != null) %>">
						<%= rowChecker.getRowCheckBox(request, rowChecker.isChecked(resultRow.getObject()), rowChecker.isDisabled(resultRow.getObject()), resultRow.getPrimaryKey()) %>
					</c:when>
					<c:otherwise>
						<aui:input checked="<%= checkboxChecked %>" cssClass="<%= checkboxCSSClass %>" data="<%= checkboxData %>" disabled="<%= checkboxDisabled %>" id="<%= checkboxId %>" label="" name="<%= checkboxName %>" title='<%= LanguageUtil.format(request, "select-x", new Object[] {HtmlUtil.escape(text)}) %>' type="checkbox" useNamespace="<%= false %>" value="<%= checkboxValue %>" wrappedField="<%= true %>" />
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>

		<c:if test="<%= Validator.isNotNull(imageUrl) %>">
			<div class="card-col-field">
				<span class="<%= Validator.isNotNull(imageCSSClass) ? imageCSSClass : StringPool.BLANK %> <%= Validator.isNotNull(imageUrl) ? imageUrl : StringPool.BLANK %>"></span>
			</div>
		</c:if>

		<div class="card-col-content card-col-gutters">
			<h4>
				<aui:a href="<%= url %>" label="<%= HtmlUtil.escape(title) %>" />
			</h4>
		</div>

		<c:if test="<%= Validator.isNotNull(actionJsp) %>">
			<div class="card-col-content card-col-gutters">
				<liferay-util:include page="<%= actionJsp %>" servletContext="<%= actionJspServletContext %>" />
			</div>
		</c:if>
	</div>
</div>