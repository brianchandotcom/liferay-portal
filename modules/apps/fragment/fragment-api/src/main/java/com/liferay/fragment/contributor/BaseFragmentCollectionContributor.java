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
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<FragmentEntry> getFragmentEntries(int type) {
		return _fragmentEntries.getOrDefault(type, Collections.emptyList());
	}

	@Override
	public String getName() {
		return _name;
	}

	public abstract ServletContext getServletContext();

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();

		readAndCheckFragmentCollectionStructure();
	}

	protected void readAndCheckFragmentCollectionStructure() {
		try {
			String name = _getContributedCollectionName();

			Enumeration<URL> enumeration = _bundle.findEntries(
				StringPool.BLANK,
				FragmentExportImportConstants.FILE_NAME_FRAGMENT_CONFIG, true);

			if (Validator.isNull(name) || !enumeration.hasMoreElements()) {
				return;
			}

			_name = name;

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				FragmentEntry fragmentEntry = _getFragmentEntry(
					FileUtil.getPath(url.getPath()));

				_updateFragmentEntryLinks(fragmentEntry);

				List<FragmentEntry> fragmentEntryList =
					_fragmentEntries.getOrDefault(
						fragmentEntry.getType(), new ArrayList<>());

				fragmentEntryList.add(fragmentEntry);

				_fragmentEntries.put(
					fragmentEntry.getType(), fragmentEntryList);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Reference
	protected FragmentEntryLinkLocalService fragmentEntryLinkLocalService;

	@Reference
	protected FragmentEntryLocalService fragmentEntryLocalService;

	private String _getContributedCollectionName() throws Exception {
		Class<?> clazz = getClass();

		String json = StringUtil.read(
			clazz.getResourceAsStream(
				"dependencies/" +
					FragmentExportImportConstants.FILE_NAME_COLLECTION_CONFIG));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		return jsonObject.getString("name");
	}

	private String _getFileContent(String path, String fileName)
		throws Exception {

		Class<?> clazz = getClass();

		StringBundler sb = new StringBundler(3);

		sb.append(path);
		sb.append("/");
		sb.append(fileName);

		return StringUtil.read(clazz.getResourceAsStream(sb.toString()));
	}

	private FragmentEntry _getFragmentEntry(String path) throws Exception {
		JSONObject jsonObject = _getStructure(path + "/fragment.json");

		String name = jsonObject.getString("name");
		String fragmentEntryKey = _getFragmentEntryKey(jsonObject);
		String css = _getFileContent(path, jsonObject.getString("cssPath"));
		String html = _getFileContent(path, jsonObject.getString("htmlPath"));
		String js = _getFileContent(path, jsonObject.getString("jsPath"));
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
		fragmentEntry.setType(type);
		fragmentEntry.setImagePreviewURL(thumbnailURL);

		return fragmentEntry;
	}

	private String _getFragmentEntryKey(JSONObject jsonObject) {
		String fragmentEntryKey = jsonObject.getString("fragmentEntryKey");

		return String.join(
			StringPool.DASH, getFragmentCollectionKey(), fragmentEntryKey);
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

	private JSONObject _getStructure(String path) throws Exception {
		Class<?> clazz = getClass();

		String json = StringUtil.read(clazz.getResourceAsStream(path));

		return JSONFactoryUtil.createJSONObject(json);
	}

	private void _updateFragmentEntryLinks(FragmentEntry fragmentEntry)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			fragmentEntryLinkLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"rendererKey", fragmentEntry.getFragmentEntryKey())));
		actionableDynamicQuery.setPerformActionMethod(
			(FragmentEntryLink fragmentEntryLink) -> {
				fragmentEntryLink.setCss(fragmentEntry.getCss());
				fragmentEntryLink.setHtml(fragmentEntry.getHtml());
				fragmentEntryLink.setJs(fragmentEntry.getJs());

				fragmentEntryLinkLocalService.updateFragmentEntryLink(
					fragmentEntryLink);
			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFragmentCollectionContributor.class);

	private Bundle _bundle;
	private final Map<Integer, List<FragmentEntry>> _fragmentEntries =
		new HashMap<>();
	private String _name;

}