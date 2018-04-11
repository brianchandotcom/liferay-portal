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
List<UADEntity> uadEntities = (List<UADEntity>)request.getAttribute(UADWebKeys.INFO_PANEL_UAD_ENTITIES);
UADEntityDisplay uadEntityDisplay = (UADEntityDisplay)request.getAttribute(UADWebKeys.INFO_PANEL_UAD_ENTITY_DISPLAY);
%>

<div class="sidebar sidebar-light">
	<div class="sidebar-body">
		<dl class="sidebar-dl sidebar-section">
			<c:choose>
				<c:when test="<%= ListUtil.isEmpty(uadEntities) %>">
					<div class="sidebar-header">
						<h2 class="sidebar-title"><%= uadEntityDisplay.getTypeName() %></h2>
						<h4 class="sidebar-subtitle"><%= uadEntityDisplay.getApplicationName() %></h4>
					</div>
				</c:when>
				<c:when test="<%= ListUtil.isNotEmpty(uadEntities) && (uadEntities.size() == 1) %>">

					<%
					UADEntity uadEntity = uadEntities.get(0);

					Serializable primaryKey = uadEntity.getPrimaryKey();

					Map<String, Object> displayValues = uadEntityDisplay.getNonanonymizableFieldValues(uadEntity.getEntity());
					String identifierFieldName = uadEntityDisplay.getDisplayFieldNames()[0];
					%>

					<div class="sidebar-header">
						<ul class="sidebar-header-actions">
							<li>
								<liferay-ui:icon-menu
									direction="left-side"
									icon="<%= StringPool.BLANK %>"
									markupView="lexicon"
									message="<%= StringPool.BLANK %>"
									showWhenSingleIcon="<%= true %>"
									triggerCssClass="component-action"
								>
									<portlet:actionURL name="/auto_anonymize_uad_entity" var="autoAnonymizeURL">
										<portlet:param name="primaryKey" value="<%= String.valueOf(uadEntity.getPrimaryKey()) %>" />
									</portlet:actionURL>

									<liferay-ui:icon
										message="anonymize"
										onClick='<%= resourceResponse.getNamespace() + "confirmAction('viewUADEntitiesFm', '" + autoAnonymizeURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-anonymize-this-entity") + "')" %>'
										url="javascript:;"
									/>

									<portlet:actionURL name="/delete_uad_entity" var="deleteURL">
										<portlet:param name="primaryKey" value="<%= String.valueOf(uadEntity.getPrimaryKey()) %>" />
									</portlet:actionURL>

									<liferay-ui:icon
										message="delete"
										onClick='<%= resourceResponse.getNamespace() + "confirmAction('viewUADEntitiesFm', '" + deleteURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this-entity") + "')" %>'
										url="javascript:;"
									/>
								</liferay-ui:icon-menu>
							</li>
						</ul>

						<h2 class="sidebar-title"><%= StringUtil.shorten(String.valueOf(displayValues.get(identifierFieldName)), 200) %></h2>

						<h4 class="sidebar-subtitle"><%= uadEntityDisplay.getTypeName() %></h4>
					</div>

					<div class="sidebar-body">
						<dl class="sidebar-dl sidebar-section">

							<%
							for (Map.Entry<String, Object> entry : displayValues.entrySet()) {
								if (identifierFieldName.equals(entry.getKey())) {
									continue;
								}
							%>

								<dt class="sidebar-dt"><%= entry.getKey() %></dt>
								<dd class="sidebar-dd"><%= StringUtil.shorten(String.valueOf(entry.getValue()), 200) %></dd>

							<%
							}
							%>

						</dl>
					</div>
				</c:when>
				<c:when test="<%= ListUtil.isNotEmpty(uadEntities) && (uadEntities.size() > 1) %>">
					<div class="sidebar-header">
						<h2 class="sidebar-title"><%= uadEntityDisplay.getTypeName() %></h2>
						<h4 class="sidebar-subtitle"><%= uadEntities.size() %> items are selected.</h4>
					</div>
				</c:when>
			</c:choose>
		</dl>
	</div>
</div>