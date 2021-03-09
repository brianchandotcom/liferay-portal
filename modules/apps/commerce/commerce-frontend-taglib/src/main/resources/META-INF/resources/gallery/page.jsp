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

<%@ include file="/gallery/init.jsp" %>

<div class="gallery-root" id="<%= galleryId %>"></div>

<aui:script require="commerce-frontend-js/components/gallery/entry as gallery">
	gallery.default('<%= galleryId %>', '<%= galleryId %>', {
		images: <%= jsonSerializer.serializeDeep(images) %>,
		namespace: '<%= namespace %>',
		viewCPAttachmentURL: '<%= viewCPAttachmentURL %>',
		portletId: '<%= portletDisplay.getRootPortletId() %>',
	});
</aui:script>