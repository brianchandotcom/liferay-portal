<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:container-fluid>
	<div class="h4">
		<liferay-ui:message key="text-styles" />
	</div>

	<pre>
	//italics//
	**bold**
	</pre>

	<div class="h4">
		<liferay-ui:message key="headers" />
	</div>

	<pre>
	== Large heading ==
	=== Medium heading ===
	==== Small heading ====
	</pre>

	<div class="h4">
		<liferay-ui:message key="links" />
	</div>

	<pre>
	[[Link to a page]]
	[[http://www.liferay.com|Link to website]]
	</pre>

	<div class="h4">
		<liferay-ui:message key="lists" />
	</div>

	<pre>
	* Item
	** Subitem
	# Ordered Item
	## Ordered Subitem
	</pre>

	<div class="h4">
		<liferay-ui:message key="images" />
	</div>

	<pre>
	{{attached-image.png}}
	{{Page Name/other-image.jpg|label}}
	</pre>

	<div class="h4">
		<liferay-ui:message key="other" />
	</div>

	<pre>
	&lt;&lt;TableOfContents&gt;&gt;
	{{{ Preformatted }}}
	</pre>

	<%
	BaseWikiEngine baseWikiEngine = BaseWikiEngine.getBaseWikiEngine(request);
	%>

	<aui:a href="<%= baseWikiEngine.getHelpURL() %>" target="_blank"><liferay-ui:message key="learn-more" /> &raquo;</aui:a>
</clay:container-fluid>