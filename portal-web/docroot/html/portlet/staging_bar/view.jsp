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
LayoutRevision layoutRevision = null;

if (layout != null) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);
}
%>

<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	String liveFriendlyURL = null;

	if (liveGroup != null) {
		try {
			Layout liveLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId());

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
	%>

	<div class="staging-bar">
		<div class="staging-tabs">
			<c:if test="<%= liveGroup != null %>">
				<span class="tab-container">
					<aui:a cssClass='<%= "tab" + (!group.isStagingGroup() ? " selected" : StringPool.BLANK) %>' href="<%= !group.isStagingGroup() ? null : liveFriendlyURL %>" label="live" />
				</span>
			</c:if>

			<c:if test="<%= stagingGroup != null %>">

				<%
				List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), layout.isPrivateLayout());
				%>

				<c:choose>
					<c:when test="<%= !layoutSetBranches.isEmpty() %>">

						<%
						for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
							boolean selected = group.isStagingGroup() && (layoutRevision != null) && (layoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());
						%>

							<portlet:actionURL var="layoutSetBranchURL">
								<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
								<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
								<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(layoutSetBranch.getGroupId()) %>" />
								<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
							</portlet:actionURL>

							<span class="tab-container">
								<aui:a cssClass='<%= "layout-set-branch tab" + (selected ? " selected" : StringPool.BLANK) %>' href="<%= selected ? null : layoutSetBranchURL %>" label="<%= layoutSetBranch.getName() %>" />

								<liferay-ui:staging extended="<%= false %>" layoutSetBranchId="<%= layoutSetBranch.getLayoutSetBranchId() %>" />
							</span>

						<%
						}
						%>

						<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="layoutSetBranchesURL">
							<portlet:param name="struts_action" value="/staging_bar/view_layout_set_branches" />
						</portlet:renderURL>

						<liferay-ui:icon cssClass="manage-backstages" id="manageBackstages" image="configuration" label="<%= true %>" message="manage-backstages" url="<%= layoutSetBranchesURL %>" />

						<aui:script use="aui-base">
							var layoutSetBranchesLink = A.one('#<portlet:namespace />manageBackstages');

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
												id: '<portlet:namespace />',
												title: '<liferay-ui:message key="manage-backstages" />',
												uri: event.currentTarget.attr('href')
											}
										);
									}
								);
							}
						</aui:script>
					</c:when>
					<c:otherwise>

						<%
						boolean selected = group.isStagingGroup() || group.isStagedRemotely();
						%>

						<span class="tab-container">
							<aui:a cssClass='<%= "tab" +  (selected ? " selected" : StringPool.BLANK)  %>' href="<%= selected ? null : stagingFriendlyURL %>" label="backstage" />

							<liferay-ui:staging extended="<%= false %>" />
						</span>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>

		<div class="staging-tabs-content">
			<aui:layout>
				<c:choose>
					<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
						<aui:column>
							<img alt="" class="staging-icon" src="<%= themeDisplay.getPathThemeImages() %>/staging_bar/backstage.png" />
						</aui:column>

						<c:if test="<%= layoutRevision != null %>">
							<aui:column columnWidth="50">
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

									<div class="layout-info">
										<div class="layout-title">
											<label><liferay-ui:message key="page" /></label>

											<em><%= layoutRevision.getName(locale) %></em>
										</div>

										<aui:workflow-status status='<%= layoutRevision.getStatus() %>' version="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
									</div>

									<div class="layout-actions">
										<span class="backstage-toolbar" id="<portlet:namespace />backstageToolbar"></span>
									</div>
								</aui:form>
							</aui:column>

							<aui:script use="liferay-staging">
								var dockbar = Liferay.Staging.Dockbar;

								dockbar.init(
									{
										namespace: '<portlet:namespace />'
									}
								);

								<c:if test="<%= !layoutRevision.isMajor() && (!layoutRevision.hasChildren()) && (layoutRevision.getParentLayoutRevisionId() != LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID) %>">
									dockbar.backstageToolbar.add(dockbar.undoButton, 0);
								</c:if>
							</aui:script>
						</c:if>
					</c:when>
					<c:otherwise>
						<img alt="" class="staging-icon" src="<%= themeDisplay.getPathThemeImages() %>/staging_bar/live.png" />

						<%
						UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

						long lastImportDate = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-date"));
						%>

						<c:choose>
							<c:when test="<%= lastImportDate > 0 %>">

								<%
								String layoutSetBranchName = null;

								long layoutSetBranchId = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-layout-set-branch-id"));

								if (layoutSetBranchId > 0) {

									try {
										LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

										layoutSetBranchName = layoutSetBranch.getName();
									}
									catch(Exception e) {
									}
								}

								if (Validator.isNull(layoutSetBranchName)) {
									layoutSetBranchName = typeSettingsProperties.getProperty("last-import-layout-set-branch-name");
								}

								if (Validator.isNull(layoutSetBranchName)) {
									layoutSetBranchName = LanguageUtil.get(pageContext, "backstage");
								}

								String variationName = null;

								List<LayoutRevision> rootRevisions = new ArrayList<LayoutRevision>();

								long layoutRevisionId = GetterUtil.getLong(typeSettingsProperties.getProperty("last-import-layout-revision-id"));

								if (layoutRevisionId > 0) {
									try {
										LayoutRevision importedLayoutRevision = LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutRevisionId);

										variationName = importedLayoutRevision.getVariationName();

										rootRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(importedLayoutRevision.getLayoutSetBranchId(), 0, importedLayoutRevision.getPlid());
									}
									catch(Exception e) {
									}
								}

								if (Validator.isNull(variationName)) {
									variationName = typeSettingsProperties.getProperty("last-import-variation-name");
								}

								String publisherName = null;

								String userUuid = GetterUtil.getString(typeSettingsProperties.getProperty("last-import-user-uuid"));

								if (Validator.isNotNull(userUuid)) {
									try {
										User publisher = UserLocalServiceUtil.getUserByUuid(userUuid);

										publisherName = publisher.getFullName();
									}
									catch(Exception e) {
									}
								}

								if (Validator.isNull(publisherName)) {
									publisherName = typeSettingsProperties.getProperty("last-import-user-name");
								}
								%>

								<c:if test="<%= Validator.isNotNull(layoutSetBranchName) && Validator.isNotNull(publisherName) %>">
									<span class="last-publication-branch">
										<liferay-ui:message arguments="<%= layoutSetBranchName %>" key="last-publication-from-x" />

										<c:if test="<%= (Validator.isNotNull(variationName) && (rootRevisions.size() > 1)) || Validator.isNotNull(layoutRevisionId) %>">
											<span class="last-publication-variation-details">(
												<c:if test="<%= Validator.isNotNull(variationName) && (rootRevisions.size() > 1) %>">
													<span class="variation-name"><liferay-ui:message key="variation" />: <strong><%= variationName %></strong></span>
												</c:if>

												<c:if test="<%= Validator.isNotNull(layoutRevisionId) %>">
													<span class="layout-version"><liferay-ui:message key="version" />: <strong><%= layoutRevisionId %></strong></span>
												</c:if>
											)</span>
										</c:if>
									</span>

									<span class="last-publication-user"><liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(pageContext, (System.currentTimeMillis() - lastImportDate), true), publisherName} %>" key="x-ago-by-x" /></span>
								</c:if>
							</c:when>
							<c:otherwise>
								<span class="staging-live-group-name"><liferay-ui:message arguments="<%= liveGroup.getDescriptiveName() %>" key="x-is-staged" /></span>

								<span class="staging-live-help"><liferay-ui:message arguments="<%= liveGroup.getDescriptiveName() %>" key="staging-live-help-x" /></span>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</aui:layout>
		</div>
	</div>
</c:if>