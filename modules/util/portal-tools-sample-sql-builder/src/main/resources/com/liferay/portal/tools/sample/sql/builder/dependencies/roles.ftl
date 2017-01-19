<#list dataFactory.roleModels as roleModel>
	${dataFactory.toInsertSQL(roleModel)}

	<@insertResourcePermissions
		_entry = roleModel
	/>
</#list>