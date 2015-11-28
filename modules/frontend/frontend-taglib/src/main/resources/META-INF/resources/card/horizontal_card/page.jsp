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

<%@ include file="/card/horizontal_card/init.jsp" %>

<c:if test="<%= ((rowChecker != null) && (resultRow != null)) || showCheckbox %>">
	<div class="checkbox checkbox-card checkbox-middle-left">
		<label>
			<c:choose>
				<c:when test="<%= (rowChecker != null) && (resultRow != null) %>">
					<%= rowChecker.getRowCheckBox(request, rowChecker.isChecked(resultRow.getObject()), rowChecker.isDisabled(resultRow.getObject()), resultRow.getPrimaryKey()) %>
				</c:when>
				<c:otherwise>
					<aui:input checked="<%= checkboxChecked %>" cssClass="<%= checkboxCSSClass %>" data="<%= checkboxData %>" disabled="<%= checkboxDisabled %>" id="<%= checkboxId %>" label="" name="<%= checkboxName %>" title='<%= LanguageUtil.format(request, "select-x", new Object[] {HtmlUtil.escape(text)}) %>' type="checkbox" useNamespace="<%= false %>" value="<%= checkboxValue %>" wrappedField="<%= true %>" />
				</c:otherwise>
			</c:choose>
</c:if>

<div class="card card-horizontal <%= Validator.isNotNull(cssClass) ? cssClass : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %> >
	<div class="card-row card-row-padded <%= showCheckbox ? "selectable" : StringPool.BLANK %>">
		<c:if test="<%= Validator.isNotNull(imageUrl) || Validator.isNotNull(icon) %>">
			<div class="card-col-field">
				<c:choose>
					<c:when test="<%= Validator.isNotNull(imageUrl) %>">
						<img alt="" class="<%= Validator.isNotNull(imageCSSClass) ? imageCSSClass : StringPool.BLANK %>" src="<%= imageUrl %>" />
					</c:when>
					<c:otherwise>
						<span class="<%= Validator.isNotNull(imageCSSClass) ? imageCSSClass : StringPool.BLANK %> <%= icon %>"></span>
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>

		<div class="card-col-content card-col-gutters">
			<h4>
				<aui:a href="<%= url %>" label="<%= HtmlUtil.escape(text) %>" />
			</h4>
		</div>

		<c:if test="<%= Validator.isNotNull(actionJsp) %>">
			<div class="card-col-content card-col-gutters">
				<liferay-util:include page="<%= actionJsp %>" servletContext="<%= actionJspServletContext %>" />
			</div>
		</c:if>
	</div>
</div>

<c:if test="<%= ((rowChecker != null) && (resultRow != null)) || showCheckbox %>">
		</label>
	</div>
</c:if>