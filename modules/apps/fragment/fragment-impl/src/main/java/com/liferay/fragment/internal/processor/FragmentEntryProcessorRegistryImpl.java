/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.processor;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.CSSFragmentEntryProcessor;
import com.liferay.fragment.processor.DocumentFragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryAutocompleteContributor;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.processor.FragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = FragmentEntryProcessorRegistry.class)
public class FragmentEntryProcessorRegistryImpl
	implements FragmentEntryProcessorRegistry {

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (FragmentEntryAutocompleteContributor
				fragmentEntryAutocompleteContributor :
					_fragmentEntryAutocompleteContributors) {

			JSONArray availableTagsJSONArray =
				fragmentEntryAutocompleteContributor.
					getAvailableTagsJSONArray();

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
	public JSONArray getDataAttributesJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_fragmentEntryProcessors) {

			JSONArray dataAttributesJSONArray =
				fragmentEntryProcessor.getDataAttributesJSONArray();

			if (dataAttributesJSONArray == null) {
				continue;
			}

			for (int i = 0; i < dataAttributesJSONArray.length(); i++) {
				jsonArray.put(dataAttributesJSONArray.getString(i));
			}
		}

		return jsonArray;
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_fragmentEntryProcessors) {

			JSONObject defaultEditableValuesJSONObject =
				fragmentEntryProcessor.getDefaultEditableValuesJSONObject(
					html, configuration);

			if ((defaultEditableValuesJSONObject != null) &&
				(defaultEditableValuesJSONObject.length() > 0)) {

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

		for (CSSFragmentEntryProcessor cssFragmentEntryProcessor :
				_cssFragmentEntryProcessors) {

			css = cssFragmentEntryProcessor.processFragmentEntryLinkCSS(
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
				_fragmentEntryProcessors) {

			html = fragmentEntryProcessor.processFragmentEntryLinkHTML(
				fragmentEntryLink, html, fragmentEntryProcessorContext);
		}

		Document document = _getDocument(html);

		for (DocumentFragmentEntryProcessor documentFragmentEntryProcessor :
				_documentFragmentEntryProcessors) {

			documentFragmentEntryProcessor.processFragmentEntryLinkHTML(
				fragmentEntryLink, document, fragmentEntryProcessorContext);
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		if (CompanyThreadLocal.isInitializingPortalInstance()) {
			return;
		}

		Set<String> validHTMLs = _validHTMLsThreadLocal.get();

		if (validHTMLs.contains(html)) {
			return;
		}

		for (FragmentEntryValidator fragmentEntryValidator :
				_fragmentEntryValidators) {

			fragmentEntryValidator.validateFragmentEntryHTML(
				html, configuration, LocaleUtil.getDefault());
		}

		validHTMLs.add(html);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_cssFragmentEntryProcessors = ServiceTrackerListFactory.open(
			bundleContext, CSSFragmentEntryProcessor.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"fragment.entry.processor.priority")));
		_documentFragmentEntryProcessors = ServiceTrackerListFactory.open(
			bundleContext, DocumentFragmentEntryProcessor.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"fragment.entry.processor.priority")));
		_fragmentEntryAutocompleteContributors = ServiceTrackerListFactory.open(
			bundleContext, FragmentEntryAutocompleteContributor.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"fragment.entry.processor.priority")));
		_fragmentEntryProcessors = ServiceTrackerListFactory.open(
			bundleContext, FragmentEntryProcessor.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"fragment.entry.processor.priority")));
		_fragmentEntryValidators = ServiceTrackerListFactory.open(
			bundleContext, FragmentEntryValidator.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"fragment.entry.processor.priority")));
	}

	@Deactivate
	protected void deactivate() {
		_cssFragmentEntryProcessors.close();
		_documentFragmentEntryProcessors.close();
		_fragmentEntryAutocompleteContributors.close();
		_fragmentEntryProcessors.close();
		_fragmentEntryValidators.close();
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private static final ThreadLocal<Set<String>> _validHTMLsThreadLocal =
		new CentralizedThreadLocal(
			FragmentEntryProcessorRegistryImpl.class.getName() +
				"._validHTMLsThreadLocal",
			HashSet::new);

	private ServiceTrackerList<CSSFragmentEntryProcessor>
		_cssFragmentEntryProcessors;
	private ServiceTrackerList<DocumentFragmentEntryProcessor>
		_documentFragmentEntryProcessors;
	private ServiceTrackerList<FragmentEntryAutocompleteContributor>
		_fragmentEntryAutocompleteContributors;
	private ServiceTrackerList<FragmentEntryProcessor> _fragmentEntryProcessors;
	private ServiceTrackerList<FragmentEntryValidator> _fragmentEntryValidators;

	@Reference
	private JSONFactory _jsonFactory;

}