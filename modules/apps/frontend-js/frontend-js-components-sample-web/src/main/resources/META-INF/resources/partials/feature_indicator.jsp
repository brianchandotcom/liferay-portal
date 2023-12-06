<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:container-fluid>
	<clay:row>
		<clay:col>
			<h2>React Component</h2>
		</clay:col>
	</clay:row>

	<div>
		<react:component
			module="js/FeatureIndicatorSamples"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"learnResourceContext", LearnMessageUtil.getReactDataJSONObject("frontend-js-components-web")
				).build()
			%>'
		/>
	</div>

	<clay:row>
		<clay:col>
			<h2>JSP Direct Instantiation of React Component</h2>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col>
			<h3>Beta Interactive</h3>

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
		</clay:col>

		<clay:col>
			<h3>Beta</h3>

			<react:component
				module="{FeatureIndicator} from frontend-js-components-web"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"learnResourceContext", LearnMessageUtil.getReactDataJSONObject("frontend-js-components-web")
					).put(
						"type", "beta"
					).build()
				%>'
			/>
		</clay:col>

		<clay:col>
			<h3>Deprecated Interactive</h3>

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
		</clay:col>

		<clay:col>
			<h3>Deprecated</h3>

			<react:component
				module="{FeatureIndicator} from frontend-js-components-web"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"learnResourceContext", LearnMessageUtil.getReactDataJSONObject("frontend-js-components-web")
					).put(
						"type", "deprecated"
					).build()
				%>'
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>