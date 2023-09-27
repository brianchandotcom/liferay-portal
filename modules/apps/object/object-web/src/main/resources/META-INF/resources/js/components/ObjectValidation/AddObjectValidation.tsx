/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {
	API,
	Input,
	REQUIRED_MSG,
	SingleSelect,
} from '@liferay/object-js-components-web';
import React, {FormEvent, useEffect, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface ModalAddObjectValidationProps extends AddObjectValidationProps {
	observer: Observer;
	onClose: () => void;
}

interface AddObjectValidationProps {
	apiURL: string;
	objectValidationRuleEngines: ObjectValidationType[];
}

interface ObjectValidationLabel {
	[key: string]: string;
}

interface ObjectValidationErrors {
	labelError: string;
	typeError: string;
}

const requiredLabel = REQUIRED_MSG;

function ModalAddObjectValidation({
	apiURL,
	objectValidationRuleEngines,
	observer,
	onClose,
}: ModalAddObjectValidationProps) {
	const [typeSelection, setTypeSelection] = useState<ObjectValidationType>({
		label: '',
		name: '',
	});
	const [labelInput, setLabelInput] = useState<ObjectValidationLabel>({
		[defaultLanguageId]: '',
	});
	const [error, setError] = useState<string>('');
	const [fieldErrors, setFieldErrors] = useState<ObjectValidationErrors>({
		labelError: '',
		typeError: '',
	});
	const [showError, setShowError] = useState<boolean>(false);

	useEffect(() => {
		setFieldErrors((currentErrors) => {
			const updatedErrors = {...currentErrors};
			updatedErrors.labelError =
				labelInput[defaultLanguageId] === '' ? requiredLabel : '';
			updatedErrors.typeError =
				typeSelection.label === '' ? requiredLabel : '';

			return updatedErrors;
		});
	}, [labelInput, typeSelection]);

	const handleSubmit = async (event: FormEvent) => {
		event.preventDefault();
		if (Object.values(fieldErrors).some((error) => error !== '')) {
			setShowError(true);
		}
		else {
			setShowError(false);
			try {
				await API.save({
					item: {
						active: false,
						engine: typeSelection.name,
						name: {
							[defaultLanguageId]: labelInput[defaultLanguageId],
						},
						script: 'script_placeholder',
					},
					method: 'POST',
					url: apiURL,
				});
				onClose();

				window.location.reload();
			}
			catch (error) {
				setError((error as Error).message);
			}
		}
	};

	const handleTypeChange = (option: ObjectValidationType) => {
		setTypeSelection({
			label: option.label,
			name: option.name,
		});
	};

	const handleLabelChange = (label: ObjectValidationLabel) => {
		setLabelInput(label);
	};

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-validation')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						autoComplete="off"
						error={
							showError && fieldErrors.labelError !== ''
								? fieldErrors.labelError
								: ''
						}
						id="objectFieldLabel"
						label={Liferay.Language.get('label')}
						name="label"
						onChange={({target: {value}}) => {
							handleLabelChange({[defaultLanguageId]: value});
						}}
						required
						value={labelInput[defaultLanguageId]}
					/>

					<SingleSelect<ObjectValidationType>
						error={
							showError && fieldErrors.typeError !== ''
								? fieldErrors.typeError
								: ''
						}
						label={Liferay.Language.get('type')}
						onChange={handleTypeChange}
						options={objectValidationRuleEngines}
						required
						value={typeSelection.label}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}

export default function AddObjectValidation({
	apiURL,
	objectValidationRuleEngines,
}: AddObjectValidationProps) {
	const [isVisible, setVisibility] = useState<boolean>(false);
	const {observer, onClose} = useModal({onClose: () => setVisibility(false)});

	useEffect(() => {
		Liferay.on('addObjectValidation', () => setVisibility(true));

		return () => Liferay.detach('addObjectValidation');

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayModalProvider>
			{isVisible && (
				<ModalAddObjectValidation
					apiURL={apiURL}
					objectValidationRuleEngines={objectValidationRuleEngines}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
}
