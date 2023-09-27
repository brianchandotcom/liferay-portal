/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
