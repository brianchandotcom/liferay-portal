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

package com.liferay.dynamic.data.mapping.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @author Isaac Obrist
 */
public class RequiredStructureException extends PortalException {

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	public static final int REFERENCED_STRUCTURE = 1;

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	public static final int REFERENCED_STRUCTURE_LINK = 2;

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	public static final int REFERENCED_TEMPLATE = 3;

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	public RequiredStructureException(int type) {
		_type = type;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	public int getType() {
		return _type;
	}

	public static class MustNotDeleteStructureReferencedByStructureLinks
		extends RequiredStructureException {

		public MustNotDeleteStructureReferencedByStructureLinks(
			long structureId) {

			super(
				String.format(
					"Structure %s cannot be deleted because it is referenced " +
						"by one or more structure links",
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
					"Structure %s cannot be deleted because it is referenced " +
						"by one or more templates",
					structureId),
				REFERENCED_TEMPLATE);

			this.structureId = structureId;
		}

		public long structureId;

	}

	public static class MustNotDeleteStructureThatHasChild
		extends RequiredStructureException {

		public MustNotDeleteStructureThatHasChild(long structureId) {
			super(
				String.format(
					"Structure %s cannot be deleted because it has child " +
						"structures",
					structureId),
				REFERENCED_STRUCTURE);

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