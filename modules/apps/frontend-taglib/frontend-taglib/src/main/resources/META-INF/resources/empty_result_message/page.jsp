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
	<div class="<%= animationTypeCssClass %>"></div>

	<h1 class="taglib-empty-result-message-title">
		<c:choose>
			<c:when test="<%= Validator.isNull(title) %>">
				<liferay-ui:message arguments="<%= elementType %>" key="no-x-yet" translateArguments="<%= false %>" />
			</c:when>
			<c:otherwise>
				<%= title %>
			</c:otherwise>
		</c:choose>
	</h1>

	<c:if test="<%= Validator.isNotNull(description) %>">
		<p class="taglib-empty-result-message-description">
			<%= description %>
		</p>
	</c:if>

	<c:if test="<%= Validator.isNotNull(actionDropdownItems) %>">
		<div class="c-mt-3 text-center">
			<c:choose>
				<c:when test="<%= actionDropdownItems.size() > 1 %>">
					<clay:dropdown-menu
						additionalProps="<%= additionalProps %>"
						displayType="<%= buttonCssClass %>"
						dropdownItems="<%= actionDropdownItems %>"
						label="new"
						propsTransformer="<%= propsTransformer %>"
						propsTransformerServletContext="<%= propsTransformerServletContext %>"
						small="<%= true %>"
					/>
				</c:when>
				<c:otherwise>

					<%
					DropdownItem actionDropdownItem = actionDropdownItems.get(0);
					%>

					<c:choose>
						<c:when test='<%= Validator.isNotNull(actionDropdownItem.get("href")) %>'>
							<clay:link
								displayType="<%= buttonCssClass %>"
								href='<%= String.valueOf(actionDropdownItem.get("href")) %>'
								label='<%= String.valueOf(actionDropdownItem.get("label")) %>'
								type="button"
							/>
						</c:when>
						<c:otherwise>
							<clay:button
								additionalProps='<%= (HashMap)actionDropdownItem.get("data") %>'
								displayType="<%= buttonCssClass %>"
								label='<%= String.valueOf(actionDropdownItem.get("label")) %>'
								propsTransformer="<%= buttonPropsTransformer %>"
								propsTransformerServletContext="<%= propsTransformerServletContext %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
</div>