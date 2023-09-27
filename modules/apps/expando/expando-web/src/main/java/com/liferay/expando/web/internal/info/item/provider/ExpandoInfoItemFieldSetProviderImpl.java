/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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