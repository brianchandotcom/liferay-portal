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

<div class="fragment-thumbnail">
	<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid thumbnail-container">
		<div class="loading-animation"></div>
	</div>

	<div class="autofit-float autofit-row button-row">
		<div class="autofit-col">
			<button class="btn btn-default">
				<liferay-ui:message key="change" />
			</button>
		</div>

		<div class="autofit-col autofit-col-end">
			<button class="btn btn-default">
				<liferay-ui:message key="cancel" />
			</button>

			<button class="btn btn-primary">
				<liferay-ui:message key="ok" />
			</button>
		</div>
	</div>
</div>