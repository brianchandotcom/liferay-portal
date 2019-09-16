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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.layout.util.template.LayoutData;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletDecorator;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/convert_layout"
	},
	service = AopService.class
)
public class ConvertLayoutMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

		Layout layout = _layoutService.updateType(
			selPlid, LayoutConstants.TYPE_CONTENT);

		_updatePortletDecorator(layout);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		Layout draftLayout = _layoutLocalService.addLayout(
			layout.getUserId(), layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId(),
			_classNameLocalService.getClassNameId(Layout.class),
			layout.getPlid(), layout.getNameMap(), layout.getTitleMap(),
			layout.getDescriptionMap(), layout.getKeywordsMap(),
			layout.getRobotsMap(), layout.getType(), layout.getTypeSettings(),
			true, true, Collections.emptyMap(), serviceContext);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String layoutTemplateId = typeSettingsProperties.getProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID);

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(layoutTemplateId);

		LayoutData layoutData = layoutConverter.convert(layout);

		JSONObject layoutDataJSONObject = layoutData.getLayoutDataJSONObject();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		if (layoutPageTemplateStructure == null) {
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					serviceContext.getUserId(), layout.getGroupId(),
					_portal.getClassNameId(Layout.class), layout.getPlid(),
					layoutDataJSONObject.toString(), serviceContext);
		}
		else {
			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid(), layoutDataJSONObject.toString());
		}

		_layoutCopyHelper.copyLayout(layout, draftLayout);

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			new Date());
	}

	private String _getDefaultPortletDecoratorId(Layout layout)
		throws PortalException {

		Theme theme = layout.getTheme();

		List<PortletDecorator> portletDecorators = theme.getPortletDecorators();

		Stream<PortletDecorator> portletDecoratorsStream =
			portletDecorators.stream();

		List<PortletDecorator> filteredPortletDecorators =
			portletDecoratorsStream.filter(
				portletDecorator -> portletDecorator.isDefaultPortletDecorator()
			).collect(
				Collectors.toList()
			);

		if (ListUtil.isEmpty(filteredPortletDecorators)) {
			return StringPool.BLANK;
		}

		PortletDecorator defaultPortletDecorator =
			filteredPortletDecorators.get(0);

		return defaultPortletDecorator.getPortletDecoratorId();
	}

	private void _updatePortletDecorator(Layout layout) throws Exception {
		String defaultPortletDecoratorId = _getDefaultPortletDecoratorId(
			layout);

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			String preferencesXML = portletPreferences.getPreferences();

			if (Validator.isNull(preferencesXML)) {
				preferencesXML = PortletConstants.DEFAULT_PREFERENCES;
			}

			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.fromDefaultXML(preferencesXML);

			String portletSetupPortletDecoratorId =
				jxPortletPreferences.getValue(
					"portletSetupPortletDecoratorId", StringPool.BLANK);

			if (Validator.isNotNull(portletSetupPortletDecoratorId)) {
				continue;
			}

			jxPortletPreferences.setValue(
				"portletSetupPortletDecoratorId", defaultPortletDecoratorId);

			portletPreferences.setPreferences(
				PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

			_portletPreferencesLocalService.updatePortletPreferences(
				portletPreferences);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}