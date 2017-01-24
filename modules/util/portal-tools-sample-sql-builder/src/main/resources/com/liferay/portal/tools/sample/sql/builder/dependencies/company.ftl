${dataFactory.toInsertSQL(dataFactory.companyModel)}
${dataFactory.toInsertSQL(dataFactory.accountModel)}
${dataFactory.toInsertSQL(dataFactory.virtualHostModel)}

${dataFactory.getCSVWriter("company").write(dataFactory.companyModel.companyId + "\n")}