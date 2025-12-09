/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {
	IItemSelectorProps,
	ItemSelector,
} from '@liferay/frontend-js-item-selector-web';
import React from 'react';

import {LogoColor, Space} from '../types/Space';
import SpaceSticker from './SpaceSticker';

interface ISpaceInputProps extends React.InputHTMLAttributes<HTMLInputElement> {
	spaceLogoColor?: LogoColor;
	spaceName?: string;
	value?: string;
}

export const SpaceInput = React.forwardRef<HTMLInputElement, ISpaceInputProps>(
	({spaceLogoColor, spaceName, value, ...otherProps}, ref) => {
		const showSticker = !!spaceName && !!value && !!value.length;

		return (
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						className="form-control-select"
						insetBefore={showSticker}
						ref={ref}
						type="text"
						value={value}
						{...otherProps}
					/>

					{showSticker && (
						<ClayInput.GroupInsetItem before>
							<SpaceSticker
								displayType={spaceLogoColor}
								hideName
								name={spaceName}
								size="sm"
							/>
						</ClayInput.GroupInsetItem>
					)}
				</ClayInput.GroupItem>
			</ClayInput.Group>
		);
	}
);

interface ISpaceSelectorProps
	extends Omit<
		IItemSelectorProps<Space>,
		'apiURL' | 'as' | 'items' | 'locator' | 'onItemsChange' | 'children'
	> {
	onSpaceChange: (space: Space) => void;
	space?: Space;
}

export default function SpaceSelector({
	onSpaceChange,
	space,
	...otherProps
}: ISpaceSelectorProps) {
	return (
		<ItemSelector<Space>
			apiURL={`${location.origin}/o/headless-asset-library/v1.0/asset-libraries?filter=type eq 'Space'`}
			as={SpaceInput}
			items={space ? [space] : []}
			locator={{
				id: 'id',
				label: 'name',
				value: 'externalReferenceCode',
			}}
			onItemsChange={(items) => {
				onSpaceChange(items[0]);
			}}
			onKeyDown={(event: React.KeyboardEvent<HTMLInputElement>) => {
				if (event.key === 'Enter') {
					event.preventDefault();
				}
			}}
			spaceLogoColor={space?.settings?.logoColor}
			spaceName={space?.name}
			{...otherProps}
		>
			{(space) => (
				<ItemSelector.Item
					key={space.externalReferenceCode}
					textValue={space.name}
				>
					<SpaceSticker
						displayType={space.settings?.logoColor}
						name={space.name}
						size="sm"
					/>
				</ItemSelector.Item>
			)}
		</ItemSelector>
	);
}
