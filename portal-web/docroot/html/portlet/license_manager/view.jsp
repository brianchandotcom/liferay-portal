<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<iframe id="<portlet:namespace />iframe" src="/c/portal/license?p_p_state=pop_up" scrolling="no" style="border: none; width: 100%;"></iframe>

<script type="text/javascript">
	parent.window.document.getElementById("<portlet:namespace />iframe").height = document.body.offsetHeight;
</script>