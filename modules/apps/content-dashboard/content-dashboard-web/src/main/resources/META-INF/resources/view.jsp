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

<clay:container
	className="main-content-body"
>
	<div class="sheet">
		<h2 class="sheet-title">
			<!-- Replace 0 from contents.lenght -->
			<%= LanguageUtil.format(request, "content-x", 0, false) %>
		</h2>

		<c:choose>
			<c:when test="<%= true %>">
				<!-- Check if contents is empty -->
				<div class="taglib-empty-result-message">
					<div class="taglib-empty-result-message-header"></div>

					<div class="sheet-text text-center">
						<%= LanguageUtil.get(request, "there-are-no-contents") %>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<!-- Show table with contents information -->
			</c:otherwise>
		</c:choose>
	</div>
</clay:container>