package com.liferay.portal.security.auth;



import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

/**
 * @author Riccardo Ferrari
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DefaultScreenNameGeneratorTest {

	private ScreenNameGenerator _defaultScreenNameGenerator;
	private boolean _users_screen_name_allow_numeric = false;
	private long _companyId;

	@Before
	public void setUp() throws SystemException{
		_defaultScreenNameGenerator = ScreenNameGeneratorFactory.getInstance();
		_users_screen_name_allow_numeric = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_ALLOW_NUMERIC));
		_companyId = 0;

		List<Company> companies =  CompanyLocalServiceUtil.getCompanies(0, 1);
		for (Company company : companies){
			_companyId = company.getCompanyId();
		}
	}

	@Test
	public void testUsingDefaultScreenNameGeneratorClass(){
		Assert.assertEquals("com.liferay.portal.security.auth.DefaultScreenNameGenerator", _defaultScreenNameGenerator.getClass().getName());
	}

	@Test
	public void testGenerate() throws Exception {

		String generatedScreenName = _defaultScreenNameGenerator.generate(_companyId, 0, "user123@liferay.com");
		Assert.assertEquals("user123", generatedScreenName);

	}

	@Test
	public void testGenerateWithAllowNumeric() throws Exception{
		String generatedScreenName = _defaultScreenNameGenerator.generate(_companyId, 0, "123@liferay.com");
		if (_users_screen_name_allow_numeric){
			Assert.assertNotSame("user.123", generatedScreenName);
			Assert.assertEquals("123", generatedScreenName);
		} else {
			Assert.assertNotSame("123", generatedScreenName);
			Assert.assertEquals("user.123", generatedScreenName);
		}
	}

	@Test
	public void testGenerateAlreadyExisting() throws Exception {
		String generatedScreenName = _defaultScreenNameGenerator.generate(_companyId, 0, "test@liferay.com");
		Assert.assertNotSame("test", generatedScreenName);
		Assert.assertEquals("test.1", generatedScreenName);
	}

}
