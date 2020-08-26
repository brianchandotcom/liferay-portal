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

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMFormInstance ddmFormInstance = (DDMFormInstance)row.getObject();

DateSearchEntry dateSearchEntry = new DateSearchEntry();

dateSearchEntry.setDate(ddmFormInstance.getModifiedDate());

String href = (String)request.getAttribute(WebKeys.SEARCH_ENTRY_HREF);
%>

<div class="clamp-container">
	<h2 class="h5 text-truncate">

		<%
		boolean hasValidStorageType = ddmFormAdminDisplayContext.hasValidStorageType(ddmFormInstance);
		%>

		<c:choose>
			<c:when test="<%= hasValidStorageType %>">
				<aui:a cssClass="form-instance-name" href="<%= href %>">
					<%= HtmlUtil.escape(ddmFormInstance.getName(locale)) %>
				</aui:a>
			</c:when>
			<c:otherwise>
				<span style="color: #da1414; margin-right: 5px;">
					<liferay-ui:icon
						icon="exclamation-full"
						markupView="lexicon"
						message='<%= LanguageUtil.format(request, "this-form-was-created-using-a-storage-type-x-that-is-not-available-for-this-liferay-dxp-installation.-install-x-to-make-it-available-for-editing", ddmFormInstance.getStorageType()) %>'
						toolTip="<%= true %>"
					/>
				</span>
				<span style="font-weight: normal;">
					<%= HtmlUtil.escape(ddmFormInstance.getName(locale)) %>
				</span>
			</c:otherwise>
		</c:choose>
	</h2>

	<span class="text-default">
		<div class="form-instance-description text-truncate">
			<%= HtmlUtil.escape(ddmFormInstance.getDescription(locale)) %>
		</div>
	</span>
	<span class="text-default">
		<span class="form-instance-id">
			<liferay-ui:message key="id" />: <%= ddmFormInstance.getFormInstanceId() %>
		</span>
		<span class="form-instance-modified-date">
			<liferay-ui:message key="modified-date" />: <%= dateSearchEntry.getName(request) %>
		</span>
	</span>
</div>