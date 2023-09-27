/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext, useMemo, useState} from 'react';

import {ChartStateContext, TimeSpan} from '../context/ChartStateContext';
import {StoreStateContext} from '../context/StoreContext';

interface ViewURL {
	default: boolean;
	languageId: string;
	languageLabel: string;
	selected: boolean;
	viewURL: string;
}

interface Props {
	onSelectedLanguageClick: (
		url: string,
		timeSpanKey: TimeSpan | undefined,
		timeSpanOffset: number
	) => void;
	viewURLs: ViewURL[];
}

export default function Translation({
	onSelectedLanguageClick,
	viewURLs,
}: Props) {
	const {languageTag: defaultLanguage} = useContext(StoreStateContext);

	const [active, setActive] = useState(false);

	const selectedLanguage = useMemo(() => {
		return (
			viewURLs.find((language) => language.selected)?.languageId ||
			defaultLanguage ||
			viewURLs[0].languageId
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
									language.selected && !!language.languageId
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
										<ClayLabel displayType="info">
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
