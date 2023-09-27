/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Rafael Praxedes
 */
@ProviderType
public interface DDMStructureLinkManager {

	public DDMStructureLink addStructureLink(
		long classNameId, long classPK, long structureId);

	public void deleteStructureLink(
			long classNameId, long classPK, long structureId)
		throws PortalException;

	public void deleteStructureLinks(long classNameId, long classPK);

	public List<DDMStructureLink> getStructureLinks(long structureId);

	public List<DDMStructureLink> getStructureLinks(
		long classNameId, long classPK);

}