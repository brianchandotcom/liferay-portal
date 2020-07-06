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

package com.liferay.html.preview.internal.processor;

import com.liferay.html.preview.processor.HtmlPreviewProcessor;
import com.liferay.html.preview.processor.HtmlPreviewProcessorTracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = HtmlPreviewProcessorTracker.class)
public class HtmlPreviewProcessorTrackerImpl
	implements HtmlPreviewProcessorTracker {

	@Override
	public HtmlPreviewProcessor getHtmlPreviewProcessor(String mimeType) {
		return _htmlPreviewProcessors.get(mimeType);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setHtmlPreviewProcessor(
		HtmlPreviewProcessor htmlPreviewProcessor) {

		_htmlPreviewProcessors.put(
			htmlPreviewProcessor.getMimeType(), htmlPreviewProcessor);
	}

	protected void unsetHtmlPreviewProcessor(
		HtmlPreviewProcessor htmlPreviewProcessor) {

		_htmlPreviewProcessors.remove(htmlPreviewProcessor.getMimeType());
	}

	private final Map<String, HtmlPreviewProcessor> _htmlPreviewProcessors =
		new ConcurrentHashMap<>();

}