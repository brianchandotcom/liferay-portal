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
import {useModal} from '@clayui/modal';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React from 'react';

import {
	CREATE_SEGMENTS_EXPERIENCE,
	EDIT_SEGMENTS_EXPERIENCE
} from '../../../actions/actions.es';
import useDispatch from '../../../store/hooks/useDispatch.es';
import useSelector from '../../../store/hooks/useSelector.es';
import ExperienceModal from './ExperienceModal.es';
import ExperiencesList from './ExperiencesList.es';
import {ExperienceType, SegmentType} from './types.es';
import {
	storeModalExperienceState,
	recoverModalExperienceState,
	useDebounceCallback
} from './utils.es';

const {useEffect, useState} = React;

const ExperienceDropdownHeader = ({
	canCreateExperiences,
	onNewExperience,
	showEmptyStateMessage
}) => (
	<>
		<div className="align-items-end d-flex justify-content-between mb-4">
			<h3 className="mb-0">
				{Liferay.Language.get('select-experience')}
			</h3>

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
		</div>

		{canCreateExperiences && (
			<p className="mb-4 text-secondary">
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
			className="mt-4 mx-0"
			displayType="warning"
			title={Liferay.Language.get('warning')}
		>
			{Liferay.Language.get(
				'changes-to-experiences-are-applied-immediately'
			)}
		</ClayAlert>
	</>
);

ExperienceDropdownHeader.propTypes = {
	canCreateExperiences: PropTypes.bool.isRequired,
	onNewExperience: PropTypes.func.isRequired,
	showEmptyStateMessage: PropTypes.bool.isRequired
};

const ExperiencesSelector = ({
	activeExperience,
	classPK,
	editSegmentsEntryURL,
	experiences,
	hasSegmentsPermission,
	hasUpdatePermissions,
	lockedActiveExperience,
	portletNamespace,
	segments,
	selectedSegmentsEntryId
}) => {
	const dispatch = useDispatch();
	const isMounted = useIsMounted();

	const [open, setOpen] = useState(false);
	const [openModal, setOpenModal] = useState(false);
	const [editingExperience, setEditingExperience] = useState({});

	const {observer, onClose: onModalClose} = useModal({
		onClose: () => {
			if (isMounted()) {
				setEditingExperience({});
				setOpenModal(false);
			}
		}
	});

	const [debouncedSetOpen] = useDebounceCallback(value => {
		if (isMounted()) {
			setOpen(value);
		}
	}, 100);

	const _handleDropdownButtonClick = () => debouncedSetOpen(!open);
	const _handleDropdownButtonBlur = () => debouncedSetOpen(false);
	const _handleDropdownBlur = () => debouncedSetOpen(false);
	const _handleDropdownFocus = () => debouncedSetOpen(true);

	const _handleNewSegmentClick = ({
		experienceId,
		experienceName,
		segmentId
	}) => {
		storeModalExperienceState({
			classPK,
			experienceId,
			experienceName,
			segmentId
		});

		Liferay.Util.navigate(editSegmentsEntryURL);
	};

	useEffect(() => {
		if (classPK) {
			const modalExperienceState = recoverModalExperienceState();

			if (
				modalExperienceState &&
				classPK === modalExperienceState.classPK
			) {
				setOpenModal(true);
				setEditingExperience({
					name: modalExperienceState.experienceName,
					segmentsEntryId:
						selectedSegmentsEntryId ||
						modalExperienceState.segmentId,
					segmentsExperienceId: modalExperienceState.experienceId
				});
			}
		}
	}, [classPK, selectedSegmentsEntryId]);

	const _openEditModal = ({name, segmentsEntryId, segmentsExperienceId}) => {
		setEditingExperience({name, segmentsEntryId, segmentsExperienceId});
		debouncedSetOpen(true);
		setOpenModal(true);
	};
	const _openCreateModal = () => {
		debouncedSetOpen(true);
		setOpenModal(true);
	};

	const _handleExperienceCreation = ({
		name,
		segmentsEntryId,
		segmentsExperienceId
	}) => {
		if (segmentsExperienceId) {
			dispatch({
				name,
				segmentsEntryId,
				segmentsExperienceId,
				type: EDIT_SEGMENTS_EXPERIENCE
			})
				.done(() => {
					if (isMounted()) {
						onModalClose();
					}
					Liferay.Util.openToast({
						title: Liferay.Language.get(
							'the-experience-was-updated-successfully'
						),
						type: 'success'
					});
				})
				.failed(() => {
					if (isMounted()) {
						setEditingExperience({
							error: Liferay.Language.get(
								'an-unexpected-error-occurred-while-creating-the-experience'
							),
							name,
							segmentsEntryId,
							segmentsExperienceId
						});
					}
				});
		} else {
			dispatch({
				name,
				segmentsEntryId,
				type: CREATE_SEGMENTS_EXPERIENCE
			})
				.done(() => {
					if (isMounted()) {
						onModalClose();
					}
					Liferay.Util.openToast({
						title: Liferay.Language.get(
							'the-experience-was-created-successfully'
						),
						type: 'success'
					});
				})
				.failed(_error => {
					if (isMounted()) {
						setEditingExperience({
							error: Liferay.Language.get(
								'an-unexpected-error-occurred-while-updating-the-experience'
							),
							name,
							segmentsEntryId,
							segmentsExperienceId
						});
					}
				});
		}
	};

	const activeExperienceId = activeExperience.segmentsExperienceId;
	const showEmptyStateMessage = experiences.length < 2 || segments.length < 1;
	const canCreateExperiences =
		hasUpdatePermissions && (hasSegmentsPermission || segments.lenght > 0);

	return (
		<div className="position-relative segments-experience-selector">
			<label
				className="mr-2"
				htmlFor={`${portletNamespace}SegmentsExperienceSelector`}
			>
				{Liferay.Language.get('experience')}
			</label>

			<button
				className="align-items-end btn btn-secondary btn-sm d-inline-flex form-control-select justify-content-between mr-2 text-left text-truncate"
				onBlur={_handleDropdownButtonBlur}
				onClick={_handleDropdownButtonClick}
				type="button"
			>
				<span className="text-truncate">{activeExperience.name}</span>

				{lockedActiveExperience && <ClayIcon symbol="lock" />}
			</button>

			{open && (
				<div
					className="dropdown-menu p-4 rounded toggled"
					onBlur={_handleDropdownBlur}
					onFocus={_handleDropdownFocus}
					tabIndex="-1"
				>
					<ExperienceDropdownHeader
						canCreateExperiences={canCreateExperiences}
						onNewExperience={_openCreateModal}
						showEmptyStateMessage={showEmptyStateMessage}
					/>

					{experiences.length > 1 && (
						<ExperiencesList
							activeExperienceId={activeExperienceId}
							experiences={experiences}
							hasUpdatePermissions={hasUpdatePermissions}
							onEditExperience={_openEditModal}
						/>
					)}
				</div>
			)}

			{openModal && (
				<ExperienceModal
					errorMessage={editingExperience.error}
					experienceId={editingExperience.segmentsExperienceId}
					hasSegmentsPermission={hasSegmentsPermission}
					initialName={editingExperience.name}
					observer={observer}
					onClose={onModalClose}
					onErrorDismiss={() => setEditingExperience({error: null})}
					onNewSegmentClick={_handleNewSegmentClick}
					onSubmit={_handleExperienceCreation}
					segmentId={editingExperience.segmentsEntryId}
					segments={segments}
				/>
			)}
		</div>
	);
};

ExperiencesSelector.propTypes = {
	activeExperience: PropTypes.shape(ExperienceType),
	classPK: PropTypes.string.isRequired,
	editSegmentsEntryURL: PropTypes.string,
	experiences: PropTypes.arrayOf(PropTypes.shape(ExperienceType)),
	hasSegmentsPermission: PropTypes.bool.isRequired,
	hasUpdatePermissions: PropTypes.bool.isRequired,
	lockedActiveExperience: PropTypes.bool.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	segments: PropTypes.arrayOf(PropTypes.shape(SegmentType)),
	selectedSegmentsEntryId: PropTypes.string
};

const DEFAULT_SEGMNET_ID = '0';

/**
 * Renders `ExperienceSelector` component with props gathered from the state
 */
const ExperiencesSelectorWrapper = () => {
	const activeExperience = useSelector(state => {
		const activeExperienceId = state.segmentsExperienceId;

		return state.availableSegmentsExperiences[activeExperienceId];
	});
	const classPK = useSelector(state => state.classPK);
	const experiences = useSelector(state => {
		return Object.entries(state.availableSegmentsExperiences)
			.map(([, experience]) => {
				const segment =
					state.availableSegmentsEntries[experience.segmentsEntryId];

				return {...experience, segmentsEntryName: segment.name};
			})
			.sort((a, b) => b.priority - a.priority);
	});
	const hasSegmentsPermission = useSelector(state => {
		return state.hasEditSegmentsEntryPermission;
	});
	const hasUpdatePermissions = useSelector(
		state => state.hasUpdatePermissions
	);
	const segments = useSelector(state =>
		Object.entries(state.availableSegmentsEntries)
			.map(([, segment]) => segment)
			.filter(segment => segment.segmentsEntryId !== DEFAULT_SEGMNET_ID)
	);
	const editSegmentsEntryURL = useSelector(
		state => state.editSegmentsEntryURL
	);
	const lockedActiveExperience = useSelector(
		state => state.lockedSegmentsExperience
	);
	const portletNamespace = useSelector(state => state.portletNamespace);
	const selectedSegmentsEntryId = useSelector(
		state => state.selectedSegmentsEntryId
	);

	return (
		<ExperiencesSelector
			activeExperience={activeExperience}
			classPK={classPK}
			editSegmentsEntryURL={editSegmentsEntryURL}
			experiences={experiences}
			hasSegmentsPermission={hasSegmentsPermission}
			hasUpdatePermissions={hasUpdatePermissions}
			lockedActiveExperience={lockedActiveExperience}
			portletNamespace={portletNamespace}
			segments={segments}
			selectedSegmentsEntryId={selectedSegmentsEntryId}
		/>
	);
};

export default ExperiencesSelectorWrapper;
