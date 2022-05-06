<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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