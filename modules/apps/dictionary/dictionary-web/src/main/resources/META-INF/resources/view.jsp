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

<%@ include file="/init.jsp" %>

<aui:form cssClass="form-inline" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "lookUp();" %>'>
	<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" inlineField="<%= true %>" label="" name="word" />

	<aui:select inlineField="<%= true %>" label="" name="type">
		<aui:option label="dictionary" value="http://dictionary.reference.com/browse/" />
		<aui:option label="thesaurus" value="http://thesaurus.reference.com/browse/" />
	</aui:select>

	<aui:button type="submit" value="find" />
</aui:form>

<aui:script>
	function <portlet:namespace />lookUp() {
		var form = document.<portlet:namespace />fm;

		var type = form.<portlet:namespace />type.selectedIndex;
		var word = form.<portlet:namespace />word.value;

		window.open(
			form.<portlet:namespace />type[type].value + encodeURIComponent(word)
		);
	}
</aui:script>