/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export default function concatPageSizePagination(keyArgs = false) {
	return {
		keyArgs,
		merge(existing = [], incoming, {variables}) {
			const merged = [...existing];

			if (variables) {
				const {page = 1, pageSize = 20} = variables;
				const offset = (page - 1) * pageSize;

				for (let i = 0; i < incoming.length; ++i) {
					merged[offset + i] = incoming[i];
				}

				return merged;
			}

			return merged.concat(incoming);
		},
	};
}
