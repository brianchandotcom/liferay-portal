package ${packagePath}.uad.aggregator;

import ${apiPackagePath}.model.${entity.name};
import ${apiPackagePath}.service.${entity.name}LocalService;
import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import ${packagePath}.uad.entity.${entity.name}UADEntity;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.aggregator.BaseUADEntityAggregator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import java.util.ArrayList;
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
	service = UADEntityAggregator.class
)
public class ${entity.name}UADEntityAggregator extends BaseUADEntityAggregator {

	@Override
	public int count(long userId) {
		return (int)_${entity.varName}LocalService.dynamicQueryCount(_getDynamicQuery(userId));
	}

	@Override
	public List<UADEntity> getUADEntities(long userId, int start, int end) {
		List<${entity.name}> ${entity.varNames} = _${entity.varName}LocalService.dynamicQuery(_getDynamicQuery(userId), start, end);

		List<UADEntity> uadEntities = new ArrayList<UADEntity>(${entity.varNames}.size());

		for (${entity.name} ${entity.varName} : ${entity.varNames}) {
			uadEntities.add(new ${entity.name}UADEntity(userId, _getUADEntityId(userId, ${entity.varName}), ${entity.varName}));
		}

		return uadEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) throws PortalException {
		${entity.name} ${entity.varName} = _${entity.varName}LocalService.get${entity.name}(_get${textFormatter.format(entity.PKVarName, 6)}(uadEntityId));

		return new ${entity.name}UADEntity(_getUserId(uadEntityId), uadEntityId, ${entity.varName});
	}

	@Override
	public String getUADEntitySetName() {
		return ${portletShortName}UADConstants.UAD_ENTITY_SET_NAME;
	}

	private DynamicQuery _getDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addDynamicQueryCriteria(_${entity.varName}LocalService.dynamicQuery(), ${portletShortName}UADConstants.USER_ID_FIELD_NAMES_${entity.constantName}, userId);
	}

	private long _get${textFormatter.format(entity.PKVarName, 6)}(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[0]);
	}

	private String _getUADEntityId(long userId, ${entity.name} ${entity.varName}) {
		return String.valueOf(${entity.varName}.get${textFormatter.format(entity.PKVarName, 6)}()) + "#" + String.valueOf(userId);
	}

	private long _getUserId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[1]);
	}

	@Reference
	private ${entity.name}LocalService _${entity.varName}LocalService;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

}