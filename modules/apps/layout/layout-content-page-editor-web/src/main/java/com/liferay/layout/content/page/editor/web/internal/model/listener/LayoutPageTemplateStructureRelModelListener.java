/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.layout.content.page.editor.web.internal.util.ContentManager;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
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
			LayoutPageTemplateStructureRel
				originalLayoutPageTemplateStructureRel,
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
			_getLayoutPageTemplateStructureClassNameId(),
			layoutPageTemplateStructure.getPlid());

		Set<LayoutDisplayPageObjectProvider<?>>
			layoutDisplayPageObjectProviders =
				_contentManager.getLayoutMappedLayoutDisplayPageObjectProviders(
					layoutPageTemplateStructureRel.getData());

		for (LayoutDisplayPageObjectProvider<?>
				layoutDisplayPageObjectProvider :
					layoutDisplayPageObjectProviders) {

			LayoutClassedModelUsage layoutClassedModelUsage =
				_layoutClassedModelUsageLocalService.
					fetchLayoutClassedModelUsage(
						layoutDisplayPageObjectProvider.getClassNameId(),
						layoutDisplayPageObjectProvider.getClassPK(),
						layoutDisplayPageObjectProvider.
							getExternalReferenceCode(),
						String.valueOf(
							layoutPageTemplateStructure.
								getLayoutPageTemplateStructureId()),
						_getLayoutPageTemplateStructureClassNameId(),
						layoutPageTemplateStructure.getPlid());

			if (layoutClassedModelUsage != null) {
				continue;
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext == null) {
				serviceContext = new ServiceContext();
			}

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				layoutPageTemplateStructure.getGroupId(),
				layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getExternalReferenceCode(),
				String.valueOf(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId()),
				_getLayoutPageTemplateStructureClassNameId(),
				layoutPageTemplateStructure.getPlid(), serviceContext);
		}
	}

	private long _getLayoutPageTemplateStructureClassNameId() {
		if (_layoutPageTemplateStructureNameId != null) {
			return _layoutPageTemplateStructureNameId;
		}

		_layoutPageTemplateStructureNameId = _portal.getClassNameId(
			LayoutPageTemplateStructure.class.getName());

		return _layoutPageTemplateStructureNameId;
	}

	@Reference
	private ContentManager _contentManager;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private Long _layoutPageTemplateStructureNameId;

	@Reference
	private Portal _portal;

}