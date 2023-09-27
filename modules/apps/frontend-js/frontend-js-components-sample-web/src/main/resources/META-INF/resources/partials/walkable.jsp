<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:container-fluid>
	<clay:row>
		<h2>Walkable Sample</h2>

		<div class="btn-group">
			<div class="btn-group-item">
				<clay:button displayType="primary" id="step1" label="Step 1"></clay:button>
			</div>

			<div class="btn-group-item">
				<clay:button displayType="primary" id="step2" label="Step 2"></clay:button>
			</div>

			<div class="btn-group-item">
				<clay:button displayType="primary" id="step3" label="Step 3"></clay:button>
			</div>
		</div>
	</clay:row>

	<clay:row>
		<clay:alert
			displayType="info"
			id="step4"
			message="Whassup?"
			title="Info"
		/>

		<clay:alert
			displayType="info"
			id="step5"
			message="Whassup 2?"
			title="Info 2"
		/>
	</clay:row>

	<div>
		<react:component
			module="js/SampleWalkthrough"
		/>
	</div>
</clay:container-fluid>