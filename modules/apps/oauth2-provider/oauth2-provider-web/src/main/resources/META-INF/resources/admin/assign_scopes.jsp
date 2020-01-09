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

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

OAuth2Application oAuth2Application = oAuth2AdminPortletDisplayContext.getOAuth2Application();

AssignScopesDisplayContext assignScopesDisplayContext = (AssignScopesDisplayContext)oAuth2AdminPortletDisplayContext;
%>

<div class="container-fluid container-fluid-max-xl container-view">
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

	<div class="row">
		<div class="col-lg-12">
			<portlet:actionURL name="/admin/assign_scopes" var="assignScopesURL">
				<portlet:param name="mvcRenderCommandName" value="/admin/assign_scopes" />
				<portlet:param name="appTab" value="assign_scopes" />
				<portlet:param name="backURL" value="<%= redirect %>" />
				<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2Application.getOAuth2ApplicationId()) %>" />
			</portlet:actionURL>

			<aui:form action="<%= assignScopesURL %>" name="fm">
				<div class="sheet">
					<ul class="hidden nav nav-underline" id="<portlet:namespace />navScopeTypes" role="tablist">
						<li class="nav-item">
							<a aria-controls="<portlet:namespace />navResourceScopes" aria-expanded="true" class="active nav-link" data-toggle="liferay-tab" href="#<portlet:namespace />navResourceScopes" id="<portlet:namespace />navResourceScopesTab" role="tab">
								<liferay-ui:message key="resource-scopes" />
							</a>
						</li>
						<li class="nav-item">
							<a aria-controls="<portlet:namespace />navGlobalScopes" class="nav-link" data-toggle="liferay-tab" href="#<portlet:namespace />navGlobalScopes" id="<portlet:namespace />navGlobalScopesTab" role="tab">
								<liferay-ui:message key="global-scopes" />
							</a>
						</li>
					</ul>

					<div class="hidden tab-content" id="<portlet:namespace />navScopeTypesTabContents">
						<div aria-labelledby="navResourceScopesTab" class="active fade show tab-pane" id="<portlet:namespace />navResourceScopes" role="tabpanel">
							<%@ include file="/admin/assign_scopes_tab1.jspf" %>
						</div>

						<div aria-labelledby="navGlobalScopesTab" class="fade tab-pane" id="<portlet:namespace />navGlobalScopes" role="tabpanel">
							<%@ include file="/admin/assign_scopes_tab2.jspf" %>
						</div>
					</div>

					<aui:button-row>
						<aui:button id="save" type="submit" value="save" />

						<aui:button href="<%= PortalUtil.escapeRedirect(redirect) %>" type="cancel" />
					</aui:button-row>
				</div>

				<aui:input id="impliedScopeAliases" name="scopeAliases" type="hidden" />
			</aui:form>
		</div>
	</div>
</div>

<aui:script require="metal-dom/src/dom as dom">
	AUI().use('node', 'aui-modal', function(A) {
		if (A.all('#<portlet:namespace />navGlobalScopes .panel').size() > 0) {
			A.one('#<portlet:namespace />navScopeTypes').toggleClass(
				'hidden',
				false
			);
		}

		A.one('#<portlet:namespace />navScopeTypesTabContents').toggleClass(
			'hidden',
			false
		);

		var handle;

		var appsAccordion = A.one('#<portlet:namespace />appsAccordion');

		appsAccordion.delegate(
			'click',
			function(event) {
				event.stopPropagation();

				if (handle) {
					handle.detach();
					handle = null;
				}

				var currentTarget = event.currentTarget;

				if (currentTarget.attr('name')) {
					return true;
				}

				var modal = new A.Modal({
					centered: true,
					cssClass: 'assign-scopes-modal modal-full-screen',
					destroyOnHide: true,
					visible: true,
					zIndex: Liferay.zIndex.OVERLAY,
					modal: true,
					bodyContent: '<div id="<portlet:namespace />modalBody"/>',
					headerContent:
						'<%= UnicodeLanguageUtil.get(request, "choose-one-of-the-following-global-scopes-that-include-this-resource-scope") %>'
				}).render();

				modal.on('visibleChange', function(event) {
					if (event.newVal) {
						return;
					}

					document
						.querySelectorAll(
							'#<portlet:namespace />globalAccordion .panel'
						)
						.forEach(function(globalAccordionPanel) {
							globalAccordionPanel.classList.remove('hide');
						});

					var globalAccordion = document.getElementById(
						'<portlet:namespace />globalAccordion'
					);
					var navGlobalScopes = document.getElementById(
						'<portlet:namespace />navGlobalScopes'
					);

					if (navGlobalScopes && globalAccordion) {
						dom.append(navGlobalScopes, globalAccordion);
					}
				});

				modal.addToolbar([
					{
						cssClass: 'btn-primary',
						label: '<liferay-ui:message key="close" />',
						on: {
							click: function() {
								modal.hide();
							}
						}
					}
				]);

				var scopeAliases = currentTarget.attr('data-slave').split(' ');

				document
					.querySelectorAll(
						'#<portlet:namespace />globalAccordion .panel'
					)
					.forEach(function(globalAccordionPanel) {
						globalAccordionPanel.classList.add('hide');
					});

				for (var i = 0; i < scopeAliases.length; i++) {
					document
						.querySelectorAll(
							'#<portlet:namespace />globalAccordion .panel[data-master]'
						)
						.forEach(function(globalAccordionPanel) {
							var masterScopeAliases = globalAccordionPanel.getAttribute(
								'data-master'
							);

							var array = masterScopeAliases.split(' ');

							if (array.indexOf(scopeAliases[i]) >= 0) {
								globalAccordionPanel.classList.remove('hide');
							}
						});
				}

				var globalAccordion = document.getElementById(
					'<portlet:namespace />globalAccordion'
				);
				var modalBody = document.getElementById(
					'<portlet:namespace />modalBody'
				);

				if (globalAccordion && modalBody) {
					dom.append(modalBody, globalAccordion);
				}

				event.preventDefault();

				return false;
			},
			'input[data-slave], a[data-slave]'
		);

		<portlet:namespace />recalculateDependants = function(checkboxElement) {
			var checkbox = A.one(checkboxElement);

			var value = checkbox.val();
			if (!value) {
				return;
			}

			var processedScopeAliases = value.split(' ');

			var scopeAlias = processedScopeAliases[0];

			<portlet:namespace />changeScopeAliasStickyStatus(
				value,
				checkbox.attr('checked')
			);

			return A.all('input[data-slave]')
				.filter(function() {
					var array = this.attr('data-slave').split(' ');

					return array.indexOf(scopeAlias) >= 0;
				})
				.each(function() {
					var slave = this;

					var scopeAliases = slave.attr('data-slave').split(' ');

					var logicalOR = checkbox.attr('checked');

					for (var i = 0; i < scopeAliases.length && !logicalOR; i++) {
						if (processedScopeAliases.indexOf(scopeAliases[i]) >= 0) {
							continue;
						}

						A.all(
							'#<portlet:namespace />globalAccordion .panel[data-master]'
						)
							.filter(function() {
								return this.one('input:checked');
							})
							.each(function() {
								var array = this.attr('data-master').split(' ');

								if (array.indexOf(scopeAliases[i]) >= 0) {
									logicalOR = true;
									processedScopeAliases.concat(array);
								}
							});
					}

					if (logicalOR) {
						slave.attr('checked', true);
						slave.attr('disabled', true);
						return;
					}

					var index = <portlet:namespace />getArrayIndexOfStickyScopeAlias(
						slave.val()
					);
					if (index == -1) {
						slave.attr('checked', false);
					}

					if (slave.attr('name') == '<portlet:namespace />scopeAliases') {
						slave.attr('disabled', false);
					}
				});
		};

		<portlet:namespace />recalculateAll = function() {
			A.all('input[name="<portlet:namespace />scopeAliases"]').each(
				function() {
					<portlet:namespace />recalculateDependants(this);
				}
			);
		};

		var <portlet:namespace />stickyScopeAliases = [];

		<portlet:namespace />changeScopeAliasStickyStatus = function(
			scopeAlias,
			sticky
		) {
			if (sticky) {
				if (
					<portlet:namespace />stickyScopeAliases.indexOf(scopeAlias) < 0
				) {
					<portlet:namespace />stickyScopeAliases.push(scopeAlias);
				}
			} else {
				var index = <portlet:namespace />getArrayIndexOfStickyScopeAlias(
					scopeAlias
				);

				if (index > -1) {
					<portlet:namespace />stickyScopeAliases.splice(index, 1);
				}
			}
		};

		<portlet:namespace />getArrayIndexOfStickyScopeAlias = function(
			scopeAlias
		) {
			for (
				var i = 0;
				i < <portlet:namespace />stickyScopeAliases.length;
				i++
			) {
				if (<portlet:namespace />stickyScopeAliases[i] == scopeAlias) {
					return i;
				}
			}
			return -1;
		};

		<portlet:namespace />recalculateAll();

		A.one('#<portlet:namespace />save').on('click', function(event) {
			event.preventDefault();

			var scopeAliases = [];

			A.all(
				'input[name="<portlet:namespace />scopeAliases"]:checked:disabled'
			).each(function() {
				scopeAliases.push(this.val());
			});

			A.one('#<portlet:namespace />impliedScopeAliases').attr(
				'value',
				scopeAliases.join(' ')
			);

			document.<portlet:namespace/>fm.submit();
		});

		A.all('#<portlet:namespace />appsAccordion .panel')
			.filter(function() {
				return this.one('input:checked');
			})
			.each(function() {
				var panelHeaderElement = this.one(
					'*[data-toggle="liferay-collapse"]'
				);

				panelHeaderElement.attr('aria-expanded', 'true');
				panelHeaderElement.removeClass('collapsed');

				var panelBodyElement = this.one('.collapse');

				panelBodyElement.addClass('show');
			});
	});
</aui:script>