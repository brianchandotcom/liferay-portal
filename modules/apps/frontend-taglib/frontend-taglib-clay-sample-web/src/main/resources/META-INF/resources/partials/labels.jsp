<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				large="<%= true %>"
			/>
		</div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label label="Status" /></div>
		<div><clay:label label="Status" large="<%= true %>" /></div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label displayType="warning" label="Pending" /></div>
		<div><clay:label displayType="warning" label="Pending" large="<%= true %>" /></div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label displayType="danger" label="Rejected" /></div>
		<div><clay:label displayType="danger" label="Rejected" large="<%= true %>" /></div>
	</clay:col>

	<clay:col
		size="2"
	>
		<div><clay:label displayType="success" label="Approved" /></div>
		<div><clay:label displayType="success" label="Approved" large="<%= true %>" /></div>
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
			large="<%= true %>"
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