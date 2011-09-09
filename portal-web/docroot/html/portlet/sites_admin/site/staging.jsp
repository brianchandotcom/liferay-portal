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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");
long liveGroupId = ((Long)request.getAttribute("site.liveGroupId")).longValue();
UnicodeProperties liveGroupTypeSettings = (UnicodeProperties)request.getAttribute("site.liveGroupTypeSettings");
%>

<c:choose>
	<c:when test="<%= GroupPermissionUtil.contains(permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING) %>">
		<liferay-ui:error exception="<%= SystemException.class %>">

			<%
			SystemException se = (SystemException)errorException;
			%>

			<liferay-ui:message key="<%= se.getMessage() %>" />
		</liferay-ui:error>

		<liferay-util:buffer var="stagingTypeHelp">
			<liferay-ui:message key="staging-type-help-1" />

			<ul>
				<li>
					<liferay-ui:message key="staging-type-help-2" />
				</li>
				<li>
					<liferay-ui:message key="staging-type-help-3" />
				</li>
			</ul>
		</liferay-util:buffer>

		<aui:select helpMessage="<%= stagingTypeHelp %>" label="staging-type" name="stagingType">
			<aui:option selected="<%= !liveGroup.isStaged() %>" value="<%= StagingConstants.TYPE_NOT_STAGED %>"><liferay-ui:message key="none" /></aui:option>
			<aui:option selected="<%= liveGroup.isStaged() && !liveGroup.isStagedRemotely() %>" value="<%= StagingConstants.TYPE_LOCAL_STAGING %>"><liferay-ui:message key="local-live" /></aui:option>
			<aui:option selected="<%= liveGroup.isStaged() && liveGroup.isStagedRemotely() %>" value="<%= StagingConstants.TYPE_REMOTE_STAGING %>"><liferay-ui:message key="remote-live" /></aui:option>
		</aui:select>

		<div class="<%= (liveGroup.isStaged() && liveGroup.isStagedRemotely() ? StringPool.BLANK : "aui-helper-hidden") %> staging-section" id="<portlet:namespace />remoteStagingOptions">
			<br />

			<liferay-ui:error exception="<%= RemoteExportException.class %>">

				<%
				RemoteExportException ree = (RemoteExportException)errorException;
				%>

				<c:if test="<%= ree.getType() == RemoteExportException.BAD_CONNECTION %>">
					<liferay-ui:message arguments="<%= ree.getURL() %>" key="there-was-a-bad-connection-with-the-remote-server-at-x" />
				</c:if>

				<c:if test="<%= ree.getType() == RemoteExportException.NO_GROUP %>">
					<liferay-ui:message arguments="<%= ree.getGroupId() %>" key="no-site-exists-on-the-remote-server-with-site-id-x" />
				</c:if>
			</liferay-ui:error>

			<aui:fieldset label="remote-live-connection-settings">
				<liferay-ui:error exception="<%= RemoteOptionsException.class %>">

					<%
					RemoteOptionsException roe = (RemoteOptionsException)errorException;
					%>

					<c:if test="<%= roe.getType() == RemoteOptionsException.REMOTE_ADDRESS %>">
						<liferay-ui:message arguments="<%= roe.getRemoteAddress() %>" key="the-remote-address-x-is-not-valid" />
					</c:if>

					<c:if test="<%= roe.getType() == RemoteOptionsException.REMOTE_GROUP_ID %>">
						<liferay-ui:message arguments="<%= roe.getRemoteGroupId() %>" key="the-remote-site-id-x-is-not-valid" />
					</c:if>

					<c:if test="<%= roe.getType() == RemoteOptionsException.REMOTE_PORT %>">
						<liferay-ui:message arguments="<%= roe.getRemotePort() %>" key="the-remote-port-x-is-not-valid" />
					</c:if>
				</liferay-ui:error>

				<div class="portlet-msg-info">
					<liferay-ui:message key="remote-publish-help" />
				</div>

				<aui:input label="remote-host-ip" name="remoteAddress" size="20" type="text" value='<%= liveGroupTypeSettings.getProperty("remoteAddress") %>' />

				<aui:input label="port" name="remotePort" size="10" type="text" value='<%= liveGroupTypeSettings.getProperty("remotePort") %>' />

				<aui:input label='<%= LanguageUtil.get(pageContext, "remote-site-id" ) %>' name="remoteGroupId" size="10" type="text" value='<%= liveGroupTypeSettings.getProperty("remoteGroupId") %>' />

				<aui:input inlineLabel="left" label="use-a-secure-network-connection" name="secureConnection" type="checkbox" value='<%= liveGroupTypeSettings.getProperty("secureConnection") %>' />
			</aui:fieldset>
		</div>

		<div class="<%= (liveGroup.isStaged() ? StringPool.BLANK : "aui-helper-hidden") %> staging-section" id="<portlet:namespace />stagedPortlets">
			<br />

			<aui:fieldset helpMessage="page-versioning-help" label="page-versioning">
				<aui:input label="enabled-on-public-pages" name="branchingPublic" type="checkbox" value='<%= GetterUtil.getBoolean(liveGroupTypeSettings.getProperty("branchingPublic")) %>' />

				<aui:input label="enabled-on-private-pages" name="branchingPrivate" type="checkbox" value='<%= GetterUtil.getBoolean(liveGroupTypeSettings.getProperty("branchingPrivate")) %>' />
			</aui:fieldset>

			<aui:fieldset helpMessage="staged-portlets-help" label="staged-portlets">
				<div class="portlet-msg-alert">
					<liferay-ui:message key="staged-portlets-alert" />
				</div>

				<div class="portlet-msg-info">
					<liferay-ui:message key="always-exported-portlets-help" />
				</div>

				<%
				Set<String> portletDataHandlerClasses = new HashSet<String>();

				List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(company.getCompanyId());

				portlets = ListUtil.sort(portlets, new PortletTitleComparator(application, locale));

				for (Portlet curPortlet : portlets) {
					if (!curPortlet.isActive()) {
						continue;
					}

					String portletDataHandlerClass = curPortlet.getPortletDataHandlerClass();

					if (!portletDataHandlerClasses.contains(portletDataHandlerClass)) {
						portletDataHandlerClasses.add(portletDataHandlerClass);
					}
					else {
						continue;
					}

					PortletDataHandler portletDataHandler = curPortlet.getPortletDataHandlerInstance();

					if (portletDataHandler == null) {
						continue;
					}

					boolean isStaged = GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(StagingConstants.STAGED_PORTLET + curPortlet.getRootPortletId()), portletDataHandler.isPublishToLiveByDefault());

					String includedInEveryPublish = StringPool.BLANK;

					if (portletDataHandler.isAlwaysExportable()) {
						includedInEveryPublish = " (*)";
					}
				%>

					<aui:input inlineLabel="right" label="<%= PortalUtil.getPortletTitle(curPortlet, application, locale) + includedInEveryPublish %>" name='<%= StagingConstants.STAGED_PORTLET + curPortlet.getRootPortletId() %>' type="checkbox" value="<%= isStaged %>" />

				<%
				}
				%>

			</aui:fieldset>
		</div>

		<aui:script>
			Liferay.provide(
				Liferay.Util,
				'toggleSelectBoxReverse',
				function(selectBoxId, value, toggleBoxId) {
					var A = AUI();

					var selectBox = A.one('#' + selectBoxId);
					var toggleBox = A.one('#' + toggleBoxId);

					if (selectBox && toggleBox) {
						var toggle = function() {
							var action = 'hide';

							if (selectBox.val() != value) {
								action = 'show';
							}

							toggleBox[action]();
						};

						toggle();

						selectBox.on('change', toggle);
					}
				},
				['aui-base']
			);

			Liferay.provide(
				Liferay.Util,
				'toggleSelectBoxCustom',
				function(selectBoxId, toggleBoxId) {
					var A = AUI();

					var selectBox = A.one('#' + selectBoxId);
					var toggleBox0 = A.one('#' + toggleBoxId + '0');
					var toggleBox1 = A.one('#' + toggleBoxId + '1');
					var toggleBox2 = A.one('#' + toggleBoxId + '2');
					var toggleBox3 = A.one('#' + toggleBoxId + '3');
					var toggleBox4 = A.one('#' + toggleBoxId + '4');

					if (selectBox) {
						var toggle = function() {
							if (selectBox.val() == '1') {
								toggleBox0['hide']();
								toggleBox1['hide']();
								toggleBox2['hide']();
								toggleBox3['hide']();
								toggleBox4['hide']();
							}
							else if (selectBox.val() == '2') {
								toggleBox0['show']();
								toggleBox1['show']();
								toggleBox2['hide']();
								toggleBox3['hide']();
								toggleBox4['hide']();
							}
							else if (selectBox.val() == '3') {
								toggleBox0['show']();
								toggleBox1['show']();
								toggleBox2['show']();
								toggleBox3['hide']();
								toggleBox4['hide']();
							}
							else if (selectBox.val() == '4') {
								toggleBox0['show']();
								toggleBox1['show']();
								toggleBox2['show']();
								toggleBox3['show']();
								toggleBox4['hide']();
							}
							else if (selectBox.val() == '5') {
								toggleBox0['show']();
								toggleBox1['show']();
								toggleBox2['show']();
								toggleBox3['show']();
								toggleBox4['show']();
							}
						};

						toggle();

						selectBox.on('change', toggle);
					}
				},
				['aui-base']
			);

			Liferay.Util.toggleSelectBoxReverse('<portlet:namespace />stagingType','<%= StagingConstants.TYPE_NOT_STAGED %>','<portlet:namespace />stagedPortlets');
			Liferay.Util.toggleSelectBoxReverse('<portlet:namespace />stagingType','<%= StagingConstants.TYPE_NOT_STAGED %>','<portlet:namespace />stagingOptions');
			Liferay.Util.toggleSelectBoxReverse('<portlet:namespace />stagingType','<%= StagingConstants.TYPE_NOT_STAGED %>','<portlet:namespace />advancedOptions');
			Liferay.Util.toggleSelectBox('<portlet:namespace />stagingType','<%= StagingConstants.TYPE_REMOTE_STAGING %>','<portlet:namespace />remoteStagingOptions');
		</aui:script>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="you-do-not-have-permission-to-manage-settings-related-to-staging" />
		</div>
	</c:otherwise>
</c:choose>