/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openSocialBookmark from './openSocialBookmark';

export default function propsTransformer({
	additionalProps: {className, classPK, postURL, type, url},
	...otherProps
}) {
	return {
		...otherProps,
		onClick(event) {
			event.preventDefault();
			event.stopPropagation();

			return openSocialBookmark({className, classPK, postURL, type, url});
		},
	};
}
