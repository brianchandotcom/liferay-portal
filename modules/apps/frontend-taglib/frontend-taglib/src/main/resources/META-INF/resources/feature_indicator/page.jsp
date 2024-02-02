<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/feature_indicator/init.jsp" %>

<%
boolean dark = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:feature-indicator:dark"));
boolean interactive = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:feature-indicator:interactive"));
String tooltipAlign = (String)request.getAttribute("liferay-frontend:feature-indicator:tooltipAlign");
String type = (String)request.getAttribute("liferay-frontend:feature-indicator:type");
%>

<div>
	<react:component
		module="{FeatureIndicator} from frontend-js-components-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"dark", dark
			).put(
				"interactive", interactive
			).put(
				"learnResourceContext", LearnMessageUtil.getReactDataJSONObject("frontend-js-components-web")
			).put(
				"tooltipAlign", tooltipAlign
			).put(
				"type", type
			).build()
		%>'
	/>
</div>