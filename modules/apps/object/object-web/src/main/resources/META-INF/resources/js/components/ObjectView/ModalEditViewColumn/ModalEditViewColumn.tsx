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
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {FormEvent, useContext, useState} from 'react';

import Input from '../../Form/Input';
import InputLocalized from '../../Form/InputLocalized/InputLocalized';
import ViewContext, {TYPES} from '../context';

interface IProps {
	editingObjectFieldName: string;
	observer: any;
	onClose: () => void;
}
type TLocale = {
	label: string;
	symbol: string;
};

const availableLocales: TLocale[] = Object.keys(Liferay.Language.available).map(
	(language) => {
		const formattedLocales = language.replace('_', '-');

		return {
			label: language,
			symbol: formattedLocales.toLowerCase(),
		};
	}
);

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

export function ModalEditViewColumn({
	editingObjectFieldName,
	observer,
	onClose,
}: IProps) {
	const [
		{
			objectView: {objectViewColumns},
		},
		dispatch,
	] = useContext(ViewContext);

	const [editingColumn] = objectViewColumns.filter(
		(viewColumn) => viewColumn.objectFieldName === editingObjectFieldName
	);
	const {label} = editingColumn;

	const [selectedLocale, setSelectedLocale] = useState<TLocale>(
		availableLocales[0]
	);

	const [translations, setTranslations] = useState(label);

	const onSubmit = (event: FormEvent) => {
		event.preventDefault();

		Object.entries(translations).forEach(([key, value]) => {
			if (value === '' && key !== defaultLanguageId) {
				delete translations[key];
			}
		});

		dispatch({
			payload: {editingObjectFieldName, translations},
			type: TYPES.EDIT_OBJECT_VIEW_COLUMN_LABEL,
		});

		onClose();
	};

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={(event) => onSubmit(event)}>
				<ClayModal.Header>
					{Liferay.Language.get('rename-column-label')}
				</ClayModal.Header>

				<ClayModal.Body>
					<Input
						disabled
						label={Liferay.Language.get('field-label')}
						name={editingObjectFieldName}
						value={editingColumn.label[defaultLanguageId]}
					/>

					<InputLocalized
						id="locale"
						label={Liferay.Language.get('column-label')}
						locales={availableLocales}
						onSelectedLocaleChange={setSelectedLocale}
						onTranslationsChange={setTranslations}
						required
						selectedLocale={selectedLocale}
						translations={translations}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group key={1} spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton displayType="primary" type="submit">
								{Liferay.Language.get('edit')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}
