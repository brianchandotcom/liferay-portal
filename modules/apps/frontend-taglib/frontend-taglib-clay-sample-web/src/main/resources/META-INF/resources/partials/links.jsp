<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<blockquote>
	<p>A link (also known as hyperlink) is a clickable (text or image) element. The link control is used for navigation.</p>
</blockquote>

<h3>SINGLE LINK</h3>

<blockquote>
	<p>Used for stand-alone hyperlinks. Can be a text or an image.</p>
</blockquote>

<clay:link
	data-custom-property="customValue"
	href="#"
	label="link text"
	target="_blank"
/>

<clay:link
	displayType="primary"
	href="#"
	label="a button link"
	rel="nofollow"
	target="_blank"
	type="button"
/>