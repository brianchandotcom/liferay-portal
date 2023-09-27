/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface IDisconnectModalProps {
	observer: any;
	onOpenChange: (value: boolean) => void;
}
declare const DisconnectModal: React.FC<IDisconnectModalProps>;
export default DisconnectModal;
