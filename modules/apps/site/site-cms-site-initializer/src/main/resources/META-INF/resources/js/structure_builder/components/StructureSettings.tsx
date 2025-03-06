/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayEmptyState from '@clayui/empty-state';
import ClayForm from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import {InputLocalized} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectSelection from '../selectors/selectSelection';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureError from '../selectors/selectStructureError';
import selectStructureLabel from '../selectors/selectStructureLabel';
import selectStructureName from '../selectors/selectStructureName';
import selectStructureUuid from '../selectors/selectStructureUuid';
import {getImage} from '../utils/getImage';
import ERCInput from './ERCInput';
import Input from './Input';
import StructureFieldSettings from './StructureFieldSettings';

export default function () {
	const selection = useSelector(selectSelection);
	const structureUuid = useSelector(selectStructureUuid);

	if (selection.length > 1) {
		return <MultiselectionState />;
	}

	const [uuid] = selection;

	if (!uuid || uuid === structureUuid) {
		return <StructureSettings />;
	}

	return <StructureFieldSettings key={uuid} uuid={uuid} />;
}

function StructureSettings() {
	const dispatch = useStateDispatch();
	const error = useSelector(selectStructureError);
	const structureLabel = useSelector(selectStructureLabel);

	const [label, setLabel] =
		useState<Liferay.Language.LocalizedValue<string>>(structureLabel);

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
				<InputLocalized
					aria-label={Liferay.Language.get('structure-label')}
					className="form-control-inline structure-builder__title-input"
					label=""
					onBlur={() => {
						dispatch({
							label,
							type: 'set-label',
						});
					}}
					onChange={(label) => setLabel(label)}
					required
					translations={
						label as Liferay.Language.LocalizedValue<string>
					}
					validate
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

function GeneralTab() {
	const dispatch = useStateDispatch();
	const name = useSelector(selectStructureName);
	const erc = useSelector(selectStructureERC);

	return (
		<div>
			<Input
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

function MultiselectionState() {
	return (
		<ClayEmptyState
			className="justify-content-center structure-builder__empty-state"
			description=""
			imgSrc={getImage('multiselection_state.svg')}
			imgSrcReducedMotion={getImage('multiselection_state.svg')}
			small
			title={Liferay.Language.get('multiple-items-selected')}
		/>
	);
}
