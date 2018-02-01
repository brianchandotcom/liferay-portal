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
UADEntityTypeComposite entityTypeComposite = (UADEntityTypeComposite)request.getAttribute(UserAssociatedDataWebKeys.UAD_ENTITY_TYPE_COMPOSITE);

UADEntityDisplay uadEntityDisplay = entityTypeComposite.getUADEntityDisplay();
%>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-entities-remain-of-this-type"
		id="UADEntities"
	>
		<liferay-ui:search-container-results
			results="<%= entityTypeComposite.getUADEntities() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.user.associated.data.entity.UADEntity"
			escapedModel="<%= true %>"
			keyProperty="name"
			modelVar="uadEntity"
		>
			<liferay-ui:search-container-column-text
				name="entity-id"
				property="UADEntityId"
			/>

			<liferay-ui:search-container-column-text
				href="<%= uadEntityDisplay.getEditURL(uadEntity, liferayPortletRequest, liferayPortletResponse) %>"
				name="edit-url"
				value="<%= uadEntityDisplay.getEditURL(uadEntity, liferayPortletRequest, liferayPortletResponse) %>"
			/>

			<liferay-ui:search-container-column-text
				name="nonanonymizable-fields"
				value="<%= uadEntityDisplay.getEntityNonAnonymizableFieldValues(uadEntity) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/entity_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>