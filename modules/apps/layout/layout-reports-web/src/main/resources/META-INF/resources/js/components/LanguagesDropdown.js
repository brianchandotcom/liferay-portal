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
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {SET_LANGUAGE_ID} from '../constants/actionTypes';
import {ConstantsContext} from '../context/ConstantsContext';
import {StoreDispatchContext} from '../context/StoreContext';
import loadIssues from '../utils/loadIssues';

export default function LanguagesDropdown({
	canonicalURLs,
	defaultLanguageId,
	selectedLanguageId,
}) {
	const [active, setActive] = useState(false);

	const dispatch = useContext(StoreDispatchContext);
	const {portletNamespace} = useContext(ConstantsContext);

	const onLanguageSelect = (languageId) => {
		dispatch({languageId, type: SET_LANGUAGE_ID});
		setActive(false);

		const url = canonicalURLs.find(
			(canonicalURL) =>
				canonicalURL.languageId === (languageId || defaultLanguageId)
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
				{Object.values(canonicalURLs).map(({languageId}, index) => (
					<ClayDropDown.Item
						active={selectedLanguageId === languageId}
						key={index}
						onClick={() => onLanguageSelect(languageId)}
						symbolLeft={languageId.toLowerCase()}
					>
						<ClayLayout.ContentRow>
							<ClayLayout.ContentCol expand>
								<span>{languageId}</span>
							</ClayLayout.ContentCol>
							{defaultLanguageId === languageId && (
								<ClayLayout.ContentCol>
									<ClayLabel displayType="primary">
										{Liferay.Language.get('default')}
									</ClayLabel>
								</ClayLayout.ContentCol>
							)}
						</ClayLayout.ContentRow>
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

LanguagesDropdown.propTypes = {
	canonicalURLs: PropTypes.arrayOf(
		PropTypes.shape({
			canonicalURL: PropTypes.string.isRequired,
			languageId: PropTypes.string.isRequired,
			title: PropTypes.string.isRequired,
		})
	),
	defaultLanguageId: PropTypes.string.isRequired,
	selectedLanguageId: PropTypes.string.isRequired,
};
