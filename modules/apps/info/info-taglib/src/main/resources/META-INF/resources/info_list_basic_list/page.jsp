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