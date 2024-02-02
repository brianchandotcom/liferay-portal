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
			module="{FeatureIndicatorSamples} from frontend-js-components-sample-web"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"learnResourceContext", LearnMessageUtil.getReactDataJSONObject("frontend-js-components-web")
				).build()
			%>'
		/>
	</div>

	<clay:row>
		<clay:col>
			<h2>liferay-frontend:feature-indicator Tag in JSP</h2>
		</clay:col>
	</clay:row>

	<clay:row
		cssClass="p-3"
	>
		<clay:col>
			<h3>Beta Interactive</h3>

			<liferay-frontend:feature-indicator
				interactive="<%= true %>"
				type="beta"
			/>
		</clay:col>

		<clay:col>
			<h3>Beta</h3>

			<liferay-frontend:feature-indicator
				type="beta"
			/>
		</clay:col>

		<clay:col>
			<h3>Deprecated Interactive</h3>

			<liferay-frontend:feature-indicator
				interactive="<%= true %>"
				type="deprecated"
			/>
		</clay:col>

		<clay:col>
			<h3>Deprecated</h3>

			<liferay-frontend:feature-indicator
				type="deprecated"
			/>
		</clay:col>
	</clay:row>

	<clay:row
		cssClass="bg-dark p-3 text-light"
	>
		<clay:col>
			<h3>Dark Beta Interactive</h3>

			<liferay-frontend:feature-indicator
				dark="<%= true %>"
				interactive="<%= true %>"
				type="beta"
			/>
		</clay:col>

		<clay:col>
			<h3>Dark Beta</h3>

			<liferay-frontend:feature-indicator
				dark="<%= true %>"
				type="beta"
			/>
		</clay:col>

		<clay:col>
			<h3>Dark Deprecated Interactive</h3>

			<liferay-frontend:feature-indicator
				dark="<%= true %>"
				interactive="<%= true %>"
				type="deprecated"
			/>
		</clay:col>

		<clay:col>
			<h3>Dark Deprecated</h3>

			<liferay-frontend:feature-indicator
				dark="<%= true %>"
				type="deprecated"
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>