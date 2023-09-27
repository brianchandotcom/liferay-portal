<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info_list_basic_list/init.jsp" %>

<c:choose>
	<c:when test="<%= Objects.equals(infoListStyleKey, BasicListInfoListStyle.NUMBERED.getKey()) %>">
		<ol>

			<%
			for (Object infoListObject : infoListObjects) {
			%>

				<li>
					<c:choose>
						<c:when test="<%= (infoItemRenderer instanceof InfoItemTemplatedRenderer) && Validator.isNotNull(templateKey) %>">

							<%
							InfoItemTemplatedRenderer<Object> infoItemTemplatedRenderer = (InfoItemTemplatedRenderer)infoItemRenderer;

							infoItemTemplatedRenderer.render(infoListObject, templateKey, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
							%>

						</c:when>
						<c:otherwise>

							<%
							infoItemRenderer.render(infoListObject, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
							%>

						</c:otherwise>
					</c:choose>
				</li>

			<%
			}
			%>

		</ol>
	</c:when>
	<c:otherwise>
		<ul class="<%= listCssClass %>">

			<%
			for (Object infoListObject : infoListObjects) {
			%>

				<li class="<%= listItemCssClass %>">
					<c:choose>
						<c:when test="<%= (infoItemRenderer instanceof InfoItemTemplatedRenderer) && Validator.isNotNull(templateKey) %>">

							<%
							InfoItemTemplatedRenderer<Object> infoItemTemplatedRenderer = (InfoItemTemplatedRenderer)infoItemRenderer;

							infoItemTemplatedRenderer.render(infoListObject, templateKey, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
							%>

						</c:when>
						<c:otherwise>

							<%
							infoItemRenderer.render(infoListObject, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
							%>

						</c:otherwise>
					</c:choose>
				</li>

			<%
			}
			%>

		</ul>
	</c:otherwise>
</c:choose>