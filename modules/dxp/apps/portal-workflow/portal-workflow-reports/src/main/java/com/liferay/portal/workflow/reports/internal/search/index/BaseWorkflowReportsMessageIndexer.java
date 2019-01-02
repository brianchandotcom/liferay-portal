/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.reports.internal.search.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseWorkflowReportsMessageIndexer
	implements WorkflowReportsMessageIndexer {

	protected void addField(Document document, String name, Object value) {
		if (value instanceof Map<?, ?>) {
			Map<?, ?> valueMap = (Map<?, ?>)value;

			for (Map.Entry<?, ?> entry : valueMap.entrySet()) {
				addField(
					document,
					StringBundler.concat(
						name, StringPool.UNDERLINE, entry.getKey()),
					entry.getValue());
			}
		}
		else {
			document.add(new Field(name, String.valueOf(value)));
		}
	}

	protected void addLocalizedField(
		Document document, String name, Map<Locale, String> localizedValue) {

		document.add(new Field(name, localizedValue));
	}

	protected String digest(Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return DigestUtils.sha256Hex(sb.toString());
	}

	protected boolean matchAnyEvent(
		String value, String... workflowReportsEventNames) {

		if (ArrayUtil.isEmpty(workflowReportsEventNames)) {
			return false;
		}

		Stream<String> stream = Arrays.stream(workflowReportsEventNames);

		return stream.anyMatch(
			reportsEventName -> Objects.equals(reportsEventName, value));
	}

}