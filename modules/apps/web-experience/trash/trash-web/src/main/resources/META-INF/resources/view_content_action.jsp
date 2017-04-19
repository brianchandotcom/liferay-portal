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

if (Validator.isNull(redirect)) {
	redirect = trashDisplayContext.getContainerModelRedirectURL();
}

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

String className = null;
long classPK = 0;

if ((row != null) && (row.getObject() instanceof TrashedModel)) {
	TrashedModel trashedModel = (TrashedModel)row.getObject();

	className = ((ClassedModel)trashedModel).getModelClassName();
	classPK = trashedModel.getTrashEntryClassPK();
}
else {
	className = (String)request.getAttribute("view.jsp-className");
	classPK = GetterUtil.getLong(request.getAttribute("view.jsp-classPK"));
}

TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= trashHandler.isMovable() %>">
		<portlet:renderURL var="moveURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/view_container_model.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(className)) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
			<portlet:param name="containerModelClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(trashHandler.getContainerModelClassName(classPK))) %>" />
		</portlet:renderURL>

		<%
		String taglibOnClick = renderResponse.getNamespace() + "restoreDialog('" + moveURL + "')";
		%>

		<liferay-ui:icon
			message="restore"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= trashHandler.isDeletable() %>">
		<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="className" value="<%= className %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteEntryURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>