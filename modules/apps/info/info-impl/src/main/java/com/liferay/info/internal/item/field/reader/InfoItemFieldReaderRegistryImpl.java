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
import com.liferay.info.item.field.reader.InfoItemFieldReaderRegistry;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

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

		List<InfoItemFieldReader> infoItemFieldReaderWrappers =
			_infoItemFieldReaderWrapperServiceTrackerMap.getService(
				itemClassName);

		if (infoItemFieldReaderWrappers == null) {
			infoItemFieldReaderWrappers = Collections.emptyList();
		}

		return ListUtil.concat(
			infoItemFieldReaders, infoItemFieldReaderWrappers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoItemFieldReaderWrapperServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext,
				(Class<InfoDisplayContributorField<Object>>)
					(Class<?>)InfoDisplayContributorField.class,
				"model.class.name",
				new ServiceTrackerCustomizer
					<InfoDisplayContributorField<Object>,
					 InfoItemFieldReader>() {

					@Override
					public InfoItemFieldReader addingService(
						ServiceReference<InfoDisplayContributorField<Object>>
							serviceReference) {

						return new InfoItemFieldReaderWrapper(
							bundleContext.getService(serviceReference));
					}

					@Override
					public void modifiedService(
						ServiceReference<InfoDisplayContributorField<Object>>
							serviceReference,
						InfoItemFieldReader infoItemFieldReader) {
					}

					@Override
					public void removedService(
						ServiceReference<InfoDisplayContributorField<Object>>
							serviceReference,
						InfoItemFieldReader infoItemFieldReader) {

						bundleContext.ungetService(serviceReference);
					}

				});
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
		_infoItemFieldReaderWrapperServiceTrackerMap.close();

		_itemInfoItemFieldReaderServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<InfoItemFieldReader>>
		_infoItemFieldReaderWrapperServiceTrackerMap;
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
		public Object getValue(Object model) {
			return InfoLocalizedValue.function(
				locale -> _infoDisplayContributorField.getValue(model, locale));
		}

		private final InfoDisplayContributorField<Object>
			_infoDisplayContributorField;

	}

}