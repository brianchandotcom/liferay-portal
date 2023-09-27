<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");

String modelResourceName = ResourceActionsUtil.getModelResource(request, modelResource);

long columnId = ParamUtil.getLong(request, "columnId");

ExpandoColumn expandoColumn = null;

if (columnId > 0) {
	expandoColumn = ExpandoColumnServiceUtil.fetchExpandoColumn(columnId);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
portletDisplay.setURLBackTitle(ParamUtil.getString(request, "backTitle"));

renderResponse.setTitle(modelResourceName + ": " + ((expandoColumn == null) ? LanguageUtil.get(request, "new-custom-field") : expandoColumn.getName()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "custom-field"), String.valueOf(renderResponse.createRenderURL()));

PortalUtil.addPortletBreadcrumbEntry(
	request, LanguageUtil.get(request, "view-attributes"),
	PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/view_attributes.jsp"
	).setRedirect(
		redirect
	).setParameter(
		"modelResource", modelResource
	).buildString());

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "new-custom-field"), null);
%>

<clay:container-fluid
	cssClass="container-view"
>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, false, false, false, true, true) %>"
	/>

	<liferay-frontend:edit-form>
		<liferay-frontend:edit-form-body>
			<clay:sheet-header>
				<h2 class="sheet-title">
					<liferay-ui:message key="new-custom-field" />
				</h2>
			</clay:sheet-header>

			<clay:row
				cssClass="clay-site-row-spacer"
			>
				<clay:col
					size="12"
				>
					<h3 class="sheet-subtitle">
						<liferay-ui:message key="text-and-numbers" />
					</h3>
				</clay:col>

				<clay:col
					md="4"
				>
					<portlet:renderURL var="createTextAreaURL">
						<portlet:param name="mvcPath" value="/edit/expando.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX %>" />
						<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING) %>" />
					</portlet:renderURL>

					<a class="card card-interactive card-interactive-secondary" href="<%= createTextAreaURL %>">
						<div class="card-body">
							<label>
								<liferay-ui:message key="text-area" />
							</label>

							<span class="form-control form-control-textarea"></span>
						</div>
					</a>
				</clay:col>

				<clay:col
					md="4"
				>
					<portlet:renderURL var="createInputFieldURL">
						<portlet:param name="mvcPath" value="/edit/expando.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_INPUT_FIELD %>" />
						<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING) %>" />
					</portlet:renderURL>

					<a class="card card-interactive card-interactive-secondary" href="<%= createInputFieldURL %>">
						<div class="card-body">
							<label>
								<liferay-ui:message key="input-field" />
							</label>

							<span class="form-control"></span>
						</div>
					</a>
				</clay:col>

				<clay:col
					size="12"
				>
					<h3 class="sheet-subtitle">
						<liferay-ui:message key="selection" />
					</h3>
				</clay:col>

				<clay:col
					md="4"
				>
					<portlet:renderURL var="createDropdownURL">
						<portlet:param name="mvcPath" value="/edit/expando.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST %>" />
						<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING_ARRAY) %>" />
					</portlet:renderURL>

					<a class="card card-interactive card-interactive-secondary" href="<%= createDropdownURL %>">
						<div class="card-body">
							<label>
								<liferay-ui:message key="dropdown" />
							</label>

							<span class="form-control form-control-select">Option 1</span>
						</div>
					</a>
				</clay:col>

				<clay:col
					md="4"
				>
					<portlet:renderURL var="createCheckboxURL">
						<portlet:param name="mvcPath" value="/edit/expando.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_CHECKBOX %>" />
						<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING_ARRAY) %>" />
					</portlet:renderURL>

					<a class="card card-interactive card-interactive-secondary" href="<%= createCheckboxURL %>">
						<div class="card-body">
							<span class="custom-checkbox custom-control">
								<label>
									<span class="custom-control-label">
										<span class="custom-control-label-text">
											<liferay-ui:message key="checkbox" />
										</span>
									</span>
								</label>
							</span>
						</div>
					</a>
				</clay:col>

				<clay:col
					md="4"
				>
					<portlet:renderURL var="createRadioURL">
						<portlet:param name="mvcPath" value="/edit/expando.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_RADIO %>" />
						<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING_ARRAY) %>" />
					</portlet:renderURL>

					<a class="card card-interactive card-interactive-secondary" href="<%= createRadioURL %>">
						<div class="card-body">
							<span class="custom-control custom-radio">
								<label>
									<span class="custom-control-label">
										<span class="custom-control-label-text">
											<liferay-ui:message key="radio" />
										</span>
									</span>
								</label>
							</span>
						</div>
					</a>
				</clay:col>

				<clay:col
					size="12"
				>
					<h3 class="sheet-subtitle">
						<liferay-ui:message key="others" />
					</h3>
				</clay:col>

				<clay:col
					md="4"
				>
					<portlet:renderURL var="createGeolocationURL">
						<portlet:param name="mvcPath" value="/edit/expando.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_GEOLOCATION %>" />
						<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.GEOLOCATION) %>" />
					</portlet:renderURL>

					<a class="card card-interactive card-interactive-secondary" href="<%= createGeolocationURL %>">
						<div class="card-body">
							<label>
								<liferay-ui:message key="geolocation" />
							</label>

							<div class="aspect-ratio custom-aspect-ratio-geolocation">
								<img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-flush" src="<%= PortalUtil.getPathContext(request) %>/images/map.svg" />
							</div>
						</div>
					</a>
				</clay:col>

				<clay:col
					md="4"
				>
					<portlet:renderURL var="createDateURL">
						<portlet:param name="mvcPath" value="/edit/expando.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_DATE %>" />
						<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.DATE) %>" />
					</portlet:renderURL>

					<a class="card card-interactive card-interactive-secondary" href="<%= createDateURL %>">
						<div class="card-body">
							<label>
								<liferay-ui:message key="date" />
							</label>

							<div class="input-group">
								<div class="input-group-item">
									<div class="form-control input-group-inset input-group-inset-after">YYYY-MM-DD</div>

									<div class="input-group-inset-item input-group-inset-item-after">
										<div class="align-items-center btn btn-unstyled d-flex">
											<clay:icon
												symbol="calendar"
											/>
										</div>
									</div>
								</div>
							</div>
						</div>
					</a>
				</clay:col>

				<clay:col
					md="4"
				>
					<portlet:renderURL var="createBooleanURL">
						<portlet:param name="mvcPath" value="/edit/expando.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="modelResource" value="<%= modelResource %>" />
						<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_BOOLEAN %>" />
						<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.BOOLEAN) %>" />
					</portlet:renderURL>

					<a class="card card-interactive card-interactive-secondary" href="<%= createBooleanURL %>">
						<div class="card-body custom-card-body-boolean">
							<span class="simple-toggle-switch toggle-switch">
								<span class="toggle-switch-check-bar">
									<span class="toggle-switch-check"></span>
									<span aria-hidden="true" class="toggle-switch-bar">
										<span class="toggle-switch-handle"></span>
									</span>
								</span>

								<label>
									<liferay-ui:message key="true-or-false" />
								</label>
							</span>
						</div>
					</a>
				</clay:col>
			</clay:row>
		</liferay-frontend:edit-form-body>
	</liferay-frontend:edit-form>
</clay:container-fluid>