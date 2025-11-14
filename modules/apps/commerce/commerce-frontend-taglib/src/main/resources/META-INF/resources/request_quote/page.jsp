<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/request_quote/init.jsp" %>

<%
HashMap<String, Object> requestQuoteData = HashMapBuilder.<String, Object>put(
	"baseOrderDetailURL", baseOrderDetailURL
).put(
	"cpDefinitionId", cpDefinitionId
).put(
	"cpInstance",
	HashMapBuilder.<String, Object>put(
		"priceOnApplication", priceOnApplication
	).put(
		"skuId", cpInstanceId
	).put(
		"skuOptions", skuOptions
	).build()
).put(
	"createCart", createCart
).put(
	"namespace", namespace
).put(
	"notesPermission", notesPermission
).put(
	"orderDetailURL", orderDetailURL
).put(
	"requestQuoteElementId", requestQuoteElementId
).put(
	"restrictedNotesPermission", restrictedNotesPermission
).build();
%>

<c:if test="<%= priceOnApplication || requestQuoteEnabled %>">
	<div class="request-quote-wrapper" id="<%= requestQuoteElementId %>">
		<button class="btn btn-lg request-quote skeleton">
			<liferay-ui:message key="request-a-quote" />
		</button>

		<react:component
			module="{RequestQuote} from commerce-frontend-js"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"data", requestQuoteData
				).put(
					"style",
					HashMapBuilder.<String, Object>put(
						"displayType", displayType
					).build()
				).build()
			%>'
		/>
	</div>
</c:if>