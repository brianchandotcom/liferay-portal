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

<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />
<liferay-ui:error exception="<%= LARFileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />

<liferay-ui:error exception="<%= LARTypeException.class %>">

	<%
	LARTypeException lte = (LARTypeException)errorException;
	%>

	<liferay-ui:message arguments="<%= lte.getMessage() %>" key="please-import-a-lar-file-of-the-correct-type-x" />
</liferay-ui:error>

<liferay-ui:error exception="<%= ExportImportMVCActionCommand.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

<liferay-ui:error exception="<%= LocaleException.class %>">

	<%
	LocaleException le = (LocaleException)errorException;
	%>

	<c:if test="<%= le.getType() == LocaleException.TYPE_EXPORT_IMPORT %>">
		<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-available-languages-in-the-lar-file-x-do-not-match-the-site's-available-languages-x" translateArguments="<%= false %>" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="an-error-occurred-because-the-live-group-does-not-have-the-current-page" />
<liferay-ui:error exception="<%= PortletIdException.class %>" message="please-import-a-lar-file-for-the-current-portlet" />

<liferay-ui:error exception="<%= PortletDataException.class %>">

	<%
	PortletDataException pde = (PortletDataException)errorException;
	%>

	<c:if test="<%= pde.getType() == PortletDataException.FUTURE_END_DATE %>">
		<liferay-ui:message key="please-enter-a-valid-end-date-that-is-in-the-past" />
	</c:if>

	<c:if test="<%= pde.getType() == PortletDataException.FUTURE_START_DATE %>">
		<liferay-ui:message key="please-enter-a-valid-start-date-that-is-in-the-past" />
	</c:if>

	<c:if test="<%= pde.getType() == PortletDataException.START_DATE_AFTER_END_DATE %>">
		<liferay-ui:message key="please-enter-a-start-date-that-comes-before-the-end-date" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= StructureDuplicateStructureKeyException.class %>">

	<%
	StructureDuplicateStructureKeyException sdske = (StructureDuplicateStructureKeyException)errorException;
	%>

	<liferay-ui:message arguments="<%= sdske.getStructureKey() %>" key="dynamic-data-mapping-structure-with-structure-key-x-already-exists" translateArguments="<%= false %>" />
</liferay-ui:error>