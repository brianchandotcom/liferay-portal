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

package com.liferay.fragment.internal.processor;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Collections;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = FragmentEntryProcessorRegistry.class)
public class FragmentEntryProcessorRegistryImpl
	implements FragmentEntryProcessorRegistry {

	@Override
	public void deleteFragmentEntryLinkData(
		FragmentEntryLink fragmentEntryLink) {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_serviceTrackerList) {

			fragmentEntryProcessor.deleteFragmentEntryLinkData(
				fragmentEntryLink);
		}
	}

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_serviceTrackerList) {

			JSONArray availableTagsJSONArray =
				fragmentEntryProcessor.getAvailableTagsJSONArray();

			if (availableTagsJSONArray == null) {
				continue;
			}

			for (int i = 0; i < availableTagsJSONArray.length(); i++) {
				jsonArray.put(availableTagsJSONArray.getJSONObject(i));
			}
		}

		return jsonArray;
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_serviceTrackerList) {

			JSONObject defaultEditableValuesJSONObject =
				fragmentEntryProcessor.getDefaultEditableValuesJSONObject(
					html, configuration);

			if (defaultEditableValuesJSONObject != null) {
				Class<?> clazz = fragmentEntryProcessor.getClass();

				jsonObject.put(
					clazz.getName(), defaultEditableValuesJSONObject);
			}
		}

		return jsonObject;
	}

	@Override
	public String processFragmentEntryLinkCSS(
			FragmentEntryLink fragmentEntryLink,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		String css = fragmentEntryLink.getCss();

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_serviceTrackerList) {

			css = fragmentEntryProcessor.processFragmentEntryLinkCSS(
				fragmentEntryLink, css, fragmentEntryProcessorContext);
		}

		return css;
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		String html = fragmentEntryLink.getHtml();

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_serviceTrackerList) {

			html = fragmentEntryProcessor.processFragmentEntryLinkHTML(
				fragmentEntryLink, html, fragmentEntryProcessorContext);
		}

		return html;
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_serviceTrackerList) {

			fragmentEntryProcessor.validateFragmentEntryHTML(
				html, configuration);
		}
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, FragmentEntryProcessor.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator(
					"fragment.entry.processor.priority")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	@Reference
	private JSONFactory _jsonFactory;

	private ServiceTrackerList<FragmentEntryProcessor, FragmentEntryProcessor>
		_serviceTrackerList;

}