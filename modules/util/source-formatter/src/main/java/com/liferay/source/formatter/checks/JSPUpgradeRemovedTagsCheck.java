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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPUpgradeRemovedTagsCheck extends BaseTagAttributesCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		String upgradeFromVersion = getAttributeValue(
			"upgrade.from.version", absolutePath);
		String upgradeToVersion = getAttributeValue(
			"upgrade.to.version", absolutePath);

		JSONObject upgradeFromTaglibsJSONObject = _getTaglibsJSONObject(
			upgradeFromVersion);
		JSONObject upgradeToTaglibsJSONObject = _getTaglibsJSONObject(
			upgradeToVersion);

		_checkMultiLineTagAttributes(
			fileName, content, upgradeFromTaglibsJSONObject,
			upgradeToTaglibsJSONObject);
		_checkSingleLineTagAttributes(
			fileName, content, upgradeFromTaglibsJSONObject,
			upgradeToTaglibsJSONObject);

		return content;
	}

	private void _checkMultiLineTagAttributes(
			String fileName, String content,
			JSONObject upgradeFromTaglibsJSONObject,
			JSONObject upgradeToTaglibsJSONObject)
		throws Exception {

		Matcher matcher = _multilineTagPattern.matcher(content);

		while (matcher.find()) {
			if (matcher.start() != 0) {
				char c = content.charAt(matcher.start() - 1);

				if (c != CharPool.NEW_LINE) {
					continue;
				}
			}

			_checkTag(
				fileName, parseTag(matcher.group(1), false),
				upgradeFromTaglibsJSONObject, upgradeToTaglibsJSONObject);
		}
	}

	private void _checkSingleLineTagAttributes(
			String fileName, String content,
			JSONObject upgradeFromTaglibsJSONObject,
			JSONObject upgradeToTaglibsJSONObject)
		throws Exception {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				for (String jspTag : getJSPTags(line)) {
					_checkTag(
						fileName, parseTag(jspTag, false),
						upgradeFromTaglibsJSONObject,
						upgradeToTaglibsJSONObject);
				}
			}
		}
	}

	private void _checkTag(
		String fileName, BaseTagAttributesCheck.Tag tag,
		JSONObject upgradeFromTaglibsJSONObject,
		JSONObject upgradeToTaglibsJSONObject) {

		if (tag == null) {
			return;
		}

		String tagName = tag.getName();

		if (!tagName.contains(":")) {
			return;
		}

		TagStatus upgradeFromTagStatus = _getTagStatus(
			upgradeFromTaglibsJSONObject, tag);
		TagStatus upgradeToTagStatus = _getTagStatus(
			upgradeToTaglibsJSONObject, tag);

		if (!upgradeFromTagStatus.equals(TagStatus.NO_TAGLIB_FOUND) &&
			upgradeToTagStatus.equals(TagStatus.NO_TAGLIB_FOUND)) {

			addMessage(fileName, "REMOVED TAGLIB: " + tagName);
		}

		if (upgradeFromTagStatus.equals(TagStatus.ATTRIBUTES_FOUND) &&
			upgradeToTagStatus.equals(TagStatus.NO_TAG_FOUND)) {

			addMessage(fileName, "REMOVED TAG: " + tagName);
		}

		if (upgradeFromTagStatus.equals(TagStatus.ATTRIBUTES_FOUND) &&
			upgradeToTagStatus.equals(TagStatus.ATTRIBUTES_FOUND)) {

			List<String> upgradeFromTagAttributes =
				upgradeFromTagStatus.getAttributes();
			List<String> upgradeToTagAttributes =
				upgradeToTagStatus.getAttributes();

			for (String upgradeFromTagAttribute : upgradeFromTagAttributes) {
				if (!upgradeToTagAttributes.contains(upgradeFromTagAttribute)) {
					addMessage(
						fileName,
						"REMOVED ATTRIBUTE: " + upgradeFromTagAttribute);
				}
			}
		}
	}

	private List<String> _getAttributeNames(JSONObject tagJSONObject) {
		List<String> attributeNames = new ArrayList<>();

		JSONArray tagAttributeNamesJSONArray = tagJSONObject.getJSONArray(
			"attributes");

		if (tagAttributeNamesJSONArray == null) {
			return attributeNames;
		}

		Iterator<JSONObject> iterator = tagAttributeNamesJSONArray.iterator();

		while (iterator.hasNext()) {
			JSONObject attributeJSONObject = iterator.next();

			attributeNames.add(attributeJSONObject.getString("name"));
		}

		return attributeNames;
	}

	private JSONObject _getTaglibsJSONObject(String version) throws Exception {
		JSONObject taglibsJSONObject = _taglibsJSONObjectMap.get(version);

		if (taglibsJSONObject != null) {
			return taglibsJSONObject.getJSONObject(version);
		}

		JSONObject portalJSONObject =
			SourceFormatterUtil.getPortalJSONObjectByVersion(version);

		taglibsJSONObject = portalJSONObject.getJSONObject("taglibs");

		_taglibsJSONObjectMap.put(version, taglibsJSONObject);

		return taglibsJSONObject;
	}

	private TagStatus _getTagStatus(
		JSONObject taglibsJSONObject, BaseTagAttributesCheck.Tag tag) {

		String[] parts = StringUtil.split(tag.getName(), ":");

		String taglib = parts[0];

		JSONObject taglibJSONObject = taglibsJSONObject.getJSONObject(taglib);

		if (taglibJSONObject == null) {
			return TagStatus.NO_TAGLIB_FOUND;
		}

		String tagName = parts[1];

		JSONObject tagJSONObject = taglibJSONObject.getJSONObject(tagName);

		if (tagJSONObject == null) {
			return TagStatus.NO_TAG_FOUND;
		}

		TagStatus tagStatus = TagStatus.ATTRIBUTES_FOUND;

		Map<String, String> attributesMap = tag.getAttributesMap();

		if (attributesMap.isEmpty()) {
			return tagStatus;
		}

		List<String> attributeNames = _getAttributeNames(tagJSONObject);

		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			String attributeName = entry.getKey();

			if (attributeNames.contains(attributeName)) {
				tagStatus.addAttribute(attributeName);
			}
		}

		return tagStatus;
	}

	private static final Pattern _multilineTagPattern = Pattern.compile(
		"(([ \t]*)<[-\\w:]+\n.*?([^%])(/?>))(\n|$)", Pattern.DOTALL);

	private final Map<String, JSONObject> _taglibsJSONObjectMap =
		new HashMap<>();

	private enum TagStatus {

		ATTRIBUTES_FOUND, NO_TAG_FOUND, NO_TAGLIB_FOUND;

		public void addAttribute(String attribute) {
			_attributes.add(attribute);
		}

		public List<String> getAttributes() {
			return _attributes;
		}

		private final List<String> _attributes = new ArrayList<>();

	}

}