/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';

const CurrentNotifications = ({notifications, setContentName}) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);

	const deleteCurrentNotifications = () => {
		setSelectedItem((previousValue) => ({
			...previousValue,
			data: {
				...previousValue.data,
				notifications: null,
			},
		}));
	};

	const getNotificationsDetails = () => {
		return [`${notifications['name']}`];
	};

	return (
		<ClayLayout.ContentCol className="current-node-data-area" float>
			<ClayLayout.Row className="current-node-data-row" justify="between">
				<ClayLink
					button={false}
					className="truncate-container"
					displayType="secondary"
					href="#"
					onClick={() => setContentName('notifications')}
				>
					{getNotificationsDetails().map((name, index) => (
						<span key={index}>{name}</span>
					))}
				</ClayLink>

				<ClayButtonWithIcon
					className="delete-button text-secondary trash-button"
					displayType="unstyled"
					onClick={deleteCurrentNotifications}
					symbol="trash"
				/>
			</ClayLayout.Row>
		</ClayLayout.ContentCol>
	);
};

export default CurrentNotifications;
