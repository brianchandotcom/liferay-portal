<#assign companyModel = dataFactory.newCompanyModel() />

${dataFactory.toInsertSQL(companyModel)}

${dataFactory.toInsertSQL(dataFactory.accountModel)}

${dataFactory.toInsertSQL(dataFactory.virtualHostModel)}

${dataFactory.getCSVWriter("company").write(companyModel.companyId + "\n")}