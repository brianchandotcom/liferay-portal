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

<%@ include file="/panel/init.jsp" %>

<%
String collapseSwitchId = Validator.isNotNull(collapseSwitchName) ? collapseSwitchName : (randomNamespace + "toggle-switch-check");
%>

<div class="<%= "card d-flex flex-column" + (Validator.isNotNull(elementClasses) ? StringPool.SPACE + elementClasses : StringPool.BLANK) %>">
	<c:if test="<%= Validator.isNotNull(actionLabel) || Validator.isNotNull(actionIcon) || Validator.isNotNull(title) %>">
		<h4 class="align-items-center card-header d-flex justify-content-between py-3">
			<%= HtmlUtil.escape(title) %>

			<c:if test="<%= Validator.isNotNull(actionTargetId) %>">
				<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as eventsDefinitions">
					var link = document.getElementById('<%= HtmlUtil.escapeJS(linkId) %>');

					if (link) {
						link.addEventListener('click', (e) => {
							e.preventDefault();
							Liferay.fire(eventsDefinitions.OPEN_MODAL, {
								id: '<%= HtmlUtil.escapeJS(actionTargetId) %>',
							});
						});
					}
				</aui:script>
			</c:if>

			<c:choose>
				<c:when test="<%= Validator.isNotNull(actionLabel) %>">
					<clay:link
						href='<%= (Validator.isNotNull(actionUrl) && Validator.isNull(actionTargetId)) ? actionUrl : "#" %>'
						id="<%= HtmlUtil.escape(linkId) %>"
						label="<%= HtmlUtil.escape(actionLabel) %>"
					/>
				</c:when>
				<c:when test="<%= Validator.isNotNull(actionIcon) %>">
					<clay:link
						cssClass="btn btn-monospaced btn-primary btn-sm text-white"
						href='<%= (Validator.isNotNull(actionUrl) && Validator.isNull(actionTargetId)) ? actionUrl : "#" %>'
						icon="<%= HtmlUtil.escapeAttribute(actionIcon) %>"
						id="<%= HtmlUtil.escape(linkId) %>"
					/>
				</c:when>
				<c:when test="<%= collapsible || Validator.isNotNull(collapseLabel) || Validator.isNotNull(collapseSwitchName) %>">
					<aui:script>
						(function () {
							var toggleSwitch = document.getElementById(
								'<%= HtmlUtil.escapeJS(randomNamespace) %>toggle-switch'
							);
							var toggleLabel = document.getElementById(
								'<%= HtmlUtil.escapeJS(randomNamespace) %>toggle-label'
							);
							var toggleCheckbox = document.getElementById(
								'<%=HtmlUtil.escapeJS(collapseSwitchId) %>'
							);
							var collapseClickable = true;
							var collapsableElement = document.getElementById(
								'<%= HtmlUtil.escapeJS(randomNamespace) %>collapse'
							);

							[toggleSwitch, toggleLabel].forEach((el) => {
								el.addEventListener('click', (e) => {
									e.preventDefault();

									if (collapseClickable) {
										toggleCheckbox.click();
										collapsableElement.classList[
											toggleCheckbox.checked ? 'remove' : 'add'
										]('show');
										toggleCheckbox.checked = !toggleCheckbox.checked;
									}

									collapseClickable = false;

									setTimeout(() => {
										collapseClickable = true;
									}, 400);
								});
							});
						})();
					</aui:script>

					<span class="d-flex mr-n2">
						<c:if test="<%= Validator.isNotNull(collapseLabel) %>">
							<label for="<%= HtmlUtil.escapeAttribute(collapseSwitchId) %>" id="<%= HtmlUtil.escapeAttribute(randomNamespace) %>toggle-label">
								<h5 class="mb-0 mr-3">
									<%= HtmlUtil.escape(collapseLabel) %>
								</h5>
							</label>
						</c:if>

						<span class="my-lg-n2 toggle-switch" id="<%= HtmlUtil.escapeAttribute(randomNamespace) %>toggle-switch">
							<input
								aria-expanded="<%= !collapsed %>"
								<%= collapsed ? StringPool.BLANK : "checked" %>
								data-target="#<%= HtmlUtil.escapeAttribute(randomNamespace) %>collapse"
								data-toggle="collapse"
								class="toggle-switch-check d-none"
								id="<%= HtmlUtil.escapeAttribute(collapseSwitchId) %>"
								<c:if test="<%= Validator.isNotNull(collapseSwitchName) %>">
									name="<%= HtmlUtil.escapeAttribute(collapseSwitchName) %>"
								</c:if>
								type="checkbox"
							/>

							<span aria-hidden="true" class="toggle-switch-bar">
								<span class="toggle-switch-handle"></span>
							</span>
						</span>
					</span>
				</c:when>
			</c:choose>
		</h4>
	</c:if>

	<div class="collapse<%= collapsed ? StringPool.BLANK : " show" %>" id="<%= randomNamespace %>collapse">
		<div class="<%= "card-body" + (Validator.isNotNull(bodyClasses) ? StringPool.SPACE + bodyClasses : StringPool.BLANK) %>">