/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.model.listener;

import com.liferay.fragment.internal.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.model.FragmentEntryVersionTable;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Georgel Pop
 */
@Component(service = ModelListener.class)
public class FragmentEntryVersionModelListener
	extends BaseModelListener<FragmentEntryVersion> {

	@Override
	public void onAfterCreate(FragmentEntryVersion fragmentEntryVersion)
		throws ModelListenerException {

		try {
			long ctCollectionId = fragmentEntryVersion.getCtCollectionId();
			long fragmentEntryId = fragmentEntryVersion.getFragmentEntryId();

			int versionCount = _fragmentEntryVersionPersistence.dslQueryCount(
				DSLQueryFactoryUtil.count(
				).from(
					FragmentEntryVersionTable.INSTANCE
				).where(
					FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(
						ctCollectionId
					).and(
						FragmentEntryVersionTable.INSTANCE.fragmentEntryId.eq(
							fragmentEntryId)
					)
				));

			if (versionCount <=
					FragmentConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX) {

				return;
			}

			List<FragmentEntryVersion> fragmentEntryVersions =
				_fragmentEntryVersionPersistence.dslQuery(
					DSLQueryFactoryUtil.select(
						FragmentEntryVersionTable.INSTANCE
					).from(
						FragmentEntryVersionTable.INSTANCE
					).where(
						FragmentEntryVersionTable.INSTANCE.ctCollectionId.eq(
							ctCollectionId
						).and(
							FragmentEntryVersionTable.INSTANCE.fragmentEntryId.
								eq(fragmentEntryId)
						)
					).orderBy(
						FragmentEntryVersionTable.INSTANCE.version.descending()
					).limit(
						FragmentConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX,
						versionCount
					));

			for (FragmentEntryVersion currentFragmentEntryVersion :
					fragmentEntryVersions) {

				_fragmentEntryVersionPersistence.remove(
					currentFragmentEntryVersion);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove old fragment entry versions for " +
						"fragment entry ID " +
							fragmentEntryVersion.getFragmentEntryId(),
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryVersionModelListener.class);

	@Reference
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

}