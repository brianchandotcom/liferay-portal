/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.internal.search.spi.model.result.contributor;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
	service = ModelSummaryContributor.class
)
public class DDMFormInstanceRecordModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		long ddmFormInstanceId = GetterUtil.getLong(
			document.get("formInstanceId"));

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String description = document.get(
			locale, prefix + Field.DESCRIPTION, Field.DESCRIPTION);

		Summary summary = new Summary(
			_getTitle(ddmFormInstanceId, locale), description);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Reference
	protected DDMFormInstanceLocalService ddmFormInstanceLocalService;

	private ResourceBundle _getResourceBundle(Locale defaultLocale) {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		return portalResourceBundleLoader.loadResourceBundle(defaultLocale);
	}

	private String _getTitle(long ddmFormInstanceId, Locale locale) {
		try {
			DDMFormInstance ddmFormInstance =
				ddmFormInstanceLocalService.getFormInstance(ddmFormInstanceId);

			String ddmFormInstanceName = ddmFormInstance.getName(locale);

			return _language.format(
				_getResourceBundle(locale), "form-record-for-form-x",
				ddmFormInstanceName, false);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordModelSummaryContributor.class);

	@Reference
	private Language _language;

}