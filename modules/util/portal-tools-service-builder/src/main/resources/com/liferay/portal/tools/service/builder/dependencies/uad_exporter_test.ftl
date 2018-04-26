package ${packagePath}.uad.exporter.test;

import ${apiPackagePath}.model.${entity.name};
import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import ${packagePath}.uad.test.${entity.name}UADTestHelper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

<#if entity.hasEntityColumn("statusByUserId")>
	import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;
</#if>

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author ${author}
 * @generated
 */
@RunWith(Arquillian.class)
public class ${entity.name}UADExporterTest extends BaseUADExporterTestCase<${entity.name}> <#if entity.hasEntityColumn("statusByUserId")>implements WhenHasStatusByUserIdField<${entity.name}> </#if>{

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	<#if entity.hasEntityColumn("statusByUserId")>
		@Override
		public ${entity.name} addBaseModelWithStatusByUserId(long userId, long statusByUserId) throws Exception {
			${entity.name} ${entity.varName} = _${entity.varName}UADTestHelper.add${entity.name}WithStatusByUserId(userId, statusByUserId);

			_${entity.varNames}.add(${entity.varName});

			return ${entity.varName};
		}
	</#if>

	@After
	public void tearDown() throws Exception {
		_${entity.varName}UADTestHelper.cleanUpDependencies(_${entity.varNames});
	}

	@Override
	protected ${entity.name} addBaseModel(long userId) throws Exception {
		${entity.name} ${entity.varName} = _${entity.varName}UADTestHelper.add${entity.name}(userId);

		_${entity.varNames}.add(${entity.varName});

		return ${entity.varName};
	}

	@Override
	protected String getPrimaryKeyName() {
		return "${entity.PKEntityColumns[0].name}";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<${entity.name}> _${entity.varNames} = new ArrayList<${entity.name}>();

	@Inject
	private ${entity.name}UADTestHelper _${entity.varName}UADTestHelper;

	@Inject(
		filter = "model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}
	)
	private UADExporter _uadExporter;

}