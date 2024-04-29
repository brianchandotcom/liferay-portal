<#-- Sample user -->

<#assign
	sampleUserModel = dataFactory.newSampleUserModel()

	userGroupModel = dataFactory.newGroupModel(sampleUserModel)
/>

<#list dataFactory.newLayoutModels(userGroupModel.groupId, "home", "", "") as layoutModel>
	<@insertLayout _layoutModel = layoutModel />
</#list>

<@insertGroup _groupModel = userGroupModel />

<#assign
	groupIds = dataFactory.getSequence(dataFactory.maxGroupCount)
	roleIds = [dataFactory.administratorRoleModel.roleId, dataFactory.powerUserRoleModel.roleId, dataFactory.userRoleModel.roleId]
/>

<@insertUser
	_groupIds = groupIds
	_roleIds = roleIds
	_userModel = sampleUserModel
/>