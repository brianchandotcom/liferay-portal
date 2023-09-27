/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {SingleSelect, Toggle} from '@liferay/object-js-components-web';
import React, {useMemo} from 'react';

import {defaultLanguageId} from '../../../../utils/constants';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';

const languages = Liferay.Language.available;
const languageLabels = Object.values(languages).map((language) => {
	return {label: language};
});
const defaultLanguage = languageLabels[0].label;

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

		return label || defaultLanguage;
	}, [objectField.indexedLanguageId]);

	return (
		<>
			{isApproved && (
				<ClayAlert displayType="info" title="Info">
					{Liferay.Language.get(
						'if-the-search-configuration-of-this-object-field-is-updated'
					)}
				</ClayAlert>
			)}

			<ClayForm.Group>
				<Toggle
					disabled={
						objectField.businessType === 'Encrypted' ||
						objectField.system
					}
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
				<SingleSelect
					label={Liferay.Language.get('language')}
					onChange={(value) => {
						const [indexedLanguageId] = Object.entries(
							languages
						).find(([, label]) => value.label === label) as [
							Liferay.Language.Locale,
							string
						];

						setValues({indexedLanguageId});
					}}
					options={languageLabels}
					required
					value={selectedLanguage}
				/>
			)}
		</>
	);
}
