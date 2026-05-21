/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.model.listener;

import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Georgel Pop
 */
@Component(service = ModelListener.class)
public class FragmentEntryVersionModelListener
	extends BaseModelListener<FragmentEntryVersion> {

	public static final int MAX_VERSIONS = 10;

	@Override
	public void onAfterCreate(FragmentEntryVersion fragmentEntryVersion)
		throws ModelListenerException {

		try {
			long fragmentEntryId = fragmentEntryVersion.getFragmentEntryId();

			int versionCount =
				_fragmentEntryVersionPersistence.countByFragmentEntryId(
					fragmentEntryId);

			if (versionCount <= MAX_VERSIONS) {
				return;
			}

			OrderByComparator<FragmentEntryVersion> orderByComparator =
				OrderByComparatorFactoryUtil.create(
					"FragmentEntryVersion", "version", false);

			List<FragmentEntryVersion> fragmentEntryVersions =
				_fragmentEntryVersionPersistence.findByFragmentEntryId(
					fragmentEntryId, MAX_VERSIONS, versionCount,
					orderByComparator);

			for (FragmentEntryVersion currentFragmentEntryVersion :
					fragmentEntryVersions) {

				_fragmentEntryVersionPersistence.remove(
					currentFragmentEntryVersion);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to clean up old fragment entry versions for " +
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