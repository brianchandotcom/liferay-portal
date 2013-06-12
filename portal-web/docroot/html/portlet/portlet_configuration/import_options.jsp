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
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

Layout exportableLayout = ExportImportUtil.getExportableLayout(themeDisplay);
%>

<portlet:actionURL var="importPortletURL">
	<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
</portlet:actionURL>

<aui:form action="<%= importPortletURL %>" cssClass="lfr-export-dialog" enctype="multipart/form-data" method="post" name="fm1">
	<aui:input name="tabs1" type="hidden" value="export_import" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="plid" type="hidden" value="<%= exportableLayout.getPlid() %>" />
	<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.IMPORT %>" />

	<div class="export-dialog-tree">
		<aui:input label="import-a-lar-file-to-overwrite-the-selected-data" name="importFileName" size="50" type="file" />

		<c:if test="<%= Validator.isNotNull(selPortlet.getConfigurationActionClass()) %>">
			<aui:fieldset cssClass="options-group" label="application">
				<ul class="lfr-tree unstyled">
					<li class="tree-item">
						<aui:input label="setup" name="<%= PortletDataHandlerKeys.PORTLET_SETUP %>" type="checkbox" value="<%= true %>" />

						<ul id="<portlet:namespace />showChangeGlobalConfiguration">
							<li class="tree-item">
								<div class="selected-labels" id="<portlet:namespace />selectedGlobalConfiguration"></div>

								<aui:a cssClass="modify-link" href="javascript:;" id="globalConfigurationLink" label="change" method="get" />
							</li>
						</ul>

						<aui:script>
							Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_SETUP %>Checkbox', '<portlet:namespace />showChangeGlobalConfiguration');
						</aui:script>

						<div class="hide" id="<portlet:namespace />globalConfiguration">
							<aui:input label="archived-setups" name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS %>" type="checkbox" value="<%= false %>" />

							<aui:input helpMessage="import-user-preferences-help" label="user-preferences" name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES %>" type="checkbox" value="<%= false %>" />
						</div>
					</li>
				</ul>
			</aui:fieldset>
		</c:if>

		<c:if test="<%= Validator.isNotNull(selPortlet.getPortletDataHandlerClass()) %>">

			<%
			PortletDataHandler portletDataHandler = selPortlet.getPortletDataHandlerInstance();
			%>

			<aui:fieldset cssClass="options-group" label="content">
				<ul class="lfr-tree unstyled">
					<li class="tree-item">
						<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= false %>" />

						<aui:input label="content" name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="checkbox" value="<%= portletDataHandler.isPublishToLiveByDefault() %>" />

						<%
						PortletDataHandlerControl[] importControls = portletDataHandler.getImportControls();
						PortletDataHandlerControl[] metadataControls = portletDataHandler.getImportMetadataControls();

						if (Validator.isNotNull(importControls) || Validator.isNotNull(metadataControls)) {
							String selectedContent = StringPool.BLANK;
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
										request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());

										selectedContent += ArrayUtil.toString(importControls, "controlName", StringPool.COMMA_AND_SPACE, locale);
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

											selectedContent += (selectedContent.equals(StringPool.BLANK) ? "" : ",") + ArrayUtil.toString(childrenControls, "controlName", StringPool.COMMA_AND_SPACE, locale);
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

								<aui:field-wrapper label="update-data">
									<aui:input checked="<%= true %>" data-name='<%= LanguageUtil.get(locale, "mirror") %>' helpMessage="import-data-strategy-mirror-help" id="mirror" label="mirror" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_MIRROR %>" />

									<aui:input data-name='<%= LanguageUtil.get(locale, "mirror-with-overwriting") %>' helpMessage="import-data-strategy-mirror-with-overwriting-help" id="mirrorWithOverwriting" label="mirror-with-overwriting" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE %>" />

									<aui:input data-name='<%= LanguageUtil.get(locale, "copy-as-new") %>' helpMessage="import-data-strategy-copy-as-new-help" id="copyAsNew" label="copy-as-new" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_COPY_AS_NEW %>" />
								</aui:field-wrapper>

								<aui:field-wrapper label="authorship-of-the-content">
									<aui:input checked="<%= true %>" data-name='<%= LanguageUtil.get(locale, "use-the-original-author") %>' helpMessage="use-the-original-author-help"  id="currentUserId" label="use-the-original-author" name="<%= PortletDataHandlerKeys.USER_ID_STRATEGY %>" type="radio" value="<%= UserIdStrategy.CURRENT_USER_ID %>" />

									<aui:input data-name='<%= LanguageUtil.get(locale, "always-use-my-user-id") %>' helpMessage="use-the-current-user-as-author-help" id="alwaysCurrentUserId" label="use-the-current-user-as-author" name="<%= PortletDataHandlerKeys.USER_ID_STRATEGY %>" type="radio" value="<%= UserIdStrategy.ALWAYS_CURRENT_USER_ID %>" />
								</aui:field-wrapper>
							</div>

							<ul id="<portlet:namespace />showChangeContent">
								<li class="tree-item">
									<div class="selected-labels" id="<portlet:namespace />selectedContent_<%= selPortlet.getRootPortletId() %>">

										<%= selectedContent %>,

										<liferay-ui:message key="mirror" />,
										<liferay-ui:message key="use-the-original-author" />
									</div>

									<%
									Map<String,Object> data = new HashMap<String,Object>();

									data.put("portletid", selPortlet.getRootPortletId());
									%>

									<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + selPortlet.getRootPortletId() %>' label="change" method="get" />
								</li>
							</ul>

							<aui:script>
								Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA %>Checkbox', '<portlet:namespace />showChangeContent');
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

		<aui:button-row>
			<aui:button type="submit" value="import" />

			<aui:button href="<%= currentURL %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script use="liferay-export-import">
	new Liferay.ExportImport(
		{
			archivedSetupsNode: '#<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS %>Checkbox',
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>Checkbox',
			form: document.<portlet:namespace />fm1,
			namespace: '<portlet:namespace />',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>Checkbox',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES %>Checkbox'
		}
	);
</aui:script>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA %>Checkbox', '<portlet:namespace />portletDataControls');
	Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PERMISSIONS %>Checkbox', '<portlet:namespace />permissionsUl');
</aui:script>