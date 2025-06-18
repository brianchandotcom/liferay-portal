/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import Toolbar from '../../common/components/Toolbar';
import SpaceService from '../../services/SpaceService';
import {Space} from '../../types/Space';

interface SpaceSettingsProps {
	backURL: string;
	depotEntryId: string;
}

export default function SpaceSettings({
	backURL,
	depotEntryId,
}: SpaceSettingsProps) {
	const [space, setSpace] = useState<Space | null>(null);

	useEffect(() => {
		SpaceService.getSpace({spaceId: depotEntryId}).then((space) => {
			setSpace(space);
		});
	}, [depotEntryId]);

	if (!space) {
		return null;
	}

	return (
		<Toolbar
			backURL={backURL}
			title={sub(Liferay.Language.get('x-settings'), space.name)}
		/>
	);
}
