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
long fragmentCollectionId = ParamUtil.getLong(request, "fragmentCollectionId");

DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
%>

<div class="lfr-dynamic-uploader" id="<portlet:namespace />uploaderContainer">
	<div class="container-fluid-1280">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>
</div>

<aui:script use="liferay-upload">
	var uploader = new Liferay.Upload(
		{
			boundingBox: '#<portlet:namespace />fileUpload',

			<%
			DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
			%>

			decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
			fallback: '#<portlet:namespace />fallback',
			fileDescription: '<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>',
			maxFileSize: '<%= UploadServletRequestConfigurationHelperUtil.getMaxSize() %> B',
			namespace: '<portlet:namespace />',
			rootElement: '#<portlet:namespace />uploaderContainer',
			'strings.uploadsCompleteText': '<liferay-ui:message key="fragment-entries-imported-successfully" />',
			uploadFile: '<portlet:actionURL name="/fragment/import_fragment_entries"><portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentCollectionId) %>" /></portlet:actionURL>'
		}
	);
</aui:script>