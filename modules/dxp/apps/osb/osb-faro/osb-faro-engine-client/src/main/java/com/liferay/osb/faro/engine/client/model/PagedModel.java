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

package com.liferay.osb.faro.engine.client.model;

import com.liferay.petra.function.transform.TransformUtil;

/**
 * @author Shinn Lok
 */
public abstract class PagedModel<T, R>
	extends org.springframework.hateoas.PagedModel<T> {

	public Results<R> getResults() {
		Results<R> results = new Results<>();

		results.setItems(
			TransformUtil.transform(
				getContent(), content -> processContent(content)));

		PageMetadata pageMetadata = getMetadata();

		if (pageMetadata != null) {
			results.setTotal((int)pageMetadata.getTotalElements());
		}

		return results;
	}

	public abstract R processContent(T content);

}