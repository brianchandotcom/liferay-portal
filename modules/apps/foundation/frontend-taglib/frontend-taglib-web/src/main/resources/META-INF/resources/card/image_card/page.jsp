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

<%@ include file="/card/image_card/init.jsp" %>

<div class="taglib-vertical-card <%= cssClass %>" <%= AUIUtil.buildData(data) %>>
	<div>
		<div class="card">
			<div class="aspect-ratio aspect-ratio-bg-center aspect-ratio-bg-cover" style="<%= "background-image: url('" + imageUrl + "')" %>">
				<img alt="" class="<%= imageCSSClass %> sr-only" src="<%= imageUrl %>" />
			</div>
		</div>
	</div>
</div>