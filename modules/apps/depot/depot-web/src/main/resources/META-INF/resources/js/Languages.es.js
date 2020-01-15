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
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

/**
 * @class Languages
 */
const Languages = ({
	availableLocales,
	defaultLocaleId,
	inheritLocales = false,
	siteAvailableLocales,
	siteDefaultLocaleId
}) => {
	const defaultOption = 'defaultOption';
	const customOption = 'customOption';

	const [selectedRadioGroupValue, setSelectedRadioGroupValue] = useState(
		inheritLocales ? defaultOption : customOption
	);

	const Language = ({displayName, isDefault, localeId, showActions}) => {
		return (
			<ClayTable.Row>
				<ClayTable.Cell expanded>
					{displayName}
					<span className="hide"> {localeId} </span>
					{isDefault && (
						<ClayLabel className="ml-3" displayType="info">
							{Liferay.Language.get('default')}
						</ClayLabel>
					)}
				</ClayTable.Cell>
				{showActions && (
					<ClayTable.Cell align="center">
						<ClayIcon symbol="ellipsis-v" />
					</ClayTable.Cell>
				)}
			</ClayTable.Row>
		);
	};

	const LanguagesList = ({defaultLocaleId, locales, showActions}) => {
		return (
			<ClayTable borderless headVerticalAlignment="middle">
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell expanded headingCell headingTitle>
							{Liferay.Language.get('language')}
						</ClayTable.Cell>

						{showActions && (
							<ClayTable.Cell align="center">
								<ClayButton displayType="secondary">
									{Liferay.Language.get('add')}
								</ClayButton>
							</ClayTable.Cell>
						)}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{locales.map(locale => {
						return (
							<Language
								{...locale}
								isDefault={defaultLocaleId === locale.localeId}
								key={locale.localeId}
								showActions={showActions}
							/>
						);
					})}
				</ClayTable.Body>
			</ClayTable>
		);
	};

	return (
		<div className="mt-5">
			<ClayRadioGroup
				name="TypeSettingsProperties--inheritLocales--"
				onSelectedValueChange={setSelectedRadioGroupValue}
				selectedValue={selectedRadioGroupValue}
			>
				<ClayRadio
					label={Liferay.Language.get(
						'use-the-default-language-options'
					)}
					value={defaultOption}
				/>

				<ClayRadio
					label={Liferay.Language.get(
						'define-a-custom-default-language-and-additional-available-languages-for-this-repository'
					)}
					value={customOption}
				/>
			</ClayRadioGroup>

			{selectedRadioGroupValue === defaultOption && (
				<LanguagesList
					defaultLocaleId={defaultLocaleId}
					locales={availableLocales}
					showActions={false}
				/>
			)}

			{selectedRadioGroupValue === customOption && (
				<LanguagesList
					defaultLocaleId={siteDefaultLocaleId}
					locales={siteAvailableLocales}
					showActions={true}
				/>
			)}
		</div>
	);
};

Languages.propTypes = {
	availableLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string
		})
	).isRequired,
	defaultLocaleId: PropTypes.string.isRequired,
	inheritLocales: PropTypes.bool,
	siteAvailableLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string
		})
	).isRequired,
	siteDefaultLocaleId: PropTypes.string.isRequired
};

export default function(props) {
	return <Languages {...props} />;
}
