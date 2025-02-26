/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayPanel from '@clayui/panel';
import React from 'react';

const SpacesWidget: React.FC = () => {
	const onAddButtonClick = (event: any) => {
		event.preventDefault();
		event.stopPropagation();
	};

	return (
		<ClayPanel
			collapsable
			displayTitle={
				<ClayPanel.Title>
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
			<ClayPanel.Body>Here goes the spaces list</ClayPanel.Body>
		</ClayPanel>
	);
};

export default SpacesWidget;
