<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<div class="h4">
	<liferay-ui:message key="text-styles" />
</div>

<pre>
'quoted'
''italics''
'''bold'''
monospaced
</pre>

<div class="h4">
	<liferay-ui:message key="headers" />
</div>

<pre>
= Header 1 =
== Header 2 ==
=== Header 3 ===
</pre>

<div class="h4">
	<liferay-ui:message key="links" />
</div>

<pre>
CamelCaseWordsAreLinksToPages
[http://www.liferay.com Liferay's Website]
</pre>

<div class="h4">
	<liferay-ui:message key="lists" />
</div>

<pre>
<i class="icon-long-arrow-right"></i>* Item
<i class="icon-long-arrow-right"></i>&nbsp;<i class="icon-long-arrow-right"></i>* Subitem

<i class="icon-long-arrow-right"></i>1 Ordered Item
<i class="icon-long-arrow-right"></i>&nbsp;<i class="icon-long-arrow-right"></i>1 Ordered Subitem
</pre>