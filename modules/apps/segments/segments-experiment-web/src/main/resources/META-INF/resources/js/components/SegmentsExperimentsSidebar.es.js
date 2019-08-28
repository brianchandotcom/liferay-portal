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
	InitialSegmentsVariantType,
	SegmentsExperienceType,
	SegmentsExperimentGoal,
	SegmentsExperimentType
} from '../types.es';
import SegmentsExperimentsContext from '../context.es';
import UnsupportedSegmentsExperiments from './UnsupportedSegmentsExperiments.es';
import {navigateToExperience} from '../util/navigation.es';

function SegmentsExperimentsSidebar({
	initialGoals,
	initialSegmentsExperiences,
	initialSegmentsVariants,
	initialSegmentsExperiment,
	initialSelectedSegmentsExperienceId = '0'
}) {
	const {segmentsExperimentsUtil, page} = useContext(
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
				onEditSegmentsExperimentStatus={
					_handleEditSegmentExperimentStatus
				}
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

	function _handleExperimentCreation(segmentsExperiment) {
		const body = {
			classNameId: page.classNameId,
			classPK: page.classPK,
			description: segmentsExperiment.description,
			goal: segmentsExperiment.goal,
			goalTarget: segmentsExperiment.goalTarget,
			name: segmentsExperiment.name,
			segmentsExperienceId: segmentsExperiment.segmentsExperienceId
		};

		segmentsExperimentsUtil
			.createExperiment(body)
			.then(function _successCallback(objectResponse) {
				const {
					segmentsExperiment,
					segmentsExperimentRel
				} = objectResponse;

				const {
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
					description: segmentsExperiment.description,
					editable: segmentsExperiment.editable,
					error: Liferay.Language.get('create-test-error'),
					name: segmentsExperiment.name,
					segmentsEntryName: segmentsExperiment.segmentsEntryName,
					segmentsExperienceId:
						segmentsExperiment.segmentsExperienceId
				});
			});
	}

	function _handleEditSegmentExperimentStatus(segmentsExperiment, status) {
		const body = {
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId,
			status
		};

		segmentsExperimentsUtil
			.editExperimentStatus(body)
			.then(function _successCallback(objectResponse) {
				const {
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

	function _handleExperimentEdition(segmentsExperiment) {
		const body = {
			description: segmentsExperiment.description,
			goal: segmentsExperiment.goal,
			goalTarget: segmentsExperiment.goalTarget,
			name: segmentsExperiment.name,
			segmentsExperimentId: segmentsExperiment.segmentsExperimentId
		};

		segmentsExperimentsUtil
			.editExperiment(body)
			.then(function _successCallback(objectResponse) {
				const {
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
					description: segmentsExperiment.description,
					editable: segmentsExperiment.editable,
					error: Liferay.Language.get('edit-test-error'),
					name: segmentsExperiment.name,
					segmentsEntryName: segmentsExperiment.segmentsEntryName,
					segmentsExperienceId:
						segmentsExperiment.segmentsExperienceId,
					segmentsExperimentId:
						segmentsExperiment.segmentsExperimentId,
					status: segmentsExperiment.status
				});
			});
	}

	function _handleSelectSegmentsExperience(segmentsExperienceId) {
		navigateToExperience(segmentsExperienceId);
	}

	function _handleVariantCreation(name) {
		return new Promise((resolve, reject) => {
			const body = {
				classNameId: page.classNameId,
				classPK: page.classPK,
				name,
				segmentsExperimentId: segmentsExperiment.segmentsExperimentId
			};

			segmentsExperimentsUtil
				.createVariant(body)
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
			classNameId: page.classNameId,
			classPK: page.classPK,
			segmentsExperimentRelId: variantId
		};

		segmentsExperimentsUtil.deleteVariant(body).then(() => {
			let variantExperienceId = null;

			const newVariants = variants.filter(variant => {
				if (variant.segmentsExperimentRelId !== variantId) return true;

				variantExperienceId = variant.segmentsExperienceId;
				return false;
			});

			if (variantExperienceId === initialSelectedSegmentsExperienceId) {
				navigateToExperience(
					initialSegmentsExperiment.segmentsExperienceId
				);
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

			segmentsExperimentsUtil
				.editVariant(body)
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
	initialSegmentsVariants: PropTypes.arrayOf(InitialSegmentsVariantType)
		.isRequired,
	initialSelectedSegmentsExperienceId: PropTypes.string
};

export default SegmentsExperimentsSidebar;
