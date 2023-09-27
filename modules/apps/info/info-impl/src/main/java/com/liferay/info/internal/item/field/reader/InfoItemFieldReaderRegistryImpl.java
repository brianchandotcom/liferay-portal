/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.info.item.field.reader.InfoItemFieldReaderRegistry;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFieldReaderRegistry.class)
public class InfoItemFieldReaderRegistryImpl
	implements InfoItemFieldReaderRegistry {

	@Override
	public List<InfoItemFieldReader> getInfoItemFieldReaders(
		String itemClassName) {

		List<InfoItemFieldReader> infoItemFieldReaders =
			_itemInfoItemFieldReaderServiceTrackerMap.getService(itemClassName);

		if (infoItemFieldReaders == null) {
			infoItemFieldReaders = Collections.emptyList();
		}

		List<InfoDisplayContributorField<?>> infoDisplayContributorFields =
			_infoDisplayContributorFieldServiceTrackerMap.getService(
				itemClassName);

		if (infoDisplayContributorFields != null) {
			for (InfoDisplayContributorField<?> infoDisplayContributorField :
					infoDisplayContributorFields) {

				infoItemFieldReaders.add(
					new InfoItemFieldReaderWrapper(
						infoDisplayContributorField));
			}
		}

		return infoItemFieldReaders;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoDisplayContributorFieldServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext,
				(Class<InfoDisplayContributorField<?>>)
					(Class<?>)InfoDisplayContributorField.class,
				"model.class.name");
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
		_infoDisplayContributorFieldServiceTrackerMap.close();

		_itemInfoItemFieldReaderServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<InfoDisplayContributorField<?>>>
		_infoDisplayContributorFieldServiceTrackerMap;
	private ServiceTrackerMap<String, List<InfoItemFieldReader>>
		_itemInfoItemFieldReaderServiceTrackerMap;

	private class InfoItemFieldReaderWrapper<T>
		implements InfoItemFieldReader<T> {

		public InfoItemFieldReaderWrapper(
			InfoDisplayContributorField<T> infoDisplayContributorField) {

			_infoDisplayContributorField = infoDisplayContributorField;
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
			).namespace(
				InfoDisplayContributorField.class.getSimpleName()
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
		public Object getValue(T model) {
			return InfoLocalizedValue.function(
				locale -> _infoDisplayContributorField.getValue(model, locale));
		}

		private final InfoDisplayContributorField<T>
			_infoDisplayContributorField;

	}

}