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

<div class="row text-center">
	<div class="col-md-1">
		<clay:badge
			label="8"
		/>

		<div>Primary</div>
	</div>

	<div class="col-md-1">
		<clay:badge
			label="87"
			style="secondary"
		/>

		<div>Secondary</div>
	</div>

	<div class="col-md-1">
		<clay:badge
			label="91"
			style="info"
		/>

		<div>Info</div>
	</div>

	<div class="col-md-1">
		<clay:badge
			label="130"
			style="danger"
		/>

		<div>Error</div>
	</div>

	<div class="col-md-1">
		<clay:badge
			label="1111"
			style="success"
		/>

		<div>Success</div>
	</div>

	<div class="col-md-1">
		<clay:badge
			label="21"
			style="warning"
		/>

		<div>Warning</div>
	</div>
</div>