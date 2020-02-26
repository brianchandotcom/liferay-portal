/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {SegmentsExperimentGoal} from '../types.es';
import BusyButton from './BusyButton/BusyButton.es';
import ValidatedInput from './ValidatedInput/ValidatedInput.es';

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
	title,
}) {
	const [busy, setBusy] = useState(false);
	const [inputDescription, setInputDescription] = useState(description);
	const [inputGoal, setInputGoal] = useState(
		(goal && goal.value) || (goals[0] && goals[0].value)
	);
	const [inputName, setInputName] = useState(name);
	const [invalidForm, setInvalidForm] = useState(false);
	const isMounted = useIsMounted();

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
						autofocus
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
									className="ml-1 reference-mark text-warning"
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
						<BusyButton
							busy={busy}
							disabled={invalidForm || busy}
							displayType="primary"
							onClick={_handleSave}
						>
							{Liferay.Language.get('save')}
						</BusyButton>
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
		if (!invalidForm && !busy) {
			const goalTarget =
				inputGoal === 'click'
					? goal && goal.target
						? goal.target
						: ''
					: '';

			setBusy(true);

			onSave({
				description: inputDescription,
				goal: inputGoal,
				goalTarget,
				name: inputName,
				segmentsExperienceId,
				segmentsExperimentId,
			}).finally(() => {
				if (isMounted()) {
					setBusy(false);
				}
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
	title: PropTypes.string.isRequired,
};

export default SegmentsExperimentsModal;
