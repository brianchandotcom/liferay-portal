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

import React, {useState, useContext} from 'react';
import PropTypes from 'prop-types';
import SegmentsExperiments from './SegmentsExperiments.es';
import SegmentsExperimentsModal from './SegmentsExperimentsModal.es';
import {SegmentsExperienceType, SegmentsExperimentType} from '../types.es';
import SegmentsExperimentsContext from '../context.es';

function SegmentsExperimentsSidebar({
	initialSegmentsExperiences,
	initialSegmentsExperiment,
	selectedSegmentsExperienceId = 0
}) {
	const {endpoints, page} = useContext(SegmentsExperimentsContext);
	const [creationModal, setCreationModal] = useState({active: false});
	const [editModal, setEditionModal] = useState({active: false});
	const [
		activeSegmentsExperienceId,
		setActiveSegmentsExperienceId
	] = useState(selectedSegmentsExperienceId);
	const [segmentsExperiences, setSegmentsExperiences] = useState(
		initialSegmentsExperiences
	);
	const [segmentsExperiment, setSegmentsExperiment] = useState(
		initialSegmentsExperiment
	);

	return (
		<div className="p-3">
			<SegmentsExperiments
				activeExperience={activeSegmentsExperienceId}
				onCreateExperiment={_handleCreateExperiment}
				onEditExperiment={_handleEditExperiment}
				onSelectExperimentChange={_handleSelectExperience}
				segmentsExperiences={initialSegmentsExperiences}
				segmentsExperiment={segmentsExperiment}
			/>
			<SegmentsExperimentsModal
				active={creationModal.active}
				description={creationModal.description}
				error={creationModal.error}
				name={creationModal.name}
				onClose={_handleModalClose}
				onSave={_handleExperimentCreation}
				segmentsExperienceId={creationModal.segmentsExperienceId}
			/>
			<SegmentsExperimentsModal
				active={editModal.active}
				description={editModal.description}
				error={editModal.error}
				name={editModal.name}
				onClose={_handleEditModalClose}
				onSave={_handleExperimentEdition}
				segmentsExperienceId={editModal.segmentsExperienceId}
				segmentsExperimentId={editModal.segmentsExperiementId}
			/>
		</div>
	);

	function _handleCreateExperiment(experienceId) {
		setCreationModal({
			active: true,
			segmentsExperienceId: experienceId
		});
	}

	function _handleModalClose() {
		setCreationModal({
			active: false
		});
	}

	function _handleEditModalClose() {
		setEditionModal({
			active: false
		});
	}

	function _handleExperimentCreation(segmentsExperiment) {
		Liferay.Service(
			endpoints.createSegmentsExperimentURL,
			{
				segmentsExperienceId: segmentsExperiment.segmentsExperienceId,
				name: segmentsExperiment.name,
				description: segmentsExperiment.description,
				classPK: page.classPK,
				classNameId: page.classNameId
			},
			function _successCallback(response) {
				setSegmentsExperiment({
					description: response.description,
					name: response.name,
					segmentsExperienceId: response.segmentsExperienceId,
					segmentsExperimentId: response.segmentsExperimentId
				});
			},
			function _errorCallback() {
				setCreationModal({
					active: true,
					name: segmentsExperiment.name,
					description: segmentsExperiment.description,
					segmentsExperienceId:
						segmentsExperiment.segmentsExperienceId,
					error: Liferay.Language.get('create-experiment-error')
				});
			}
		);
	}

	function _handleEditExperiment() {
		setEditionModal({
			active: true,
			name: segmentsExperiment.name,
			description: segmentsExperiment.description,
			segmentsExperienceId: segmentsExperiment.segmentsExperienceId,
			segmentsExperiementId: segmentsExperiment.segmentsExperimentId
		});
	}

	function _handleExperimentEdition(segmentsExperiment) {
		Liferay.Service(
			endpoints.editSegmentsExperimentURL,
			{
				name: segmentsExperiment.name,
				description: segmentsExperiment.description,
				segmentsExperimentId: segmentsExperiment.segmentsExperimentId
			},
			function _successCallback(response) {
				setSegmentsExperiment({
					description: response.description,
					name: response.name,
					segmentsExperienceId: response.segmentsExperienceId,
					segmentsExperimentId: response.segmentsExperimentId
				});
			},
			function _errorCallback() {
				setCreationModal({
					active: true,
					name: segmentsExperiment.name,
					description: segmentsExperiment.description,
					segmentsExperienceId:
						segmentsExperiment.segmentsExperienceId,
					error: Liferay.Language.get('edit-experiment-error')
				});
			}
		);
	}

	function _handleSelectExperience(id) {
		let index;
		let segmentsExperiment;

		segmentsExperiences.forEach((experience, i) => {
			if (experience.segmentsExperienceId === id) {
				index = JSON.stringify(i);
				segmentsExperiment = experience.segmentsExperiment;
			}
		});

		const newSegmentsExperiments = [...segmentsExperiences];
		newSegmentsExperiments[index] = {
			...segmentsExperiences[index],
			segmentsExperiment
		};

		setActiveSegmentsExperienceId(id);
		setSegmentsExperiences(newSegmentsExperiments);
		setSegmentsExperiment(segmentsExperiment);
	}
}

SegmentsExperimentsSidebar.propTypes = {
	selectedSegmentsExperienceId: PropTypes.string,
	initialSegmentsExperiment: SegmentsExperimentType,
	initialSegmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType)
};

export default SegmentsExperimentsSidebar;
