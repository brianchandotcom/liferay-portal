/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.test.util;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public abstract class BaseCTUpgradeProcessTestCase {

	@Test
	public void testMissingCtCollectionId() throws Exception {
		CTCollection ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		CTModel<?> productionCTModel = addCTModel();

		CTModel<?> publicationCTModel = (CTModel<?>)productionCTModel.clone();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			publicationCTModel = updateCTModel(publicationCTModel);
		}

		Map<String, Object> productionModelAttributes =
			productionCTModel.getModelAttributes();

		Map<String, Object> publicationModelAttributes =
			publicationCTModel.getModelAttributes();

		Map<String, Boolean> modelAttributeDiffs = new HashMap<>();

		for (Map.Entry<String, Object> productionEntry :
				productionModelAttributes.entrySet()) {

			Object publicationValue = publicationModelAttributes.get(
				productionEntry.getKey());

			modelAttributeDiffs.put(
				productionEntry.getKey(),
				Objects.equals(productionEntry.getValue(), publicationValue));
		}

		runUpgrade();

		CTPersistence<?> ctPersistence = getCTPersistence();

		ctPersistence.clearCache(
			Collections.singleton(productionCTModel.getPrimaryKey()));

		productionCTModel = fetchCTModel(productionCTModel.getPrimaryKey());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			publicationCTModel = fetchCTModel(
				publicationCTModel.getPrimaryKey());
		}

		productionModelAttributes = productionCTModel.getModelAttributes();

		publicationModelAttributes = publicationCTModel.getModelAttributes();

		for (Map.Entry<String, Object> entry :
				productionModelAttributes.entrySet()) {

			Object publicationValue = publicationModelAttributes.get(
				entry.getKey());

			Assert.assertEquals(
				modelAttributeDiffs.get(entry.getKey()),
				Objects.equals(entry.getValue(), publicationValue));
		}

		_ctCollectionService.deleteCTCollection(ctCollection);

		deleteCTModel(productionCTModel.getPrimaryKey());
	}

	protected abstract CTModel<?> addCTModel() throws Exception;

	protected void deleteCTModel(long primaryKey) throws Exception {
		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					CTPersistence<?> ctPersistence = getCTPersistence();

					ctPersistence.remove(primaryKey);

					return null;
				});
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	protected CTModel<?> fetchCTModel(long primaryKey) throws Exception {
		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					CTPersistence<?> ctPersistence = getCTPersistence();

					return ctPersistence.fetchByPrimaryKey(primaryKey);
				});
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	protected abstract CTPersistence<?> getCTPersistence();

	protected abstract void runUpgrade() throws Exception;

	protected abstract CTModel<?> updateCTModel(CTModel<?> ctModel)
		throws Exception;

	@Inject
	private static CTCollectionService _ctCollectionService;

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}