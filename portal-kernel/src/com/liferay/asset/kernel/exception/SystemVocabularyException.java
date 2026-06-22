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

	public static class MustNotChangeSystemVocabularyGroupRels
		extends SystemVocabularyException {

		public MustNotChangeSystemVocabularyGroupRels(long vocabularyId) {
			super(
				String.format(
					"The spaces of the system vocabulary %s cannot be changed",
					vocabularyId));

			this.vocabularyId = vocabularyId;
		}

		public long vocabularyId;

	}

	public static class MustNotDeleteSystemVocabulary
		extends SystemVocabularyException {

		public MustNotDeleteSystemVocabulary(long vocabularyId) {
			super(
				String.format(
					"The system vocabulary %s cannot be deleted",
					vocabularyId));

			this.vocabularyId = vocabularyId;
		}

		public long vocabularyId;

	}

	public static class MustNotModifySystemVocabulary
		extends SystemVocabularyException {

		public MustNotModifySystemVocabulary(long vocabularyId) {
			super(
				String.format(
					"Only the multivalued setting of the system vocabulary " +
						"%s can be modified",
					vocabularyId));

			this.vocabularyId = vocabularyId;
		}

		public long vocabularyId;

	}

	public static class MustNotRenameSystemVocabulary
		extends SystemVocabularyException {

		public MustNotRenameSystemVocabulary(long vocabularyId) {
			super(
				String.format(
					"The system vocabulary %s cannot be renamed",
					vocabularyId));

			this.vocabularyId = vocabularyId;
		}

		public long vocabularyId;

	}

	private SystemVocabularyException(String msg) {
		super(msg);
	}

}