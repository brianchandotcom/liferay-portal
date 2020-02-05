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
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {ConfigContext} from '../../../app/config/index';
import Button from '../../../common/components/Button';

const ExperienceModal = ({
	errorMessage,
	experienceId,
	hasSegmentsPermission,
	initialName = '',
	observer,
	onErrorDismiss,
	onClose,
	onNewSegmentClick,
	onSubmit,
	segmentId,
	segments = []
}) => {
	const {portletNamespace} = useContext(ConfigContext);
	const [selectedSegmentId, setSelectedSegmentId] = useState(
		segmentId !== undefined
			? segmentId
			: segments[0] && segments[0].segmentsEntryId
	);

	const isMounted = useIsMounted();

	const [name, setName] = useState(initialName);
	const [requiredNameError, setRequiredNameError] = useState(false);
	const [requiredSegmentError, setRequiredSegmentError] = useState(false);
	const [loading, setLoading] = useState(false);

	const handleFormSubmit = event => {
		event.preventDefault();

		const validName = _getValidValue(name);
		const validSegmentId = _getValidValue(selectedSegmentId);

		if (!validName || !validSegmentId) {
			if (!validName) setRequiredNameError(true);
			if (!validSegmentId) setRequiredSegmentError(true);
		}
		else {
			setLoading(true);

			onSubmit({
				name,
				segmentsEntryId: selectedSegmentId,
				segmentsExperienceId: experienceId
			}).finally(() => {
				if (isMounted()) {
					setLoading(false);
				}
			});
		}
	};
	const handleNameChange = event => {
		const {value} = event.target;

		if (!_getValidValue(value)) {
			setRequiredNameError(true);
		}
		else {
			setRequiredNameError(false);
		}

		setName(value);
	};
	const handleSegmentChange = event => {
		const {value} = event.target;

		if (!_getValidValue(value)) {
			setRequiredSegmentError(true);
		}
		else {
			setRequiredSegmentError(false);
		}

		setSelectedSegmentId(event.target.value);
	};
	const handleNewSegmentClick = event => {
		event.preventDefault();

		onNewSegmentClick({
			experienceId,
			experienceName: name,
			segmentId: selectedSegmentId
		});
	};

	const nameInputId = portletNamespace + 'segmentsExperienceName';
	const segmentSelectId = portletNamespace + 'segmentsExperienceSegment';

	const nameGroupClassName = classNames('my-2', {
		'has-error': requiredNameError
	});
	const segmentGroupClassName = classNames('my-2', {
		'has-error': requiredSegmentError
	});

	const modalTitle = experienceId
		? Liferay.Language.get('edit-experience')
		: Liferay.Language.get('new-experience');

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>{modalTitle}</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm
					autoComplete="off"
					className="mb-3"
					noValidate
					onSubmit={handleFormSubmit}
				>
					{errorMessage && (
						<ClayAlert
							displayType="danger"
							onClose={onErrorDismiss}
							title={errorMessage}
						/>
					)}
					<ClayForm.Group className={nameGroupClassName}>
						<label htmlFor={nameInputId}>
							{Liferay.Language.get('name')}

							<ClayIcon
								className="ml-1 reference-mark"
								focusable="false"
								role="presentation"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							autoFocus
							id={nameInputId}
							onChange={handleNameChange}
							placeholder={Liferay.Language.get(
								'experience-name'
							)}
							required
							type="text"
							value={name}
						/>

						{requiredNameError && (
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator
									className="mr-1"
									symbol="exclamation-full"
								/>
								{Liferay.Language.get(
									'an-experience-name-is-required'
								)}
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.Group>

					<ClayForm.Group className={segmentGroupClassName}>
						<label htmlFor={segmentSelectId}>
							{Liferay.Language.get('audience')}

							<ClayIcon
								className="ml-1 reference-mark"
								focusable="false"
								role="presentation"
								symbol="asterisk"
							/>
						</label>
						<div className="d-flex">
							<ClaySelect
								disabled={segments.length === 0}
								id={segmentSelectId}
								onChange={handleSegmentChange}
								value={selectedSegmentId}
							>
								{segments.length ? (
									segments.map(segment => {
										return (
											<ClaySelect.Option
												key={segment.segmentsEntryId}
												label={segment.name}
												value={segment.segmentsEntryId}
											/>
										);
									})
								) : (
									<ClaySelect.Option
										label={Liferay.Language.get(
											'no-segments'
										)}
										value=""
									/>
								)}
							</ClaySelect>

							{hasSegmentsPermission === true && (
								<Button
									className="flex-shrink-0 ml-2"
									disabled={loading}
									displayType="secondary"
									onClick={handleNewSegmentClick}
									type="button"
								>
									{Liferay.Language.get('new-segment')}
								</Button>
							)}
						</div>

						{requiredSegmentError && (
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation" />

								{Liferay.Language.get(
									'an-audience-is-required'
								)}
							</ClayForm.FeedbackItem>
						)}
					</ClayForm.Group>
				</ClayForm>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={loading}
							displayType="secondary"
							onClick={onClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<Button
							disabled={loading}
							displayType="primary"
							loading={loading}
							onClick={handleFormSubmit}
						>
							{Liferay.Language.get('save')}
						</Button>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</ClayModal>
	);
};

ExperienceModal.propTypes = {
	errorMessage: PropTypes.string,
	experienceId: PropTypes.string,
	hasSegmentsPermission: PropTypes.bool.isRequired,
	initialName: PropTypes.string,
	observer: PropTypes.object.isRequired,
	onClose: PropTypes.func.isRequired,
	onErrorDismiss: PropTypes.func.isRequired,
	onNewSegmentClick: PropTypes.func.isRequired,
	onSubmit: PropTypes.func.isRequired,
	segmentId: PropTypes.string,
	segments: PropTypes.array.isRequired
};

function _getValidValue(value) {
	return value && value.replace(/ /g, '');
}

export default ExperienceModal;
