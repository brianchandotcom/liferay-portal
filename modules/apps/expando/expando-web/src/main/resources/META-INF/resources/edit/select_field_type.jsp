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
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceName = ResourceActionsUtil.getModelResource(request, modelResource);

long columnId = ParamUtil.getLong(request, "columnId");

ExpandoColumn expandoColumn = null;

if (columnId > 0) {
	expandoColumn = ExpandoColumnServiceUtil.fetchExpandoColumn(columnId);
}

renderResponse.setTitle(modelResourceName + ": " + ((expandoColumn == null) ? LanguageUtil.get(request, "new-custom-field") : expandoColumn.getName()));
%>

<liferay-frontend:edit-form>
	<liferay-frontend:edit-form-body>
		<div class="sheet-header">
			<h2 class="sheet-title">
				<liferay-ui:message key="new-custom-field" />
			</h2>
		</div>

		<aui:row cssClass="clay-site-row-spacer">
			<aui:col span="<%= 12 %>">
				<h3 class="sheet-subtitle">
					<liferay-ui:message key="text-and-numbers" />
				</h3>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createTextAreaURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
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
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createTextFieldURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_FIELD %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.STRING) %>" />
				</portlet:renderURL>

				<a class="card card-interactive card-interactive-secondary" href="<%= createTextFieldURL %>">
					<div class="card-body">
						<label>
							<liferay-ui:message key="text-field" />
						</label>

						<span class="form-control"></span>
					</div>
				</a>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createNumberURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
					<portlet:param name="modelResource" value="<%= modelResource %>" />
					<portlet:param name="displayType" value="<%= ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_NUMBER %>" />
					<portlet:param name="type" value="<%= String.valueOf(ExpandoColumnConstants.NUMBER) %>" />
				</portlet:renderURL>

				<a class="card card-interactive card-interactive-secondary" href="<%= createNumberURL %>">
					<div class="card-body">
						<label>
							<liferay-ui:message key="number" />
						</label>

						<span class="form-control"></span>
					</div>
				</a>
			</aui:col>

			<aui:col span="<%= 12 %>">
				<h3 class="sheet-subtitle">
					<liferay-ui:message key="selection" />
				</h3>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createDropdownURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
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
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createCheckboxURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
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
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createRadioURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
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
			</aui:col>

			<aui:col span="<%= 12 %>">
				<h3 class="sheet-subtitle">
					<liferay-ui:message key="others" />
				</h3>
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createGeolocationURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
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
			</aui:col>

			<aui:col span="<%= 4 %>">
				<portlet:renderURL var="createDateURL">
					<portlet:param name="mvcPath" value="/edit/expando.jsp" />
					<portlet:param name="redirect" value="<%= redirect %>" />
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
			</aui:col>
		</aui:row>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>