<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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