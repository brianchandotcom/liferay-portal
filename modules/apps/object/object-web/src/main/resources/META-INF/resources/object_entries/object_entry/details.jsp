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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

ObjectEntriesDetailsDisplayContext objectEntriesDetailsDisplayContext = (ObjectEntriesDetailsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

//renderResponse.setTitle(LanguageUtil.format(request, "edit-x", objectDefinition.getShortName(), false));

%>

<portlet:actionURL name="/object_definitions/edit_object_definition" var="editObjectDefinitionURL" />

<div class="container-fluid container-fluid-max-xl container-form-lg container-no-gutters form">
	<clay:sheet>
		<liferay-frontend:fieldset-group>
			<clay:sheet-section>
				<clay:row>
					<clay:col
						md="11"
					>
						<%= objectEntriesDetailsDisplayContext.renderDDMForm(pageContext) %>
					</clay:col>
				</clay:row>
			</clay:sheet-section>
		</liferay-frontend:fieldset-group>
	</clay:sheet>
</div>