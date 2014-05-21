<#include "../init.ftl">

<#if !(fields?? && fields.get(fieldName)??) && (fieldRawValue == "")>
	<#assign fieldRawValue = predefinedValue>
</#if>

<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

<#assign fileEntryTitle = "">

<#if (fieldRawValue != "")>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileEntry = getFileEntry(fileJSONObject)>

	<#if (fileEntry != "")>
		<#assign fileEntryTitle = fileEntry.getTitle()>
	</#if>
</#if>

<@aui["field-wrapper"] data=data>
	<@aui.input helpMessage=escape(fieldStructure.tip) inlineField=true label=escape(label) name="${namespacedFieldName}Title" readonly="readonly" type="text" value=fileEntryTitle>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<@aui["button-row"]>
		<@aui.button id=namespacedFieldName value="select" />

		<@aui.button onClick="window['${portletNamespace}${namespacedFieldName}clearFileEntry']();" value="clear" />
	</@>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	${fieldStructure.children}
</@>

<@aui.script>
	window['${portletNamespace}${namespacedFieldName}clearFileEntry'] = function() {
		window['${portletNamespace}${namespacedFieldName}setFileEntry']('', '', '', '', '');
	};

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}setFileEntry',
		function(url, uuid, groupId, title, version) {
			var A = AUI();

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
	var namespacedField = A.one('#${namespacedFieldName}');

	if (namespacedField) {
		namespacedField.on(
			'click',
			function(event) {
				var portletURL = Liferay.PortletURL.createURL('${themeDisplay.getURLControlPanel()}');

				portletURL.setDoAsGroupId(${scopeGroupId?c});
				portletURL.setParameter('eventName', '${portletNamespace}selectDocumentLibrary');
				portletURL.setParameter('groupId', ${scopeGroupId?c});
				portletURL.setParameter('refererPortletName', '${themeDisplay.getPortletDisplay().getId()}');
				portletURL.setParameter('struts_action', '/document_selector/view');
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
						uri: portletURL.toString()
					},
					function(event) {
						window['${portletNamespace}${namespacedFieldName}setFileEntry'](event.url, event.uuid, event.groupid, event.title, event.version);
					}
				);
			}
		);
	}
</@>