package ${entity.UADPackagePath}.uad.display.test;

import ${apiPackagePath}.model.${entity.name};
import ${entity.UADPackagePath}.uad.constants.${entity.UADApplicationName}UADConstants;
import ${entity.UADPackagePath}.uad.test.${entity.name}UADTestHelper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.test.util.BaseUADDisplayTestCase;

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
public class ${entity.name}UADDisplayTest extends BaseUADDisplayTestCase<${entity.name}> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	protected ${entity.name} addBaseModel(long userId) throws Exception {
		${entity.name} ${entity.varName} = _${entity.varName}UADTestHelper.add${entity.name}(userId);

		_${entity.varNames}.add(${entity.varName});

		return ${entity.varName};
	}

	@Override
	protected String getApplicationName() {
		return ${entity.UADApplicationName}UADConstants.APPLICATION_NAME;
	}

	@Override
	protected UADDisplay getUADDisplay() {
		return _uadDisplay;
	}

	@After
	public void tearDown() throws Exception {
		_${entity.varName}UADTestHelper.cleanUpDependencies(_${entity.varNames});
	}

	@DeleteAfterTestRun
	private final List<${entity.name}> _${entity.varNames} = new ArrayList<${entity.name}>();

	@Inject
	private ${entity.name}UADTestHelper _${entity.varName}UADTestHelper;

	@Inject(filter = "component.name=*.${entity.name}UADDisplay")
	private UADDisplay _uadDisplay;

}