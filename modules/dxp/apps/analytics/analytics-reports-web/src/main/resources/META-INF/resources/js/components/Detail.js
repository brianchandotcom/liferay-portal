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
import {Align} from 'metal-position';
import PropTypes from 'prop-types';
import React from 'react';

import {numberFormat} from '../utils/numberFormat';
import Hint from './Hint';
import TotalCount from './TotalCount';

const KEYWORD_VALUE_TYPE = [
	{label: Liferay.Language.get('traffic'), name: 'traffic'},
	{label: Liferay.Language.get('volume'), name: 'volume'},
	{label: Liferay.Language.get('position'), name: 'position'},
];

export default function Detail({
	currentPage,
	languageTag,
	trafficShareDataProvider,
	trafficVolumeDataProvider,
}) {
	const [isDropdownOpen, setIsDropdownOpen] = React.useState(false);

	const [keywordValueType, setKeywordValueType] = React.useState(
		KEYWORD_VALUE_TYPE.find(keywordValueType => {
			return keywordValueType.name == 'traffic';
		})
	);

	return (
		<>
			<div className="p-3">
				<TotalCount
					className="mb-2"
					dataProvider={trafficVolumeDataProvider}
					label={Liferay.Util.sub(
						Liferay.Language.get('traffic-volume')
					)}
					popoverAlign={Align.Bottom}
					popoverHeader={Liferay.Language.get('traffic-volume')}
					popoverMessage={Liferay.Language.get(
						'traffic-volume-is-the-estimated-number-of-visitors-coming-to-your-page'
					)}
					popoverPosition="bottom"
				/>

				<TotalCount
					className="mb-4"
					dataProvider={trafficShareDataProvider}
					label={Liferay.Util.sub(
						Liferay.Language.get('traffic-share')
					)}
					percentage={true}
					popoverHeader={Liferay.Language.get('traffic-share')}
					popoverMessage={Liferay.Language.get(
						'traffic-share-is-the-percentage-of-traffic-sent-to-your-page-by-one-source'
					)}
				/>

				<table className="table-keywords">
					<thead>
						<tr height="40">
							<th>
								{Liferay.Language.get('best-keyword')}
								<span className="text-secondary">
									<Hint
										message={Liferay.Language.get(
											'best-keyword-help'
										)}
										title={Liferay.Language.get(
											'best-keyword'
										)}
									/>
								</span>
							</th>
							<th>
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
											<span className="font-weight-bold">
												<span className="pr-2">
													{keywordValueType.label}
												</span>
												<ClayIcon symbol="angle-down" />
											</span>
										</ClayButton>
									}
								>
									<ClayDropDown.ItemList>
										{KEYWORD_VALUE_TYPE.map(
											(valueType, index) => (
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
																		keywordValueType.name ==
																		valueType.name
																	);
																}
															)
														);
														setIsDropdownOpen(
															false
														);
													}}
												>
													{valueType.label}
												</ClayDropDown.Item>
											)
										)}
									</ClayDropDown.ItemList>
								</ClayDropDown>
							</th>
						</tr>
					</thead>
					<tbody>
						{currentPage.data.keywords.map(keyword => {
							return (
								<tr height="30" key={keyword.title}>
									<td>{keyword.title}</td>
									<td
										align="right"
										className="text-secondary"
									>
										{numberFormat(
											languageTag,
											keywordValueType.name == 'traffic'
												? keyword.value
												: keywordValueType.name ==
												  'volume'
												? keyword.volume
												: keyword.position
										)}
									</td>
								</tr>
							);
						})}
					</tbody>
				</table>
			</div>
		</>
	);
}

Detail.proptypes = {
	currentPage: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
