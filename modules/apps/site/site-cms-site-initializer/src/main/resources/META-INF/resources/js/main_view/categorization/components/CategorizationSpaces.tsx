/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useMemo, useState} from 'react';

import SpaceService from '../../../common/services/SpaceService';
import ScopeMultiSelect, {ScopeItem as SpaceItem} from './ScopeMultiSelect';

export default function CategorizationSpaces({
	assetLibraries,
	checkboxText,
	disabled = false,
	setSelectedSpaces,
	setSpaceChange,
	setSpaceInputError,
}: {
	assetLibraries?: any;
	checkboxText: string;
	disabled?: boolean;
	setSelectedSpaces: (value: any) => void;
	setSpaceChange?: (value: boolean) => void;
	setSpaceInputError: (value: string) => void;
}) {
	const [sourceItems, setSourceItems] = useState<SpaceItem[]>([]);

	useEffect(() => {
		const loadSpaces = async () => {
			const response = await SpaceService.getSpaces();

			setSourceItems(
				response.map(
					(item): SpaceItem => ({
						displayType: item.settings?.logoColor,
						label: item.name,
						scopeKey: item.assetLibraryKey,
						value: item.id,
					})
				)
			);
		};

		loadSpaces();
	}, []);

	const preselectedItems = useMemo(() => {
		if (
			assetLibraries?.some((assetLibrary: any) => assetLibrary.id === -1)
		) {
			return [];
		}

		const scopeKeys = assetLibraries?.map(
			(assetLibrary: any) => assetLibrary.scopeKey
		);

		return sourceItems.filter((item) => scopeKeys?.includes(item.scopeKey));
	}, [assetLibraries, sourceItems]);

	return (
		<ScopeMultiSelect<SpaceItem>
			disabled={disabled}
			labels={{
				allItemsValue: Liferay.Language.get('all-spaces'),
				ariaLabel: Liferay.Language.get('space-selector'),
				checkbox:
					checkboxText === 'tag'
						? Liferay.Language.get(
								'make-this-tag-available-in-all-spaces'
							)
						: Liferay.Language.get(
								'make-this-vocabulary-available-in-all-spaces'
							),
				field: Liferay.Language.get('space'),
			}}
			onChange={setSpaceChange}
			onError={setSpaceInputError}
			onSelectionChange={setSelectedSpaces}
			preselectedItems={preselectedItems}
			sourceItems={sourceItems}
		/>
	);
}
