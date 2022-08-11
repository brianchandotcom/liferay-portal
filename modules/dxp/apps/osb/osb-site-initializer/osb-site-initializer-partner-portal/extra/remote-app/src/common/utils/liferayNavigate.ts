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

import {PRMPageRoute} from '../enums/prmPageRoute';
import {Liferay} from '../services/liferay';

export default function liferayNavigate(path: PRMPageRoute) {
	const relativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();
	const relativePath = relativeURL.split('/').slice(0, 3);

	relativePath.push(path);

	Liferay.Util.navigate(relativePath.join('/'));
}
