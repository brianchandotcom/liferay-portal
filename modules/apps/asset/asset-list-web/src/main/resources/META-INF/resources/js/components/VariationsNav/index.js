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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
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
	const states = {
		createTheFirstSegment:
			segmentsEntriesAvailables &&
			assetListEntrySegmentsEntryRels.length === 1,
		default() {
			return !this.emptyState && !this.createTheFirstSegment;
		},
		defaultWithHeaderButton() {
			return (
				!this.createTheFirstSegment &&
				segmentsEntriesAvailables &&
				assetListEntryValid
			);
		},
		emptyState:
			!segmentsEntriesAvailables &&
			assetListEntrySegmentsEntryRels.length === 1,
	};

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

				{states.defaultWithHeaderButton() && (
					<ClayTooltipProvider>
						<ClayButtonWithIcon
							data-tooltip-align="top"
							displayType="unstyled"
							onClick={handleAddVariation}
							small
							symbol="plus"
							title={Liferay.Language.get('create-variation')}
						/>
					</ClayTooltipProvider>
				)}
			</div>

			{(states.emptyState || states.createTheFirstSegment) && (
				<ClayEmptyState
					description={Liferay.Language.get(
						'no-personalized-variations-were-found'
					)}
					title={Liferay.Language.get(
						'no-personalized-variations-yet'
					)}
				>
					{states.createTheFirstSegment && (
						<ClayButton
							displayType="primary"
							onClick={handleAddVariation}
							small
						>
							{Liferay.Language.get('add-personalized-variation')}
						</ClayButton>
					)}
				</ClayEmptyState>
			)}

			{states.default() && (
				<>
					<p className="mb-3 small text-secondary">
						{Liferay.Language.get(
							'create-personalized-variations-of-the-collections-for-different-segments'
						)}
					</p>

					<SortableList
						items={assetListEntrySegmentsEntryRels}
						namespace={portletNamespace}
						savePriorityURL={updateVariationsPriorityURL}
					/>
				</>
			)}
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
