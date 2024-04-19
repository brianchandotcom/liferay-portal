<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

OAuth2Application oAuth2Application = oAuth2AdminPortletDisplayContext.getOAuth2Application();

Tree.Node<String> scopeAliasTreeNode = assignScopesTreeDisplayContext.getScopeAliasTreeNode();

pageContext.setAttribute("assignedDeletedScopeAliases", assignScopesTreeDisplayContext.getAssignedDeletedScopeAliases());
pageContext.setAttribute("assignedScopeAliases", assignScopesTreeDisplayContext.getAssignedScopeAliases());
pageContext.setAttribute("scopeAliasesDescriptionsMap", assignScopesTreeDisplayContext.getScopeAliasesDescriptionsMap());
%>

<clay:container-fluid
	cssClass="container-view"
>
	<clay:sheet>
		<clay:sheet-header>
			<h2 class="sheet-title"><liferay-ui:message key="scopes" /></h2>

			<div class="sheet-text"><liferay-ui:message key="scopes-description" /></div>
		</clay:sheet-header>

		<clay:sheet-section>
			<liferay-ui:error exception="<%= OAuth2ApplicationClientCredentialUserIdException.class %>">

				<%
				OAuth2ApplicationClientCredentialUserIdException oAuth2ApplicationClientCredentialUserIdException = (OAuth2ApplicationClientCredentialUserIdException)errorException;
				%>

				<c:choose>
					<c:when test="<%= Validator.isNotNull(oAuth2ApplicationClientCredentialUserIdException.getClientCredentialUserScreenName()) %>">
						<liferay-ui:message arguments="<%= oAuth2ApplicationClientCredentialUserIdException.getClientCredentialUserScreenName() %>" key="this-operation-cannot-be-performed-because-you-cannot-impersonate-x" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message arguments="<%= oAuth2ApplicationClientCredentialUserIdException.getClientCredentialUserId() %>" key="this-operation-cannot-be-performed-because-you-cannot-impersonate-x" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:error>

			<clay:row>
				<clay:col>
					<portlet:actionURL name="/oauth2_provider/assign_scopes" var="assignScopesURL">
						<portlet:param name="mvcRenderCommandName" value="/oauth2_provider/assign_scopes" />
						<portlet:param name="navigation" value="assign_scopes" />
						<portlet:param name="backURL" value="<%= redirect %>" />
						<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2Application.getOAuth2ApplicationId()) %>" />
					</portlet:actionURL>

					<aui:form action="<%= assignScopesURL %>" name="fm">
						<ul class="list-group">
							<oauth2-tree:tree
								trees="<%= (Collection)scopeAliasTreeNode.getTrees() %>"
							>
								<oauth2-tree:node>
									<li class="borderless list-group-item<c:if test="${assignedDeletedScopeAliases.contains(tree.value)}"> removed-scope</c:if>" id="${tree.value}-container">
										<clay:row>
												<c:choose>
													<c:when test="${parentNodes.size() > 0}">
													<div class="col-md-8">
														<div class="scope-children-${parentNodes.size()}">
															<aui:input checked="${assignedScopeAliases.contains(tree.value)}" data-has-children="true" data-parent="${parentNodes.getFirst().value}" disabled="${assignedDeletedScopeAliases.contains(tree.value)}" id="${tree.value}" label="${tree.value}" name="scopeAliases" type="checkbox" value="${tree.value}" />
														</div>
													</div>
													</c:when>
													<c:otherwise>
													<div class="col-md-8">
														<aui:input checked="${assignedScopeAliases.contains(tree.value)}" data-has-children="true" disabled="${assignedDeletedScopeAliases.contains(tree.value)}" id="${tree.value}" label="${tree.value}" name="scopeAliases" type="checkbox" value="${tree.value}" />
													</div>
													</c:otherwise>
												</c:choose>

												<div class="col-md-4 text-left">
													<c:choose>
														<c:when test="${assignedDeletedScopeAliases.contains(tree.value)}">
															<liferay-ui:message key="this-scope-is-no-longer-available" />
														</c:when>
														<c:otherwise>
															${scopeAliasesDescriptionsMap.get(tree.value)}
														</c:otherwise>
													</c:choose>
												</div>
										</clay:row>
									</li>

									<oauth2-tree:render-children />
								</oauth2-tree:node>

								<oauth2-tree:leaf>
									<li class="borderless list-group-item<c:if test="${assignedDeletedScopeAliases.contains(tree.value)}"> removed-scope</c:if>" id="${tree.value}-container">
										<clay:row>
											<c:choose>
												<c:when test="${parentNodes.size() > 0}">
												<div class="col-md-8">
													<div class="scope-children-${parentNodes.size()}">
														<aui:input checked="${assignedScopeAliases.contains(tree.value)}" data-parent="${parentNodes.getFirst().value}" disabled="${assignedDeletedScopeAliases.contains(tree.value)}" id="${tree.value}" label="${tree.value}" name="scopeAliases" type="checkbox" value="${tree.value}" />
													</div>
												</div>
												</c:when>
												<c:otherwise>
												<div class="col-md-8">
													<aui:input checked="${assignedScopeAliases.contains(tree.value)}" disabled="${assignedDeletedScopeAliases.contains(tree.value)}" id="${tree.value}" label="${tree.value}" name="scopeAliases" type="checkbox" value="${tree.value}" />
												</div>
												</c:otherwise>
											</c:choose>

											<div class="col-md-4 text-left">
												<c:choose>
													<c:when test="${assignedDeletedScopeAliases.contains(tree.value)}">
														<liferay-ui:message key="this-scope-is-no-longer-available" />
													</c:when>
													<c:otherwise>
														${scopeAliasesDescriptionsMap.get(tree.value)}
													</c:otherwise>
												</c:choose>
											</div>
										</clay:row>
									</li>
								</oauth2-tree:leaf>
							</oauth2-tree:tree>
							</li>
						</ul>

						<aui:button-row>
							<aui:button id="save" type="submit" value="save" />

							<aui:button href="<%= PortalUtil.escapeRedirect(redirect) %>" type="cancel" />
						</aui:button-row>
					</aui:form>
				</clay:col>
			</clay:row>
		</clay:sheet-section>
	</clay:sheet>
</clay:container-fluid>

<aui:script sandbox="<%= true %>">
	Liferay.Util.delegate(
		document.body,
		'click',
		'input[name="<portlet:namespace />scopeAliases"]',
		(event) => {
			recalculateScopeChildren(event.target);
			recalculateScopeParents(event.target);
		}
	);

	const recalculateScopeChildren = (checkboxElement) => {
		const valueId = checkboxElement.value;
		const isChecked = checkboxElement.checked;

		document
			.querySelectorAll('input[data-parent="' + valueId + '"]')
			.forEach((element) => {
				element.checked = isChecked;
				const hasChildren = checkboxElement.dataset.hasChildren;
				if (hasChildren) {
					recalculateScopeChildren(element);
				}
			});
	};

	const recalculateScopeParents = (checkboxElement) => {
		const parent = checkboxElement.dataset.parent;
		const isChecked = checkboxElement.checked;

		if (parent && !isChecked) {
			const parentElement = document.querySelector(
				'input[value="' + parent + '"]'
			);
			parentElement.checked = isChecked;
			recalculateScopeParents(parentElement);
		}
	};

	const checkNewScopesInCheckedParents = () => {
		document
			.querySelectorAll('input[data-has-children="true"]')
			.forEach((parent) => {
				if (parent.checked) {
					const parentValue = parent.value;
					document
						.querySelectorAll(
							'input[data-parent="' + parentValue + '"]'
						)
						.forEach((element) => {
							if (!element.checked) {
								element.checked = true;

								const elementId = element.value;

								const container = document.getElementById(
									elementId + '-container'
								);

								container.classList.add('added-scope');
							}
						});
				}
			});
	};

	checkNewScopesInCheckedParents();
</aui:script>