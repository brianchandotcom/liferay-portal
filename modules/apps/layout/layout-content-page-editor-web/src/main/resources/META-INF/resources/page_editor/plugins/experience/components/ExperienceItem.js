/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import {navigate, openConfirmModal, setSessionValue} from 'frontend-js-web';
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
	onDuplicateExperience,
	onEditExperience,
	onPriorityDecrease,
	onPriorityIncrease,
	onSelect,
}) => {
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

		openConfirmModal({
			message: confirmationMessage,
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					onDeleteExperience(experience.segmentsExperienceId);
				}
			},
		});
	};
	const handleExperienceDuplicate = () => {
		onDuplicateExperience(experience.segmentsExperienceId);
	};
	const handleExperimentNavigation = (event) => {
		event.preventDefault();

		setSessionValue(
			'com.liferay.segments.experiment.web_panelState',
			'open'
		).then(() => {
			navigate(experience.segmentsExperimentURL);
		});
	};

	const itemClassName = classNames('d-flex dropdown-menu__experience', {
		'dropdown-menu__experience--active': active,
	});

	return (
		<ClayList.Item aria-current={active} className={itemClassName}>
			<ClayList.ItemField expand>
				<ClayButton displayType="unstyled" onClick={handleSelect}>
					<div className="c-inner" tabIndex="-1">
						<ClayLayout.ContentRow verticalAlign="center">
							<ClayLayout.ContentCol
								style={{flexShrink: 1, minWidth: 0}}
							>
								<ClayLayout.ContentSection>
									<span className="text-truncate-inline">
										<span
											className="font-weight-semi-bold text-truncate"
											data-tooltip-align="top"
											title={experience.name}
										>
											{experience.name}
										</span>

										{experience.hasLockedSegmentsExperiment && (
											<ExperienceLockIcon />
										)}

										{experience.active ? (
											<ClayLabel
												className="flex-shrink-0 inline-item-after"
												displayType="success"
											>
												{Liferay.Language.get('active')}
											</ClayLabel>
										) : (
											<ClayLabel
												className="flex-shrink-0 inline-item-after"
												displayType="secondary"
											>
												{Liferay.Language.get(
													'inactive'
												)}
											</ClayLabel>
										)}
									</span>

									<span className="text-truncate">
										<span className="mr-1 text-secondary">
											{Liferay.Language.get('audience')}
										</span>

										{experience.segmentsEntryName}
									</span>

									{experience.segmentsExperimentStatus && (
										<div>
											<span className="font-weight-normal inline-item-before text-secondary">
												{Liferay.Language.get(
													'ab-test'
												)}
											</span>

											<ExperimentLabel
												label={
													experience
														.segmentsExperimentStatus
														.label
												}
												value={
													experience
														.segmentsExperimentStatus
														.value
												}
											/>
										</div>
									)}
								</ClayLayout.ContentSection>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</div>
				</ClayButton>
			</ClayList.ItemField>

			<ClayList.ItemField className="align-self-center">
				<ExperienceActions
					editable={editable}
					experience={experience}
					handleExperienceDelete={handleExperienceDelete}
					handleExperienceDuplicate={handleExperienceDuplicate}
					handleExperienceEdit={handleExperienceEdit}
					handleExperimentNavigation={handleExperimentNavigation}
					handlePriorityDecrease={handlePriorityDecrease}
					handlePriorityIncrease={handlePriorityIncrease}
					lockedDecreasePriority={lockedDecreasePriority}
					lockedIncreasePriority={lockedIncreasePriority}
				/>
			</ClayList.ItemField>
		</ClayList.Item>
	);
};

const ExperienceActions = ({
	editable,
	experience,
	handleExperienceDelete,
	handleExperienceDuplicate,
	handleExperienceEdit,
	handleExperimentNavigation,
	handlePriorityDecrease,
	handlePriorityIncrease,
	lockedDecreasePriority,
	lockedIncreasePriority,
}) => {
	return (
		<>
			{editable && (
				<div className="pl-2">
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get(
							'prioritize-experience'
						)}
						borderless
						className="component-action mx-1"
						disabled={lockedIncreasePriority}
						displayType="unstyled"
						monospaced
						onClick={handlePriorityIncrease}
						outline
						symbol="angle-up"
						title={Liferay.Language.get('prioritize-experience')}
						type="button"
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get(
							'deprioritize-experience'
						)}
						borderless
						className="component-action mx-1"
						disabled={lockedDecreasePriority}
						displayType="unstyled"
						monospaced
						onClick={handlePriorityDecrease}
						outline
						symbol="angle-down"
						title={Liferay.Language.get('deprioritize-experience')}
						type="button"
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('edit-experience')}
						borderless
						className="component-action mx-1"
						displayType="unstyled"
						monospaced
						onClick={handleExperienceEdit}
						outline
						symbol="pencil"
						title={Liferay.Language.get('edit-experience')}
						type="button"
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get(
							'duplicate-experience'
						)}
						borderless
						className="component-action mx-1"
						displayType="unstyled"
						monospaced
						onClick={handleExperienceDuplicate}
						outline
						symbol="copy"
						title={Liferay.Language.get('duplicate-experience')}
						type="button"
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('delete-experience')}
						borderless
						className="component-action mx-1"
						displayType="unstyled"
						monospaced
						onClick={handleExperienceDelete}
						outline
						symbol="trash"
						title={Liferay.Language.get('delete-experience')}
						type="button"
					/>
				</div>
			)}

			{experience.hasLockedSegmentsExperiment &&
				experience.segmentsExperimentURL && (
					<div className="pl-2">
						<ClayLink
							aria-label={Liferay.Language.get(
								'go-to-test-details'
							)}
							borderless
							className="component-action mx-1"
							displayType="unstyled"
							href={experience.segmentsExperimentURL}
							monospaced
							onClick={handleExperimentNavigation}
							outline
							title={Liferay.Language.get('go-to-test-details')}
						>
							<ClayIcon symbol="test" />
						</ClayLink>
					</div>
				)}
		</>
	);
};

const ExperienceLockIcon = () => {
	const iconRef = React.useRef();
	const [showtoolTip, setShowtoolTip] = React.useState(false);

	return (
		<span className="inline-item-after">
			<ClayIcon
				className="text-secondary"
				onMouseEnter={() => setShowtoolTip(true)}
				onMouseLeave={() => setShowtoolTip(false)}
				ref={iconRef}
				symbol="lock"
			/>

			{showtoolTip && (
				<Popover
					anchor={iconRef.current}
					header={Liferay.Language.get('experience-locked')}
				>
					{Liferay.Language.get(
						'edit-is-not-allowed-for-this-experience'
					)}
				</Popover>
			)}
		</span>
	);
};

ExperienceItem.propTypes = {
	active: PropTypes.bool,
	editable: PropTypes.bool,
	experience: PropTypes.shape(ExperienceType),
	lockedDecreasePriority: PropTypes.bool.isRequired,
	lockedIncreasePriority: PropTypes.bool.isRequired,
	onDeleteExperience: PropTypes.func.isRequired,
	onDuplicateExperience: PropTypes.func.isRequired,
	onEditExperience: PropTypes.func.isRequired,
	onPriorityDecrease: PropTypes.func.isRequired,
	onPriorityIncrease: PropTypes.func.isRequired,
	onSelect: PropTypes.func.isRequired,
};

export default ExperienceItem;
