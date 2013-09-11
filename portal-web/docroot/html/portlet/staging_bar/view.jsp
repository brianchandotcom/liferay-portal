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
			liveLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());

			liveFriendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
		}
		catch (Exception e) {
			liveFriendlyURL = PortalUtil.getGroupFriendlyURL(liveGroup, layout.isPrivateLayout(), themeDisplay);
		}

		liveFriendlyURL = PortalUtil.addPreservedParameters(themeDisplay, liveFriendlyURL);
	}

	String stagingFriendlyURL = null;

	if (stagingGroup != null) {
		try {
			Layout stagingLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), stagingGroup.getGroupId(), layout.isPrivateLayout());

			stagingFriendlyURL = PortalUtil.getLayoutFriendlyURL(stagingLayout, themeDisplay);
		}
		catch (Exception e) {
			stagingFriendlyURL = PortalUtil.getGroupFriendlyURL(stagingGroup, layout.isPrivateLayout(), themeDisplay);
		}

		stagingFriendlyURL = PortalUtil.addPreservedParameters(themeDisplay, stagingFriendlyURL);
	}

	List<LayoutSetBranch> layoutSetBranches = null;

	if (group.isStagingGroup() || group.isStagedRemotely()) {
		layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), layout.isPrivateLayout());
	}
	%>

	<aui:nav collapsible="<%= false %>" cssClass="staging-bar" id="stagingBar">
		<c:if test="<%= !group.isStagedRemotely() && ((liveGroup != null) && layout.isPrivateLayout() ? (liveGroup.getPrivateLayoutsPageCount() > 0) : (liveGroup.getPublicLayoutsPageCount() > 0)) %>">
			<aui:nav-item cssClass='<%= ((layoutSetBranches != null) ? " active" : StringPool.BLANK) + " staging-toggle" %>' href="<%= (layoutSetBranches != null) ? null : stagingFriendlyURL %>" label="staging" />

			<aui:nav-item cssClass='<%= (!group.isStagingGroup() ? "active" : StringPool.BLANK) + " live-link staging-toggle" %>' href="<%= !group.isStagingGroup() ? null : liveFriendlyURL %>" label="live" />
		</c:if>

		<aui:nav-item anchorCssClass="staging-link" cssClass="site-variations" dropdown="<%= true %>" iconClass="icon-cog" label="staging">
			<c:if test="<%= stagingGroup != null %>">
				<aui:nav-item cssClass="row-fluid">
					<c:if test="<%= (layoutSetBranches != null) && (layoutSetBranches.size() >= 1) %>">
						<div class="site-pages-variation-options span6">
							<c:if test="<%= group.isStagingGroup() || layoutSetBranches.size() <= _MAX_INLINE_BRANCHES %>">
								<h5>
									<span class="site-pages-variation-label"><liferay-ui:message key="site-variations-for" /></span>

									<span class="company-name"><%= company.getName() %></span>
								</h5>

								<aui:select cssClass="variation-options" label="" name="sitePageVariations">

									<%
									for (int i = 0; i < layoutSetBranches.size(); i++) {
										LayoutSetBranch curLayoutSetBranch = null;

										if (layoutSetBranches.size() > _MAX_INLINE_BRANCHES) {
											curLayoutSetBranch = layoutSetBranch;
										}
										else {
											curLayoutSetBranch = layoutSetBranches.get(i);
										}

										boolean selected = (group.isStagingGroup() || group.isStagedRemotely()) && (curLayoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());

										String sitePagesVariationLabel = "staging";

										if (layoutSetBranches.size() != 1) {
											 sitePagesVariationLabel = HtmlUtil.escape(curLayoutSetBranch.getName());
										}

										sitePagesVariationLabel = LanguageUtil.get(pageContext, sitePagesVariationLabel);
									%>

										<portlet:actionURL var="layoutSetBranchURL">
											<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
											<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
											<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
											<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
											<portlet:param name="privateLayout" value="<%= String.valueOf(layout.isPrivateLayout()) %>" />
											<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()) %>" />
										</portlet:actionURL>

										<aui:option selected="<%= selected %>" value="<%= layoutSetBranchURL %>">
											<%= sitePagesVariationLabel %>

											<c:if test="<%= selected %>">
												(<liferay-ui:message arguments="<%= layouts.size() %>" key='<%= (layouts.size() == 1) ? "1-page" : "x-pages" %>' />)
											</c:if>
										</aui:option>

									<%
										if (layoutSetBranches.size() > _MAX_INLINE_BRANCHES) {
											break;
										}
									}
									%>

								</aui:select>

								<i class="icon-angle-right"></i>
							</c:if>

							<portlet:renderURL var="layoutSetBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="struts_action" value="/staging_bar/view_layout_set_branches" />
							</portlet:renderURL>

							<div class="manage-layout-set-branches page-variations">
								<aui:icon
									cssClass="manage-layout-set-branches-link"
									id="manageLayoutSetBranches"
									image="cog"
									label="manage-site-pages-variations"
									url="<%= layoutSetBranchesURL %>"
								/>
							</div>
						</div>
					</c:if>

					<%
					UnicodeProperties typeSettingsProperties = null;
					%>

					<c:choose>
						<c:when test="<%= (group.isStagingGroup() || group.isStagedRemotely()) && branchingEnabled %>">
							<div class="page-variations-options span6">
								<h5>
									<span class="page-variation-label"><liferay-ui:message key="page-variations-for" /></span>

									<span class="page-name"><%= HtmlUtil.escape(layout.getName(locale)) %></span>
								</h5>

								<portlet:actionURL var="editLayoutRevisionURL">
									<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
								</portlet:actionURL>

								<aui:form action="<%= editLayoutRevisionURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "savePage();" %>'>
									<aui:input name="<%= Constants.CMD %>" type="hidden" />
									<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
									<aui:input name="groupId" type="hidden" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
									<aui:input name="layoutRevisionId" type="hidden" value="<%= layoutRevision.getLayoutRevisionId() %>" />
									<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutRevision.getLayoutSetBranchId() %>" />
									<aui:input name="updateRecentLayoutRevisionId" type="hidden" value="<%= false %>" />

									<portlet:renderURL var="layoutBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
										<portlet:param name="struts_action" value="/staging_bar/view_layout_branches" />
										<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
									</portlet:renderURL>

									<div class="layout-info">
										<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

										<%
										List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));
										%>

										<c:if test="<%= (layoutRevisions.size() > 1) || (layoutRevisions.size() <= 1) %>">
											<div class="variations-options">
												<aui:select cssClass="variation-options" label="" name="pageVariations">

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
												%>

													<portlet:actionURL var="layoutBranchURL">
														<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
														<portlet:param name="<%= Constants.CMD %>" value="select_layout_branch" />
														<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
														<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
														<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
														<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
													</portlet:actionURL>

													<aui:option label="<%= HtmlUtil.escape(curLayoutBranch.getName()) %>" selected="<%= selected %>" value="<%= layoutBranchURL %>" />

												<%
													if (layoutRevisions.size() > _MAX_INLINE_BRANCHES) {
														break;
													}
												}
												%>

												</aui:select>

												<div class="manage-page-variations page-variations">
													<aui:icon
														cssClass="manage-layout-set-branches-link"
														id="manageLayoutRevisions"
														image="cog"
														label="manage-page-variations"
														url="<%= layoutBranchesURL %>"
													/>
												</div>

												<div class="layout-revision-details" id="<portlet:namespace />layoutRevisionDetails">
													<liferay-util:include page="/html/portlet/staging_bar/view_layout_revision_details.jsp" />
												</div>

												<c:if test="<%= layoutRevisions.size() > _MAX_INLINE_BRANCHES %>">
													<p class="go-to-layout-branches-tab">
														<liferay-ui:icon-menu cssClass="layoutset-branches-menu" direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/signal_instance.png" %>' message="page-variations">

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
																	message="<%= HtmlUtil.escape(curLayoutBranch.getName()) %>"
																	url="<%= selected ? null : rootLayoutRevisionURL %>"
																/>

															<%
															}
															%>

														</liferay-ui:icon-menu>
													</p>
												</c:if>
											</div>
										</c:if>

										<div class="variations-content">
											<c:if test="<%= Validator.isNotNull(layoutBranch.getDescription()) %>">
												<div class="layout-branch-description">
													<%= HtmlUtil.escape(layoutBranch.getDescription()) %>
												</div>
											</c:if>

											<%
											request.setAttribute("view.jsp-layoutRevision", layoutRevision);
											request.setAttribute("view.jsp-layoutSetBranch", layoutSetBranch);
											%>

										</div>
									</div>
								</aui:form>
							</div>

							<liferay-ui:staging cssClass="publish-link" extended="<%= false %>" layoutSetBranchId="<%= layoutRevision.getLayoutSetBranchId() %>" onlyActions="<%= true %>" />

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
													id: '<portlet:namespace />layoutRevisions',
													title: '<%= UnicodeLanguageUtil.get(pageContext, "manage-page-variations") %>',
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
											<liferay-ui:message arguments='<%= "<strong>" + HtmlUtil.escape(layout.getName(locale)) + "</strong>" %>' key="page-x-has-not-been-published-to-live-yet" />
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
														User publisher = UserLocalServiceUtil.getUserByUuidAndCompanyId(lastImportUserUuid, company.getCompanyId());

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
														<liferay-ui:message arguments='<%= new String[] {"<strong>" + HtmlUtil.escape(layout.getName(locale)) + "</strong>", "<em>" + LanguageUtil.get(pageContext, HtmlUtil.escape(lastImportLayoutSetBranchName)) + "</em>"} %>' key='<%= (group.isStagingGroup() || group.isStagedRemotely()) ? "page-x-was-last-published-to-live" : "page-x-was-last-published-from-x" %>' />

														<c:if test="<%= (Validator.isNotNull(lastImportLayoutBranchName) && (layoutRevisions.size() > 1)) || Validator.isNotNull(lastImportLayoutRevisionId) %>">
															<span class="last-publication-variation-details">(
																<c:if test="<%= Validator.isNotNull(lastImportLayoutBranchName) && (layoutRevisions.size() > 1) %>">
																	<span class="variation-name">
																		<liferay-ui:message key="variation" />: <strong><liferay-ui:message key="<%= HtmlUtil.escape(lastImportLayoutBranchName) %>" /></strong>
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
													<liferay-ui:message arguments="<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>" key="x-is-staged" />
												</span>

												<span class="staging-live-help">
													<liferay-ui:message arguments="<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>" key='<%= (group.isStagingGroup() || group.isStagedRemotely()) ? "staging-staging-help-x" : "staging-live-help-x" %>' />
												</span>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>

							<c:if test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
								<liferay-ui:staging cssClass="publish-link" extended="<%= false %>" onlyActions="<%= true %>" />
							</c:if>
						</c:otherwise>
					</c:choose>

					<c:if test="<%= (layoutSetBranches != null) && (layoutSetBranches.size() > _MAX_INLINE_BRANCHES) %>">
						<div class="">
							<liferay-ui:icon-menu cssClass="layoutset-branches-menu" direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/staging.png" %>' message='<%= LanguageUtil.format(pageContext, "site-pages-variations-x", layoutSetBranches.size()) %>'>

								<%
								for (LayoutSetBranch curLayoutSetBranch : layoutSetBranches) {
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
										message="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>"
										url="<%= selected ? null : layoutSetBranchURL %>"
									/>

								<%
								}
								%>

							</liferay-ui:icon-menu>
						</div>
					</c:if>
				</aui:nav-item>

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
										id: '<portlet:namespace />layoutSetBranches',
										title: '<%= UnicodeLanguageUtil.get(pageContext, "manage-site-pages-variations") %>',
										uri: event.currentTarget.attr('href')
									}
								);
							}
						);
					}
				</aui:script>
			</c:if>
		</aui:nav-item>

		<c:if test="<%= group.isStagedRemotely() %>">

			<%
			UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

			String remoteAddress = typeSettingsProperties.getProperty("remoteAddress");
			int remotePort = GetterUtil.getInteger(typeSettingsProperties.getProperty("remotePort"));
			String remotePathContext = typeSettingsProperties.getProperty("remotePathContext");
			boolean secureConnection = GetterUtil.getBoolean(typeSettingsProperties.getProperty("secureConnection"));
			long remoteGroupId = GetterUtil.getLong(typeSettingsProperties.getProperty("remoteGroupId"));

			String remoteURL = StagingUtil.buildRemoteURL(remoteAddress, remotePort, remotePathContext, secureConnection, remoteGroupId, layout.isPrivateLayout());
			%>

			<aui:nav-item cssClass="remote-live-link" href="<%= remoteURL %>" iconClass="icon-external-link-sign" label="go-to-remote-live" />
		</c:if>
	</aui:nav>

	<c:if test="<%= !branchingEnabled %>">
		<aui:script use="liferay-staging">
			Liferay.StagingBar.init(
				{
					namespace: '<portlet:namespace />',
					portletId: '<%= portletDisplay.getId() %>'
				}
			);
		</aui:script>
	</c:if>
</c:if>

<%!
private static final int _MAX_INLINE_BRANCHES = 8;
%>