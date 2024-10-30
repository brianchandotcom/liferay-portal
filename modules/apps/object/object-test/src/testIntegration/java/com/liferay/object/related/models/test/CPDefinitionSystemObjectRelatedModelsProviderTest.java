/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class CPDefinitionSystemObjectRelatedModelsProviderTest
	extends BaseSystemObjectRelatedModelsProviderTestCase {

	@Override
	protected long[] addBaseModels(int count) throws Exception {
		long[] cpDefinitionIds = new long[count];

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				LocaleUtil.US.getDisplayLanguage(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId()));

		for (int i = 0; i < count; i++) {
			CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
				commerceCatalog.getGroupId());

			cpDefinitionIds[i] = cpDefinition.getCPDefinitionId();
		}

		return cpDefinitionIds;
	}

	@Override
	protected void assertFailure(long primaryKey) {
		AssertUtils.assertFailure(
			NoSuchCPDefinitionException.class,
			"No CPDefinition exists with the primary key " + primaryKey,
			() -> _cpDefinitionLocalService.getCPDefinition(primaryKey));
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		_cpDefinitionLocalService.deleteCPDefinition(primaryKey);
	}

	@Override
	protected Object fetchBaseModel(long primaryKey) {
		return _cpDefinitionLocalService.fetchCPDefinition(primaryKey);
	}

	@Override
	protected String getName(long primaryKey) throws Exception {
		return String.valueOf(primaryKey);
	}

	@Override
	protected ObjectDefinition getSystemObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.fetchObjectDefinitionByClassName(
			TestPropsValues.getCompanyId(), CPDefinition.class.getName());
	}

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}