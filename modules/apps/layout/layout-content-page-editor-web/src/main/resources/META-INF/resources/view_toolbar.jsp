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
ContentPageEditorDisplayContext contentPageEditorDisplayContext = (ContentPageEditorDisplayContext)request.getAttribute(ContentPageEditorWebKeys.LIFERAY_SHARED_CONTENT_PAGE_EDITOR_DISPLAY_CONTEXT);
%>

<div class="management-bar navbar navbar-expand-md page-editor__toolbar <%= contentPageEditorDisplayContext.isMasterLayout() ? "page-editor__toolbar--master-layout" : StringPool.BLANK %>" id="<%= contentPageEditorDisplayContext.getPortletNamespace() %>pageEditorToolbar">
	<clay:container-fluid>
		<ul class="navbar-nav start">
			<li class="nav-item">
				<div class="dropdown">
					<clay:button
						cssClass="dropdown-toggle"
						disabled="<%= true %>"
						displayType="secondary"
						icon="en-us"
						monospaced="<%= true %>"
						small="<%= true %>"
					/>
				</div>
			</li>
		</ul>

		<ul class="middle navbar-nav">
			<li class="nav-item"></li>
		</ul>

		<ul class="end navbar-nav">
			<li class="nav-item"></li>
			<li class="nav-item">
				<div class="btn-group flex-nowrap" role="group">
					<clay:button
						disabled="<%= true %>"
						displayType="secondary"
						icon="undo"
						monospaced="<%= true %>"
						small="<%= true %>"
						title='<%= LanguageUtil.get(request, "undo") %>'
					/>

					<clay:button
						disabled="<%= true %>"
						displayType="secondary"
						icon="redo"
						monospaced="<%= true %>"
						small="<%= true %>"
						title='<%= LanguageUtil.get(request, "redo") %>'
					/>
				</div>

				<span class="d-none d-sm-block">
					<div class="dropdown ml-2">
						<clay:button
							cssClass="dropdown-toggle"
							disabled="<%= true %>"
							displayType="secondary"
							icon="time"
							monospaced="<%= true %>"
							small="<%= true %>"
							title='<%= LanguageUtil.get(request, "history") %>'
						/>
					</div>
				</span>
			</li>
			<li class="nav-item">
				<div class="dropdown">
					<clay:button
						cssClass="dropdown-toggle form-control-select page-editor__edit-mode-selector text-left"
						disabled="<%= true %>"
						displayType="secondary"
						small="<%= true %>"
					>
						<liferay-ui:message key="page-design" />
					</clay:button>
				</div>
			</li>
			<li class="nav-item">
				<ul class="navbar-nav">
					<li class="nav-item">
						<clay:button
							disabled="<%= true %>"
							displayType="secondary"
							icon="view"
							monospaced="<%= true %>"
							small="<%= true %>"
							title='<%= LanguageUtil.get(request, "view") %>'
						/>
					</li>

					<c:if test="<%= contentPageEditorDisplayContext.isContentLayout() %>">
						<li class="nav-item">
							<clay:button
								disabled="<%= true %>"
								displayType="secondary"
								icon="page-template"
								monospaced="<%= true %>"
								small="<%= true %>"
								title='<%= LanguageUtil.get(request, "create-page-template") %>'
							/>
						</li>
					</c:if>
				</ul>
			</li>

			<c:if test="<%= contentPageEditorDisplayContext.isSingleSegmentsExperienceMode() %>">
				<li class="nav-item">
					<clay:button
						cssClass="mr-3"
						disabled="<%= true %>"
						displayType="secondary"
						small="<%= true %>"
					>
						<liferay-ui:message key="discard-variant" />
					</clay:button>
				</li>
			</c:if>

			<li class="nav-item">
				<clay:button
					disabled="<%= true %>"
					displayType="primary"
					small="<%= true %>"
				>
					<c:choose>
						<c:when test="<%= contentPageEditorDisplayContext.isMasterLayout() %>">
							<liferay-ui:message key="publish-master" />
						</c:when>
						<c:when test="<%= contentPageEditorDisplayContext.isSingleSegmentsExperienceMode() %>">
							<liferay-ui:message key="save-variant" />
						</c:when>
						<c:when test="<%= contentPageEditorDisplayContext.isWorkflowEnabled() %>">
							<liferay-ui:message key="submit-for-publication" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="publish" />
						</c:otherwise>
					</c:choose>
				</clay:button>
			</li>
		</ul>
	</clay:container-fluid>
</div>