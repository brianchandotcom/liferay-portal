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

package com.liferay.expando.web.internal.info.item.provider;

import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.expando.web.internal.info.field.reader.ExpandoInfoItemFieldReader;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(service = ExpandoInfoItemFieldSetProvider.class)
public class ExpandoInfoItemFieldSetProviderImpl
	implements ExpandoInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoFieldSet(String itemClassName) {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			unsafeConsumer -> {
				for (ExpandoInfoItemFieldReader expandoInfoItemFieldReader :
						_getExpandoFieldReaders(itemClassName)) {

					unsafeConsumer.accept(
						expandoInfoItemFieldReader.getInfoField());
				}
			}
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "custom-fields")
		).name(
			"expando"
		).build();
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
		String itemClassName, Object itemObject) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		for (ExpandoInfoItemFieldReader expandoInfoItemFieldReader :
				_getExpandoFieldReaders(itemClassName)) {

			InfoFieldValue<Object> infoFieldValue = new InfoFieldValue<>(
				expandoInfoItemFieldReader.getInfoField(),
				expandoInfoItemFieldReader.getValue(itemObject));

			infoFieldValues.add(infoFieldValue);
		}

		return infoFieldValues;
	}

	private List<ExpandoInfoItemFieldReader> _getExpandoFieldReaders(
		String itemClassName) {

		List<ExpandoInfoItemFieldReader> expandoInfoItemFieldReaders =
			new ArrayList<>();

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			CompanyThreadLocal.getCompanyId(), itemClassName, 0L);

		Enumeration<String> attributeNamesEnumeration =
			expandoBridge.getAttributeNames();

		while (attributeNamesEnumeration.hasMoreElements()) {
			String attributeName = attributeNamesEnumeration.nextElement();

			expandoInfoItemFieldReaders.add(
				new ExpandoInfoItemFieldReader(attributeName, expandoBridge));
		}

		return expandoInfoItemFieldReaders;
	}

}