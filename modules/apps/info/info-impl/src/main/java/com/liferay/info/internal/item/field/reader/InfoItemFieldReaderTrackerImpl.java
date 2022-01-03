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

package com.liferay.info.internal.item.field.reader;

import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.item.field.reader.InfoItemFieldReader;
import com.liferay.info.item.field.reader.InfoItemFieldReaderTracker;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFieldReaderTracker.class)
public class InfoItemFieldReaderTrackerImpl
	implements InfoItemFieldReaderTracker {

	@Override
	public List<InfoItemFieldReader> getInfoItemFieldReaders(
		String itemClassName) {

		List<InfoItemFieldReader> infoItemFieldReaders =
			_itemInfoItemFieldReaderServiceTrackerMap.getService(itemClassName);

		if (infoItemFieldReaders == null) {
			infoItemFieldReaders = Collections.emptyList();
		}

		List<InfoItemFieldReader> infoItemFieldReaderWrappers =
			_infoItemFieldReaderWrappersMap.get(itemClassName);

		if (infoItemFieldReaderWrappers == null) {
			infoItemFieldReaderWrappers = Collections.emptyList();
		}

		return ListUtil.concat(
			infoItemFieldReaders, infoItemFieldReaderWrappers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_itemInfoItemFieldReaderServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, InfoItemFieldReader.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(infoItemFieldReader, emitter) -> emitter.emit(
						GenericUtil.getGenericClassName(infoItemFieldReader))),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"info.item.field.order")));
	}

	@Deactivate
	protected void deactivate() {
		_itemInfoItemFieldReaderServiceTrackerMap.close();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoItemFieldReaderWrapper(
		InfoDisplayContributorField<Object> infoDisplayContributorField,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		if (Validator.isNull(className)) {
			return;
		}

		List<InfoItemFieldReader> infoItemFieldReaderWrappers =
			_infoItemFieldReaderWrappersMap.computeIfAbsent(
				className, itemClass -> new ArrayList<>());

		infoItemFieldReaderWrappers.add(
			new InfoItemFieldReaderWrapper(infoDisplayContributorField));
	}

	protected void unsetInfoItemFieldReaderWrapper(
		InfoDisplayContributorField<?> infoDisplayContributorField,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		if (Validator.isNull(className)) {
			return;
		}

		List<InfoItemFieldReader> infoItemFieldReaderWrappers =
			_infoItemFieldReaderWrappersMap.get(className);

		if (infoItemFieldReaderWrappers != null) {
			for (InfoItemFieldReader infoItemFieldReader :
					infoItemFieldReaderWrappers) {

				InfoItemFieldReaderWrapper infoItemFieldReaderWrapper =
					(InfoItemFieldReaderWrapper)infoItemFieldReader;

				InfoDisplayContributorField<?>
					existingInfoDisplayContributorField =
						infoItemFieldReaderWrapper.
							getInfoDisplayContributorField();

				if (existingInfoDisplayContributorField ==
						infoDisplayContributorField) {

					infoItemFieldReaderWrappers.remove(
						infoDisplayContributorField);
				}
			}
		}
	}

	private final Map<String, List<InfoItemFieldReader>>
		_infoItemFieldReaderWrappersMap = new ConcurrentHashMap<>();
	private ServiceTrackerMap<String, List<InfoItemFieldReader>>
		_itemInfoItemFieldReaderServiceTrackerMap;

	private class InfoItemFieldReaderWrapper implements InfoItemFieldReader {

		public InfoItemFieldReaderWrapper(
			InfoDisplayContributorField<Object> infoDisplayContributorField) {

			_infoDisplayContributorField = infoDisplayContributorField;
		}

		public InfoDisplayContributorField<?> getInfoDisplayContributorField() {
			return _infoDisplayContributorField;
		}

		@Override
		public InfoField<?> getInfoField() {
			InfoFieldType infoFieldType = TextInfoFieldType.INSTANCE;

			InfoDisplayContributorFieldType infoDisplayContributorFieldType =
				_infoDisplayContributorField.getType();

			if (infoDisplayContributorFieldType ==
					InfoDisplayContributorFieldType.IMAGE) {

				infoFieldType = ImageInfoFieldType.INSTANCE;
			}
			else if (infoDisplayContributorFieldType ==
						InfoDisplayContributorFieldType.URL) {

				infoFieldType = URLInfoFieldType.INSTANCE;
			}

			return InfoField.builder(
			).infoFieldType(
				infoFieldType
			).name(
				getKey()
			).labelInfoLocalizedValue(
				(InfoLocalizedValue<String>)InfoLocalizedValue.function(
					locale -> _infoDisplayContributorField.getLabel(locale))
			).build();
		}

		@Override
		public String getKey() {
			return _infoDisplayContributorField.getKey();
		}

		@Override
		public Object getValue(Object model) {
			return InfoLocalizedValue.function(
				locale -> _infoDisplayContributorField.getValue(model, locale));
		}

		private final InfoDisplayContributorField<Object>
			_infoDisplayContributorField;

	}

}