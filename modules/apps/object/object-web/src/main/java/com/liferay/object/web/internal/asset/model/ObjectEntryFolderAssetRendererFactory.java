/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
	service = AssetRendererFactory.class
)
public class ObjectEntryFolderAssetRendererFactory
	extends BaseAssetRendererFactory<ObjectEntryFolder> {

	public static final String TYPE = "object_entry_folder";

	public ObjectEntryFolderAssetRendererFactory() {
		setLinkable(true);
		setClassName(ObjectEntryFolder.class.getName());
		setPortletId(ObjectPortletKeys.OBJECT_DEFINITIONS);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<ObjectEntryFolder> getAssetRenderer(
			long classPK, int type)
		throws PortalException {

		return new ObjectEntryFolderAssetRenderer(
			_objectEntryFolderService.getObjectEntryFolder(classPK));
	}

	@Override
	public String getClassName() {
		return ObjectEntryFolder.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "folder";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Reference
	private ObjectEntryFolderService _objectEntryFolderService;

}