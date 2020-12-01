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
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {useChartState} from '../context/ChartStateContext';

export default function Translation({
	defaultLanguage,
	onSelectedLanguageClick,
	viewURLs,
}) {
	const [active, setActive] = useState(false);

	const selectedLanguage = useMemo(() => {
		return (
			viewURLs.find((language) => language.selected)?.languageId ||
			defaultLanguage
		);
	}, [defaultLanguage, viewURLs]);

	const {state: chartState} = useChartState();

	return (
		<ClayLayout.ContentRow>
			<ClayLayout.ContentCol expand>
				<h5>{Liferay.Language.get('languages-translated-into')}</h5>
				<span className="text-secondary">
					{Liferay.Language.get(
						'select-language-to-view-its-metrics'
					)}
				</span>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol>
				<ClayDropDown
					active={active}
					hasLeftSymbols
					onActiveChange={setActive}
					trigger={
						<ClayButton
							className="btn-monospaced"
							displayType="secondary"
							small
						>
							<ClayIcon symbol={selectedLanguage.toLowerCase()} />
							<span
								className="d-block font-weight-normal"
								style={{fontSize: '9px'}}
							>
								{selectedLanguage}
							</span>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{Object.values(viewURLs).map((language, index) => (
							<ClayDropDown.Item
								active={
									language.selected && language.languageId
								}
								key={index}
								onClick={() => {
									onSelectedLanguageClick(
										language.viewURL,
										chartState.timeSpanKey,
										chartState.timeSpanOffset
									);
								}}
								symbolLeft={language.languageId.toLowerCase()}
							>
								<ClayLayout.ContentRow>
									<ClayLayout.ContentCol expand>
										<span>{language.languageId}</span>
									</ClayLayout.ContentCol>
									<ClayLayout.ContentCol>
										<ClayLabel
											displayType={
												language.default
													? 'primary'
													: 'success'
											}
										>
											{language.default
												? Liferay.Language.get(
														'default'
												  )
												: Liferay.Language.get(
														'translated'
												  )}
										</ClayLabel>
									</ClayLayout.ContentCol>
								</ClayLayout.ContentRow>
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
}

Translation.propTypes = {
	defaultLanguage: PropTypes.string.isRequired,
	onSelectedLanguageClick: PropTypes.func.isRequired,
	viewURLs: PropTypes.arrayOf(
		PropTypes.shape({
			default: PropTypes.bool.isRequired,
			languageId: PropTypes.string.isRequired,
			selected: PropTypes.bool.isRequired,
			viewURL: PropTypes.string.isRequired,
		})
	).isRequired,
};
