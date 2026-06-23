/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback} from 'react';

import SpaceSticker from '../../../common/components/SpaceSticker';
import SpaceService from '../../../common/services/SpaceService';
import ScopeMultiSelect, {ScopeItem as SpaceItem} from './ScopeMultiSelect';

export default function CategorizationSpaces({
	assetLibraries,
	checkboxText,
	setSelectedSpaces,
	setSpaceChange,
	setSpaceInputError,
	spaceInputError,
}: {
	assetLibraries?: any;
	checkboxText: string;
	setSelectedSpaces: (value: any) => void;
	setSpaceChange?: (value: boolean) => void;
	setSpaceInputError: (value: string) => void;
	spaceInputError: string;
}) {
	const getItems = useCallback(
		() =>
			SpaceService.getSpaces().then((response) =>
				response.map(
					(item): SpaceItem => ({
						displayType: item.settings?.logoColor,
						label: item.name,
						scopeKey: item.assetLibraryKey,
						value: item.id,
					})
				)
			),
		[]
	);

	return (
		<ScopeMultiSelect<SpaceItem>
			error={spaceInputError}
			getItems={getItems}
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
			preselectedItems={assetLibraries}
			renderItem={(item) => (
				<SpaceSticker
					displayType={item.displayType}
					name={item.label}
					size="sm"
				/>
			)}
			showErrorInitially={checkboxText !== 'vocabulary'}
		/>
	);
}
