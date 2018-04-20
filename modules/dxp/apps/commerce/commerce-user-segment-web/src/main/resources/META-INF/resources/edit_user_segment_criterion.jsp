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
CommerceUserSegmentDisplayContext commerceUserSegmentDisplayContext = (CommerceUserSegmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceUserSegmentEntry commerceUserSegmentEntry = commerceUserSegmentDisplayContext.getCommerceUserSegmentEntry();
CommerceUserSegmentCriterion commerceUserSegmentCriterion = commerceUserSegmentDisplayContext.getCommerceUserSegmentCriterion();
long commerceUserSegmentEntryId = commerceUserSegmentDisplayContext.getCommerceUserSegmentEntryId();
long commerceUserSegmentCriterionId = commerceUserSegmentDisplayContext.getCommerceUserSegmentCriterionId();
List<CommerceUserSegmentCriterionType> commerceUserSegmentCriterionTypes = commerceUserSegmentDisplayContext.getCommerceUserSegmentCriterionTypes();

String type = BeanParamUtil.getString(commerceUserSegmentCriterion, request, "type");

PortletURL portletURL = commerceUserSegmentDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCommerceUserSegmentCriterion");

String title = LanguageUtil.get(request, (commerceUserSegmentCriterion == null) ? "add-criterion" : "edit-criterion");

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "user-segments"), String.valueOf(renderResponse.createRenderURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, commerceUserSegmentEntry.getName(locale), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, CommerceUserSegmentScreenNavigationConstants.ENTRY_KEY_COMMERCE_USER_SEGMENT_ENTRY_CRITERIA), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, portletURL.toString(), data);
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceUserSegmentCriterion" var="editCommerceUserSegmentCriterionActionURL" />

<aui:form action="<%= editCommerceUserSegmentCriterionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceUserSegmentCriterion();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceUserSegmentCriterion == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="addTypeSettings" type="hidden" />
	<aui:input name="commerceUserSegmentEntryId" type="hidden" value="<%= commerceUserSegmentEntryId %>" />
	<aui:input name="commerceUserSegmentCriterionId" type="hidden" value="<%= commerceUserSegmentCriterionId %>" />
	<aui:input name="deleteTypeSettings" type="hidden" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceUserSegmentCriterionTypeException.class %>" message="please-select-a-valid-criterion-type" />

		<aui:model-context bean="<%= commerceUserSegmentCriterion %>" model="<%= CommerceUserSegmentCriterion.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:select name="type" onChange='<%= renderResponse.getNamespace() + "selectType();" %>' showEmptyOption="<%= true %>">

					<%
					for (CommerceUserSegmentCriterionType commerceUserSegmentCriterionType : commerceUserSegmentCriterionTypes) {
						String commerceUserSegmentCriterionTypeKey = commerceUserSegmentCriterionType.getKey();
					%>

						<aui:option label="<%= commerceUserSegmentCriterionType.getLabel(locale) %>" selected="<%= (commerceUserSegmentCriterion != null) && commerceUserSegmentCriterionTypeKey.equals(type) %>" value="<%= commerceUserSegmentCriterionTypeKey %>" />

					<%
					}
					%>

				</aui:select>

				<%
				CommerceUserSegmentCriterionTypeJSPContributor commerceUserSegmentCriterionTypeJSPContributor = commerceUserSegmentDisplayContext.getCommerceUserSegmentCriterionTypeJSPContributor(type);
				%>

				<c:if test="<%= commerceUserSegmentCriterionTypeJSPContributor != null %>">

					<%
					commerceUserSegmentCriterionTypeJSPContributor.render(commerceUserSegmentEntryId, commerceUserSegmentCriterionId, request, response);
					%>

				</c:if>

				<aui:input name="priority" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceUserSegmentCriterion() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectType',
		function() {
			var A = AUI();

			var type = A.one('#<portlet:namespace />type').val();

			var portletURL = new Liferay.PortletURL.createURL('<%= currentURLObj %>');

			portletURL.setParameter('type', type);

			window.location.replace(portletURL.toString());
		},
		['liferay-portlet-url']
	);
</aui:script>