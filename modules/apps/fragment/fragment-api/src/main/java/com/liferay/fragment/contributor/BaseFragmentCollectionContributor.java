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

import com.liferay.fragment.constants.FragmentEntryTypeConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseFragmentCollectionContributor
	implements FragmentCollectionContributor {

	@Override
	public List<DefaultFragmentEntry> getDefaultFragmentEntries() {
		return _defaultFragmentEntries;
	}

	@Override
	public abstract String getKey();

	@Override
	public String getName() {
		return _name;
	}

	protected abstract Class getResourceClass();

	protected void readAndCheckFragmentCollectionStructure() {
		try {
			JSONObject jsonObject = _getStructure("collection.json");

			String name = jsonObject.getString("name");
			JSONArray jsonArray = jsonObject.getJSONArray("fragments");

			if (Validator.isNotNull(name) && (jsonArray.length() > 0)) {
				List<DefaultFragmentEntry> defaultFragmentEntries =
					new ArrayList<>();

				for (int i = 0; i < jsonArray.length(); i++) {
					defaultFragmentEntries.add(
						_getDefaultFragmentEntry(
							getKey(), jsonArray.getString(i)));
				}

				_name = name;
				_defaultFragmentEntries = defaultFragmentEntries;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	private DefaultFragmentEntry _getDefaultFragmentEntry(
			String key, String path)
		throws Exception {

		JSONObject jsonObject = _getStructure(path + "/fragment.json");

		String name = jsonObject.getString("name");
		String css = _getFileContent(path, jsonObject.getString("cssPath"));
		String html = _getFileContent(path, jsonObject.getString("htmlPath"));
		String js = _getFileContent(path, jsonObject.getString("jsPath"));
		int type = FragmentEntryTypeConstants.getTypeFromLabel(
			jsonObject.getString("type"));

		return new DefaultFragmentEntry(key, name, css, html, js, type);
	}

	private String _getFileContent(String path, String fileName)
		throws Exception {

		Class<?> resourceClass = getResourceClass();

		StringBundler sb = new StringBundler(4);

		sb.append("dependencies/");
		sb.append(path);
		sb.append("/");
		sb.append(fileName);

		return StringUtil.read(
			resourceClass.getResourceAsStream(sb.toString()));
	}

	private JSONObject _getStructure(String path) throws Exception {
		Class<?> resourceClass = getResourceClass();

		String structure = StringUtil.read(
			resourceClass.getResourceAsStream("dependencies/" + path));

		return JSONFactoryUtil.createJSONObject(structure);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFragmentCollectionContributor.class);

	private List<DefaultFragmentEntry> _defaultFragmentEntries;
	private String _name;

}