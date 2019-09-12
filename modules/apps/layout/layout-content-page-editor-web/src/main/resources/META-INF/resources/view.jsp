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
String portletNamespace = PortalUtil.getPortletNamespace(ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

ContentPageEditorDisplayContext contentPageEditorDisplayContext = (ContentPageEditorDisplayContext)request.getAttribute(ContentPageEditorWebKeys.LIFERAY_SHARED_CONTENT_PAGE_EDITOR_DISPLAY_CONTEXT);
%>

<liferay-editor:resources
	editorName="alloyeditor"
/>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/layout-content-page-editor-web/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<soy:component-renderer
	componentId='<%= portletNamespace + "fragmentsEditor" %>'
	context="<%= contentPageEditorDisplayContext.getEditorSoyContext() %>"
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
sb.append("/js/store/store.es as StoreModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/utils/FragmentsEditorFetchUtils.es as FragmentsEditorFetchUtilsModule");

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<aui:script require="<%= sb.toString() %>">
	var store = StoreModule.createStore(
		<%= jsonSerializer.serializeDeep(contentPageEditorDisplayContext.getEditorSoyContext()) %>,
		ReducersModule.reducer,
		[
			'<%= portletNamespace + "disabledAreaMaskWrapper" %>',
			'<%= portletNamespace + "editModeWrapper" %>',
			'<%= portletNamespace + "fragmentsEditor" %>',
			'<%= portletNamespace + "sidebar" %>',
			'<%= portletNamespace + "toolbar" %>'
		]
	);

	var editModeComponents = {
		<%= portletNamespace + "disabledAreaMaskWrapper" %>:
			DisabledAreaMaskModule.default,
		<%= portletNamespace + "editModeWrapper" %>: EditModeWrapperModule.default
	};

	Object.keys(editModeComponents).forEach(function(key) {
		Liferay.component(
			key,
			new editModeComponents[key]({
				store: store
			})
		);
	});

	FragmentsEditorFetchUtilsModule.setStore(store);

	function handleDestroyPortlet() {
		Object.keys(editModeComponents).forEach(function(key) {
			Liferay.destroyComponent(key);
		});

		FragmentsEditorFetchUtilsModule.setStore(null);

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>