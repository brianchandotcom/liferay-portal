/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NotificationTemplateSourceDataRenderer} from './NotificationTemplateSourceDataRenderer';

export default function propsTransformer({...otherProps}) {
	return {
		...otherProps,
		customDataRenderers: {
			notificationTemplateSourceDataRenderer:
				NotificationTemplateSourceDataRenderer,
		},
	};
}
