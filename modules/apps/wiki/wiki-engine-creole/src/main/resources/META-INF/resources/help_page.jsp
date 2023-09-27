<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:container-fluid>
	<h4>
		<liferay-ui:message key="text-styles" />
	</h4>

	<pre>
	//italics//
	**bold**
	</pre>

	<h4>
		<liferay-ui:message key="headers" />
	</h4>

	<pre>
	== Large heading ==
	=== Medium heading ===
	==== Small heading ====
	</pre>

	<h4>
		<liferay-ui:message key="links" />
	</h4>

	<pre>
	[[Link to a page]]
	[[http://www.liferay.com|Link to website]]
	</pre>

	<h4>
		<liferay-ui:message key="lists" />
	</h4>

	<pre>
	* Item
	** Subitem
	# Ordered Item
	## Ordered Subitem
	</pre>

	<h4>
		<liferay-ui:message key="images" />
	</h4>

	<pre>
	{{attached-image.png}}
	{{Page Name/other-image.jpg|label}}
	</pre>

	<h4>
		<liferay-ui:message key="other" />
	</h4>

	<pre>
	&lt;&lt;TableOfContents&gt;&gt;
	{{{ Preformatted }}}
	</pre>

	<%
	BaseWikiEngine baseWikiEngine = BaseWikiEngine.getBaseWikiEngine(request);
	%>

	<aui:a href="<%= baseWikiEngine.getHelpURL() %>" target="_blank"><liferay-ui:message key="learn-more" /> &raquo;</aui:a>
</clay:container-fluid>