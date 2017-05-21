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
CPInstance cpInstance = (CPInstance)request.getAttribute(CPWebKeys.CP_INSTANCE);

CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CPDefinitionOptionRel> cpDefinitionOptionRels = cpInstanceDisplayContext.getCPDefinitionOptionRels();

Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>> cpDefinitionOptionRelListMap = cpInstanceDisplayContext.parseCPInstanceDDMContent(cpInstanceDisplayContext.getCPInstanceId());
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpInstance %>" model="<%= CPInstance.class %>" />

<aui:fieldset>
	<c:choose>
		<c:when test="<%= cpInstance != null %>">

			<%
			for (CPDefinitionOptionRel cpDefinitionOptionRel : cpDefinitionOptionRels) {
				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels = cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);
				StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);
			%>

				<h6 class="text-default">
					<strong><%= HtmlUtil.escape(cpDefinitionOptionRel.getTitle(languageId)) %></strong>

					<%
					for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : cpDefinitionOptionValueRels) {
						stringJoiner.add(cpDefinitionOptionValueRel.getTitle(languageId));
					}
					%>

					<%= HtmlUtil.escape(stringJoiner.toString()) %>
				</h6>

			<%
			}
			%>

		</c:when>
		<c:otherwise>
			<%= cpInstanceDisplayContext.renderOptions(renderRequest, renderResponse) %>

			<aui:input name="ddmFormValues" type="hidden" />
		</c:otherwise>
	</c:choose>
</aui:fieldset>