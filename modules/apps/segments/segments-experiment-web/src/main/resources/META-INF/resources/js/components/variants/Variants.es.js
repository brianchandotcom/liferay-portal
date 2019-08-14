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
import ClayModal from '@clayui/modal';
import {segmentsVariantType} from '../../types.es';
import VariantList from './internal/VariantList.es';
import VariantForm from './internal/VariantForm.es';

function Variants({
	onVariantCreation,
	onVariantDeletion,
	onVariantEdition,
	selectedSegmentsExperienceId,
	variants
}) {
	const [creatingVariant, setCreatingVariant] = useState(false);
	const [editingVariant, setEditingVariant] = useState({active: false});

	return (
		<div className="mt-3">
			<h4 className="sheet-subtitle">
				{Liferay.Language.get('variants')}
			</h4>

			<VariantList
				onVariantDeletion={_handleVariantDeletion}
				onVariantEdition={_handleVariantEdition}
				selectedSegmentsExperienceId={selectedSegmentsExperienceId}
				variants={variants}
			/>

			{creatingVariant && (
				<ClayModal onClose={() => setCreatingVariant(false)} size="sm">
					{onClose => (
						<VariantForm
							errorMessage={Liferay.Language.get(
								'create-variant-error-message'
							)}
							onClose={onClose}
							onSave={_handleVariantCreation}
							title={Liferay.Language.get('create-new-variant')}
						/>
					)}
				</ClayModal>
			)}

			{editingVariant.active && (
				<ClayModal
					onClose={() => setEditingVariant({active: false})}
					size="sm"
				>
					{onClose => (
						<VariantForm
							errorMessage={Liferay.Language.get(
								'edit-variant-error-message'
							)}
							name={editingVariant.name}
							onClose={onClose}
							onSave={_handleVariantEditionSave}
							title={Liferay.Language.get('edit-variant')}
							variantId={editingVariant.variantId}
						/>
					)}
				</ClayModal>
			)}

			<ClayButton
				className="my-2"
				displayType="secondary"
				onClick={() => setCreatingVariant(!creatingVariant)}
				small
			>
				{Liferay.Language.get('create-variant')}
			</ClayButton>
		</div>
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
	onVariantCreation: PropTypes.func.isRequired,
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	selectedSegmentsExperienceId: PropTypes.string.isRequired,
	variants: PropTypes.arrayOf(segmentsVariantType)
};

export default Variants;
