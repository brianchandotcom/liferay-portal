/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectEntryGroupIdException extends PortalException {

	public String getMessageKey() {
		return _messageKey;
	}

	public static class InvalidGroupIdForDomain
		extends ObjectEntryGroupIdException {

		public InvalidGroupIdForDomain(long groupId, String domain) {
			super(
				StringBundler.concat(
					"Group ID ", groupId, " is not valid for domain \"", domain,
					"\""),
				"group-id-x-is-not-valid-for-domain-x");
		}

	}

	public static class InvalidGroupIdForAssetCategory
		extends ObjectEntryGroupIdException {

		public InvalidGroupIdForAssetCategory(long assetCategoryId) {
			super(
				String.format(
					"Asset category %d does not belong to the object entry's " +
						"scope",
					assetCategoryId),
				"asset-category-x-does-not-belong-to-the-object-entrys-scope");
		}

	}

	public static class InvalidGroupIdForScope
		extends ObjectEntryGroupIdException {

		public InvalidGroupIdForScope(long groupId, String scope) {
			super(
				StringBundler.concat(
					"Group ID ", groupId, " is not valid for scope \"", scope,
					"\""),
				"group-id-x-is-not-valid-for-scope-x");
		}

	}

	public static class MustShareSameGroupId
		extends ObjectEntryGroupIdException {

		public MustShareSameGroupId() {
			super(
				"Object entries within the same scope must share the same " +
					"group ID to be related",
				"object-entries-within-the-same-scope-must-share-the-same-" +
					"group-id-to-be-related");
		}

	}

	protected ObjectEntryGroupIdException(String messageKey) {
		_messageKey = messageKey;
	}

	protected ObjectEntryGroupIdException(String message, String messageKey) {
		super(message);

		_messageKey = messageKey;
	}

	private final String _messageKey;

}
