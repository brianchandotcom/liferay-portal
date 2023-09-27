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
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {SET_LANGUAGE_ID} from '../../constants/actionTypes';
import {
	StoreDispatchContext,
	StoreStateContext,
} from '../../context/StoreContext';
import loadIssues from '../../utils/loadIssues';

export default function Translation({
	defaultLanguageId,
	pageURLs,
	selectedLanguageId,
}) {
	const [active, setActive] = useState(false);

	const dispatch = useContext(StoreDispatchContext);

	const {loading} = useContext(StoreStateContext);

	const onLanguageSelect = (languageId) => {
		dispatch({languageId, type: SET_LANGUAGE_ID});
		setActive(false);

		const url = pageURLs.find(
			(pageURL) =>
				pageURL.languageId === (languageId || defaultLanguageId)
		);

		loadIssues({
			dispatch,
			languageId,
			refreshCache: false,
			url,
		});
	};

	return (
		<ClayDropDown
			active={active}
			hasLeftSymbols
			menuElementAttrs={{
				containerProps: {
					className: 'cadmin',
				},
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="btn-monospaced"
					disabled={loading}
					displayType="secondary"
					small
				>
					<ClayIcon symbol={selectedLanguageId.toLowerCase()} />

					<span
						className="d-block font-weight-normal"
						style={{fontSize: '9px'}}
					>
						{selectedLanguageId}
					</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{Object.values(pageURLs).map(
					({languageId, languageLabel}, index) => (
						<ClayDropDown.Item
							active={selectedLanguageId === languageId}
							key={index}
							onClick={() => onLanguageSelect(languageId)}
							symbolLeft={languageId.toLowerCase()}
						>
							<ClayLayout.ContentRow>
								<ClayLayout.ContentCol expand>
									<ClayTooltipProvider>
										<span
											className="text-truncate-inline"
											data-tooltip-align="top"
											title={languageLabel}
										>
											<span className="text-truncate">
												{languageLabel}
											</span>
										</span>
									</ClayTooltipProvider>
								</ClayLayout.ContentCol>

								{defaultLanguageId === languageId && (
									<ClayLabel
										className="c-ml-1 flex-shrink-0"
										displayType="primary"
									>
										{Liferay.Language.get('default')}
									</ClayLabel>
								)}
							</ClayLayout.ContentRow>
						</ClayDropDown.Item>
					)
				)}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

Translation.propTypes = {
	defaultLanguageId: PropTypes.string.isRequired,
	pageURLs: PropTypes.arrayOf(
		PropTypes.shape({
			languageId: PropTypes.string.isRequired,
			title: PropTypes.string.isRequired,
			url: PropTypes.string.isRequired,
		})
	),
	selectedLanguageId: PropTypes.string.isRequired,
};
