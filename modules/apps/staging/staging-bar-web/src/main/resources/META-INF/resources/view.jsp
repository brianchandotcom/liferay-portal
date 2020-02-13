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

<%@ include file="/init.jsp" %>

<%
boolean branchingEnabled = GetterUtil.getBoolean((String)renderRequest.getAttribute(StagingProcessesWebKeys.BRANCHING_ENABLED));
LayoutRevision layoutRevision = (LayoutRevision)renderRequest.getAttribute(WebKeys.LAYOUT_REVISION);
List<LayoutSetBranch> layoutSetBranches = (List<LayoutSetBranch>)renderRequest.getAttribute(StagingProcessesWebKeys.LAYOUT_SET_BRANCHES);
liveGroup = (Group)renderRequest.getAttribute(StagingProcessesWebKeys.LIVE_GROUP);
Layout liveLayout = (Layout)renderRequest.getAttribute(StagingProcessesWebKeys.LIVE_LAYOUT);
String liveURL = (String)renderRequest.getAttribute(StagingProcessesWebKeys.LIVE_URL);
String remoteSiteURL = (String)renderRequest.getAttribute(StagingProcessesWebKeys.REMOTE_SITE_URL);
stagingGroup = (Group)renderRequest.getAttribute(StagingProcessesWebKeys.STAGING_GROUP);
String stagingURL = (String)renderRequest.getAttribute(StagingProcessesWebKeys.STAGING_URL);

if (liveLayout != null) {
	request.setAttribute("view.jsp-typeSettingsProperties", liveLayout.getTypeSettingsProperties());
}
%>

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">
	<c:if test="<%= liveGroup != null %>">
		<nav class="navbar navbar-collapse-absolute navbar-expand navbar-underline navigation-bar navigation-bar-secondary staging-navbar">
			<div class="container-fluid container-fluid-max-xl">
				<ul class="navbar-nav">
					<c:choose>
						<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
							<c:if test="<%= stagingGroup != null %>">
								<li class="nav-item">
									<a class="active nav-link" id="stagingLink" value="staging">
										<liferay-ui:message key="staging" />
									</a>
								</li>
							</c:if>
						</c:when>
						<c:otherwise>
							<li class="nav-item">
								<a class="nav-link" href="<%= (layoutSetBranches != null) ? null : stagingURL %>" value="staging">
									<liferay-ui:message key="staging" />
								</a>
							</li>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="<%= group.isStagedRemotely() %>">
							<li class="nav-item">
								<c:choose>
									<c:when test="<%= !remoteSiteURL.isEmpty() %>">
										<a class="nav-link href="<%= HtmlUtil.escape(remoteSiteURL) %>" value="go-to-remote-live">
											<aui:icon image="home" label="go-to-remote-live" markupView="lexicon" />
										</a>
									</c:when>
									<c:when test="<%= SessionErrors.contains(renderRequest, AuthException.class) %>">
										<a class="nav-link" value="go-to-remote-live">
											<aui:icon image="home" label="go-to-remote-live" markupView="lexicon" />
										</a>

										<liferay-ui:icon
											icon="exclamation-full"
											markupView="lexicon"
											message="an-error-occurred-while-authenticating-user"
											toolTip="<%= true %>"
										/>
									</c:when>
									<c:when test="<%= SessionErrors.contains(renderRequest, RemoteExportException.class) %>">
										<a class="nav-link" value="go-to-remote-live">
											<aui:icon image="home" label="go-to-remote-live" markupView="lexicon" />
										</a>

										<liferay-ui:icon
											icon="exclamation-full"
											markupView="lexicon"
											message="the-connection-to-the-remote-live-site-cannot-be-established-due-to-a-network-problem"
											toolTip="<%= true %>"
										/>
									</c:when>
									<c:otherwise>
										<a class="nav-link" value="go-to-remote-live">
											<aui:icon image="home" label="go-to-remote-live" markupView="lexicon" />
										</a>

										<liferay-ui:icon
											icon="exclamation-full"
											markupView="lexicon"
											message="an-unexpected-error-occurred"
											toolTip="<%= true %>"
										/>
									</c:otherwise>
								</c:choose>
							</li>
						</c:when>
						<c:when test="<%= group.isStagingGroup() %>">
							<c:if test="<%= Validator.isNotNull(liveURL) %>">
								<li class="nav-item">
									<a class="nav-link" href="<%= HtmlUtil.escape(liveURL) %>" value="live">
										<liferay-ui:message key="live" />
									</a>
								</li>
							</c:if>
						</c:when>
						<c:otherwise>
							<li class="nav-item">
								<a class="active nav-link" id="liveLink" value="live">
									<liferay-ui:message key="live" />
								</a>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>

				<button class="btn btn-monospaced staging-toggle" id="closeStagingOptions" title="<%= LanguageUtil.get(request, "view-page-staging-options") %>">
					<liferay-ui:icon
						icon="info-circle"
						markupView="lexicon"
					/>

					<liferay-ui:icon
						icon="times-circle"
						markupView="lexicon"
					/>
				</button>
			</div>
		</nav>

		<c:if test="<%= !layout.isSystem() || layout.isTypeControlPanel() || !Objects.equals(layout.getFriendlyURL(), PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL) %>">
			<div class="staging-bar">
				<div class="container-fluid container-fluid-max-xl">
					<div class="row">
						<c:choose>
							<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
								<c:if test="<%= stagingGroup != null %>">
									<liferay-ui:error exception="<%= AuthException.class %>">
										<liferay-ui:message arguments="<%= user.getScreenName() %>" key="an-error-occurred-while-authenticating-user-x-on-the-remote-server" />
									</liferay-ui:error>

									<liferay-ui:error exception="<%= Exception.class %>" message="an-unexpected-error-occurred" />

									<c:choose>
										<c:when test="<%= branchingEnabled %>">
											<div class="col">
												<liferay-util:include page="/view_layout_set_branch_details.jsp" servletContext="<%= application %>" />
											</div>

											<div class="col">
												<c:if test="<%= !layoutRevision.isIncomplete() %>">
													<liferay-util:include page="/view_layout_branch_details.jsp" servletContext="<%= application %>" />
												</c:if>
											</div>

											<div class="col staging-alert-container" id="<portlet:namespace />layoutRevisionStatus">
												<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

												<liferay-util:include page="/view_layout_revision_status.jsp" servletContext="<%= application %>" />
											</div>

											<div class="col" id="<portlet:namespace />layoutRevisionDetails">
												<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

												<liferay-util:include page="/view_layout_revision_details.jsp" servletContext="<%= application %>" />
											</div>
										</c:when>
										<c:otherwise>
											<div class="col staging-alert-container">
												<c:choose>
													<c:when test="<%= liveLayout == null %>">
														<span class="last-publication-branch">
															<liferay-ui:message arguments='<%= "<strong>" + HtmlUtil.escape(layout.getName(locale)) + "</strong>" %>' key="page-x-has-not-been-published-to-live-yet" translateArguments="<%= false %>" />
														</span>
													</c:when>
													<c:otherwise>
														<liferay-util:include page="/last_publication_date_message.jsp" servletContext="<%= application %>" />
													</c:otherwise>
												</c:choose>
											</div>

											<div class="col-md-2 col-sm-3 staging-button-container">
												<liferay-staging:menu
													cssClass="publish-link test5"
													onlyActions="<%= true %>"
												/>
											</div>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:when>
							<c:otherwise>
								<div class="col staging-alert-container">
									<div class="alert alert-warning hide warning-content" id="<portlet:namespace />warningMessage">
										<liferay-ui:message key="an-inital-staging-publication-is-in-progress" />
									</div>

									<liferay-util:include page="/last_publication_date_message.jsp" servletContext="<%= application %>" />
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</c:if>
	</c:if>

	<c:if test="<%= !branchingEnabled %>">
		<aui:script use="liferay-staging">
			Liferay.StagingBar.init({
				namespace: '<portlet:namespace />',
				portletId: '<%= portletDisplay.getId() %>'
			});
		</aui:script>
	</c:if>

	<aui:script use="aui-base">
		var staging = document.querySelector('.staging');

		if (staging) {
			var stagingToggle = document.querySelector('.staging-toggle');

			if (stagingToggle) {
				stagingToggle.addEventListener('click', function(event) {
					event.preventDefault();

					staging.classList.toggle('staging-show');
				});
			}
		}

		var stagingLink = document.getElementById('<portlet:namespace />stagingLink');
		var warningMessage = document.getElementById(
			'<portlet:namespace />warningMessage'
		);

		var checkBackgroundTasks = function() {
			Liferay.Service(
				'/backgroundtask.backgroundtask/get-background-tasks-count',
				{
					completed: false,
					groupId: '<%= liveGroup.getGroupId() %>',
					taskExecutorClassName:
						'<%= BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR %>'
				},
				function(obj) {
					var incomplete = obj > 0;

					if (incomplete) {
						if (stagingLink) {
							stagingLink.classList.add('hide');
						}

						if (warningMessage) {
							warningMessage.classList.remove('hide');
						}

						setTimeout(checkBackgroundTasks, 5000);
					}
					else {
						if (stagingLink) {
							stagingLink.classList.remove('hide');
						}

						if (warningMessage) {
							warningMessage.classList.add('hide');
						}
					}
				}
			);
		};

		checkBackgroundTasks();
	</aui:script>
</c:if>