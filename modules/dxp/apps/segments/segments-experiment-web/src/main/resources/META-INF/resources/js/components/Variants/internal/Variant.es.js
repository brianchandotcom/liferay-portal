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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import SegmentsExperimentsContext from '../../../context.es';
import {navigateToExperience} from '../../../util/navigation.es';
import {indexToPercentageString} from '../../../util/percentages.es';

function Variant({
	active,
	control = false,
	editable,
	name,
	onVariantDeletion,
	onVariantEdition,
	onVariantPublish,
	publishable,
	segmentsExperienceId,
	showSplit,
	split,
	variantId,
	winner
}) {
	const [openDropdown, setOpenDropdown] = useState(false);
	const {editVariantLayoutURL} = useContext(SegmentsExperimentsContext);

	return (
		<ClayList.Item active={active} flex>
			<ClayList.ItemField expand>
				<ClayList.ItemTitle>
					<ClayButton
						className="lfr-portal-tooltip text-truncate"
						data-title={name}
						displayType="unstyled"
						onClick={_handleVariantNavigation}
					>
						{winner && (
							<ClayIcon
								className="mr-1 text-success"
								symbol="check-circle-full"
							/>
						)}

						{control ? (
							<>
								<span className="mr-2">
									{Liferay.Language.get('variant-control')}
								</span>

								<ClayIcon symbol="lock" />
							</>
						) : (
							name
						)}
					</ClayButton>
				</ClayList.ItemTitle>
			</ClayList.ItemField>

			{!control && editable && (
				<>
					<ClayList.ItemField>
						<ClayButton
							borderless
							className="btn-monospaced"
							displayType="secondary"
							onClick={_handleEditVariantContent}
						>
							<ClayIcon symbol="pencil" />
						</ClayButton>
					</ClayList.ItemField>

					<ClayList.ItemField>
						<ClayDropDown
							active={openDropdown}
							onActiveChange={setOpenDropdown}
							trigger={
								<ClayButton
									aria-label={Liferay.Language.get(
										'show-actions'
									)}
									borderless
									className="btn-monospaced"
									displayType="secondary"
								>
									<ClayIcon symbol="ellipsis-v" />
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList>
								<ClayDropDown.Item onClick={_handleEdition}>
									{Liferay.Language.get('edit')}
								</ClayDropDown.Item>

								<ClayDropDown.Item onClick={_handleDeletion}>
									{Liferay.Language.get('delete')}
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</ClayList.ItemField>
				</>
			)}
			{showSplit && (
				<ClayList.ItemField>
					<span
						aria-label={Liferay.Language.get('traffic-split')}
						className="font-weight-normal list-group-title mr-1 text-secondary"
					>
						{indexToPercentageString(split)}
					</span>
				</ClayList.ItemField>
			)}

			{publishable && (
				<ClayList.ItemField>
					<ClayButton
						displayType={winner ? 'primary' : 'secondary'}
						onClick={() => onVariantPublish(segmentsExperienceId)}
						small
					>
						{Liferay.Language.get('publish')}
					</ClayButton>
				</ClayList.ItemField>
			)}
		</ClayList.Item>
	);

	function _handleDeletion() {
		const confirmed = confirm(
			Liferay.Language.get('are-you-sure-you-want-to-delete-this')
		);

		if (confirmed) {
			return onVariantDeletion(variantId);
		}
	}

	function _handleEdition() {
		return onVariantEdition({name, variantId});
	}

	function _handleEditVariantContent() {
		navigateToExperience(segmentsExperienceId, editVariantLayoutURL);
	}

	function _handleVariantNavigation() {
		navigateToExperience(segmentsExperienceId);
	}
}

Variant.propTypes = {
	active: PropTypes.bool.isRequired,
	control: PropTypes.bool.isRequired,
	editable: PropTypes.bool.isRequired,
	name: PropTypes.string.isRequired,
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	onVariantPublish: PropTypes.func.isRequired,
	publishable: PropTypes.bool.isRequired,
	segmentsExperienceId: PropTypes.string.isRequired,
	showSplit: PropTypes.bool.isRequired,
	split: PropTypes.number,
	variantId: PropTypes.string.isRequired,
	winner: PropTypes.bool
};

export default Variant;
