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

<%@ include file="/empty_result_message/init.jsp" %>

<div class="taglib-empty-result-message">
	<div class="text-center">
		<div class="<%= animationTypeCssClass %>"></div>

		<h1 class="taglib-empty-result-message-title">
			<liferay-ui:message arguments="<%= elementType %>" key="no-x-yet" translateArguments="<%= false %>" />
		</h1>

		<c:if test="<%= Validator.isNotNull(description) %>">
			<p class="taglib-empty-result-message-description">
				<%= description %>
			</p>
		</c:if>

		<c:if test="<%= Validator.isNotNull(actions) %>">
			<div class="taglib-empty-result-message-actions">
				<clay:dropdown-menu
					items="<%= actions %>"
					itemsIconAlignment="top"
					label='<%= LanguageUtil.get(request, "new") %>'
					style="secondary"
					triggerCssClasses="btn-secondary"
				/>
			</div>
		</c:if>
	</div>
</div>