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

import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React from 'react';

import {SegmentsVariantType} from '../../../types.es';
import Variant from './Variant.es';

function VariantList({
	editable,
	onVariantDeletion,
	onVariantEdition,
	onVariantPublish,
	publishable,
	selectedSegmentsExperienceId,
	variants,
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
	variants: PropTypes.arrayOf(SegmentsVariantType),
};

export default VariantList;
