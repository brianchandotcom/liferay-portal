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
import React, {useState} from 'react';

export default function LanguagesDropdown({
	canonicalURLs,
	defaultLanguageId,
	onSelectedLanguageId,
	selectedLanguageId,
}) {
	const [active, setActive] = useState(false);

	return (
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
						onClick={() => {
							onSelectedLanguageId(languageId);
						}}
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
	onSelectedLanguageId: PropTypes.func.isRequired,
	selectedLanguageId: PropTypes.string.isRequired,
};
