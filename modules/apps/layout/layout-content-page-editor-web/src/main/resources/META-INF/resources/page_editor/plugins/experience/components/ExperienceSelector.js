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
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';
import {createPortal} from 'react-dom';

import {config} from '../../../app/config/index';
import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import selectCanUpdateExperiences from '../../../app/selectors/selectCanUpdateExperiences';
import selectCanUpdateSegments from '../../../app/selectors/selectCanUpdateSegments';
import createExperience from '../thunks/createExperience';
import duplicateExperience from '../thunks/duplicateExperience';
import removeExperience from '../thunks/removeExperience';
import updateExperience from '../thunks/updateExperience';
import updateExperiencePriority from '../thunks/updateExperiencePriority';
import {
	recoverModalExperienceState,
	storeModalExperienceState,
	useDebounceCallback,
} from '../utils';
import ExperienceModal from './ExperienceModal';
import ExperiencesList from './ExperiencesList';

/**
 * It produces an object with a target and subtarget keys indicating what experiences
 * should change to change the priority of target priority.
 */
function getUpdateExperiencePriorityTargets(
	orderedExperiences,
	targetExperienceId,
	direction
) {
	const targetIndex = orderedExperiences.findIndex(
		(experience) => experience.segmentsExperienceId === targetExperienceId
	);

	let subtargetIndex;

	if (direction === 'up') {
		subtargetIndex = targetIndex - 1;
	}
	else {
		subtargetIndex = targetIndex + 1;
	}

	const subtargetExperience = orderedExperiences[subtargetIndex];
	const targetExperience = orderedExperiences[targetIndex];

	return {
		priority: subtargetExperience.priority,
		segmentsExperienceId: targetExperience.segmentsExperienceId,
	};
}

const ExperienceSelector = ({
	experiences,
	segments,
	selectId,
	selectedExperience,
}) => {
	const dispatch = useDispatch();

	const canUpdateExperiences = useSelector(selectCanUpdateExperiences);
	const canUpdateSegments = useSelector(selectCanUpdateSegments);

	const buttonRef = useRef();
	const [buttonBoundingClientRect, setButtonBoundingClientRect] = useState({
		bottom: 0,
		left: 0,
	});

	const isMounted = useIsMounted();
	const [open, setOpen] = useState(false);
	const [openModal, setOpenModal] = useState(false);
	const [editingExperience, setEditingExperience] = useState({});

	const {observer: modalObserver, onClose: onModalClose} = useModal({
		onClose: () => {
			setOpenModal(false);
			setEditingExperience({});
		},
	});

	const [debouncedSetOpen] = useDebounceCallback((value) => {
		if (isMounted() && buttonRef.current) {
			setButtonBoundingClientRect(
				buttonRef.current.getBoundingClientRect()
			);

			setOpen(value);
		}
	}, 100);

	const handleDropdownButtonClick = () => debouncedSetOpen(!open);
	const handleDropdownButtonBlur = () => debouncedSetOpen(false);
	const handleDropdownBlur = () => debouncedSetOpen(false);
	const handleDropdownFocus = () => debouncedSetOpen(true);

	const handleNewSegmentClick = ({
		experienceId,
		experienceName,
		segmentId,
	}) => {
		storeModalExperienceState({
			experienceId,
			experienceName,
			plid: config.plid,
			segmentId,
		});

		Liferay.Util.navigate(config.editSegmentsEntryURL);
	};

	useEffect(() => {
		if (config.plid) {
			const modalExperienceState = recoverModalExperienceState();

			if (
				modalExperienceState &&
				config.plid === modalExperienceState.plid
			) {
				setOpenModal(true);
				setEditingExperience({
					languageIds: modalExperienceState.languageIds,
					name: modalExperienceState.experienceName,
					segmentsEntryId:
						config.selectedSegmentsEntryId ||
						modalExperienceState.segmentId,
					segmentsExperienceId: modalExperienceState.experienceId,
				});
			}
		}
	}, []);

	useEffect(() => {
		if (open) {
			const element = document.querySelector(
				'.dropdown-menu__experience--active'
			);

			element?.scrollIntoView?.({
				behavior: 'auto',
				block: 'center',
				inline: 'nearest',
			});
		}
	}, [open]);

	useEffect(() => {
		const element = document.querySelector(
			'.dropdown-menu__experience--active'
		);

		element?.scrollIntoView?.({
			behavior: 'smooth',
			block: 'center',
			inline: 'nearest',
		});
	}, [

		//LPS-127205

		experiences.length,
	]);

	const handleExperienceCreation = ({
		languageIds,
		name,
		segmentsEntryId,
		segmentsExperienceId,
	}) => {
		if (segmentsExperienceId) {
			return dispatch(
				updateExperience({
					languageIds,
					name,
					segmentsEntryId,
					segmentsExperienceId,
				})
			)
				.then(() => {
					if (isMounted()) {
						onModalClose();
					}
					openToast({
						message: Liferay.Language.get(
							'the-experience-was-updated-successfully'
						),
						type: 'success',
					});
				})
				.catch(() => {
					if (isMounted()) {
						setEditingExperience({
							error: Liferay.Language.get(
								'an-unexpected-error-occurred-while-updating-the-experience'
							),
							languageIds,
							name,
							segmentsEntryId,
							segmentsExperienceId,
						});
					}
				});
		}
		else {
			return dispatch(
				createExperience({
					languageIds,
					name,
					segmentsEntryId,
				})
			)
				.then(() => {
					if (isMounted()) {
						onModalClose();
					}
					openToast({
						message: Liferay.Language.get(
							'the-experience-was-created-successfully'
						),
						type: 'success',
					});
				})
				.catch(() => {
					if (isMounted()) {
						setEditingExperience({
							error: Liferay.Language.get(
								'an-unexpected-error-occurred-while-creating-the-experience'
							),
							languageIds,
							name,
							segmentsEntryId,
							segmentsExperienceId,
						});
					}
				});
		}
	};

	const handleOnNewExperiecneClick = () => setOpenModal(true);

	const handleEditExperienceClick = (experienceData) => {
		const {
			languageIds,
			name,
			segmentsEntryId,
			segmentsExperienceId,
		} = experienceData;

		setOpenModal(true);

		setEditingExperience({
			languageIds,
			name,
			segmentsEntryId,
			segmentsExperienceId,
		});
	};

	const deleteExperience = (id) => {
		dispatch(
			removeExperience({
				segmentsExperienceId: id,
				selectedExperienceId: selectedExperience.segmentsExperienceId,
			})
		)
			.then(() => {
				openToast({
					message: Liferay.Language.get(
						'the-experience-was-deleted-successfully'
					),
					type: 'success',
				});
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
	};

	const handleExperienceDuplication = (id) => {
		dispatch(
			duplicateExperience({
				segmentsExperienceId: id,
			})
		)
			.then(() => {
				openToast({
					message: Liferay.Language.get(
						'the-experience-was-duplicated-successfully'
					),
					type: 'success',
				});
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
	};

	const decreasePriority = (id) => {
		const target = getUpdateExperiencePriorityTargets(
			experiences,
			id,
			'down'
		);

		dispatch(updateExperiencePriority(target));
	};

	const increasePriority = (id) => {
		const target = getUpdateExperiencePriorityTargets(
			experiences,
			id,
			'up'
		);

		dispatch(updateExperiencePriority(target));
	};

	return (
		<>
			<ClayButton
				className="form-control-select pr-4 text-left text-truncate"
				displayType="secondary"
				id={selectId}
				onBlur={handleDropdownButtonBlur}
				onClick={handleDropdownButtonClick}
				ref={buttonRef}
				small
				type="button"
			>
				<ClayLayout.ContentRow verticalAlign="center">
					<ClayLayout.ContentCol expand>
						<span className="text-truncate">
							{selectedExperience.name}
						</span>
					</ClayLayout.ContentCol>
					<ClayLayout.ContentCol>
						{selectedExperience.hasLockedSegmentsExperiment && (
							<ClayIcon symbol="lock" />
						)}
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</ClayButton>

			{open &&
				createPortal(
					<div
						className="dropdown-menu p-4 page-editor__toolbar-experience__dropdown-menu toggled"
						onBlur={handleDropdownBlur}
						onFocus={handleDropdownFocus}
						style={{
							left: buttonBoundingClientRect.left,
							top: buttonBoundingClientRect.bottom,
						}}
						tabIndex="-1"
					>
						<ExperiencesSelectorHeader
							canCreateExperiences={canUpdateExperiences}
							onNewExperience={handleOnNewExperiecneClick}
							showEmptyStateMessage={experiences.length <= 1}
						/>

						{experiences.length > 1 && (
							<ExperiencesList
								activeExperienceId={
									selectedExperience.segmentsExperienceId
								}
								canUpdateExperiences={canUpdateExperiences}
								defaultExperienceId={
									config.defaultSegmentsExperienceId
								}
								experiences={experiences}
								onDeleteExperience={deleteExperience}
								onDuplicateExperience={
									handleExperienceDuplication
								}
								onEditExperience={handleEditExperienceClick}
								onPriorityDecrease={decreasePriority}
								onPriorityIncrease={increasePriority}
							/>
						)}
					</div>,
					document.body
				)}

			{openModal && (
				<ExperienceModal
					canUpdateSegments={canUpdateSegments}
					errorMessage={editingExperience.error}
					experienceId={editingExperience.segmentsExperienceId}
					initialName={editingExperience.name}
					languageIds={editingExperience.languageIds}
					observer={modalObserver}
					onClose={onModalClose}
					onErrorDismiss={() => setEditingExperience({error: null})}
					onNewSegmentClick={handleNewSegmentClick}
					onSubmit={handleExperienceCreation}
					segmentId={editingExperience.segmentsEntryId}
					segments={segments}
				/>
			)}
		</>
	);
};

const ExperiencesSelectorHeader = ({
	canCreateExperiences,
	onNewExperience,
	showEmptyStateMessage,
}) => {
	return (
		<>
			<ClayLayout.ContentRow className="mb-3" verticalAlign="center">
				<ClayLayout.ContentCol expand>
					<h3 className="mb-0">
						{Liferay.Language.get('select-experience')}
					</h3>
				</ClayLayout.ContentCol>
				<ClayLayout.ContentCol>
					{canCreateExperiences === true && (
						<ClayButton
							aria-label={Liferay.Language.get('new-experience')}
							displayType="secondary"
							onClick={onNewExperience}
							small
						>
							{Liferay.Language.get('new-experience')}
						</ClayButton>
					)}
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			{canCreateExperiences && (
				<p className="text-secondary">
					{showEmptyStateMessage
						? Liferay.Language.get(
								'experience-help-message-empty-state'
						  )
						: Liferay.Language.get(
								'experience-help-message-started-state'
						  )}
				</p>
			)}

			<ClayAlert
				className="mx-0"
				displayType="warning"
				title={Liferay.Language.get('warning')}
			>
				{Liferay.Language.get(
					'changes-to-experiences-are-applied-immediately'
				)}
			</ClayAlert>
		</>
	);
};

export default ExperienceSelector;
