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

import React from 'react';
import PropTypes from 'prop-types';
import ClayList from '@clayui/list';
import Variant from './Variant.es';
import {SegmentsVariantType} from '../../../types.es';

function VariantList({
	editable,
	onVariantDeletion,
	onVariantEdition,
	onVariantPublish,
	publishable,
	selectedSegmentsExperienceId,
	variants
}) {
	return (
		<ClayList>
			{variants.map(variant => {
				const publishableVariant =
					publishable && !!(!variant.control || variant.winner);

				return (
					<Variant
						active={
							variant.segmentsExperienceId ===
							selectedSegmentsExperienceId
						}
						control={variant.control}
						editable={editable}
						key={variant.segmentsExperimentRelId}
						name={variant.name}
						onVariantDeletion={onVariantDeletion}
						onVariantEdition={onVariantEdition}
						onVariantPublish={onVariantPublish}
						publishable={publishableVariant}
						segmentsExperienceId={variant.segmentsExperienceId}
						showSplit={!publishable && !editable}
						split={variant.split}
						variantId={variant.segmentsExperimentRelId}
						winner={variant.winner}
					/>
				);
			})}
		</ClayList>
	);
}

VariantList.propTypes = {
	editable: PropTypes.bool.isRequired,
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	publishable: PropTypes.bool.isRequired,
	variants: PropTypes.arrayOf(SegmentsVariantType)
};

export default VariantList;
