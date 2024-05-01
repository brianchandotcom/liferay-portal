<#assign defaultCompanyModel = dataFactory.newDefaultCompanyModel() />

${dataFactory.setCompanyId(defaultCompanyModel.companyId)}

${dataFactory.setWebId(defaultCompanyModel.webId)}

${dataFactory.toInsertSQL(defaultCompanyModel)}

${dataFactory.toInsertSQL(dataFactory.newVirtualHostModel())}

<#list dataFactory.newPortalPreferencesModels() as portalPreferencesModel>
	${dataFactory.toInsertSQL(portalPreferencesModel)}
</#list>

<#include "roles.ftl">

<#include "default_groups.ftl">

<#-- SYSTEM_DEFAULT SAPEntry -->

<#list dataFactory.newSystemDefaultSAPEntryModel(defaultCompanyModel) as sapEntryModel>
	${dataFactory.toInsertSQL(sapEntryModel)}

	<#assign sapEntryResourcePermissionModel = dataFactory.newSAPEntryResourcePermissionModel(sapEntryModel) />

	${dataFactory.toInsertSQL(sapEntryResourcePermissionModel)}
</#list>