/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			{variants.map((variant) => (
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
					publishable={publishable}
					segmentsExperienceId={variant.segmentsExperienceId}
					showSplit={!publishable && !editable}
					split={variant.split}
					variantId={variant.segmentsExperimentRelId}
					winner={variant.winner}
				/>
			))}
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
