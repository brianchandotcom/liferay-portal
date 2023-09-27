/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TProperty} from './Properties';
interface IChannelTabProps {
	initialIds: number[];
	onChannelsChange: (ids: number[]) => void;
	property: TProperty;
}
declare const ChannelTab: React.FC<IChannelTabProps>;
export default ChannelTab;
