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

import {STATUS_DRAFT} from '../../../utils/ExperimentsStatus.es';
import ExperimentLabel from './ExperiementLabel.es';
import {ExperienceType} from './types.es.js';

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
	const _handleSelect = () => onSelect(experience.segmentsExperienceId);
	const _handlePriorityIncrease = () =>
		onPriorityIncrease(
			experience.segmentsExperienceId,
			experience.priority
		);
	const _handlePriorityDecrease = () =>
		onPriorityDecrease(
			experience.segmentsExperienceId,
			experience.priority
		);
	const _handleExperienceEdit = () => {
		const {name, segmentsEntryId, segmentsExperienceId} = experience;

		onEditExperience({name, segmentsEntryId, segmentsExperienceId});
	};
	const _handleExperienceDelete = () => {
		const experienceHasRunningExperiment =
			experience.segmentsExperimentStatus &&
			experience.segmentsExperimentStatus.value === STATUS_DRAFT;

		const confirmationMessage = experienceHasRunningExperiment
			? Liferay.Language.get(
					'delete-experince-with-running-test-confirmation-message'
			  )
			: Liferay.Language.get('do-you-want-to-delete-this-experience');

		const confirmed = confirm(confirmationMessage);

		if (confirmed) onDeleteExperience(experience.segmentsExperienceId);
	};
	const _handleExperimentNavigation = event => {
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
					onClick={_handleSelect}
				>
					<span className="d-flex flex-column flex-grow-1 text-truncate">
						<strong className="text-truncate">
							{experience.name}

							{experience.hasLockedSegmentsExperiment && (
								<ClayIcon
									className="text-secondary"
									small="true"
									symbol="lock"
								/>
							)}
						</strong>

						<span className="audience d-block text-truncate">
							<span className="text-secondary">
								{Liferay.Language.get('audience')}{' '}
							</span>
							{experience.segmentsEntryName}
						</span>

						{experience.segmentsExperimentStatus && (
							<div>
								<span className="font-weight-normal text-secondary">
									{Liferay.Language.get('test')}{' '}
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
						className="mx-2 text-secondary"
						disabled={lockedIncreasePriority}
						displayType="unstyled"
						monospaced
						onClick={_handlePriorityIncrease}
						small="true"
						symbol="angle-up"
						title={Liferay.Language.get('prioritize-experience')}
					/>

					<ClayButtonWithIcon
						className="mx-2 text-secondary"
						disabled={lockedDecreasePriority}
						displayType="unstyled"
						monospaced
						onClick={_handlePriorityDecrease}
						small="true"
						symbol="angle-down"
						title={Liferay.Language.get('deprioritize-experience')}
					/>

					<ClayButtonWithIcon
						className="mx-2 text-secondary"
						displayType="unstyled"
						monospaced
						onClick={_handleExperienceEdit}
						small="true"
						symbol="pencil"
						title={Liferay.Language.get('edit-experience')}
					/>

					<ClayButtonWithIcon
						className="mx-2 text-secondary"
						displayType="unstyled"
						monospaced
						onClick={_handleExperienceDelete}
						small="true"
						symbol="times-circle"
						title={Liferay.Language.get('delete-experience')}
					/>
				</div>
			)}

			{experience.hasLockedSegmentsExperiment &&
				experience.segmentsExperimentURL && (
					<div className="align-items-center d-flex dropdown-menu__experience--btn-group px-2">
						<a
							className="btn btn-borderless btn-monospaced btn-sm btn-unstyled mr-0 mx-2 text-secondary"
							href={experience.segmentsExperimentURL}
							onClick={_handleExperimentNavigation}
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
