<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String helpMessage = GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:helpMessage"));
String inlineLabel = GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:inlineLabel"));
String label = GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:label"));
String name = GetterUtil.getString((String)request.getAttribute("aui:field-wrapper:name"));
boolean required = GetterUtil.getBoolean((String)request.getAttribute("aui:field-wrapper:required"));

boolean showForLabel = false;

if (Validator.isNotNull(name)) {
	showForLabel = true;

	name = namespace + name;
}
%>

		<c:if test='<%= inlineLabel.equals("right") %>'>
			<label <%= AUIUtil.buildLabel(inlineLabel, showForLabel, name) %>>
				<liferay-ui:message key="<%= label %>" />

				<c:if test="<%= required %>">
					<span class="aui-label-required">(<liferay-ui:message key="required" />)</span>
				</c:if>

				<c:if test="<%= Validator.isNotNull(helpMessage) %>">
					<liferay-ui:icon-help message="<%= helpMessage %>" />
				</c:if>
			</label>
		</c:if>
	</div>
</div>