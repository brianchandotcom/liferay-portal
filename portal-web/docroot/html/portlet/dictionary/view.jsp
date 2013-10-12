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

<aui:form name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "lookUp();" %>'>
	<aui:fieldset>
		<aui:field-wrapper>
			<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" label="" name="word" />

			<aui:select label="" name="type">
				<option value="http://dictionary.reference.com/browse/" /><liferay-ui:message key="dictionary" /></option>
				<option value="http://thesaurus.reference.com/browse/" /><liferay-ui:message key="thesaurus" /></option>
			</aui:select>

			<aui:button type="submit" value="find" />
		</aui:field-wrapper>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />lookUp() {
		var form = document.<portlet:namespace />fm;

		var type = form.<portlet:namespace />type.selectedIndex;
		var word = form.<portlet:namespace />word.value;

		window.open(form.<portlet:namespace />type[type].value + encodeURIComponent(word));
	}
</aui:script>