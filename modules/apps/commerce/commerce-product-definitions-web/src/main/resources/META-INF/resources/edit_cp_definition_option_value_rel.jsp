<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionOptionValueRelDisplayContext cpDefinitionOptionValueRelDisplayContext = (CPDefinitionOptionValueRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionOptionValueRel cpDefinitionOptionValueRel = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionValueRel();
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition_option_value_rel" var="editProductDefinitionOptionValueRelActionURL" />

<c:choose>
	<c:when test="<%= cpDefinitionOptionValueRel == null %>">
		<commerce-ui:modal-content
			title='<%= LanguageUtil.get(request, "add-value") %>'
		>
			<aui:form action="<%= editProductDefinitionOptionValueRelActionURL %>" method="post" name="cpDefinitionOptionValueRelfm">
				<%@ include file="/edit_cp_definition_option_value_rel.jspf" %>

				<c:if test="<%= cpDefinitionOptionValueRelDisplayContext.hasCustomAttributesAvailable() %>">
					<liferay-expando:custom-attribute-list
						className="<%= CPDefinitionOptionValueRel.class.getName() %>"
						classPK="<%= (cpDefinitionOptionValueRel != null) ? cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</c:if>
			</aui:form>
		</commerce-ui:modal-content>
	</c:when>
	<c:otherwise>
		<liferay-frontend:side-panel-content
			title='<%= LanguageUtil.format(request, "edit-x", cpDefinitionOptionValueRel.getName(languageId), false) %>'
		>
			<aui:form action="<%= editProductDefinitionOptionValueRelActionURL %>" method="post" name="cpDefinitionOptionValueRelfm">
				<commerce-ui:panel
					title='<%= LanguageUtil.get(request, "details") %>'
				>
					<%@ include file="/edit_cp_definition_option_value_rel.jspf" %>
				</commerce-ui:panel>

				<c:if test="<%= cpDefinitionOptionValueRelDisplayContext.hasCustomAttributesAvailable() %>">
					<commerce-ui:panel
						title='<%= LanguageUtil.get(request, "custom-attribute") %>'
					>
						<liferay-expando:custom-attribute-list
							className="<%= CPDefinitionOptionValueRel.class.getName() %>"
							classPK="<%= (cpDefinitionOptionValueRel != null) ? cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId() : 0 %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</commerce-ui:panel>
				</c:if>

				<aui:button cssClass="btn-lg" type="submit" value="save" />
			</aui:form>
		</liferay-frontend:side-panel-content>
	</c:otherwise>
</c:choose>