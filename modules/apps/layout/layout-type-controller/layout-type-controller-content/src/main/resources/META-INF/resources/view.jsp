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
FragmentsEditorDisplayContext fragmentsEditorDisplayContext = new FragmentsEditorDisplayContext(request, renderResponse);
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
/>

<soy:component-renderer
	componentId='<%= renderResponse.getNamespace() + "fragments" %>'
	context="<%= fragmentsEditorDisplayContext.getFragmentEntryLinkListContext() %>"
	module="layout-admin-web/js/fragments_editor/components/fragment_entry_link/FragmentEntryLinkList.es"
	templateNamespace="com.liferay.layout.admin.web.FragmentEntryLinkList.render"
/>

<%
JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<aui:script require="layout-admin-web/js/fragments_editor/components/edit_mode/DisabledAreaMask.es as DisabledAreaMaskModule, layout-admin-web/js/fragments_editor/components/edit_mode/EditModeWrapper.es as EditModeWrapperModule, layout-admin-web/js/fragments_editor/reducers/changes.es as ChangesReducerModule, layout-admin-web/js/fragments_editor/reducers/fragments.es as FragmentsReducerModule, layout-admin-web/js/fragments_editor/reducers/placeholders.es as PlaceholdersReducerModule, layout-admin-web/js/fragments_editor/reducers/translations.es as TranslationsReducerModule, layout-admin-web/js/fragments_editor/reducers/sidebar.es as SidebarReducerModule, layout-admin-web/js/fragments_editor/store/store.es as StoreModule, layout-admin-web/js/fragments_editor/reducers/dialogs.es as DialogsReducerModule, layout-admin-web/js/fragments_editor/reducers/sections.es as SectionsReducerModule">
	StoreModule.createStore(
		<%= jsonSerializer.serializeDeep(fragmentsEditorDisplayContext.getEditorContext()) %>,
		[
			ChangesReducerModule.saveChangesReducer,
			DialogsReducerModule.hideMappingDialogReducer,
			DialogsReducerModule.hideMappingTypeDialogReducer,
			DialogsReducerModule.openAssetTypeDialogReducer,
			DialogsReducerModule.openMappingFieldsDialogReducer,
			DialogsReducerModule.selectMappeableTypeReducer,
			FragmentsReducerModule.addFragmentEntryLinkReducer,
			FragmentsReducerModule.moveFragmentEntryLinkReducer,
			FragmentsReducerModule.removeFragmentEntryLinkReducer,
			FragmentsReducerModule.updateEditableValueReducer,
			PlaceholdersReducerModule.updateActiveItemReducer,
			PlaceholdersReducerModule.updateDropTargetReducer,
			PlaceholdersReducerModule.updateHighlightMappingReducer,
			PlaceholdersReducerModule.updateHoveredItemReducer,
			SidebarReducerModule.hideFragmentsEditorSidebarReducer,
			SidebarReducerModule.toggleFragmentsEditorSidebarReducer,
			SectionsReducerModule.addSectionReducer,
			SectionsReducerModule.moveSectionReducer,
			SectionsReducerModule.removeSectionReducer,
			TranslationsReducerModule.languageIdReducer,
			TranslationsReducerModule.translationStatusReducer
		],
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