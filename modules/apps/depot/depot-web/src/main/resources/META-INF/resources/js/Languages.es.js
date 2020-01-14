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
const Languages = ({defaultLanguage, inheritLocales, languages}) => {
	const [selectedRadioGroupValue, setSelectedRadioGroupValue] = useState(
		inheritLocales ? defaultLocales : customLocales
	);

	const Language = ({id, label}) => {
		return (
			<ClayTable.Row>
				<ClayTable.Cell>
					{label}
					<span className="hide"> {id} </span>
				</ClayTable.Cell>
				<ClayTable.Cell>
					<ClayIcon symbol="ellipsis-v" />
				</ClayTable.Cell>
			</ClayTable.Row>
		);
	};

	const LanguagesList = ({languages}) => {
		return (
			<ClayTable borderless>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell expanded headingCell headingTitle>
							{Liferay.Language.get('language')}
						</ClayTable.Cell>
						<ClayTable.Cell>
							<ClayButton displayType="secondary">{Liferay.Language.get('add')}</ClayButton>
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{languages.map(language => {
						return <Language id={language.key} label={language.value} key={language.id} />;
					})}
				</ClayTable.Body>
			</ClayTable>
		)
	}

	return (
		<div class="languages">
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
				<span> defaultLocales </span>
			)}

			{selectedRadioGroupValue ===  customLocales && (
				<span> customLocales </span>
			)}
			<LanguagesList languages={languages} />
		</div>
	);
}

Languages.propTypes = {
	defaultLanguage: PropTypes.string,
	inheritLocales: PropTypes.bool,
	languages: PropTypes.array
};

export default function(props) {
	return <Languages {...props} />;
}