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
import ClayTable from '@clayui/table';
import Variant from './Variant.es';
import {segmentsVariantType} from '../../../types.es';

function VariantList({variants}) {
	return (
		<React.Fragment>
			<ClayTable bordered={false}>
				<ClayTable.Body>
					<Variant
						active={true}
						control={true}
						name={Liferay.Language.get('variant-control')}
					/>

					{variants.map(variant => {
						return (
							<Variant
								active={false}
								control={variant.control}
								key={variant.segmentsExperimentRelId}
								name={variant.name}
							/>
						);
					})}
				</ClayTable.Body>
			</ClayTable>
			{variants.length === 1 ? (
				<React.Fragment>
					<h4>{Liferay.Language.get('no-variants-message')}</h4>
					<p className="text-secondary small">
						{Liferay.Language.get('no-variants-tip')}
					</p>
				</React.Fragment>
			) : null}
		</React.Fragment>
	);
}

VariantList.propTypes = {
	variants: PropTypes.arrayOf(segmentsVariantType)
};

export default VariantList;
