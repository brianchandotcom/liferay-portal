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

<%@ include file="/html/taglib/ui/panel/init.jsp" %>

<div class="panel panel-default">
	<div class="panel-heading" id="<%= id %>Header" role="tab">
		<div class="h4 panel-title">
			<a aria-controls="<%= id %>Content" aria-expanded="true" class="collapse-icon collapse-icon-middle <%= (panelCount.getValue() > 1) ? "collapsed" : StringPool.BLANK %>" data-toggle="collapse" data-parent="#<%= parentId %>" href="#<%= id %>Content" role="button">
				<c:if test="<%= Validator.isNotNull(iconCssClass) %>">
					<i class="<%= iconCssClass %>"></i>
				</c:if>

				<liferay-ui:message key="<%= title %>" />

				<c:if test="<%= Validator.isNotNull(helpMessage) %>">
					<liferay-ui:icon-help message="<%= helpMessage %>" />
				</c:if>
			</a>
		</div>
	</div>

	<div aria-labelledby="<%= id %>Header" class="panel-collapse collapse <%= (panelCount.getValue() == 1) ? "in" : StringPool.BLANK %>" id="<%= id %>Content" role="tabpanel">
		<div class="panel-body">