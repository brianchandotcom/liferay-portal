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
import {
	SegmentsExperienceType,
	SegmentsExperimentType,
	initialSegmentsVariantType
} from '../types.es';
import SegmentsExperimentsContext from '../context.es';
import UnsupportedSegmentsExperiments from './UnsupportedSegmentsExperiments.es';

function SegmentsExperimentsSidebar({
	initialSegmentsExperiences,
	initialSegmentsVariants,
	initialSegmentsExperiment,
	initialSelectedSegmentsExperienceId = '0'
}) {
	const {endpoints, page, contentPageEditorNamespace} = useContext(
		SegmentsExperimentsContext
	);
	const [creationModal, setCreationModal] = useState({active: false});
	const [editModal, setEditionModal] = useState({active: false});
	const [segmentsExperiment, setSegmentsExperiment] = useState(
		initialSegmentsExperiment
	);
	const [variants, setVariants] = useState(
		initialSegmentsVariants.map(variant => {
			if (
				variant.segmentsExperienceId ===
				initialSegmentsExperiment.segmentsExperienceId
			)
				return {...variant, control: true};
			return {...variant, control: false};
		})
	);

	return page.type === 'content' ? (
		<div className="p-3">
			<SegmentsExperiments
				onCreateSegmentsExperiment={_handleCreateSegmentsExperiment}
				onEditSegmentsExperiment={_handleEditSegmentsExperiment}
				onSelectSegmentsExperienceChange={
					_handleSelectSegmentsExperience
				}
				onVariantCreation={_handleVariantCreation}
				segmentsExperiences={initialSegmentsExperiences}
				segmentsExperiment={segmentsExperiment}
				selectedSegmentsExperienceId={
					initialSelectedSegmentsExperienceId
				}
				variants={variants}
			/>
			{creationModal.active && (
				<SegmentsExperimentsModal
					active={creationModal.active}
					description={creationModal.description}
					error={creationModal.error}
					name={creationModal.name}
					onClose={_handleModalClose}
					onSave={_handleExperimentCreation}
					segmentsExperienceId={creationModal.segmentsExperienceId}
					title={Liferay.Language.get('create-new-test')}
				/>
			)}
			{editModal.active && (
				<SegmentsExperimentsModal
					active={editModal.active}
					description={editModal.description}
					error={editModal.error}
					name={editModal.name}
					onClose={_handleEditModalClose}
					onSave={_handleExperimentEdition}
					segmentsExperienceId={editModal.segmentsExperienceId}
					segmentsExperimentId={editModal.segmentsExperimentId}
					title={Liferay.Language.get('edit-test')}
				/>
			)}
		</div>
	) : (
		<UnsupportedSegmentsExperiments />
	);

	function _handleCreateSegmentsExperiment(experienceId) {
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
				setCreationModal({
					active: false
				});
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
					error: Liferay.Language.get('create-test-error')
				});
			}
		);
	}

	function _handleEditSegmentsExperiment() {
		setEditionModal({
			active: true,
			name: segmentsExperiment.name,
			description: segmentsExperiment.description,
			segmentsExperienceId: segmentsExperiment.segmentsExperienceId,
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId
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
				setEditionModal({
					active: false
				});
				setSegmentsExperiment({
					description: response.description,
					name: response.name,
					segmentsExperienceId: response.segmentsExperienceId,
					segmentsExperimentId: response.segmentsExperimentId
				});
			},
			function _errorCallback() {
				setEditionModal({
					active: true,
					name: segmentsExperiment.name,
					description: segmentsExperiment.description,
					segmentsExperienceId:
						segmentsExperiment.segmentsExperienceId,
					segmentsExperimentId:
						segmentsExperiment.segmentsExperimentId,
					error: Liferay.Language.get('edit-test-error')
				});
			}
		);
	}

	function _handleSelectSegmentsExperience(segmentsExperienceId) {
		const currentUrl = new URL(window.location.href);
		const urlQueryString = currentUrl.search;
		const urlSearchParams = new URLSearchParams(urlQueryString);

		urlSearchParams.set('segmentsExperienceId', segmentsExperienceId);
		currentUrl.search = urlSearchParams.toString();

		const newUrl = currentUrl.toString();

		Liferay.Util.navigate(newUrl);
	}

	function _handleVariantCreation(name) {
		return new Promise((resolve, reject) => {
			const body = {
				name,
				classPK: page.classPK,
				classNameId: page.classNameId,
				segmentsExperimentId: segmentsExperiment.segmentsExperimentId
			};
			fetch(endpoints.createSegmentsVariantURL, {
				body: getFormData(body, contentPageEditorNamespace),
				credentials: 'include',
				method: 'POST'
			})
				.then(response => response.json())
				.then(objectResponse => {
					if (objectResponse.error) throw objectResponse.error;
					return objectResponse;
				})
				.then(({segmentsExperimentRel}) => {
					const {
						name,
						segmentsExperienceId,
						segmentsExperimentId,
						segmentsExperimentRelId
					} = segmentsExperimentRel;

					setVariants([
						...variants,
						{
							name,
							segmentsExperimentId,
							segmentsExperienceId,
							segmentsExperimentRelId
						}
					]);
					resolve();
				})
				.catch(error => reject(error));
		});
	}
}

SegmentsExperimentsSidebar.propTypes = {
	initialSelectedSegmentsExperienceId: PropTypes.string,
	initialSegmentsExperiment: SegmentsExperimentType,
	initialSegmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	initialSegmentsVariants: PropTypes.arrayOf(initialSegmentsVariantType)
		.isRequired
};

export default SegmentsExperimentsSidebar;

function getFormData(body, prefix, _formData = new FormData()) {
	Object.entries(body).forEach(([key, value]) => {
		_formData.append(`${prefix}${key}`, value);
	});

	return _formData;
}
