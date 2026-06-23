/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class SystemVocabularyException extends PortalException {

	public static class MustNotChangeGroupRels
		extends SystemVocabularyException {

		public MustNotChangeGroupRels(long vocabularyId) {
			super(
				String.format(
					"Group rels of vocabulary %s cannot be changed",
					vocabularyId));

			this.vocabularyId = vocabularyId;
		}

		public long vocabularyId;

	}

	public static class MustNotDelete extends SystemVocabularyException {

		public MustNotDelete(long vocabularyId) {
			super(
				String.format("Vocabulary %s cannot be deleted", vocabularyId));

			this.vocabularyId = vocabularyId;
		}

		public long vocabularyId;

	}

	public static class MustNotModify extends SystemVocabularyException {

		public MustNotModify(long vocabularyId) {
			super(
				String.format(
					"Vocabulary %s cannot be modified", vocabularyId));

			this.vocabularyId = vocabularyId;
		}

		public long vocabularyId;

	}

	public static class MustNotRename extends SystemVocabularyException {

		public MustNotRename(long vocabularyId) {
			super(
				String.format("Vocabulary %s cannot be renamed", vocabularyId));

			this.vocabularyId = vocabularyId;
		}

		public long vocabularyId;

	}

	private SystemVocabularyException(String msg) {
		super(msg);
	}

}