/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch, navigate} from 'frontend-js-web';
import React, {useCallback, useRef, useState} from 'react';

import {MODAL_TYPES, ModalType} from '../constants/modalTypes';
import {MappingType} from '../types/MappingTypes';
import {ValidationError} from '../types/ValidationError';
import DisplayPageModalForm from './DisplayPageModalForm';

interface Props {
	displayPageName: string;
	formSubmitURL: string;
	mappingTypes: MappingType[];
	namespace: string;
	onClose: () => void;
	title: string;
	type: ModalType;
	warningMessage: string;
}

export default function DisplayPageModal({
	displayPageName,
	formSubmitURL,
	mappingTypes,
	namespace,
	onClose,
	title,
	type,
	warningMessage,
}: Props) {
	const [error, setError] = useState<ValidationError>({});
	const [loading, setLoading] = useState(false);
	const [warningVisible, setWarningVisible] = useState(true);

	const {observer} = useModal({onClose});

	const formRef = useRef<HTMLFormElement>(null);

	const validateForm = useCallback(
		(form) => {
			const {elements} = form;
			const error: ValidationError = {};

			const errorMessage = Liferay.Language.get('this-field-is-required');

			const nameField = elements[`${namespace}name`];

			if (nameField && !nameField.value) {
				error.name = errorMessage;
			}

			const classNameIdField = elements[`${namespace}classNameId`];

			if (classNameIdField.selectedIndex === 0) {
				error.classNameId = errorMessage;
			}

			const classTypeIdField = elements[`${namespace}classTypeId`];

			if (classTypeIdField && classTypeIdField.selectedIndex === 0) {
				error.classTypeId = errorMessage;
			}

			return error;
		},
		[namespace]
	);

	const handleSubmit = useCallback(
		(event) => {
			event.preventDefault();

			const form = formRef.current;

			const error = validateForm(form);

			if (Object.keys(error).length !== 0) {
				setError(error);

				return;
			}

			setLoading(true);

			fetch(formSubmitURL, {
				body: new FormData(form!),
				method: 'POST',
			})
				.then((response) => response.json())
				.then((responseContent) => {
					if (responseContent.error) {
						setLoading(false);
						setError(responseContent.error);
					}
					else if (responseContent.redirectURL) {
						navigate(responseContent.redirectURL, {
							beforeScreenFlip: onClose,
						});
					}
				})
				.catch(() =>
					setError({
						other:
							type === MODAL_TYPES.create
								? Liferay.Language.get(
										'an-unexpected-error-occurred-while-creating-the-display-page'
								  )
								: Liferay.Language.get(
										'an-unexpected-error-occurred-while-changing-the-content-type'
								  ),
					})
				);
		},
		[formSubmitURL, onClose, type, validateForm]
	);

	const visible = observer.mutation;

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>{title}</ClayModal.Header>

			{warningMessage && warningVisible ? (
				<ClayAlert
					displayType="warning"
					onClose={() => setWarningVisible(false)}
					title={Liferay.Language.get('warning')}
					variant="stripe"
				>
					{warningMessage}
				</ClayAlert>
			) : null}

			<ClayModal.Body>
				{error && error.other && (
					<ClayAlert
						displayType="danger"
						onClose={() => {}}
						title={Liferay.Language.get('error')}
					>
						{error.other}
					</ClayAlert>
				)}

				{visible && (
					<DisplayPageModalForm
						displayPageName={displayPageName}
						error={error}
						formRef={formRef}
						mappingTypes={mappingTypes}
						namespace={namespace}
						onSubmit={handleSubmit}
						type={type}
					/>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={handleSubmit}
						>
							{loading && (
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
							)}

							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
