<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/empty_result_message/init.jsp" %>

<div class="taglib-empty-result-message <%= Validator.isNotNull(animationTypeCssClass) ? "c-empty-state-animation" : StringPool.BLANK %>">
	<div class="c-empty-state-image <%= animationTypeCssClass %>"></div>

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
		<c:choose>
			<c:when test="<%= actionDropdownItems.size() > 1 %>">
				<clay:dropdown-menu
					additionalProps="<%= additionalProps %>"
					displayType="<%= buttonCssClass %>"
					dropdownItems="<%= actionDropdownItems %>"
					label="new"
					propsTransformer="<%= propsTransformer %>"
					propsTransformerServletContext="<%= propsTransformerServletContext %>"
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
	</c:if>
</div>