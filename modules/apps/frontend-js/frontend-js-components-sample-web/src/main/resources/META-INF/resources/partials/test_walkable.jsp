<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:container-fluid>
	<clay:row>
		<h2>Test Walkable Sample</h2>

		<div class="btn-group">
			<div class="btn-group-item">
				<clay:button displayType="primary" id="teststep1" label="Step 1"></clay:button>
			</div>
		</div>
	</clay:row>

	<clay:row>
		<p>Walkable sample to test walkthrough behavior with no element on page.</p>
	</clay:row>

	<div>
		<react:component
			module="js/TestSampleWalkthrough"
		/>
	</div>
</clay:container-fluid>