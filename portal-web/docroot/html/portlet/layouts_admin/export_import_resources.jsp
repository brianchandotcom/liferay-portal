<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String cmd = Constants.IMPORT;

long groupId = ParamUtil.getLong(request, "groupId");

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}
else {
	group = (Group)request.getAttribute(WebKeys.GROUP);
}

Group liveGroup = group;

if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();
}

boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
%>

<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />

<liferay-ui:error exception="<%= LARFileSizeException.class %>">
	<liferay-ui:message arguments="<%= PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE) / 1024 %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" />
</liferay-ui:error>

<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-lar-file-of-the-correct-type" />
<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

<liferay-ui:error exception="<%= LayoutPrototypeException.class %>">

	<%
	LayoutPrototypeException lpe = (LayoutPrototypeException)errorException;
	%>

	<liferay-ui:message key="the-lar-file-could-not-be-imported-because-it-requires-page-templates-or-site-templates-that-could-not-be-found.-please-import-the-following-templates-manually" />

	<ul>

		<%
		List<Tuple> missingLayoutPrototypes = lpe.getMissingLayoutPrototypes();

		for (Tuple missingLayoutPrototype : missingLayoutPrototypes) {
			String layoutPrototypeClassName = (String)missingLayoutPrototype.getObject(0);
			String layoutPrototypeUuid = (String)missingLayoutPrototype.getObject(1);
			String layoutPrototypeName = (String)missingLayoutPrototype.getObject(2);
		%>

			<li>
				<%= ResourceActionsUtil.getModelResource(locale, layoutPrototypeClassName) %>: <strong><%= layoutPrototypeName %></strong> (<%= layoutPrototypeUuid %>)
			</li>

		<%
		}
		%>

	</ul>
</liferay-ui:error>

<liferay-ui:error exception="<%= LocaleException.class %>">

	<%
	LocaleException le = (LocaleException)errorException;
	%>

	<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-available-languages-in-the-lar-file-x-do-not-match-the-portal's-available-languages-x" />
</liferay-ui:error>

<liferay-ui:error exception="<%= RecordSetDuplicateRecordSetKeyException.class %>">

	<%
	RecordSetDuplicateRecordSetKeyException rsdrske = (RecordSetDuplicateRecordSetKeyException)errorException;
	%>

	<liferay-ui:message arguments="<%= rsdrske.getRecordSetKey() %>" key="dynamic-data-list-record-set-with-record-set-key-x-already-exists" />
</liferay-ui:error>

<liferay-ui:error exception="<%= StructureDuplicateStructureKeyException.class %>">

	<%
	StructureDuplicateStructureKeyException sdske = (StructureDuplicateStructureKeyException)errorException;
	%>

	<liferay-ui:message arguments="<%= sdske.getStructureKey() %>" key="dynamic-data-mapping-structure-with-structure-key-x-already-exists" />
</liferay-ui:error>

<portlet:actionURL var="importPagesURL">
	<portlet:param name="struts_action" value="/layouts_admin/import_layouts" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
</portlet:actionURL>

<aui:form action="<%= importPagesURL %>" cssClass="lfr-export-dialog" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.IMPORT %>" />

	<%@ include file="/html/portlet/layouts_admin/import_options.jspf" %>

	<aui:button-row>
		<aui:button type="submit" value="import" />
	</aui:button-row>
</aui:form>

<c:if test='<%= cmd.equals(Constants.IMPORT) && SessionMessages.contains(liferayPortletRequest, "requestProcessed") %>'>
	<aui:script>
		var opener = Liferay.Util.getOpener();

		if (opener.<portlet:namespace />saveLayoutset) {
			Liferay.fire(
				'closeWindow',
				{
					id: '<portlet:namespace />importDialog'
				}
			);

			opener.<portlet:namespace />saveLayoutset('view');
		}
	</aui:script>
</c:if>