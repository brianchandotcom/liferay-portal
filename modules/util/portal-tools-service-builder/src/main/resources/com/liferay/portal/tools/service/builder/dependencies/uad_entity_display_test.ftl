package ${packagePath}.uad.display.test;

import ${apiPackagePath}.model.${entity.name};
import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import ${packagePath}.uad.test.${entity.name}UADEntityTestHelper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.test.util.BaseUADEntityDisplayTestCase;

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
public class ${entity.name}UADEntityDisplayTest extends BaseUADEntityDisplayTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		${entity.name} ${entity.varName} = _${entity.varName}UADEntityTestHelper.add${entity.name}(userId);

		_${entity.varNames}.add(${entity.varName});

		return ${entity.varName};
	}

	@Override
	protected String getApplicationName() {
		return ${portletShortName}UADConstants.APPLICATION_NAME;
	}

	@Override
	protected UADAggregator getUADAggregator() {
		return _uadAggregator;
	}

	@Override
	protected UADEntityDisplay getUADEntityDisplay() {
		return _uadEntityDisplay;
	}

	@Override
	protected String getTypeDescription() {
		return "${entity.UADTypeDescription}";
	}

	@After
	public void tearDown() throws Exception {
		_${entity.varName}UADEntityTestHelper.cleanUpDependencies(_${entity.varNames});
	}

	@DeleteAfterTestRun
	private final List<${entity.name}> _${entity.varNames} = new ArrayList<${entity.name}>();

	@Inject
	private ${entity.name}UADEntityTestHelper _${entity.varName}UADEntityTestHelper;

	@Inject(
		filter = "model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}
	)
	private UADAggregator _uadAggregator;

	@Inject(
		filter = "model.class.name=" + ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName}
	)
	private UADEntityDisplay _uadEntityDisplay;

}