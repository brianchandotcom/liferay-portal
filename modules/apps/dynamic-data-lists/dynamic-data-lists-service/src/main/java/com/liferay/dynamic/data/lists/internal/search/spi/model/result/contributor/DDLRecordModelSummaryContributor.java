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

package com.liferay.dynamic.data.lists.internal.search.spi.model.result.contributor;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord",
	service = ModelSummaryContributor.class
)
public class DDLRecordModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Summary summary = new Summary(
			getTitle(GetterUtil.getLong(document.get("recordSetId")), locale),
			document.get(
				locale,
				Field.SNIPPET.concat(
					StringPool.UNDERLINE
				).concat(
					Field.DESCRIPTION
				),
				Field.DESCRIPTION));

		summary.setMaxContentLength(200);

		return summary;
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		return resourceBundleLoader.loadResourceBundle(locale);
	}

	protected String getTitle(long ddlRecordSetId, Locale locale) {
		try {
			DDLRecordSet ddlRecordSet = ddlRecordSetLocalService.getRecordSet(
				ddlRecordSetId);

			String recordSetName = ddlRecordSet.getName(locale);

			return LanguageUtil.format(
				getResourceBundle(locale), "new-entry-for-form-x",
				recordSetName, false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return StringPool.BLANK;
	}

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordModelSummaryContributor.class);

}