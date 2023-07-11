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
TrashContainerModelDisplayContext trashContainerModelDisplayContext = new TrashContainerModelDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<p class="p-3">
	<liferay-ui:message arguments="<%= trashContainerModelDisplayContext.getMissingContainerMessageArguments() %>" key="the-original-x-of-this-file-does-not-exist-anymore" translateArguments="<%= false %>" />
</p>

<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="selectContainerFm">
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= trashDisplayContext.getContainerModelBreadcrumbEntries(trashContainerModelDisplayContext.getContainerModelClassName(), trashContainerModelDisplayContext.getContainerModelId(), trashContainerModelDisplayContext.getContainerURL()) %>"
	/>

	<aui:button-row>
		<aui:button
			cssClass="selector-button"
			data='<%=
				HashMapBuilder.<String, Object>put(
					"classname", trashContainerModelDisplayContext.getClassName()
				).put(
					"classpk", trashContainerModelDisplayContext.getClassPK()
				).put(
					"containermodelid", trashContainerModelDisplayContext.getContainerModelId()
				).put(
					"redirect", trashContainerModelDisplayContext.getRedirect()
				).build()
			%>'
			value='<%= LanguageUtil.format(request, "choose-this-x", trashContainerModelDisplayContext.getContainerModelName()) %>'
		/>
	</aui:button-row>

	<liferay-ui:search-container
		searchContainer="<%= trashContainerModelDisplayContext.getSearchContainer() %>"
		total="<%= trashContainerModelDisplayContext.getContainerModelsCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= trashContainerModelDisplayContext.getContainerModels() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.ContainerModel"
			keyProperty="containerModelId"
			modelVar="curContainerModel"
		>

			<%
			long curContainerModelId = curContainerModel.getContainerModelId();

			PortletURL containerURL = PortletURLBuilder.create(
				trashContainerModelDisplayContext.getContainerURL()
			).setParameter(
				"containerModelId", curContainerModelId
			).buildPortletURL();

			TrashHandler curContainerTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(curContainerModel.getModelClassName());
			%>

			<liferay-ui:search-container-column-text
				name="<%= LanguageUtil.get(request, trashContainerModelDisplayContext.getContainerModelName()) %>"
			>
				<c:choose>
					<c:when test="<%= curContainerModel.getContainerModelId() > 0 %>">
						<clay:link
							href="<%= containerURL.toString() %>"
							label="<%= curContainerModel.getContainerModelName() %>"
						/>
					</c:when>
					<c:otherwise>
						<%= curContainerModel.getContainerModelName() %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name='<%= LanguageUtil.format(request, "num-of-x", trashContainerModelDisplayContext.getContainerModelName()) %>'
				value="<%= String.valueOf(curContainerTrashHandler.getContainerModelsCount(curContainerModelId, curContainerModel.getParentContainerModelId())) %>"
			/>

			<liferay-ui:search-container-column-text>
				<aui:button
					cssClass="selector-button"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"classname", trashContainerModelDisplayContext.getClassName()
						).put(
							"classpk", trashContainerModelDisplayContext.getClassPK()
						).put(
							"containermodelid", curContainerModelId
						).put(
							"redirect", trashContainerModelDisplayContext.getRedirect()
						).build()
					%>'
					value="choose"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>