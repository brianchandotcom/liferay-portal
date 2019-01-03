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

package com.liferay.mobile.device.rules.web.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class MDRRuleGroupInstanceStagedModelDataHandler
	extends BaseStagedModelDataHandler<MDRRuleGroupInstance> {

	public static final String[] CLASS_NAMES = {
		MDRRuleGroupInstance.class.getName()
	};

	@Override
	public void deleteStagedModel(MDRRuleGroupInstance ruleGroupInstance) {
		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(
			ruleGroupInstance);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		MDRRuleGroupInstance ruleGroupInstance =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (ruleGroupInstance != null) {
			deleteStagedModel(ruleGroupInstance);
		}
	}

	@Override
	public MDRRuleGroupInstance fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _mdrRuleGroupInstanceLocalService.
			fetchMDRRuleGroupInstanceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<MDRRuleGroupInstance> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mdrRuleGroupInstanceLocalService.
			getMDRRuleGroupInstancesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<MDRRuleGroupInstance>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			MDRRuleGroupInstance ruleGroupInstance)
		throws Exception {

		MDRRuleGroup ruleGroup = _mdrRuleGroupLocalService.getRuleGroup(
			ruleGroupInstance.getRuleGroupId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, ruleGroupInstance, ruleGroup,
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element ruleGroupInstanceElement =
			portletDataContext.getExportDataElement(ruleGroupInstance);

		String className = ruleGroupInstance.getClassName();

		if (className.equals(Layout.class.getName())) {
			Layout layout = _layoutLocalService.getLayout(
				ruleGroupInstance.getClassPK());

			ruleGroupInstanceElement.addAttribute(
				"layout-uuid", layout.getUuid());
		}

		portletDataContext.addClassedModel(
			ruleGroupInstanceElement,
			ExportImportPathUtil.getModelPath(ruleGroupInstance),
			ruleGroupInstance);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			MDRRuleGroupInstance ruleGroupInstance)
		throws Exception {

		long userId = portletDataContext.getUserId(
			ruleGroupInstance.getUserUuid());

		Map<Long, Long> ruleGroupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MDRRuleGroup.class);

		Long ruleGroupId = MapUtil.getLong(
			ruleGroupIds, ruleGroupInstance.getRuleGroupId(),
			ruleGroupInstance.getRuleGroupId());

		long classPK = 0;

		Element ruleGroupInstanceElement =
			portletDataContext.getImportDataStagedModelElement(
				ruleGroupInstance);

		String layoutUuid = ruleGroupInstanceElement.attributeValue(
			"layout-uuid");

		try {
			if (Validator.isNotNull(layoutUuid)) {
				Layout layout = _layoutLocalService.getLayoutByUuidAndGroupId(
					layoutUuid, portletDataContext.getScopeGroupId(),
					portletDataContext.isPrivateLayout());

				classPK = layout.getPrimaryKey();
			}
			else {
				LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
					portletDataContext.getScopeGroupId(),
					portletDataContext.isPrivateLayout());

				classPK = layoutSet.getLayoutSetId();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(5);

				sb.append("Layout ");
				sb.append(layoutUuid);
				sb.append(" is missing for rule group instance ");
				sb.append(ruleGroupInstance.getRuleGroupInstanceId());
				sb.append(", skipping this rule group instance.");

				_log.warn(sb.toString());
			}

			return;
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ruleGroupInstance);

		serviceContext.setUserId(userId);

		MDRRuleGroupInstance importedRuleGroupInstance = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MDRRuleGroupInstance existingMDRRuleGroupInstance =
				fetchStagedModelByUuidAndGroupId(
					ruleGroupInstance.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingMDRRuleGroupInstance == null) {
				serviceContext.setUuid(ruleGroupInstance.getUuid());

				importedRuleGroupInstance =
					_mdrRuleGroupInstanceLocalService.addRuleGroupInstance(
						portletDataContext.getScopeGroupId(),
						ruleGroupInstance.getClassName(), classPK, ruleGroupId,
						ruleGroupInstance.getPriority(), serviceContext);
			}
			else {
				importedRuleGroupInstance =
					_mdrRuleGroupInstanceLocalService.updateRuleGroupInstance(
						existingMDRRuleGroupInstance.getRuleGroupInstanceId(),
						ruleGroupInstance.getPriority());
			}
		}
		else {
			importedRuleGroupInstance =
				_mdrRuleGroupInstanceLocalService.addRuleGroupInstance(
					portletDataContext.getScopeGroupId(),
					ruleGroupInstance.getClassName(), classPK, ruleGroupId,
					ruleGroupInstance.getPriority(), serviceContext);
		}

		portletDataContext.importClassedModel(
			ruleGroupInstance, importedRuleGroupInstance);
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {

		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MDRRuleGroupInstanceStagedModelDataHandler.class);

	private LayoutLocalService _layoutLocalService;
	private LayoutSetLocalService _layoutSetLocalService;
	private MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;
	private MDRRuleGroupLocalService _mdrRuleGroupLocalService;

}