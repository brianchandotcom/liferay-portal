/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {LanguagePicker, Provider} from '@clayui/core';
import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelectWithOption,
	ClayToggle,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {IVocabulary} from '../types/IVocabulary';

const VISIBILITY_OPTIONS = [
	{
		label: Liferay.Language.get('public'),
		value: 0,
	},
	{
		label: Liferay.Language.get('private'),
		value: 1,
	},
];

export default function EditGeneralInfo({
	defaultLanguageId,
	locales,
	nameInputError,
	onChangeVocabulary,
	setNameInputError,
	spritemap,
	vocabulary,
}: {
	defaultLanguageId: string;
	locales: any[];
	nameInputError: string;
	onChangeVocabulary: Function;
	setNameInputError: Function;
	spritemap: string;
	vocabulary: IVocabulary;
}) {
	const [isChecked, setIsChecked] = useState<boolean>(true);
	const [languageId, setLanguageId] = useState<string>(defaultLanguageId);
	const [toggled, setToggle] = useState<boolean>(true);

	const onChangeDescription = (newDescription: string) => {
		onChangeVocabulary(() => ({
			...vocabulary,
			description_i18n: {
				...vocabulary.description_i18n,
				[languageId]: newDescription,
			},
		}));
	};

	const onChangeName = (newName: string) => {
		if (newName) {
			setNameInputError('');
		}
		else {
			setNameInputError(
				sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('name')
				)
			);
		}

		onChangeVocabulary(() => ({
			...vocabulary,
			name_i18n: {
				...vocabulary.name_i18n,
				[languageId]: newName,
			},
		}));
	};

	return (
		<div className="vertical-nav-content-wrapper">
			<ClayForm.Group className="c-gap-4 d-flex flex-column p-4">
				<div className="d-flex">
					<div className="autofit-col autofit-col-expand form-title">
						{Liferay.Language.get('basic-info')}
					</div>

					<div className="autofit-col" style={{width: 'fit-content'}}>
						<Provider spritemap={spritemap}>
							<LanguagePicker
								defaultLocaleId={defaultLanguageId}
								locales={locales}
								onSelectedLocaleChange={(
									localId: React.Key
								) => {
									setLanguageId(localId as string);
								}}
								selectedLocaleId={languageId}
								small
							/>
						</Provider>
					</div>
				</div>

				<div className={nameInputError ? 'has-error' : ''}>
					<label>
						{Liferay.Language.get('name')}

						<ClayIcon
							className="c-ml-1 reference-mark"
							focusable="false"
							role="presentation"
							symbol="asterisk"
						/>
					</label>

					<ClayInput
						id={Liferay.Language.get('name')}
						onChange={({target: {value}}) => onChangeName(value)}
						required
						type="text"
						value={vocabulary.name_i18n[languageId] || ''}
					/>

					{nameInputError && (
						<ClayAlert displayType="danger" variant="feedback">
							{nameInputError}
						</ClayAlert>
					)}
				</div>

				<div>
					<label>{Liferay.Language.get('description')}</label>

					<ClayInput
						component="textarea"
						onChange={({target: {value}}) =>
							onChangeDescription(value)
						}
						type="text"
						value={
							vocabulary.description_i18n
								? vocabulary.description_i18n[languageId] || ''
								: ''
						}
					/>
				</div>

				<label className="toggle-switch">
					<ClayToggle onToggle={setToggle} toggled={toggled} />

					{Liferay.Language.get('allow-multiple-categories')}

					<ClayTooltipProvider>
						<span
							className="help-text-icon ml-2"
							title={Liferay.Language.get('multi-valued-help')}
						>
							<ClayIcon symbol="question-circle-full" />
						</span>
					</ClayTooltipProvider>
				</label>

				<div>
					<label>
						{Liferay.Language.get('visibility')}

						<ClayTooltipProvider>
							<span
								className="help-text-icon ml-2"
								title={Liferay.Language.get('visibility-help')}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClaySelectWithOption options={VISIBILITY_OPTIONS} />
				</div>
			</ClayForm.Group>

			<ClayForm.Group className="c-gap-4 d-flex flex-column p-4">
				<div className="form-title">
					{Liferay.Language.get('space')}
				</div>

				<div>
					<label>
						{Liferay.Language.get('space')}

						<ClayIcon
							className="c-ml-1 reference-mark"
							focusable="false"
							role="presentation"
							symbol="asterisk"
						/>
					</label>

					<ClaySelectWithOption options={[]} />
				</div>

				<ClayCheckbox
					checked={isChecked}
					label={Liferay.Language.get(
						'make-this-vocabulary-available-in-all-spaces'
					)}
					onChange={() => setIsChecked(!isChecked)}
				/>
			</ClayForm.Group>
		</div>
	);
}
