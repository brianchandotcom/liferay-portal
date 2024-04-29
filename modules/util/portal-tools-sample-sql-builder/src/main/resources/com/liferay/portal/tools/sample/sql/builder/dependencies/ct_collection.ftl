<#assign ctSchemaVersionModel = dataFactory.newCTSchemaVersionModel() />

${dataFactory.toInsertSQL(ctSchemaVersionModel)}

<#--Create a Publications specific group-->

<#assign publicationGroupModel = dataFactory.newPublicationGroupModel() />
${dataFactory.toInsertSQL(publicationGroupModel)}

<#--CTCollection data creation-->

<#list dataFactory.newCTCollectionModels(ctSchemaVersionModel) as ctCollectionModel>
	${dataFactory.toInsertSQL(ctCollectionModel)}

	<#list dataFactory.newResourcePermissionModels("com.liferay.change.tracking.model.CTCollection", ctCollectionModel.ctCollectionId?c, defaultAdminUserModel.userId) as resourcePermissionModel>
		${dataFactory.toInsertSQL(resourcePermissionModel)}
	</#list>

	<#assign
		ctCollectionId = ctCollectionModel.ctCollectionId
		ctCollectionIdString = ctCollectionModel.ctCollectionId?c
		publicationGroupId = publicationGroupModel.groupId
	/>

	<#include "ct_ddm.ftl">

	<#include "ct_journal_article.ftl">

	<#include "ct_layout.ftl">
</#list>