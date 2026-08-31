/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.model.XMLSitemapRegenerationEntry;
import com.liferay.site.service.XMLSitemapRegenerationEntryLocalService;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
@Sync
public class XMLSitemapRegenerationEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_xmlSitemapRegenerationEntryLocalService.
			deleteXMLSitemapRegenerationEntries(TestPropsValues.getCompanyId());
	}

	@Test
	public void testAddXMLSitemapRegenerationEntryDeduplicatesInTransaction()
		throws Throwable {

		String assetTypeKey = RandomTestUtil.randomString();
		long companyId = TestPropsValues.getCompanyId();
		long groupId = RandomTestUtil.randomLong();

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				_xmlSitemapRegenerationEntryLocalService.
					addXMLSitemapRegenerationEntry(
						assetTypeKey, companyId, groupId);
				_xmlSitemapRegenerationEntryLocalService.
					addXMLSitemapRegenerationEntry(
						assetTypeKey, companyId, groupId);

				return null;
			});

		List<XMLSitemapRegenerationEntry> xmlSitemapRegenerationEntries =
			_getXMLSitemapRegenerationEntries(companyId);

		Assert.assertEquals(
			xmlSitemapRegenerationEntries.toString(), 1,
			xmlSitemapRegenerationEntries.size());

		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry =
			xmlSitemapRegenerationEntries.get(0);

		Assert.assertEquals(
			assetTypeKey, xmlSitemapRegenerationEntry.getAssetTypeKey());
		Assert.assertEquals(groupId, xmlSitemapRegenerationEntry.getGroupId());
	}

	@Test
	public void testAddXMLSitemapRegenerationEntryDiscardedOnRollback()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_xmlSitemapRegenerationEntryLocalService.
						addXMLSitemapRegenerationEntry(
							RandomTestUtil.randomString(), companyId,
							RandomTestUtil.randomLong());

					throw new IllegalStateException();
				});

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertTrue(
				throwable.toString(),
				throwable instanceof IllegalStateException);
		}

		List<XMLSitemapRegenerationEntry> xmlSitemapRegenerationEntries =
			_getXMLSitemapRegenerationEntries(companyId);

		Assert.assertTrue(
			xmlSitemapRegenerationEntries.toString(),
			xmlSitemapRegenerationEntries.isEmpty());
	}

	@Test
	public void testAddXMLSitemapRegenerationEntryWithDistinctKeys()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		long groupId = RandomTestUtil.randomLong();

		String assetTypeKey1 = RandomTestUtil.randomString();
		String assetTypeKey2 = RandomTestUtil.randomString();

		_xmlSitemapRegenerationEntryLocalService.addXMLSitemapRegenerationEntry(
			assetTypeKey1, companyId, groupId);
		_xmlSitemapRegenerationEntryLocalService.addXMLSitemapRegenerationEntry(
			assetTypeKey2, companyId, groupId);

		List<XMLSitemapRegenerationEntry> xmlSitemapRegenerationEntries =
			_getXMLSitemapRegenerationEntries(companyId);

		Assert.assertEquals(
			xmlSitemapRegenerationEntries.toString(), 2,
			xmlSitemapRegenerationEntries.size());
	}

	@Test
	public void testCompanyModelListenerDeletesEntries() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		_xmlSitemapRegenerationEntryLocalService.addXMLSitemapRegenerationEntry(
			RandomTestUtil.randomString(), companyId,
			RandomTestUtil.randomLong());

		_companyModelListener.onBeforeRemove(
			_companyLocalService.getCompany(companyId));

		List<XMLSitemapRegenerationEntry> xmlSitemapRegenerationEntries =
			_getXMLSitemapRegenerationEntries(companyId);

		Assert.assertTrue(
			xmlSitemapRegenerationEntries.toString(),
			xmlSitemapRegenerationEntries.isEmpty());
	}

	@Test
	public void testSchedulerJobConfigurationDrainsEntries() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry1 =
			_xmlSitemapRegenerationEntryLocalService.
				addXMLSitemapRegenerationEntry(
					RandomTestUtil.randomString(), companyId,
					RandomTestUtil.randomLong());

		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry2 =
			_xmlSitemapRegenerationEntryLocalService.
				addXMLSitemapRegenerationEntry(
					RandomTestUtil.randomString(), companyId,
					RandomTestUtil.randomLong());

		UnsafeConsumer<Long, Exception> unsafeConsumer =
			_schedulerJobConfiguration.getCompanyJobExecutorUnsafeConsumer();

		unsafeConsumer.accept(companyId);

		Assert.assertNull(
			_xmlSitemapRegenerationEntryLocalService.
				fetchXMLSitemapRegenerationEntry(
					xmlSitemapRegenerationEntry1.
						getXmlSitemapRegenerationEntryId()));
		Assert.assertNull(
			_xmlSitemapRegenerationEntryLocalService.
				fetchXMLSitemapRegenerationEntry(
					xmlSitemapRegenerationEntry2.
						getXmlSitemapRegenerationEntryId()));
	}

	private List<XMLSitemapRegenerationEntry> _getXMLSitemapRegenerationEntries(
		long companyId) {

		return _xmlSitemapRegenerationEntryLocalService.
			getXMLSitemapRegenerationEntries(companyId);
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.internal.model.listener.CompanyModelListener"
	)
	private ModelListener<Company> _companyModelListener;

	@Inject(
		filter = "component.name=com.liferay.site.internal.scheduler.XMLSitemapRegenerationSchedulerJobConfiguration"
	)
	private SchedulerJobConfiguration _schedulerJobConfiguration;

	@Inject
	private XMLSitemapRegenerationEntryLocalService
		_xmlSitemapRegenerationEntryLocalService;

}