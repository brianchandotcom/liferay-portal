<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<div class="bg-white border-bottom commerce-header<%= Validator.isNotNull(wrapperCssClasses) ? StringPool.SPACE + wrapperCssClasses : StringPool.BLANK %> side-panel-top-anchor">
	<div class="container-fluid container-fluid-max-xxxl<%= Validator.isNotNull(cssClasses) ? StringPool.SPACE + HtmlUtil.escapeAttribute(cssClasses) : StringPool.BLANK %>">
		<div class="align-items-center c-py-3 c-py-lg-2 d-lg-flex">
			<div class="align-items-center d-flex">
				<c:if test="<%= Validator.isNotNull(thumbnailURL) %>">
					<span class="d-none d-sm-block sticker sticker-xl">
						<span class="sticker-overlay">
							<img alt="thumbnail" class="sticker-img" src="<%= HtmlUtil.escapeAttribute(thumbnailURL) %>" />
						</span>
					</span>
				</c:if>

				<div class="border-right c-ml-sm-2 c-mr-3 c-pr-3 header-details">
					<h3 class="c-mb-0 commerce-header-title text-truncate" data-qa-id="headerDetailsTitle">
						<%= HtmlUtil.escape(title) %>
					</h3>

					<c:if test="<%= isWorkflowedModel %>">

						<%
						WorkflowedModel workflowedModel = (WorkflowedModel)bean;
						%>

						<c:if test="<%= workflowedModel != null %>">
							<c:choose>
								<c:when test="<%= bean instanceof GroupedModel %>">
									<aui:workflow-status bean="<%= bean %>" model="<%= model %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= workflowedModel.getStatus() %>" />
								</c:when>
								<c:otherwise>
									<aui:workflow-status model="<%= model %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= workflowedModel.getStatus() %>" />
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>

					<c:if test="<%= Validator.isNotNull(additionalStatusLabel) %>">
						<clay:label
							displayType='<%= Validator.isNull(additionalStatusLabelStyle) ? "secondary" : additionalStatusLabelStyle %>'
							label="<%= additionalStatusLabel %>"
						/>
					</c:if>
				</div>

				<div class="header-info">
					<c:if test="<%= Validator.isNotNull(beanIdLabel) %>">
						<div class="align-items-center d-flex py-1">
							<span class="header-info-title text-black-50">
								<liferay-ui:message key="<%= HtmlUtil.escape(beanIdLabel) %>" />
							</span>

							<strong class="c-ml-2 header-info-value" data-qa-id="<%= beanId %>">
								<%= (displayBeanId > 0) ? String.valueOf(displayBeanId) : "" %>
							</strong>

							<span class="c-ml-2 lfr-portal-tooltip text-secondary" title="<%= LanguageUtil.get(request, "identification-number") %>">
								<clay:icon
									symbol="question-circle"
								/>
							</span>
						</div>
					</c:if>

					<c:if test="<%= Validator.isNotNull(externalReferenceCode) || Validator.isNotNull(externalReferenceCodeEditURL) %>">
						<div class="align-items-center c-mt-n2 d-flex py-1">
							<span class="header-info-title text-secondary">
								<liferay-ui:message key="erc" />
							</span>

							<strong class="c-ml-2 header-info-value">
								<%= HtmlUtil.escape(externalReferenceCode) %>
							</strong>

							<span class="c-ml-2 lfr-portal-tooltip text-secondary" title="<%= LanguageUtil.get(request, "external-reference-code") %>">
								<clay:icon
									symbol="question-circle"
								/>
							</span>

							<c:if test="<%= Validator.isNotNull(externalReferenceCodeEditURL) %>">
								<clay:button
									additionalProps='<%=
										HashMapBuilder.<String, Object>put(
											"title", LanguageUtil.format(request, "edit-x", "external-reference-code")
										).put(
											"url", externalReferenceCodeEditURL
										).build()
									%>'
									cssClass="text-secondary"
									displayType="link"
									icon="pencil"
									id="erc-edit-modal-opener"
									propsTransformer="{ExternalReferenceCodeButtonPropsTransformer} from commerce-frontend-taglib"
									small="<%= true %>"
								/>
							</c:if>
						</div>
					</c:if>
				</div>
			</div>

			<hr class="d-lg-none" />

			<div class="align-items-center c-ml-auto d-flex justify-content-end">
				<c:if test="<%= !CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT.equals(PortalUtil.getPortletId(request)) && Validator.isNotNull(reviewWorkflowTask) %>">

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

						<liferay-ui:csp>
							<button aria-expanded="false" aria-haspopup="true" class="align-items-center btn btn-secondary d-flex dropdown-toggle header-assign-button justify-content-between" data-toggle="dropdown" onclick="<portlet:namespace />toggleDropdown();" type="button">
								<liferay-ui:message key="<%= HtmlUtil.escape(assignee) %>" />

								<clay:icon
									symbol="caret-bottom"
								/>
							</button>
						</liferay-ui:csp>

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
											Liferay.Util.openModal({
												containerProps: {},
												id: '<%= myWorkflowTasksPortletNamespace %>assignToDialog',
												iframeBodyCssClass: 'dialog-with-footer task-dialog',
												title: '<liferay-ui:message key="assign-to-me" />',
												url: '<%= HtmlUtil.escapeJS(assignToMeURL) %>',
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
										Liferay.Util.openModal({
											containerProps: {},
											id: '<%= myWorkflowTasksPortletNamespace %>assignToDialog',
											iframeBodyCssClass: 'dialog-with-footer task-dialog',
											title: '<liferay-ui:message key="assign-to-..." />',
											url: '<%= HtmlUtil.escapeJS(assignToURL) %>',
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
											<c:choose>
												<c:when test="<%= Validator.isNotNull(action.getSubmitButtonId()) %>">
													document
														.getElementById(
															'<%= HtmlUtil.escapeJS(action.getSubmitButtonId()) %>'
														)
														.click();
												</c:when>
												<c:otherwise>
													submitForm(form);
												</c:otherwise>
											</c:choose>
										});
								</aui:script>
							</c:if>

						<%
						}
						%>

					</div>
				</c:if>

				<c:if test="<%= Validator.isNotNull(dropdownItems) || Validator.isNotNull(previewURL) %>">
					<c:if test="<%= Validator.isNotNull(dropdownItems) && (dropdownItems.size() > 0) %>">
						<div class="c-ml-3" id="dropdown-header-container">
							<liferay-ui:icon
								icon="ellipsis-v"
								markupView="lexicon"
							/>
						</div>

						<liferay-frontend:component
							context='<%=
								HashMapBuilder.<String, Object>put(
									"items", dropdownItems
								).put(
									"spritemap", themeDisplay.getPathThemeSpritemap()
								).build()
							%>'
							module="{dropdownMain} from commerce-frontend-taglib"
						/>
					</c:if>

					<c:if test="<%= Validator.isNotNull(previewURL) %>">
						<clay:link
							cssClass="btn btn-outline-borderless btn-outline-secondary btn-sm text-primary"
							href="<%= previewURL %>"
							icon="shortcut"
						/>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
</div>

<aui:script sandbox="<%= true %>">
	const pageHeader = document.querySelector('.page-header');

	if (pageHeader) {
		pageHeader.classList.add('sticky-header-menu');
	}
</aui:script>