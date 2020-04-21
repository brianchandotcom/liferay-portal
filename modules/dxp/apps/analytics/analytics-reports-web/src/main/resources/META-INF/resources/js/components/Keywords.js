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
import {ClayTooltipProvider} from '@clayui/tooltip';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {numberFormat} from '../utils/numberFormat';
import Hint from './Hint';

const KEYWORD_VALUE_TYPE = [
	{label: Liferay.Language.get('traffic'), name: 'traffic'},
	{label: Liferay.Language.get('search-volume'), name: 'volume'},
	{label: Liferay.Language.get('position'), name: 'position'},
];

export default function Keywords({currentPage, languageTag}) {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);

	const [keywordValueType, setKeywordValueType] = useState(
		KEYWORD_VALUE_TYPE.find(keywordValueType => {
			return keywordValueType.name === 'traffic';
		})
	);

	return (
		<ul className="list-group list-group-keywords-list list-group-sm">
			<li className="list-group-item list-group-item-flex">
				<div className="autofit-col autofit-col-expand">
					<div className="list-group-title text-truncate-inline">
						<span className="text-truncate">
							{Liferay.Language.get('best-keyword')}
							<span className="text-secondary">
								<Hint
									message={Liferay.Language.get(
										'best-keyword-help'
									)}
									title={Liferay.Language.get('best-keyword')}
								/>
							</span>
						</span>
					</div>
				</div>
				<div className="autofit-col">
					<div className="list-group-title">
						<ClayDropDown
							active={isDropdownOpen}
							onActiveChange={isActive =>
								setIsDropdownOpen(isActive)
							}
							trigger={
								<ClayButton
									className="px-0 text-body"
									displayType="link"
									small
								>
									<span className="font-weight-semi-bold">
										<span className="pr-2">
											{keywordValueType.label}
										</span>
										<ClayIcon symbol="caret-bottom" />
									</span>
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList>
								{KEYWORD_VALUE_TYPE.map((valueType, index) => (
									<ClayDropDown.Item
										active={
											valueType.name ===
											keywordValueType.name
										}
										key={index}
										onClick={() => {
											setKeywordValueType(
												KEYWORD_VALUE_TYPE.find(
													keywordValueType => {
														return (
															keywordValueType.name ===
															valueType.name
														);
													}
												)
											);
											setIsDropdownOpen(false);
										}}
									>
										{valueType.label}
									</ClayDropDown.Item>
								))}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</div>
				</div>
			</li>
			{currentPage.data.keywords.map(keyword => {
				return (
					<li
						className="list-group-item list-group-item-flex"
						key={keyword.title}
					>
						<div className="autofit-col autofit-col-expand">
							<ClayTooltipProvider>
								<span
									className="text-dark text-truncate-inline"
									data-tooltip-align="top"
									title={keyword.title}
								>
									<span className="text-truncate">
										{keyword.title}
									</span>
								</span>
							</ClayTooltipProvider>
						</div>
						<div className="autofit-col text-right text-secondary">
							{numberFormat(
								languageTag,
								keywordValueType.name === 'traffic'
									? keyword.value
									: keywordValueType.name === 'volume'
									? keyword.volume
									: keyword.position
							)}
						</div>
					</li>
				);
			})}
		</ul>
	);
}

Keywords.proptypes = {
	currentPage: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
};
