/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.store.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.change.tracking.store.service.CTSContentLocalService;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class CTStoreCTEventListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testOnAfterPublish() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CTSettingsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"cleanUpCTSContentData", true
						).build())) {

			CTCollection ctCollection1 =
				_ctCollectionLocalService.addCTCollection(
					null, TestPropsValues.getCompanyId(),
					TestPropsValues.getUserId(), 0, StringUtil.randomString(),
					null);

			DLFolder dlFolder = DLTestUtil.addDLFolder(
				TestPropsValues.getGroupId());

			FileEntry fileEntry = null;

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollection1.getCtCollectionId())) {

				fileEntry = _dlAppLocalService.addFileEntry(
					null, TestPropsValues.getUserId(),
					dlFolder.getRepositoryId(), dlFolder.getFolderId(),
					RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
					RandomTestUtil.randomBytes(), null, null, null,
					ServiceContextTestUtil.getServiceContext());
			}

			long[] ctsContentIds1 = _getCTSContentIds(
				ctCollection1.getCtCollectionId());

			_assertCTSContents(
				ctCollection1.getCtCollectionId(), ctsContentIds1);

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection1.getCtCollectionId());

			_assertNoCTSContents(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, ctsContentIds1);
			_assertNoCTSContents(
				ctCollection1.getCtCollectionId(), ctsContentIds1);

			CTCollection ctCollection2 =
				_ctCollectionLocalService.addCTCollection(
					null, TestPropsValues.getCompanyId(),
					TestPropsValues.getUserId(), 0, StringUtil.randomString(),
					null);

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollection2.getCtCollectionId())) {

				fileEntry = _dlAppLocalService.updateFileEntry(
					TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
					RandomTestUtil.randomString(), fileEntry.getMimeType(),
					fileEntry.getTitle(), null, fileEntry.getDescription(),
					null, null, RandomTestUtil.randomBytes(), null, null, null,
					ServiceContextTestUtil.getServiceContext());
			}

			long[] ctsContentIds2 = _getCTSContentIds(
				ctCollection2.getCtCollectionId());

			_assertCTSContents(
				ctCollection2.getCtCollectionId(), ctsContentIds2);

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection2.getCtCollectionId());

			_assertNoCTSContents(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, ctsContentIds2);
			_assertNoCTSContents(
				ctCollection1.getCtCollectionId(), ctsContentIds2);

			CTCollection ctCollection3 =
				_ctCollectionLocalService.addCTCollection(
					null, TestPropsValues.getCompanyId(),
					TestPropsValues.getUserId(), 0, StringUtil.randomString(),
					null);

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollection3.getCtCollectionId())) {

				_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());
			}

			long[] ctsContentIds3 = _getCTSContentIds(
				ctCollection3.getCtCollectionId());

			_assertCTSContents(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, ctsContentIds3);

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection3.getCtCollectionId());

			_assertCTSContents(
				ctCollection3.getCtCollectionId(), ctsContentIds3);
			_assertNoCTSContents(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, ctsContentIds3);

			Assert.assertNull(
				_dlAppLocalService.fetchFileEntry(fileEntry.getFileEntryId()));

			CTCollection ctCollection4 =
				_ctCollectionLocalService.undoCTCollection(
					ctCollection3.getCtCollectionId(),
					TestPropsValues.getUserId(), RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection4.getCtCollectionId());

			Assert.assertNotNull(
				_dlAppLocalService.fetchFileEntry(fileEntry.getFileEntryId()));

			CTCollection ctCollection5 =
				_ctCollectionLocalService.undoCTCollection(
					ctCollection4.getCtCollectionId(),
					TestPropsValues.getUserId(), RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection5.getCtCollectionId());

			Assert.assertNull(
				_dlAppLocalService.fetchFileEntry(fileEntry.getFileEntryId()));
		}
	}

	private void _assertCTSContents(long ctCollectionId, long[] ctsContentIds) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId)) {

			for (long ctContentId : ctsContentIds) {
				Assert.assertNotNull(
					_ctsContentLocalService.fetchCTSContent(ctContentId));
			}
		}
	}

	private void _assertNoCTSContents(
		long ctCollectionId, long[] ctsContentIds) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId)) {

			for (long ctContentId : ctsContentIds) {
				Assert.assertNull(
					_ctsContentLocalService.fetchCTSContent(ctContentId));
			}
		}
	}

	private long[] _getCTSContentIds(long ctCollectionId) {
		return TransformUtil.transformToLongArray(
			_ctEntryLocalService.getCTEntries(
				ctCollectionId,
				_portal.getClassNameId(CTSContent.class.getName())),
			CTEntry::getModelClassPK);
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTCollectionService _ctCollectionService;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@Inject
	private CTSContentLocalService _ctsContentLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private Portal _portal;

}