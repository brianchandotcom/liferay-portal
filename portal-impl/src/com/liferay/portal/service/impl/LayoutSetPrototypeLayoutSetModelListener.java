/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.LayoutSetPrototypeUtil;
import com.liferay.portal.kernel.service.persistence.LayoutSetUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sites.kernel.util.Sites;

/**
 * @author Raymond Augé
 */
public class LayoutSetPrototypeLayoutSetModelListener
	extends BaseModelListener<LayoutSet> {

	@Override
	public void onAfterUpdate(
		LayoutSet originalLayoutSet, LayoutSet layoutSet) {

		if (layoutSet == null) {
			return;
		}

		Group group = null;

		try {
			group = layoutSet.getGroup();

			if (!group.isLayoutSetPrototype()) {
				return;
			}
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return;
		}

		try {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					group.getClassPK());

			layoutSetPrototype.setModifiedDate(layoutSet.getModifiedDate());

			LayoutSetPrototypeUtil.update(layoutSetPrototype);

			UnicodeProperties settingsUnicodeProperties =
				layoutSet.getSettingsProperties();

			if ((settingsUnicodeProperties == null) ||
				!settingsUnicodeProperties.containsKey(
					Sites.MERGE_FAIL_COUNT)) {

				return;
			}

			int mergeFailCount = GetterUtil.getInteger(
				settingsUnicodeProperties.getProperty(Sites.MERGE_FAIL_COUNT));

			UnicodeProperties originalSettingsUnicodeProperties =
				originalLayoutSet.getSettingsProperties();

			int originalMergeFailCount = GetterUtil.getInteger(
				originalSettingsUnicodeProperties.getProperty(
					Sites.MERGE_FAIL_COUNT));

			if ((mergeFailCount == originalMergeFailCount) ||
				(mergeFailCount == 0)) {

				settingsUnicodeProperties.remove(Sites.MERGE_FAIL_COUNT);

				LayoutSetUtil.updateImpl(layoutSet);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeLayoutSetModelListener.class);

}