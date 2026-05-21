/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.fragment.internal.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.model.FragmentEntryVersionTable;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = CTEventListener.class)
public class FragmentEntryVersionCTEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		List<CTEntry> ctEntries = _ctEntryLocalService.getCTEntries(
			ctCollectionId, _portal.getClassNameId(FragmentEntryVersion.class));

		if (ctEntries.isEmpty()) {
			return;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try {
					TransactionInvokerUtil.invoke(
						_transactionConfig,
						() -> _removeFragmentEntryVersions(ctEntries));
				}
				catch (Throwable throwable) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to remove old fragment entry versions ",
								"after publishing change tracking collection ",
								"ID ", ctCollectionId),
							throwable);
					}
				}

				return null;
			});
	}

	private Void _removeFragmentEntryVersions(List<CTEntry> ctEntries) {
		List<Long> fragmentEntryIds = _fragmentEntryVersionPersistence.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				FragmentEntryVersionTable.INSTANCE.fragmentEntryId
			).from(
				FragmentEntryVersionTable.INSTANCE
			).where(
				FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(
					CTConstants.CT_COLLECTION_ID_PRODUCTION
				).and(
					FragmentEntryVersionTable.INSTANCE.fragmentEntryVersionId.
						in(
							TransformUtil.transformToArray(
								ctEntries, CTEntry::getModelClassPK,
								Long.class))
				)
			));

		for (long fragmentEntryId : fragmentEntryIds) {
			_removeFragmentEntryVersions(fragmentEntryId);
		}

		return null;
	}

	private void _removeFragmentEntryVersions(long fragmentEntryId) {
		try {
			List<FragmentEntryVersion> fragmentEntryVersions =
				_fragmentEntryVersionPersistence.dslQuery(
					DSLQueryFactoryUtil.select(
						FragmentEntryVersionTable.INSTANCE
					).from(
						FragmentEntryVersionTable.INSTANCE
					).where(
						FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(
							CTConstants.CT_COLLECTION_ID_PRODUCTION
						).and(
							FragmentEntryVersionTable.INSTANCE.fragmentEntryId.
								eq(fragmentEntryId)
						)
					).orderBy(
						FragmentEntryVersionTable.INSTANCE.version.descending()
					).limit(
						FragmentConstants.MAX_FRAGMENT_ENTRY_VERSION_COUNT,
						Integer.MAX_VALUE
					));

			for (FragmentEntryVersion fragmentEntryVersion :
					fragmentEntryVersions) {

				_fragmentEntryVersionPersistence.remove(fragmentEntryVersion);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove old fragment entry versions for " +
						"fragment entry ID " + fragmentEntryId,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryVersionCTEventListener.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

	@Reference
	private Portal _portal;

}