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

package com.liferay.asset.tags.admin.web.lar;

import com.liferay.asset.tags.admin.web.constants.AssetTagsAdminPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.adapter.ModelAdapterUtil;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.adapter.StagedAssetTag;
import com.liferay.portlet.asset.model.adapter.impl.StagedAssetTagImpl;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.ManifestSummary;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.exportimport.xstream.XStreamAliasRegistryUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetTagsAdminPortletKeys.ASSET_TAGS_ADMIN
	},
	service = PortletDataHandler.class
)
public class AssetTagsPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "asset_tag";

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(StagedAssetTag.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "tags", true, false, null,
				StagedAssetTag.class.getName()));
		setPublishToLiveByDefault(true);

		XStreamAliasRegistryUtil.register(
			StagedAssetTagImpl.class, "StagedAssetTag");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				AssetTagsPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		AssetTagLocalServiceUtil.deleteGroupTags(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "tags")) {
			return getExportDataRootElementString(rootElement);
		}

		ActionableDynamicQuery actionableDynamicQuery =
			getTagActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "tags")) {
			return null;
		}

		Element tagsElement = portletDataContext.getImportDataGroupElement(
			StagedAssetTag.class);

		List<Element> tagElements = tagsElement.elements();

		for (Element tagElement : tagElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, tagElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			getTagActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getTagActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType.toString(), modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext,
							new StagedModelType(AssetTag.class));

					manifestSummary.addModelDeletionCount(
						stagedModelType.toString(), modelDeletionCount);

					return modelAdditionCount;
				}

			};

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

			@Override
			public void addCriteria(DynamicQuery dynamicQuery) {
				portletDataContext.addDateRangeCriteria(
					dynamicQuery, "modifiedDate");
			}

		});
		exportActionableDynamicQuery.setBaseLocalService(
			AssetTagLocalServiceUtil.getService());
		exportActionableDynamicQuery.setClass(AssetTag.class);
		exportActionableDynamicQuery.setClassLoader(
			this.getClass().getClassLoader());
		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());
		exportActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());
		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					StagedAssetTag stagedAssetTag = ModelAdapterUtil.adapt(
						(AssetTag)object, AssetTag.class, StagedAssetTag.class);

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, stagedAssetTag);
				}

			});
		exportActionableDynamicQuery.setPrimaryKeyPropertyName("tagId");
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(StagedAssetTag.class));

		return exportActionableDynamicQuery;
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

}