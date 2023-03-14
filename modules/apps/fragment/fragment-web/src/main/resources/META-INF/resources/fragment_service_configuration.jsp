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

<%
FragmentServiceConfigurationDisplayContext fragmentServiceConfigurationDisplayContext = (FragmentServiceConfigurationDisplayContext)request.getAttribute(FragmentServiceConfigurationDisplayContext.class.getName());
%>

<aui:form action="<%= fragmentServiceConfigurationDisplayContext.getEditFragmentServiceConfigurationURL() %>" method="post" name="fm">
	<clay:sheet>
		<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>" message="there-was-an-unknown-error" />

		<clay:sheet-header>
			<h2>
				<liferay-ui:message key="fragment-configuration-name" />
			</h2>
		</clay:sheet-header>

		<clay:sheet-section>
			<div>
				<span aria-hidden="true" class="loading-animation"></span>

				<react:component
					module="js/FragmentServiceConfiguration"
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
		</clay:sheet-section>

		<clay:sheet-footer>
			<aui:button primary="<%= true %>" type="submit" value="save" />

			<aui:button type="cancel" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>