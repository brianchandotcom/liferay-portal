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
	<p>Badges help highlight important information such as notifications or new and unread messages. Badges have circular borders and are only used to specify a number.</p>
</blockquote>

<clay:row
	cssClass="text-center"
>
	<clay:col
		md="1"
	>
		<clay:badge
			label="8"
		/>

		<div>Primary</div>
	</clay:col>

	<clay:col
		md="1"
	>
		<clay:badge
			displayType="secondary"
			label="87"
		/>

		<div>Secondary</div>
	</clay:col>

	<clay:col
		md="1"
	>
		<clay:badge
			displayType="info"
			label="91"
		/>

		<div>Info</div>
	</clay:col>

	<clay:col
		md="1"
	>
		<clay:badge
			displayType="danger"
			label="130"
		/>

		<div>Error</div>
	</clay:col>

	<clay:col
		md="1"
	>
		<clay:badge
			displayType="success"
			label="1111"
		/>

		<div>Success</div>
	</clay:col>

	<clay:col
		md="1"
	>
		<clay:badge
			displayType="warning"
			label="21"
		/>

		<div>Warning</div>
	</clay:col>
</clay:row>