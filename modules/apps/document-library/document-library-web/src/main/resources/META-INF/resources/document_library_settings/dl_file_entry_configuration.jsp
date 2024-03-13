<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
DLFileEntryConfigurationDisplayContext dlFileEntryConfigurationDisplayContext = (DLFileEntryConfigurationDisplayContext)request.getAttribute(DLFileEntryConfigurationDisplayContext.class.getName());
%>

<aui:form action="<%= dlFileEntryConfigurationDisplayContext.getEditDLFileEntryConfigurationURL() %>" method="post" name="fm">
	<clay:sheet>
		<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>" message="there-was-an-unknown-error" />

		<liferay-ui:error exception="<%= DLFileEntryConfigurationException.InvalidPreviewableProcessorMaxSizeException.class %>">
			<liferay-ui:message key="maximum-file-size-limit-is-invalid" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= DLFileEntryConfigurationException.InvalidMaxNumberOfPagesException.class %>">
			<liferay-ui:message key="maximum-number-of-pages-limit-is-invalid" />
		</liferay-ui:error>

		<clay:sheet-header>
			<h2 class="c-mb-4">
				<liferay-ui:message key="dl-file-entry-configuration-name" />
			</h2>
		</clay:sheet-header>

		<clay:alert
			dismissible="<%= true %>"
			displayType="info"
			message="changes-will-only-apply-to-new-documents-uploaded"
		/>

		<clay:sheet-section>
			<h3 class="c-mb-2 sheet-subtitle text-secondary"><liferay-ui:message key="dl-file-entry-configuration-maximum-file-size-title" /></h3>

			<p class="c-mb-4 text-3 text-secondary">
				<liferay-ui:message key="dl-file-entry-configuration-maximum-file-size-help" />
			</p>

			<div>
				<span aria-hidden="true" class="loading-animation"></span>

				<react:component
					module="{LimitedNumberInput} from document-library-web"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"errorMessage", dlFileEntryConfigurationDisplayContext.getPreviewableProcessorMaxSizeLimitExceededErrorMessage()
						).put(
							"label", LanguageUtil.get(resourceBundle, "maximum-file-size")
						).put(
							"limitValue", dlFileEntryConfigurationDisplayContext.getPreviewableProcessorMaxSizeLimit()
						).put(
							"minimumValue", dlFileEntryConfigurationDisplayContext.getUnlimitedPreviewableProcessorMaxSize()
						).put(
							"name", liferayPortletResponse.getNamespace() + "previewableProcessorMaxSize"
						).put(
							"unlimitedValue", dlFileEntryConfigurationDisplayContext.getUnlimitedPreviewableProcessorMaxSize()
						).put(
							"value", dlFileEntryConfigurationDisplayContext.getPreviewableProcessorMaxSize()
						).build()
					%>'
				/>
			</div>
		</clay:sheet-section>

		<clay:sheet-section>
			<h3 class="c-mb-2 sheet-subtitle text-secondary"><liferay-ui:message key="dl-file-entry-configuration-maximum-number-of-pages-title" /></h3>

			<p class="c-mb-4 text-3 text-secondary">
				<liferay-ui:message key="dl-file-entry-configuration-maximum-number-of-pages-help" />
			</p>

			<div>
				<span aria-hidden="true" class="loading-animation"></span>

				<react:component
					module="{LimitedNumberInput} from document-library-web"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"errorMessage", dlFileEntryConfigurationDisplayContext.getMaxNumberOfPagesLimitExceededErrorMessage()
						).put(
							"label", LanguageUtil.get(resourceBundle, "maximum-number-of-pages")
						).put(
							"limitValue", dlFileEntryConfigurationDisplayContext.getMaxNumberOfPagesLimit()
						).put(
							"minimumValue", dlFileEntryConfigurationDisplayContext.getUnlimitedMaxNumberOfPages()
						).put(
							"name", liferayPortletResponse.getNamespace() + "maxNumberOfPages"
						).put(
							"unlimitedValue", dlFileEntryConfigurationDisplayContext.getUnlimitedMaxNumberOfPages()
						).put(
							"value", dlFileEntryConfigurationDisplayContext.getMaxNumberOfPages()
						).build()
					%>'
				/>
			</div>
		</clay:sheet-section>

		<clay:sheet-footer>
			<clay:button
				displayType="primary"
				label="save"
				type="submit"
			/>
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>