<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/template_selector/init.jsp" %>

<clay:content-row
	floatElements=""
	verticalAlign="center"
>
	<clay:content-col
		cssClass="inline-item-before"
	>
		<react:component
			module="{DisplayTemplateSelector} from template-taglib"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"namespace", liferayPortletResponse.getNamespace()
				).put(
					"props", request.getAttribute("liferay-template:template-selector:templateSelectorProps")
				).build()
			%>'
		/>
	</clay:content-col>
</clay:content-row>