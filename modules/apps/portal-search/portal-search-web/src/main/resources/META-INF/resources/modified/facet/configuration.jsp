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

<%@ page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<portlet:defineObjects />

<%
ModifiedFacetPortletPreferences modifiedFacetPortletPreferences = new ModifiedFacetPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));

JSONArray rangesJSONArray = modifiedFacetPortletPreferences.getRangesJSONArray();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<aui:fieldset id='<%= renderResponse.getNamespace() + "rangesId" %>'>

				<%
				int[] rangesIndexes = new int[rangesJSONArray.length()];

				for (int i = 0; i < rangesJSONArray.length(); i++) {
					rangesIndexes[i] = i;

					JSONObject jsonObject = rangesJSONArray.getJSONObject(i);
				%>

					<div class="lfr-form-row lfr-form-row-inline">
						<div class="row-fields">
							<aui:input label="label" name='<%= "label_" + i %>' value='<%= jsonObject.getString("label") %>' />
						</div>
					</div>

				<%
				}
				%>

				<aui:input name="<%= PortletPreferencesJspUtil.getInputName(ModifiedFacetPortletPreferences.PREFERENCE_KEY_RANGES) %>" type="hidden" value="<%= modifiedFacetPortletPreferences.getRangesString() %>" />

				<aui:input name='<%= "rangesIndexes" %>' type="hidden" value="<%= StringUtil.merge(rangesIndexes) %>" />
			</aui:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="liferay-auto-fields">
	var autoFields = new Liferay.AutoFields(
		{
			contentBox: 'fieldset#<portlet:namespace />rangesId',
			fieldIndexes: '<portlet:namespace />rangesIndexes',
			namespace: '<portlet:namespace />'
		}
	).render();
</aui:script>

<aui:script>
	var form = AUI.$(document.<portlet:namespace />fm);

	$('#<portlet:namespace />fm').on(
		'submit',
		function(event) {
			event.preventDefault();

			var ranges = [];

			for (var i = 0 ; i < <%= rangesJSONArray.length() %> ; i ++) {
				var label = $('[name="<portlet:namespace />label_' + i +'"]').val();

				ranges.push({
					label : label
				});
			}
			form.fm('<%= PortletPreferencesJspUtil.getInputName(ModifiedFacetPortletPreferences.PREFERENCE_KEY_RANGES) %>').val(JSON.stringify(ranges));

			submitForm(form);
		}
	);
</aui:script>