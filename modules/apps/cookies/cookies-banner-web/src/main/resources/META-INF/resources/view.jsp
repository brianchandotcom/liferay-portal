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

<clay:container-fluid
	cssClass="container-view"
>
	<liferay-ui:breadcrumb
		showLayout="<%= false %>"
	/>

	<clay:row>
		<clay:col
			lg="3"
		>
			<clay:content-row
				cssClass="mb-4"
				verticalAlign="center"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<strong class="text-uppercase">
						<h4>Cookies!!!</h4>
					</strong>
				</clay:content-col>
			</clay:content-row>
		</clay:col>
	</clay:row>
</clay:container-fluid>