/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React, {useState} from 'react';

import ContentContainer from '../components/ContentContainer/ContentContainer';

/**
 * Holds the props received by RichTextLocalized on each render so tests
 * can call onTranslationsChange / onSelectedLocaleChange directly.
 */

let richTextProps: {
	onSelectedLocaleChange: (locale: {
		label: Liferay.Language.Locale;
		symbol: string;
	}) => void;
	onTranslationsChange: (translations: LocalizedValue<string>) => void;
	selectedLocale: Liferay.Language.Locale;
};

jest.mock('@liferay/object-js-components-web', () => ({
	Card: ({children}: {children: React.ReactNode}) => <div>{children}</div>,
	FormError: {},
	RichTextLocalized: (props: typeof richTextProps) => {
		richTextProps = props;

		return null;
	},
	SingleSelect: () => null,
}));

jest.mock('../components/ContentContainer/Attachments', () => ({
	Attachments: () => null,
}));

const initialValues: NotificationTemplate = {
	attachmentObjectFieldIds: [],
	body: {en_US: ''},
	description: '',
	editorType: 'richText',
	externalReferenceCode: '',
	name: '',
	objectDefinitionExternalReferenceCode: '',
	objectDefinitionId: null,
	recipientType: 'email',
	recipients: [],
	subject: {en_US: ''},
	system: false,
	type: 'email',
};

function ContentContainerWrapper() {
	const [selectedLocale, setSelectedLocale] =
		useState<Liferay.Language.Locale>('en_US');

	const [values, setValues] = useState<NotificationTemplate>(initialValues);

	const handleSetValues = (partial: Partial<NotificationTemplate>) => {
		setValues((current) => ({...current, ...partial}));
	};

	return (
		<ContentContainer
			baseResourceURL=""
			editorConfig={{}}
			errors={{}}
			objectDefinitions={[]}
			selectedLocale={selectedLocale}
			setSelectedLocale={setSelectedLocale}
			setValues={handleSetValues}
			values={values}
		/>
	);
}

describe('ContentContainer: subject is independent from template locale', () => {
	it('does not copy the subject across locales when the template locale switches', () => {
		const mockSetValues = jest.fn();

		render(
			<ContentContainer
				baseResourceURL=""
				editorConfig={{}}
				errors={{}}
				objectDefinitions={[]}
				selectedLocale="en_US"
				setSelectedLocale={() => {}}
				setValues={mockSetValues}
				values={{...initialValues, subject: {en_US: 'Hello'}}}
			/>
		);

		act(() => {
			richTextProps.onSelectedLocaleChange({
				label: 'pt_BR',
				symbol: 'pt-br',
			});
		});

		expect(mockSetValues).not.toHaveBeenCalled();
	});

	it('preserves the subject value when the template locale is switched', async () => {
		render(<ContentContainerWrapper />);

		const subjectInput = screen.getByRole('textbox');

		await userEvent.type(subjectInput, 'Hello');

		expect(subjectInput).toHaveValue('Hello');

		act(() => {
			richTextProps.onSelectedLocaleChange({
				label: 'pt_BR',
				symbol: 'pt-br',
			});
		});

		expect(richTextProps.selectedLocale).toBe('pt_BR');
		expect(subjectInput).toHaveValue('Hello');
	});
});
