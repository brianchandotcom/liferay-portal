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

package com.liferay.fragment.contributor;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseFragmentCollectionContributor
	implements FragmentCollectionContributor {

	@Override
	public List<FragmentEntry> getFragmentEntries() {
		_initialize();

		List<FragmentEntry> fragmentEntries = new ArrayList<>();

		for (Map.Entry<Integer, List<FragmentEntry>> entry :
				_fragmentEntries.entrySet()) {

			fragmentEntries.addAll(entry.getValue());
		}

		return fragmentEntries;
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(int type) {
		_initialize();

		return _fragmentEntries.getOrDefault(type, Collections.emptyList());
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(int type, Locale locale) {
		return _getFragmentEntries(getFragmentEntries(type), locale);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(Locale locale) {
		return _getFragmentEntries(getFragmentEntries(), locale);
	}

	@Override
	public String getName() {
		_initialize();

		return _names.get(LocaleUtil.getDefault());
	}

	@Override
	public String getName(Locale locale) {
		_initialize();

		String name = _names.get(locale);

		if (Validator.isNotNull(name)) {
			return name;
		}

		return getName();
	}

	@Override
	public Map<Locale, String> getNames() {
		_initialize();

		if (_names != null) {
			return Collections.unmodifiableMap(_names);
		}

		return Collections.emptyMap();
	}

	@Override
	public ResourceBundleLoader getResourceBundleLoader() {
		ServletContext servletContext = getServletContext();

		return Optional.ofNullable(
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByServletContextName(
					servletContext.getServletContextName())
		).orElse(
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader()
		);
	}

	public abstract ServletContext getServletContext();

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	protected void readAndCheckFragmentCollectionStructure() {
		try {
			Map<Locale, String> names = _getContributedCollectionNames();

			Enumeration<URL> enumeration = _bundle.findEntries(
				StringPool.BLANK,
				FragmentExportImportConstants.FILE_NAME_FRAGMENT_CONFIG, true);

			if (MapUtil.isEmpty(names) || !enumeration.hasMoreElements()) {
				return;
			}

			_fragmentEntries = new HashMap<>();
			_fragmentEntryNames = new HashMap<>();
			_names = names;

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				FragmentEntry fragmentEntry = _getFragmentEntry(url);

				List<FragmentEntry> fragmentEntryList =
					_fragmentEntries.computeIfAbsent(
						fragmentEntry.getType(), type -> new ArrayList<>());

				fragmentEntryList.add(fragmentEntry);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	protected FragmentEntryLinkLocalService fragmentEntryLinkLocalService;

	@Reference
	protected FragmentEntryLocalService fragmentEntryLocalService;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	protected FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	protected FragmentEntryValidator fragmentEntryValidator;

	private Map<Locale, String> _getContributedCollectionNames()
		throws Exception {

		Class<?> clazz = getClass();

		String json = StreamUtil.toString(
			clazz.getResourceAsStream(
				"dependencies/" +
					FragmentExportImportConstants.FILE_NAME_COLLECTION_CONFIG));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		String name = jsonObject.getString("name");

		Map<Locale, String> names = new HashMap<>();

		_setLocalizedNames(name, names, getResourceBundleLoader());

		return names;
	}

	private List<FragmentEntry> _getFragmentEntries(
		List<FragmentEntry> fragmentEntries, Locale locale) {

		Stream<FragmentEntry> stream = fragmentEntries.stream();

		return stream.map(
			fragmentEntry -> {
				Map<Locale, String> names = _fragmentEntryNames.getOrDefault(
					fragmentEntry.getFragmentEntryKey(),
					Collections.emptyMap());

				fragmentEntry.setName(
					names.getOrDefault(
						locale,
						names.getOrDefault(
							LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
							fragmentEntry.getName())));

				return fragmentEntry;
			}
		).collect(
			Collectors.toList()
		);
	}

	private FragmentEntry _getFragmentEntry(URL url) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			StreamUtil.toString(url.openStream()));

		String fragmentEntryKey = StringBundler.concat(
			getFragmentCollectionKey(), StringPool.DASH,
			jsonObject.getString("fragmentEntryKey"));

		Map<Locale, String> names = _fragmentEntryNames.getOrDefault(
			fragmentEntryKey, new HashMap<>());

		String name = jsonObject.getString("name");

		_setLocalizedNames(name, names, getResourceBundleLoader());

		_fragmentEntryNames.put(fragmentEntryKey, names);

		String path = FileUtil.getPath(url.getPath());

		String css = _read(path, jsonObject.getString("cssPath"), "index.css");
		String html = _read(
			path, jsonObject.getString("htmlPath"), "index.html");
		String js = _read(path, jsonObject.getString("jsPath"), "index.js");
		String configuration = _read(
			path, jsonObject.getString("configurationPath"), "index.json");

		String thumbnailURL = _getImagePreviewURL(
			jsonObject.getString("thumbnail"));
		int type = FragmentConstants.getTypeFromLabel(
			jsonObject.getString("type"));

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.createFragmentEntry(0L);

		fragmentEntry.setFragmentEntryKey(fragmentEntryKey);
		fragmentEntry.setName(name);
		fragmentEntry.setCss(css);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(js);
		fragmentEntry.setConfiguration(configuration);
		fragmentEntry.setType(type);
		fragmentEntry.setImagePreviewURL(thumbnailURL);

		return fragmentEntry;
	}

	private String _getImagePreviewURL(String fileName) {
		URL url = _bundle.getResource(
			"META-INF/resources/thumbnails/" + fileName);

		if (url == null) {
			return StringPool.BLANK;
		}

		ServletContext servletContext = getServletContext();

		return servletContext.getContextPath() + "/thumbnails/" + fileName;
	}

	private void _initialize() {
		if (_initialized) {
			return;
		}

		synchronized (this) {
			if (_initialized) {
				return;
			}

			readAndCheckFragmentCollectionStructure();

			_initialized = true;
		}
	}

	private String _read(String path, String fileName, String defaultFileName)
		throws Exception {

		Class<?> clazz = getClass();

		StringBundler sb = new StringBundler(3);

		sb.append(path);
		sb.append("/");

		if (Validator.isNotNull(fileName)) {
			sb.append(fileName);
		}
		else {
			sb.append(defaultFileName);
		}

		InputStream is = clazz.getResourceAsStream(sb.toString());

		if (is != null) {
			return StringUtil.read(is);
		}

		return StringPool.BLANK;
	}

	private void _setLocalizedNames(
		String name, Map<Locale, String> names,
		ResourceBundleLoader resourceBundleLoader) {

		Set<Locale> availableLocales = new HashSet<>(
			LanguageUtil.getAvailableLocales());

		availableLocales.add(LocaleUtil.getDefault());

		for (Locale locale : availableLocales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			ResourceBundle resourceBundle =
				resourceBundleLoader.loadResourceBundle(
					LocaleUtil.fromLanguageId(languageId));

			if (Validator.isNotNull(name)) {
				names.put(
					LocaleUtil.fromLanguageId(languageId),
					LanguageUtil.get(resourceBundle, name, name));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFragmentCollectionContributor.class);

	private Bundle _bundle;
	private Map<Integer, List<FragmentEntry>> _fragmentEntries;
	private Map<String, Map<Locale, String>> _fragmentEntryNames;
	private volatile boolean _initialized;
	private Map<Locale, String> _names;

}