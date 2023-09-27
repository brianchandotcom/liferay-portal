/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	updateVariants,
} from '../../state/actions.es';
import {DispatchContext, StateContext} from '../../state/context.es';
import {navigateToExperience} from '../../util/navigation.es';
import {STATUS_DRAFT} from '../../util/statuses.es';
import {openErrorToast, openSuccessToast} from '../../util/toasts.es';
import VariantForm from './internal/VariantForm.es';
import VariantTable from './internal/VariantTable.es';

function Variants({selectedSegmentsExperienceId}) {
	const dispatch = useContext(DispatchContext);
	const {errors, experiment, variants} = useContext(StateContext);
	const {APIService, page} = useContext(SegmentsExperimentsContext);
	const [creatingVariant, setCreatingVariant] = useState(false);

	const {
		observer: creatingVariantObserver,
		onClose: creatingVariantOnClose,
	} = useModal({
		onClose: () => setCreatingVariant(false),
	});

	const [editingVariant, setEditingVariant] = useState({active: false});

	const {
		observer: editingVariantObserver,
		onClose: editingVariantOnClose,
	} = useModal({
		onClose: () => setEditingVariant({active: false}),
	});

	return (
		<>
			<h4 className="mb-3 mt-4 sheet-subtitle">
				{Liferay.Language.get('variants')}

				{experiment.status.value === STATUS_DRAFT && (
					<ClayIcon
						className="lexicon-icon-sm ml-1 reference-mark text-warning"
						style={{verticalAlign: 'super'}}
						symbol="asterisk"
					/>
				)}
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
						<div className="font-weight-semi-bold mb-3 text-danger">
							<ClayIcon
								className="mr-2"
								symbol="exclamation-full"
							/>

							{Liferay.Language.get(
								'a-variant-needs-to-be-created'
							)}
						</div>
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
				</>
			)}

			<VariantTable
				experiment={experiment}
				onVariantDeletion={_handleVariantDeletion}
				onVariantEdition={_handleVariantEdition}
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
			plid: page.plid,
			segmentsExperimentRelId: variantId,
		};

		return APIService.deleteVariant(body)
			.then(() => {
				openSuccessToast();

				let variantExperienceId = null;

				const newVariants = variants.filter((variant) => {
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
			.catch((_error) => {
				openErrorToast();
			});
	}

	function _handleVariantEdition({name, variantId}) {
		setEditingVariant({
			active: true,
			name,
			variantId,
		});
	}

	function _handleVariantEditionSave({name, variantId}) {
		const body = {
			name,
			plid: page.plid,
			segmentsExperimentRelId: variantId,
		};

		return APIService.editVariant(body).then(({segmentsExperimentRel}) => {
			openSuccessToast();

			dispatch(
				updateVariant({
					changes: {
						name: segmentsExperimentRel.name,
					},
					variantId,
				})
			);
		});
	}

	function _handleVariantCreation({name}) {
		const body = {
			name,
			plid: page.plid,
			segmentsExperimentId: experiment.segmentsExperimentId,
		};

		return APIService.createVariant(body)
			.then(({segmentsExperimentRel}) => {
				const {
					name,
					segmentsExperienceId,
					segmentsExperimentId,
					segmentsExperimentRelId,
					split,
				} = segmentsExperimentRel;

				openSuccessToast();

				dispatch(
					addVariant({
						control: false,
						name,
						segmentsExperienceId,
						segmentsExperimentId,
						segmentsExperimentRelId,
						split,
					})
				);
			})
			.catch((_error) => {
				openErrorToast();
			});
	}
}

Variants.propTypes = {
	onVariantPublish: PropTypes.func.isRequired,
	selectedSegmentsExperienceId: PropTypes.string.isRequired,
};

export default Variants;
