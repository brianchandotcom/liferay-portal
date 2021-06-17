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
import {ClayTooltipProvider} from '@clayui/tooltip';
import PropTypes from 'prop-types';
import React, {useContext, useMemo, useState} from 'react';

import {ChartStateContext} from '../context/ChartStateContext';
import {StoreStateContext} from '../context/StoreContext';

export default function Translation({onSelectedLanguageClick, viewURLs}) {
	const {languageTag: defaultLanguage} = useContext(StoreStateContext);

	const [active, setActive] = useState(false);

	const selectedLanguage = useMemo(() => {
		return (
			viewURLs.find((language) => language.selected)?.languageId ||
			defaultLanguage
		);
	}, [defaultLanguage, viewURLs]);

	const {timeSpanKey, timeSpanOffset} = useContext(ChartStateContext);

	return (
		<ClayLayout.ContentRow>
			<ClayLayout.ContentCol>
				<ClayDropDown
					active={active}
					hasLeftSymbols
					menuElementAttrs={{
						className: 'dropdown-menu__translation',
					}}
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
										timeSpanKey,
										timeSpanOffset
									);
								}}
								symbolLeft={language.languageId.toLowerCase()}
							>
								<ClayLayout.ContentRow>
									<ClayLayout.ContentCol expand>
										<ClayTooltipProvider>
											<span
												className="text-truncate-inline"
												data-tooltip-align="top"
												title={language.languageLabel}
											>
												<span className="text-truncate">
													{language.languageLabel}
												</span>
											</span>
										</ClayTooltipProvider>
									</ClayLayout.ContentCol>
									{language.default && (
										<ClayLabel displayType="primary">
											{Liferay.Language.get('default')}
										</ClayLabel>
									)}
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
