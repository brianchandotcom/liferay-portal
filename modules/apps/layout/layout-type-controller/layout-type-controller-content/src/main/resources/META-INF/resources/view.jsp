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
	componentId='<%= renderResponse.getNamespace() + "fragments" %>'
	context="<%= fragmentsEditorDisplayContext.getFragmentEntryLinkListContext() %>"
	module="layout-admin-web/js/fragments_editor/components/fragment_entry_link/FragmentEntryLinkList.es"
	templateNamespace="com.liferay.layout.admin.web.FragmentEntryLinkList.render"
/>

<%
JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<aui:script require="layout-admin-web/js/fragments_editor/components/edit_mode/DisabledAreaPopover.es as DisabledAreaPopoverModule, layout-admin-web/js/fragments_editor/reducers/changes.es as ChangesReducerModule, layout-admin-web/js/fragments_editor/reducers/fragments.es as FragmentsReducerModule, layout-admin-web/js/fragments_editor/reducers/placeholders.es as PlaceholdersReducerModule, layout-admin-web/js/fragments_editor/reducers/translations.es as TranslationsReducerModule, layout-admin-web/js/fragments_editor/reducers/sidebar.es as SidebarReducerModule, layout-admin-web/js/fragments_editor/store/store.es as StoreModule, layout-admin-web/js/fragments_editor/reducers/dialogs.es as DialogsReducerModule">
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
			PlaceholdersReducerModule.updateDragTargetReducer,
			PlaceholdersReducerModule.updateHighlightMappingReducer,
			SidebarReducerModule.hideFragmentsEditorSidebarReducer,
			SidebarReducerModule.toggleFragmentsEditorSidebarReducer,
			TranslationsReducerModule.languageIdReducer,
			TranslationsReducerModule.translationStatusReducer
		],
		[
			'<portlet:namespace />fragments',
			'<portlet:namespace />sidebar',
			'<portlet:namespace />toolbar'
		]
	);

	const disabledAreaPopover = new DisabledAreaPopoverModule.DisabledAreaPopover(
		{
			selector: '#banner, #footer'
		}
	);

	function handleDestroyPortlet () {
		disabledAreaPopover.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>