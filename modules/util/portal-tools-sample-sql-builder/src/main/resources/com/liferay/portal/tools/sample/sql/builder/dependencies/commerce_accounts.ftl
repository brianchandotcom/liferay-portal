<#assign commerceAccountEntryModels = dataFactory.newCommerceAccountEntryModels() />

<#list commerceAccountEntryModels as commerceAccountEntryModel>
	${dataFactory.toInsertSQL(commerceAccountEntryModel)}

	${dataFactory.toInsertSQL(dataFactory.newAccountEntryUserRelModel(sampleUserModel, commerceAccountEntryModel.accountEntryId))}

	<@insertGroup _groupModel=dataFactory.newCommerceAccountEntryGroupModel(commerceAccountEntryModel) />
</#list>