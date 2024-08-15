<#-- Default service account user -->

<#assign
	defaultServiceAccountUserModel = dataFactory.newDefaultServiceAccountUserModel()
/>

<@insertGroup _groupModel = dataFactory.newGroupModel(defaultServiceAccountUserModel) />

<@insertUser
	_groupIds = []
	_roleIds = [dataFactory.userRoleModel.roleId]
	_userModel = defaultServiceAccountUserModel
/>