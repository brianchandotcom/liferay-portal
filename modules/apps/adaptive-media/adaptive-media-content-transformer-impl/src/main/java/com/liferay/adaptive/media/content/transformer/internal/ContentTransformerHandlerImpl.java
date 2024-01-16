/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformerHandler;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Transforms the content by invoking all available {@link ContentTransformer}.
 * There can be more than one content transformer available, and they will all
 * be executed, but the order is not guaranteed.
 *
 * @author Alejandro Tardín
 */
@Component(service = ContentTransformerHandler.class)
public class ContentTransformerHandlerImpl
	implements ContentTransformerHandler {

	@Override
	public String transform(String originalContent) {
		String transformedContent = originalContent;

		try {
			transformedContent = _htmlContentTransformer.transform(
				originalContent);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		for (ContentTransformer contentTransformer : _serviceTrackerList) {
			try {
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
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, ContentTransformer.class);

		_htmlContentTransformer = new HtmlContentTransformerImpl(
			_amImageHTMLTagFactory, _amImageMimeTypeProvider,
			_dlAppLocalService);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	protected final void setServiceTrackerList(
		ServiceTrackerList<ContentTransformer> serviceTrackerList) {

		_serviceTrackerList = serviceTrackerList;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentTransformerHandlerImpl.class);

	@Reference
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	private ContentTransformer _htmlContentTransformer;
	private ServiceTrackerList<ContentTransformer> _serviceTrackerList;

}