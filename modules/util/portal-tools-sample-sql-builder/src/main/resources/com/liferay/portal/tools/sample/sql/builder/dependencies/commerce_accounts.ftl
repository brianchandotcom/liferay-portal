<#assign
	commerceAccountEntryModels = dataFactory.newCommerceAccountEntryModels()

	commerceAccountEntryGroupModels = dataFactory.newCommerceAccountEntryGroupModels(commerceAccountEntryModels)
/>

<#list commerceAccountEntryModels as commerceAccountEntryModel>
	${dataFactory.toInsertSQL(commerceAccountEntryModel)}

	${dataFactory.toInsertSQL(dataFactory.newAccountEntryUserRelModel(sampleUserModel, commerceAccountEntryModel.accountEntryId))}
</#list>

<#list commerceAccountEntryGroupModels as commerceAccountEntryGroupModel>
	<@insertGroup _groupModel=commerceAccountEntryGroupModel />
</#list>