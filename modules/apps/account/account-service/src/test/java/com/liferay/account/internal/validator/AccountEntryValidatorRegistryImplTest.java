/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator;

import com.liferay.account.constants.AccountEntryValidatorResultConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.validator.AccountEntryValidator;
import com.liferay.account.validator.AccountEntryValidatorResult;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Tancredi Covioli
 */
public class AccountEntryValidatorRegistryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_accountEntryValidatorRegistryImpl, "_serviceTrackerMap",
			_serviceTrackerMap);
	}

	@Test
	public void testGetAccountEntryValidator() {
		AccountEntryValidator accountEntryValidator = Mockito.mock(
			AccountEntryValidator.class);

		ServiceWrapper<AccountEntryValidator> serviceWrapper =
			_toServiceWrapper(accountEntryValidator);

		Mockito.when(
			_serviceTrackerMap.getService("key")
		).thenReturn(
			serviceWrapper
		);

		Assert.assertSame(
			accountEntryValidator,
			_accountEntryValidatorRegistryImpl.getAccountEntryValidator("key"));
	}

	@Test
	public void testGetAccountEntryValidatorReturnsNullForBlankKey() {
		Assert.assertNull(
			_accountEntryValidatorRegistryImpl.getAccountEntryValidator(
				StringPool.BLANK));

		Mockito.verifyNoInteractions(_serviceTrackerMap);
	}

	@Test
	public void testGetAccountEntryValidatorReturnsNullWhenNotRegistered() {
		Mockito.when(
			_serviceTrackerMap.getService("key")
		).thenReturn(
			null
		);

		Assert.assertNull(
			_accountEntryValidatorRegistryImpl.getAccountEntryValidator("key"));
	}

	@Test
	public void testGetAccountEntryValidators() {
		AccountEntryValidator accountEntryValidator1 = Mockito.mock(
			AccountEntryValidator.class);
		AccountEntryValidator accountEntryValidator2 = Mockito.mock(
			AccountEntryValidator.class);

		List<ServiceWrapper<AccountEntryValidator>> serviceWrappers =
			Arrays.asList(
				_toServiceWrapper(accountEntryValidator1),
				_toServiceWrapper(accountEntryValidator2));

		Mockito.when(
			_serviceTrackerMap.values()
		).thenReturn(
			serviceWrappers
		);

		List<AccountEntryValidator> accountEntryValidators =
			_accountEntryValidatorRegistryImpl.getAccountEntryValidators();

		Assert.assertEquals(2, accountEntryValidators.size());

		Assert.assertSame(
			accountEntryValidator1, accountEntryValidators.get(0));
		Assert.assertSame(
			accountEntryValidator2, accountEntryValidators.get(1));
	}

	@Test
	public void testValidate() throws Exception {
		Map<String, Object> additionalProps =
			HashMapBuilder.<String, Object>put(
				"field", "value"
			).build();

		AccountEntry accountEntry = Mockito.mock(AccountEntry.class);

		AccountEntryValidator accountEntryValidator1 = Mockito.mock(
			AccountEntryValidator.class);
		AccountEntryValidator accountEntryValidator2 = Mockito.mock(
			AccountEntryValidator.class);

		AccountEntryValidatorResult accountEntryValidatorResult1 =
			AccountEntryValidatorResult.builder(
				"key1"
			).build();
		AccountEntryValidatorResult accountEntryValidatorResult2 =
			AccountEntryValidatorResult.builder(
				"key2"
			).resultStatus(
				AccountEntryValidatorResultConstants.FAILURE
			).build();

		Mockito.when(
			accountEntryValidator1.validate(accountEntry, additionalProps)
		).thenReturn(
			accountEntryValidatorResult1
		);

		Mockito.when(
			accountEntryValidator2.validate(accountEntry, additionalProps)
		).thenReturn(
			accountEntryValidatorResult2
		);

		List<ServiceWrapper<AccountEntryValidator>> serviceWrappers =
			Arrays.asList(
				_toServiceWrapper(accountEntryValidator1),
				_toServiceWrapper(accountEntryValidator2));

		Mockito.when(
			_serviceTrackerMap.values()
		).thenReturn(
			serviceWrappers
		);

		List<AccountEntryValidatorResult> accountEntryValidatorResults =
			_accountEntryValidatorRegistryImpl.validate(
				accountEntry, additionalProps);

		Assert.assertEquals(2, accountEntryValidatorResults.size());

		Assert.assertSame(
			accountEntryValidatorResult1, accountEntryValidatorResults.get(0));
		Assert.assertSame(
			accountEntryValidatorResult2, accountEntryValidatorResults.get(1));

		Mockito.verify(
			accountEntryValidator1
		).validate(
			accountEntry, additionalProps
		);

		Mockito.verify(
			accountEntryValidator2
		).validate(
			accountEntry, additionalProps
		);
	}

	@Test
	public void testValidateReturnsEmptyListForNullAccountEntry()
		throws Exception {

		List<AccountEntryValidatorResult> accountEntryValidatorResults =
			_accountEntryValidatorRegistryImpl.validate(
				null, Collections.emptyMap());

		Assert.assertTrue(accountEntryValidatorResults.isEmpty());

		Mockito.verifyNoInteractions(_serviceTrackerMap);
	}

	private ServiceWrapper<AccountEntryValidator> _toServiceWrapper(
		AccountEntryValidator accountEntryValidator) {

		ServiceWrapper<AccountEntryValidator> serviceWrapper = Mockito.mock(
			ServiceWrapper.class);

		Mockito.when(
			serviceWrapper.getService()
		).thenReturn(
			accountEntryValidator
		);

		return serviceWrapper;
	}

	private final AccountEntryValidatorRegistryImpl
		_accountEntryValidatorRegistryImpl =
			new AccountEntryValidatorRegistryImpl();
	private final ServiceTrackerMap
		<String, ServiceWrapper<AccountEntryValidator>> _serviceTrackerMap =
			Mockito.mock(ServiceTrackerMap.class);

}