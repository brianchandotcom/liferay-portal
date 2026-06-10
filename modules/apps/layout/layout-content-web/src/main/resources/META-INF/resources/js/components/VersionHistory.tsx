/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMediaQuery} from '@liferay/layout-js-components-web';
import React, {useState} from 'react';

import {Config, initializeConfig} from '../config';
import ResponsivePanel from './ResponsivePanel';
import Toolbar from './Toolbar';

const LARGE_MEDIA_QUERY = '(min-width: 992px)';

interface Props {
	config: Config;
}

export default function VersionHistory({config}: Props) {
	initializeConfig(config);

	const [isPanelOpen, setIsPanelOpen] = useState(true);

	const isScreenLarge = useMediaQuery(LARGE_MEDIA_QUERY);

	return (
		<>
			<Toolbar
				isSidePanelOpen={isPanelOpen || isScreenLarge}
				openSidePanel={() => setIsPanelOpen(true)}
			/>

			<ResponsivePanel
				onOpenChange={setIsPanelOpen}
				open={isPanelOpen || isScreenLarge}
			>
				<></>
			</ResponsivePanel>
		</>
	);
}
