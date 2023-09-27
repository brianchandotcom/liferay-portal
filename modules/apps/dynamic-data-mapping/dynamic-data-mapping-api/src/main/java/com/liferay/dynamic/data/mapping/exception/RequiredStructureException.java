/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @author Isaac Obrist
 */
public class RequiredStructureException extends PortalException {

	public static class MustNotDeleteStructureReferencedByStructureLinks
		extends RequiredStructureException {

		public MustNotDeleteStructureReferencedByStructureLinks(
			long structureId) {

			super(
				String.format(
					"Structure %s cannot be deleted because it is referenced " +
						"by one or more structure links",
					structureId));

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
					structureId));

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
					structureId));

			this.structureId = structureId;
		}

		public long structureId;

	}

	private RequiredStructureException(String message) {
		super(message);
	}

}