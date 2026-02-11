/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.gcs.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.store.StoreArea;
import com.liferay.document.library.kernel.store.StoreAreaAwareStoreWrapper;
import com.liferay.document.library.kernel.store.StoreAreaProcessor;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@FeatureFlag("LPS-174816")
@RunWith(Arquillian.class)
public class GCStoreStoreAreaAwareStoreWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		String gcsStoreClassName = "com.liferay.portal.store.gcs.GCSStore";
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		Assume.assumeTrue(
			StringBundler.concat(
				"Property \"", PropsKeys.DL_STORE_IMPL, "\" is not set to \"",
				gcsStoreClassName, "\""),
			dlStoreImpl.equals(gcsStoreClassName));
	}

	@Test
	public void testDeleteCompanyDirectory() throws Exception {
		String fileName = StringUtil.randomString();

		Company company = CompanyTestUtil.addCompany();

		try {
			_wrappedStore.addFile(
				company.getCompanyId(), company.getGroupId(), fileName,
				Store.VERSION_DEFAULT,
				new UnsyncByteArrayInputStream(new byte[0]));

			_wrappedStore.deleteDirectory(company.getCompanyId());

			Assert.assertFalse(
				_store.hasFile(
					company.getCompanyId(), company.getGroupId(), fileName,
					Store.VERSION_DEFAULT));

			StoreArea.withStoreArea(
				StoreArea.DELETED,
				() -> Assert.assertTrue(
					_store.hasFile(
						company.getCompanyId(), company.getGroupId(), fileName,
						Store.VERSION_DEFAULT)));
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static final Snapshot<StoreAreaProcessor>
		_storeAreaProcessorSnapshot = new Snapshot<>(
			GCStoreStoreAreaAwareStoreWrapperTest.class,
			StoreAreaProcessor.class,
			"(store.type=" + PropsValues.DL_STORE_IMPL + ")");
	private static final Snapshot<Store> _storeSnapshot = new Snapshot<>(
		GCStoreStoreAreaAwareStoreWrapperTest.class, Store.class,
		"(default=true)", true);
	private static final Store _wrappedStore = new StoreAreaAwareStoreWrapper(
		_storeSnapshot::get, _storeAreaProcessorSnapshot::get);

	@Inject(
		filter = "store.type=com.liferay.portal.store.gcs.GCSStore",
		type = Store.class
	)
	private Store _store;

}