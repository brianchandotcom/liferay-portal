/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformerContentType;
import com.liferay.adaptive.media.content.transformer.ContentTransformerHandler;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Transforms the content by invoking the {@link ContentTransformer} available
 * for a specific {@link ContentTransformerContentType}. There can be more than
 * one content transformer available for a particular content type, and they
 * will all be executed, but the order is not guaranteed.
 *
 * @author Alejandro Tardín
 */
@Component(service = ContentTransformerHandler.class)
public class ContentTransformerHandlerImpl
	implements ContentTransformerHandler {

	@Override
	public <T> T transform(
		ContentTransformerContentType<T> contentTransformerContentType,
		T originalContent) {

		List<ContentTransformer<?>> contentTransformers =
			_serviceTrackerMap.getService(contentTransformerContentType);

		if (contentTransformers == null) {
			return originalContent;
		}

		T transformedContent = originalContent;

		for (ContentTransformer<?> curContentTransformer :
				contentTransformers) {

			try {
				ContentTransformer<T> contentTransformer =
					(ContentTransformer<T>)curContentTransformer;

				transformedContent = contentTransformer.transform(
					transformedContent);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		return transformedContent;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext,
			(Class<ContentTransformer<?>>)(Class<?>)ContentTransformer.class,
			null,
			(serviceReference, emitter) -> {
				ContentTransformer<?> contentTransformer =
					bundleContext.getService(serviceReference);

				emitter.emit(
					contentTransformer.getContentTransformerContentType());

				bundleContext.ungetService(serviceReference);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected final void setServiceTrackerMap(
		ServiceTrackerMap
			<ContentTransformerContentType<?>, List<ContentTransformer<?>>>
				serviceTrackerMap) {

		_serviceTrackerMap = serviceTrackerMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentTransformerHandlerImpl.class);

	private ServiceTrackerMap
		<ContentTransformerContentType<?>, List<ContentTransformer<?>>>
			_serviceTrackerMap;

}