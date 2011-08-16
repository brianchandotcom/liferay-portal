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

<%@ include file="/html/portlet/osgiadmin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Bundle bundle = null;
boolean viewBundle = false;

if (row != null) {
	bundle = (Bundle)row.getObject();
}
else {
	bundle = (Bundle)request.getAttribute("edit_bundle.jsp-bundle");
	viewBundle = true;
}

boolean view = false;

if (row == null) {
	view = true;
}

Dictionary<String,String> headers = bundle.getHeaders(themeDisplay.getLanguageId());

String bundleUpdateLocation = headers.get(org.osgi.framework.Constants.BUNDLE_UPDATELOCATION);

if (Validator.isNull(bundleUpdateLocation)) {
	bundleUpdateLocation = bundle.getLocation();

	try {
		URL url = new URL(bundleUpdateLocation);
	}
	catch (MalformedURLException mue) {
		bundleUpdateLocation = null;
	}
}
%>

<liferay-ui:icon-menu showExpanded="<%= view %>" showWhenSingleIcon="<%= view %>">
	<c:if test="<%= permissionChecker.isOmniadmin() %>">
		<c:if test="<%= !viewBundle %>">
			<portlet:renderURL var="viewURL">
				<portlet:param name="struts_action" value="/osgiadmin/edit_bundle" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="bundleId" value="<%= String.valueOf(bundle.getBundleId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="view"
				url="<%= viewURL %>"
			/>
		</c:if>

		<c:if test="<%= bundle.getBundleId() != 0 %>">
			<liferay-ui:icon
				cssClass='<%= (bundle.getState() != Bundle.ACTIVE ? "" : "aui-helper-hidden ") + renderResponse.getNamespace() + "start_" + bundle.getBundleId() %>'
				message="start"
				src='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>'
				url='<%= "javascript:Liferay.OSGiAdmin.Util.start({bundleId:" + bundle.getBundleId() + ", message: \'" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-start-this-bundle") + "\', namespace: \'" + renderResponse.getNamespace() + "\'})" %>'
			/>

			<liferay-ui:icon
				cssClass='<%= (bundle.getState() == Bundle.ACTIVE ? "" : "aui-helper-hidden ") + renderResponse.getNamespace() + "stop_" + bundle.getBundleId() %>'
				message="stop"
				src='<%= themeDisplay.getPathThemeImages() + "/application/close.png" %>'
				url='<%= "javascript:Liferay.OSGiAdmin.Util.stop({bundleId:" + bundle.getBundleId() + ", message: \'" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-stop-this-bundle") + "\', namespace: \'" + renderResponse.getNamespace() + "\'})" %>'
			/>

			<liferay-ui:icon
				message="uninstall"
				src='<%= themeDisplay.getPathThemeImages() + "/common/delete.png" %>'
				url='<%= "javascript:" + renderResponse.getNamespace() + "uninstall(\'" + bundle.getBundleId() + "\');" %>'
			/>

			<c:if test="<%= Validator.isNotNull(bundleUpdateLocation) %>">
				<liferay-ui:icon
					message="update"
					src='<%= themeDisplay.getPathThemeImages() + "/common/undo.png" %>'
					url='<%= "javascript:Liferay.OSGiAdmin.Util.update({bundleId:" + bundle.getBundleId() + ", message: \'" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-update-this-bundle") + "\', namespace: \'" + renderResponse.getNamespace() + "\'})" %>'
				/>
			</c:if>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>