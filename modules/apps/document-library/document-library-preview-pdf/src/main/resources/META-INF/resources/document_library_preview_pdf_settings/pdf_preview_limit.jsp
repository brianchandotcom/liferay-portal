<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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