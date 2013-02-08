<#include "../init.ftl">

<#assign groupLocalService = serviceLocator.findService("com.liferay.portal.service.GroupLocalService")>

<#assign controlPanelGroup = groupLocalService.getGroup(themeDisplay.getCompanyId(), "Control Panel")>

<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.service.LayoutLocalService")>

<#assign controlPanelPlid = layoutLocalService.getDefaultPlid(controlPanelGroup.getGroupId(), true)>

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#assign fileEntryTitle = "">
<#assign fileEntryURL = "">

<#if (fieldRawValue != "")>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileEntry = getFileEntry(fileJSONObject)>

	<#if (fileEntry != "")>
		<#assign fileEntryTitle = fileEntry.getTitle()>
		<#assign fileEntryURL = getFileEntryURL(fileEntry)>
	</#if>
</#if>

<@aui["field-wrapper"] data=data>
	<@aui.input inlineField=true label=escape(label) name="${namespacedFieldName}Title" readonly="readonly" type="text" url=fileEntryURL value=fileEntryTitle>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<@aui["button-row"]>
		<@aui.button id="${namespace}${namespacedFieldName}selectFileEntry" value="select" />

		<@aui.button onClick="window['${namespace}${namespacedFieldName}downloadFileEntry']();" value="download" />

		<@aui.button onClick="window['${namespace}${namespacedFieldName}clearFileEntry']();" value="clear" />
	</@>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />
</@>

<@aui.script>
	window['${namespace}${namespacedFieldName}clearFileEntry'] = function() {
		window['${namespace}${namespacedFieldName}setFileEntry']('', '', '', '', '');
	};

	Liferay.provide(
		window,
		'${namespace}${namespacedFieldName}downloadFileEntry',
		function() {
			var A = AUI();

			var titleNode = A.one('#${namespace}${namespacedFieldName}Title');

			if (titleNode) {
				var url = titleNode.attr('url');

				if (url) {
					location.href = url;
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'${namespace}${namespacedFieldName}setFileEntry',
		function(url, uuid, groupId, title, version) {
			var A = AUI();

			var inputNode = A.one('#${namespace}${namespacedFieldName}');

			if (inputNode) {
				if (uuid) {
					inputNode.val(
						A.JSON.stringify(
							{
								groupId: groupId,
								uuid: uuid,
								version: version
							}
						)
					);
				}
				else {
					inputNode.val('');
				}
			}

			var titleNode = A.one('#${namespace}${namespacedFieldName}Title');

			if (titleNode) {
				titleNode.attr('url', url);
				titleNode.val(title);
			}
		},
		['json']
	);
</@>

<@aui.script use="liferay-portlet-url">
	var selectFileEntryNode = A.one('#${namespace}${namespacedFieldName}selectFileEntry');

	if (selectFileEntryNode) {
		selectFileEntryNode.on(
			'click',
			function(event) {
				var portletURL = Liferay.PortletURL.createRenderURL();

				portletURL.setParameter('groupId', ${scopeGroupId?c});
				portletURL.setParameter('struts_action', '/dynamic_data_mapping/select_document_library');

				portletURL.setPlid(${controlPanelPlid?c});

				portletURL.setPortletId('166');

				portletURL.setWindowState('pop_up');

				Liferay.Util.openWindow(
					{
						id: '${namespace}selectDocumentLibrary',
						uri: portletURL.toString()
					}
				);

				window['${portalUtil.getPortletNamespace("166")}selectDocumentLibrary'] = window['${namespace}${namespacedFieldName}setFileEntry'];
			}
		);
	}
</@>