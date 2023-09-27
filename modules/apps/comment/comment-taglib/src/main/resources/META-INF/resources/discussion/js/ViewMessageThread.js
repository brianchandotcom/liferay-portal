/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {toggleDisabled} from 'frontend-js-web';

export default function ViewMessageThread({
	index,
	portletNamespace: namespace,
}) {
	window[`${namespace}${index}EditOnChange`] = function (html) {
		toggleDisabled(
			`#${namespace}editReplyButton${index}`,
			html.trim() === ''
		);
	};

	window[`${namespace}${index}ReplyOnChange`] = function (html) {
		toggleDisabled(
			`#${namespace}postReplyButton${index}`,
			html.trim() === ''
		);
	};
}
