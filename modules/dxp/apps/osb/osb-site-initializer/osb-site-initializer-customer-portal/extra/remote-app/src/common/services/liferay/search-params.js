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

const liferaySearchParams = new URLSearchParams(window.location.search);

export const SearchParams = {
	get: (param) => liferaySearchParams.get(param),
};

export const PARAMS_KEYS = {
	PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE: 'kor_id',
};
