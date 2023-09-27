<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		<clay:button
			displayType="secondary"
			id='<%= refererPortletName + "selectDDMTemplateButton" %>'
			label="select"
		/>

		<clay:button
			displayType="secondary"
			id='<%= refererPortletName + "clearddmTemplateButton" %>'
			label="clear"
		/>
	</div>
</clay:sheet-section>

<liferay-frontend:component
	componentId="journalTemplate"
	context="<%= journalContentDisplayContext.getJournalTemplateContext() %>"
	module="js/JournalTemplate"
/>