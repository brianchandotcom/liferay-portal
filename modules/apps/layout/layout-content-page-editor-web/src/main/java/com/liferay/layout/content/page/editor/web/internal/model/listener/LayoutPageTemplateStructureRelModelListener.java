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

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.content.page.editor.web.internal.util.ContentUtil;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.Optional;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class LayoutPageTemplateStructureRelModelListener
	extends BaseModelListener<LayoutPageTemplateStructureRel> {

	@Override
	public void onAfterUpdate(
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws ModelListenerException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layoutPageTemplateStructureRel.
						getLayoutPageTemplateStructureId());

		if (layoutPageTemplateStructure == null) {
			return;
		}

		_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsages(
			String.valueOf(
				layoutPageTemplateStructure.getLayoutPageTemplateStructureId()),
			_portal.getClassNameId(LayoutPageTemplateStructure.class),
			layoutPageTemplateStructure.getClassPK());

		Set<InfoDisplayObjectProvider> infoDisplayObjectProviders =
			ContentUtil.getLayoutMappedInfoDisplayObjectProviders(
				layoutPageTemplateStructureRel.getData());

		for (InfoDisplayObjectProvider infoDisplayObjectProvider :
				infoDisplayObjectProviders) {

			LayoutClassedModelUsage layoutClassedModelUsage =
				_layoutClassedModelUsageLocalService.
					fetchLayoutClassedModelUsage(
						infoDisplayObjectProvider.getClassNameId(),
						infoDisplayObjectProvider.getClassPK(),
						String.valueOf(
							layoutPageTemplateStructure.
								getLayoutPageTemplateStructureId()),
						_portal.getClassNameId(
							LayoutPageTemplateStructure.class),
						layoutPageTemplateStructure.getClassPK());

			if (layoutClassedModelUsage != null) {
				continue;
			}

			ServiceContext serviceContext = Optional.ofNullable(
				ServiceContextThreadLocal.getServiceContext()
			).orElse(
				new ServiceContext()
			);

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				layoutPageTemplateStructure.getGroupId(),
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK(),
				String.valueOf(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId()),
				_portal.getClassNameId(LayoutPageTemplateStructure.class),
				layoutPageTemplateStructure.getClassPK(), serviceContext);
		}
	}

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}