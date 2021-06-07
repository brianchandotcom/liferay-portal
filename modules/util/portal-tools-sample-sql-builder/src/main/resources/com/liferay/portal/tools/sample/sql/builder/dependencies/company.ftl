<#assign companyModel = dataFactory.newCompanyModel() />

${dataFactory.toInsertSQL(companyModel)}

${dataFactory.toInsertSQL(dataFactory.newVirtualHostModel())}

<#list dataFactory.newPortalPreferencesModels() as portalPreferencesModel>
	${dataFactory.toInsertSQL(portalPreferencesModel)}
</#list>

${csvFileWriter.write("company", companyModel.companyId + "\n")}