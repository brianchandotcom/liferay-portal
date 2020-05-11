<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingResultContentDisplayBuilder" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingResultContentDisplayContext" %>

<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
RankingResultContentDisplayBuilder rankingResultContentDisplayBuilder = new RankingResultContentDisplayBuilder();

rankingResultContentDisplayBuilder.setAssetEntryId(ParamUtil.getLong(request, "assetEntryId"));
rankingResultContentDisplayBuilder.setEntryClassName(ParamUtil.getString(request, "entryClassName"));
rankingResultContentDisplayBuilder.setEntryClassPK(ParamUtil.getLong(request, "entryClassPK"));
rankingResultContentDisplayBuilder.setLocale(locale);
rankingResultContentDisplayBuilder.setPermissionChecker(permissionChecker);
rankingResultContentDisplayBuilder.setPortal(PortalUtil.getPortal());
rankingResultContentDisplayBuilder.setRenderRequest(renderRequest);
rankingResultContentDisplayBuilder.setRenderResponse(renderResponse);

RankingResultContentDisplayContext rankingResultContentDisplayContext = rankingResultContentDisplayBuilder.build();
%>

<clay:container
	className="container-no-gutters-sm-down container-view"
>
	<c:choose>
		<c:when test="<%= rankingResultContentDisplayContext.isVisible() %>">
			<div class="result-rankings-view-content-container sheet sheet-lg">
				<div class="autofit-row">
					<div class="autofit-col autofit-col-expand">
						<div class="sheet-title">
							<%= rankingResultContentDisplayContext.getHeaderTitle() %>
						</div>
					</div>

					<div class="autofit-col visible-interaction">
						<c:if test="<%= rankingResultContentDisplayContext.hasEditPermission() %>">
							<div class="asset-actions lfr-meta-actions">

								<%
								Map<String, Object> data = HashMapBuilder.<String, Object>put(
									"destroyOnHide", true
								).put(
									"id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset"
								).put(
									"title", LanguageUtil.format(request, "edit-x", HtmlUtil.escape(rankingResultContentDisplayContext.getIconEditTarget()), false)
								).build();
								%>

								<liferay-ui:icon
									cssClass="visible-interaction"
									data="<%= data %>"
									icon="pencil"
									label="<%= false %>"
									markupView="lexicon"
									message='<%= LanguageUtil.format(request, "edit-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(rankingResultContentDisplayContext.getIconEditTarget())}, false) %>'
									method="get"
									url="<%= rankingResultContentDisplayContext.getIconURLString() %>"
									useDialog="<%= true %>"
								/>
							</div>
						</c:if>
					</div>
				</div>

				<liferay-asset:asset-display
					assetEntry="<%= rankingResultContentDisplayContext.getAssetEntry() %>"
					assetRenderer="<%= rankingResultContentDisplayContext.getAssetRenderer() %>"
					assetRendererFactory="<%= rankingResultContentDisplayContext.getAssetRendererFactory() %>"
				/>
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-danger">
				<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />
			</div>
		</c:otherwise>
	</c:choose>
</clay:container>