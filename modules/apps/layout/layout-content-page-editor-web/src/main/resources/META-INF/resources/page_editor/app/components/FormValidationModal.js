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

import ClayAlert from '@clayui/alert';
import {default as ClayButton} from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import ClayPanel from '@clayui/panel';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../config/index';
import {useFormValidations} from '../contexts/FormValidationContext';
import {getFormErrorDescription} from '../utils/getFormErrorDescription';

export function FormValidationModal({onCloseModal, onPublish}) {
	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const formValidations = useFormValidations();

	if (
		formValidations.length === 1 &&
		formValidations[0].errors.length === 1
	) {
		const [formValidation] = formValidations;

		return (
			<SingleErrorModal
				formValidation={formValidation}
				observer={observer}
				onClose={onClose}
				onPublish={onPublish}
			/>
		);
	}

	return (
		<ClayModal
			className="page-editor__form-validation-modal"
			observer={observer}
			status="warning"
		>
			<ClayModal.Header>
				{Liferay.Language.get('form-errors')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="mb-4">
					{Liferay.Language.get(
						'the-following-errors-have-been-found-with-the-forms-on-the-page'
					)}
				</p>

				{formValidations.map((formValidation, index) => {
					const typeLabel = config.formTypes.find(
						({value}) => value === formValidation.classNameId
					).label;

					return (
						<ClayPanel
							className="mb-0"
							collapsable
							defaultExpanded
							displayTitle={sub(
								Liferay.Language.get('x-form'),
								typeLabel
							)}
							displayType="link"
							key={index}
							showCollapseIcon
						>
							<ClayPanel.Body>
								{formValidation.errors.map((error) => {
									const {summary} = getFormErrorDescription({
										name: typeLabel,
										type: error,
									});

									return (
										<ClayAlert
											displayType="warning"
											key={error}
											variant="feedback"
										>
											{summary}
										</ClayAlert>
									);
								})}
							</ClayPanel.Body>
						</ClayPanel>
					);
				})}
			</ClayModal.Body>

			<ModalFooter onClose={onClose} onPublish={onPublish} />
		</ClayModal>
	);
}

FormValidationModal.propTypes = {
	onCloseModal: PropTypes.func.isRequired,
	onPublish: PropTypes.func.isRequired,
};

function SingleErrorModal({formValidation, observer, onClose, onPublish}) {
	const [errorType] = formValidation.errors;

	const typeLabel = config.formTypes.find(
		({value}) => value === formValidation.classNameId
	).label;

	const {message, title} = getFormErrorDescription({
		name: typeLabel,
		type: errorType,
	});

	return (
		<ClayModal
			className="page-editor__form-validation-modal"
			observer={observer}
			status="warning"
		>
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				<p className="mb-0">{message}</p>
			</ClayModal.Body>

			<ModalFooter onClose={onClose} onPublish={onPublish} />
		</ClayModal>
	);
}

SingleErrorModal.propTypes = {
	formValidation: PropTypes.object.isRequired,
	observer: PropTypes.object.isRequired,
	onClose: PropTypes.func.isRequired,
	onPublish: PropTypes.func.isRequired,
};

function ModalFooter({onClose, onPublish}) {
	return (
		<ClayModal.Footer
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{Liferay.Language.get('cancel')}
					</ClayButton>

					<ClayButton displayType="warning" onClick={onPublish}>
						{Liferay.Language.get('publish')}
					</ClayButton>
				</ClayButton.Group>
			}
		/>
	);
}

ModalFooter.propTypes = {
	onClose: PropTypes.func.isRequired,
	onPublish: PropTypes.func.isRequired,
};
