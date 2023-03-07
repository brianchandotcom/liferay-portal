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
PDFPreviewConfigurationDisplayContext pdfPreviewConfigurationDisplayContext = (PDFPreviewConfigurationDisplayContext)request.getAttribute(PDFPreviewConfigurationDisplayContext.class.getName());
%>

<aui:form action="<%= pdfPreviewConfigurationDisplayContext.getEditPDFPreviewConfigurationURL() %>" method="post" name="fm">
	<clay:sheet>
		<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>" message="there-was-an-unknown-error" />

		<liferay-ui:error exception="<%= PDFPreviewException.class %>">
			<liferay-ui:message key="maximum-number-of-pages-limit-is-not-valid" />
		</liferay-ui:error>

		<clay:sheet-header>
			<h2 class="sheet-title">
				<liferay-ui:message key="pdf-preview-configuration-name" />
			</h2>

			<div class="sheet-text">
				<liferay-ui:message key="maximum-number-of-pages-help" />
			</div>
		</clay:sheet-header>

		<clay:sheet-section>
			<span aria-hidden="true" class="loading-animation"></span>

			<react:component
				module="document_library_preview_pdf_settings/js/PDFPreviewLimit"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"maxLimitSize", pdfPreviewConfigurationDisplayContext.getMaxLimitSize()
					).put(
						"namespace", liferayPortletResponse.getNamespace()
					).put(
						"scopeLabel", pdfPreviewConfigurationDisplayContext.getSuperiorScopeLabel()
					).put(
						"value", pdfPreviewConfigurationDisplayContext.getMaxNumberOfPages()
					).build()
				%>'
			/>
		</clay:sheet-section>

		<clay:sheet-footer>
			<aui:button primary="<%= true %>" type="submit" value="save" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>