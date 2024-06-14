/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

const FORMATTED_ITEM_SEPARATOR = ' - ';

const ListTypeDefinitionsAutocomplete = ({namespace}) => {
	const [listTypeDefinitions, setListTypeDefinitions] = useState([]);
	const [value, setValue] = useState('');

	useEffect(() => {
		fetch('/o/headless-admin-list-type/v1.0/list-type-definitions')
			.then((response) => response.json())
			.then(({items}) => setListTypeDefinitions(items));
	}, []);

	const items = useMemo(
		() =>
			listTypeDefinitions.filter(
				({externalReferenceCode, name, name_i18n}) =>
					externalReferenceCode.includes(value) ||
					name.includes(value) ||
					name_i18n[
						Liferay.ThemeDisplay.getBCP47LanguageId()
					].includes(value)
			),
		[listTypeDefinitions, value]
	);

	const formattedValue = useMemo(
		() =>
			value
				.split(FORMATTED_ITEM_SEPARATOR)
				.slice(0, -1)
				.join(FORMATTED_ITEM_SEPARATOR),
		[value]
	);

	return (
		<ClayForm.Group aria-required={true}>
			<label
				aria-required={true}
				className="control-label"
				htmlFor={`${namespace}autocomplete`}
				id={`${namespace}autocomplete-label`}
			>
				{Liferay.Language.get('name')}

				<span className="reference-mark text-warning">
					<ClayIcon symbol="asterisk" />
				</span>

				<span className="hide-accessible sr-only">
					{Liferay.Language.get('required')}
				</span>
			</label>

			<ClayAutocomplete
				aria-labelledby={`${namespace}autocomplete-label`}
				aria-required={true}
				id={`${namespace}autocomplete`}
				items={items}
				menuTrigger="focus"
				onChange={setValue}
				placeholder={Liferay.Language.get('select-a-picklist')}
				required={true}
				value={formattedValue}
			>
				{({externalReferenceCode, id, name_i18n}, index) => (
					<ClayAutocomplete.Item
						key={index}
						onClick={() => {
							Liferay.fire('list-type-definition-id-selected', {
								id,
							});
						}}
						textValue={`${
							name_i18n[Liferay.ThemeDisplay.getBCP47LanguageId()]
						}${FORMATTED_ITEM_SEPARATOR}${externalReferenceCode}`}
					/>
				)}
			</ClayAutocomplete>
		</ClayForm.Group>
	);
};

export default ListTypeDefinitionsAutocomplete;
