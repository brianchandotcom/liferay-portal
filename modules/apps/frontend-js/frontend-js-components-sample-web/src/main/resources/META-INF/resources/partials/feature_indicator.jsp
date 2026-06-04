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
		cssClass="align-items-center p-2"
	>
		<clay:col
			size="2"
		>
			<h4>Type</h4>
		</clay:col>

		<clay:col>
			<h4>Interactive</h4>
		</clay:col>

		<clay:col>
			<h4>Default</h4>
		</clay:col>

		<clay:col>
			<h4>Icon Only</h4>
		</clay:col>

		<clay:col>
			<h4>Interactive Icon Only</h4>
		</clay:col>
	</clay:row>

	<%
	for (String featureIndicatorType : new String[] {"beta", "deprecated", "enterprise", "maintenance", "private-beta"}) {
	%>

		<clay:row
			cssClass="align-items-center mb-2"
		>
			<clay:col
				size="2"
			>
				<strong class="text-uppercase"><%= featureIndicatorType %></strong>
			</clay:col>

			<clay:col>
				<liferay-frontend:feature-indicator
					interactive="<%= true %>"
					type="<%= featureIndicatorType %>"
				/>
			</clay:col>

			<clay:col>
				<liferay-frontend:feature-indicator
					type="<%= featureIndicatorType %>"
				/>
			</clay:col>

			<clay:col>
				<liferay-frontend:feature-indicator
					iconOnly="<%= true %>"
					type="<%= featureIndicatorType %>"
				/>
			</clay:col>

			<clay:col>
				<liferay-frontend:feature-indicator
					iconOnly="<%= true %>"
					interactive="<%= true %>"
					type="<%= featureIndicatorType %>"
				/>
			</clay:col>
		</clay:row>

		<clay:row
			cssClass="align-items-center bg-dark mb-3 p-2 text-light"
		>
			<clay:col
				size="2"
			>
				<strong class="text-uppercase"><%= featureIndicatorType %> (dark)</strong>
			</clay:col>

			<clay:col>
				<liferay-frontend:feature-indicator
					dark="<%= true %>"
					interactive="<%= true %>"
					type="<%= featureIndicatorType %>"
				/>
			</clay:col>

			<clay:col>
				<liferay-frontend:feature-indicator
					dark="<%= true %>"
					type="<%= featureIndicatorType %>"
				/>
			</clay:col>

			<clay:col>
				<liferay-frontend:feature-indicator
					dark="<%= true %>"
					iconOnly="<%= true %>"
					type="<%= featureIndicatorType %>"
				/>
			</clay:col>

			<clay:col>
				<liferay-frontend:feature-indicator
					dark="<%= true %>"
					iconOnly="<%= true %>"
					interactive="<%= true %>"
					type="<%= featureIndicatorType %>"
				/>
			</clay:col>
		</clay:row>

	<%
	}
	%>

</clay:container-fluid>