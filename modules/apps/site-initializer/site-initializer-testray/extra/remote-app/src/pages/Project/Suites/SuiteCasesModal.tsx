/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React, {useCallback, useState} from 'react';

import Form from '../../../components/Form';
import DualListBox, {
	BoxItem,
	Boxes,
} from '../../../components/Form/DualListBox';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {useFetch} from '../../../hooks/useFetch';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {
	APIResponse,
	TestrayCaseType,
	TestrayComponent,
	TestrayRequirement,
	TestrayTeam,
} from '../../../services/rest';

type SuiteCasesModalProps = {
	modal: FormModalOptions;
	type: 'select-cases' | 'select-case-parameters';
};

const onMapDefault = ({id, name}: any) => ({
	label: name,
	value: id.toString(),
});

type SelectCaseParametersProps = {
	setState: any;
	state: State;
};

const SelectCaseParameters: React.FC<SelectCaseParametersProps> = ({
	setState,
}) => {
	const {data: casetypes} = useFetch<APIResponse<TestrayCaseType>>(
		'/casetypes?fields=id,name'
	);
	const {data: components} = useFetch<APIResponse<TestrayComponent>>(
		'/components?fields=id,name'
	);
	const {data: requirements} = useFetch<APIResponse<TestrayRequirement>>(
		'/requirements?fields=id,name'
	);

	const {data: teams} = useFetch<APIResponse<TestrayTeam>>(
		'/teams?fields=id,name'
	);

	const getSelectedCaseParameters = useCallback(() => {
		if (!casetypes || !components || !requirements || !teams) {
			return;
		}

		const defaultBox: any = [];

		const testrayCaseTypes = casetypes.items || [];
		const testrayComponents = components.items || [];
		const testrayRequirements = requirements.items || [];
		const testrayTeams = teams?.items || [];

		return {
			testrayCaseTypes: [testrayCaseTypes.map(onMapDefault), defaultBox],
			testrayComponents: [
				testrayComponents.map(onMapDefault),
				defaultBox,
			],
			testrayPriorities: [
				[...new Array(5)].map((_, index) => ({
					label: String(index + 1),
					value: String(index + 1),
				})),
				defaultBox,
			],
			testrayRequirements: [
				testrayRequirements.map(({id, key, summary}) => ({
					label: `${key} (${summary})`,
					value: id.toString(),
				})),
				defaultBox,
			],
			testrayTeams: [testrayTeams.map(onMapDefault), defaultBox],
		};
	}, [casetypes, components, requirements, teams]);

	const selectedCaseParameters = getSelectedCaseParameters();

	const onSetValue = (name: string) => ([, rightSelected]: Boxes) => {
		setState((prevState: State) => ({...prevState, [name]: rightSelected}));
	};

	return (
		<>
			<DualListBox
				boxes={selectedCaseParameters?.testrayTeams}
				leftLabel={i18n.translate('available-teams')}
				rightLabel={i18n.translate('current-teams')}
				setValue={onSetValue('testrayTeams')}
			/>

			<DualListBox
				boxes={selectedCaseParameters?.testrayCaseTypes}
				leftLabel={i18n.translate('available-case-types')}
				rightLabel={i18n.translate('current-case-types')}
				setValue={onSetValue('testrayCaseTypes')}
			/>

			<DualListBox
				boxes={selectedCaseParameters?.testrayComponents}
				leftLabel={i18n.translate('available-main-components')}
				rightLabel={i18n.translate('current-main-components')}
				setValue={onSetValue('testrayComponents')}
			/>

			<DualListBox
				boxes={selectedCaseParameters?.testrayComponents}
				leftLabel={i18n.translate('available-subcomponents')}
				rightLabel={i18n.translate('current-subcomponents')}
				setValue={onSetValue('testraySubComponents')}
			/>

			<DualListBox
				boxes={selectedCaseParameters?.testrayPriorities}
				leftLabel={i18n.translate('available-priorities')}
				rightLabel={i18n.translate('current-priorities')}
				setValue={onSetValue('testrayPriorities')}
			/>

			<DualListBox
				boxes={selectedCaseParameters?.testrayRequirements}
				leftLabel={i18n.translate('available-requirements')}
				rightLabel={i18n.translate('current-requirements')}
				setValue={onSetValue('testrayRequirements')}
			/>
		</>
	);
};

export type State = {
	testrayCaseTypes: BoxItem[];
	testrayComponents: BoxItem[];
	testrayPriorities: BoxItem[];
	testrayRequirements: BoxItem[];
	testraySubComponents: BoxItem[];
	testrayTeams: BoxItem[];
};

export const initialState = {
	testrayCaseTypes: [],
	testrayComponents: [],
	testrayPriorities: [],
	testrayRequirements: [],
	testraySubComponents: [],
	testrayTeams: [],
};

const SuiteCasesModal: React.FC<SuiteCasesModalProps> = ({
	modal: {observer, onClose, onSave, visible},
	type,
}) => {
	const [state, setState] = useState<State>(initialState);

	return (
		<Modal
			last={
				<Form.Footer
					isModal
					onClose={onClose}
					onSubmit={() => onSave(state)}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate(type)}
			visible={visible}
		>
			<SelectCaseParameters setState={setState} state={state} />
		</Modal>
	);
};

export default withVisibleContent(SuiteCasesModal);
