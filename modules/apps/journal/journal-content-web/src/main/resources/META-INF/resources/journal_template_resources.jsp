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

<%
DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
String ddmTemplateImageURL = ddmTemplate.getTemplateImageURL(themeDisplay);
%>

<div class="row">
	<div class="col-md-4">
		<liferay-frontend:horizontal-card
			text="<%= ddmTemplate.getName(locale) %>"
		>
			<liferay-frontend:horizontal-card-col>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(ddmTemplateImageURL) %>">
						<img alt="" class="<%= Validator.isNotNull(ddmTemplateImageURL) ? "icon-monospaced" : StringPool.BLANK %>" src="<%= ddmTemplateImageURL %>" />
					</c:when>
					<c:otherwise>
						<liferay-frontend:horizontal-card-icon
							icon="edit-layout"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-frontend:horizontal-card-col>
		</liferay-frontend:horizontal-card>
	</div>
</div>