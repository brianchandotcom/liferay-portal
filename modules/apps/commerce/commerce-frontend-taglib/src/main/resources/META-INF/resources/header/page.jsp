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

<%@ include file="/header/init.jsp" %>

<%
boolean isWorkflowedModel = false;

if (bean instanceof WorkflowedModel) {
	isWorkflowedModel = true;

	if (transitionPortletURL != null) {
		actions.addAll(0, HeaderHelperUtil.getWorkflowTransitionHeaderActionModels(themeDisplay.getUserId(), themeDisplay.getCompanyId(), model.getName(), beanId, transitionPortletURL));
	}
}

String myWorkflowTasksPortletNamespace = PortalUtil.getPortletNamespace(PortletKeys.MY_WORKFLOW_TASK);
%>

<div class="bg-white border-bottom commerce-header<%= fullWidth ? " container-fluid" : StringPool.BLANK %><%= Validator.isNotNull(wrapperCssClasses) ? StringPool.SPACE + wrapperCssClasses : StringPool.BLANK %> side-panel-top-anchor">
	<div class="container<%= Validator.isNotNull(cssClasses) ? StringPool.SPACE + HtmlUtil.escapeAttribute(cssClasses) : StringPool.BLANK %>">
		<div class="align-items-center c-py-3 c-py-lg-2 d-lg-flex">
			<div class="align-items-center d-flex">
				<c:if test="<%= Validator.isNotNull(thumbnailUrl) %>">
					<span class="d-none d-sm-block sticker sticker-xl">
						<span class="sticker-overlay">
							<img alt="thumbnail" class="sticker-img" src="<%= HtmlUtil.escapeAttribute(thumbnailUrl) %>" />
						</span>
					</span>
				</c:if>

				<div class="border-right c-ml-sm-2 c-mr-3 c-pr-3 header-details">
					<h3 class="c-mb-0 commerce-header-title text-truncate">
						<%= HtmlUtil.escape(title) %>
					</h3>

					<c:if test="<%= isWorkflowedModel %>">

						<%
						WorkflowedModel workflowedModel = (WorkflowedModel)bean;
						%>

						<c:if test="<%= workflowedModel != null %>">
							<aui:workflow-status bean="<%= bean %>" model="<%= model %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= workflowedModel.getStatus() %>" />
						</c:if>
					</c:if>
				</div>

				<div class="header-info">
					<c:if test="<%= Validator.isNotNull(beanIdLabel) %>">
						<div class="align-items-center d-flex">
							<span class="header-info-title">
								<liferay-ui:message key="<%= HtmlUtil.escape(beanIdLabel) %>" />:
							</span>

							<strong class="c-ml-1 header-info-value">
								<%= beanId %>
							</strong>

							<span class="c-ml-1 lfr-portal-tooltip text-secondary" title="<%= LanguageUtil.get(request, "identification-number") %>">
								<clay:icon
									symbol="question-circle"
								/>
							</span>
						</div>
					</c:if>

					<c:if test="<%= Validator.isNotNull(externalReferenceCode) || Validator.isNotNull(externalReferenceCodeEditUrl) %>">
						<div class="align-items-center c-mt-n2 d-flex">
							<span class="header-info-title">
								<liferay-ui:message key="erc" />:
							</span>

							<strong class="c-ml-1 header-info-value">
								<%= HtmlUtil.escape(externalReferenceCode) %>
							</strong>

							<span class="c-ml-1 lfr-portal-tooltip text-secondary" title="<%= LanguageUtil.get(request, "external-reference-code") %>">
								<clay:icon
									symbol="question-circle"
								/>
							</span>

							<c:if test="<%= Validator.isNotNull(externalReferenceCodeEditUrl) %>">
								<clay:button
									cssClass="c-ml-1 c-p-0 h-auto text-secondary w-auto"
									displayType="link"
									icon="pencil"
									id="erc-edit-modal-opener"
									small="<%= true %>"
								/>

								<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events">
									document
										.querySelector('#erc-edit-modal-opener')
										.addEventListener('click', (e) => {
											e.preventDefault();
											Liferay.fire(events.OPEN_MODAL, {id: 'erc-edit-modal'});
										});
								</aui:script>

								<commerce-ui:modal
									id="erc-edit-modal"
									refreshPageOnClose="<%= true %>"
									title='<%= LanguageUtil.format(request, "edit-x", "external-reference-code") %>'
									url="<%= externalReferenceCodeEditUrl %>"
								/>
							</c:if>
						</div>
					</c:if>
				</div>
			</div>

			<hr class="d-lg-none" />

			<div class="align-items-center c-ml-auto d-flex justify-content-end">
				<c:if test="<%= Validator.isNotNull(reviewWorkflowTask) %>">

					<%
					boolean assignedToCurrentUser = false;

					if (reviewWorkflowTask.getAssigneeUserId() == user.getUserId()) {
						assignedToCurrentUser = true;
					}

					String assignee = PortalUtil.getUserName(reviewWorkflowTask.getAssigneeUserId(), "nobody");

					if (assignedToCurrentUser) {
						assignee = "me";
					}
					%>

					<div class="border-right c-mr-1 c-mr-sm-3 c-pr-sm-3 position-relative">
						<div class="bg-white c-px-1 header-assign-label position-absolute text-secondary">
							<liferay-ui:message key="assigned-to" />:
						</div>

						<button aria-expanded="false" aria-haspopup="true" class="align-items-center btn btn-secondary d-flex dropdown-toggle header-assign-button justify-content-between" data-toggle="dropdown" onclick="<portlet:namespace />toggleDropdown();" type="button">
							<liferay-ui:message key="<%= HtmlUtil.escape(assignee) %>" />

							<clay:icon
								symbol="caret-bottom"
							/>
						</button>

						<div class="dropdown-menu dropdown-menu-right" id="<portlet:namespace />commerce-dropdown-assigned-to">
							<c:if test="<%= !assignedToCurrentUser %>">
								<clay:button
									cssClass="dropdown-item transition-link"
									displayType="secondary"
									id='<%= liferayPortletResponse.getNamespace() + "assign-to-me-modal-opener" %>'
									label='<%= LanguageUtil.get(request, "assign-to-me") %>'
									small="<%= false %>"
								/>

								<liferay-portlet:renderURL portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="assignToMeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
									<portlet:param name="mvcPath" value="/workflow_task_assign.jsp" />
									<portlet:param name="hideDefaultSuccessMessage" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="workflowTaskId" value="<%= String.valueOf(reviewWorkflowTask.getWorkflowTaskId()) %>" />
									<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
								</liferay-portlet:renderURL>

								<aui:script>
									document
										.querySelector('#<portlet:namespace />assign-to-me-modal-opener')
										.addEventListener('click', (e) => {
											Liferay.Util.openWindow({
												dialog: {
													destroyOnHide: true,
													height: 430,
													resizable: false,
													width: 896,
												},
												dialogIframe: {
													bodyCssClass: 'dialog-with-footer task-dialog',
												},
												id: '<%= myWorkflowTasksPortletNamespace %>assignToDialog',
												title: '<liferay-ui:message key="assign-to-me" />',
												uri: '<%= HtmlUtil.escapeJS(assignToMeURL) %>',
											});
										});
								</aui:script>
							</c:if>

							<clay:button
								cssClass="dropdown-item transition-link"
								displayType="secondary"
								id='<%= liferayPortletResponse.getNamespace() + "assign-to-modal-opener" %>'
								label='<%= LanguageUtil.get(request, "assign-to-...") %>'
								small="<%= false %>"
							/>

							<liferay-portlet:renderURL portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="assignToURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="mvcPath" value="/workflow_task_assign.jsp" />
								<portlet:param name="hideDefaultSuccessMessage" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="workflowTaskId" value="<%= String.valueOf(reviewWorkflowTask.getWorkflowTaskId()) %>" />
							</liferay-portlet:renderURL>

							<aui:script>
								document
									.querySelector('#<portlet:namespace />assign-to-modal-opener')
									.addEventListener('click', (e) => {
										Liferay.Util.openWindow({
											dialog: {
												destroyOnHide: true,
												height: 430,
												resizable: false,
												width: 896,
											},
											dialogIframe: {
												bodyCssClass: 'dialog-with-footer task-dialog',
											},
											id: '<%= myWorkflowTasksPortletNamespace %>assignToDialog',
											title: '<liferay-ui:message key="assign-to-..." />',
											uri: '<%= HtmlUtil.escapeJS(assignToURL) %>',
										});
									});

								function <%= myWorkflowTasksPortletNamespace %>refreshPortlet() {
									window.location.reload();
								}

								Liferay.provide(window, '<portlet:namespace />toggleDropdown', () => {
									var dropdownElement = window.document.querySelector(
										'#<portlet:namespace />commerce-dropdown-assigned-to'
									);

									if (dropdownElement) {
										if (dropdownElement.classList.contains('show')) {
											dropdownElement.classList.remove('show');
										}
										else {
											dropdownElement.classList.add('show');
										}
									}
								});
							</aui:script>
						</div>
					</div>
				</c:if>

				<c:if test="<%= Validator.isNotNull(actions) && !actions.isEmpty() %>">
					<div class="header-actions">

						<%
						for (HeaderActionModel action : actions) {
							String buttonCssClasses = "btn c-mb-1 c-mb-sm-0 ";

							if (Validator.isNotNull(action.getAdditionalClasses())) {
								buttonCssClasses += HtmlUtil.escapeAttribute(action.getAdditionalClasses());
							}
							else {
								buttonCssClasses += "btn-secondary";
							}

							boolean submitCheck = Validator.isNull(action.getId());

							String actionId = Validator.isNotNull(action.getId()) ? action.getId() : "header-action_" + PortalUtil.generateRandomKey(request, "taglib_step_tracker");
						%>

							<clay:link
								cssClass="<%= HtmlUtil.escape(buttonCssClasses) %>"
								href="<%= Validator.isNotNull(action.getHref()) ? action.getHref() : StringPool.POUND %>"
								id="<%= HtmlUtil.escape(actionId) %>"
								label="<%= LanguageUtil.get(request, HtmlUtil.escape(action.getLabel())) %>"
							/>

							<c:if test="<%= submitCheck && Validator.isNotNull(action.getFormId()) %>">
								<aui:script>
									document
										.getElementById('<%= HtmlUtil.escapeJS(actionId) %>')
										.addEventListener('click', (e) => {
											e.preventDefault();
											var form = document.getElementById(
												'<%= HtmlUtil.escapeJS(action.getFormId()) %>'
											);
											if (!form) {
												throw new Error(
													'Form with id: ' +
														<%= HtmlUtil.escapeJS(action.getFormId()) %> +
														' not found!'
												);
											}
											submitForm(form);
										});
								</aui:script>
							</c:if>

						<%
						}
						%>

					</div>
				</c:if>

				<c:if test="<%= Validator.isNotNull(dropdownItems) || Validator.isNotNull(previewUrl) %>">
					<c:if test="<%= Validator.isNotNull(dropdownItems) && (dropdownItems.size() > 0) %>">
						<div class="c-ml-3" id="dropdown-header-container">
							<liferay-ui:icon
								icon="ellipsis-v"
								markupView="lexicon"
							/>
						</div>

						<aui:script require="commerce-frontend-js/components/dropdown/entry as dropdown">
							dropdown.default('dropdown-header', 'dropdown-header-container', {
								items: <%= jsonSerializer.serializeDeep(dropdownItems) %>,
								spritemap:
									'<%= themeDisplay.getPathThemeImages() + "/lexicon/icons.svg" %>',
							});
						</aui:script>
					</c:if>

					<c:if test="<%= Validator.isNotNull(previewUrl) %>">
						<clay:link
							cssClass="btn btn-outline-borderless btn-outline-secondary btn-sm text-primary"
							href="<%= previewUrl %>"
							icon="shortcut"
						/>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
</div>

<aui:script require="commerce-frontend-js/utilities/debounce as debounce">
	var commerceHeader = document.querySelector('.commerce-header');
	var pageHeader = document.querySelector('.page-header');

	function updateMenuDistanceFromTop() {
		if (!commerceHeader || !commerceHeader.getClientRects()[0]) return;
		var distanceFromTop = commerceHeader.getClientRects()[0].bottom;
		pageHeader.style.top = distanceFromTop + 'px';
	}

	var debouncedUpdateMenuDistanceFromTop = debounce.default(
		updateMenuDistanceFromTop,
		200
	);

	if (pageHeader) {
		pageHeader.classList.add('sticky-header-menu');
		updateMenuDistanceFromTop();
		window.addEventListener('resize', debouncedUpdateMenuDistanceFromTop);

		Liferay.once('beforeNavigate', () => {
			window.removeEventListener(
				'resize',
				debouncedUpdateMenuDistanceFromTop
			);
		});
	}
</aui:script>