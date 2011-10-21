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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<%
boolean branchingEnabled = false;

LayoutRevision layoutRevision = null;

LayoutSetBranch layoutSetBranch = null;

LayoutBranch layoutBranch = null;

Layout liveLayout = null;

if (layout != null) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);

	if (layoutRevision != null) {
		branchingEnabled = true;

		layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutRevision.getLayoutSetBranchId());

		layoutBranch = layoutRevision.getLayoutBranch();
	}
}
%>

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	String liveFriendlyURL = null;

	if (liveGroup != null) {
		try {
			liveLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId());

			liveFriendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
		}
		catch (Exception e) {
			liveFriendlyURL = PortalUtil.getGroupFriendlyURL(liveGroup, layout.getPrivateLayout(), themeDisplay);
		}

		liveFriendlyURL = PortalUtil.addPreservedParameters(themeDisplay, liveFriendlyURL);
	}

	String stagingFriendlyURL = null;

	if (stagingGroup != null) {
		try {
			Layout stagingLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), stagingGroup.getGroupId());

			stagingFriendlyURL = PortalUtil.getLayoutFriendlyURL(stagingLayout, themeDisplay);
		}
		catch (Exception e) {
			stagingFriendlyURL = PortalUtil.getGroupFriendlyURL(stagingGroup, layout.getPrivateLayout(), themeDisplay);
		}

		stagingFriendlyURL = PortalUtil.addPreservedParameters(themeDisplay, stagingFriendlyURL);
	}

	List<LayoutSetBranch> layoutSetBranches = null;

	if (group.isStagingGroup() || group.isStagedRemotely()) {
		layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), layout.isPrivateLayout());
	}
	%>

	<div class="staging-bar">
		<ul class="aui-tabview-list staging-tabview-list">
			<c:if test="<%= !group.isStagedRemotely() && ((liveGroup != null) && layout.isPrivateLayout() ? (liveGroup.getPrivateLayoutsPageCount() > 0) : (liveGroup.getPublicLayoutsPageCount() > 0)) %>">
				<li class="aui-state-default aui-tab first <%= (!group.isStagingGroup() ? " aui-state-active aui-tab-active" : StringPool.BLANK) %>">
					<span class="aui-tab-content">
						<aui:a cssClass="aui-tab-label" href="<%= !group.isStagingGroup() ? null : liveFriendlyURL %>" label="live" />
					</span>
				</li>
			</c:if>

			<c:if test="<%= stagingGroup != null %>">
				<li class="aui-state-default aui-tab last <%= (layoutSetBranches != null) ? " aui-state-active aui-tab-active" : StringPool.BLANK %>">
					<span class="aui-tab-content">
						<aui:a cssClass="aui-tab-label" href="<%= (layoutSetBranches != null) ? null : stagingFriendlyURL %>" label="staging">
							<c:if test="<%= (layoutSetBranches != null) && (layoutSetBranches.size() <= 1) %>">
								<liferay-ui:staging extended="<%= false %>" showManageBranches="<%= branchingEnabled %>" />
							</c:if>
						</aui:a>
					</span>
				</li>
			</c:if>
		</ul>

		<c:if test="<%= (layoutSetBranches != null) && (layoutSetBranches.size() > 1) %>">
			<ul class="aui-tabview-list site-variations-tabview-list">
				<c:if test="<%= group.isStagingGroup() || layoutSetBranches.size() <= _MAX_INLINE_BRANCHES %>">

					<%
					for (int i = 0; i < layoutSetBranches.size(); i++) {
						LayoutSetBranch curLayoutSetBranch = null;

						if (layoutSetBranches.size() > _MAX_INLINE_BRANCHES) {
							curLayoutSetBranch = layoutSetBranch;
						}
						else {
							curLayoutSetBranch = layoutSetBranches.get(i);
						}

						boolean first = (i == 0);
						boolean selected = (group.isStagingGroup() || group.isStagedRemotely()) && (curLayoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());

						String cssClass = "aui-state-default aui-tab";

						if (first) {
							cssClass += " first";
						}

						if (selected) {
							cssClass += " aui-state-active aui-tab-active";
						}
					%>

						<portlet:actionURL var="layoutSetBranchURL">
							<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
							<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
							<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
							<portlet:param name="privateLayout" value="<%= String.valueOf(layout.isPrivateLayout()) %>" />
							<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()) %>" />
						</portlet:actionURL>

						<li class="<%= cssClass %>">
							<span class="aui-tab-content">
								<span class="aui-tab-label">
									<aui:a href="<%= selected ? null : layoutSetBranchURL %>" label='<%= layoutSetBranches.size() == 1 ? "staging" : curLayoutSetBranch.getName() %>' />

									<liferay-ui:staging extended="<%= false %>" layoutSetBranchId="<%= curLayoutSetBranch.getLayoutSetBranchId() %>" />
								</span>
							</span>
						</li>

					<%
						if (layoutSetBranches.size() > _MAX_INLINE_BRANCHES) {
							break;
						}
					}
					%>

				</c:if>

				<c:if test="<%= layoutSetBranches.size() > _MAX_INLINE_BRANCHES %>">
					<li class="aui-state-default aui-tab go-to-layout-set-branches-tab">
						<span class="aui-tab-content">
							<liferay-ui:icon-menu align="left" cssClass="aui-tab-label layoutset-branches-menu" direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/dock/staging.png" %>' message='<%= LanguageUtil.format(pageContext, "site-pages-variations-x", layoutSetBranches.size()) %>'>

								<%
								for (int i = 0; i < layoutSetBranches.size(); i++) {
									LayoutSetBranch curLayoutSetBranch = layoutSetBranches.get(i);

									boolean selected = group.isStagingGroup() && (layoutRevision != null) && (curLayoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());
								%>

									<portlet:actionURL var="layoutSetBranchURL">
										<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
										<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
										<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
										<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
										<portlet:param name="privateLayout" value="<%= String.valueOf(layout.isPrivateLayout()) %>" />
										<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()) %>" />
									</portlet:actionURL>

									<liferay-ui:icon
										cssClass='<%= selected ? "disabled" : StringPool.BLANK %>'
										image='<%= selected ? "../arrows/01_right" : "copy"  %>'
										message="<%= curLayoutSetBranch.getName() %>"
										url="<%= selected ? null : layoutSetBranchURL %>"
									/>

								<%
								}
								%>

							</liferay-ui:icon-menu>
						</span>
					</li>
				</c:if>

				<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="layoutSetBranchesURL">
					<portlet:param name="struts_action" value="/staging_bar/view_layout_set_branches" />
				</portlet:renderURL>

				<li class="aui-state-default aui-tab last manage-layout-set-branches-tab">
					<span class="aui-tab-content">
						<liferay-ui:icon
							cssClass="aui-tab-label manage-layout-set-branches"
							id="manageLayoutSetBranches"
							image="configuration"
							label="<%= true %>"
							message="manage-site-pages-variations"
							url="<%= layoutSetBranchesURL %>"
						/>
					</span>
				</li>

				<aui:script use="aui-base">
					var layoutSetBranchesLink = A.one('#<portlet:namespace />manageLayoutSetBranches');

					if (layoutSetBranchesLink) {
						layoutSetBranchesLink.detach('click');

						layoutSetBranchesLink.on(
							'click',
							function(event) {
								event.preventDefault();

								Liferay.Util.openWindow(
									{
										dialog:
											{
												width: 820
											},
										id: '<portlet:namespace />layoutSetBranches',
										title: '<liferay-ui:message key="manage-site-pages-variations" />',
										uri: event.currentTarget.attr('href')
									}
								);
							}
						);
					}
				</aui:script>
			</ul>
		</c:if>

		<%
		UnicodeProperties typeSettingsProperties = null;
		%>

		<div class="aui-tabview-content staging-tabview-content">
			<c:choose>
				<c:when test="<%= (group.isStagingGroup() || group.isStagedRemotely()) && branchingEnabled %>">
					<div class="layout-set-branch-info">
						<c:if test="<%= Validator.isNotNull(layoutSetBranch.getDescription()) %>">
							<span class="layout-set-branch-description"><%= layoutSetBranch.getDescription() %></span>
						</c:if>

						<span class="layout-set-branch-pages">
							<liferay-ui:message arguments="<%= layouts.size() %>" key='<%= (layouts.size() == 1) ? "1-page" : "x-pages" %>' />
						</span>
					</div>

					<div class="staging-details">
						<portlet:actionURL var="editLayoutRevisonURL">
							<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
							<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
						</portlet:actionURL>

						<aui:form action="<%= editLayoutRevisonURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "savePage();" %>'>
							<aui:input name="<%= Constants.CMD %>" type="hidden" />
							<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
							<aui:input name="layoutRevisionId" type="hidden" value="<%= layoutRevision.getLayoutRevisionId() %>" />
							<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutRevision.getLayoutSetBranchId() %>" />
							<aui:input name="updateRecentLayoutRevisionId" type="hidden" value="<%= false %>" />

							<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="layoutBranchesURL">
								<portlet:param name="struts_action" value="/staging_bar/view_layout_branches" />
								<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
							</portlet:renderURL>

							<div class="layout-info">
								<div class="layout-title">
									<span class="layout-title-label"><liferay-ui:message key="current-page" />:</span>

									<span class="layout-breadcrumb">
										<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showParentGroups="<%= false %>" showPortletBreadcrumb="<%= false %>" />
									</span>
								</div>

								<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

								<%
								List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));
								%>

								<c:if test="<%= layoutRevisions.size() > 1 %>">
									<ul class="aui-tabview-list variations-tabview-list">

										<%
										for (int i = 0; i < layoutRevisions.size(); i ++) {
											LayoutBranch curLayoutBranch = null;

											if (layoutRevisions.size() > _MAX_INLINE_BRANCHES) {
												curLayoutBranch = layoutBranch;
											}
											else {
												LayoutRevision rootLayoutRevision = layoutRevisions.get(i);

												curLayoutBranch = rootLayoutRevision.getLayoutBranch();
											}

											boolean selected = (curLayoutBranch.getLayoutBranchId() == layoutRevision.getLayoutBranchId());

											String cssClass = "aui-state-default aui-tab layout-set-branch";

											if (i == 0) {
												cssClass += " first";
											}

											if (selected) {
												cssClass += " aui-state-active aui-tab-active";
											}
										%>

											<portlet:actionURL var="layoutBranchURL">
												<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
												<portlet:param name="<%= Constants.CMD %>" value="select_layout_branch" />
												<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
												<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
												<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
												<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
											</portlet:actionURL>

											<li class="<%= cssClass %>">
												<span class="aui-tab-content">
													<aui:a cssClass="aui-tab-label" href="<%= selected ? null : layoutBranchURL %>" label="<%= curLayoutBranch.getName() %>" />
												</span>
											</li>

										<%
											if (layoutRevisions.size() > _MAX_INLINE_BRANCHES) {
												break;
											}
										}
										%>

										<c:if test="<%= layoutRevisions.size() > _MAX_INLINE_BRANCHES %>">
											<li class="aui-state-default aui-tab go-to-layout-branches-tab">
												<span class="aui-tab-content">
													<liferay-ui:icon-menu align="left" cssClass="aui-tab-label layoutset-branches-menu" direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/signal_instance.png" %>' message="page-variations">

														<%
														for (int i = 0; i < layoutRevisions.size(); i ++) {
															LayoutRevision rootLayoutRevision = layoutRevisions.get(i);

															LayoutBranch curLayoutBranch = rootLayoutRevision.getLayoutBranch();

															boolean selected = (rootLayoutRevision.getLayoutBranchId() == layoutRevision.getLayoutBranchId());
														%>

															<portlet:actionURL var="rootLayoutRevisionURL">
																<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
																<portlet:param name="<%= Constants.CMD %>" value="select_layout_branch" />
																<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
																<portlet:param name="groupId" value="<%= String.valueOf(rootLayoutRevision.getGroupId()) %>" />
																<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(rootLayoutRevision.getLayoutSetBranchId()) %>" />
																<portlet:param name="layoutBranchId" value="<%= String.valueOf(rootLayoutRevision.getLayoutBranchId()) %>" />
															</portlet:actionURL>

															<liferay-ui:icon
																cssClass='<%= selected ? "disabled" : StringPool.BLANK %>'
																image='<%= selected ? "../arrows/01_right" : "copy"  %>'
																message="<%= curLayoutBranch.getName() %>"
																url="<%= selected ? null : rootLayoutRevisionURL %>"
															/>

														<%
														}
														%>

													</liferay-ui:icon-menu>
												</span>
											</li>
										</c:if>

										<li class="aui-state-default aui-tab last manage-page-variations-tab">
											<span class="aui-tab-content">
												<liferay-ui:icon
													cssClass="aui-tab-label manage-layout-branches-tab"
													id="manageLayoutRevisions"
													image="configuration"
													label="<%= true %>"
													message="manage-page-variations"
													url="<%= layoutBranchesURL %>"
												/>
											</span>
										</li>
									</ul>
								</c:if>

								<div class="aui-tabview-content variations-tabview-content">
									<c:if test="<%= Validator.isNotNull(layoutBranch.getDescription()) %>">
										<div class="layout-branch-description">
											<%= layoutBranch.getDescription() %>
										</div>
									</c:if>

									<%
									request.setAttribute("view.jsp-layoutRevision", layoutRevision);
									request.setAttribute("view.jsp-layoutSetBranch", layoutSetBranch);
									%>

									<div class="layout-revision-details" id="<portlet:namespace />layoutRevisionDetails">
										<liferay-util:include page="/html/portlet/staging_bar/view_layout_revision_details.jsp" />
									</div>

									<c:if test="<%= layoutRevisions.size() <= 1 %>">
										<liferay-ui:icon
											cssClass="manage-layout-branches-tab"
											id="manageLayoutRevisions"
											image="configuration"
											label="<%= true %>"
											message="manage-page-variations"
											url="<%= layoutBranchesURL %>"
										/>
									</c:if>
								</div>
							</div>
						</aui:form>
					</div>

					<aui:script use="aui-base">
						var layoutRevisionsLink = A.one('#<portlet:namespace />manageLayoutRevisions');

						if (layoutRevisionsLink) {
							layoutRevisionsLink.detach('click');

							layoutRevisionsLink.on(
								'click',
								function(event) {
									event.preventDefault();

									Liferay.Util.openWindow(
										{
											dialog:
												{
													width: 820
												},
											id: '<portlet:namespace />layoutRevisions',
											title: '<liferay-ui:message key="manage-page-variations" />',
											uri: event.currentTarget.attr('href')
										}
									);
								}
							);
						}
					</aui:script>
				</c:when>
				<c:otherwise>
					<div class="staging-details">
						<c:choose>
							<c:when test="<%= liveLayout == null %>">
								<span class="last-publication-branch">
									<liferay-ui:message arguments='<%= "<strong>" + layout.getName(locale) + "</strong>" %>' key="page-x-has-not-been-published-to-live-yet" />
								</span>
							</c:when>
							<c:otherwise>

								<%
								typeSettingsProperties = liveLayout.getTypeSettingsProperties();

								long lastImportDate = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-date"));
								%>

								<c:choose>
										<c:when test="<%= lastImportDate > 0 %>">

											<%
											String lastImportLayoutSetBranchName = null;

											long lastImportLayoutSetBranchId = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-layout-set-branch-id"));

											if (lastImportLayoutSetBranchId > 0) {

												try {
													LayoutSetBranch lastImportLayoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(lastImportLayoutSetBranchId);

													lastImportLayoutSetBranchName = lastImportLayoutSetBranch.getName();
												}
												catch (Exception e) {
												}
											}

											if (Validator.isNull(lastImportLayoutSetBranchName)) {
												lastImportLayoutSetBranchName = typeSettingsProperties.getProperty("last-import-layout-set-branch-name");
											}

											if (Validator.isNull(lastImportLayoutSetBranchName)) {
												lastImportLayoutSetBranchName = LanguageUtil.get(pageContext, "staging");
											}

											String lastImportLayoutBranchName = null;

											List<LayoutRevision> layoutRevisions = new ArrayList<LayoutRevision>();

											long lastImportLayoutRevisionId = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-layout-revision-id"));

											if (lastImportLayoutRevisionId > 0) {
												try {
													LayoutRevision lastImportLayoutRevision = LayoutRevisionLocalServiceUtil.getLayoutRevision(lastImportLayoutRevisionId);

													lastImportLayoutBranchName = lastImportLayoutRevision.getLayoutBranch().getName();

													layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(lastImportLayoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, lastImportLayoutRevision.getPlid());
												}
												catch (Exception e) {
												}
											}

											if (Validator.isNull(lastImportLayoutBranchName)) {
												lastImportLayoutBranchName = typeSettingsProperties.getProperty("last-import-layout-branch-name");
											}

											String publisherName = null;

											String lastImportUserUuid = GetterUtil.getString(typeSettingsProperties.getProperty("last-import-user-uuid"));

											if (Validator.isNotNull(lastImportUserUuid)) {
												try {
													User publisher = UserLocalServiceUtil.getUserByUuid(lastImportUserUuid);

													publisherName = publisher.getFullName();
												}
												catch (Exception e) {
												}
											}

											if (Validator.isNull(publisherName)) {
												publisherName = typeSettingsProperties.getProperty("last-import-user-name");
											}
											%>

											<c:if test="<%= Validator.isNotNull(lastImportLayoutSetBranchName) && Validator.isNotNull(publisherName) %>">
												<span class="last-publication-branch">
													<liferay-ui:message arguments='<%= new String[] {"<strong>" + layout.getName(locale) + "</strong>", "<em>" + LanguageUtil.get(pageContext, lastImportLayoutSetBranchName) + "</em>"} %>' key='<%= (group.isStagingGroup() || group.isStagedRemotely()) ? "page-x-was-last-published-to-live" : "page-x-was-last-published-from-x" %>' />

													<c:if test="<%= (Validator.isNotNull(lastImportLayoutBranchName) && (layoutRevisions.size() > 1)) || Validator.isNotNull(lastImportLayoutRevisionId) %>">
														<span class="last-publication-variation-details">(
															<c:if test="<%= Validator.isNotNull(lastImportLayoutBranchName) && (layoutRevisions.size() > 1) %>">
																<span class="variation-name">
																	<liferay-ui:message key="variation" />: <strong><%= lastImportLayoutBranchName %></strong>
																</span>
															</c:if>

															<c:if test="<%= Validator.isNotNull(lastImportLayoutRevisionId) %>">
																<span class="layout-version">
																	<liferay-ui:message key="version" />: <strong><%= lastImportLayoutRevisionId %></strong>
																</span>
															</c:if>
														)</span>
													</c:if>
												</span>

												<span class="last-publication-user">
													<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(pageContext, (System.currentTimeMillis() - lastImportDate), true), publisherName} %>" key="x-ago-by-x" />
												</span>
											</c:if>
										</c:when>
										<c:otherwise>
											<span class="staging-live-group-name">
												<liferay-ui:message arguments="<%= liveGroup.getDescriptiveName() %>" key="x-is-staged" />
											</span>

											<span class="staging-live-help">
												<liferay-ui:message arguments="<%= liveGroup.getDescriptiveName() %>" key='<%= (group.isStagingGroup() || group.isStagedRemotely()) ? "staging-staging-help-x" : "staging-live-help-x" %>' />
											</span>
										</c:otherwise>
									</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<c:if test="<%= !branchingEnabled %>">
		<aui:script use="liferay-staging">
			Liferay.StagingBar.init(
				{
					namespace: '<portlet:namespace />'
				}
			);
		</aui:script>
	</c:if>
</c:if>

<%!
private static final int _MAX_INLINE_BRANCHES = 8;
%>