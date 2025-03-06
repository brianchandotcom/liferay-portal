/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {getAssetsLibrariesByCompany} from '../api/api';
import SpaceSticker from '../components/SpaceSticker';

const MAX_NUMBER_SPACES = 5;

const SpacesNavigation: React.FC = () => {
	const [assetLibraries, setAssetsLibraries] = useState<
		{id: string; name: string}[]
	>([]);

	useEffect(() => {
		getAssetsLibrariesByCompany().then((result: any) => {
			setAssetsLibraries(result);
		});
	}, []);

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
				</ClayPanel.Title>
			}
			showCollapseIcon
		>
			<ClayPanel.Body className="py-0">
				<ul className="list-unstyled">
					{assetLibraries.slice(0, MAX_NUMBER_SPACES).map((space) => (
						<li className="mb-2" key={space.id}>
							<SpaceSticker name={space.name} />
						</li>
					))}

					<li>
						<span className="mr-2 sticker">
							<ClayIcon symbol="box-container" />
						</span>

						{sub(
							Liferay.Language.get('all-spaces-x'),
							assetLibraries.length
						)}
					</li>
				</ul>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default SpacesNavigation;
