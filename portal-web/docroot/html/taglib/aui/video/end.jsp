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

<%@ include file="/html/taglib/aui/video/init.jsp" %>

</div>

<aui:script use="aui-video">
	new A.Video(
		{
			boundingBox: '#video-id',
			width: <%= width %>,
			height: <%= height %>,
			url: '<%= url %>',
			ogvUrl: '<%= ogvUrl %>',
			swfUrl: '<%= swfUrl %>',
			poster: '<%= poster %>',
			cssClass: null,
			flashPlayerVersion: '9,0,0,0',
			render: true
		}
	);
</aui:script>

