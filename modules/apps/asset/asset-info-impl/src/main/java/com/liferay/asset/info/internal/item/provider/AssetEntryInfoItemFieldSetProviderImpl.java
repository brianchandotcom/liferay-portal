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

package com.liferay.asset.info.internal.item.provider;

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetEntryInfoItemFieldSetProvider.class)
public class AssetEntryInfoItemFieldSetProviderImpl
	implements AssetEntryInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoFieldSet(String itemClassName) {
		InfoFieldSet infoFieldSet = new InfoFieldSet(
			InfoLocalizedValue.localize(getClass(), "asset"), "asset");

		infoFieldSet.add(_categoriesInfoField);

		infoFieldSet.add(_tagsInfoField);

		infoFieldSet.add(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName()));

		return infoFieldSet;
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
		AssetEntry assetEntry) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		infoFieldValues.add(
			new InfoFieldValue<>(
				_categoriesInfoField, () -> _getCategoryNames(assetEntry)));

		infoFieldValues.add(
			new InfoFieldValue<>(
				_tagsInfoField,
				() -> ListUtil.toString(
					assetEntry.getTags(), AssetTag.NAME_ACCESSOR)));

		infoFieldValues.addAll(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				AssetEntry.class.getName(), assetEntry));

		return infoFieldValues;
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
			String itemClassName, long itemClassPK)
		throws NoSuchInfoItemException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				itemClassName);

		try {
			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				itemClassName, itemClassPK);

			return getInfoFieldValues(assetEntry);
		}
		catch (NoSuchEntryException noSuchEntryException) {
			throw new NoSuchInfoItemException(
				StringBundler.concat(
					"Unable to get asset entry with class name ", itemClassName,
					" and class PK ", itemClassPK),
				noSuchEntryException);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private String _getCategoryNames(AssetEntry assetEntry) {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		return ListUtil.toString(
			assetEntry.getCategories(),
			new Accessor<AssetCategory, String>() {

				@Override
				public String get(AssetCategory assetCategory) {
					String title = assetCategory.getTitle(locale);

					if (Validator.isNull(title)) {
						return assetCategory.getName();
					}

					return title;
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<AssetCategory> getTypeClass() {
					return AssetCategory.class;
				}

			});
	}

	private final InfoField _categoriesInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "categories"), "categories");

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	private final InfoField _tagsInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "tags"), "tagNames");

}