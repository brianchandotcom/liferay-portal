<#assign
	accountModel = dataFactory.newAccountModel()

	companyModel = dataFactory.newCompanyModel()

	virtualHostModel =  dataFactory.newVirtualHostModel()
/>

${dataFactory.toInsertSQL(companyModel)}

${dataFactory.toInsertSQL(accountModel)}

${dataFactory.toInsertSQL(virtualHostModel)}

${dataFactory.getCSVWriter("company").write(companyModel.companyId + "\n")}