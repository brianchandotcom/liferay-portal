package ${packagePath}.uad.aggregator.test;

import ${apiPackagePath}.model.${entity.name};
import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import ${packagePath}.uad.test.${entity.name}UADEntityTestHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.test.util.BaseUADEntityAggregatorTestCase;
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
public class ${entity.name}UADEntityAggregatorTest extends BaseUADEntityAggregatorTestCase <#if entity.hasEntityColumn("statusByUserId")>implements WhenHasStatusByUserIdField </#if>{

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
		${entity.name} ${entity.varName} = _${entity.varName}UADEntityTestHelper.add${entity.name}(userId);

		_${entity.varNames}.add(${entity.varName});

		return ${entity.varName};
	}

	@Override
	protected String getUADRegistryKey() {
		return ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName};
	}

	@DeleteAfterTestRun
	private final List<${entity.name}> _${entity.varNames} = new ArrayList<${entity.name}>();

	@Inject
	private ${entity.name}UADEntityTestHelper _${entity.varName}UADEntityTestHelper;

}