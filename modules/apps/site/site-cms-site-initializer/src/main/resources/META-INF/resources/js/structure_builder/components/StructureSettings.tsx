/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import React from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectSelectedItem from '../selectors/selectSelectedItem';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureError from '../selectors/selectStructureError';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureName from '../selectors/selectStructureName';
import ERCInput from './ERCInput';
import StructureFieldSettings from './StructureFieldSettings';
import TextInput from './TextInput';

export function StructureSettings() {
	const dispatch = useStateDispatch();
	const error = useSelector(selectStructureError);
	const label = useSelector(selectStructureLabel);

	return (
		<ClayLayout.ContainerFluid size="md" view>
			{error ? (
				<ClayAlert
					displayType="danger"
					role={null}
					title={Liferay.Language.get('error')}
				>
					{error}
				</ClayAlert>
			) : null}

			<ClayLabel className="mb-3" displayType="info">
				{Liferay.Language.get('content')}
			</ClayLabel>

			<ClayForm.Group>
				<ClayInput
					aria-label={Liferay.Language.get('structure-label')}
					className="form-control-inline structure-builder__title-input"
					onChange={(event) =>
						dispatch({label: event.target.value, type: 'set-label'})
					}
					sizing="lg"
					type="text"
					value={label}
				/>
			</ClayForm.Group>

			<ClayTabs>
				<ClayTabs.List>
					<ClayTabs.Item>
						{Liferay.Language.get('general')}
					</ClayTabs.Item>

					<ClayTabs.Item>
						{Liferay.Language.get('validations')}
					</ClayTabs.Item>
				</ClayTabs.List>

				<ClayTabs.Panels fade>
					<ClayTabs.TabPane>
						<GeneralTab />
					</ClayTabs.TabPane>

					<ClayTabs.TabPane>
						<ValidationsTab />
					</ClayTabs.TabPane>
				</ClayTabs.Panels>
			</ClayTabs>
		</ClayLayout.ContainerFluid>
	);
}

export default function () {
	const selectedItem = useSelector(selectSelectedItem);

	if (selectedItem.type === 'structure') {
		return <StructureSettings />;
	}

	return <StructureFieldSettings fieldName={selectedItem.name} />;
}

function GeneralTab() {
	const dispatch = useStateDispatch();
	const name = useSelector(selectStructureName);
	const erc = useSelector(selectStructureERC);

	return (
		<div>
			<TextInput
				label={Liferay.Language.get('structure-name')}
				onValueChange={(value) =>
					dispatch({name: value, type: 'update-structure'})
				}
				required
				value={name}
			/>

			<ERCInput
				onValueChange={(value) =>
					dispatch({erc: value, type: 'update-structure'})
				}
				value={erc}
			/>
		</div>
	);
}

function ValidationsTab() {
	return <div></div>;
}
