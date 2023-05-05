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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

LayoutsSEODisplayContext layoutsSEODisplayContext = (LayoutsSEODisplayContext)request.getAttribute(LayoutSEOWebKeys.LAYOUT_PAGE_LAYOUT_SEO_DISPLAY_CONTEXT);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsSEODisplayContext.getSelLayout(), themeDisplay);
}
%>

<h2 class="mb-4 text-7"><liferay-ui:message key="custom-meta-tags" /></h2>

<liferay-frontend:edit-form
	action="<%= layoutsSEODisplayContext.getEditCustomMetaTagsURL() %>"
	cssClass="pt-0"
	method="post"
	name="fm"
	wrappedFormContent="<%= false %>"
>
	<aui:input name="redirect" type="hidden" value="<%= layoutsSEODisplayContext.getRedirectURL() %>" />
	<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
	<aui:input name="groupId" type="hidden" value="<%= layoutsSEODisplayContext.getGroupId() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsSEODisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsSEODisplayContext.getLayoutId() %>" />

	<liferay-frontend:edit-form-body>
		<clay:sheet
			cssClass="ml-0"
		>
			<clay:sheet-section>
				<h3 class="mb-4"><liferay-ui:message key="settings" /></h3>

				<p class="text-secondary">
					<liferay-ui:message key="custom-meta-tags-description" />
				</p>

				<liferay-ui:error exception="<%= DDMFormValuesValidationException.class %>" message="field-validation-failed" />

				<liferay-ui:error exception="<%= DDMFormValuesValidationException.RequiredValue.class %>">

					<%
					DDMFormValuesValidationException.RequiredValue rv = (DDMFormValuesValidationException.RequiredValue)errorException;

					String fieldLabelValue = rv.getFieldLabelValue(themeDisplay.getLocale());

					if (Validator.isNull(fieldLabelValue)) {
						fieldLabelValue = rv.getFieldName();
					}
					%>

					<liferay-ui:message arguments="<%= HtmlUtil.escape(fieldLabelValue) %>" key="no-value-is-defined-for-field-x" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-ddm:html
					classNameId="<%= PortalUtil.getClassNameId(com.liferay.dynamic.data.mapping.model.DDMStructure.class) %>"
					classPK="<%= layoutsSEODisplayContext.getDDMStructurePrimaryKey() %>"
					ddmFormValues="<%= layoutsSEODisplayContext.getDDMFormValues() %>"
					defaultEditLocale="<%= PortalUtil.getSiteDefaultLocale(layoutsSEODisplayContext.getGroupId()) %>"
					defaultLocale="<%= PortalUtil.getSiteDefaultLocale(layoutsSEODisplayContext.getGroupId()) %>"
					fieldsNamespace="<%= String.valueOf(layoutsSEODisplayContext.getDDMStructurePrimaryKey()) %>"
					groupId="<%= layoutsSEODisplayContext.getGroupId() %>"
					requestedLocale="<%= locale %>"
				/>
			</clay:sheet-section>
		</clay:sheet>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>