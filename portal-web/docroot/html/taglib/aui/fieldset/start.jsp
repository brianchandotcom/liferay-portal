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

<%@ include file="/html/taglib/aui/fieldset/init.jsp" %>

<fieldset class="fieldset <%= collapsed ? "panel panel-default" : StringPool.BLANK %> <%= cssClass %>" <%= Validator.isNotNull(id) ? "id=\"" + namespace + id + "\"" : StringPool.BLANK %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
	<c:if test="<%= Validator.isNotNull(label) %>">
		<c:choose>
			<c:when test="<%= collapsed %>">
				<legend class="fieldset-legend panel-heading">
					<a aria-controls="collapseOne" aria-expanded="true" data-toggle="collapse" href="#<%= panelId %>" role="button">
						<liferay-ui:message key="<%= label %>" localizeKey="<%= localizeLabel %>" />

						<c:if test="<%= Validator.isNotNull(helpMessage) %>">
							<liferay-ui:icon-help message="<%= helpMessage %>" />
						</c:if>
					</a>
				</legend>
			</c:when>
			<c:otherwise>
				<legend class="fieldset-legend">
					<span class="legend">
						 <liferay-ui:message key="<%= label %>" localizeKey="<%= localizeLabel %>" />

						<c:if test="<%= Validator.isNotNull(helpMessage) %>">
							<liferay-ui:icon-help message="<%= helpMessage %>" />
						</c:if>
					</span>
				</legend>
			</c:otherwise>
		</c:choose>
	</c:if>

	<div class="<%= collapsed ? "panel-collapse collapse in" : StringPool.BLANK %> <%= column ? "row-fluid" : StringPool.BLANK %>" id="<%= panelId %>">