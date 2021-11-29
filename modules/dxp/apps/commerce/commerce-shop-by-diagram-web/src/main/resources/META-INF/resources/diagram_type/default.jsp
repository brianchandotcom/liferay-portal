<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<div class="row">
	<div class="col-lg-8 d-flex flex-column">
		<commerce-ui:panel
			bodyClasses="p-0"
			elementClasses="flex-fill"
			title='<%= LanguageUtil.get(resourceBundle, "diagram-mapping") %>'
		>
			<div>
				<span aria-hidden="true" class="loading-animation"></span>

				<react:component
					module="js/Diagram/Diagram"
					props="<%= (Map<String, Object>)request.getAttribute(CSDiagramWebKeys.CS_DIAGRAM_CP_TYPE_PROPS) %>"
				/>
			</div>
		</commerce-ui:panel>
	</div>

	<div class="col-lg-4">
		<commerce-ui:panel
			bodyClasses="p-0"
			elementClasses="flex-fill"
			title='<%= LanguageUtil.get(resourceBundle, "mapped-products") %>'
		>
			<react:component
				module="js/DiagramTable/DiagramTable"
				props="<%= (Map<String, Object>)request.getAttribute(CSDiagramWebKeys.CS_DIAGRAM_CP_TYPE_PROPS) %>"
			/>
		</commerce-ui:panel>
	</div>
</div>