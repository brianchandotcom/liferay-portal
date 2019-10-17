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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%@ page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
SortPortletPreferences sortPortletPreferences = new SortPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));

JSONArray fieldsJSONArray = sortPortletPreferences.getFieldsJSONArray();
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
			<aui:fieldset id='<%= renderResponse.getNamespace() + "fieldsId" %>'>

				<%
				int[] fieldsIndexes = new int[fieldsJSONArray.length()];

				for (int i = 0; i < fieldsJSONArray.length(); i++) {
					fieldsIndexes[i] = i;

					JSONObject jsonObject = fieldsJSONArray.getJSONObject(i);
				%>

					<div class="field-form-row lfr-form-row lfr-form-row-inline">
						<div class="row-fields">
							<aui:input cssClass="label-input" label="label" name='<%= "label_" + i %>' value='<%= jsonObject.getString("label") %>' />

							<aui:input cssClass="sort-field-input" label="field" name='<%= "field_" + i %>' value='<%= jsonObject.getString("field") %>' />
						</div>
					</div>

				<%
				}
				%>

				<aui:input cssClass="fields-input" name="<%= PortletPreferencesJspUtil.getInputName(SortPortletPreferences.PREFERENCE_KEY_FIELDS) %>" type="hidden" value="<%= sortPortletPreferences.getFieldsString() %>" />

				<aui:input name="fieldsIndexes" type="hidden" value="<%= StringUtil.merge(fieldsIndexes) %>" />
			</aui:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="liferay-auto-fields">
	var autoFields = new Liferay.AutoFields({
		contentBox: 'fieldset#<portlet:namespace />fieldsId',
		fieldIndexes: '<portlet:namespace />fieldsIndexes',
		namespace: '<portlet:namespace />'
	}).render();
</aui:script>

<aui:script use="liferay-search-sort-configuration">
	new Liferay.Search.SortConfiguration(A.one(document.<portlet:namespace />fm));
</aui:script>