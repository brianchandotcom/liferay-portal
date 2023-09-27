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

<aui:form action="<%= fragmentServiceConfigurationDisplayContext.getEditFragmentServiceConfigurationURL() %>" method="post" name="fm">
	<clay:sheet
		size="full"
	>
		<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>" message="there-was-an-unknown-error" />

		<clay:sheet-header>
			<h2>
				<liferay-ui:message key="fragment-configuration-name" />
			</h2>

			<c:if test="<%= fragmentServiceConfigurationDisplayContext.showInfoMessage() %>">
				<clay:alert
					message="this-configuration-is-not-saved-yet.-the-values-shown-are-the-default"
				/>
			</c:if>
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
			<clay:button
				displayType="primary"
				label="save"
				type="submit"
			/>

			<clay:link
				displayType="secondary"
				href="<%= fragmentServiceConfigurationDisplayContext.getRedirect() %>"
				label="cancel"
				type="button"
			/>
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>