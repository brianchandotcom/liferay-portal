/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DisplayType} from '@clayui/alert';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React from 'react';

import {Language, Translations} from './TranslationSelector';

const LABEL_STATUS = {
	default: {
		displayType: 'info',
		label: Liferay.Language.get('default'),
	},
	notTranslated: {
		displayType: 'warning',
		label: Liferay.Language.get('not-translated'),
	},
	translated: {
		displayType: 'success',
		label: Liferay.Language.get('translated'),
	},
	translating: {
		displayType: 'secondary',
		label: '',
	},
} as const;

interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	item: Language;
	translations: Translations;
}

interface Status {
	displayType: DisplayType;
	label: string;
}

export default function StatusLabel({
	defaultLanguageId,
	item,
	translations,
}: Props) {
	let status: Status = LABEL_STATUS.notTranslated;

	if (item.id === defaultLanguageId) {
		status = LABEL_STATUS.default;
	}
	else if (item.translations) {
		const totalTranslations = Object.keys(translations).length;

		if (item.translations === totalTranslations) {
			status = LABEL_STATUS.translated;
		}
		else {
			status = LABEL_STATUS.translating;
			status.label = sub(Liferay.Language.get('translating-x-x'), [
				item.translations,
				totalTranslations,
			]);
		}
	}

	return (
		<ClayLayout.ContentCol containerElement="span">
			<span className="sr-only">
				{sub(
					Liferay.Language.get('x-language-x'),
					item.label,
					status.label
				)}
			</span>

			<ClayLayout.ContentSection>
				<ClayLabel displayType={status.displayType}>
					<span aria-hidden="true">{status.label}</span>
				</ClayLabel>
			</ClayLayout.ContentSection>
		</ClayLayout.ContentCol>
	);
}
