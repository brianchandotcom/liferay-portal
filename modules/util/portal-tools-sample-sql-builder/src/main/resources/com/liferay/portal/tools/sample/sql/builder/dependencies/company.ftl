<#list dataFactory.newCompanyModels() as companyModel>
	${dataFactory.setCompanyId(companyModel.companyId)}

	${dataFactory.setWebId(companyModel.webId)}

	<#assign virtualHostModel = dataFactory.newVirtualHostModel() />

	${dataFactory.toInsertSQL(companyModel)}

	${dataFactory.toInsertSQL(virtualHostModel)}

	<#list dataFactory.newPortalPreferencesModels() as portalPreferencesModel>
		${dataFactory.toInsertSQL(portalPreferencesModel)}
	</#list>

	${csvFileWriter.write("company", virtualHostModel.hostname + "," + companyModel.companyId + "\n")}

	<#include "roles.ftl">

	<#include "groups.ftl">
</#list>