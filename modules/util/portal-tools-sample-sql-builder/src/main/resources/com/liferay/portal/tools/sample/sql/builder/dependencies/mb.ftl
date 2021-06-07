<#list dataFactory.newMBCategoryModels(groupId) as mbCategoryModel>
	${dataFactory.toInsertSQL(mbCategoryModel)}
	${dataFactory.toInsertSQL(dataFactory.newMBMailingListModel(mbCategoryModel, sampleUserModel))}

	${csvFileWriter.write("mbCategory", virtualHostModel.hostname + "," + mbCategoryModel.categoryId + "," + mbCategoryModel.name + "\n")}

	<#list dataFactory.newMBThreadModels(mbCategoryModel) as mbThreadModel>
		${dataFactory.toInsertSQL(mbThreadModel)}

		${dataFactory.toInsertSQL(dataFactory.newSubscriptionModel(mbThreadModel))}

		<@insertAssetEntry _entry=mbThreadModel />

		${dataFactory.toInsertSQL(dataFactory.newMBThreadFlagModel(mbThreadModel))}

		<#list dataFactory.newMBMessageModels(mbThreadModel) as mbMessageModel>
			<@insertMBMessage _mbMessageModel=mbMessageModel />

			${dataFactory.toInsertSQL(dataFactory.newSocialActivityModel(mbMessageModel))}
		</#list>

		${csvFileWriter.write("mbThread", virtualHostModel.hostname + "," + mbCategoryModel.categoryId + "," + mbThreadModel.threadId + "," + mbThreadModel.rootMessageId + "\n")}
	</#list>
</#list>