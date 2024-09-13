/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useControlledState} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {openModal} from 'frontend-js-web';
import React, {useCallback} from 'react';

import {CheckboxField} from '../../../../../../app/components/fragment_configuration_fields/CheckboxField';
import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {TextField} from '../../../../../../app/components/fragment_configuration_fields/TextField';
import {FORM_DEFAULT_NUMBER_OF_STEPS} from '../../../../../../app/config/constants/formDefaultNumberOfSteps';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {
	useItemLocalConfig,
	useUpdateItemLocalConfig,
} from '../../../../../../app/contexts/LocalConfigContext';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import updateFragmentConfiguration from '../../../../../../app/thunks/updateFragmentConfiguration';
import {getStepperChild} from '../../../../../../app/utils/getStepperChild';

const FORM_TYPE_OPTIONS = [
	{label: Liferay.Language.get('simple'), value: 'simple'},
	{label: Liferay.Language.get('multistep'), value: 'multistep'},
];

export default function FormMultistepOptions({item, onValueSelect}) {
	const localConfig = useItemLocalConfig(item.itemId);

	const updateItemLocalConfig = useUpdateItemLocalConfig();

	const [formType, setFormType] = useControlledState(item.config.formType);

	const [numberOfSteps, setNumberOfSteps] = useControlledState(
		item.config.numberOfSteps
	);

	const dispatch = useDispatch();
	const layoutData = useSelector((state) => state.layoutData);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);

	const updateNumberOfSteps = useCallback(
		(value) => {
			setNumberOfSteps(value);

			onValueSelect({numberOfSteps: value}).then(() => {
				const stepper = getStepperChild(
					item,
					layoutData,
					fragmentEntryLinks
				);

				if (stepper) {
					updateStepperConfiguration({
						dispatch,
						fragmentEntryLink:
							fragmentEntryLinks[
								stepper.config.fragmentEntryLinkId
							],
						numberOfSteps: value,
					});
				}
			});
		},
		[
			dispatch,
			fragmentEntryLinks,
			item,
			layoutData,
			onValueSelect,
			setNumberOfSteps,
		]
	);

	return (
		<>
			<SelectField
				className={classNames('mb-2', 'mt-3')}
				field={{
					label: Liferay.Language.get('form-type'),
					name: 'formType',
					typeOptions: {
						validValues: FORM_TYPE_OPTIONS,
					},
				}}
				onValueSelect={(_name, formType) => {
					setFormType(formType);

					if (formType === 'multistep') {
						onValueSelect({
							formType,
							numberOfSteps: FORM_DEFAULT_NUMBER_OF_STEPS,
						});
					}
					else {
						const stepper = getStepperChild(
							item,
							layoutData,
							fragmentEntryLinks
						);

						if (stepper) {
							openWarningModal({
								onCancel: () => {
									setFormType('multistep');
								},
								onContinue: () => {
									onValueSelect({
										formType: 'simple',
										numberOfSteps: 1,
									});
								},
							});
						}
						else {
							onValueSelect({
								formType: 'simple',
								numberOfSteps: 1,
							});
						}
					}
				}}
				value={formType}
			/>

			{formType === 'multistep' ? (
				<TextField
					field={{
						label: Liferay.Language.get('number-of-steps'),
						typeOptions: {
							validation: {
								errorMessage: Liferay.Language.get(
									'at-least-two-steps-are-required'
								),
								min: 2,
								type: 'number',
							},
						},
					}}
					onValueSelect={(_, numberOfSteps) =>
						updateNumberOfSteps(numberOfSteps)
					}
					value={numberOfSteps || FORM_DEFAULT_NUMBER_OF_STEPS}
				/>
			) : null}

			{formType === 'multistep' ? (
				<CheckboxField
					field={{
						label: Liferay.Language.get(
							'display-all-steps-in-edit-mode'
						),
						name: 'displayAllSteps',
					}}
					onValueSelect={(_name, value) => {
						updateItemLocalConfig(item.itemId, {
							displayAllSteps: value,
						});
					}}
					value={localConfig.displayAllSteps}
				/>
			) : null}
		</>
	);
}

function openWarningModal({onCancel, onContinue}) {
	openModal({
		bodyHTML: Liferay.Language.get(
			'this-action-will-delete-the-stepper-fragment-of-the-form-container'
		),

		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				onClick: ({processClose}) => {
					processClose();
					onCancel();
				},
				type: 'cancel',
			},
			{
				displayType: 'info',
				label: Liferay.Language.get('continue'),
				onClick: ({processClose}) => {
					processClose();
					onContinue();
				},
			},
		],
		status: 'info',
		title: Liferay.Language.get('convert-to-simple-form'),
	});
}

function updateStepperConfiguration({
	dispatch,
	fragmentEntryLink,
	numberOfSteps,
}) {
	const configurationValues = {
		...fragmentEntryLink.editableValues[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		],
		numberOfSteps,
	};

	dispatch(
		updateFragmentConfiguration({
			configurationValues,
			fragmentEntryLink,
		})
	);
}
