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

<%@ include file="/admin/common/init.jsp" %>

<%
long kbFolderId = ParamUtil.getLong(request, "kbFolderId");

KBFolder kbFolder = null;

if (kbFolderId != 0) {
	kbFolder = KBFolderServiceUtil.getKBFolder(kbFolderId);
}

long defaultParentResourcePrimKey = KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;

if (kbFolder != null) {
	defaultParentResourcePrimKey = kbFolder.getParentKBFolderId();
}

long parentResourcePrimKey = ParamUtil.getLong(request, "parentResourcePrimKey", defaultParentResourcePrimKey);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((kbFolder == null) ? LanguageUtil.get(resourceBundle, "new-folder") : kbFolder.getName());
%>

<liferay-portlet:actionURL name="updateKBFolder" var="updateKBFolderURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= updateKBFolderURL %>" method="post" name="fm">
		<aui:input name="mvcPath" type="hidden" value="/admin/common/edit_folder.jsp" />
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (kbFolder == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="kbFolderId" type="hidden" value="<%= String.valueOf(kbFolderId) %>" />
		<aui:input name="parentResourceClassNameId" type="hidden" value="<%= PortalUtil.getClassNameId(KBFolderConstants.getClassName()) %>" />
		<aui:input name="parentResourcePrimKey" type="hidden" value="<%= parentResourcePrimKey %>" />

		<liferay-ui:error exception="<%= DuplicateKBFolderNameException.class %>" message="please-enter-a-unique-folder-name" />
		<liferay-ui:error exception="<%= InvalidKBFolderNameException.class %>" message="please-enter-a-valid-folder-name" />

		<aui:model-context bean="<%= kbFolder %>" model="<%= KBFolder.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="name" />

				<aui:input name="description" />
			</aui:fieldset>

			<liferay-expando:custom-attributes-available
				className="<%= KBFolder.class.getName() %>"
			>
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
					<liferay-expando:custom-attribute-list
						className="<%= KBFolder.class.getName() %>"
						classPK="<%= (kbFolder != null) ? kbFolder.getKbFolderId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</liferay-expando:custom-attributes-available>

			<c:if test="<%= kbFolder == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<aui:field-wrapper>
						<liferay-ui:input-permissions
							modelName="<%= KBFolder.class.getName() %>"
						/>
					</aui:field-wrapper>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" value="save" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>