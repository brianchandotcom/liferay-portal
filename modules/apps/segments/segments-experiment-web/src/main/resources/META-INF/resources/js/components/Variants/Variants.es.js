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

import React, {useState} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {SegmentsVariantType} from '../../types.es';
import VariantList from './internal/VariantList.es';
import VariantForm from './internal/VariantForm.es';

function Variants({
	editable,
	onVariantCreation,
	onVariantDeletion,
	onVariantEdition,
	selectedSegmentsExperienceId,
	variants
}) {
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

	return (
		<>
			<h4 className="mb-3 mt-4 sheet-subtitle">
				{Liferay.Language.get('variants')}
			</h4>

			{variants.length === 1 && (
				<>
					<p className="mb-2 small">
						<b>
							{Liferay.Language.get(
								'no-variants-have-been-created-for-this-test'
							)}
						</b>
					</p>

					<p className="mb-2 text-secondary small">
						{Liferay.Language.get('variants-help')}
					</p>
				</>
			)}

			{editable && (
				<ClayButton
					className="mb-3"
					data-testid="create-variant"
					displayType="secondary"
					onClick={() => setCreatingVariant(!creatingVariant)}
					small
				>
					{Liferay.Language.get('create-variant')}
				</ClayButton>
			)}

			<VariantList
				editable={editable}
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
		onVariantDeletion(variantId);
	}

	function _handleVariantEdition({name, variantId}) {
		setEditingVariant({
			active: true,
			name,
			variantId
		});
	}

	function _handleVariantEditionSave({name, variantId}) {
		return onVariantEdition({name, variantId});
	}

	function _handleVariantCreation({name}) {
		return onVariantCreation(name);
	}
}

Variants.propTypes = {
	editable: PropTypes.bool.isRequired,
	onVariantCreation: PropTypes.func.isRequired,
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	selectedSegmentsExperienceId: PropTypes.string.isRequired,
	variants: PropTypes.arrayOf(SegmentsVariantType)
};

export default Variants;
