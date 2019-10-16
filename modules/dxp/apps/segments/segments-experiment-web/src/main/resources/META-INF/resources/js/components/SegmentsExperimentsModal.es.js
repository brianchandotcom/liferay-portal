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
import ClayIcon from '@clayui/icon';
import {ClaySelect} from '@clayui/form';
import {SegmentsExperimentGoal} from '../types.es';

function SegmentsExperimentsModal({
	description = '',
	error,
	goal,
	goals = [],
	name = '',
	onClose,
	onSave,
	segmentsExperienceId,
	segmentsExperimentId,
	title
}) {
	const [inputDescription, setInputDescription] = useState(description);
	const [inputGoal, setInputGoal] = useState(
		(goal && goal.value) || (goals[0] && goals[0].value)
	);
	const [inputName, setInputName] = useState(name);
	const [invalidForm, setInvalidForm] = useState(false);

	return (
		<>
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
						onValidationChange={_handleInputNameValidation}
						value={inputName}
					/>

					<div className="form-group">
						<label>{Liferay.Language.get('description')}</label>
						<textarea
							className="form-control"
							maxLength="4000"
							onChange={_handleDescriptionChange}
							placeholder={Liferay.Language.get(
								'description-placeholder'
							)}
							value={inputDescription}
						/>
					</div>
					{goals.length > 0 && (
						<div className="form-group">
							<label className="w100">
								{Liferay.Language.get('select-goal')}
								<ClayIcon
									className="reference-mark text-warning ml-1"
									symbol="asterisk"
								/>
								<ClaySelect
									className="mt-1"
									defaultValue={inputGoal}
									onChange={_handleGoalChange}
								>
									{goals.map(goal => (
										<ClaySelect.Option
											key={goal.value}
											label={goal.label}
											value={goal.value}
										/>
									))}
								</ClaySelect>
							</label>
						</div>
					)}
				</form>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={onClose}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							disabled={invalidForm}
							displayType="primary"
							onClick={_handleSave}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);

	function _handleGoalChange(event) {
		setInputGoal(event.target.value);
	}

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
	 * Triggers `onSave` prop
	 *
	 * Resets `goalTarget` if goal is not 'click'
	 */
	function _handleSave() {
		if (!invalidForm) {
			const goalTarget =
				inputGoal === 'click'
					? goal && goal.target
						? goal.target
						: ''
					: '';

			onSave({
				description: inputDescription,
				goal: inputGoal,
				goalTarget,
				name: inputName,
				segmentsExperienceId,
				segmentsExperimentId
			});
		}
	}

	function _handleFormSubmit(event) {
		event.preventDefault();

		_handleSave();
	}
}

SegmentsExperimentsModal.propTypes = {
	description: PropTypes.string,
	error: PropTypes.string,
	goal: SegmentsExperimentGoal,
	goals: PropTypes.arrayOf(SegmentsExperimentGoal),
	name: PropTypes.string,
	onClose: PropTypes.func.isRequired,
	onSave: PropTypes.func.isRequired,
	segmentsExperienceId: PropTypes.string,
	segmentsExperimentId: PropTypes.string,
	title: PropTypes.string.isRequired
};

export default SegmentsExperimentsModal;
