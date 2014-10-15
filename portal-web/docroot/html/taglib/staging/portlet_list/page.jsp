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

<%@ include file="/html/taglib/staging/portlet_list/init.jsp" %>

<ul class="portlet-list">

	<%
	for (Portlet portlet : portlets) {
		String portletDataHandlerClass = portlet.getPortletDataHandlerClass();

		if (portletDataHandlerClasses.contains(portletDataHandlerClass)) {
			continue;
		}

		portletDataHandlerClasses.add(portletDataHandlerClass);

		String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

		PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

		portletDataHandler.prepareManifestSummary(portletDataContext);

		long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);

		long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());

		boolean displayCounts = (exportModelCount != 0) || (modelDeletionCount != 0);

		if (!type.equals(Constants.EXPORT)) {
			UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

			displayCounts = displayCounts && GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(StagingUtil.getStagedPortletId(portlet.getRootPortletId())), portletDataHandler.isPublishToLiveByDefault());
		}

		boolean showPortletDataInput = MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId(), portletDataHandler.isPublishToLiveByDefault()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
	%>

	<c:if test="<%= displayCounts %>">
		<li class="tree-item">
			<liferay-util:buffer var="badgeHTML">
				<span class="badge badge-info"><%= exportModelCount > 0 ? exportModelCount : StringPool.BLANK %></span>

				<span class="badge badge-warning deletions"><%= modelDeletionCount > 0 ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(request, "deletions")) : StringPool.BLANK %></span>
			</liferay-util:buffer>

			<aui:input checked="<%= showPortletDataInput %>" disabled="<%= disableInputs %>" label="<%= portletTitle + badgeHTML %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>" type="checkbox" />

			<%
			PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
			PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

			if (ArrayUtil.isEmpty(exportControls) && ArrayUtil.isEmpty(metadataControls)) {
				continue;
			}
			%>

			<div class='<%= disableInputs && showPortletDataInput ? StringPool.BLANK : "hide " %>' id="<portlet:namespace />content_<%= portlet.getPortletId() %>">
				<ul class="lfr-tree list-unstyled">
					<li class="tree-item">
						<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">

							<%
							if (exportControls != null) {
								if (type.equals(Constants.EXPORT)) {
									request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
									request.setAttribute("render_controls.jsp-controls", exportControls);
									request.setAttribute("render_controls.jsp-disableInputs", disableInputs);
									request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
									request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
									request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
							%>

									<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
										<ul class="lfr-tree list-unstyled">
											<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
										</ul>
									</aui:field-wrapper>

							<%
								}
								else if (liveGroup.isStagedPortlet(portlet.getRootPortletId())) {
									request.setAttribute("render_controls.jsp-action", Constants.PUBLISH);
									request.setAttribute("render_controls.jsp-controls", exportControls);
									request.setAttribute("render_controls.jsp-disableInputs", disableInputs);
									request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
									request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
									request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
							%>

									<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
										<ul class="lfr-tree list-unstyled">
											<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
										</ul>
									</aui:field-wrapper>

							<%
								}
							}

							if (metadataControls != null) {
								for (PortletDataHandlerControl metadataControl : metadataControls) {
									if (displayedControls.contains(metadataControl.getControlName())) {
										continue;
									}

									displayedControls.add(metadataControl.getControlName());

									PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)metadataControl;

									PortletDataHandlerControl[] childrenControls = control.getChildren();

									if (ArrayUtil.isNotEmpty(childrenControls)) {
										request.setAttribute("render_controls.jsp-controls", childrenControls);
							%>

										<aui:field-wrapper label="content-metadata">
											<ul class="lfr-tree list-unstyled">
												<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
											</ul>
										</aui:field-wrapper>

							<%
									}
								}
							}
							%>

						</aui:fieldset>
					</li>
				</ul>
			</div>

			<%
			String portletId = portlet.getPortletId();

			if (!type.equals(Constants.EXPORT)) {
				portletId = portlet.getRootPortletId();
			}
			%>

			<ul class="hide" id="<portlet:namespace />showChangeContent_<%= portlet.getPortletId() %>">
				<li>
					<span class="selected-labels" id="<portlet:namespace />selectedContent_<%= portlet.getPortletId() %>"></span>

					<%
					Map<String,Object> data = new HashMap<String,Object>();

					data.put("portletid", portletId);
					data.put("portlettitle", portletTitle);
					%>

					<span <%= !disableInputs ? StringPool.BLANK : "class=\"hide\"" %>>
						<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + portlet.getPortletId() %>' label="change" method="get" />
					</span>
				</li>
			</ul>

			<aui:script>
				Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>', '<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + portlet.getPortletId() %>');
			</aui:script>
		</li>
	</c:if>

	<%
	}
	%>

</ul>

<aui:fieldset cssClass="content-options" label='<%= type.equals(Constants.EXPORT) ? "for-each-of-the-selected-content-types,-export-their" : "for-each-of-the-selected-content-types,-publish-their" %>'>
	<span class="selected-labels" id="<portlet:namespace />selectedContentOptions"></span>

	<span <%= !disableInputs ? StringPool.BLANK : "class=\"hide\"" %>>
		<aui:a cssClass="modify-link" href="javascript:;" id="contentOptionsLink" label="change" method="get" />
	</span>

	<div class="hide" id="<portlet:namespace />contentOptions">
		<ul class="lfr-tree list-unstyled">
			<li class="tree-item">
				<aui:input disabled="<%= disableInputs %>" label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.COMMENTS, true) %>" />

				<aui:input disabled="<%= disableInputs %>" label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.RATINGS, true) %>" />

				<%
				long modelDeletionCount = manifestSummary.getModelDeletionCount();
				%>

				<c:if test="<%= modelDeletionCount != 0 %>">

					<%
					String deletionsLabel = LanguageUtil.get(request, "deletions") + (modelDeletionCount > 0 ? " (" + modelDeletionCount + ")" : StringPool.BLANK);
					%>

					<aui:input checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS, !type.equals(Constants.EXPORT)) %>" data-name="<%= deletionsLabel %>" disabled="<%= disableInputs %>" helpMessage="deletions-help" label="<%= deletionsLabel %>" name="<%= PortletDataHandlerKeys.DELETIONS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS, false) %>" />
				</c:if>
			</li>
		</ul>
	</div>
</aui:fieldset>