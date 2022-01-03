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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLink from '@clayui/link';
import {ClayTooltipProvider} from '@clayui/tooltip';
import PropTypes from 'prop-types';
import React from 'react';

import SortableList from './SortableList';

const VariationsNav = ({
	assetListEntrySegmentsEntryRels,
	assetListEntryValid,
	openSelectSegmentsEntryDialogMethod,
	portletNamespace,
	segmentsEntriesAvailables,
	updateVariationsPriorityURL,
}) => {
	const handleAddVariation = () => {
		const callback = window[openSelectSegmentsEntryDialogMethod];

		if (typeof callback !== 'function') {
			return;
		}

		callback();
	};

	return (
		<>
			<div className="align-items-center d-flex justify-content-between mb-3">
				<p className="font-weight-semi-bold h5 mb-0 text-uppercase">
					{Liferay.Language.get('personalized-variations')}
				</p>

				<ClayTooltipProvider>
					<ClayButtonWithIcon
						data-tooltip-align="top"
						disabled={
							!segmentsEntriesAvailables || !assetListEntryValid
						}
						displayType="unstyled"
						onClick={handleAddVariation}
						small
						symbol="plus"
						title={Liferay.Language.get('create-variation')}
					/>
				</ClayTooltipProvider>
			</div>

			<p className="mb-3 small text-secondary">
				{Liferay.Language.get(
					'create-personalized-variations-of-the-collections-for-different-segments'
				)}
			</p>

			{!segmentsEntriesAvailables &&
				assetListEntrySegmentsEntryRels.length < 2 && (
					<p className="mb-3 small text-secondary">
						<span>
							{Liferay.Language.get(
								'you-need-segments-to-create-a-personalized-variation'
							)}{' '}
						</span>
					</p>
				)}

			<SortableList
				items={assetListEntrySegmentsEntryRels}
				namespace={portletNamespace}
				savePriorityURL={updateVariationsPriorityURL}
			/>
		</>
	);
};

VariationsNav.propTypes = {
	assetListEntrySegmentsEntryRels: PropTypes.array.isRequired,
	assetListEntryValid: PropTypes.bool.isRequired,
	openSelectSegmentsEntryDialogMethod: PropTypes.string.isRequired,
	segmentsEntriesAvailables: PropTypes.bool.isRequired,
	updateVariationsPriorityURL: PropTypes.string.isRequired,
};

export default VariationsNav;
