package ${packagePath}.uad.entity;

import ${apiPackagePath}.model.${entity.name};
import ${packagePath}.uad.constants.${portletShortName}UADConstants;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.test.util.BaseUADEntityTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author ${author}
 * @generated
 */
public class ${entity.name}UADEntityTest extends BaseUADEntityTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		super.setUp();
	}

	@Test
	public void testGet${entity.name}() throws Exception {
		${entity.name}UADEntity ${entity.varName}UADEntity = (${entity.name}UADEntity)uadEntity;

		Assert.assertEquals(_${entity.varName}, ${entity.varName}UADEntity.get${entity.name}());
	}

	@Override
	protected UADEntity createUADEntity(long userId, String uadEntityId) {
		return new ${entity.name}UADEntity(userId, uadEntityId, _${entity.varName});
	}

	@Override
	protected String getUADRegistryKey() {
		return ${portletShortName}UADConstants.CLASS_NAME_${entity.constantName};
	}

	@Mock
	private ${entity.name} _${entity.varName};

}