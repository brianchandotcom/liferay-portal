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

package com.liferay.journal.web.internal.asset.display.contributor;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.BaseAssetDisplayContributor;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.asset.JournalArticleDDMFormValuesReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetDisplayContributor.class)
public class JournalArticleAssetDisplayContributor
	extends BaseAssetDisplayContributor<JournalArticle>
	implements AssetDisplayContributor {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	protected String[] getAssetEntryModelFields() {
		return null;
	}

	@Override
	protected Map<String, Object> getClassTypeValues(
		JournalArticle article, Locale locale) {

		Map<String, Object> classTypeValues = new HashMap<>();

		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			_fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(
			_journalConverter);

		try {
			DDMFormValues ddmFormValues =
				journalArticleDDMFormValuesReader.getDDMFormValues();

			Map<String, List<DDMFormFieldValue>> ddmFormFieldsValuesMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			for (Map.Entry<String, List<DDMFormFieldValue>>
					entry: ddmFormFieldsValuesMap.entrySet()) {

				List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

				DDMFormFieldValue ddmFormFieldValue0 = ddmFormFieldValues.get(
					0);

				Value value = ddmFormFieldValue0.getValue();

				classTypeValues.put(entry.getKey(), value.getString(locale));
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return classTypeValues;
	}

	@Override
	protected Object getFieldValue(
		JournalArticle article, String field, Locale locale) {

		return StringPool.BLANK;
	}

	@Override
	@Reference(
		target = "(bundle.symbolic.name=com.liferay.journal.web)", unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		this.resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAssetDisplayContributor.class);

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

}