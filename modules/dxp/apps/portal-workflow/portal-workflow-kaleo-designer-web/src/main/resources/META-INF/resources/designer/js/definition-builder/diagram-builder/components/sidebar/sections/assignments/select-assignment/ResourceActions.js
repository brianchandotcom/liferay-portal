/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import SidebarPanel from '../../../SidebarPanel';

const ResourceActions = () => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);
	const [resourceActions, setResourceActions] = useState('');

	const onChange = ({target: {value}}) => {
		setSelectedItem((previousValue) => ({
			...previousValue,
			data: {
				...previousValue.data,
				assignments: value
					? {
							assignmentType: ['resourceActions'],
							resourceAction: value,
					  }
					: null,
			},
		}));

		setResourceActions(value);
	};

	useEffect(() => {
		setResourceActions(selectedItem.data.assignments?.resourceAction || '');
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('resource-actions')}>
			<ClayForm.Group>
				<label htmlFor="resource-actions">
					{Liferay.Language.get('resource-actions')}

					<span className="ml-1 mr-1 text-warning">*</span>

					<span
						data-tooltip-align="bottom-right"
						title={Liferay.Language.get(
							'enter-the-comma-separated-resource-actions'
						)}
					>
						<ClayIcon
							className="text-muted"
							symbol="question-circle-full"
						/>
					</span>
				</label>

				<ClayInput
					component="textarea"
					id="resource-actions"
					onChange={onChange}
					placeholder={Liferay.Language.get(
						'type-resource-actions-here'
					)}
					type="text"
					value={resourceActions}
				/>
			</ClayForm.Group>
		</SidebarPanel>
	);
};

export default ResourceActions;
