<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutsTreeDisplayContext layoutsTreeDisplayContext = new LayoutsTreeDisplayContext(group, groupId, request, renderRequest, renderResponse);
%>

<aui:fieldset cssClass="options-group" id="pages-fieldset" markupView="lexicon">
	<clay:sheet-section>
		<h3 class="sheet-subtitle"><liferay-ui:message key="pages" /></h3>

		<div class="d-flex flex-wrap layout-selector" id="<portlet:namespace />pages">
			<c:if test="<%= (!layoutsTreeDisplayContext.isDisableInputs() && layoutsTreeDisplayContext.isPrivateLayoutsEnabled()) || LayoutStagingUtil.isBranchingLayoutSet(layoutsTreeDisplayContext.getSelectPagesGroup(), layoutsTreeDisplayContext.isSelectPagesPrivateLayout()) %>">
				<div class="layout-selector-options">
					<aui:fieldset label="pages-options">
						<c:if test="<%= !layoutsTreeDisplayContext.isDisableInputs() && layoutsTreeDisplayContext.isPrivateLayoutsEnabled() %>">
							<c:choose>
								<c:when test="<%= layoutsTreeDisplayContext.isSelectPagesPrivateLayout() %>">
									<aui:button id="changeToPublicLayoutsButton" value="change-to-public-pages" />
								</c:when>
								<c:otherwise>
									<aui:button id="changeToPrivateLayoutsButton" value="change-to-private-pages" />
								</c:otherwise>
							</c:choose>
						</c:if>

						<c:if test="<%= LayoutStagingUtil.isBranchingLayoutSet(layoutsTreeDisplayContext.getSelectPagesGroup(), layoutsTreeDisplayContext.isSelectPagesPrivateLayout()) %>">
							<aui:select disabled="<%= layoutsTreeDisplayContext.isDisableInputs() %>" label="site-pages-variation" name="layoutSetBranchId">

								<%
								for (LayoutSetBranch layoutSetBranch : layoutsTreeDisplayContext.getLayoutSetBranches()) {
									boolean translateLayoutSetBranchName = LayoutSetBranchConstants.MASTER_BRANCH_NAME.equals(HtmlUtil.escape(layoutSetBranch.getName()));

									boolean selected = false;

									if ((layoutsTreeDisplayContext.getLayoutSetBranchId() == layoutSetBranch.getLayoutSetBranchId()) || ((layoutsTreeDisplayContext.getLayoutSetBranchId() == 0) && layoutSetBranch.isMaster())) {
										selected = true;
									}
								%>

									<aui:option label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>" localizeLabel="<%= translateLayoutSetBranchName %>" selected="<%= selected %>" value="<%= layoutSetBranch.getLayoutSetBranchId() %>" />

								<%
								}
								%>

							</aui:select>
						</c:if>
					</aui:fieldset>
				</div>
			</c:if>

			<div class="layout-selector-options">
				<aui:fieldset helpMessage="<%= layoutsTreeDisplayContext.getChildPageHelpMessage() %>" label='<%= "pages-to-" + layoutsTreeDisplayContext.getAction() %>'>
					<c:choose>
						<c:when test="<%= layoutsTreeDisplayContext.isDisableInputs() %>">
							<liferay-util:buffer
								var="badgeHTML"
							>
								<span class="badge badge-info">
									<liferay-ui:message key="<%= layoutsTreeDisplayContext.getLayoutsCountMessageKey() %>" />
								</span>
							</liferay-util:buffer>

							<li class="tree-item">
								<liferay-ui:message arguments="<%= badgeHTML %>" key="pages-x" />
							</li>
						</c:when>
						<c:otherwise>
							<div>
								<react:component
									module="select_pages/js/PagesTree"
									props="<%= layoutsTreeDisplayContext.getPagesTreeData() %>"
								/>
							</div>
						</c:otherwise>
					</c:choose>
				</aui:fieldset>
			</div>

			<div class="layout-selector-options">
				<aui:fieldset label="look-and-feel">
					<liferay-staging:checkbox
						checked="<%= MapUtil.getBoolean(layoutsTreeDisplayContext.getParameterMap(), PortletDataHandlerKeys.THEME_REFERENCE, ParamUtil.getBoolean(request, PortletDataHandlerKeys.THEME_REFERENCE, true)) %>"
						disabled="<%= layoutsTreeDisplayContext.isDisableInputs() %>"
						label="theme-settings"
						name="<%= PortletDataHandlerKeys.THEME_REFERENCE %>"
						popover="export-import-theme-settings-help"
					/>

					<liferay-staging:checkbox
						checked="<%= MapUtil.getBoolean(layoutsTreeDisplayContext.getParameterMap(), PortletDataHandlerKeys.LOGO, ParamUtil.getBoolean(request, PortletDataHandlerKeys.LOGO, true)) %>"
						disabled="<%= layoutsTreeDisplayContext.isDisableInputs() %>"
						label="logo"
						name="<%= PortletDataHandlerKeys.LOGO %>"
					/>

					<liferay-staging:checkbox
						checked="<%= MapUtil.getBoolean(layoutsTreeDisplayContext.getParameterMap(), PortletDataHandlerKeys.LAYOUT_SET_SETTINGS, ParamUtil.getBoolean(request, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS, true)) %>"
						disabled="<%= layoutsTreeDisplayContext.isDisableInputs() %>"
						label="site-pages-settings"
						name="<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>"
					/>

					<liferay-staging:checkbox
						checked="<%= MapUtil.getBoolean(layoutsTreeDisplayContext.getParameterMap(), PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS, ParamUtil.getBoolean(request, PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS, true)) %>"
						disabled="<%= layoutsTreeDisplayContext.isDisableInputs() %>"
						label="site-template-settings"
						name="<%= PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS %>"
					/>

					<c:if test="<%= Objects.equals(layoutsTreeDisplayContext.getAction(), Constants.PUBLISH) %>">
						<liferay-staging:checkbox
							checked="<%= MapUtil.getBoolean(layoutsTreeDisplayContext.getParameterMap(), PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS, ParamUtil.getBoolean(request, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS, false)) %>"
							disabled="<%= layoutsTreeDisplayContext.isDisableInputs() %>"
							label="delete-missing-layouts"
							name="<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>"
							popover="delete-missing-layouts-staging-help"
						/>
					</c:if>
				</aui:fieldset>
			</div>
		</div>

		<c:if test="<%= Objects.equals(layoutsTreeDisplayContext.getAction(), Constants.PUBLISH) %>">
			<div class="d-flex deletions flex-wrap layout-selector" id="<portlet:namespace />pagedeletions">
				<div class="layout-selector-options">
					<aui:fieldset label="page-deletions">
						<span>
							<liferay-staging:checkbox
								checked="<%= MapUtil.getBoolean(layoutsTreeDisplayContext.getParameterMap(), PortletDataHandlerKeys.DELETE_LAYOUTS, false) %>"
								deletions="<%= ExportImportHelperUtil.getLayoutModelDeletionCount(layoutsTreeDisplayContext.getPortletDataContext(), layoutsTreeDisplayContext.isSelectPagesPrivateLayout()) %>"
								disabled="<%= layoutsTreeDisplayContext.isDisableInputs() %>"
								label="publish-page-deletions"
								name="<%= PortletDataHandlerKeys.DELETE_LAYOUTS %>"
								popover="affected-by-the-content-sections-date-range-selector"
							/>
						</span>
					</aui:fieldset>
				</div>
			</div>
		</c:if>
	</clay:sheet-section>
</aui:fieldset>