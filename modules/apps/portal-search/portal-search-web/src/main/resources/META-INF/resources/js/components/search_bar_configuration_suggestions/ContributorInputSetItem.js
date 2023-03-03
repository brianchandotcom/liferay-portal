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

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {CONTRIBUTOR_TYPES} from '../../utils/types/contributorTypes';
import ContributorInputSetItemHeader from './ContributorInputSetItemHeader';
import BasicAttributes from './attributes/BasicAttributes';
import SXPBlueprintAttributes from './attributes/SXPBlueprintAttributes';

function ContributorInputSetItem({
	index,
	learnMessages,
	onInputSetItemChange,
	value = {},
}) {
	const [touched, setTouched] = useState({
		displayGroupName: false,
		size: false,
		sxpBlueprintId: false,
	});

	const _handleBlur = (field) => () => {
		setTouched({...touched, [field]: true});
	};

	return (
		<ClayInput.GroupItem>
			<ContributorInputSetItemHeader
				contributorName={value.contributorName}
				learnMessages={learnMessages}
			/>

			<div className="form-group-autofit">
				<ClayInput.GroupItem
					className={getCN({
						'has-error':
							!value.displayGroupName && touched.displayGroupName,
					})}
				>
					<label>
						{Liferay.Language.get('display-group-name')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>

						<ClayTooltipProvider>
							<span
								className="ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'display-group-name-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClayInput
						onBlur={_handleBlur('displayGroupName')}
						onChange={onInputSetItemChange(
							index,
							'displayGroupName'
						)}
						required
						type="text"
						value={value.displayGroupName || ''}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem
					className={getCN({
						'has-error':
							(!value.size || value.size < 0) && touched.size,
					})}
				>
					<label>
						{Liferay.Language.get('size')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>

						<ClayTooltipProvider>
							<span
								className="ml-2"
								data-tooltip-align="top"
								title={Liferay.Language.get(
									'size-suggestion-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClayInput
						aria-label={Liferay.Language.get('size')}
						min="0"
						onBlur={_handleBlur('size')}
						onChange={onInputSetItemChange(index, 'size')}
						required
						type="number"
						value={value.size || ''}
					/>

					{value.size < 0 && touched.size && (
						<div className="form-feedback-group">
							<div className="form-feedback-item">
								{sub(
									Liferay.Language.get(
										'please-enter-a-value-greater-than-or-equal-to-x'
									),
									'0'
								)}
							</div>
						</div>
					)}
				</ClayInput.GroupItem>
			</div>

			{value.contributorName === CONTRIBUTOR_TYPES.BASIC && (
				<BasicAttributes
					index={index}
					onInputSetItemChange={onInputSetItemChange}
					value={value}
				/>
			)}

			{value.contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT && (
				<SXPBlueprintAttributes
					index={index}
					onBlur={_handleBlur}
					onInputSetItemChange={onInputSetItemChange}
					touched={touched}
					value={value}
				/>
			)}
		</ClayInput.GroupItem>
	);
}

export default ContributorInputSetItem;
