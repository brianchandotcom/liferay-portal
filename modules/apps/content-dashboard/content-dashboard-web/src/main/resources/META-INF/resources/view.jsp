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
	cssClass="main-content-body"
>
	<div class="sheet">
		<h2 class="sheet-title">
			<%= LanguageUtil.format(request, "content-per-audience-and-stage-x", 0, false) %>
		</h2>

		<div id="audit-graph">
			<div class="inline-item my-5 p-5 w-100">
				<span aria-hidden="true" class="loading-animation"></span>
			</div>

			<react:component
				module="js/AuditGraphApp"
			/>
		</div>
	</div>
</clay:container>

<clay:container
	cssClass="main-content-body"
>
	<div class="sheet">
		<h2 class="sheet-title">
			<%= LanguageUtil.format(request, "content-x", 0, false) %>
		</h2>

		<c:choose>
			<c:when test="<%= true %>">
				<div class="taglib-empty-result-message">
					<div class="taglib-empty-result-message-header"></div>

					<div class="sheet-text text-center">
						<%= LanguageUtil.get(request, "there-is-no-content") %>
					</div>
				</div>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</div>
</clay:container>