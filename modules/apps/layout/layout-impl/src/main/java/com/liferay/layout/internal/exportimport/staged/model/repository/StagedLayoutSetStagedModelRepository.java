/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = "model.class.name=com.liferay.layout.set.model.adapter.StagedLayoutSet",
	service = StagedModelRepository.class
)
public class StagedLayoutSetStagedModelRepository
	implements StagedModelRepository<StagedLayoutSet> {

	public StagedLayoutSet addStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		// Not supported for layout sets

		return null;
	}

	public void deleteStagedModel(StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		// Not supported for layout sets

	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		// Not supported for layout sets

	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		// Not supported for layout sets

	}

	@Override
	public StagedLayoutSet fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		boolean privateLayout = GetterUtil.getBoolean(uuid);

		try {
			return ModelAdapterUtil.adapt(
				_layoutSetLocalService.getLayoutSet(groupId, privateLayout),
				LayoutSet.class, StagedLayoutSet.class);
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	@Override
	public List<StagedLayoutSet> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		boolean privateLayout = GetterUtil.getBoolean(uuid);

		DynamicQuery dynamicQuery = _layoutSetLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property privateLayoutProperty = PropertyFactoryUtil.forName(
			"privateLayout");

		dynamicQuery.add(privateLayoutProperty.eq(privateLayout));

		return TransformUtil.transform(
			(List<LayoutSet>)dynamicQuery.list(),
			layoutSet -> ModelAdapterUtil.adapt(
				layoutSet, LayoutSet.class, StagedLayoutSet.class));
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return null;
	}

	@Override
	public StagedLayoutSet getStagedModel(long layoutSetId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			_layoutSetLocalService.getLayoutSet(layoutSetId), LayoutSet.class,
			StagedLayoutSet.class);
	}

	public StagedLayoutSet saveStagedModel(StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			_layoutSetLocalService.updateLayoutSet(
				stagedLayoutSet.getLayoutSet()),
			LayoutSet.class, StagedLayoutSet.class);
	}

	public StagedLayoutSet updateStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		LayoutSet existingLayoutSet = _layoutSetLocalService.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		// Layout set prototype settings

		boolean layoutSetPrototypeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS);

		if (layoutSetPrototypeSettings &&
			Validator.isNotNull(layoutSet.getLayoutSetPrototypeUuid())) {

			existingLayoutSet.setLayoutSetPrototypeUuid(
				layoutSet.getLayoutSetPrototypeUuid());
			existingLayoutSet.setLayoutSetPrototypeLinkEnabled(
				MapUtil.getBoolean(
					portletDataContext.getParameterMap(),
					PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED));

			existingLayoutSet = _layoutSetLocalService.updateLayoutSet(
				existingLayoutSet);
		}

		// Layout set settings

		boolean layoutSetSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		if (layoutSetSettings) {
			existingLayoutSet = _layoutSetLocalService.updateSettings(
				existingLayoutSet.getGroupId(),
				existingLayoutSet.isPrivateLayout(), layoutSet.getSettings());
		}

		return ModelAdapterUtil.adapt(
			existingLayoutSet, LayoutSet.class, StagedLayoutSet.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedLayoutSetStagedModelRepository.class);

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}