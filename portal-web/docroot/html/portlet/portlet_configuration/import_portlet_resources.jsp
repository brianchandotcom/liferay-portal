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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
Layout exportableLayout = ExportImportHelperUtil.getExportableLayout(themeDisplay);

FileEntry fileEntry = ExportImportHelperUtil.getTempFileEntry(themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), ExportImportHelper.TEMP_FOLDER_NAME + selPortlet.getPortletId());

ManifestSummary manifestSummary = ExportImportHelperUtil.getManifestSummary(user.getUserId(), themeDisplay.getSiteGroupId(), new HashMap<String, String[]>(), fileEntry);
%>

<portlet:actionURL var="importPortletActionURL">
	<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
</portlet:actionURL>

<portlet:renderURL var="importPortletRenderURL">
	<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
	<portlet:param name="tabs2" value="import" />
	<portlet:param name="portletResource" value="<%= portletResource %>" />
</portlet:renderURL>

<aui:form action="<%= importPortletActionURL %>" cssClass="lfr-export-dialog" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.IMPORT %>" />
	<aui:input name="tabs1" type="hidden" value="export_import" />
	<aui:input name="tabs2" type="hidden" value="import" />
	<aui:input name="redirect" type="hidden" value="<%= importPortletRenderURL %>" />
	<aui:input name="plid" type="hidden" value="<%= exportableLayout.getPlid() %>" />
	<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<div class="export-dialog-tree">
		<div id="<portlet:namespace />importConfiguration">
			<aui:fieldset cssClass="options-group" label="file">
				<dl class="import-file-details">
					<dt>
						<liferay-ui:message key="name" />
					</dt>
					<dd>
						<%= fileEntry.getTitle() %>
					</dd>
					<dt>
						<liferay-ui:message key="export" />
					</dt>
					<dd>

						<%
						Date exportDate = manifestSummary.getExportDate();
						%>

						<span onmouseover="Liferay.Portal.ToolTip.show(this, '<%= dateFormatDateTime.format(exportDate) %>')">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(pageContext, System.currentTimeMillis() - exportDate.getTime(), true) %>" key="x-ago" />
						</span>
					</dd>
					<dt>
						<liferay-ui:message key="author" />
					</dt>
					<dd>
						<%= fileEntry.getUserName() %>
					</dd>
					<dt>
						<liferay-ui:message key="size" />
					</dt>
					<dd>
						<%= fileEntry.getSize() / 1024 %>k
					</dd>
				</dl>
			</aui:fieldset>

			<%
			PortletDataHandler portletDataHandler = selPortlet.getPortletDataHandlerInstance();
			%>

			<c:if test="<%= (portletDataHandler != null) && (portletDataHandler.getConfigurationControls(selPortlet) != null) %>">
				<aui:fieldset cssClass="options-group" label="application">
					<ul class="lfr-tree unstyled">
						<li class="tree-item">
							<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION %>" type="hidden" value="<%= true %>" />

							<aui:input label="configuration" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>" type="checkbox" value="<%= true %>" />

							<div class="hide" id="<portlet:namespace />configuration_<%= selPortlet.getRootPortletId() %>">
								<aui:fieldset cssClass="portlet-type-data-section" label="configuration">
									<ul class="lfr-tree unstyled">

										<%
										request.setAttribute("render_controls.jsp-action", Constants.IMPORT);
										request.setAttribute("render_controls.jsp-controls", portletDataHandler.getConfigurationControls(selPortlet));
										request.setAttribute("render_controls.jsp-portletId", selPortlet.getRootPortletId());
										%>

										<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
									</ul>
								</aui:fieldset>
							</div>

							<ul class="hide" id="<portlet:namespace />showChangeConfiguration_<%= selPortlet.getRootPortletId() %>">
								<li>
									<div class="selected-labels" id="<portlet:namespace />selectedConfiguration_<%= selPortlet.getRootPortletId() %>"></div>

									<%
									Map<String,Object> data = new HashMap<String,Object>();

									data.put("portletid", selPortlet.getRootPortletId());
									%>

									<aui:a cssClass="configuration-link modify-link" data="<%= data %>" href="javascript:;" label="change" method="get" />
								</li>
							</ul>

							<aui:script>
								Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>Checkbox', '<portlet:namespace />showChangeConfiguration<%= StringPool.UNDERLINE + selPortlet.getRootPortletId() %>');
							</aui:script>
						</li>
					</ul>
				</aui:fieldset>
			</c:if>

			<%
			long importModelCount = portletDataHandler.getExportModelCount(manifestSummary);
			%>

			<c:if test="<%= (importModelCount != 0) && (portletDataHandler != null) %>">
				<aui:fieldset cssClass="options-group" label="content">
					<ul class="lfr-tree unstyled">
						<li class="tree-item">
							<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= false %>" />

							<aui:input label='<%= LanguageUtil.get(pageContext, "content") + (importModelCount > 0 ? " (" + importModelCount + ")" : StringPool.BLANK) %>' name='<%= PortletDataHandlerKeys.PORTLET_DATA + "_" + selPortlet.getRootPortletId() %>' type="checkbox" value="<%= portletDataHandler.isPublishToLiveByDefault() %>" />

							<%
							PortletDataHandlerControl[] importControls = portletDataHandler.getImportControls();
							PortletDataHandlerControl[] metadataControls = portletDataHandler.getImportMetadataControls();

							if (Validator.isNotNull(importControls) || Validator.isNotNull(metadataControls)) {
							%>

								<div class="hide" id="<portlet:namespace />content_<%= selPortlet.getRootPortletId() %>">
									<aui:field-wrapper label='<%= Validator.isNotNull(metadataControls) ? "content" : StringPool.BLANK %>'>
										<aui:input data-name='<%= LanguageUtil.get(locale, "delete-portlet-data") %>' label="delete-portlet-data-before-importing" name="<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" type="checkbox" />

										<div id="<portlet:namespace />showDeleteContentWarning">
											<div class="alert alert-block">
												<liferay-ui:message key="delete-content-before-importing-warning" />

												<liferay-ui:message key="delete-content-before-importing-suggestion" />
											</div>
										</div>

										<aui:script>
											Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>Checkbox', '<portlet:namespace />showDeleteContentWarning');
										</aui:script>

										<c:if test="<%= importControls != null %>">

											<%
											request.setAttribute("render_controls.jsp-action", Constants.IMPORT);
											request.setAttribute("render_controls.jsp-controls", importControls);
											request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
											request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
											%>

											<ul class="lfr-tree unstyled">
												<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
											</ul>
										</c:if>
									</aui:field-wrapper>

									<c:if test="<%= metadataControls != null %>">

										<%
										for (PortletDataHandlerControl metadataControl : metadataControls) {
											PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)metadataControl;

											PortletDataHandlerControl[] childrenControls = control.getChildren();

											if ((childrenControls != null) && (childrenControls.length > 0)) {
												request.setAttribute("render_controls.jsp-controls", childrenControls);
											%>

												<aui:field-wrapper label="content-metadata">
													<ul class="lfr-tree unstyled">
														<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
													</ul>
												</aui:field-wrapper>

											<%
											}
										}
										%>

									</c:if>
								</div>

								<ul id="<portlet:namespace />showChangeContent">
									<li class="tree-item">
										<div class="selected-labels" id="<portlet:namespace />selectedContent_<%= selPortlet.getRootPortletId() %>"></div>

										<%
										Map<String,Object> data = new HashMap<String,Object>();

										data.put("portletid", selPortlet.getRootPortletId());
										%>

										<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + selPortlet.getRootPortletId() %>' label="change" method="get" />
									</li>
								</ul>

								<aui:script>
									Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>Checkbox', '<portlet:namespace />showChangeContent');
								</aui:script>

							<%
							}
							%>

						</li>

						<li>
							<aui:fieldset cssClass="comments-and-ratings" label="for-each-of-the-selected-content-types,-import-their">
								<div class="selected-labels" id="<portlet:namespace />selectedCommentsAndRatings"></div>

								<aui:a cssClass="modify-link" href="javascript:;" id="commentsAndRatingsLink" label="change" method="get" />

								<div class="hide" id="<portlet:namespace />commentsAndRatings">
									<ul class="lfr-tree unstyled">
										<li class="tree-item">
											<aui:input label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= true %>" />

											<aui:input label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= true %>" />
										</li>
									</ul>
								</div>
							</aui:fieldset>
						</li>
					</ul>
				</aui:fieldset>

				<aui:fieldset cssClass="options-group" label="permissions">
					<ul class="lfr-tree unstyled">
						<li class="tree-item">
							<aui:input helpMessage="export-import-portlet-permissions-help" label="permissions" name="<%= PortletDataHandlerKeys.PERMISSIONS %>" type="checkbox" />

							<ul id="<portlet:namespace />permissionsUl">
								<li class="tree-item">
									<aui:input label="permissions-assigned-to-roles" name="permissionsAssignedToRoles" type="checkbox" value="<%= true %>" />
								</li>
							</ul>
						</li>
					</ul>
				</aui:fieldset>
			</c:if>

			<portlet:renderURL var="importPortletURL">
				<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
				<portlet:param name="portletResource" value="<%= String.valueOf(portletResource) %>" />
				<portlet:param name="tabs2" value="import" />
			</portlet:renderURL>

			<aui:button href="<%= importPortletURL %>" name="back1" value="back" />

			<aui:button cssClass="btn-primary" name="continue" value="continue" />
		</div>

		<div class="hide" id="<portlet:namespace />importStrategy">
			<aui:fieldset cssClass="options-group" label="update-data">
				<aui:input checked="<%= true %>" data-name='<%= LanguageUtil.get(locale, "mirror") %>' helpMessage="import-data-strategy-mirror-help" id="mirror" label="mirror" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_MIRROR %>" />

				<aui:input data-name='<%= LanguageUtil.get(locale, "mirror-with-overwriting") %>' helpMessage="import-data-strategy-mirror-with-overwriting-help" id="mirrorWithOverwriting" label="mirror-with-overwriting" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE %>" />

				<aui:input data-name='<%= LanguageUtil.get(locale, "copy-as-new") %>' helpMessage="import-data-strategy-copy-as-new-help" id="copyAsNew" label="copy-as-new" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_COPY_AS_NEW %>" />
			</aui:fieldset>

			<aui:fieldset cssClass="options-group" label="authorship-of-the-content">
				<aui:input checked="<%= true %>" data-name='<%= LanguageUtil.get(locale, "use-the-original-author") %>' helpMessage="use-the-original-author-help"  id="currentUserId" label="use-the-original-author" name="<%= PortletDataHandlerKeys.USER_ID_STRATEGY %>" type="radio" value="<%= UserIdStrategy.CURRENT_USER_ID %>" />

				<aui:input data-name='<%= LanguageUtil.get(locale, "always-use-my-user-id") %>' helpMessage="use-the-current-user-as-author-help" id="alwaysCurrentUserId" label="use-the-current-user-as-author" name="<%= PortletDataHandlerKeys.USER_ID_STRATEGY %>" type="radio" value="<%= UserIdStrategy.ALWAYS_CURRENT_USER_ID %>" />
			</aui:fieldset>

			<aui:button-row>
				<aui:button name="back" value="back" />

				<aui:button type="submit" value="import" />
			</aui:button-row>
		</div>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PERMISSIONS %>Checkbox', '<portlet:namespace />permissionsUl');
</aui:script>

<aui:script use="aui-base">
	A.one(<portlet:namespace />continue).on(
		'click',
		function() {
			A.one('#<portlet:namespace />importConfiguration').hide()
			A.one('#<portlet:namespace />importStrategy').show();
		}
	);

	A.one(<portlet:namespace />back).on(
		'click',
		function() {
			A.one('#<portlet:namespace />importConfiguration').show()
			A.one('#<portlet:namespace />importStrategy').hide();
		}
	);
</aui:script>

<aui:script use="liferay-export-import">
	new Liferay.ExportImport(
		{
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>Checkbox',
			form: document.<portlet:namespace />fm1,
			namespace: '<portlet:namespace />',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>Checkbox'
		}
	);
</aui:script>