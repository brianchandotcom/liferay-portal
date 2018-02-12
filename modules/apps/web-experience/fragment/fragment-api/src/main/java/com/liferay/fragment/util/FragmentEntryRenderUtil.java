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

package com.liferay.fragment.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import org.jsoup.nodes.Element;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Pablo Molina
 */
public class FragmentEntryRenderUtil {

	public static FragmentEntryProcessorRegistry getService() {
		return _serviceTracker.getService();
	}

	public static String renderFragmentEntry(FragmentEntry fragmentEntry) {
		return renderFragmentEntry(
			fragmentEntry.getFragmentEntryId(), 0, fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs());
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, long fragmentEntryInstanceId) {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(fragmentEntryId);

		return renderFragmentEntry(
			fragmentEntryId, fragmentEntryInstanceId, fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs());
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, long fragmentEntryInstanceId, String css,
		String html, String js) {

		Element divElement = new Element("div");

		StringBundler sb = new StringBundler(4);

		sb.append("fragment-");
		sb.append(fragmentEntryId);
		sb.append("-");
		sb.append(fragmentEntryInstanceId);

		divElement.attr("id", sb.toString());

		divElement.prepend(html);

		Element styleElement = divElement.prependElement("style");

		styleElement.prepend(css);

		Element scriptElement = divElement.prependElement("script");

		scriptElement.prependText("(function() {" + js + ";}());");

		return divElement.toString();
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, String css, String html, String js) {

		return renderFragmentEntry(fragmentEntryId, 0, css, html, js);
	}

	public static String renderFragmentEntryLink(
			long fragmentEntryLinkId, long position)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry =
			getService();

		String html = fragmentEntryProcessorRegistry.processFragmentEntryHTML(
			fragmentEntryLink.getHtml(),
			JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues()));

		return renderFragmentEntry(
			fragmentEntryLink.getFragmentEntryId(), position,
			fragmentEntryLink.getCss(), html, fragmentEntryLink.getJs());
	}

	private static final ServiceTracker
		<FragmentEntryProcessorRegistry, FragmentEntryProcessorRegistry>
			_serviceTracker = ServiceTrackerFactory.open(
				FragmentEntryProcessorRegistry.class);

}