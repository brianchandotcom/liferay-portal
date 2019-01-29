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
FragmentsEditorDisplayContext fragmentsEditorDisplayContext = (FragmentsEditorDisplayContext)request.getAttribute(ContentLayoutTypeControllerWebKeys.LIFERAY_SHARED_FRAGMENTS_EDITOR_DISPLAY_CONTEXT);
%>

<liferay-editor:resources
	editorName="alloyeditor"
/>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/layout-admin-web/css/fragments_editor/FragmentsEditorEditMode.css") %>" rel="stylesheet">
</liferay-util:html-top>

<soy:component-renderer
	componentId='<%= renderResponse.getNamespace() + "sidebar" %>'
	context="<%= fragmentsEditorDisplayContext.getFragmentsEditorSidebarContext() %>"
	module="layout-admin-web/js/fragments_editor/components/sidebar/FragmentsEditorSidebar.es"
	templateNamespace="com.liferay.layout.admin.web.FragmentsEditorSidebar.render"
	useNamespace="<%= false %>"
/>

<soy:component-renderer
	componentId='<%= renderResponse.getNamespace() + "fragments" %>'
	context="<%= fragmentsEditorDisplayContext.getFragmentEntryLinkListContext() %>"
	module="layout-admin-web/js/fragments_editor/components/fragment_entry_link/FragmentEntryLinkList.es"
	templateNamespace="com.liferay.layout.admin.web.FragmentEntryLinkList.render"
	useNamespace="<%= false %>"
/>

<%
JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<aui:script require="layout-admin-web/js/fragments_editor/components/edit_mode/DisabledAreaMask.es as DisabledAreaMaskModule, layout-admin-web/js/fragments_editor/components/edit_mode/EditModeWrapper.es as EditModeWrapperModule, layout-admin-web/js/fragments_editor/reducers/reducers.es as ReducersModule, layout-admin-web/js/fragments_editor/store/store.es as StoreModule">
	StoreModule.createStore(
		<%= jsonSerializer.serializeDeep(fragmentsEditorDisplayContext.getEditorContext()) %>,
		ReducersModule.reducers,
		[
			'<portlet:namespace />editModeWrapper',
			'<portlet:namespace />fragments',
			'<portlet:namespace />sidebar',
			'<portlet:namespace />toolbar'
		]
	);

	const disabledAreaMask = new DisabledAreaMaskModule.DisabledAreaMask();
	const editModeWrapper = new EditModeWrapperModule.EditModeWrapper();

	Liferay.component('<portlet:namespace />editModeWrapper', editModeWrapper);

	function handleDestroyPortlet () {
		disabledAreaMask.dispose();
		editModeWrapper.dispose();

		Liferay.destroyComponent('<portlet:namespace />editModeWrapper');
		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>