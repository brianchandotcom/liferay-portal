/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Leonardo Barros
 */
@ProviderType
public interface DDMStructureManager {

	public static final String STRUCTURE_INDEXER_FIELD_NAMESPACE = "ddm";

	public static final String STRUCTURE_INDEXER_FIELD_PREFIX =
		STRUCTURE_INDEXER_FIELD_NAMESPACE +
			DDMStructureManager.STRUCTURE_INDEXER_FIELD_SEPARATOR;

	public static final String STRUCTURE_INDEXER_FIELD_SEPARATOR =
		StringPool.DOUBLE_UNDERLINE;

	public static final int STRUCTURE_TYPE_AUTO = 1;

	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException;

	public void deleteStructure(long structureId) throws PortalException;

	public DDMStructure fetchStructure(long structureId);

	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey);

	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId);

	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, ServiceContext serviceContext)
		throws PortalException;

	public void updateStructureKey(long structureId, String structureKey)
		throws PortalException;

}