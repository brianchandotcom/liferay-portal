/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert, {DisplayType} from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Text} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {LearnMessage, LearnResourcesContext} from 'frontend-js-components-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {ChangeEvent, useEffect, useRef, useState} from 'react';

type TFeedback = {displayType: DisplayType; message: string};

const EMPTY_FEEDBACK: TFeedback = {
	displayType: 'info',
	message: '',
};

const disableFormSubmitButton = (
	disabled: boolean,
	portletNamespace: string
) => {
	const submitButton = document.getElementById(
		portletNamespace + 'editClientExtensionEntrySubmitButton'
	) as HTMLButtonElement;

	if (submitButton) {
		submitButton.disabled = disabled;
	}
};

const readInputFile = (file: File) => {
	const fileReader = new FileReader();

	return new Promise<string>((resolve) => {
		fileReader.onload = () => {
			resolve(fileReader.result as string);
		};

		fileReader.readAsText(file);
	});
};

const post = async (body: any, endpoint: string) => {
	return await fetch('/o/frontend-token-definition' + endpoint, {
		body,
		method: 'POST',
	});
};

const EMPTY_FILE_JSON_REPRESENTATION = '{}';

const FrontendTokenDefinitionFilePicker = ({
	externalReferenceCode,
	frontendTokenDefinition: currentFrontendTokenDefinition,
	learnResources,
	portletNamespace,
}) => {
	const [feedback, setFeedback] = useState(EMPTY_FEEDBACK);
	const [alertRole, setAlertRole] = useState<'alert' | null>(null);
	const [isValidatingJSON, setIsValidatingJSON] = useState(false);
	const [frontendTokenDefinition, setFrontendTokenDefinition] = useState<
		string
	>(
		currentFrontendTokenDefinition !== ''
			? currentFrontendTokenDefinition
			: undefined
	);

	const fileInputRef = useRef<HTMLInputElement>();
	const selectFileButtonRef = useRef<HTMLButtonElement>();

	const clearSelection = () => {
		setFeedback(EMPTY_FEEDBACK);

		setFrontendTokenDefinition('');

		disableFormSubmitButton(false, portletNamespace);

		selectFileButtonRef.current?.focus();
	};

	const validateFrontendTokenDefinitionFile = async (file: File) => {
		return post(objectToFormData({file}), '/validate-file')
			.then(async (response) => {
				const data = await response.json();

				if (response.ok) {
					setFeedback({
						displayType: 'success',
						message: data.message,
					});

					disableFormSubmitButton(false, portletNamespace);
				}
				else {
					throw data.message;
				}
			})
			.catch((error) => {
				disableFormSubmitButton(true, portletNamespace);

				setFeedback({
					displayType: 'danger',
					message: error
						? error
						: Liferay.Language.get(
								'your-upload-failed-to-complete-please-try-again-or-contact-support-if-the-error-persists'
						  ),
				});
			});
	};

	const handleFileInputChange = async ({
		target,
	}: ChangeEvent<HTMLInputElement>) => {
		setIsValidatingJSON(true);

		setFrontendTokenDefinition('');

		if (!alertRole) {
			setAlertRole('alert');
		}

		setFeedback({
			displayType: 'info',
			message: Liferay.Language.get(
				'the-frontend-token-definition-json-file-is-being-uploaded-and-validated'
			),
		});

		const filePath = target.value;

		if (!filePath.endsWith('.json')) {
			setFeedback({
				displayType: 'danger',
				message: Liferay.Language.get(
					'the-format-is-not-valid-please-upload-a-valid-frontend-token-definition-json-file'
				),
			});

			disableFormSubmitButton(true, portletNamespace);
		}
		else if (target.files === null) {
			setFeedback({
				displayType: 'danger',
				message: Liferay.Language.get(
					'your-upload-failed-to-complete-please-try-again-or-contact-support-if-the-error-persists'
				),
			});

			disableFormSubmitButton(true, portletNamespace);
		}
		else {
			const file = target.files[0];

			await validateFrontendTokenDefinitionFile(file);

			setFrontendTokenDefinition(
				(await readInputFile(file)) || EMPTY_FILE_JSON_REPRESENTATION
			);
		}

		setIsValidatingJSON(false);
	};

	useEffect(() => {
		if (frontendTokenDefinition && externalReferenceCode) {
			post(
				objectToFormData({
					companyId: Liferay.ThemeDisplay.getCompanyId(),
					externalReferenceCode,
				}),
				'/tokens-info'
			)
				.then((response) => response.json())
				.then((data) => {
					if (data.message) {
						setFeedback({
							displayType: 'success',
							message: data.message,
						});
					}
				});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const fileInputId = `${portletNamespace}file`;
	const frontendTokenDefinitionDescriptionId = `${portletNamespace}frontendTokenDefinitionDescription`;

	return (
		<LearnResourcesContext.Provider value={learnResources}>
			<label
				aria-describedby={frontendTokenDefinitionDescriptionId}
				className="d-block"
				htmlFor={fileInputId}
				tabIndex={0}
			>
				{Liferay.Language.get('frontend-token-definition-json-upload')}
			</label>

			<Text
				as="p"
				color="secondary"
				id={frontendTokenDefinitionDescriptionId}
				size={3}
			>
				{`${Liferay.Language.get(
					'the-frontend-token-definition-is-a-json'
				)} `}

				<LearnMessage
					resource="client-extension-web"
					resourceKey="learn-style-book-token-definitions"
				/>
			</Text>

			<ClayInput
				accept=".json"
				className="d-none"
				id={fileInputId}
				name={fileInputId}
				onChange={handleFileInputChange}
				ref={fileInputRef}
				type="file"
			/>

			<ClayInput
				id={`${portletNamespace}frontendTokenDefinition`}
				name={`${portletNamespace}frontendTokenDefinition`}
				type="hidden"
				value={frontendTokenDefinition}
			/>

			<div className="my-2">
				<ClayButton
					disabled={isValidatingJSON}
					displayType="secondary"
					onClick={() => fileInputRef.current?.click()}
					ref={selectFileButtonRef}
				>
					{!frontendTokenDefinition
						? Liferay.Language.get('select-json')
						: Liferay.Language.get('replace-json')}
				</ClayButton>

				<div className="inline-item">
					{!frontendTokenDefinition && (
						<small className="inline-item inline-item-after">
							<strong>
								{isValidatingJSON
									? Liferay.Language.get('validating-json')
									: Liferay.Language.get('no-json-selected')}
							</strong>
						</small>
					)}

					{frontendTokenDefinition && (
						<ClayButtonWithIcon
							borderless
							className="ml-2"
							displayType="secondary"
							monospaced
							onClick={clearSelection}
							symbol="times-circle-full"
							title={Liferay.Language.get(
								'remove-file-from-selection'
							)}
						/>
					)}

					{isValidatingJSON && (
						<ClayLoadingIndicator
							className="ml-2"
							displayType="secondary"
							size="sm"
						/>
					)}
				</div>
			</div>

			<ClayAlert
				displayType={feedback.displayType}
				role={alertRole}
				style={{display: feedback.message ? 'block' : 'none'}}
				title={feedback.message}
				variant="feedback"
			/>
		</LearnResourcesContext.Provider>
	);
};

FrontendTokenDefinitionFilePicker.propTypes = {
	externalReferenceCode: PropTypes.number.isRequired,
	frontendTokenDefinition: PropTypes.string.isRequired,
	learnResources: PropTypes.object.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default FrontendTokenDefinitionFilePicker;
