/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayPanel from '@clayui/panel';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SpaceService from '../../structure_builder/services/SpaceService';
import SpaceSticker from '../components/SpaceSticker';

interface AssetLibrary {
	id: number;
	name: string;
	url: string;
}

interface SpacesNavigationProps {
	assetLibraries: AssetLibrary[];
	assetLibrariesCount: number;
	showAddButton: boolean;
}

const SpacesNavigation: React.FC<SpacesNavigationProps> = ({
	assetLibraries,
	assetLibrariesCount,
	showAddButton,
}) => {
	const onAddButtonClick = (event: any) => {
		event.preventDefault();
		event.stopPropagation();
	};

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={
				<ClayPanel.Title className="align-items-center d-flex font-weight-semi-bold justify-content-between text-2 text-uppercase">
					<span>{Liferay.Language.get('spaces')}</span>

					{showAddButton && (
						<span className="float-right mr-2">
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('add-space')}
								displayType="secondary"
								onClick={onAddButtonClick}
								size="sm"
								symbol="plus"
								title={Liferay.Language.get('add-space')}
								type="button"
							/>
						</span>
					)}
				</ClayPanel.Title>
			}
			showCollapseIcon
		>
			<ClayPanel.Body className="p-0">
				<ul className="menubar-primary nav nav-stacked" role="menu">
					{assetLibraries.map((assetLibrary) => (
						<li className="nav-item" key={assetLibrary.id}>
							<ClayLink className="nav-link" href={assetLibrary.url}>
								<SpaceSticker name={assetLibrary.name} />
							</ClayLink>
						</li>
					))}

					<li className="nav-item" role="none">
						<ClayLink
							className="nav-link"
							href="/web/cms/all-spaces"
						>
							<span className="mr-2 sticker">
								<ClayIcon symbol="box-container" />
							</span>

							{sub(
								Liferay.Language.get('all-spaces-x'),
								assetLibrariesCount
							)}
						</ClayLink>
					</li>
				</ul>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default SpacesNavigation;
