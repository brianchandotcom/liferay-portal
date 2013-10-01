<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_tag_admin/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_asset_tad_admin_edit_tag") + StringPool.UNDERLINE;

AssetTag tag = (AssetTag)request.getAttribute(WebKeys.ASSET_TAG);

long tagId = BeanParamUtil.getLong(tag, request, "tagId");

int[] tagPropertiesIndexes = null;

List<AssetTagProperty> tagProperties = Collections.emptyList();

String tagPropertiesIndexesParam = ParamUtil.getString(request, "tagPropertiesIndexes");

if (Validator.isNotNull(tagPropertiesIndexesParam)) {
	tagProperties = new ArrayList<AssetTagProperty>();

	tagPropertiesIndexes = StringUtil.split(tagPropertiesIndexesParam, 0);

	for (int tagPropertiesIndex : tagPropertiesIndexes) {
		tagProperties.add(new AssetTagPropertyImpl());
	}
}
else {
	if (tag != null) {
		tagProperties = AssetTagPropertyServiceUtil.getTagProperties(tag.getTagId());

		tagPropertiesIndexes = new int[tagProperties.size()];

		for (int i = 0; i < tagProperties.size(); i++) {
			tagPropertiesIndexes[i] = i;
		}
	}

	if (tagProperties.isEmpty()) {
		tagProperties = new ArrayList<AssetTagProperty>();

		tagProperties.add(new AssetTagPropertyImpl());

		tagPropertiesIndexes = new int[] {0};
	}

	if (tagPropertiesIndexes == null) {
		tagPropertiesIndexes = new int[0];
	}
}
%>

<portlet:actionURL var="editTagURL">
	<portlet:param name="struts_action" value="/asset_tag_admin/edit_tag" />
</portlet:actionURL>

<aui:form action="<%= editTagURL %>" cssClass="update-tag-form" method="get" name='<%= randomNamespace + "fm" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= tag == null ? Constants.ADD : Constants.UPDATE %>" />

	<aui:model-context bean="<%= tag %>" model="<%= AssetTag.class %>" />

	<aui:fieldset>
		<div>
			<div class="add-tag-layer asset-tag-layer">
				<aui:input name="tagId" type="hidden" value="<%= tagId %>" />

				<aui:input autoFocus="<%= true %>" cssClass="tag-name" name="name" />

				<c:if test="<%= PropsValues.ASSET_TAG_PERMISSIONS_ENABLED || PropsValues.ASSET_TAG_PROPERTIES_ENABLED %>">
					<liferay-ui:panel-container extended="<%= false %>" id="assetTagPanelContainer" persistState="<%= true %>">
						<c:if test="<%= PropsValues.ASSET_TAG_PERMISSIONS_ENABLED && (tag == null) %>">
							<liferay-ui:panel collapsible="<%= true %>" cssClass="tag-permissions-actions" defaultState="open" extended="<%= true %>" id="assetTagPermissionsPanel" persistState="<%= true %>" title="permissions">
								<liferay-ui:input-permissions
									modelName="<%= AssetTag.class.getName() %>"
								/>
							</liferay-ui:panel>
						</c:if>

						<c:if test="<%= PropsValues.ASSET_TAG_PROPERTIES_ENABLED %>">
							<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= true %>" helpMessage="properties-are-a-way-to-add-more-detailed-information-to-a-specific-tag" id="assetTagPropertiesPanel" persistState="<%= true %>" title="properties">
								<aui:fieldset cssClass="tag-tagProperties" id="tagProperties">

									<%
									for (int i = 0; i < tagPropertiesIndexes.length; i++) {
										int tagPropertiesIndex = tagPropertiesIndexes[i];

										AssetTagProperty tagProperty = tagProperties.get(i);
									%>

										<aui:model-context bean="<%= tagProperty %>" model="<%= AssetTagProperty.class %>" />

										<div class="lfr-form-row lfr-form-row-inline">
											<div class="row-fields">
												<aui:input fieldParam='<%= "key" + tagPropertiesIndex %>' id='<%= "key" + tagPropertiesIndex %>' name="key" />

												<aui:input fieldParam='<%= "value" + tagPropertiesIndex %>' id='<%= "value" + tagPropertiesIndex %>' name="value" />
											</div>
										</div>

									<%
									}
									%>

									<aui:input name="tagPropertiesIndexes" type="hidden" value="<%= StringUtil.merge(tagPropertiesIndexes) %>" />
								</aui:fieldset>
							</liferay-ui:panel>
						</c:if>
					</liferay-ui:panel-container>
				</c:if>

				<aui:button-row>
					<aui:button type="submit" />

					<c:if test="<%= tag != null %>">
						<c:if test="<%= AssetTagPermission.contains(permissionChecker, tag, ActionKeys.DELETE) %>">
							<aui:button id="deleteTagButton" value="delete" />
						</c:if>

						<c:if test="<%= PropsValues.ASSET_TAG_PERMISSIONS_ENABLED && AssetTagPermission.contains(permissionChecker, tag, ActionKeys.PERMISSIONS) %>">
							<liferay-security:permissionsURL
								modelResource="<%= AssetTag.class.getName() %>"
								modelResourceDescription="<%= tag.getName() %>"
								resourcePrimKey="<%= String.valueOf(tag.getTagId()) %>"
								var="permissionsURL"
								windowState="<%= LiferayWindowState.POP_UP.toString() %>"
							/>

							<aui:button data-url="<%= permissionsURL %>" id="updateTagPermissions" value="permissions" />
						</c:if>
					</c:if>

					<aui:button cssClass="close-panel" type="cancel" value="close" />
				</aui:button-row>
			</div>
		</div>
	</aui:fieldset>
</aui:form>

<c:if test="<%= PropsValues.ASSET_TAG_PROPERTIES_ENABLED %>">
	<aui:script use="liferay-auto-fields">
		var autoFields = new Liferay.AutoFields(
			{
				contentBox: 'fieldset#<portlet:namespace />tagProperties',
				fieldIndexes: '<portlet:namespace />tagPropertiesIndexes',
				namespace: '<portlet:namespace />'
			}
		).render();

		var tagPropertiesTrigger = A.one('fieldset#<portlet:namespace />tagProperties');

		if (tagPropertiesTrigger) {
			tagPropertiesTrigger.setData('autoFieldsInstance', autoFields);
		}
	</aui:script>
</c:if>