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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

ObjectEntryDisplayContext objectEntryDisplayContext = (ObjectEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectDefinition objectDefinition = objectEntryDisplayContext.getObjectDefinition();
ObjectEntry objectEntry = objectEntryDisplayContext.getObjectEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/object_entries/edit_object_entry" var="editObjectEntryURL" />

<liferay-frontend:edit-form
	action="<%= editObjectEntryURL %>"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (objectEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="objectEntryId" type="hidden" value="<%= (objectEntry == null) ? 0 : objectEntry.getObjectEntryId() %>" />
		<aui:input name="objectDefinitionId" type="hidden" value="<%= objectDefinition.getObjectDefinitionId() %>" />
		<aui:input name="ddmFormValues" type="hidden" value="" />

		<liferay-frontend:fieldset-group>
			<clay:sheet-section>
				<clay:row>
					<clay:col
						md="11"
					>
						<%= objectEntryDisplayContext.renderDDMForm(pageContext) %>
					</clay:col>
				</clay:row>
			</clay:sheet-section>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button name="save" onClick='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "submitObjectEntry();" %>' type="submit" value="save" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />submitObjectEntry() {
		const form = document.getElementById('<portlet:namespace />fm');

		const DDMFormInstance = Liferay.component('editObjectEntry');

		const current = DDMFormInstance.reactComponentRef.current;

		current.validate().then((result) => {
			if (result) {
				const ddmFormValues = form.querySelector(
					'#<portlet:namespace />ddmFormValues'
				);

				const fields = current.getFields();

				const values = fields.reduce(
					(obj, cur) => Object.assign(obj, {[cur.fieldName]: cur.value}),
					{}
				);

				ddmFormValues.value = JSON.stringify(values);

				form.submit();
			}
		});
	}
</aui:script>