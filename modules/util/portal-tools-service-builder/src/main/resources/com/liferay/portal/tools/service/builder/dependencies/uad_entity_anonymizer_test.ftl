package ${packagePath}.uad.anonymizer.test;

import ${apiPackagePath}.model.${entity.name};
import ${apiPackagePath}.service.${entity.name}LocalService;
import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import ${packagePath}.uad.test.${entity.name}UADEntityTestHelper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADEntityAnonymizerTestCase;

<#if entity.hasEntityColumn("statusByUserId")>
	import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;
</#if>

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author ${author}
 * @generated
 */
@RunWith(Arquillian.class)
public class ${entity.name}UADEntityAnonymizerTest extends BaseUADEntityAnonymizerTestCase <#if entity.hasEntityColumn("statusByUserId")>implements WhenHasStatusByUserIdField </#if>{

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	<#if entity.hasEntityColumn("statusByUserId")>
		@Override
		public BaseModel<?> addBaseModelWithStatusByUserId(long userId, long statusByUserId) throws Exception {
			${entity.name} ${entity.varName} = _${entity.varName}UADEntityTestHelper.add${entity.name}WithStatusByUserId(userId, statusByUserId);

			_${entity.varNames}.add(${entity.varName});

			return ${entity.varName};
		}
	</#if>

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected BaseModel<?> addBaseModel(long userId, boolean deleteAfterTestRun) throws Exception {
		${entity.name} ${entity.varName} = _${entity.varName}UADEntityTestHelper.add${entity.name}(userId);

		if (deleteAfterTestRun) {
			_${entity.varNames}.add(${entity.varName});
		}

		return ${entity.varName};
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	@Override
	protected UADEntityAnonymizer getUADEntityAnonymizer() {
		return _uadEntityAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user) throws Exception {
		${entity.name} ${entity.varName} = _${entity.varName}LocalService.get${entity.name}(baseModelPK);

		<#list entity.UADUserIdColumnNames as uadUserIdColumnName>
			<#list entity.UADAnonymizableEntityColumnsMap[uadUserIdColumnName] as uadAnonymizableEntityColumn>
				<#if !uadAnonymizableEntityColumn.isPrimitiveType()>
					${uadAnonymizableEntityColumn.type} ${uadAnonymizableEntityColumn.name} = ${entity.varName}.get${uadAnonymizableEntityColumn.methodName}();
				</#if>
			</#list>
		</#list>

		if (<#list entity.UADUserIdColumnNames as uadUserIdColumnName>
				<#list entity.UADAnonymizableEntityColumnsMap[uadUserIdColumnName] as uadAnonymizableEntityColumn>
					<#if uadAnonymizableEntityColumn.isPrimitiveType()>
						(${entity.varName}.get${uadAnonymizableEntityColumn.methodName}() !=
					<#else>
						!${uadAnonymizableEntityColumn.name}.equals(
					</#if>

					user.get${textFormatter.format(uadAnonymizableEntityColumn.UADAnonymizeFieldName, 6)}())

					<#sep> && </#sep>
				</#list>

				<#sep> && </#sep>
			</#list>) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_${entity.varName}LocalService.fetch${entity.name}(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<${entity.name}> _${entity.varNames} = new ArrayList<${entity.name}>();

	@Inject
	private ${entity.name}LocalService _${entity.varName}LocalService;

	@Inject
	private ${entity.name}UADEntityTestHelper _${entity.varName}UADEntityTestHelper;

	@Inject(
		filter = "model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}
	)
	private UADEntityAggregator _uadEntityAggregator;

	@Inject(
		filter = "model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}
	)
	private UADEntityAnonymizer _uadEntityAnonymizer;

}