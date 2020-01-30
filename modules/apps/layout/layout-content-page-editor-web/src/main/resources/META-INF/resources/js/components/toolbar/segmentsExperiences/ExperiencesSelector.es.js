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
import React, {useMemo} from 'react';

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
	defaultSegmentsExperienceId,
	editSegmentsEntryURL,
	experiences,
	hasSegmentsPermission,
	hasUpdatePermissions,
	lockedActiveExperience = false,
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

	const handleDropdownButtonClick = () => debouncedSetOpen(!open);
	const handleDropdownButtonBlur = () => debouncedSetOpen(false);
	const handleDropdownBlur = () => debouncedSetOpen(false);
	const handleDropdownFocus = () => debouncedSetOpen(true);

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

	const openEditModal = ({name, segmentsEntryId, segmentsExperienceId}) => {
		setEditingExperience({name, segmentsEntryId, segmentsExperienceId});
		debouncedSetOpen(true);
		setOpenModal(true);
	};
	const openCreateModal = () => {
		debouncedSetOpen(true);
		setOpenModal(true);
	};

	const handleExperienceCreation = ({
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
				className="mr-sm-2"
				htmlFor={`${portletNamespace}SegmentsExperienceSelector`}
			>
				{Liferay.Language.get('experience')}
			</label>

			<button
				className="align-items-end btn btn-secondary btn-sm d-inline-flex form-control-select justify-content-between mr-sm-2 text-left text-truncate"
				id={`${portletNamespace}SegmentsExperienceSelector`}
				onBlur={handleDropdownButtonBlur}
				onClick={handleDropdownButtonClick}
				type="button"
			>
				<span className="text-truncate">{activeExperience.name}</span>

				{lockedActiveExperience && <ClayIcon symbol="lock" />}
			</button>

			{open && (
				<div
					className="dropdown-menu p-4 rounded toggled"
					onBlur={handleDropdownBlur}
					onFocus={handleDropdownFocus}
					tabIndex="-1"
				>
					<ExperienceDropdownHeader
						canCreateExperiences={canCreateExperiences}
						onNewExperience={openCreateModal}
						showEmptyStateMessage={showEmptyStateMessage}
					/>

					{experiences.length > 1 && (
						<ExperiencesList
							activeExperienceId={activeExperienceId}
							defaultSegmentsExperienceId={
								defaultSegmentsExperienceId
							}
							experiences={experiences}
							hasUpdatePermissions={hasUpdatePermissions}
							onEditExperience={openEditModal}
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
					onSubmit={handleExperienceCreation}
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
	lockedActiveExperience: PropTypes.bool,
	portletNamespace: PropTypes.string.isRequired,
	segments: PropTypes.arrayOf(PropTypes.shape(SegmentType)),
	selectedSegmentsEntryId: PropTypes.string
};

/**
 * Renders `ExperienceSelector` component with props gathered from the state
 */
const ExperiencesSelectorWrapper = () => {
	const {
		activeExperience,
		availableSegmentsEntries,
		availableSegmentsExperiences,
		classPK,
		defaultSegmentsEntryId,
		defaultSegmentsExperienceId,
		editSegmentsEntryURL,
		hasSegmentsPermission,
		hasUpdatePermissions,
		lockedActiveExperience,
		portletNamespace,
		selectedSegmentsEntryId
	} = useSelector(state => {
		return {
			activeExperience:
				state.availableSegmentsExperiences[state.segmentsExperienceId],
			availableSegmentsEntries: state.availableSegmentsEntries,
			availableSegmentsExperiences: state.availableSegmentsExperiences,
			classPK: state.classPK,
			defaultSegmentsEntryId: state.defaultSegmentsEntryId,
			defaultSegmentsExperienceId: state.defaultSegmentsExperienceId,
			editSegmentsEntryURL: state.editSegmentsEntryURL,
			hasSegmentsPermission: state.hasEditSegmentsEntryPermission,
			hasUpdatePermissions: state.hasUpdatePermissions,
			lockedActiveExperience: state.lockedSegmentsExperience,
			portletNamespace: state.portletNamespace,
			selectedSegmentsEntryId: state.selectedSegmentsEntryId
		};
	});

	const experiences = useMemo(
		() =>
			Object.values(availableSegmentsExperiences)
				.map(experience => {
					const segment =
						availableSegmentsEntries[experience.segmentsEntryId];

					return {...experience, segmentsEntryName: segment.name};
				})
				.sort((a, b) => b.priority - a.priority),
		[availableSegmentsEntries, availableSegmentsExperiences]
	);

	const segments = useMemo(
		() =>
			Object.values(availableSegmentsEntries).filter(
				segment => segment.segmentsEntryId !== defaultSegmentsEntryId
			),
		[availableSegmentsEntries, defaultSegmentsEntryId]
	);

	return (
		<ExperiencesSelector
			activeExperience={activeExperience}
			classPK={classPK}
			defaultSegmentsExperienceId={defaultSegmentsExperienceId}
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
