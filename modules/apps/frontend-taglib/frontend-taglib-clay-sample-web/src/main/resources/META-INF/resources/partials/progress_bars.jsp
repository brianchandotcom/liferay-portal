<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<blockquote>
	<p>
		Progress bar is a progress indicator used to show the completion
		percentage of a task.
	</p>
</blockquote>

<clay:progressbar
	value="<%= 30 %>"
/>

<clay:progressbar
	value="<%= 70 %>"
	warn="<%= true %>"
/>

<clay:progressbar
	value="<%= 100 %>"
/>