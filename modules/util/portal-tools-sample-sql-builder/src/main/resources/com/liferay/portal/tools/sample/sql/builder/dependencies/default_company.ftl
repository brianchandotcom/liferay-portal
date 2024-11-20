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

<#include "notification_templates.ftl">

<#include "system_object_definitions.ftl">