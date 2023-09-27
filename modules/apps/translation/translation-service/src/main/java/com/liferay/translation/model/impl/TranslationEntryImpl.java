/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.model.impl;

import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.translation.service.TranslationEntryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TranslationEntryImpl extends TranslationEntryBaseImpl {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
			long groupId, String className, long classPK, String content)
		throws PortalException {

		return TranslationEntryLocalServiceUtil.getInfoItemFieldValues(
			getGroupId(), getClassName(), getClassPK(), getContent());
	}

}