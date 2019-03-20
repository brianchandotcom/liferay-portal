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

<c:choose>
	<c:when test="<%= (rowChecker != null) && (resultRow != null) %>">
		<liferay-util:buffer
			var="checkboxInput"
		>
			<%= rowChecker.getRowCheckBox(request, rowChecker.isChecked(resultRow.getObject()), rowChecker.isDisabled(resultRow.getObject()), resultRow.getPrimaryKey()) %>
		</liferay-util:buffer>

		<c:if test="<%= Validator.isNotNull(checkboxInput) %>">
			<div class="card-type-directory form-check form-check-card form-check-middle-left">
					<div class="custom-checkbox custom-control">
						<label>
							<%= checkboxInput %>
							<span class="custom-control-label"></span>
		</c:if>
	</c:when>
</c:choose>

<div class="card card-horizontal <%= Validator.isNotNull(cssClass) ? cssClass : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %>>
	<div class="card-body">
		<div class="card-row">
			<c:if test="<%= Validator.isNotNull(colHTML) %>">
				<div class="autofit-col">
					<%= colHTML %>
				</div>
			</c:if>

			<div class="autofit-col autofit-col-expand autofit-col-gutters">
				<aui:a data="<%= linkData %>" href="<%= url %>" title="<%= HtmlUtil.escapeAttribute(text) %>">
					<%= HtmlUtil.escape(text) %>
				</aui:a>
			</div>

			<liferay-util:buffer
				var="actionJspBuffer"
			>
				<liferay-util:include page="<%= actionJsp %>" servletContext="<%= actionJspServletContext %>" />
			</liferay-util:buffer>

			<c:if test="<%= Validator.isNotNull(actionJspBuffer) %>">
				<div class="autofit-col">
					<%= actionJspBuffer %>
				</div>
			</c:if>
		</div>
	</div>
</div>

<c:choose>
	<c:when test="<%= (rowChecker != null) && (resultRow != null) %>">
		<liferay-util:buffer
			var="checkboxInput"
		>
			<%= rowChecker.getRowCheckBox(request, rowChecker.isChecked(resultRow.getObject()), rowChecker.isDisabled(resultRow.getObject()), resultRow.getPrimaryKey()) %>
		</liferay-util:buffer>

		<c:if test="<%= Validator.isNotNull(checkboxInput) %>">
					</label>
				</div>
			</div>
		</c:if>
	</c:when>
</c:choose>