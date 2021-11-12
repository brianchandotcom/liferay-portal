<#assign mbCategoryModels = dataFactory.newMBCategoryModels(groupId) />

<#list mbCategoryModels as mbCategoryModel>
	${dataFactory.toInsertSQL(mbCategoryModel)}
	${dataFactory.toInsertSQL(dataFactory.newMBMailingListModel(mbCategoryModel, sampleUserModel))}

	${csvFileWriter.write("mbCategory", virtualHostModel.hostname + "," + mbCategoryModel.categoryId + "," + mbCategoryModel.name + "\n")}

	<#assign mbThreadModels = dataFactory.newMBThreadModels(mbCategoryModel) />

	<#list mbThreadModels as mbThreadModel>
		${dataFactory.toInsertSQL(mbThreadModel)}

		${dataFactory.toInsertSQL(dataFactory.newSubscriptionModel(mbThreadModel))}

		<@insertAssetEntry _entry=mbThreadModel />

		${dataFactory.toInsertSQL(dataFactory.newMBThreadFlagModel(mbThreadModel))}

		<#assign mbMessageModels = dataFactory.newMBMessageModels(mbThreadModel) />

		<#list mbMessageModels as mbMessageModel>
			<@insertMBMessage _mbMessageModel=mbMessageModel />

			${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(mbMessageModel))}
		</#list>

		${csvFileWriter.write("mbThread", virtualHostModel.hostname + "," + mbCategoryModel.categoryId + "," + mbThreadModel.threadId + "," + mbThreadModel.rootMessageId + "\n")}
	</#list>
</#list>