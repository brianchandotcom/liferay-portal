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

<clay:row>
	<clay:col
		md="6"
	>
		<aui:select label="order-by" name="preferences--orderByColumn1--" value="<%= assetPublisherDisplayContext.getOrderByColumn1() %>" wrapperCssClass="field-inline w80">
			<aui:option label="average-score" value="ratings" />
			<aui:option label="total-score" value="ratingsTotalScore" />
		</aui:select>
	</clay:col>
</clay:row>