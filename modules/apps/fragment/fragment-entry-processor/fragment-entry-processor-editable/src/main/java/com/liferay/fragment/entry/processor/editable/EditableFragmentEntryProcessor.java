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

package com.liferay.fragment.entry.processor.editable;

import com.liferay.fragment.entry.processor.editable.mapper.EditableElementMapper;
import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

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
@Component(
	property = "fragment.entry.processor.priority:Integer=2",
	service = FragmentEntryProcessor.class
)
public class EditableFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public JSONArray getDataAttributesJSONArray() {
		JSONArray jsonArray = JSONUtil.put("lfr-editable-id");

		for (String key : _editableElementParserServiceTrackerMap.keySet()) {
			jsonArray.put("lfr-editable-type:" + key);
		}

		return jsonArray;
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		return _getDefaultEditableValuesJSONObject(html);
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		return html;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_editableElementMapperServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, EditableElementMapper.class, "type");
		_editableElementParserServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, EditableElementParser.class, "type");
	}

	@Deactivate
	protected void deactivate() {
		_editableElementParserServiceTrackerMap.close();
		_editableElementMapperServiceTrackerMap.close();
	}

	private JSONObject _getDefaultEditableValuesJSONObject(String html) {
		JSONObject defaultEditableValuesJSONObject =
			_jsonFactory.createJSONObject();

		Document document = _getDocument(html);

		for (Element element :
				document.select("lfr-editable,*[data-lfr-editable-id]")) {

			EditableElementParser editableElementParser =
				_getEditableElementParser(element);

			if (editableElementParser == null) {
				continue;
			}

			JSONObject defaultValueJSONObject = JSONUtil.put(
				"config", editableElementParser.getAttributes(element)
			).put(
				"defaultValue", editableElementParser.getValue(element)
			);

			defaultEditableValuesJSONObject.put(
				EditableFragmentEntryProcessorUtil.getElementId(element),
				defaultValueJSONObject);
		}

		return defaultEditableValuesJSONObject;
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private EditableElementParser _getEditableElementParser(Element element) {
		String type = EditableFragmentEntryProcessorUtil.getElementType(
			element);

		return _editableElementParserServiceTrackerMap.getService(type);
	}

	private ServiceTrackerMap<String, EditableElementMapper>
		_editableElementMapperServiceTrackerMap;
	private ServiceTrackerMap<String, EditableElementParser>
		_editableElementParserServiceTrackerMap;

	@Reference
	private JSONFactory _jsonFactory;

}