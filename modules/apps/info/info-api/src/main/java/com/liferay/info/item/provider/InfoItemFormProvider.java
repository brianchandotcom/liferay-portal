/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.provider;

import com.liferay.info.exception.NoSuchClassTypeException;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.form.InfoForm;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Jorge Ferrer
 */
public interface InfoItemFormProvider<T> {

	public InfoForm getInfoForm();

	public default InfoForm getInfoForm(long itemClassTypeId)
		throws NoSuchClassTypeException {

		return getInfoForm();
	}

	public default InfoForm getInfoForm(String formVariationKey)
		throws NoSuchFormVariationException {

		long itemClassTypeId = GetterUtil.getLong(formVariationKey);

		if (itemClassTypeId > 0) {
			try {
				return getInfoForm(itemClassTypeId);
			}
			catch (NoSuchClassTypeException noSuchClassTypeException) {
				throw new NoSuchFormVariationException(
					String.valueOf(noSuchClassTypeException.getClassTypeId()),
					noSuchClassTypeException.getCause());
			}
		}

		return getInfoForm();
	}

	public default InfoForm getInfoForm(String formVariationKey, long groupId)
		throws NoSuchFormVariationException {

		return getInfoForm(formVariationKey);
	}

	public default InfoForm getInfoForm(T t) {
		return getInfoForm();
	}

}