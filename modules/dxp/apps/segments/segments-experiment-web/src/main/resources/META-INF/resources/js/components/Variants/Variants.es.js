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

import React, {useContext, useState} from 'react';
import PropTypes from 'prop-types';
import {openErrorToast, openSuccessToast} from '../../util/toasts.es';
import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import VariantList from './internal/VariantList.es';
import VariantForm from './internal/VariantForm.es';
import {
	addVariant,
	updateVariant,
	updateVariants,
	archiveExperiment
} from '../../state/actions.es';
import {DispatchContext, StateContext} from '../../state/context.es';
import SegmentsExperimentsContext from '../../context.es';
import {navigateToExperience} from '../../util/navigation.es';
import {
	STATUS_COMPLETED,
	STATUS_FINISHED_NO_WINNER,
	STATUS_FINISHED_WINNER
} from '../../util/statuses.es';

function Variants({selectedSegmentsExperienceId}) {
	const dispatch = useContext(DispatchContext);
	const {experiment, variants} = useContext(StateContext);
	const {APIService, page} = useContext(SegmentsExperimentsContext);

	const {
		observer: creatingVariantObserver,
		onClose: creatingVariantOnClose
	} = useModal({
		onClose: () => setCreatingVariant(false)
	});
	const {
		observer: editingVariantObserver,
		onClose: editingVariantOnClose
	} = useModal({
		onClose: () => setEditingVariant({active: false})
	});
	const [creatingVariant, setCreatingVariant] = useState(false);
	const [editingVariant, setEditingVariant] = useState({active: false});

	const publishable =
		experiment.status.value === STATUS_FINISHED_WINNER ||
		experiment.status.value === STATUS_FINISHED_NO_WINNER;

	return (
		<>
			<h4 className="mb-3 mt-4 sheet-subtitle">
				{Liferay.Language.get('variants')}
			</h4>

			{variants.length === 1 && (
				<>
					<p className="mb-2">
						<b>
							{Liferay.Language.get(
								'no-variants-have-been-created-for-this-test'
							)}
						</b>
					</p>

					<p className="mb-2 text-secondary">
						{Liferay.Language.get('variants-help')}
					</p>
				</>
			)}

			{experiment.editable && (
				<ClayButton
					className="mb-3"
					data-testid="create-variant"
					displayType="secondary"
					onClick={() => setCreatingVariant(!creatingVariant)}
				>
					{Liferay.Language.get('create-variant')}
				</ClayButton>
			)}

			<VariantList
				editable={experiment.editable}
				onVariantDeletion={_handleVariantDeletion}
				onVariantEdition={_handleVariantEdition}
				onVariantPublish={_handlePublishVariant}
				publishable={publishable}
				selectedSegmentsExperienceId={selectedSegmentsExperienceId}
				variants={variants}
			/>

			{creatingVariant && (
				<ClayModal observer={creatingVariantObserver} size="sm">
					<VariantForm
						errorMessage={Liferay.Language.get(
							'create-variant-error-message'
						)}
						onClose={creatingVariantOnClose}
						onSave={_handleVariantCreation}
						title={Liferay.Language.get('create-new-variant')}
					/>
				</ClayModal>
			)}

			{editingVariant.active && (
				<ClayModal observer={editingVariantObserver} size="sm">
					<VariantForm
						errorMessage={Liferay.Language.get(
							'edit-variant-error-message'
						)}
						name={editingVariant.name}
						onClose={editingVariantOnClose}
						onSave={_handleVariantEditionSave}
						title={Liferay.Language.get('edit-variant')}
						variantId={editingVariant.variantId}
					/>
				</ClayModal>
			)}
		</>
	);

	function _handleVariantDeletion(variantId) {
		const body = {
			classNameId: page.classNameId,
			classPK: page.classPK,
			segmentsExperimentRelId: variantId
		};

		return APIService.deleteVariant(body)
			.then(() => {
				openSuccessToast();

				let variantExperienceId = null;

				const newVariants = variants.filter(variant => {
					if (variant.segmentsExperimentRelId !== variantId)
						return true;

					variantExperienceId = variant.segmentsExperienceId;
					return false;
				});

				if (variantExperienceId === selectedSegmentsExperienceId) {
					navigateToExperience(experiment.segmentsExperienceId);
				} else {
					dispatch(updateVariants(newVariants));
				}
			})
			.catch(_error => {
				openErrorToast();
			});
	}

	function _handleVariantEdition({name, variantId}) {
		setEditingVariant({
			active: true,
			name,
			variantId
		});
	}

	function _handleVariantEditionSave({name, variantId}) {
		const body = {
			classNameId: page.classNameId,
			classPK: page.classPK,
			name,
			segmentsExperimentRelId: variantId
		};

		return APIService.editVariant(body).then(({segmentsExperimentRel}) => {
			openSuccessToast();

			dispatch(
				updateVariant({
					changes: {
						name: segmentsExperimentRel.name
					},
					variantId
				})
			);
		});
	}

	function _handlePublishVariant(experienceId) {
		const body = {
			segmentsExperimentId: experiment.segmentsExperimentId,
			status: STATUS_COMPLETED,
			winnerSegmentsExperienceId: experienceId
		};

		const confirmed = confirm(
			Liferay.Language.get(
				'are-you-sure-you-want-to-publish-this-variant'
			)
		);

		if (confirmed) {
			APIService.publishExperience(body)
				.then(({segmentsExperiment}) => {
					openSuccessToast();

					dispatch(
						archiveExperiment({
							status: segmentsExperiment.status
						})
					);
				})
				.catch(_error => {
					openErrorToast();
				});
		}
	}

	function _handleVariantCreation({name}) {
		const body = {
			classNameId: page.classNameId,
			classPK: page.classPK,
			name,
			segmentsExperimentId: experiment.segmentsExperimentId
		};

		return APIService.createVariant(body)
			.then(({segmentsExperimentRel}) => {
				const {
					name,
					segmentsExperienceId,
					segmentsExperimentId,
					segmentsExperimentRelId,
					split
				} = segmentsExperimentRel;

				openSuccessToast();

				dispatch(
					addVariant({
						control: false,
						name,
						segmentsExperienceId,
						segmentsExperimentId,
						segmentsExperimentRelId,
						split
					})
				);
			})
			.catch(_error => {
				openErrorToast();
			});
	}
}

Variants.propTypes = {
	selectedSegmentsExperienceId: PropTypes.string.isRequired
};

export default Variants;
