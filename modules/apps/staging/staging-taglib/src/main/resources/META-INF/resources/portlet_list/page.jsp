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

<%@ include file="/portlet_list/init.jsp" %>

<ul class="portlet-list">

	<%
	DateRange dateRange = null;

	for (Portlet portlet : portlets) {
		if (!type.equals(Constants.EXPORT) && (liveGroup != null) && !liveGroup.isStagedPortlet(portlet.getRootPortletId())) {
			continue;
		}

		PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

		Class<?> portletDataHandlerClass = portletDataHandler.getClass();

		String portletDataHandlerClassName = portletDataHandlerClass.getName();

		if (portletDataHandlerClassNames.contains(portletDataHandlerClassName)) {
			continue;
		}

		portletDataHandlerClassNames.add(portletDataHandlerClassName);

		String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

		PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
		PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();
		PortletDataHandlerControl[] stagingControls = portletDataHandler.getStagingControls();

		if (!type.equals(Constants.EXPORT) && liveGroup.isStagedPortlet(portlet.getRootPortletId())) {
			exportControls = stagingControls;
		}

		if (ArrayUtil.isEmpty(exportControls) && ArrayUtil.isEmpty(metadataControls)) {
			continue;
		}

		if (useRequestValues) {
			dateRange = ExportImportDateUtil.getDateRange(renderRequest, exportGroupId, privateLayout, 0, portlet.getRootPortletId(), defaultRange);
		}
		else {
			dateRange = ExportImportDateUtil.getDateRange(exportImportConfiguration, portlet.getRootPortletId());
		}

		PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(company.getCompanyId(), exportGroupId, (range != null) ? range : defaultRange, dateRange.getStartDate(), dateRange.getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

		long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);

		long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());

		boolean displayCounts = (exportModelCount > 0) || (modelDeletionCount > 0);

		if (!type.equals(Constants.EXPORT)) {
			UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

			displayCounts = displayCounts && GetterUtil.getBoolean(liveGroupTypeSettings.getProperty(StagingUtil.getStagedPortletId(portlet.getRootPortletId())), portletDataHandler.isPublishToLiveByDefault());
		}

		if (!displayCounts && !showAllPortlets) {
			continue;
		}

		boolean showPortletDataInput = MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId(), portletDataHandler.isPublishToLiveByDefault()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
	%>

		<li class="tree-item">
			<liferay-staging:checkbox
				checked="<%= showPortletDataInput %>"
				deletions="<%= modelDeletionCount %>"
				disabled="<%= disableInputs %>"
				items="<%= exportModelCount %>"
				label="<%= portletTitle %>"
				name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>"
			/>

			<div class="<%= (disableInputs && showPortletDataInput) ? StringPool.BLANK : "hide " %>" id="<portlet:namespace />content_<%= portlet.getPortletId() %>">
				<ul class="lfr-tree list-unstyled">
					<li class="tree-item">
						<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">

							<%
							if (exportControls != null) {
								if (type.equals(Constants.EXPORT)) {
									request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
									request.setAttribute("render_controls.jsp-childControl", false);
									request.setAttribute("render_controls.jsp-controls", exportControls);
									request.setAttribute("render_controls.jsp-disableInputs", disableInputs);
									request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
									request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
									request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
									request.setAttribute("render_controls.jsp-portletId", portlet.getPortletId());
							%>

									<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
										<ul class="lfr-tree list-unstyled">
											<liferay-util:include page="/portlet_list/render_controls.jsp" servletContext="<%= application %>" />
										</ul>
									</aui:field-wrapper>

								<%
								}
								else if (liveGroup.isStagedPortlet(portlet.getRootPortletId())) {
									request.setAttribute("render_controls.jsp-action", Constants.PUBLISH);
									request.setAttribute("render_controls.jsp-childControl", false);
									request.setAttribute("render_controls.jsp-controls", exportControls);
									request.setAttribute("render_controls.jsp-disableInputs", disableInputs);
									request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
									request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
									request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
									request.setAttribute("render_controls.jsp-portletId", portlet.getPortletId());
								%>

									<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
										<ul class="lfr-tree list-unstyled">
											<liferay-util:include page="/portlet_list/render_controls.jsp" servletContext="<%= application %>" />
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
										request.setAttribute("render_controls.jsp-portletId", portlet.getPortletId());
							%>

										<aui:field-wrapper label="content-metadata">
											<ul class="lfr-tree list-unstyled">
												<liferay-util:include page="/portlet_list/render_controls.jsp" servletContext="<%= application %>" />
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
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("portletid", portletId);
					data.put("portlettitle", portletTitle);
					%>

					<span <%= !disableInputs ? StringPool.BLANK : "class=\"hide\"" %>>
						<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + portlet.getPortletId() %>' label="change" method="get" />
					</span>
				</li>
			</ul>

			<aui:script>
				Liferay.Util.toggleBoxes(
					'<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>',
					'<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + portlet.getPortletId() %>'
				);
			</aui:script>
		</li>

	<%
	}
	%>

</ul>

<c:if test="<%= type.equals(Constants.EXPORT) %>">
	<aui:fieldset cssClass="content-options" label="for-each-of-the-selected-content-types,-export-their">
		<span class="selected-labels" id="<portlet:namespace />selectedContentOptions"></span>

		<span <%= !disableInputs ? StringPool.BLANK : "class=\"hide\"" %>>
			<aui:a cssClass="modify-link" href="javascript:;" id="contentOptionsLink" label="change" method="get" />
		</span>

		<div class="hide" id="<portlet:namespace />contentOptions">
			<ul class="lfr-tree list-unstyled">
				<li class="tree-item">
					<aui:input disabled="<%= disableInputs %>" label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.COMMENTS, true) %>" />

					<aui:input disabled="<%= disableInputs %>" label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.RATINGS, true) %>" />
				</li>
			</ul>
		</div>
	</aui:fieldset>
</c:if>