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

package com.liferay.segments.web.internal.field.customizer;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	property = {
		"segments.field.customizer.entity.name=Organization",
		"segments.field.customizer.entity.name=User",
		"segments.field.customizer.key=" + AssetCategorySegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=50"
	},
	service = SegmentsFieldCustomizer.class
)
public class AssetCategorySegmentsFieldCustomizer
	extends BaseSegmentsFieldCustomizer {

	public static final String KEY = "category";

	@Override
	public ClassedModel getClassedModel(String fieldValue) {
		return _getAssetCategory(fieldValue);
	}

	@Override
	public String getClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public String getFieldValueName(String fieldValue, Locale locale) {
		AssetCategory assetCategory = _getAssetCategory(fieldValue);

		if (assetCategory == null) {
			return fieldValue;
		}

		return assetCategory.getName();
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public Field.SelectEntity getSelectEntity(PortletRequest portletRequest) {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest, AssetCategory.class.getName(),
				PortletProvider.Action.BROWSE);

			if (portletURL == null) {
				return null;
			}

			portletURL.setParameter("mvcPath", "/view.jsp");
			portletURL.setParameter("singleSelect", Boolean.TRUE.toString());

			ArrayList<Long> vocabularyIds = new ArrayList<>();

			List<AssetVocabulary> assetVocabularies =
				_assetVocabularyLocalService.getAssetVocabularies(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				vocabularyIds.add(assetVocabulary.getVocabularyId());
			}

			portletURL.setParameter(
				"vocabularyIds", StringUtil.merge(vocabularyIds));

			portletURL.setParameter("eventName", "selectEntity");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return new Field.SelectEntity(
				"selectEntity",
				getSelectEntityTitle(
					_portal.getLocale(portletRequest),
					AssetCategory.class.getName()),
				portletURL.toString(), false);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get select entity", exception);
			}

			return null;
		}
	}

	private AssetCategory _getAssetCategory(String fieldValue) {
		long assetCategoryId = GetterUtil.getLong(fieldValue);

		if (assetCategoryId == 0) {
			return null;
		}

		return _assetCategoryLocalService.fetchAssetCategory(assetCategoryId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategorySegmentsFieldCustomizer.class);

	private static final List<String> _fieldNames = ListUtil.fromArray(
		"assetCategoryIds");

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private Portal _portal;

}