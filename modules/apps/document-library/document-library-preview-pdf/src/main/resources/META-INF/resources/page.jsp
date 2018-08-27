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
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

String downloadURL = DLUtil.getDownloadURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, StringPool.BLANK);
%>

<iframe src="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/pdfjs-1.9.426-dist/web/viewer.html?file=" + downloadURL) %>" style="border: none; height: 100%; width: 100%;"></iframe>