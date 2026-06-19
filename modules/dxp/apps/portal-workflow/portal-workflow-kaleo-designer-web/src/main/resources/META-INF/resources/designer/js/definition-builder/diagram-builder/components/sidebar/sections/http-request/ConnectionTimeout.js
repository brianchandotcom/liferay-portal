/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';

const ConnectionTimeout = () => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('connection-timeout')}>
			<ClayForm.Group>
				<label htmlFor="timeout">
					{Liferay.Language.get('value-in-milliseconds')}
				</label>

				<ClayInput
					id="timeout"
					min="0"
					onChange={({target}) => {
						if (!selectedItem) {
							return;
						}

						setSelectedItem({
							...selectedItem,
							data: {
								...selectedItem.data,
								timeout: target.value,
							},
						});
					}}
					onWheel={(event) => event.target.blur()}
					placeholder="1000"
					step="1"
					type="number"
					value={selectedItem?.data.timeout ?? ''}
				/>
			</ClayForm.Group>
		</SidebarPanel>
	);
};

ConnectionTimeout.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default ConnectionTimeout;
