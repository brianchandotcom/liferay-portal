<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
	String redirect = ParamUtil.getString(request, "redirect");

	String redirectWindowState = ParamUtil.getString(request,
			"redirectWindowState");

	String cmd = ParamUtil.getString(request, Constants.CMD,
			Constants.EXPORT);

	String rootNodeName = ParamUtil.getString(request, "rootNodeName");
%>

<aui:form cssClass="lfr-export-dialog" method="post" name="fm1">
	<aui:input name="<%=Constants.CMD%>" type="hidden" value="<%=cmd%>" />

	<c:choose>
		<c:when test="<%=cmd.equals(Constants.EXPORT) %>">
			<%@ include
				file="/html/portlet/portal_settings/export_import_options.jspf" %>
			<aui:button-row>
				<aui:button type="submit" value="export" />
			</aui:button-row>
		</c:when>
		<c:when test="<%=cmd.equals(Constants.IMPORT) %>">
			<%@ include
				file="/html/portlet/portal_settings/export_import_options.jspf" %>
			<!-- TODO need to be updated -->
			<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />
			<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-lar-file-of-the-correct-type" />
			<liferay-ui:error exception="<%= PortalSettingsImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />
			<aui:button-row>
				<aui:button type="submit" value="import" />
			</aui:button-row>
		</c:when>
	</c:choose>
</aui:form>

<c:if test='<%= cmd.equals(Constants.IMPORT) && SessionMessages.contains(renderRequest, "request_processed") %>'>
	<aui:script>
		var opener = Liferay.Util.getOpener();
		if (opener.<portlet:namespace />saveCompany) {
			Liferay.fire(
				'closeWindow',
				{
					id: '<portlet:namespace />importDialog'
				}
			);

		  opener.<portlet:namespace />saveCompany();
		}
	</aui:script>
</c:if>

<aui:script use="aui-base,aui-loading-mask,json-stringify">
	var form = A.one('#<portlet:namespace />fm1');
	form.on(
		'submit',
		function(event) {
		  event.preventDefault();
			 <c:choose>
				<c:when test="<%=cmd.equals(Constants.EXPORT) %>">
					<portlet:actionURL var="exportSettingsURL">
					   <portlet:param name="struts_action" value="/portal_settings/export_settings" />
					</portlet:actionURL>
					submitForm(form, '<%=exportSettingsURL + "&etag=0" %>', false);
				</c:when>
				<c:when test="<%=cmd.equals(Constants.IMPORT) %>">
					<portlet:actionURL var="importSettingsURL">
					   <portlet:param name="struts_action" value="/portal_settings/import_settings" />
					</portlet:actionURL>
					form.attr('encoding', 'multipart/form-data');
					submitForm(form, '<%=importSettingsURL%>');
				</c:when>
			</c:choose>
               });
</aui:script>