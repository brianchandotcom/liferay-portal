<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMFormInstance ddmFormInstance = (DDMFormInstance)row.getObject();

DateSearchEntry dateSearchEntry = new DateSearchEntry();

dateSearchEntry.setDate(ddmFormInstance.getModifiedDate());
%>

<div class="clamp-container">
	<h2 class="h5 text-truncate">

		<%
		boolean hasValidDDMFormFields = ddmFormAdminDisplayContext.hasValidDDMFormFields(ddmFormInstance);
		boolean hasValidStorageType = ddmFormAdminDisplayContext.hasValidStorageType(ddmFormInstance);
		%>

		<c:choose>
			<c:when test="<%= hasValidDDMFormFields && hasValidStorageType %>">
				<aui:a cssClass="form-instance-name" href="<%= (String)request.getAttribute(WebKeys.SEARCH_ENTRY_HREF) %>">
					<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(ddmFormInstance.getName(locale))) %>
				</aui:a>
			</c:when>
			<c:otherwise>

				<%
				String errorMessage = StringPool.BLANK;

				if (!hasValidDDMFormFields) {
					errorMessage = LanguageUtil.format(request, "this-form-was-created-using-a-custom-field-type-x-that-is-not-available-for-this-liferay-dxp-installation.-instal-x-to-make-it-available-for-editing", ddmFormAdminDisplayContext.getInvalidDDMFormFieldType(ddmFormInstance));
				}
				else if (!hasValidStorageType) {
					errorMessage = LanguageUtil.format(request, "this-form-was-created-using-a-storage-type-x-that-is-not-available-for-this-liferay-dxp-installation.-install-x-to-make-it-available-for-editing", ddmFormInstance.getStorageType());
				}
				%>

				<span class="error-icon">
					<liferay-ui:icon
						icon="exclamation-full"
						markupView="lexicon"
						message="<%= errorMessage %>"
						toolTip="<%= true %>"
					/>
				</span>
				<span class="invalid-form-instance">
					<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(ddmFormInstance.getName(locale))) %>
				</span>
			</c:otherwise>
		</c:choose>
	</h2>

	<span class="text-default">
		<div class="form-instance-description text-truncate">
			<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(ddmFormInstance.getDescription(locale))) %>
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