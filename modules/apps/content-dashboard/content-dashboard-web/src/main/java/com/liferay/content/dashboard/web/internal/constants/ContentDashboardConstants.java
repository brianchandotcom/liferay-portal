/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.constants;

/**
 * @author Stefan Tanasie
 */
public class ContentDashboardConstants {

	public enum DefaultInternalAssetVocabularyName {

		AUDIENCE("audience"), STAGE("stage");

		@Override
		public String toString() {
			return _internalVocabularyName;
		}

		private DefaultInternalAssetVocabularyName(
			String internalVocabularyName) {

			_internalVocabularyName = internalVocabularyName;
		}

		private final String _internalVocabularyName;

	}

}