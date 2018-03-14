package ${packagePath}.uad.anonymizer;

import ${apiPackagePath}.model.${entity.name};
import ${apiPackagePath}.service.${entity.name}LocalService;
import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import ${packagePath}.uad.entity.${entity.name}UADEntity;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.BaseUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

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
public class ${entity.name}UADEntityAnonymizer extends BaseUADEntityAnonymizer {

	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		${entity.name} ${entity.varName} = _get${entity.name}(uadEntity);

		_autoAnonymize(${entity.varName}, uadEntity.getUserId());
	}

	@Override
	public void autoAnonymizeAll(final long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<${entity.name}>() {

				@Override
				public void performAction(${entity.name} ${entity.varName}) throws PortalException {
					_autoAnonymize(${entity.varName}, userId);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		_${entity.varName}LocalService.${deleteUADEntityMethodName}(_get${entity.name}(uadEntity));
	}

	@Override
	public void deleteAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<${entity.name}>() {

				@Override
				public void performAction(${entity.name} ${entity.varName}) throws PortalException {
					_${entity.varName}LocalService.${deleteUADEntityMethodName}(${entity.varName});
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	@Override
	public List<String> getUADEntityNonanonymizableFieldNames() {
		return Arrays.asList(<#list entity.UADNonanonymizableEntityColumns as uadNonanonymizableEntityColumn>"${uadNonanonymizableEntityColumn.name}"<#sep>, </#sep></#list>);
	}

	private void _autoAnonymize(${entity.name} ${entity.varName}, long userId) throws PortalException {
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

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(_${entity.varName}LocalService.getActionableDynamicQuery(), ${portletShortName}UADConstants.USER_ID_FIELD_NAMES_${entity.constantName}, userId);
	}

	private ${entity.name} _get${entity.name}(UADEntity uadEntity) throws PortalException {
		_validate(uadEntity);

		${entity.name}UADEntity ${entity.varName}UADEntity = (${entity.name}UADEntity)uadEntity;

		return ${entity.varName}UADEntity.get${entity.name}();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof ${entity.name}UADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private ${entity.name}LocalService _${entity.varName}LocalService;

	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

	@Reference(
		target = "(model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName} + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

}