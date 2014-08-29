<@aui.script>
	var A = AUI();

	window['${portletNamespace}${namespacedFieldName}clearFileEntry'] = function(title) {
		title = title || '';

		A.one("#${portletNamespace}${namespacedFieldName}ClearFile").setStyle('display', 'none');

		window['${portletNamespace}${namespacedFieldName}setFileEntry']('', '', '', title, '');
	};

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}setFileEntry',
		function(url, uuid, groupId, title, version) {

			var inputNode = A.one('#${portletNamespace}${namespacedFieldName}');

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

			var titleNode = A.one('#${portletNamespace}${namespacedFieldName}Title');

			if (titleNode) {
				titleNode.val(title);
			}
		},
		['json']
	);
</@>

<@aui.script use="liferay-portlet-url">
	var A = AUI();
	var chooseFileButton = A.one('#${portletNamespace}${namespacedFieldName}ChooseFile');

	if (chooseFileButton) {
		chooseFileButton.on(
			'click',
			function(event) {
				var portletURL = Liferay.PortletURL.createURL('${themeDisplay.getURLControlPanel()}');

				portletURL.setDoAsGroupId(${scopeGroupId?c});
				portletURL.setParameter('eventName', '${portletNamespace}selectDocumentLibrary');
				portletURL.setParameter('folderId', '${folderId}');
				portletURL.setParameter('groupId', ${scopeGroupId?c});
				portletURL.setParameter('refererPortletName', '${themeDisplay.getPortletDisplay().getId()}');
				portletURL.setParameter('struts_action', '/document_selector/view');
				portletURL.setParameter('tabs1Names', 'documents');
				portletURL.setPortletId('200');
				portletURL.setWindowState('pop_up');

				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							destroyOnHide: true,
							modal: true
						},
						eventName: '${portletNamespace}selectDocumentLibrary',
						id: '${portletNamespace}selectDocumentLibrary',
						title: '${languageUtil.get(locale, "select-document")}',
						uri: portletURL.toString()
					},
					function(event) {
						window['${portletNamespace}${namespacedFieldName}setFileEntry'](event.url, event.uuid, event.groupid, event.title, event.version);
						A.one("#${portletNamespace}${namespacedFieldName}ClearFile").setStyle('display', 'inline-block');
					}
				);
			}
		);
	}
</@>