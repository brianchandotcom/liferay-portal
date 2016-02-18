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

<%@ include file="/wiki/init.jsp" %>

<h4>
	<liferay-ui:message key="text-styles" />
</h4>

<pre>
'quoted'
''italics''
'''bold'''
monospaced
</pre>

<h4>
	<liferay-ui:message key="headers" />
</h4>

<pre>
= Header 1 =
== Header 2 ==
=== Header 3 ===
</pre>

<h4>
	<liferay-ui:message key="links" />
</h4>

<pre>
CamelCaseWordsAreLinksToPages
[http://www.liferay.com Liferay's Website]
</pre>

<h4>
	<liferay-ui:message key="lists" />
</h4>

<pre>
<i class="icon-long-arrow-right"></i>* Item
<i class="icon-long-arrow-right"></i>&nbsp;<i class="icon-long-arrow-right"></i>* Subitem

<i class="icon-long-arrow-right"></i>1 Ordered Item
<i class="icon-long-arrow-right"></i>&nbsp;<i class="icon-long-arrow-right"></i>1 Ordered Subitem
</pre>