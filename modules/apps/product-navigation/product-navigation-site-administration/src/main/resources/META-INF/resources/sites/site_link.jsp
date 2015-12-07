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

<%
boolean privateLayout = (Boolean)request.getAttribute("my_sites.jsp-privateLayout");
boolean selectedSite = (Boolean)request.getAttribute("my_sites.jsp-selectedSite");
Group siteGroup = (Group)request.getAttribute("my_sites.jsp-siteGroup");
String siteName = GetterUtil.getString(request.getAttribute("my_sites.jsp-siteGroup"), siteGroup.getDescriptiveName(locale));
boolean showPrivateLabel = (Boolean)request.getAttribute("my_sites.jsp-showPrivateLabel");
boolean showStagingLabel = (Boolean)request.getAttribute("my_sites.jsp-showStagingLabel");

SiteAdministrationPanelCategoryDisplayContext siteAdministrationPanelCategoryDisplayContext = new SiteAdministrationPanelCategoryDisplayContext(liferayPortletRequest, liferayPortletResponse, siteGroup);
%>

<li class="list-group-item selectable-site">
	<aui:a cssClass='<%= "site-link" + (selectedSite ? " selected-site" : StringPool.BLANK) %>' href="<%= siteAdministrationPanelCategoryDisplayContext.getGroupURL(privateLayout) %>">
		<div class="list-group-item-field">
			<c:choose>
				<c:when test="<%= selectedSite %>">
					<div class="sticker sticker-primary">
						<aui:icon image="check" markupView="lexicon" />
					</div>
				</c:when>
				<c:when test="<%= Validator.isNotNull(siteAdministrationPanelCategoryDisplayContext.getLogoURL()) %>">
					<div class="aspect-ratio-bg-cover sticker" style="background-image: url(<%= siteAdministrationPanelCategoryDisplayContext.getLogoURL() %>);">
					</div>
				</c:when>
				<c:otherwise>
					<div class="sticker sticker-default">
						<aui:icon image="sites" markupView="lexicon" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="list-group-item-content">
			<h5><%= HtmlUtil.escape(siteName) %></h5>

			<c:if test="<%= showStagingLabel %>">
				<small><liferay-ui:message key="staging" /></small>
			</c:if>

			<c:if test="<%= showPrivateLabel %>">
				<small><liferay-ui:message key='<%= privateLayout ? "private" : "public" %>' /></small>
			</c:if>
		</div>
	</aui:a>
</li>