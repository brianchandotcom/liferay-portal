/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.internal.util.LogUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.scope.Scope;

import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class FileEntryUtil {

	public static FileEntry fetchFileEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode, Scope scope,
			long scopeGroupId)
		throws PortalException {

		FileEntry fileEntry = null;

		Long groupId = ItemScopeUtil.getItemGroupId(
			companyId, scope, scopeGroupId);

		if (groupId != null) {
			fileEntry =
				DLAppLocalServiceUtil.fetchFileEntryByExternalReferenceCode(
					groupId, externalReferenceCode);
		}

		if (fileEntry == null) {
			LogUtil.logOptionalReference(
				FileEntry.class.getName(), externalReferenceCode, scope,
				scopeGroupId);
		}

		return fileEntry;
	}

	public static ItemExternalReference getFileEntryItemExternalReference(
			long companyId, JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		if (jsonObject == null) {
			return null;
		}

		FileEntry fileEntry = null;

		long fileEntryId = jsonObject.getLong("fileEntryId");

		if (fileEntryId <= 0) {
			fileEntryId = jsonObject.getLong("classPK");
		}

		if (fileEntryId > 0) {
			fileEntry = DLAppLocalServiceUtil.fetchFileEntry(fileEntryId);
		}

		if (fileEntry != null) {
			return _getItemExternalReference(
				fileEntry.getExternalReferenceCode(),
				ItemScopeUtil.getItemScope(
					fileEntry.getGroupId(), scopeGroupId));
		}

		String externalReferenceCode = jsonObject.getString(
			"externalReferenceCode");

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		return _getItemExternalReference(
			externalReferenceCode,
			ItemScopeUtil.getItemScope(
				companyId, jsonObject.getString("scopeExternalReferenceCode"),
				scopeGroupId));
	}

	public static JSONObject getFileEntryJSONObject(
			long companyId, String externalReferenceCode, Scope scope,
			long scopeGroupId)
		throws PortalException {

		FileEntry fileEntry = fetchFileEntryByExternalReferenceCode(
			companyId, externalReferenceCode, scope, scopeGroupId);

		if (fileEntry == null) {
			return JSONUtil.put(
				"className", FileEntry.class.getName()
			).put(
				"classNameId",
				PortalUtil.getClassNameId(FileEntry.class.getName())
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"scopeExternalReferenceCode",
				ItemScopeUtil.getItemScopeExternalReferenceCode(
					scope, scopeGroupId)
			);
		}

		return JSONUtil.put(
			"className", FileEntry.class.getName()
		).put(
			"classNameId",
			ClassNameLocalServiceUtil.getClassNameId(FileEntry.class.getName())
		).put(
			"classPK", String.valueOf(fileEntry.getFileEntryId())
		).put(
			"externalReferenceCode", externalReferenceCode
		).put(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId())
		).put(
			"scopeExternalReferenceCode",
			ItemScopeUtil.getItemScopeExternalReferenceCode(scope, scopeGroupId)
		).put(
			"title", fileEntry.getTitle()
		).put(
			"url", () -> _getImagePreviewURL(fileEntry)
		);
	}

	public static boolean isItemImageValue(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}

		if (Objects.equals(
				FileEntry.class.getName(), jsonObject.getString("className")) ||
			(Objects.equals(
				FileEntry.class.getName(),
				PortalUtil.fetchClassName(jsonObject.getLong("classNameId"))) &&
			 (jsonObject.has("classPK") ||
			  jsonObject.has("externalReferenceCode"))) ||
			jsonObject.has("fileEntryId")) {

			return true;
		}

		return false;
	}

	private static String _getImagePreviewURL(FileEntry fileEntry)
		throws PortalException {

		String mimeType = fileEntry.getMimeType();

		if (mimeType.startsWith("image")) {
			return DLURLHelperUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
		}

		return DLURLHelperUtil.getImagePreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
			false, false);
	}

	private static ItemExternalReference _getItemExternalReference(
		String externalReferenceCode, Scope scope) {

		ItemExternalReference itemExternalReference =
			new ItemExternalReference();

		itemExternalReference.setClassName(() -> FileEntry.class.getName());
		itemExternalReference.setExternalReferenceCode(
			() -> externalReferenceCode);
		itemExternalReference.setScope(() -> scope);

		return itemExternalReference;
	}

}