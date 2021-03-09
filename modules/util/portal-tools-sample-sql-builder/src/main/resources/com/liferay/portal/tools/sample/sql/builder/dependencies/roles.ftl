<#list dataFactory.newRoleModels() as roleModel>
	${dataFactory.toInsertSQL(roleModel)}
</#list>