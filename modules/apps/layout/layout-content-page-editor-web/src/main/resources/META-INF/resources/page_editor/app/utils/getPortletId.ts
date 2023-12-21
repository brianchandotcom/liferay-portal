/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Props = {
	instanceId: string;
	portletId: string;
};

export default function getPortletId({instanceId, portletId}: Props) {
	return instanceId ? `${portletId}_INSTANCE_${instanceId}` : portletId;
}
