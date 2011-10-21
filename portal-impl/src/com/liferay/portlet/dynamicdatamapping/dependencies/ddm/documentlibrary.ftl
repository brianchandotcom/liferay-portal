<#include "../init.ftl">

<#assign groupLocalService = serviceLocator.findService("com.liferay.portal.service.GroupLocalService")>

<#assign controlPanelGroup = groupLocalService.getGroup(themeDisplay.getCompanyId(), "Control Panel")>

<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.service.LayoutLocalService")>

<#assign controlPanelPlid = layoutLocalService.getDefaultPlid(controlPanelGroup.getGroupId(), true)>

<@aui["field-wrapper"] label=label>
	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<label id="${namespacedFieldName}Label">
		<#assign fileEntryTitle = "">
		<#assign fileEntryURL = "">

		<#if (fields??) && (fieldValue != "")>
			<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

			<#assign fileEntry = getFileEntry(fileJSONObject)>

			<#assign fileEntryTitle = fileEntry.getTitle()>
			<#assign fileEntryURL = getFileEntryURL(fileEntry)>
		</#if>

		<a href="${fileEntryURL}">${fileEntryTitle}</a>
	</label>

	<@aui.button id=namespacedFieldName value="select" />
</@>

<@aui.script>
	Liferay.provide(
		window,
		'${portalUtil.getPortletNamespace("15")}selectDocumentLibrary',
		function(url, uuid, title, version) {
			var A = AUI();

			var inputNode = A.one('#${portletNamespace}${namespacedFieldName}');

			if (inputNode) {
				inputNode.val(
					A.JSON.stringify(
						{
							groupId: ${scopeGroupId?c},
							uuid: uuid,
							version: version
						}
					)
				);
			}

			var labelNode = A.one('#${namespacedFieldName}Label');

			if (labelNode) {
				labelNode.setContent('<a href="' + url + '">' + title + '</a>');
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
				var portletURL = Liferay.PortletURL.createRenderURL();

				portletURL.setParameter('groupId', ${scopeGroupId?c});
				portletURL.setParameter('struts_action', '/journal/select_document_library');

				portletURL.setPlid(${controlPanelPlid?c});

				portletURL.setPortletId('15');

				portletURL.setWindowState('pop_up');

				Liferay.Util.openWindow(
					{
						uri: portletURL.toString()
					}
				);
			}
		);
	}
</@>