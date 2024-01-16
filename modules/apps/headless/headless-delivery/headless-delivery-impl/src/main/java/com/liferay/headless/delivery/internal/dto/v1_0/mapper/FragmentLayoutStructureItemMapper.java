/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.FragmentStyle;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageWidgetInstanceDefinition;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.StyledLayoutStructureItemUtil;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.exporter.PortletPreferencesPortletConfigurationExporter;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jürgen Kappler
 */
public class FragmentLayoutStructureItemMapper
	extends BaseStyledLayoutStructureItemMapper {

	public FragmentLayoutStructureItemMapper(
		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		FragmentEntryLocalService fragmentEntryLocalService,
		GroupLocalService groupLocalService, JSONFactory jsonFactory,
		LayoutLocalService layoutLocalService,
		PortletLocalService portletLocalService,
		PortletPreferencesPortletConfigurationExporter
			portletPreferencesPortletConfigurationExporter,
		PortletRegistry portletRegistry,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService, TeamLocalService teamLocalService,
		InfoItemServiceRegistry infoItemServiceRegistry, Portal portal) {

		super(infoItemServiceRegistry, portal);

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_jsonFactory = jsonFactory;

		_widgetInstanceMapper = new WidgetInstanceMapper(
			layoutLocalService, portal, portletLocalService,
			portletPreferencesPortletConfigurationExporter,
			resourceActionLocalService, resourcePermissionLocalService,
			roleLocalService, teamLocalService);

		_pageFragmentInstanceDefinitionMapper =
			new PageFragmentInstanceDefinitionMapper(
				fragmentCollectionContributorRegistry,
				fragmentEntryConfigurationParser,
				_fragmentEntryLinkLocalService, fragmentEntryLocalService,
				groupLocalService, infoItemServiceRegistry, _jsonFactory,
				portal, portletRegistry, _widgetInstanceMapper);
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)layoutStructureItem;

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

		if (fragmentEntryLink == null) {
			return null;
		}

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}

		String portletId = editableValuesJSONObject.getString("portletId");

		JSONObject itemConfigJSONObject =
			fragmentStyledLayoutStructureItem.getItemConfigJSONObject();

		if (Validator.isNull(portletId)) {
			return new PageElement() {
				{
					definition =
						_pageFragmentInstanceDefinitionMapper.
							getPageFragmentInstanceDefinition(
								fragmentStyledLayoutStructureItem,
								toFragmentStyle(
									itemConfigJSONObject.getJSONObject(
										"styles"),
									saveMappingConfiguration),
								getFragmentViewPorts(itemConfigJSONObject),
								saveInlineContent, saveMappingConfiguration);
					id = layoutStructureItem.getItemId();
					type = Type.FRAGMENT;
				}
			};
		}

		String instanceId = editableValuesJSONObject.getString("instanceId");

		return new PageElement() {
			{
				definition = _toPageWidgetInstanceDefinition(
					fragmentEntryLink, fragmentStyledLayoutStructureItem,
					itemConfigJSONObject.getString("name", null),
					toFragmentStyle(
						itemConfigJSONObject.getJSONObject("styles"),
						saveMappingConfiguration),
					getFragmentViewPorts(
						itemConfigJSONObject.getJSONObject("style")),
					PortletIdCodec.encode(portletId, instanceId));
				id = layoutStructureItem.getItemId();
				type = Type.WIDGET;
			}
		};
	}

	private PageWidgetInstanceDefinition _toPageWidgetInstanceDefinition(
		FragmentEntryLink fragmentEntryLink,
		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem,
		String nameValue,
		FragmentStyle pageWidgetInstanceDefinitionFragmentStyle,
		FragmentViewport[] pageWidgetInstanceDefinitionFragmentViewports,
		String portletId) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		return new PageWidgetInstanceDefinition() {
			{
				cssClasses = StyledLayoutStructureItemUtil.getCssClasses(
					fragmentStyledLayoutStructureItem);
				customCSS = StyledLayoutStructureItemUtil.getCustomCSS(
					fragmentStyledLayoutStructureItem);
				customCSSViewports =
					StyledLayoutStructureItemUtil.getCustomCSSViewports(
						fragmentStyledLayoutStructureItem);
				fragmentStyle = pageWidgetInstanceDefinitionFragmentStyle;
				fragmentViewports =
					pageWidgetInstanceDefinitionFragmentViewports;
				name = nameValue;
				widgetInstance = _widgetInstanceMapper.getWidgetInstance(
					fragmentEntryLink, portletId);
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentLayoutStructureItemMapper.class);

	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final JSONFactory _jsonFactory;
	private final PageFragmentInstanceDefinitionMapper
		_pageFragmentInstanceDefinitionMapper;
	private final WidgetInstanceMapper _widgetInstanceMapper;

}