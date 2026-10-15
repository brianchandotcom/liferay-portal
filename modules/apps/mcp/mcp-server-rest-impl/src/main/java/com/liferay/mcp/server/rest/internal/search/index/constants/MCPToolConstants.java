/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class MCPToolConstants {

	public static final String OPERATION_VARIANT_APPROVED = "approved";

	public static final String OPERATION_VARIANT_BATCH = "batch";

	public static final String OPERATION_VARIANT_COPY = "copy";

	public static final String OPERATION_VARIANT_EXPIRE = "expire";

	public static final String OPERATION_VARIANT_HISTORY = "history";

	public static final String OPERATION_VARIANT_KEYED = "keyed";

	public static final String OPERATION_VARIANT_MOVE = "move";

	public static final String OPERATION_VARIANT_NESTED = "nested";

	public static final String OPERATION_VARIANT_OPENAPI = "openapi";

	public static final String OPERATION_VARIANT_PERMISSIONS = "permissions";

	public static final String OPERATION_VARIANT_PREVIEW = "preview";

	public static final String OPERATION_VARIANT_RATING = "rating";

	public static final String OPERATION_VARIANT_RESTORE = "restore";

	public static final String OPERATION_VARIANT_SUBSCRIPTION = "subscription";

	public static final String OPERATION_VARIANT_TRANSLATION = "translation";

	public static final String OPERATION_VARIANT_TRAVERSAL = "traversal";

	public static final String OPERATION_VARIANT_VALIDATE = "validate";

	public static final Map<String, String> pathSegmentOperationVariants =
		Collections.unmodifiableMap(
			HashMapBuilder.put(
				"approved", OPERATION_VARIANT_APPROVED
			).put(
				"batch", OPERATION_VARIANT_BATCH
			).put(
				"by-key", OPERATION_VARIANT_KEYED
			).put(
				"by-uuid", OPERATION_VARIANT_KEYED
			).put(
				"copy", OPERATION_VARIANT_COPY
			).put(
				"expire", OPERATION_VARIANT_EXPIRE
			).put(
				"export-batch", OPERATION_VARIANT_BATCH
			).put(
				"export-preview", OPERATION_VARIANT_PREVIEW
			).put(
				"friendly-url-history", OPERATION_VARIANT_HISTORY
			).put(
				"import-preview", OPERATION_VARIANT_PREVIEW
			).put(
				"move", OPERATION_VARIANT_MOVE
			).put(
				"my-rating", OPERATION_VARIANT_RATING
			).put(
				"openapi", OPERATION_VARIANT_OPENAPI
			).put(
				"permissions", OPERATION_VARIANT_PERMISSIONS
			).put(
				"preview", OPERATION_VARIANT_PREVIEW
			).put(
				"rated-by-me", OPERATION_VARIANT_RATING
			).put(
				"restore", OPERATION_VARIANT_RESTORE
			).put(
				"subscribe", OPERATION_VARIANT_SUBSCRIPTION
			).put(
				"translation", OPERATION_VARIANT_TRANSLATION
			).put(
				"translations", OPERATION_VARIANT_TRANSLATION
			).put(
				"unsubscribe", OPERATION_VARIANT_SUBSCRIPTION
			).put(
				"validate", OPERATION_VARIANT_VALIDATE
			).build());

}