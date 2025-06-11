/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Badge from '@clayui/badge';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import {convertArrayToFormattedString} from '../util/convertArrayToFormattedString';
import SpaceSticker, {LogoColor} from './SpaceSticker';

export interface Space {
	logoColor: LogoColor;
	name: string;
}

interface SpaceDisplayProps {
	spaces: Space[];
}

export default function SpacesDisplay(props: SpaceDisplayProps) {
	const {spaces} = props;
	if (!spaces.length) {
		return null;
	}

	const firstSpace: Space = spaces[0];
	const otherSpaces: Space[] = spaces.slice(1);

	const firstSpaceElement = (
		<span className="align-items-center d-flex space-renderer-sticker">
			<SpaceSticker
				displayType={firstSpace.logoColor}
				name={firstSpace.name}
				size="sm"
			/>
		</span>
	);

	if (otherSpaces.length) {
		const spaceNamesInAString = convertArrayToFormattedString(
			spaces.map((space) => space.name)
		);
		const tooltipText = Liferay.Util.sub(
			Liferay.Language.get('available-in-spaces-x'),
			spaceNamesInAString
		);

		return (
			<span className="align-items-center c-gap-2 d-flex flex-wrap">
				{firstSpaceElement}

				<ClayTooltipProvider>

					{/* This fragment it's necessary to properly work with tooltip and badge component */}
					<>
						<Badge
							className="badge-pill"
							data-tooltip-align="bottom"
							displayType="secondary"
							label={`+${otherSpaces.length}`}
							style={{cursor: 'pointer'}}
							title={tooltipText}
						/>
					</>
				</ClayTooltipProvider>
			</span>
		);
	}

	return firstSpaceElement;
}
