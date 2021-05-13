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

<%@ include file="/dynamic_include/init.jsp" %>

<%
DLAudioFFMPEGAudioConverter dlAudioFFMPEGAudioConverter = (DLAudioFFMPEGAudioConverter)request.getAttribute(DLAudioFFMPEGAudioConverter.class.getName());
%>

<c:if test="<%= dlAudioFFMPEGAudioConverter.isEnabled() && !DLAudioFFMPEGUtil.isFFMPEGInstalled() %>">
	<aui:alert closeable="<%= false %>" type="danger">
		<liferay-ui:message arguments="https://ffmpeg.org/download.html" key="the-ffmpeg-executable-is-not-installed-on-your-system.-please-contact-your-administrator-to-install-it-following-the-instructions-in-x" />
	</aui:alert>
</c:if>