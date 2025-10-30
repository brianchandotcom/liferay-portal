/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field;

import com.liferay.petra.lang.HashUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author Víctor Galán
 */
public class RelatedInfoFieldValue<T> {

	public RelatedInfoFieldValue(
		Map<RelatedInfoFieldValueIdentifier, InfoFieldValue<T>>
			relatedInfoFieldValues) {

		_relatedInfoFieldValues = relatedInfoFieldValues;
	}

	public InfoFieldValue<T> getInfoFieldValue(
		String externalReferenceCode, String parentExternalReferenceCode) {

		return _relatedInfoFieldValues.get(
			new RelatedInfoFieldValueIdentifier(
				externalReferenceCode, parentExternalReferenceCode));
	}

	public Map<RelatedInfoFieldValueIdentifier, InfoFieldValue<T>>
		getRelatedInfoFieldValues() {

		return _relatedInfoFieldValues;
	}

	public static class RelatedInfoFieldValueIdentifier {

		public RelatedInfoFieldValueIdentifier(
			String externalReferenceCode, String parentExternalReferenceCode) {

			_externalReferenceCode = externalReferenceCode;
			_parentExternalReferenceCode = parentExternalReferenceCode;
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof RelatedInfoFieldValueIdentifier)) {
				return false;
			}

			RelatedInfoFieldValueIdentifier relatedInfoFieldValueIdentifier =
				(RelatedInfoFieldValueIdentifier)object;

			if (Objects.equals(
					_externalReferenceCode,
					relatedInfoFieldValueIdentifier._externalReferenceCode) &&
				Objects.equals(
					_parentExternalReferenceCode,
					relatedInfoFieldValueIdentifier.
						_parentExternalReferenceCode)) {

				return true;
			}

			return false;
		}

		public String getExternalReferenceCode() {
			return _externalReferenceCode;
		}

		public String getParentExternalReferenceCode() {
			return _parentExternalReferenceCode;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _externalReferenceCode);

			return HashUtil.hash(hashCode, _parentExternalReferenceCode);
		}

		private final String _externalReferenceCode;
		private final String _parentExternalReferenceCode;

	}

	private final Map<RelatedInfoFieldValueIdentifier, InfoFieldValue<T>>
		_relatedInfoFieldValues;

}