/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		default: assetListEntrySegmentsEntryRels.length > 1,
		emptyState: assetListEntrySegmentsEntryRels.length === 1,
		showDefaultStateHeaderAddVariationButton() {
			return (
				this.default && assetListEntryValid && segmentsEntriesAvailables
			);
		},
		showEmptyStateAddVariationButton() {
			return (
				this.emptyState &&
				assetListEntryValid &&
				segmentsEntriesAvailables
			);
		},
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

				{states.showDefaultStateHeaderAddVariationButton() && (
					<ClayTooltipProvider>
						<ClayButtonWithIcon
							data-tooltip-align="top"
							displayType="unstyled"
							onClick={handleAddVariation}
							size="sm"
							symbol="plus"
							title={Liferay.Language.get('create-variation')}
						/>
					</ClayTooltipProvider>
				)}
			</div>

			{states.emptyState && (
				<ClayEmptyState
					description={Liferay.Language.get(
						'no-personalized-variations-were-found'
					)}
					title={Liferay.Language.get(
						'no-personalized-variations-yet'
					)}
				>
					{states.showEmptyStateAddVariationButton() && (
						<ClayButton
							displayType="primary"
							onClick={handleAddVariation}
							size="sm"
						>
							{Liferay.Language.get('add-personalized-variation')}
						</ClayButton>
					)}
				</ClayEmptyState>
			)}

			{states.default && (
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
