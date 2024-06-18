/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.RepeatableInfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.RepeatableInfoFieldValueInfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"info.item.identifier=com.liferay.info.item.RepeatableInfoFieldValueInfoItemIdentifier",
		"service.ranking:Integer=100"
	},
	service = InfoItemObjectProvider.class
)
public class RepeatableInfoFieldValueInfoItemObjectProvider
	implements InfoItemObjectProvider<RepeatableInfoFieldValue> {

	@Override
	public RepeatableInfoFieldValue getInfoItem(
			InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof
				RepeatableInfoFieldValueInfoItemIdentifier)) {

			throw new NoSuchInfoItemException(
				"Unsupported info item identifier type " + infoItemIdentifier);
		}

		RepeatableInfoFieldValueInfoItemIdentifier
			repeatableInfoFieldValueInfoItemIdentifier =
				(RepeatableInfoFieldValueInfoItemIdentifier)infoItemIdentifier;

		InfoItemReference objectInfoItemReference =
			repeatableInfoFieldValueInfoItemIdentifier.
				getObjectInfoItemReference();

		InfoItemIdentifier objectInfoItemIdentifier =
			objectInfoItemReference.getInfoItemIdentifier();

		InfoItemObjectProvider<?> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class,
				objectInfoItemReference.getClassName(),
				objectInfoItemIdentifier.getInfoItemServiceFilter());

		Object infoItem = infoItemObjectProvider.getInfoItem(
			objectInfoItemIdentifier);

		if (infoItem == null) {
			throw new NoSuchInfoItemException(
				"Unable to get info item with info item identifier " +
					infoItemIdentifier);
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				objectInfoItemReference.getClassName());

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(infoItem);

		List<InfoFieldValue<Object>> finalInfoFieldValues = new ArrayList<>();

		for (String fieldName :
				repeatableInfoFieldValueInfoItemIdentifier.getFieldNames()) {

			List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>(
				infoItemFieldValues.getInfoFieldValues(fieldName));

			finalInfoFieldValues.add(
				infoFieldValues.get(
					repeatableInfoFieldValueInfoItemIdentifier.
						getIterationNumber()));
		}

		return new RepeatableInfoFieldValue(
			repeatableInfoFieldValueInfoItemIdentifier.
				getObjectInfoItemReference(),
			finalInfoFieldValues);
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}