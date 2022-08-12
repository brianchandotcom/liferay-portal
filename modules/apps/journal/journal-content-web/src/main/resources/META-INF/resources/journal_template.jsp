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
String refererPortletName = ParamUtil.getString(request, "refererPortletName");
%>

<clay:sheet-section>
	<div class="sheet-subtitle">
		<liferay-ui:message key="template" />
	</div>

	<div>
		<liferay-ui:message key="please-select-one-option" />
	</div>

	<%
	String defaultDDMTemplateName = LanguageUtil.get(request, "no-template");

	DDMTemplate defaultDDMTemplate = journalContentDisplayContext.getDefaultDDMTemplate();

	if (defaultDDMTemplate != null) {
		defaultDDMTemplateName = HtmlUtil.escape(defaultDDMTemplate.getName(locale));
	}
	%>

	<aui:input checked="<%= journalContentDisplayContext.isDefaultTemplate() %>" id='<%= refererPortletName + "ddmTemplateTypeDefault" %>' label='<%= LanguageUtil.format(request, "use-default-template-x", defaultDDMTemplateName, false) %>' name='<%= refererPortletName + "ddmTemplateType" %>' type="radio" useNamespace="<%= false %>" value="default" />

	<aui:input checked="<%= !journalContentDisplayContext.isDefaultTemplate() %>" id='<%= refererPortletName + "ddmTemplateTypeCustom" %>' label="use-a-specific-template" name='<%= refererPortletName + "ddmTemplateType" %>' type="radio" useNamespace="<%= false %>" value="custom" />

	<div id="<%= refererPortletName %>customDDMTemplateContainer">
		<div class="template-preview-content">
			<c:choose>
				<c:when test="<%= journalContentDisplayContext.isDefaultTemplate() %>">
					<p class="text-default">
						<liferay-ui:message key="no-template" />
					</p>
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/journal_template_resources.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</div>

		<aui:button id='<%= refererPortletName + "selectDDMTemplateButton" %>' useNamespace="<%= false %>" value="select" />

		<aui:button id='<%= refererPortletName + "clearddmTemplateButton" %>' useNamespace="<%= false %>" value="clear" />
	</div>
</clay:sheet-section>

<liferay-frontend:component
	componentId="journalTemplate"
	context="<%= journalContentDisplayContext.getJournalTemplateContext() %>"
	module="js/JournalTemplate"
/>