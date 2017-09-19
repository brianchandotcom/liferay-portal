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

package com.liferay.asset.internal.verify;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.verify.VerifyLayout;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Douglas Wong
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.asset.service"},
	service = VerifyProcess.class
)
public class AssetServiceVerifyProcess extends VerifyLayout {

	protected void deleteOrphanedAssetEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = _portal.getClassNameId(
				DLFileEntryConstants.getClassName());

			StringBundler sb = new StringBundler(5);

			sb.append("delete from AssetEntry where classNameId = ");
			sb.append(classNameId);
			sb.append(" and classPK not in (select fileVersionId from ");
			sb.append("DLFileVersion) and classPK not in (select fileEntryId ");
			sb.append("from DLFileEntry)");

			runSQL(sb.toString());

			EntityCacheUtil.clearCache(AssetEntryImpl.class);
			FinderCacheUtil.clearCache(AssetEntryImpl.class.getName());
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteOrphanedAssetEntries();
		verifyAssetLayouts();
	}

	protected void verifyAssetLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			verifyUuid("AssetEntry");
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private Portal _portal;

}