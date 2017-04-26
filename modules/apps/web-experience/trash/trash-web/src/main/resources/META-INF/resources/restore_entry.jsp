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
long trashEntryId = ParamUtil.getLong(request, "trashEntryId");

long duplicateEntryId = ParamUtil.getLong(request, "duplicateEntryId");
String oldName = ParamUtil.getString(request, "oldName");
boolean overridable = ParamUtil.getBoolean(request, "overridable");

PortletURL backURL = renderResponse.createRenderURL();

String redirect = ParamUtil.getString(request, "redirect", backURL.toString());

TrashEntry entry = TrashEntryLocalServiceUtil.getEntry(trashEntryId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(oldName);
%>

<liferay-ui:error exception="<%= RestoreEntryException.class %>" message='<%= LanguageUtil.format(request, "an-entry-with-name-x-already-exists", HtmlUtil.escape(oldName)) %>' translateMessage="<%= false %>" />

<liferay-portlet:actionURL name="restoreEntry" varImpl="restoreURL" />

<aui:form action="<%= restoreURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="trashEntryId" type="hidden" value="<%= trashEntryId %>" />
	<aui:input name="duplicateEntryId" type="hidden" value="<%= duplicateEntryId %>" />
	<aui:input name="oldName" type="hidden" value="<%= oldName %>" />

	<aui:fieldset-group markupview="lexicon">
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= overridable %>">
					<aui:input checked="<%= true %>" id="override" label="overwrite-the-existing-entry-with-the-one-from-the-recycle-bin" name="<%= Constants.CMD %>" type="radio" value="<%= Constants.OVERRIDE %>" />

					<aui:input id="rename" label="keep-both-entries-and-rename-the-entry-from-the-recycle-bin-as" name="<%= Constants.CMD %>" type="radio" value="<%= Constants.RENAME %>" />
				</c:when>
				<c:otherwise>
					<aui:input id="rename" name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.RENAME %>" />
				</c:otherwise>
			</c:choose>

			<aui:input label='<%= overridable ? StringPool.BLANK : "keep-both-entries-and-rename-the-entry-from-the-recycle-bin-as" %>' name="newName" value="<%= com.liferay.trash.kernel.util.TrashUtil.getNewName(themeDisplay, entry.getClassName(), entry.getClassPK(), oldName) %>" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-cancel btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	var rename = A.one('#<portlet:namespace />rename');
	var newName = A.one('#<portlet:namespace />newName');

	rename.on('click', A.fn('focusFormField', Liferay.Util, newName));

	newName.on(
		'focus',
		function(event) {
			rename.attr('checked', true);
		}
	);
</aui:script>