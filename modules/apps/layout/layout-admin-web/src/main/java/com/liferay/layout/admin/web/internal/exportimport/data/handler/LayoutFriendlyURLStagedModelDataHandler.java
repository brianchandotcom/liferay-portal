/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = StagedModelDataHandler.class)
public class LayoutFriendlyURLStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutFriendlyURL> {

	public static final String[] CLASS_NAMES = {
		LayoutFriendlyURL.class.getName()
	};

	@Override
	public void deleteStagedModel(LayoutFriendlyURL layoutFriendlyURL) {
		_layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(
			layoutFriendlyURL);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		LayoutFriendlyURL layoutFriendlyURL = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		deleteStagedModel(layoutFriendlyURL);
	}

	@Override
	public LayoutFriendlyURL fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _layoutFriendlyURLLocalService.
			fetchLayoutFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<LayoutFriendlyURL> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutFriendlyURLLocalService.
			getLayoutFriendlyURLsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<LayoutFriendlyURL>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutFriendlyURL layoutFriendlyURL)
		throws Exception {

		Element layoutFriendlyURLElement =
			portletDataContext.getExportDataElement(layoutFriendlyURL);

		portletDataContext.addClassedModel(
			layoutFriendlyURLElement,
			ExportImportPathUtil.getModelPath(layoutFriendlyURL),
			layoutFriendlyURL);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutFriendlyURL layoutFriendlyURL)
		throws Exception {

		long userId = portletDataContext.getUserId(
			layoutFriendlyURL.getUserUuid());

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		long plid = MapUtil.getLong(
			plids, layoutFriendlyURL.getPlid(), layoutFriendlyURL.getPlid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutFriendlyURL);

		LayoutFriendlyURL importedLayoutFriendlyURL = null;

		LayoutFriendlyURL existingLayoutFriendlyURL =
			_fetchExistingLayoutFriendlyURL(
				portletDataContext, layoutFriendlyURL, plid);

		layoutFriendlyURL = _getUniqueLayoutFriendlyURL(
			portletDataContext, layoutFriendlyURL, existingLayoutFriendlyURL);

		boolean privateLayout = portletDataContext.isPrivateLayout();

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout != null) {
			privateLayout = layout.isPrivateLayout();
		}

		if (existingLayoutFriendlyURL == null) {
			serviceContext.setUuid(layoutFriendlyURL.getUuid());

			importedLayoutFriendlyURL =
				_layoutFriendlyURLLocalService.addLayoutFriendlyURL(
					userId, portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), plid, privateLayout,
					layoutFriendlyURL.getFriendlyURL(),
					layoutFriendlyURL.getLanguageId(), serviceContext);
		}
		else {
			importedLayoutFriendlyURL =
				_layoutFriendlyURLLocalService.updateLayoutFriendlyURL(
					userId, portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), plid, privateLayout,
					layoutFriendlyURL.getFriendlyURL(),
					layoutFriendlyURL.getLanguageId(), serviceContext);
		}

		portletDataContext.importClassedModel(
			layoutFriendlyURL, importedLayoutFriendlyURL);
	}

	private LayoutFriendlyURL _fetchExistingLayoutFriendlyURL(
		PortletDataContext portletDataContext,
		LayoutFriendlyURL layoutFriendlyURL, long plid) {

		LayoutFriendlyURL existingLayoutFriendlyURL =
			fetchStagedModelByUuidAndGroupId(
				layoutFriendlyURL.getUuid(),
				portletDataContext.getScopeGroupId());

		if (existingLayoutFriendlyURL == null) {
			existingLayoutFriendlyURL =
				_layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(
					plid, layoutFriendlyURL.getLanguageId(), false);
		}

		return existingLayoutFriendlyURL;
	}

	private LayoutFriendlyURL _getUniqueLayoutFriendlyURL(
			PortletDataContext portletDataContext,
			LayoutFriendlyURL layoutFriendlyURL,
			LayoutFriendlyURL existingLayoutFriendlyURL)
		throws Exception {

		String friendlyURL = layoutFriendlyURL.getFriendlyURL();

		boolean privateLayout = portletDataContext.isPrivateLayout();

		if (existingLayoutFriendlyURL != null) {
			privateLayout = existingLayoutFriendlyURL.isPrivateLayout();
		}

		for (int i = 1;; i++) {
			LayoutFriendlyURL duplicateLayoutFriendlyURL =
				_layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(
					portletDataContext.getScopeGroupId(), privateLayout,
					layoutFriendlyURL.getFriendlyURL(),
					layoutFriendlyURL.getLanguageId());

			if ((duplicateLayoutFriendlyURL == null) ||
				((existingLayoutFriendlyURL != null) &&
				 (existingLayoutFriendlyURL.getLayoutFriendlyURLId() ==
					 duplicateLayoutFriendlyURL.getLayoutFriendlyURLId()))) {

				break;
			}

			layoutFriendlyURL.setFriendlyURL(friendlyURL + i);
		}

		return layoutFriendlyURL;
	}

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

}