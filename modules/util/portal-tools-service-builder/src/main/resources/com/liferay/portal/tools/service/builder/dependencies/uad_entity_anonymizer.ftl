package ${packagePath}.uad.anonymizer;

import ${apiPackagePath}.model.${entity.name};
import ${apiPackagePath}.service.${entity.name}LocalService;
import ${packagePath}.uad.constants.${portletShortName}UADConstants;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.anonymizer.DynamicQueryUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
 * @generated
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}},
	service = UADEntityAnonymizer.class
)
public class ${entity.name}UADEntityAnonymizer extends DynamicQueryUADEntityAnonymizer<${entity.name}> {

	@Override
	public void autoAnonymize(${entity.name} ${entity.varName}, long userId) throws PortalException {
		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		<#list entity.UADUserIdColumnNames as uadUserIdColumnName>
			<#assign uadUserIdEntityColumn = entity.getEntityColumn(uadUserIdColumnName) />

					if (${entity.varName}.get${uadUserIdEntityColumn.methodName}() == userId) {
			<#list entity.UADAnonymizableEntityColumnsMap[uadUserIdColumnName] as uadAnonymizableEntityColumn>
				${entity.varName}.set${uadAnonymizableEntityColumn.methodName}(anonymousUser.get${textFormatter.format(uadAnonymizableEntityColumn.UADAnonymizeFieldName, 6)}());
			</#list>
					}
		</#list>

		_${entity.varName}LocalService.update${entity.name}(${entity.varName});
	}

	@Override
	public void delete(${entity.name} ${entity.varName}) throws PortalException {
		_${entity.varName}LocalService.${deleteUADEntityMethodName}(${entity.varName});
	}

	@Override
	public List<String> getNonanonymizableFieldNames() {
		return Arrays.asList(<#list entity.UADNonanonymizableEntityColumns as uadNonanonymizableEntityColumn>"${uadNonanonymizableEntityColumn.name}"<#sep>, </#sep></#list>);
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _${entity.varName}LocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return ${portletShortName}UADConstants.USER_ID_FIELD_NAMES_${entity.constantName};
	}

	@Reference
	private ${entity.name}LocalService _${entity.varName}LocalService;

	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;

}