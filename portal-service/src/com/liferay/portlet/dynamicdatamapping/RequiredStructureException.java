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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @author Isaac Obrist
 */
public class RequiredStructureException extends PortalException {

	@Deprecated
	public static final int REFERENCED_STRUCTURE = 1;

	@Deprecated
	public static final int REFERENCED_STRUCTURE_LINK = 2;

	@Deprecated
	public static final int REFERENCED_TEMPLATE = 3;

	@Deprecated
	public RequiredStructureException(int type) {
		_type = type;
	}

	@Deprecated
	public int getType() {
		return _type;
	}

	public static class MustNotDeleteReferencedStructure
		extends RequiredStructureException {

		public MustNotDeleteReferencedStructure(long structureId) {
			super(
				String.format(
					"Structure %s is required and cannot be deleted.",
					structureId),
				REFERENCED_STRUCTURE);

			this.structureId = structureId;
		}

		public long structureId;

	}

	public static class MustNotDeleteReferencedStructureLink
		extends RequiredStructureException {

		public MustNotDeleteReferencedStructureLink(long structureId) {
			super(
				String.format(
					"Structure link %s is required and cannot be deleted.",
					structureId),
				REFERENCED_STRUCTURE_LINK);

			this.structureId = structureId;
		}

		public long structureId;

	}

	public static class MustNotDeleteStructureReferencedByTemplates
		extends RequiredStructureException {

		public MustNotDeleteStructureReferencedByTemplates(long structureId) {
			super(
				String.format(
					"Structure %s is referenced by templates and cannot be " +
							"deleted.",
					structureId),
				REFERENCED_TEMPLATE);

			this.structureId = structureId;
		}

		public long structureId;

	}

	private RequiredStructureException(String message, int type) {
		super(message);

		_type = type;
	}

	private final int _type;

}