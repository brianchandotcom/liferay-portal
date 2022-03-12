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