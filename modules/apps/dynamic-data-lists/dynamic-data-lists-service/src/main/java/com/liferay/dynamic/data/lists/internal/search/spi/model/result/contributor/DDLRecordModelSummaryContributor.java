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

import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
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
			_getTitle(GetterUtil.getLong(document.get("recordSetId")), locale),
			document.get(
				locale,
				StringBundler.concat(
					Field.SNIPPET, StringPool.UNDERLINE, Field.DESCRIPTION),
				Field.DESCRIPTION));

		summary.setMaxContentLength(200);

		return summary;
	}

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	private String _getLanguageKey(DDLRecordSet ddlRecordSet) {
		if (ddlRecordSet.getScope() ==
				DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) {

			return "record-for-list-x";
		}

		return "form-record-for-form-x";
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		return resourceBundleLoader.loadResourceBundle(locale);
	}

	private String _getTitle(long ddlRecordSetId, Locale locale) {
		try {
			DDLRecordSet ddlRecordSet = ddlRecordSetLocalService.getRecordSet(
				ddlRecordSetId);

			String recordSetName = ddlRecordSet.getName(locale);

			return LanguageUtil.format(
				_getResourceBundle(locale), _getLanguageKey(ddlRecordSet),
				recordSetName, false);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordModelSummaryContributor.class);

}