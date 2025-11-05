/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.util;

import com.liferay.content.dashboard.document.library.internal.constants.ContentDashboardConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mikel Lorza
 */
@Component(service = DDMFormValuesIndexer.class)
public class DDMFormValuesIndexerImpl implements DDMFormValuesIndexer {

	@Override
	public void index(Document document, DDMFormValues ddmFormValues) {
		long tiffImageLength = _getDDMFormFieldsValueValue(
			ddmFormValues, "TIFF_IMAGE_LENGTH");

		if (tiffImageLength <= 0) {
			return;
		}

		long tiffImageWidth = _getDDMFormFieldsValueValue(
			ddmFormValues, "TIFF_IMAGE_WIDTH");

		if (tiffImageWidth <= 0) {
			return;
		}

		String aspectRatio =
			ContentDashboardConstants.AspectRatio.SQUARE.toString();

		if (tiffImageLength > tiffImageWidth) {
			aspectRatio = ContentDashboardConstants.AspectRatio.TALL.toString();
		}
		else if (tiffImageLength < tiffImageWidth) {
			aspectRatio = ContentDashboardConstants.AspectRatio.WIDE.toString();
		}

		String resolution =
			ContentDashboardConstants.Resolution.LARGE.toString();

		if ((tiffImageLength <=
				ContentDashboardConstants.Resolution.SMALL.
					getEndLengthValue()) &&
			(tiffImageWidth <=
				ContentDashboardConstants.Resolution.SMALL.
					getEndWidthValue())) {

			resolution = ContentDashboardConstants.Resolution.SMALL.toString();
		}
		else if ((tiffImageLength <=
					ContentDashboardConstants.Resolution.MEDIUM.
						getEndLengthValue()) &&
				 (tiffImageWidth <=
					 ContentDashboardConstants.Resolution.MEDIUM.
						 getEndWidthValue())) {

			resolution = ContentDashboardConstants.Resolution.MEDIUM.toString();
		}

		document.addText("aspectRatio", aspectRatio);
		document.addText("resolution", resolution);
	}

	private long _getDDMFormFieldsValueValue(
		DDMFormValues ddmFormValues, String name) {

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValues.getDDMFormFieldValue(name, false);

		if (ddmFormFieldValue == null) {
			return 0;
		}

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return 0;
		}

		return GetterUtil.getLong(value.getString(LocaleUtil.ROOT));
	}

}