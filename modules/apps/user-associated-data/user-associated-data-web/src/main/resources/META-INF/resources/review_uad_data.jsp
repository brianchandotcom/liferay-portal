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
List<ScopeDisplay> scopeDisplays = (List<ScopeDisplay>)request.getAttribute(UADWebKeys.SCOPE_DISPLAYS);
int totalReviewableUADEntitiesCount = (int)request.getAttribute(UADWebKeys.TOTAL_UAD_ENTITIES_COUNT);
List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays = (List<UADApplicationSummaryDisplay>)request.getAttribute(UADWebKeys.UAD_APPLICATION_SUMMARY_DISPLAY_LIST);
List<UADDisplay<?>> uadDisplays = (List<UADDisplay<?>>)request.getAttribute(UADWebKeys.APPLICATION_UAD_DISPLAYS);

ViewUADEntitiesDisplay viewUADEntitiesDisplay = (ViewUADEntitiesDisplay)request.getAttribute(UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY);

long[] groupIds = viewUADEntitiesDisplay.getGroupIds();
String scope = viewUADEntitiesDisplay.getScope();

portletDisplay.setShowBackIcon(true);

LiferayPortletURL usersAdminURL = liferayPortletResponse.createLiferayPortletURL(UsersAdminPortletKeys.USERS_ADMIN, PortletRequest.RENDER_PHASE);

portletDisplay.setURLBack(usersAdminURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "personal-data-erasure")));
%>

<liferay-util:include page="/uad_data_navigation_bar.jsp" servletContext="<%= application %>" />

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<div class="panel panel-secondary">
				<div class="collapse-icon collapse-icon-middle panel-header" data-target="#<portlet:namespace />scopePanelBody" data-toggle="liferay-collapse">
					<span class="panel-title">
						<%= StringUtil.toUpperCase(LanguageUtil.get(request, "scope"), locale) %>
					</span>

					<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

					<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
				</div>

				<div class="collapse panel-collapse show" id="<portlet:namespace />scopePanelBody">
					<div class="panel-body">

						<%
						for (ScopeDisplay scopeDisplay : scopeDisplays) {
						%>

							<clay:radio
								checked="<%= scopeDisplay.isActive() %>"
								disabled="<%= !scopeDisplay.hasItems() %>"
								label="<%= LanguageUtil.get(request, scopeDisplay.getScopeName()) %>"
								name="scope"
								value="<%= scopeDisplay.getScopeName() %>"
							/>

						<%
						}
						%>

					</div>
				</div>
			</div>

			<div class="panel panel-secondary">
				<div class="collapse-icon collapse-icon-middle panel-header" data-target="#<portlet:namespace />applicationPanelBody" data-toggle="liferay-collapse">
					<span class="panel-title">

						<%
						String applicationPanelTitle = StringUtil.toUpperCase(LanguageUtil.get(request, "applications"), locale);

						UADApplicationSummaryDisplay firstUADApplicationSummaryDisplay = uadApplicationSummaryDisplays.get(0);
						%>

						<%= StringUtil.appendParentheticalSuffix(applicationPanelTitle, firstUADApplicationSummaryDisplay.getCount()) %>
					</span>

					<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

					<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
				</div>

				<div class="collapse panel-collapse show" id="<portlet:namespace />applicationPanelBody">
					<div class="panel-body">

						<%
						for (UADApplicationSummaryDisplay uadApplicationSummaryDisplay : uadApplicationSummaryDisplays) {
							String applicationName = UADLanguageUtil.getApplicationName(uadApplicationSummaryDisplay.getApplicationKey(), locale);
						%>

							<clay:radio
								checked="<%= Objects.equals(uadApplicationSummaryDisplay.getApplicationKey(), viewUADEntitiesDisplay.getApplicationKey()) %>"
								disabled="<%= !uadApplicationSummaryDisplay.hasItems() %>"
								label="<%= StringUtil.appendParentheticalSuffix(applicationName, uadApplicationSummaryDisplay.getCount()) %>"
								name="applicationKey"
								value="<%= uadApplicationSummaryDisplay.getApplicationKey() %>"
							/>

						<%
						}
						%>

					</div>
				</div>
			</div>

			<c:if test="<%= !Objects.equals(viewUADEntitiesDisplay.getApplicationKey(), UADConstants.ALL_APPLICATIONS) %>">
				<div class="panel-group">
					<div class="panel panel-secondary">
						<div class="collapse-icon collapse-icon-middle panel-header" data-target="#<portlet:namespace />entitiesTypePanelBody" data-toggle="liferay-collapse">
							<span class="panel-title">

								<%
								String applicationName = UADLanguageUtil.getApplicationName(viewUADEntitiesDisplay.getApplicationKey(), locale);
								%>

								<%= StringUtil.toUpperCase(applicationName, locale) %>
							</span>

							<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

							<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
						</div>

						<div class="collapse panel-collapse show" id="<portlet:namespace />entitiesTypePanelBody">
							<div class="panel-body">
								<c:choose>
									<c:when test="<%= viewUADEntitiesDisplay.isHierarchy() %>">

										<%
										UADHierarchyDisplay uadHierarchyDisplay = (UADHierarchyDisplay)request.getAttribute(UADWebKeys.UAD_HIERARCHY_DISPLAY);
										%>

										<clay:radio
											checked="<%= true %>"
											label="<%= StringUtil.appendParentheticalSuffix(uadHierarchyDisplay.getEntitiesTypeLabel(locale), (int)uadHierarchyDisplay.searchCount(selectedUser.getUserId(), groupIds, null)) %>"
											name="uadRegistryKey"
											value="<%= viewUADEntitiesDisplay.getApplicationKey() %>"
										/>
									</c:when>
									<c:otherwise>

										<%
										for (UADDisplay<?> uadDisplay : uadDisplays) {
											long count = uadDisplay.searchCount(selectedUser.getUserId(), groupIds, null);
										%>

											<clay:radio
												checked="<%= Objects.equals(uadDisplay.getTypeName(locale), viewUADEntitiesDisplay.getTypeName()) %>"
												disabled="<%= count == 0 %>"
												label="<%= StringUtil.appendParentheticalSuffix(uadDisplay.getTypeName(locale), (int)count) %>"
												name="uadRegistryKey"
												value="<%= uadDisplay.getTypeClass().getName() %>"
											/>

										<%
										}
										%>

									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</clay:col>

		<clay:col
			lg="9"
		>
			<clay:sheet
				size="full"
			>
				<clay:sheet-header>
					<h2 class="sheet-title"><liferay-ui:message key="review-data" /></h2>
				</clay:sheet-header>

				<clay:sheet-section>
					<h3 class="sheet-subtitle">
						<liferay-ui:message key="status-summary" />
					</h3>

					<strong><liferay-ui:message key="remaining-items" />: </strong><%= totalReviewableUADEntitiesCount %>
				</clay:sheet-section>

				<clay:sheet-section>
					<c:choose>
						<c:when test="<%= totalReviewableUADEntitiesCount == 0 %>">
							<liferay-ui:empty-result-message
								message="all-data-that-requires-review-has-been-anonymized"
							/>
						</c:when>
						<c:otherwise>
							<h3 class="sheet-subtitle"><liferay-ui:message key="view-data" /></h3>

							<liferay-util:include page="/view_uad_entities.jsp" servletContext="<%= application %>" />
						</c:otherwise>
					</c:choose>
				</clay:sheet-section>
			</clay:sheet>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<portlet:renderURL var="reviewUADDataURL">
	<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
	<portlet:param name="mvcRenderCommandName" value="/user_associated_data/review_uad_data" />
	<portlet:param name="applicationKey" value="<%= viewUADEntitiesDisplay.getApplicationKey() %>" />
	<portlet:param name="scope" value="<%= scope %>" />
</portlet:renderURL>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {delegate} = frontendJsWeb;

	var baseURL = '<%= reviewUADDataURL %>';

	var clickListeners = [];

	var registerClickHandler = function (element, clickHandlerFn) {
		clickListeners.push(delegate(element, 'click', 'input', clickHandlerFn));
	};

	registerClickHandler(<portlet:namespace />applicationPanelBody, (event) => {
		var url = new URL(baseURL, window.location.origin);

		url.searchParams.set(
			'<portlet:namespace />applicationKey',
			event.target.value
		);

		Liferay.Util.navigate(url.toString());
	});

	<c:if test="<%= !Objects.equals(viewUADEntitiesDisplay.getApplicationKey(), UADConstants.ALL_APPLICATIONS) %>">
		registerClickHandler(<portlet:namespace />entitiesTypePanelBody, (event) => {
			var url = new URL(baseURL, window.location.origin);

			url.searchParams.set(
				'<portlet:namespace />uadRegistryKey',
				event.target.value
			);

			Liferay.Util.navigate(url.toString());
		});
	</c:if>

	registerClickHandler(<portlet:namespace />scopePanelBody, (event) => {
		var url = new URL(baseURL, window.location.origin);

		url.searchParams.set('<portlet:namespace />applicationKey', '');
		url.searchParams.set('<portlet:namespace />scope', event.target.value);

		Liferay.Util.navigate(url.toString());
	});

	function handleDestroyPortlet() {
		for (var i = 0; i < clickListeners.length; i++) {
			clickListeners[i].dispose();
		}

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>