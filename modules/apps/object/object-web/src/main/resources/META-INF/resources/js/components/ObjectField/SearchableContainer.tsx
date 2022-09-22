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

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import {Card, Select} from '@liferay/object-js-components-web';
import React, {useMemo} from 'react';

import {ObjectFieldErrors} from './ObjectFieldFormBase';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
const languages = Liferay.Language.available;
const languageLabels = Object.values(languages).map((language) => {
	return {label: language};
});

interface ISearchableProps {
	disabled?: boolean;
	errors: ObjectFieldErrors;
	isApproved: boolean;
	objectField: Partial<ObjectField>;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
}

export function SearchableContainer({
	isApproved,
	objectField,
	readOnly,
	setValues,
}: ISearchableProps) {
	const isSearchableString =
		objectField.indexed &&
		(objectField.DBType === 'Clob' ||
			objectField.DBType === 'String' ||
			objectField.businessType === 'Attachment') &&
		objectField.businessType !== 'Aggregation';

	const selectedLanguage = useMemo(() => {
		const label =
			objectField.indexedLanguageId &&
			languages[objectField.indexedLanguageId];

		return label ?? undefined;
	}, [objectField.indexedLanguageId]);

	return (
		<Card title={Liferay.Language.get('searchable')}>
			{isApproved && (
				<ClayAlert displayType="info" title="Info">
					{Liferay.Language.get(
						'if-the-search-configuration-of-this-object-field-is-updated'
					)}
				</ClayAlert>
			)}

			<ClayForm.Group>
				<ClayToggle
					label={Liferay.Language.get('searchable')}
					name="indexed"
					onToggle={(indexed) => setValues({indexed})}
					toggled={objectField.indexed}
				/>
			</ClayForm.Group>

			{isSearchableString && (
				<ClayForm.Group>
					<ClayRadioGroup
						onChange={(selected: string | number) => {
							const indexedAsKeyword = selected === 'true';
							const indexedLanguageId = indexedAsKeyword
								? null
								: defaultLanguageId;

							setValues({
								indexedAsKeyword,
								indexedLanguageId,
							});
						}}
						value={new Boolean(
							objectField.indexedAsKeyword
						).toString()}
					>
						<ClayRadio
							disabled={readOnly}
							label={Liferay.Language.get('keyword')}
							value="true"
						/>

						<ClayRadio
							disabled={readOnly}
							label={Liferay.Language.get('text')}
							value="false"
						/>
					</ClayRadioGroup>
				</ClayForm.Group>
			)}

			{isSearchableString && !objectField.indexedAsKeyword && (
				<Select
					label={Liferay.Language.get('language')}
					name="indexedLanguageId"
					onChange={({target: {value}}) => {
						const [indexedLanguageId] = Object.entries(
							languages
						).find(([, label]) => value === label) as [
							Locale,
							string
						];

						setValues({indexedLanguageId});
					}}
					options={languageLabels}
					required
					value={selectedLanguage}
				/>
			)}
		</Card>
	);
}
