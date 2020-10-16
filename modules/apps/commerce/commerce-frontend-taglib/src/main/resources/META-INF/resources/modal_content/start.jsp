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

<%@ include file="/modal_content/init.jsp" %>

<div class="modal-iframe-wrapper">
	<c:if test="<%= Validator.isNotNull(title) %>">
		<header class="modal-header modal-iframe-header">
			<h2 class="modal-title"><%= HtmlUtil.escape(title) %></h2>

			<button aria-label="close" class="btn btn-unstyled close modal-closer" type="button">
				<clay:icon
					symbol="times"
				/>
			</button>
		</header>
	</c:if>

	<div class="<%= "modal-iframe-content" + (Validator.isNotNull(contentCssClasses) ? StringPool.SPACE + HtmlUtil.escapeAttribute(contentCssClasses) : StringPool.BLANK) %>">