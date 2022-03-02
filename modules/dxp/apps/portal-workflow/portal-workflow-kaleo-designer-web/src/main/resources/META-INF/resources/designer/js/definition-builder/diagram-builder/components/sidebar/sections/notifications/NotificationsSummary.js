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

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';
import CurrentNotifications from './CurrentNotifications';

const NotificationsSummary = ({setContentName}) => {
	const {selectedItem} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('notifications')}>
			{!selectedItem?.data.notifications ? (
				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() => setContentName('notifications')}
				>
					{Liferay.Language.get('new')}
				</ClayButton>
			) : (
				<CurrentNotifications
					notifications={selectedItem.data.notifications}
					setContentName={setContentName}
				/>
			)}
		</SidebarPanel>
	);
};

NotificationsSummary.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default NotificationsSummary;
