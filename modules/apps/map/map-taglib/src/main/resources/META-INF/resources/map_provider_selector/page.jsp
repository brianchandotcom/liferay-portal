<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/map_provider_selector/init.jsp" %>

<c:choose>
	<c:when test="<%= mapProviders.isEmpty() %>">
		<div class="alert alert-danger">
			<liferay-ui:message key="a-list-of-map-providers-should-be-shown-here" />
		</div>
	</c:when>
	<c:otherwise>
		<p class="small text-secondary"><liferay-ui:message key="select-the-maps-api-provider-to-use-when-displaying-geolocalized-assets" /></p>

		<%
		for (MapProvider mapProvider : mapProviders) {
		%>

			<aui:input checked="<%= Objects.equals(mapProviderKey, mapProvider.getKey()) %>" helpMessage="<%= mapProvider.getHelpMessage() %>" id='<%= mapProvider.getKey() + "Enabled" %>' label="<%= mapProvider.getLabel(locale) %>" name="<%= name %>" type="radio" value="<%= mapProvider.getKey() %>" />

			<div id="<portlet:namespace /><%= mapProvider.getKey() %>Options">

				<%
				request.setAttribute(MapProviderWebKeys.MAP_PROVIDER_CONFIGURATION_PREFIX, configurationPrefix);

				mapProvider.includeConfiguration(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
				%>

			</div>

			<%
			StringBundler sb = new StringBundler(((mapProviders.size() - 1) * 6) - 1);

			for (MapProvider curMapProvider : mapProviders) {
				if (Objects.equals(mapProvider.getKey(), curMapProvider.getKey())) {
					continue;
				}

				sb.append(StringPool.APOSTROPHE);
				sb.append(namespace);
				sb.append(curMapProvider.getKey());
				sb.append("Options");
				sb.append(StringPool.APOSTROPHE);
				sb.append(StringPool.COMMA);
			}

			if (mapProviders.size() > 1) {
				sb.setIndex(sb.index() - 1);
			}
			%>

			<aui:script>
				Liferay.Util.toggleRadio(
					'<%= namespace %><%= mapProvider.getKey() %>Enabled',
					'<%= namespace %><%= mapProvider.getKey() %>Options',
					[<%= sb.toString() %>]
				);
			</aui:script>

		<%
		}
		%>

	</c:otherwise>
</c:choose>