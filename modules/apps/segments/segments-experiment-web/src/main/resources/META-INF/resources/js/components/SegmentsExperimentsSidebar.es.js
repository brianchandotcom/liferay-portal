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
	SegmentsVariantType
} from '../types.es';
import SegmentsExperimentsContext from '../context.es';
import UnsupportedSegmentsExperiments from './UnsupportedSegmentsExperiments.es';
import {navigateToExperience} from '../util/navigation.es';
import {STATUS_RUNNING} from '../util/statuses.es';

function SegmentsExperimentsSidebar({
	initialGoals,
	initialSegmentsExperiences,
	initialSegmentsVariants,
	initialSegmentsExperiment,
	initialSelectedSegmentsExperienceId = '0'
}) {
	const {APIService, page} = useContext(SegmentsExperimentsContext);

	const [creationModal, setCreationModal] = useState({active: false});
	const [editionModal, setEditionModal] = useState({active: false});
	const [segmentsExperiment, setSegmentsExperiment] = useState(
		initialSegmentsExperiment
	);
	const [variants, setVariants] = useState(initialSegmentsVariants);

	return page.type === 'content' ? (
		<div className="p-3">
			<SegmentsExperiments
				onCreateSegmentsExperiment={_handleCreateSegmentsExperiment}
				onDeleteSegmentsExperiment={_handleDeleteSegmentsExperiment}
				onEditSegmentsExperiment={_handleEditSegmentsExperiment}
				onEditSegmentsExperimentStatus={
					_handleEditSegmentExperimentStatus
				}
				onRunExperiment={_handleRunExperiment}
				onSelectSegmentsExperienceChange={
					_handleSelectSegmentsExperience
				}
				onTargetChange={_handleTargetChange}
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
					goals={initialGoals}
					handleClose={_handleModalClose}
					name={creationModal.name}
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
					goal={editionModal.goal.value}
					goals={initialGoals}
					handleClose={_handleEditModalClose}
					name={editionModal.name}
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

	function _handleDeleteSegmentsExperiment() {
		const body = {
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId
		};

		APIService.deleteExperiment(body).then(() => {
			navigateToExperience(initialSelectedSegmentsExperienceId);
		});
	}

	function _handleExperimentCreation(experimentData) {
		const {
			description,
			goal,
			goalTarget,
			name,
			segmentsExperienceId
		} = experimentData;

		const body = {
			classNameId: page.classNameId,
			classPK: page.classPK,
			description,
			goal,
			goalTarget,
			name,
			segmentsExperienceId
		};

		APIService.createExperiment(body)
			.then(function _successCallback(objectResponse) {
				const {
					segmentsExperiment,
					segmentsExperimentRel
				} = objectResponse;

				const {
					confidenceLevel,
					description,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status
				} = segmentsExperiment;

				setVariants([{...segmentsExperimentRel, control: true}]);

				setCreationModal({
					active: false
				});

				setSegmentsExperiment({
					confidenceLevel,
					description,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status
				});
			})
			.catch(function _errorCallback() {
				setCreationModal({
					active: true,
					description: experimentData.description,
					editable: experimentData.editable,
					error: Liferay.Language.get('create-test-error'),
					name: experimentData.name,
					segmentsEntryName: experimentData.segmentsEntryName,
					segmentsExperienceId: experimentData.segmentsExperienceId
				});
			});
	}

	function _handleRunExperiment({splitVariantsMap, confidenceLevel}) {
		const body = {
			confidenceLevel,
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			segmentsExperimentRels: JSON.stringify(splitVariantsMap),
			status: STATUS_RUNNING
		};

		return APIService.runExperiment(body).then(function(response) {
			const {segmentsExperiment} = response;
			const updatedVariants = variants.map(variant => ({
				...variant,
				split: splitVariantsMap[variant.segmentsExperimentRelId]
			}));

			setSegmentsExperiment(segmentsExperiment);
			setVariants(updatedVariants);
		});
	}

	function _handleEditSegmentExperimentStatus(segmentsExperiment, status) {
		const body = {
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status
		};

		APIService.editExperimentStatus(body)
			.then(function _successCallback(objectResponse) {
				const {
					confidenceLevel,
					description,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status
				} = objectResponse.segmentsExperiment;

				setSegmentsExperiment({
					confidenceLevel,
					description,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status
				});
			})
			.catch(function _errorCallback() {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});
	}

	function _handleEditSegmentsExperiment() {
		setEditionModal({
			active: true,
			description: segmentsExperiment.description,
			editable: segmentsExperiment.editable,
			goal: segmentsExperiment.goal,
			name: segmentsExperiment.name,
			segmentsEntryName: segmentsExperiment.segmentsEntryName,
			segmentsExperienceId: segmentsExperiment.segmentsExperienceId,
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status: segmentsExperiment.status
		});
	}

	function _handleExperimentEdition(experimentData) {
		const {
			description,
			goal,
			goalTarget,
			name,
			segmentsExperimentId
		} = experimentData;

		const body = {
			description,
			goal,
			goalTarget,
			name,
			segmentsExperimentId
		};

		APIService.editExperiment(body)
			.then(function _successCallback(objectResponse) {
				const {
					confidenceLevel,
					description,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status
				} = objectResponse.segmentsExperiment;

				setEditionModal({
					active: false
				});

				setSegmentsExperiment({
					confidenceLevel,
					description,
					editable,
					goal,
					name,
					segmentsEntryName,
					segmentsExperienceId,
					segmentsExperimentId,
					status
				});
			})
			.catch(function _errorCallback() {
				setEditionModal({
					active: true,
					description: experimentData.description,
					editable: experimentData.editable,
					error: Liferay.Language.get('edit-test-error'),
					name: experimentData.name,
					segmentsEntryName: experimentData.segmentsEntryName,
					segmentsExperienceId: experimentData.segmentsExperienceId,
					segmentsExperimentId: experimentData.segmentsExperimentId,
					status: experimentData.status
				});
			});
	}

	function _handleSelectSegmentsExperience(segmentsExperienceId) {
		navigateToExperience(segmentsExperienceId);
	}

	function _handleTargetChange(selector) {
		const body = {
			description: segmentsExperiment.description,
			goal: segmentsExperiment.goal.value,
			goalTarget: selector,
			name: segmentsExperiment.name,
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId
		};

		APIService.editExperiment(body).then(() => {
			setSegmentsExperiment({
				...segmentsExperiment,
				goal: {...segmentsExperiment.goal, target: selector}
			});
		});
	}

	function _handleVariantCreation(name) {
		return new Promise((resolve, reject) => {
			const body = {
				classNameId: page.classNameId,
				classPK: page.classPK,
				name,
				segmentsExperimentId: segmentsExperiment.segmentsExperimentId
			};

			APIService.createVariant(body)
				.then(({segmentsExperimentRel}) => {
					const {
						name,
						segmentsExperienceId,
						segmentsExperimentId,
						segmentsExperimentRelId,
						split
					} = segmentsExperimentRel;

					setVariants([
						...variants,
						{
							control: false,
							name,
							segmentsExperienceId,
							segmentsExperimentId,
							segmentsExperimentRelId,
							split
						}
					]);
					resolve();
				})
				.catch(error => reject(error));
		});
	}

	function _handleVariantDeletion(variantId) {
		const body = {
			classNameId: page.classNameId,
			classPK: page.classPK,
			segmentsExperimentRelId: variantId
		};

		APIService.deleteVariant(body).then(() => {
			let variantExperienceId = null;

			const newVariants = variants.filter(variant => {
				if (variant.segmentsExperimentRelId !== variantId) return true;

				variantExperienceId = variant.segmentsExperienceId;
				return false;
			});

			if (variantExperienceId === initialSelectedSegmentsExperienceId) {
				navigateToExperience(initialSelectedSegmentsExperienceId);
			} else {
				setVariants(newVariants);
			}
		});
	}

	function _handleVariantEdition({name, variantId}) {
		return new Promise((resolve, reject) => {
			const body = {
				classNameId: page.classNameId,
				classPK: page.classPK,
				name,
				segmentsExperimentRelId: variantId
			};

			APIService.editVariant(body)
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
	initialSegmentsVariants: PropTypes.arrayOf(SegmentsVariantType).isRequired,
	initialSelectedSegmentsExperienceId: PropTypes.string
};

export default SegmentsExperimentsSidebar;
