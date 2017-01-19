<#assign mbCategoryModels = dataFactory.newMBCategoryModels(groupId) />

<#list mbCategoryModels as mbCategoryModel>
	${dataFactory.toInsertSQL(mbCategoryModel)}

	<@insertResourcePermissions
		_entry = mbCategoryModel
	/>

	${dataFactory.toInsertSQL(dataFactory.newMBMailingListModel(mbCategoryModel))}

	<#assign mbThreadModels = dataFactory.newMBThreadModels(mbCategoryModel) />

	<#list mbThreadModels as mbThreadModel>
		${dataFactory.toInsertSQL(mbThreadModel)}

		<@insertSubscription
			_entry = mbThreadModel
		/>

		<@insertAssetEntry
			_entry = mbThreadModel
		/>

		${dataFactory.toInsertSQL(dataFactory.newMBThreadFlagModel(mbThreadModel))}

		<#assign mbMessageModels = dataFactory.newMBMessageModels(mbThreadModel) />

		<#list mbMessageModels as mbMessageModel>
			<@insertMBMessage
				_mbMessageModel = mbMessageModel
			/>

			<@insertSocialActivity
				_entry = mbMessageModel
			/>
		</#list>

		${dataFactory.getCSVWriter("messageBoard").write(mbCategoryModel.categoryId + "," + mbThreadModel.threadId + "," + mbThreadModel.rootMessageId + "\n")}
	</#list>
</#list>