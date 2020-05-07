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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(assetListDisplayContext.getAssetListEntryTitle());
%>

<clay:container
	className="container-view"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">

						<%
						List<SegmentsEntry> availableSegmentsEntries = editAssetListDisplayContext.getAvailableSegmentsEntries();

						List<AssetListEntrySegmentsEntryRel> assetEntryListSegmentsEntryRels = editAssetListDisplayContext.getAssetListEntrySegmentsEntryRels();
						%>

						<c:choose>
							<c:when test="<%= assetEntryListSegmentsEntryRels.size() > 1 %>">
								<div class="autofit-row autofit-row-center">
									<div class="autofit-col autofit-col-expand">
										<strong class="text-uppercase">
											<liferay-ui:message key="personalized-variations" />
										</strong>
									</div>

									<div class="autofit-col autofit-col-end">
										<ul class="navbar-nav">
											<li>
												<c:if test="<%= availableSegmentsEntries.size() > 0 %>">
													<liferay-ui:icon
														icon="plus"
														iconCssClass="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
														id="addAssetListEntryVariationIcon"
														markupView="lexicon"
														url='<%= "javascript:" + renderResponse.getNamespace() + "openSelectSegmentsEntryDialog();" %>'
													/>
												</c:if>
											</li>
										</ul>
									</div>
								</div>

								<ul class="nav nav-stacked">

									<%
									for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel : assetEntryListSegmentsEntryRels) {
									%>

										<li class="nav-item">

											<%
											PortletURL editAssetListEntryURL = renderResponse.createRenderURL();

											editAssetListEntryURL.setParameter("mvcPath", "/edit_asset_list_entry.jsp");
											editAssetListEntryURL.setParameter("assetListEntryId", String.valueOf(assetListEntrySegmentsEntryRel.getAssetListEntryId()));
											editAssetListEntryURL.setParameter("segmentsEntryId", String.valueOf(assetListEntrySegmentsEntryRel.getSegmentsEntryId()));
											%>

											<a class="nav-link text-truncate <%= (editAssetListDisplayContext.getSegmentsEntryId() == assetListEntrySegmentsEntryRel.getSegmentsEntryId()) ? "active" : StringPool.BLANK %>" href="<%= editAssetListEntryURL.toString() %>">
												<%= HtmlUtil.escape(editAssetListDisplayContext.getSegmentsEntryName(assetListEntrySegmentsEntryRel.getSegmentsEntryId(), locale)) %>
											</a>
										</li>

									<%
									}
									%>

								</ul>
							</c:when>
							<c:otherwise>
								<p class="text-uppercase">
									<strong><liferay-ui:message key="personalized-variations" /></strong>
								</p>

								<liferay-frontend:empty-result-message
									actionDropdownItems="<%= (availableSegmentsEntries.size() > 0) ? editAssetListDisplayContext.getAssetListEntryVariationActionDropdownItems() : null %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									componentId='<%= renderResponse.getNamespace() + "emptyResultMessageComponent" %>'
									description='<%= LanguageUtil.get(request, "no-personalized-variations-were-found") %>'
									elementType='<%= LanguageUtil.get(request, "personalized-variations") %>'
								/>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>

			<%
			AssetListEntry assetListEntry = assetListDisplayContext.getAssetListEntry();
			%>

			<c:choose>
				<c:when test="<%= assetListEntry.getType() == AssetListEntryTypeConstants.TYPE_DYNAMIC %>">
					<liferay-util:include page="/edit_asset_list_entry_dynamic.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/edit_asset_list_entry_manual.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</clay:col>
	</clay:row>
</clay:container>

<script>
	<portlet:actionURL name="/asset_list/add_asset_list_entry_variation" var="addAssetListEntryVariationURL">
		<portlet:param name="assetListEntryId" value="<%= String.valueOf(editAssetListDisplayContext.getAssetListEntryId()) %>" />
		<portlet:param name="type" value="<%= String.valueOf(editAssetListDisplayContext.getAssetListEntryType()) %>" />
	</portlet:actionURL>

	function <portlet:namespace />openSelectSegmentsEntryDialog() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true,
				},
				id: '<portlet:namespace />selectEntity',
				title:
					'<liferay-ui:message arguments="personalized-variation" key="new-x" />',
				uri:
					'<%= editAssetListDisplayContext.getSelectSegmentsEntryURL() %>',
			},
			function (event) {
				Liferay.Util.postForm(document.<portlet:namespace />fm, {
					data: {
						segmentsEntryId: event.entityid,
					},
					url: '<%= addAssetListEntryVariationURL %>',
				});
			}
		);
	}
</script>