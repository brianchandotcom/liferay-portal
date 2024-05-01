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

	<#include "list_type_definitions.ftl">

	<#include "roles.ftl">

	<#include "default_groups.ftl">

	<#include "groups.ftl">

	<#include "ct_collection.ftl">

	<#-- SYSTEM_DEFAULT SAPEntry -->

	<#list dataFactory.newSystemDefaultSAPEntryModel(companyModel) as sapEntryModel>
		${dataFactory.toInsertSQL(sapEntryModel)}

		<#assign sapEntryResourcePermissionModel = dataFactory.newSAPEntryResourcePermissionModel(sapEntryModel) />

		${dataFactory.toInsertSQL(sapEntryResourcePermissionModel)}
	</#list>
</#list>