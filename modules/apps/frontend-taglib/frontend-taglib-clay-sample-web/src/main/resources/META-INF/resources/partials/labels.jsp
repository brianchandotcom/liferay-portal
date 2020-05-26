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
	cssClass="mb-3"
>
	<clay:col
		size="2"
	>
		<div>
			<clay:label
				displayType="info"
				label="Label text"
			/>
		</div>

		<div>
			<clay:label
				displayType="info"
				label="Label text"
				large="true"
			/>
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
		<div><clay:label displayType="warning" label="Pending" /></div>
		<div><clay:label displayType="warning" label="Pending" large="true" /></div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label displayType="danger" label="Rejected" /></div>
		<div><clay:label displayType="danger" label="Rejected" large="true" /></div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label displayType="success" label="Approved" /></div>
		<div><clay:label displayType="success" label="Approved" large="true" /></div>
	</clay:col>
</clay:row>

<h3>LABEL REMOVABLE</h3>

<clay:row
	cssClass="row"
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
			displayType="success"
			label="Large Label"
			large="true"
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