/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import SegmentsExperimentsContext from '../../context.es';
import {
	addVariant,
	reviewVariants,
	updateVariant,
	updateVariants
} from '../../state/actions.es';
import {DispatchContext, StateContext} from '../../state/context.es';
import {navigateToExperience} from '../../util/navigation.es';
import {
	STATUS_FINISHED_NO_WINNER,
	STATUS_FINISHED_WINNER
} from '../../util/statuses.es';
import {openErrorToast, openSuccessToast} from '../../util/toasts.es';
import VariantForm from './internal/VariantForm.es';
import VariantList from './internal/VariantList.es';

function Variants({onVariantPublish, selectedSegmentsExperienceId}) {
	const dispatch = useContext(DispatchContext);
	const {errors, experiment, variants} = useContext(StateContext);
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
				<ClayIcon
					className="ml-1 reference-mark text-warning"
					symbol="asterisk"
				/>
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
					{errors.variantsError && (
						<div className="font-weight-bold mb-3 text-danger">
							<ClayIcon
								className="mr-2"
								symbol="exclamation-full"
							/>
							{Liferay.Language.get(
								'a-variant-needs-to-be-created'
							)}
						</div>
					)}
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
				onVariantPublish={onVariantPublish}
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
					if (variant.segmentsExperimentRelId !== variantId) {
						return true;
					}

					variantExperienceId = variant.segmentsExperienceId;

					return false;
				});

				if (variantExperienceId === selectedSegmentsExperienceId) {
					navigateToExperience(experiment.segmentsExperienceId);
				}
				else {
					dispatch(updateVariants(newVariants));
					dispatch(reviewVariants());
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
	onVariantPublish: PropTypes.func.isRequired,
	selectedSegmentsExperienceId: PropTypes.string.isRequired
};

export default Variants;
