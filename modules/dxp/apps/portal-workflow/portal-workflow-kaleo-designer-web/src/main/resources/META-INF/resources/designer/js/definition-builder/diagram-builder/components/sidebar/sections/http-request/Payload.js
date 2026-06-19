/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';
import {getUpdatedDataItem} from '../utils';

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
					onChange={({target}) =>
						setSelectedItem(
							getUpdatedDataItem(
								'requestBody',
								selectedItem,
								target
							)
						)
					}
					placeholder="{{inputVariable}}"
					type="text"
					value={selectedItem?.data.requestBody ?? ''}
				/>
			</ClayForm.Group>
		</SidebarPanel>
	);
};

export default Payload;
