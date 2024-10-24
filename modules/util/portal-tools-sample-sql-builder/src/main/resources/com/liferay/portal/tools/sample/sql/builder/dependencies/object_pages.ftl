<#assign
	portletId = "com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_" + objectDefinitionModel.getObjectDefinitionId()

	layoutModel = dataFactory.newLayoutModel(groupId, objectDefinitionModel.getName(), "", portletId)
/>

<@insertLayout _layoutModel = layoutModel />

<#assign portletPreferencesModel = dataFactory.newPortletPreferencesModel(layoutModel.plid, portletId) />

${dataFactory.toInsertSQL(portletPreferencesModel)}

${csvFileWriter.write("object", virtualHostModel.hostname + "," + groupModel.friendlyURL + "," + groupId + "," + objectDefinitionModel.getName() + "," + objectDefinitionModel.getObjectDefinitionId() + "," + portletId + "\n")}