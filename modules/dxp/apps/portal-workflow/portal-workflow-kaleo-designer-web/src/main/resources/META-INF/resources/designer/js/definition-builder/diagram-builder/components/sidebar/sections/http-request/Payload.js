/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';

const Payload = () => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('payload')}>
			<ClayForm.Group>
				<label htmlFor="requestBody">
					{Liferay.Language.get('request-body')}
				</label>

				<ClayInput
					component="textarea"
					id="requestBody"
					onChange={({target}) => {
						if (!selectedItem) {
							return;
						}

						setSelectedItem({
							...selectedItem,
							data: {
								...selectedItem.data,
								requestBody: target.value,
							},
						});
					}}
					placeholder="{{inputVariable}}"
					type="text"
					value={selectedItem?.data.requestBody ?? ''}
				/>
			</ClayForm.Group>
		</SidebarPanel>
	);
};

Payload.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default Payload;
