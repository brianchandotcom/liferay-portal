<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div>
	<react:component
		module="js/App"
	/>
</div>

<div class="h1">Feature Indicator (JSP)</div>

<div class="p-3">
	<div class="d-inline-block">
		<react:component
			module="{FeatureIndicator} from frontend-js-components-web"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"interactive", "true"
				).put(
					"learnResourceContext", LearnMessageUtil.getReactDataJSONObject("frontend-js-components-web")
				).put(
					"type", "beta"
				).build()
			%>'
		/>
	</div>

	<div class="d-inline-block">
		<react:component
			module="{FeatureIndicator} from frontend-js-components-web"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"interactive", "true"
				).put(
					"learnResourceContext", LearnMessageUtil.getReactDataJSONObject("frontend-js-components-web")
				).put(
					"type", "deprecated"
				).build()
			%>'
		/>
	</div>
</div>