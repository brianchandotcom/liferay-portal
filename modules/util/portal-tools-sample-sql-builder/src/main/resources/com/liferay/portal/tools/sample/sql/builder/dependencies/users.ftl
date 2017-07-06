<#assign
	groupIds = dataFactory.getNewUserGroupIds(groupModel.groupId)
	roleIds = [dataFactory.administratorRoleModel.roleId, dataFactory.powerUserRoleModel.roleId, dataFactory.userRoleModel.roleId]

	userModels = dataFactory.newUserModels()
/>

<#list userModels as userModel>
	<#assign
		userGroupModel = dataFactory.newGroupModel(userModel)

		layoutModel = dataFactory.newLayoutModel(userGroupModel.groupId, "home", "", "")
	/>

	<@insertLayout _layoutModel=layoutModel />

	<@insertGroup
		_groupModel=userGroupModel
		_publicPageCount=1
	/>

	<@insertUser
		_groupIds=groupIds
		_roleIds=roleIds
		_userModel=userModel
	/>
</#list>