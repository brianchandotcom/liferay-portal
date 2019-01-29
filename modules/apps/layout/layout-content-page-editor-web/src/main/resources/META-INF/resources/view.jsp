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
FragmentsEditorDisplayContext fragmentsEditorDisplayContext = (FragmentsEditorDisplayContext)request.getAttribute(ContentPageEditorWebKeys.LIFERAY_SHARED_FRAGMENTS_EDITOR_DISPLAY_CONTEXT);
%>

<liferay-editor:resources
	editorName="alloyeditor"
/>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/layout-content-page-editor-web/css/main.css") %>" rel="stylesheet">
</liferay-util:html-top>

<soy:component-renderer
	componentId='<%= renderResponse.getNamespace() + "fragmentsEditor" %>'
	context="<%= fragmentsEditorDisplayContext.getEditorContext() %>"
	module="js/FragmentsEditor.es"
	templateNamespace="com.liferay.layout.content.page.editor.web.FragmentsEditor.render"
/>

<%
StringBundler sb = new StringBundler(8);

sb.append(npmResolvedPackageName);
sb.append("/js/components/edit_mode/DisabledAreaMask.es as DisabledAreaMaskModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/components/edit_mode/EditModeWrapper.es as EditModeWrapperModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/reducers/reducers.es as ReducersModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/store/store.es as StoreModule");

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<aui:script require="<%= sb.toString() %>">
	StoreModule.createStore(
		<%= jsonSerializer.serializeDeep(fragmentsEditorDisplayContext.getEditorContext()) %>,
		ReducersModule.reducers,
		[
			'<portlet:namespace />editModeWrapper',
			'<portlet:namespace />fragmentsEditor',
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