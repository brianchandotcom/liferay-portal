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

<%@ include file="/dynamic_include/init.jsp" %>

<script src="//code.tidio.co/<%= clickToChatProviderAccountId %>.js" async></script>

<script type="text/javascript">
	var _tn = _tn || [];
	_tn.push(['account','<%= clickToChatProviderAccountId %>']);
	_tn.push(['action','track-view']);

	<c:if test="<%= themeDisplay.isSignedIn() %>">
		_tn.push(['_setName','<%= user.getScreenName() %>']);
		_tn.push(['_setEmail','<%= user.getEmailAddress() %>']);
	</c:if>

	(function() {
	document.write(unescape("%3Cspan id='tolvnow'%3E%3C/span%3E"));
	var tss = document.createElement('script'); tss.type = 'text/javascript'; tss.async = true;
	tss.src = '//tracker.tolvnow.com/js/tn.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(tss, s);
	})();
</script>