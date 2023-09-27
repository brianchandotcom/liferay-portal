<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<h3>EMBEDDED</h3>

<blockquote>
	<p>Embedded alerts are thought to be used inside context as forms. Usually you will only need to use the information one. Its width depends on the container with you use it, always respecting the container margins to the content. The close action is not of mandatory use.</p>
</blockquote>

<clay:alert
	displayType="danger"
	message="This is an error message."
	title="Error"
/>

<clay:alert
	displayType="success"
	message="This is a success message."
	title="Success"
/>

<clay:alert
	message="This is an info message."
	title="Info"
/>

<clay:alert
	displayType="warning"
	message="This is a warning message."
	title="Warning"
/>

<clay:alert
	displayType="secondary"
	message="This is a secondary message."
	title="Secondary"
/>

<h3>STRIPE</h3>

<blockquote>
	<p>Stripe alerts are always placed below the last navigation element, either the header or the navigation bar. This alert appears usually on "Save" action communicating the status of the action once received from the server. The close action is mandatory in this alert type. Its width is always full container width and pushes all the content below it.</p>
</blockquote>

<clay:stripe
	dismissible="<%= true %>"
	displayType="danger"
	message="This is an error message."
	title="Error"
/>

<clay:stripe
	dismissible="<%= true %>"
	displayType="success"
	message="This is a success message."
	title="Success"
/>

<clay:stripe
	dismissible="<%= true %>"
	message="This is an info message."
	title="Info"
/>

<clay:stripe
	dismissible="<%= true %>"
	displayType="warning"
	message="This is a warning message."
	title="Warning"
/>

<clay:stripe
	dismissible="<%= true %>"
	displayType="secondary"
	message="This is a secondary message."
	title="Secondary"
/>

<div>
	<react:component
		module="js/ClaySampleToastAlert"
	/>
</div>