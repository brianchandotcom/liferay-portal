/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.constants;

import com.liferay.portal.kernel.search.Field;

import java.util.Objects;

/**
 * @author Stefan Tanasie
 */
public class ContentDashboardConstants {

	public enum DateType {

		CREATE_DATE("create-date", Field.CREATE_DATE),
		DISPLAY_DATE("display-date", Field.DISPLAY_DATE),
		EXPIRATION_DATE("expiration-date", Field.EXPIRATION_DATE),
		MODIFIED_DATE("modified-date", Field.MODIFIED_DATE),
		PUBLISHED_DATE("published-date", Field.PUBLISH_DATE),
		REVIEW_DATE("review-date", "reviewDate");

		public static DateType parse(String type) {
			for (DateType dateType : values()) {
				if (Objects.equals(dateType.getType(), type)) {
					return dateType;
				}
			}

			return null;
		}

		public String getField() {
			return _field;
		}

		public String getType() {
			return _type;
		}

		@Override
		public String toString() {
			return _type;
		}

		private DateType(String type, String field) {
			_type = type;
			_field = field;
		}

		private final String _field;
		private final String _type;

	}

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