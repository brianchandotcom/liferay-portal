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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {STATUS_DRAFT} from '../statuses';
import {ExperienceType} from '../types';
import ExperimentLabel from './ExperimentLabel';
import Popover from './Popover';

const ExperienceItem = ({
	active,
	editable,
	experience,
	lockedDecreasePriority,
	lockedIncreasePriority,
	onDeleteExperience,
	onEditExperience,
	onPriorityDecrease,
	onPriorityIncrease,
	onSelect
}) => {
	const iconRef = React.useRef();
	const [showtoolTip, setShowtoolTip] = React.useState(false);
	const handleSelect = () => onSelect(experience.segmentsExperienceId);
	const handlePriorityIncrease = () =>
		onPriorityIncrease(
			experience.segmentsExperienceId,
			experience.priority
		);
	const handlePriorityDecrease = () =>
		onPriorityDecrease(
			experience.segmentsExperienceId,
			experience.priority
		);
	const handleExperienceEdit = () => {
		const {name, segmentsEntryId, segmentsExperienceId} = experience;

		onEditExperience({name, segmentsEntryId, segmentsExperienceId});
	};
	const handleExperienceDelete = () => {
		const experienceHasRunningExperiment =
			experience.segmentsExperimentStatus &&
			experience.segmentsExperimentStatus.value === STATUS_DRAFT;

		const confirmationMessage = experienceHasRunningExperiment
			? Liferay.Language.get(
					'delete-experience-with-running-test-confirmation-message'
			  )
			: Liferay.Language.get('do-you-want-to-delete-this-experience');

		const confirmed = confirm(confirmationMessage);

		if (confirmed) {
			onDeleteExperience(experience.segmentsExperienceId);
		}
	};
	const handleExperimentNavigation = event => {
		event.preventDefault();

		Liferay.Util.Session.set(
			'com.liferay.segments.experiment.web_panelState',
			'open'
		).then(() => {
			Liferay.Util.navigate(experience.segmentsExperimentURL);
		});
	};

	const itemClassName = classNames('d-flex dropdown-menu__experience', {
		'dropdown-menu__experience--active': active
	});

	return (
		<li aria-checked={active} className={itemClassName} role="listitem">
			<span className="overflow-hidden p-2 w-100">
				<button
					className="align-items-baseline btn btn-unstyled d-flex justify-content-between p-2 text-dark title w-100"
					onClick={handleSelect}
					type="button"
				>
					<span className="d-flex flex-column flex-grow-1 text-truncate">
						<strong className="text-truncate">
							{experience.name}

							{experience.hasLockedSegmentsExperiment && (
								<>
									<ClayIcon
										className="ml-2 text-secondary"
										onMouseEnter={() =>
											setShowtoolTip(true)
										}
										onMouseLeave={() =>
											setShowtoolTip(false)
										}
										ref={iconRef}
										small="true"
										symbol="lock"
									/>

									{showtoolTip && (
										<Popover
											anchor={iconRef.current}
											header={Liferay.Language.get(
												'experience-locked'
											)}
										>
											{Liferay.Language.get(
												'edit-is-not-allowed-for-this-experience'
											)}
										</Popover>
									)}
								</>
							)}
						</strong>

						<span className="audience d-block text-truncate">
							<span className="mr-1 text-secondary">
								{Liferay.Language.get('audience')}
							</span>
							{experience.segmentsEntryName}
						</span>

						{experience.segmentsExperimentStatus && (
							<div>
								<span className="font-weight-normal mr-1 text-secondary">
									{Liferay.Language.get('ab-test')}
								</span>

								<ExperimentLabel
									label={
										experience.segmentsExperimentStatus
											.label
									}
									value={
										experience.segmentsExperimentStatus
											.value
									}
								/>
							</div>
						)}
					</span>
				</button>
			</span>

			{editable && (
				<div className="align-items-center d-flex dropdown-menu__experience--btn-group px-2">
					<ClayButtonWithIcon
						className="component-action mx-2 text-secondary"
						disabled={lockedIncreasePriority}
						displayType="unstyled"
						monospaced
						onClick={handlePriorityIncrease}
						small="true"
						symbol="angle-up"
						title={Liferay.Language.get('prioritize-experience')}
						type="button"
					/>

					<ClayButtonWithIcon
						className="component-action mx-2 text-secondary"
						disabled={lockedDecreasePriority}
						displayType="unstyled"
						monospaced
						onClick={handlePriorityDecrease}
						small="true"
						symbol="angle-down"
						title={Liferay.Language.get('deprioritize-experience')}
						type="button"
					/>

					<ClayButtonWithIcon
						className="component-action mx-2 text-secondary"
						displayType="unstyled"
						monospaced
						onClick={handleExperienceEdit}
						small="true"
						symbol="pencil"
						title={Liferay.Language.get('edit-experience')}
						type="button"
					/>

					<ClayButtonWithIcon
						className="component-action mx-2 text-secondary"
						displayType="unstyled"
						monospaced
						onClick={handleExperienceDelete}
						small="true"
						symbol="times-circle"
						title={Liferay.Language.get('delete-experience')}
						type="button"
					/>
				</div>
			)}

			{experience.hasLockedSegmentsExperiment &&
				experience.segmentsExperimentURL && (
					<div className="align-items-center d-flex dropdown-menu__experience--btn-group px-2">
						<a
							className="btn btn-borderless btn-monospaced btn-sm btn-unstyled component-action mr-0 mx-2 text-secondary"
							href={experience.segmentsExperimentURL}
							onClick={handleExperimentNavigation}
							title={Liferay.Language.get('go-to-test-details')}
						>
							<ClayIcon symbol="test" />
						</a>
					</div>
				)}
		</li>
	);
};

ExperienceItem.propTypes = {
	active: PropTypes.bool,
	editable: PropTypes.bool,
	experience: PropTypes.shape(ExperienceType),
	lockedDecreasePriority: PropTypes.bool.isRequired,
	lockedIncreasePriority: PropTypes.bool.isRequired,
	onDeleteExperience: PropTypes.func.isRequired,
	onEditExperience: PropTypes.func.isRequired,
	onPriorityDecrease: PropTypes.func.isRequired,
	onPriorityIncrease: PropTypes.func.isRequired,
	onSelect: PropTypes.func.isRequired
};

export default ExperienceItem;
