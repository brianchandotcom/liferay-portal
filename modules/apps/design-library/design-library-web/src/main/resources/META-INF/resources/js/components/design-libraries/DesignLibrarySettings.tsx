/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import DesignLibraryService from '../../services/DesignLibraryService';
import {DesignLibrary} from '../../types';
import DesignLibraryGeneralSettings from './DesignLibraryGeneralSettings';

interface DesignLibrarySettingsProps {
	externalReferenceCode: string;
	groupId: string;
}

export default function DesignLibrarySettings({
	externalReferenceCode,
	groupId,
}: DesignLibrarySettingsProps) {
	const [designLibrary, setDesignLibrary] = useState<DesignLibrary | null>(
		null
	);

	useEffect(() => {
		DesignLibraryService.get(externalReferenceCode).then(
			(designLibrary) => {
				setDesignLibrary(designLibrary);
			}
		);
	}, [externalReferenceCode]);

	if (!designLibrary) {
		return null;
	}

	return (
		<>
			<DesignLibraryGeneralSettings
				designLibrary={designLibrary}
				groupId={groupId}
				setDesignLibrary={setDesignLibrary}
			/>
		</>
	);
}
