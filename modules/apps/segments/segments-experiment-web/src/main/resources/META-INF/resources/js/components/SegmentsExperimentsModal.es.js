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

import React, {useState} from 'react';
import PropTypes from 'prop-types';
import ClayModal from '@clayui/modal';
import ClayButton from '@clayui/button';
import ClayAlert from '@clayui/alert';
import ValidatedInput from './ValidatedInput/ValidatedInput.es';

function SegmentsExperimentsModal({
	onClose,
	active,
	error,
	onSave,
	name = '',
	description = '',
	segmentsExperienceId,
	segmentsExperimentId,
	title
}) {
	const [inputDescription, setInputDescription] = useState(description);
	const [inputName, setInputName] = useState(name);
	const [invalidForm, setInvalidForm] = useState(false);

	return active ? (
		<ClayModal onClose={_handleModalClose} size="sm">
			{onClose => {
				return (
					<React.Fragment>
						<ClayModal.Header>{title}</ClayModal.Header>
						<ClayModal.Body>
							<form onSubmit={_handleFormSubmit}>
								{error && (
									<ClayAlert
										displayType="danger"
										title={Liferay.Language.get('error')}
									>
										{error}
									</ClayAlert>
								)}
								<ValidatedInput
									autofocus={true}
									errorMessage={Liferay.Language.get(
										'test-name-is-required'
									)}
									label={Liferay.Language.get('test-name')}
									onChange={_handleNameChange}
									onValidationChange={
										_handleInputNameValidation
									}
									value={inputName}
								/>
								<div className="form-group">
									<label>
										{Liferay.Language.get('description')}
									</label>
									<textarea
										className="form-control"
										onChange={_handleDescriptionChange}
										placeholder={Liferay.Language.get(
											'description-placeholder'
										)}
										value={inputDescription}
									/>
								</div>
							</form>
						</ClayModal.Body>
						<ClayModal.Footer
							last={
								<ClayButton.Group spaced>
									<ClayButton
										disabled={invalidForm}
										displayType="secondary"
										onClick={onClose}
									>
										{Liferay.Language.get('cancel')}
									</ClayButton>
									<ClayButton
										displayType="primary"
										onClick={_handleSave}
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								</ClayButton.Group>
							}
						/>
					</React.Fragment>
				);
			}}
		</ClayModal>
	) : null;

	function _handleNameChange(event) {
		setInputName(event.target.value);
	}

	function _handleDescriptionChange(event) {
		setInputDescription(event.target.value);
	}

	function _handleInputNameValidation(error) {
		setInvalidForm(error);
	}

	/**
	 * Triggers `onTestCreation` and closes the modal
	 */
	function _handleSave() {
		if (!invalidForm) {
			onSave({
				name: inputName,
				description: inputDescription,
				segmentsExperienceId,
				segmentsExperimentId
			});
		}
	}

	/**
	 * Resets modal values and triggers `onClose`
	 */
	function _handleModalClose() {
		onClose();
	}

	function _handleFormSubmit(event) {
		event.preventDefault();
		_handleSave();
	}
}

SegmentsExperimentsModal.propTypes = {
	active: PropTypes.bool.isRequired,
	error: PropTypes.string,
	description: PropTypes.string,
	segmentsExperienceId: PropTypes.string,
	segmentsExperimentId: PropTypes.string,
	name: PropTypes.string,
	onClose: PropTypes.func.isRequired,
	onSave: PropTypes.func.isRequired,
	title: PropTypes.string.isRequired
};

export default SegmentsExperimentsModal;
