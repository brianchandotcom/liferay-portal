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

<blockquote>
	<p>Labels are a mechanism to categorize information providing quick recognition.</p>
</blockquote>

<clay:row
	className="mb-3"
>
	<clay:col
		size="2"
	>
		<div>
			<clay:label label="Label text" displayType="info" />
		</div>

		<div>
			<clay:label label="Label text" large="true" displayType="info" />
		</div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label label="Status" /></div>
		<div><clay:label label="Status" large="true" /></div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label label="Pending" displayType="warning" /></div>
		<div><clay:label label="Pending" large="true" displayType="warning" /></div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label label="Rejected" displayType="danger" /></div>
		<div><clay:label label="Rejected" large="true" displayType="danger" /></div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label label="Approved" displayType="success" /></div>
		<div><clay:label label="Approved" large="true" displayType="success" /></div>
	</clay:col>
</clay:row>

<h3>LABEL REMOVABLE</h3>

<clay:row
	className="row"
>
	<clay:col
		size="12"
	>
		<clay:label
			dismissible="<%= true %>"
			label="Normal Label"
		/>

		<clay:label
			dismissible="<%= true %>"
			label="Large Label"
			large="true"
			displayType="success"
		/>
	</clay:col>
</clay:row>

<h3>LABEL WITH LINK</h3>

<clay:row>
	<clay:col
		size="12"
	>
		<clay:label
			href="#"
			label="Label Text"
		/>
	</clay:col>
</clay:row>