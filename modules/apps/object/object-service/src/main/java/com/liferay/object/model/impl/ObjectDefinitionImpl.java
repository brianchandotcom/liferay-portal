/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.definition.util.ObjectDefinitionUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFolderLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectDefinitionImpl extends ObjectDefinitionBaseImpl {

	public static String getShortName(String name) {
		String shortName = name;

		if (shortName.startsWith("C_")) {
			shortName = shortName.substring(2);
		}

		return shortName;
	}

	@Override
	public String getDestinationName() {
		return StringBundler.concat(
			"liferay/object/", getCompanyId(), StringPool.SLASH,
			getShortName());
	}

	@Override
	public String getExtensionDBTableName() {

		// See DBInspector.java#isObjectTable

		if (isUnmodifiableSystemObject()) {
			String extensionDBTableName = getDBTableName();

			if (extensionDBTableName.endsWith("_")) {
				extensionDBTableName += "x_";
			}
			else {
				extensionDBTableName += "_x_";
			}

			extensionDBTableName += getCompanyId();

			return extensionDBTableName;
		}

		return getDBTableName() + "_x";
	}

	@Override
	public String getLocalizationDBTableName() {
		if (!isEnableLocalization()) {
			return null;
		}

		return getDBTableName() + "_l";
	}

	@Override
	public String getObjectFolderExternalReferenceCode() {
		ObjectFolder objectFolder =
			ObjectFolderLocalServiceUtil.fetchObjectFolder(getObjectFolderId());

		if (objectFolder == null) {
			return null;
		}

		return objectFolder.getExternalReferenceCode();
	}

	@Override
	public String getOSGiJaxRsName() {
		return getOSGiJaxRsName(StringPool.BLANK);
	}

	@Override
	public String getOSGiJaxRsName(String className) {
		return getName() + className;
	}

	@Override
	public String getPortletId() {
		if (isUnmodifiableSystemObject()) {
			throw new UnsupportedOperationException();
		}

		return "com_liferay_object_web_internal_object_definitions_portlet_" +
			"ObjectDefinitionsPortlet_" + getObjectDefinitionId();
	}

	@Override
	public String getResourceName() {
		if (isUnmodifiableSystemObject()) {
			throw new UnsupportedOperationException();
		}

		return "com.liferay.object#" + getObjectDefinitionId();
	}

	@Override
	public String getRESTContextPath() {
		if (isUnmodifiableSystemObject()) {
			throw new UnsupportedOperationException();
		}

		if (isModifiable() && isSystem()) {
			return ObjectDefinitionUtil.
				getModifiableSystemObjectDefinitionRESTContextPath(getName());
		}

		String shortName = TextFormatter.formatPlural(
			StringUtil.toLowerCase(getShortName()));

		if (!isRootDescendantNode()) {
			return "/c/" + shortName;
		}

		ObjectDefinition rootObjectDefinition =
			ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				getRootObjectDefinitionId());

		return StringBundler.concat(
			"/c/",
			TextFormatter.formatPlural(
				StringUtil.toLowerCase(rootObjectDefinition.getShortName())),
			StringPool.SLASH, shortName);
	}

	@Override
	public String getShortName() {
		return getShortName(getName());
	}

	@Override
	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDefaultStorageType() {
		if (Objects.equals(
				getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT)) {

			return true;
		}

		return false;
	}

	public boolean isLinkedToObjectFolder(long objectFolderId) {
		if (getObjectFolderId() == objectFolderId) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isRootDescendantNode() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-187142")) {
			return false;
		}

		if ((getRootObjectDefinitionId() > 0) && !isRootNode()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRootNode() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-187142")) {
			return false;
		}

		if (getObjectDefinitionId() == getRootObjectDefinitionId()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isUnmodifiableSystemObject() {
		if (FeatureFlagManagerUtil.isEnabled("LPS-167253")) {
			if (!isModifiable() && isSystem()) {
				return true;
			}

			return false;
		}

		return isSystem();
	}

}