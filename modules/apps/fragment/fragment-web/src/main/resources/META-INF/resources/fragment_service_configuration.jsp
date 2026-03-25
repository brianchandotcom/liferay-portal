<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FragmentServiceConfigurationDisplayContext fragmentServiceConfigurationDisplayContext = (FragmentServiceConfigurationDisplayContext)request.getAttribute(FragmentServiceConfigurationDisplayContext.class.getName());
%>

<div class="form-group">
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		module="{FragmentServiceConfiguration} from fragment-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"alreadyPropagateContributedFragmentChanges", fragmentServiceConfigurationDisplayContext.isAlreadyPropagateContributedFragmentChanges()
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"propagateChanges", fragmentServiceConfigurationDisplayContext.isPropagateChangesEnabled()
			).put(
				"propagateContributedFragmentChanges", fragmentServiceConfigurationDisplayContext.isPropagateContributedFragmentChangesEnabled()
			).put(
				"propagateContributedFragmentEntriesChangesURL", fragmentServiceConfigurationDisplayContext.getPropagateContributedFragmentEntriesChangesURL()
			).build()
		%>'
	/>
</div>