/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';

const HTTP_METHODS = ['GET', 'POST', 'PUT', 'PATCH', 'DELETE'];

const HTTPEndpoint = () => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const updateSelectedItem =
		(field) =>
		({target}) => {
			if (!selectedItem) {
				return;
			}

			setSelectedItem({
				...selectedItem,
				data: {
					...selectedItem.data,
					[field]: target.value,
				},
			});
		};

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('http-endpoint')}>
			<ClayForm.Group>
				<label htmlFor="httpMethod">
					{Liferay.Language.get('http-method')}
				</label>

				<ClaySelect
					aria-label={Liferay.Language.get('http-method')}
					id="httpMethod"
					onChange={updateSelectedItem('httpMethod')}
					value={selectedItem?.data.httpMethod ?? 'GET'}
				>
					{HTTP_METHODS.map((httpMethod) => (
						<ClaySelect.Option
							key={httpMethod}
							label={httpMethod}
							value={httpMethod}
						/>
					))}
				</ClaySelect>

				<label className="mt-4" htmlFor="url">
					{Liferay.Language.get('url')}
				</label>

				<ClayInput
					id="url"
					onChange={updateSelectedItem('url')}
					placeholder="https://ai-sandbox.liferay.net/o/ai-hub/v1.0/..."
					required={true}
					type="text"
					value={selectedItem?.data.url ?? ''}
				/>
			</ClayForm.Group>
		</SidebarPanel>
	);
};

HTTPEndpoint.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default HTTPEndpoint;
