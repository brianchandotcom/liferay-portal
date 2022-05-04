/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.portal.security.samba;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Jonathan McCann
 */
public class PortalSambaUtilTest extends BaseVLDAPTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		_clazz = Class.forName(PortalSambaUtil.class.getName());

		_classInstance = _clazz.newInstance();
	}

	@Test
	public void testCheckAttribute() throws Exception {
		_setUpCompanyLocalServiceUtil();
		_setUpExpandoBridge();

		Method checkAttributeMethod = _clazz.getDeclaredMethod(
			"_checkAttribute", String.class);

		checkAttributeMethod.setAccessible(true);

		checkAttributeMethod.invoke(_classInstance, "sambaLMPassword");

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).addAttribute(
			"sambaLMPassword", false
		);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttributeProperties(
			"sambaLMPassword",
			UnicodePropertiesBuilder.put(
				ExpandoColumnConstants.PROPERTY_HIDDEN, StringPool.TRUE
			).build(),
			false
		);
	}

	@Test
	public void testCheckAttributes() throws Exception {
		_setUpCompanyLocalServiceUtil();
		_setUpExpandoBridge();

		PortalSambaUtil.checkAttributes();

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			ExpandoColumnConstants.PROPERTY_HIDDEN, StringPool.TRUE
		).build();

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).addAttribute(
			"sambaLMPassword", false
		);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttributeProperties(
			"sambaLMPassword", unicodeProperties, false
		);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).addAttribute(
			"sambaNTPassword", false
		);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttributeProperties(
			"sambaNTPassword", unicodeProperties, false
		);
	}

	@Test
	public void testCheckAttributeWithExistingAttribute() throws Exception {
		_setUpCompanyLocalServiceUtil();
		_setUpExpandoBridge();

		Mockito.when(
			_expandoBridge.hasAttribute(Matchers.eq("sambaLMPassword"))
		).thenReturn(
			true
		);

		Method checkAttributeMethod = _clazz.getDeclaredMethod(
			"_checkAttribute", String.class);

		checkAttributeMethod.setAccessible(true);

		checkAttributeMethod.invoke(_classInstance, "sambaLMPassword");

		Mockito.verify(
			_expandoBridge, Mockito.times(0)
		).addAttribute(
			Matchers.anyString(), Matchers.anyBoolean()
		);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttributeProperties(
			"sambaLMPassword",
			UnicodePropertiesBuilder.put(
				ExpandoColumnConstants.PROPERTY_HIDDEN, StringPool.TRUE
			).build(),
			false
		);
	}

	@Test
	public void testEncryptSambaLMPassword() throws Exception {
		Method getSambaLMPassword = _clazz.getDeclaredMethod(
			"_encryptSambaLMPassword", String.class);

		getSambaLMPassword.setAccessible(true);

		String sambaLMPassword = (String)getSambaLMPassword.invoke(
			_classInstance, "");

		Assert.assertEquals(
			"AAD3B435B51404EEAAD3B435B51404EE", sambaLMPassword);

		sambaLMPassword = (String)getSambaLMPassword.invoke(
			_classInstance, "test");

		Assert.assertEquals(
			"01FC5A6BE7BC6929AAD3B435B51404EE", sambaLMPassword);

		sambaLMPassword = (String)getSambaLMPassword.invoke(
			_classInstance, "password");

		Assert.assertEquals(
			"E52CAC67419A9A224A3B108F3FA6CB6D", sambaLMPassword);
	}

	@Test
	public void testEncryptSambaNTPassword() throws Exception {
		Method getSambaNTPassword = _clazz.getDeclaredMethod(
			"_encryptSambaNTPassword", String.class);

		getSambaNTPassword.setAccessible(true);

		String sambaNTPassword = (String)getSambaNTPassword.invoke(
			_classInstance, "");

		Assert.assertEquals(
			"31D6CFE0D16AE931B73C59D7E0C089C0", sambaNTPassword);

		sambaNTPassword = (String)getSambaNTPassword.invoke(
			_classInstance, "password");

		Assert.assertEquals(
			"8846F7EAEE8FB117AD06BDD830B7586C", sambaNTPassword);
	}

	@Test
	public void testGetSambaLMPassword() throws Exception {
		_setUpExpandoBridge();
		_setUpUser();

		PortalSambaUtil.getSambaLMPassword(_user);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).getAttribute(
			"sambaLMPassword", false
		);
	}

	@Test
	public void testGetSambaNTPassword() throws Exception {
		_setUpExpandoBridge();
		_setUpUser();

		PortalSambaUtil.getSambaNTPassword(_user);

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).getAttribute(
			"sambaNTPassword", false
		);
	}

	@Test
	public void testSetSambaLMPassword() throws Exception {
		_setUpExpandoBridge();
		_setUpUser();

		PortalSambaUtil.setSambaLMPassword(_user, "password");

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttribute(
			"sambaLMPassword", "E52CAC67419A9A224A3B108F3FA6CB6D", false
		);
	}

	@Test
	public void testSetSambaNTPassword() throws Exception {
		_setUpExpandoBridge();
		_setUpUser();

		PortalSambaUtil.setSambaNTPassword(_user, "password");

		Mockito.verify(
			_expandoBridge, Mockito.times(1)
		).setAttribute(
			"sambaNTPassword", "8846F7EAEE8FB117AD06BDD830B7586C", false
		);
	}

	private void _setUpCompanyLocalServiceUtil() throws Exception {
		CompanyLocalService companyLocalService = Mockito.mock(
			CompanyLocalService.class);

		ReflectionTestUtil.setFieldValue(
			CompanyLocalServiceUtil.class, "_service", companyLocalService);

		Mockito.doAnswer(
			invocation -> {
				UnsafeConsumer<Long, Exception> unsafeConsumer =
					(UnsafeConsumer)invocation.getArguments()[0];

				unsafeConsumer.accept(PRIMARY_KEY);

				return null;
			}
		).when(
			companyLocalService
		).forEachCompanyId(
			Mockito.any(UnsafeConsumer.class)
		);
	}

	private void _setUpExpandoBridge() throws Exception {
		_expandoBridge = Mockito.mock(ExpandoBridge.class);

		Mockito.when(
			_expandoBridge.getAttributeProperties("sambaLMPassword")
		).thenReturn(
			new UnicodeProperties()
		);

		Mockito.when(
			_expandoBridge.getAttributeProperties("sambaNTPassword")
		).thenReturn(
			new UnicodeProperties()
		);

		ExpandoBridgeFactory expandoBridgeFactory = Mockito.mock(
			ExpandoBridgeFactory.class);

		Mockito.when(
			expandoBridgeFactory.getExpandoBridge(
				Matchers.eq(PRIMARY_KEY), Matchers.eq(User.class.getName()))
		).thenReturn(
			_expandoBridge
		);

		Mockito.doReturn(
			_expandoBridge
		).when(
			expandoBridgeFactory
		).getExpandoBridge(
			Matchers.eq(PRIMARY_KEY), Matchers.eq(User.class.getName())
		);

		ReflectionTestUtil.setFieldValue(
			ExpandoBridgeFactoryUtil.class, "_expandoBridgeFactory",
			expandoBridgeFactory);
	}

	private void _setUpUser() {
		_user = Mockito.mock(User.class);

		Mockito.when(
			_user.getExpandoBridge()
		).thenReturn(
			_expandoBridge
		);
	}

	private static Object _classInstance;
	private static Class<?> _clazz;
	private static ExpandoBridge _expandoBridge;
	private static User _user;

}