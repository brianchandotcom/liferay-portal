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

package com.liferay.fragment.entry.processor.resources;

import com.liferay.fragment.entry.processor.resources.util.ResourcesFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=4",
	service = FragmentEntryProcessor.class
)
public class ResourcesFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public String processFragmentEntryLinkCSS(
			FragmentEntryLink fragmentEntryLink, String css,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		return ResourcesFragmentEntryProcessorUtil.processResources(
			fragmentEntryLink, css);
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		return ResourcesFragmentEntryProcessorUtil.processResources(
			fragmentEntryLink, html);
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration) {
	}

}