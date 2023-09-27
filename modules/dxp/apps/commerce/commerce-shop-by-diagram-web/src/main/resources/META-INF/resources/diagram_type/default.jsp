<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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