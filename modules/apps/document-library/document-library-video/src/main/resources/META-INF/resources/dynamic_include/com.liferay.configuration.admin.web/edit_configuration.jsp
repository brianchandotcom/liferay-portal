<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DLVideoFFMPEGVideoConverter dlVideoFFMPEGVideoConverter = (DLVideoFFMPEGVideoConverter)request.getAttribute(DLVideoFFMPEGVideoConverter.class.getName());
%>

<c:if test="<%= dlVideoFFMPEGVideoConverter.isEnabled() && !DLVideoFFMPEGUtil.isFFMPEGInstalled() %>">
	<clay:alert
		displayType="danger"
		message='<%= LanguageUtil.format(request, "the-ffmpeg-executable-is-not-installed-on-your-system.-please-contact-your-administrator-to-install-it-following-the-instructions-in-x", "https://ffmpeg.org/download.html") %>'
	/>
</c:if>