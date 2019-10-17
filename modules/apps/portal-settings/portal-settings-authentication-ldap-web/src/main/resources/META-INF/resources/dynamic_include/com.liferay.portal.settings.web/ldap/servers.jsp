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

<%
ConfigurationProvider<LDAPAuthConfiguration> ldapAuthConfigurationProvider = ConfigurationProviderUtil.getLDAPAuthConfigurationProvider();

LDAPAuthConfiguration ldapAuthConfiguration = ldapAuthConfigurationProvider.getConfiguration(themeDisplay.getCompanyId());

ConfigurationProvider<LDAPServerConfiguration> ldapServerConfigurationProvider = ConfigurationProviderUtil.getLDAPServerConfigurationProvider();

List<LDAPServerConfiguration> ldapServerConfigurations = ldapServerConfigurationProvider.getConfigurations(themeDisplay.getCompanyId(), false);

String authenticationURL = currentURL + "#_LFR_FN_authentication";

boolean ldapAuthEnabled = ldapAuthConfiguration.enabled();
%>

<c:if test="<%= ldapAuthEnabled && ldapServerConfigurations.isEmpty() %>">
	<div class="alert alert-info">
		<liferay-ui:message key="default-ldap-server-settings-are-in-use-please-add-an-ldap-server-to-override-the-default-settings" />
	</div>
</c:if>

<aui:button-row>

	<%
	PortletURL addServerURL = renderResponse.createRenderURL();

	addServerURL.setParameter("mvcRenderCommandName", "/portal_settings/edit_ldap_server");
	addServerURL.setParameter("redirect", authenticationURL);
	%>

	<aui:button href="<%= addServerURL.toString() %>" name="addButton" value="add" />
</aui:button-row>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= LDAPSettingsConstants.CMD_UPDATE_SERVER %>" />

	<aui:input name='<%= "ldap--" + LDAPConstants.AUTH_SERVER_PRIORITY + "--" %>' type="hidden" />

	<c:if test="<%= !ldapServerConfigurations.isEmpty() %>">
		<br /><br />

		<div class="ldap-servers searchcontainer-content">
			<table class="table table-bordered table-hover table-striped">
				<thead class="table-columns">
					<tr>
						<th class="table-header">
							<liferay-ui:message key="ldap-server-id" />
						</th>
						<th class="table-header">
							<liferay-ui:message key="ldap-server-name" />
						</th>
						<th class="table-header"></th>
					</tr>
				</thead>

				<tbody class="table-data">

					<%
					for (LDAPServerConfiguration ldapServerConfiguration : ldapServerConfigurations) {
						long ldapServerId = ldapServerConfiguration.ldapServerId();

						String ldapServerName = ldapServerConfiguration.serverName();
					%>

						<tr data-ldapServerId="<%= ldapServerId %>">
							<td class="table-cell">
								<%= ldapServerId %>
							</td>
							<td class="table-cell">
								<%= HtmlUtil.escape(ldapServerName) %>
							</td>
							<td align="right" class="table-cell">
								<div class="control">
									<c:if test="<%= ldapServerConfigurations.size() > 1 %>">
										<liferay-ui:icon
											icon="order-arrow-up"
											markupView="lexicon"
											message="up"
											url='<%= "javascript:" + renderResponse.getNamespace() + "raiseLDAPServerPriority(" + ldapServerId + ");" %>'
										/>

										<liferay-ui:icon
											icon="order-arrow-down"
											markupView="lexicon"
											message="down"
											url='<%= "javascript:" + renderResponse.getNamespace() + "lowerLDAPServerPriority(" + ldapServerId + ");" %>'
										/>
									</c:if>

									<portlet:renderURL var="editURL">
										<portlet:param name="mvcRenderCommandName" value="/portal_settings/edit_ldap_server" />
										<portlet:param name="redirect" value="<%= authenticationURL %>" />
										<portlet:param name="ldapServerId" value="<%= String.valueOf(ldapServerId) %>" />
									</portlet:renderURL>

									<liferay-ui:icon
										icon="pencil"
										markupView="lexicon"
										message="edit"
										url="<%= editURL %>"
									/>

									<portlet:actionURL name="/portal_settings/edit_ldap_server" var="deleteURL">
										<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
										<portlet:param name="redirect" value="<%= authenticationURL %>" />
										<portlet:param name="ldapServerId" value="<%= String.valueOf(ldapServerId) %>" />
									</portlet:actionURL>

									<liferay-ui:icon-delete
										showIcon="<%= true %>"
										url="<%= deleteURL %>"
									/>
								</div>
							</td>
						</tr>

					<%
					}
					%>

				</tbody>
			</table>
		</div>
	</c:if>
</aui:fieldset>

<script>
	function <portlet:namespace />changeLDAPServerPriority(ldapServerId, action) {
		var ldapServer = document.querySelector(
			'.ldap-servers tr[data-ldapServerId="' + ldapServerId + '"]'
		);

		if (ldapServer) {
			var swapLdapServer = ldapServer.nextElementSibling;

			if (action === 'raise') {
				swapLdapServer = ldapServer.previousElementSibling;
			}

			if (swapLdapServer) {
				var parentNode = ldapServer.parentNode;

				if (action === 'raise') {
					parentNode.insertBefore(ldapServer, swapLdapServer);
				} else {
					parentNode.insertBefore(swapLdapServer, ldapServer);
				}
			}
		}

		<portlet:namespace />saveLdap();
	}

	function <portlet:namespace />lowerLDAPServerPriority(ldapServerId) {
		<portlet:namespace />changeLDAPServerPriority(ldapServerId, 'lower');
	}

	function <portlet:namespace />raiseLDAPServerPriority(ldapServerId) {
		<portlet:namespace />changeLDAPServerPriority(ldapServerId, 'raise');
	}

	function <portlet:namespace />saveLdap() {
		var ldapServerIdsNodes = document.querySelectorAll(
			'.ldap-servers .table-data tr'
		);

		var ldapServerIds = Array.prototype.map.call(ldapServerIdsNodes, function(
			ldapServerIdsNode
		) {
			return ldapServerIdsNode.dataset.ldapserverid;
		});

		Liferay.Util.setFormValues(document.<portlet:namespace />fm, {
			'ldap--<%= LDAPConstants.AUTH_SERVER_PRIORITY %>--': ldapServerIds.join(
				','
			)
		});
	}

	Liferay.Util.toggleBoxes(
		'<portlet:namespace />ldapImportEnabled',
		'<portlet:namespace />importEnabledSettings'
	);
</script>