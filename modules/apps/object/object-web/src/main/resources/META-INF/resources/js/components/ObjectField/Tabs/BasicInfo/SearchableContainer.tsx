/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {SingleSelect, Toggle} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {useEffect} from 'react';

import {defaultLanguageId} from '../../../../utils/constants';

import '../../EditObjectFieldContent.scss';

const languages = Liferay.Language.available;
const languageLabels = Object.entries(languages).map(([key, value]) => {
	return {label: value, value: key};
});

interface SearchableProps {
	isApproved: boolean;
	modelBuilder?: boolean;
	onSubmit?: (value?: Partial<ObjectField>) => void;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function SearchableContainer({
	isApproved,
	modelBuilder,
	onSubmit,
	readOnly,
	setValues,
	values,
}: SearchableProps) {
	const isSearchableString =
		values.indexed &&
		(values.DBType === 'Clob' ||
			values.DBType === 'String' ||
			values.businessType === 'Attachment') &&
		values.businessType !== 'Aggregation';

	useEffect(() => {
		if (!values.indexedLanguageId) {
			setValues({
				indexedLanguageId: defaultLanguageId,
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div
			className={classNames({
				'lfr-objects__edit-object-field-card-content':
					modelBuilder === false,
				'lfr-objects__edit-object-field-model-builder-panel':
					modelBuilder,
			})}
		>
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
						values.businessType === 'Aggregation' ||
						values.businessType === 'Encrypted' ||
						values.businessType === 'Formula' ||
						values.system
					}
					label={Liferay.Language.get('searchable')}
					name="indexed"
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
					onToggle={(indexed) => setValues({indexed})}
					toggled={values.indexed}
				/>
			</ClayForm.Group>

			{isSearchableString && (
				<ClayForm.Group
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
				>
					<ClayRadioGroup
						onChange={(selected: string | number) => {
							const indexedAsKeyword = selected === 'true';
							const indexedLanguageId = indexedAsKeyword
								? ''
								: defaultLanguageId;

							setValues({
								indexedAsKeyword,
								indexedLanguageId,
							});
						}}
						value={new Boolean(values.indexedAsKeyword).toString()}
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

			{isSearchableString && !values.indexedAsKeyword && (
				<SingleSelect
					items={languageLabels}
					label={Liferay.Language.get('language')}
					onSelectionChange={(value) => {
						setValues({indexedLanguageId: value as string});

						if (onSubmit) {
							onSubmit({
								...values,
								indexedLanguageId: value as string,
							});
						}
					}}
					required
					selectedKey={values.indexedLanguageId as string}
				/>
			)}
		</div>
	);
}
