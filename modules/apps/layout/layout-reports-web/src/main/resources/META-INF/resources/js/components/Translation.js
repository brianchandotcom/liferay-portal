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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {SET_LANGUAGE_ID} from '../constants/actionTypes';
import {ConstantsContext} from '../context/ConstantsContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import loadIssues from '../utils/loadIssues';

export default function Translation({
	defaultLanguageId,
	pageURLs,
	selectedLanguageId,
}) {
	const [active, setActive] = useState(false);

	const dispatch = useContext(StoreDispatchContext);
	const {portletNamespace} = useContext(ConstantsContext);

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
			portletNamespace,
			url,
		});
	};

	return (
		<ClayDropDown
			active={active}
			hasLeftSymbols
			menuElementAttrs={{
				className: 'dropdown-menu__languages',
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
									<ClayLabel displayType="primary">
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
