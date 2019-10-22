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
