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
import React, {useContext, useMemo, useState} from 'react';

import {StoreStateContext} from '../../context/StoreContext';
import {numberFormat} from '../../utils/numberFormat';

const KEYWORD_VALUE_TYPE = [
	{label: Liferay.Language.get('views'), name: 'views'},
	{label: Liferay.Language.get('views-percentage'), name: 'views-percentage'},
];

export default function Countries({currentPage}) {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);

	const [keywordValueType, setKeywordValueType] = useState(
		KEYWORD_VALUE_TYPE[0]
	);

	const {languageTag, publishedToday} = useContext(StoreStateContext);

	const countries = useMemo(() => {
		const dataKeys = new Set();

		return currentPage.data.countrySearchKeywords.reduce(
			(acc, {countryCode, countryName, views, viewsP}) => {
				if (dataKeys.has(countryCode)) {
					return acc;
				}

				dataKeys.add(countryCode);

				return acc.concat({countryCode, countryName, views, viewsP});
			},
			[]
		);
	}, [currentPage.data.countrySearchKeywords]);

	const handleKeywordValueType = (valueTypeName) => {
		const newKeywordValueType = KEYWORD_VALUE_TYPE.find(
			(keywordValueType) => {
				return keywordValueType.name === valueTypeName;
			}
		);
		setKeywordValueType(newKeywordValueType);
	};

	return (
		<>
			<h5 className="mt-3 sheet-subtitle">
				{Liferay.Language.get('views-by-location')}
			</h5>

			<ClayList className="list-group-keywords-list">
				<ClayList.Item flex>
					<ClayList.ItemField expand>
						<ClayList.ItemTitle className="text-truncate-inline">
							<span className="text-truncate">
								{Liferay.Language.get('Country')}
							</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>

					<ClayList.ItemField>
						<ClayDropDown
							active={isDropdownOpen}
							onActiveChange={setIsDropdownOpen}
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
											handleKeywordValueType(
												valueType.name
											);
											setIsDropdownOpen(false);
										}}
									>
										{valueType.label}
									</ClayDropDown.Item>
								))}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</ClayList.ItemField>
				</ClayList.Item>

				{!publishedToday &&
					countries.map(
						({countryCode, countryName, views, viewsP}) => {
							return (
								<ClayList.Item flex key={countryCode}>
									<ClayList.ItemField expand>
										<ClayList.ItemText>
											<span
												className="text-truncate-inline"
												data-tooltip-align="top"
												title={countryName}
											>
												<span className="text-secondary text-truncate">
													{countryName}
												</span>
											</span>
										</ClayList.ItemText>
									</ClayList.ItemField>

									<ClayList.ItemField expand>
										<span className="align-self-end font-weight-semi-bold text-dark">
											{ 
												keywordValueType.name ===
													'views'
													? numberFormat(languageTag, views)
													: keywordValueType.name ===
													  'views-percentage'
													? `${numberFormat(languageTag, viewsP)}%`
													: views
											}
										</span>
									</ClayList.ItemField>
								</ClayList.Item>
							);
						}
					)}
			</ClayList>
		</>
	);
}

Countries.propTypes = {
	currentPage: PropTypes.object.isRequired,
};
