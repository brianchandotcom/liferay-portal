/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';
import CurrentActions from './CurrentActions';

const ActionsSummary = ({setContentName}) => {
	const {selectedItem} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('actions')}>
			{!selectedItem?.data?.actions ? (
				<ClayButton
					aria-label={Liferay.Language.get('new-action')}
					className="mr-3"
					displayType="secondary"
					onClick={() => setContentName('actions')}
					title={Liferay.Language.get('new-action')}
				>
					{Liferay.Language.get('new')}
				</ClayButton>
			) : (
				<CurrentActions
					actions={selectedItem.data?.actions}
					setContentName={setContentName}
				/>
			)}
		</SidebarPanel>
	);
};

ActionsSummary.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default ActionsSummary;
