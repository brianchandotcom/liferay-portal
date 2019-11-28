<#assign
	companyModel = dataFactory.newCompanyModel() />

	virtualHostModel =  dataFactory.newVirtualHostModel()
/>

${dataFactory.toInsertSQL(companyModel)}

${dataFactory.toInsertSQL(dataFactory.accountModel)}

${dataFactory.toInsertSQL(virtualHostModel)}

${dataFactory.getCSVWriter("company").write(companyModel.companyId + "\n")}