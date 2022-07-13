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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.search.web.internal.search.bar.portlet.display.context.SearchBarPortletInstanceConfigurationDisplayContext" %>

<portlet:defineObjects />

<%
SearchBarPortletInstanceConfigurationDisplayContext searchBarPortletInstanceConfigurationDisplayContext = (SearchBarPortletInstanceConfigurationDisplayContext)request.getAttribute(SearchBarPortletInstanceConfigurationDisplayContext.class.getName());
%>

<aui:input name="displayStyleGroupId" value="<%= searchBarPortletInstanceConfigurationDisplayContext.getDisplayStyleGroupId() %>" />

<aui:input name="displayStyle" value="<%= searchBarPortletInstanceConfigurationDisplayContext.getDisplayStyle() %>" />

<c:if test="<%= searchBarPortletInstanceConfigurationDisplayContext.isSuggestionsConfigurationVisible() %>">
	<aui:input name="enableSuggestions" type="checkbox" value="<%= searchBarPortletInstanceConfigurationDisplayContext.isEnableSuggestions() %>" />

	<div>
		<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>

		<react:component
			module="js/components/SystemSettingsFieldList"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"fieldLabel", LanguageUtil.get(request, "suggestions-contributor-configuration")
				).put(
					"fieldName", "suggestionsContributorConfigurations"
				).put(
					"initialValue", searchBarPortletInstanceConfigurationDisplayContext.getSuggestionsContributorConfigurations()
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).build()
			%>'
		/>
	</div>

	<aui:input label="character-threshold-for-displaying-suggestions" name="suggestionsDisplayThreshold" value="<%= searchBarPortletInstanceConfigurationDisplayContext.getSuggestionsDisplayThreshold() %>" />
</c:if>