<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dictionary/init.jsp" %>

<form name="<portlet:namespace />fm" onSubmit="window.open(document.<portlet:namespace />fm.<portlet:namespace />type[document.<portlet:namespace />fm.<portlet:namespace />type.selectedIndex].value + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />word.value)); return false;">

<input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="<portlet:namespace />word" size="30" type="text" />

<select name="<portlet:namespace />type">
	<option value="http://dictionary.reference.com/search?q="><liferay-ui:message key="dictionary" /></option>
	<option value="http://thesaurus.reference.com/search?q="><liferay-ui:message key="thesaurus" /></option>
</select>

<input type="submit" value="<liferay-ui:message key="find" />" />

</form>