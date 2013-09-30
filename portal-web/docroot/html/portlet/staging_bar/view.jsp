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
		<c:if test="<%= (liveGroup != null) && layout.isPrivateLayout() ? (liveGroup.getPrivateLayoutsPageCount() > 0) : (liveGroup.getPublicLayoutsPageCount() > 0) %>">
			<c:choose>
				<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
					<c:if test="<%= stagingGroup != null %>">
						<aui:nav-item anchorCssClass="staging-link" cssClass="active staging-toggle site-variations" dropdown="<%= true %>" id="stagingLink" label="staging" toggle="<%= true %>">
							<aui:nav-item cssClass="row-fluid">
								<c:if test="<%= (layoutSetBranches != null) && (layoutSetBranches.size() >= 1) %>">
									<div class="site-pages-variation-options span6">
										<h5>
											<span class="site-pages-variation-label"><liferay-ui:message key="site-variations-for" /></span>

											<span class="site-name"><%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %></span>
										</h5>

										<aui:select cssClass="variation-options" label="" name="sitePageVariations">

											<%
											for (LayoutSetBranch curLayoutSetBranch : layoutSetBranches) {
												boolean selected = (group.isStagingGroup() || group.isStagedRemotely()) && (curLayoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());

												String sitePagesVariationLabel = "staging";

												if (layoutSetBranches.size() != 1) {
													sitePagesVariationLabel = HtmlUtil.escape(curLayoutSetBranch.getName());
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

												<aui:option selected="<%= selected %>" value="<%= layoutSetBranchURL %>">
													<liferay-ui:message key="<%= sitePagesVariationLabel %>" />

													<c:if test="<%= selected %>">
														(<liferay-ui:message arguments="<%= layouts.size() %>" key='<%= (layouts.size() == 1) ? "1-page" : "x-pages" %>' />)
													</c:if>
												</aui:option>

											<%
											}
											%>

										</aui:select>

										<i class="icon-angle-right"></i>

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

													<div class="variations-options">
														<aui:select cssClass="variation-options" label="" name="pageVariations">

														<%
														for (LayoutRevision rootLayoutRevision : layoutRevisions) {
															LayoutBranch curLayoutBranch = rootLayoutRevision.getLayoutBranch();
														%>

															<portlet:actionURL var="layoutBranchURL">
																<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
																<portlet:param name="<%= Constants.CMD %>" value="select_layout_branch" />
																<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
																<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
																<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
																<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
															</portlet:actionURL>

															<aui:option label="<%= HtmlUtil.escape(curLayoutBranch.getName()) %>" selected="<%= curLayoutBranch.getLayoutBranchId() == layoutRevision.getLayoutBranchId() %>" value="<%= layoutBranchURL %>" />

														<%
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
													</div>

													<c:if test="<%= Validator.isNotNull(layoutBranch.getDescription()) %>">
														<div class="variations-content">
															<div class="layout-branch-description">
																<%= HtmlUtil.escape(layoutBranch.getDescription()) %>
															</div>
														</div>

														<%
														request.setAttribute("view.jsp-layoutRevision", layoutRevision);
														request.setAttribute("view.jsp-layoutSetBranch", layoutSetBranch);
														%>
													</c:if>
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
													request.setAttribute("view.jsp-typeSettingsProperties", liveLayout.getTypeSettingsProperties());
													%>

													<liferay-util:include page="/html/portlet/staging_bar/last_publication_date_message.jsp" />
												</c:otherwise>
											</c:choose>
										</div>

										<c:if test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
											<liferay-ui:staging cssClass="publish-link" extended="<%= false %>" onlyActions="<%= true %>" />
										</c:if>
									</c:otherwise>
								</c:choose>
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
						</aui:nav-item>
					</c:if>
				</c:when>
				<c:otherwise>
					<aui:nav-item cssClass='<%= ((layoutSetBranches != null) ? " active" : StringPool.BLANK) + " staging-toggle" %>' href="<%= (layoutSetBranches != null) ? null : stagingFriendlyURL %>" label="staging" />
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="<%= group.isStagedRemotely() %>">

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
				</c:when>
				<c:when test="<%= group.isStagingGroup() %>">
					<aui:nav-item cssClass='<%= (!group.isStagingGroup() ? "active" : StringPool.BLANK) + " live-link staging-toggle" %>' href="<%= !group.isStagingGroup() ? null : liveFriendlyURL %>" label="live" />
				</c:when>
				<c:otherwise>
					<aui:nav-item anchorCssClass="staging-link" cssClass="active live-link staging-toggle" dropdown="<%= true %>" id="liveLink" label="live" toggle="<%= true %>">
						<aui:nav-item cssClass="row-fluid">
							<div class="staging-details">

								<%
								request.setAttribute("view.jsp-typeSettingsProperties", liveLayout.getTypeSettingsProperties());
								%>

								<liferay-util:include page="/html/portlet/staging_bar/last_publication_date_message.jsp" />
							</div>
						</aui:nav-item>
					</aui:nav-item>
				</c:otherwise>
			</c:choose>
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