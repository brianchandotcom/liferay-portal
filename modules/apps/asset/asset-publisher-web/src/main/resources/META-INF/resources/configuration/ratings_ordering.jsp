<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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