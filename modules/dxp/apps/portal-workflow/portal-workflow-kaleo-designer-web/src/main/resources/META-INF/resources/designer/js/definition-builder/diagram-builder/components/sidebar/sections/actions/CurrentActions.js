/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';

const CurrentActions = ({actions, setContentName}) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);

	const deleteCurrentActions = () => {
		setSelectedItem((previousValue) => ({
			...previousValue,
			data: {
				...previousValue.data,
				actions: null,
			},
		}));
	};

	const getActionsDetails = () => {
		return [`${actions['name']}`];
	};

	return (
		<ClayLayout.ContentCol className="current-node-data-area" float>
			<ClayLayout.Row className="current-node-data-row" justify="between">
				<ClayLink
					button={false}
					className="truncate-container"
					displayType="secondary"
					href="#"
					onClick={() => setContentName('actions')}
				>
					{getActionsDetails().map((name, index) => (
						<span key={index}>{name}</span>
					))}
				</ClayLink>

				<ClayButtonWithIcon
					className="delete-button text-secondary trash-button"
					displayType="unstyled"
					onClick={deleteCurrentActions}
					symbol="trash"
				/>
			</ClayLayout.Row>
		</ClayLayout.ContentCol>
	);
};

export default CurrentActions;
