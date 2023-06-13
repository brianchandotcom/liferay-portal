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

import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext} from 'react';

import LearnMessage from '../../../shared/LearnMessage';
import SearchContext from '../../../shared/SearchContext';
import InputSetItemHeader from './InputSetItemHeader';
import CharacterThresholdInput from './inputs/CharacterThresholdInput';
import DisplayGroupNameInput from './inputs/DisplayGroupNameInput';
import FieldsInput from './inputs/FieldsInput';
import SXPBlueprintSelectorInput from './inputs/SXPBlueprintSelectorInput';
import SizeInput from './inputs/SizeInput';

function SXPBlueprint({index, onBlur, onInputSetItemChange, touched, value}) {
	const {learnMessages} = useContext(SearchContext);

	const _handleChangeAttribute = (property) => (event) => {
		onInputSetItemChange(index, {
			attributes: {
				...value.attributes,
				[property]: event.target.value,
			},
		});
	};

	const _handleChangeFields = (fields) => {
		onInputSetItemChange(index, {
			attributes: {...value.attributes, fields},
		});
	};

	const _handleChangeSXPBlueprint = (id) => {
		onInputSetItemChange(index, {
			attributes: {
				...value.attributes,
				sxpBlueprintId: id,
			},
		});
	};

	return (
		<>
			<InputSetItemHeader>
				<InputSetItemHeader.Title>
					{Liferay.Language.get('blueprint-suggestions-contributor')}
				</InputSetItemHeader.Title>

				<InputSetItemHeader.Description>
					{Liferay.Language.get(
						'blueprint-suggestions-contributor-help'
					)}

					<LearnMessage
						className="c-ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-blueprints"
					/>
				</InputSetItemHeader.Description>
			</InputSetItemHeader>

			<div className="c-mb-3 form-group-autofit">
				<DisplayGroupNameInput
					onBlur={onBlur('displayGroupName')}
					onChange={onInputSetItemChange(index, 'displayGroupName')}
					touched={touched.displayGroupName}
					value={value.displayGroupName}
				/>

				<SizeInput
					onBlur={onBlur('size')}
					onChange={onInputSetItemChange(index, 'size')}
					touched={touched.size}
					value={value.size}
				/>
			</div>

			<div className="c-mb-3 form-group-autofit">
				<SXPBlueprintSelectorInput
					onBlur={onBlur('attributes.sxpBlueprintId')}
					onSubmit={_handleChangeSXPBlueprint}
					sxpBlueprintId={value.attributes?.sxpBlueprintId}
					touched={touched['attributes.sxpBlueprintId']}
				/>
			</div>

			<div className="c-mb-3 form-group-autofit">
				<CharacterThresholdInput
					onBlur={onBlur('attributes.characterThreshold')}
					onChange={_handleChangeAttribute('characterThreshold')}
					touched={touched['attributes.characterThreshold']}
					value={value.attributes?.characterThreshold}
				/>

				<ClayInput.GroupItem>
					<label>
						{Liferay.Language.get('include-asset-url')}

						<ClayTooltipProvider>
							<span
								className="c-ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'include-asset-url-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClaySelect
						aria-label={Liferay.Language.get('include-asset-url')}
						onChange={_handleChangeAttribute('includeAssetURL')}
						value={value.attributes?.includeAssetURL}
					>
						<ClaySelect.Option
							label={Liferay.Language.get('true')}
							value={true}
						/>

						<ClaySelect.Option
							label={Liferay.Language.get('false')}
							value={false}
						/>
					</ClaySelect>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem>
					<label>
						{Liferay.Language.get('include-asset-summary')}

						<ClayTooltipProvider>
							<span
								className="c-ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'include-asset-summary-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClaySelect
						aria-label={Liferay.Language.get(
							'include-asset-summary'
						)}
						onChange={_handleChangeAttribute(
							'includeAssetSearchSummary'
						)}
						value={value.attributes?.includeAssetSearchSummary}
					>
						<ClaySelect.Option
							label={Liferay.Language.get('true')}
							value={true}
						/>

						<ClaySelect.Option
							label={Liferay.Language.get('false')}
							value={false}
						/>
					</ClaySelect>
				</ClayInput.GroupItem>
			</div>

			<div className="c-mb-0 form-group-autofit">
				<FieldsInput
					fields={value.attributes?.fields}
					onBlur={onBlur('attributes.fields')}
					onChange={_handleChangeFields}
					touched={touched['attributes.fields']}
				/>
			</div>
		</>
	);
}

export default SXPBlueprint;
