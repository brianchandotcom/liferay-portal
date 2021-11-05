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
import {ClayTooltipProvider} from '@clayui/tooltip';
import PropTypes from 'prop-types';
import React from 'react';

import EmptyState from './EmptyState';
import SortableList from './SortableList';

const VariationsNav = ({
	assetEntryListSegmentsEntryRels,
	availableSegmentsEntries,
	createNewSegmentURL,
	openSelectSegmentsEntryDialogMethod,
	validAssetListEntry,
}) => {
	const showVariationsList = assetEntryListSegmentsEntryRels.length > 1;
	const showAddVariationButton =
		showVariationsList && availableSegmentsEntries && validAssetListEntry;

	const handleAddVariation = () => {
		const callback = window[openSelectSegmentsEntryDialogMethod];

		if (typeof callback !== 'function') {
			return;
		}

		callback();
	};

	return (
		<>
			<div className="align-items-center d-flex justify-content-between">
				<p className="font-weight-semi-bold h5 mb-0 text-uppercase">
					{Liferay.Language.get('personalized-variations')}
				</p>
				{showAddVariationButton && (
					<ClayTooltipProvider>
						<ClayButtonWithIcon
							data-tooltip-align="top"
							displayType="secondary"
							onClick={handleAddVariation}
							small
							symbol="plus"
							title="Add new variation"
						/>
					</ClayTooltipProvider>
				)}
			</div>

			{!showVariationsList && (
				<EmptyState
					actionButtonUrl={createNewSegmentURL}
					availableSegments={availableSegmentsEntries}
					disableActionButton={
						availableSegmentsEntries && !validAssetListEntry
					}
					selectSegmentCallback={handleAddVariation}
				/>
			)}

			{showVariationsList && (
				<SortableList items={assetEntryListSegmentsEntryRels} />
			)}
		</>
	);
};

VariationsNav.propTypes = {
	assetEntryListSegmentsEntryRels: PropTypes.array.isRequired,
	availableSegmentsEntries: PropTypes.bool.isRequired,
	createNewSegmentURL: PropTypes.string.isRequired,
	openSelectSegmentsEntryDialogMethod: PropTypes.string.isRequired,
	validAssetListEntry: PropTypes.bool.isRequired,
};

export default VariationsNav;
