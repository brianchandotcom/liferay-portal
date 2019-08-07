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
	SegmentsExperimentGoal,
	SegmentsExperimentType,
	initialSegmentsVariantType
} from '../types.es';
import SegmentsExperimentsContext from '../context.es';
import UnsupportedSegmentsExperiments from './UnsupportedSegmentsExperiments.es';

function SegmentsExperimentsSidebar({
	initialGoals,
	initialSegmentsExperiences,
	initialSegmentsVariants,
	initialSegmentsExperiment,
	initialSelectedSegmentsExperienceId = '0'
}) {
	const {endpoints, page, contentPageEditorNamespace, namespace} = useContext(
		SegmentsExperimentsContext
	);
	const [creationModal, setCreationModal] = useState({active: false});
	const [editionModal, setEditionModal] = useState({active: false});
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
				onVariantDeletion={_handleVariantDeletion}
				onVariantEdition={_handleVariantEdition}
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
					goal={initialGoals[0].value}
					goals={initialGoals}
					name={creationModal.name}
					onClose={_handleModalClose}
					onSave={_handleExperimentCreation}
					segmentsExperienceId={creationModal.segmentsExperienceId}
					title={Liferay.Language.get('create-new-test')}
				/>
			)}
			{editionModal.active && (
				<SegmentsExperimentsModal
					active={editionModal.active}
					description={editionModal.description}
					error={editionModal.error}
					goal={editionModal.goal}
					goals={initialGoals}
					name={editionModal.name}
					onClose={_handleEditModalClose}
					onSave={_handleExperimentEdition}
					segmentsExperienceId={editionModal.segmentsExperienceId}
					segmentsExperimentId={editionModal.segmentsExperimentId}
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
		const body = {
			classPK: page.classPK,
			classNameId: page.classNameId,
			description: segmentsExperiment.description,
			goal: segmentsExperiment.goal,
			goalTarget: '',
			name: segmentsExperiment.name,
			segmentsExperienceId: segmentsExperiment.segmentsExperienceId
		};

		fetch(endpoints.createSegmentsExperimentURL, {
			body: getFormData(body, namespace),
			credentials: 'include',
			method: 'POST'
		})
			.then(response => response.json())
			.then(objectResponse => {
				if (objectResponse.error) throw objectResponse.error;
				return objectResponse;
			})
			.then(function _successCallback(objectResponse) {
				const {
					segmentsExperiment,
					segmentsExperimentRel
				} = objectResponse;

				setVariants([{...segmentsExperimentRel, control: true}]);

				setCreationModal({
					active: false
				});

				setSegmentsExperiment({
					description: segmentsExperiment.description,
					name: segmentsExperiment.name,
					segmentsExperienceId:
						segmentsExperiment.segmentsExperienceId,
					segmentsExperimentId:
						segmentsExperiment.segmentsExperimentId,
					goal: segmentsExperiment.goal,
					goalTarget: segmentsExperiment.goalTarget
				});
			})
			.catch(function _errorCallback() {
				setCreationModal({
					active: true,
					description: segmentsExperiment.description,
					error: Liferay.Language.get('create-test-error'),
					name: segmentsExperiment.name,
					segmentsExperienceId:
						segmentsExperiment.segmentsExperienceId
				});
			});
	}

	function _handleEditSegmentsExperiment() {
		setEditionModal({
			active: true,
			description: segmentsExperiment.description,
			goal: segmentsExperiment.goal,
			goalTarget: '',
			name: segmentsExperiment.name,
			segmentsExperienceId: segmentsExperiment.segmentsExperienceId,
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId
		});
	}

	function _handleExperimentEdition(segmentsExperiment) {
		Liferay.Service(
			endpoints.editSegmentsExperimentURL,
			{
				description: segmentsExperiment.description,
				goal: segmentsExperiment.goal,
				goalTarget: '',
				name: segmentsExperiment.name,
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
					description: segmentsExperiment.description,
					error: Liferay.Language.get('edit-test-error'),
					name: segmentsExperiment.name,
					segmentsExperienceId:
						segmentsExperiment.segmentsExperienceId,
					segmentsExperimentId:
						segmentsExperiment.segmentsExperimentId
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
				classPK: page.classPK,
				classNameId: page.classNameId,
				name,
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
							control: false,
							name,
							segmentsExperienceId,
							segmentsExperimentId,
							segmentsExperimentRelId
						}
					]);
					resolve();
				})
				.catch(error => reject(error));
		});
	}

	function _handleVariantDeletion(variantId) {
		const body = {
			classPK: page.classPK,
			classNameId: page.classNameId,
			segmentsExperimentRelId: variantId
		};

		fetch(endpoints.deleteSegmentsVariantURL, {
			body: getFormData(body, namespace),
			credentials: 'include',
			method: 'POST'
		})
			.then(response => response.json())
			.then(response => {
				if (response.error) throw response.error;
				return response;
			})
			.then(() => {
				setVariants(
					variants.filter(
						variant => variant.segmentsExperimentRelId !== variantId
					)
				);
			});
	}

	function _handleVariantEdition({name, variantId}) {
		return new Promise((resolve, reject) => {
			const body = {
				classPK: page.classPK,
				classNameId: page.classNameId,
				name,
				segmentsExperimentRelId: variantId
			};

			fetch(endpoints.editSegmentsVariantURL, {
				body: getFormData(body, namespace),
				credentials: 'include',
				method: 'POST'
			})
				.then(response => response.json())
				.then(response => {
					if (response.error) throw response.error;
					return response;
				})
				.then(({segmentsExperimentRel}) => {
					setVariants(
						variants.map(variant => {
							if (
								segmentsExperimentRel.segmentsExperimentRelId ===
								variant.segmentsExperimentRelId
							) {
								return {
									...variant,
									name: segmentsExperimentRel.name
								};
							}
							return variant;
						})
					);
					resolve();
				})
				.catch(() => {
					reject();
				});
		});
	}
}

SegmentsExperimentsSidebar.propTypes = {
	initialGoals: PropTypes.arrayOf(SegmentsExperimentGoal),
	initialSegmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	initialSegmentsExperiment: SegmentsExperimentType,
	initialSegmentsVariants: PropTypes.arrayOf(initialSegmentsVariantType)
		.isRequired,
	initialSelectedSegmentsExperienceId: PropTypes.string
};

export default SegmentsExperimentsSidebar;

function getFormData(body, prefix, _formData = new FormData()) {
	Object.entries(body).forEach(([key, value]) => {
		_formData.append(`${prefix}${key}`, value);
	});

	return _formData;
}
