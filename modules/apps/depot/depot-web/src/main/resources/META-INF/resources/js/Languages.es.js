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
import ClayIcon from '@clayui/icon';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const defaultLocales = 'defaultLocales';
const customLocales = 'customLocales';

/**
 * @class Languages
 */
const Languages = ({availableLocales, defaultUserLocale, inheritLocales}) => {
	const [selectedRadioGroupValue, setSelectedRadioGroupValue] = useState(
		inheritLocales ? defaultLocales : customLocales
	);

	const Language = ({isDefaultLanguage, localeId, displayName, showActions}) => {
		return (
			<ClayTable.Row>
				<ClayTable.Cell>
					{displayName}
					<span className="hide"> {localeId} </span>
					{isDefaultLanguage && (
						<span> DEFAULT!! </span>
					)}
				</ClayTable.Cell>
				{ showActions && (
					<ClayTable.Cell>
						<ClayIcon symbol="ellipsis-v" />
					</ClayTable.Cell>
				)}
			</ClayTable.Row>
		);
	};

	const LanguagesList = ({defaultLanguage, languages, showActions}) => {
		return (
			<ClayTable borderless>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell expanded headingCell headingTitle>
							{Liferay.Language.get('language')}
						</ClayTable.Cell>

						{ showActions && (
							<ClayTable.Cell>
								<ClayButton displayType="secondary">{Liferay.Language.get('add')}</ClayButton>
							</ClayTable.Cell>
						)}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{languages.map(locale => {
						console.log(locale);
						return <Language {...locale}
							isDefaultLanguage = {locale.country === defaultLanguage.country && locale.language == defaultLanguage.language}
							key={locale.localeId}
							showActions={showActions}
						/>;
					})}
				</ClayTable.Body>
			</ClayTable>
		)
	}

	return (
		<div class="languages mt-5">
			<ClayRadioGroup
				name="TypeSettingsProperties--inheritLocales--"
				onSelectedValueChange={setSelectedRadioGroupValue}
				selectedValue={selectedRadioGroupValue}
			>
				<ClayRadio
					label={Liferay.Language.get('use-the-default-language-options')}
					value={defaultLocales}
				/>

				<ClayRadio
					label={Liferay.Language.get('define-a-custom-default-language-and-additional-available-languages-for-this-repository')}
					value={customLocales}
				/>
			</ClayRadioGroup>

			{selectedRadioGroupValue ===  defaultLocales && (
				<LanguagesList defaultLanguage={defaultUserLocale} languages={availableLocales} showActions={false} />
			)}

			{selectedRadioGroupValue ===  customLocales && (
				<span> customLocales </span>
			)}
		</div>
	);
}

Languages.propTypes = {
	availableLocales: PropTypes.array,
	defaultUserLocale: PropTypes.shape({
		country: PropTypes.string,
		displayName: PropTypes.string,
		language: PropTypes.string,
		localeId: PropTypes.string
	}),
	inheritLocales: PropTypes.bool,
};

export default function(props) {
	console.log(props);
	return <Languages {...props} />;
}