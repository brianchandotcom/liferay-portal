<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/custom_attribute_list/init.jsp" %>

<%
Set<Locale> availableLocales = (Set<Locale>)request.getAttribute("liferay-expando:custom-attribute-list:availableLocales");
String className = (String)request.getAttribute("liferay-expando:custom-attribute-list:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-expando:custom-attribute-list:classPK"));
boolean editable = GetterUtil.getBoolean((String)request.getAttribute("liferay-expando:custom-attribute-list:editable"));
String ignoreAttributeNames = GetterUtil.getString((String)request.getAttribute("liferay-expando:custom-attribute-list:ignoreAttributeNames"));
boolean label = GetterUtil.getBoolean((String)request.getAttribute("liferay-expando:custom-attribute-list:label"));

ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), className, classPK);

String modelResourceName = ResourceActionsUtil.getModelResource(request, className);

List<String> attributeNames = ListUtil.remove(Collections.list(expandoBridge.getAttributeNames()), ListUtil.fromString(ignoreAttributeNames, StringPool.COMMA));
%>

<div class="taglib-custom-attributes-list">

	<%
	for (String attributeName : attributeNames) {
	%>

		<liferay-expando:custom-attribute
			availableLocales="<%= availableLocales %>"
			className="<%= className %>"
			classPK="<%= classPK %>"
			editable="<%= editable %>"
			label="<%= label %>"
			name="<%= attributeName %>"
		/>

	<%
	}
	%>

	<c:if test="<%= attributeNames.isEmpty() %>">
		<span class="field">
			<span class="field-content">
				<label>
					<liferay-ui:message arguments="<%= modelResourceName %>" key="no-custom-fields-are-defined-for-x" translateArguments="<%= false %>" />
				</label>
			</span>
		</span>
	</c:if>
</div>