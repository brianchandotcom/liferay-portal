/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DisplayType} from '@clayui/alert';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React from 'react';

import {TranslationProgress} from './TranslationAdminSelector';

interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	labels?: {
		default?: string;
		notTranslated?: string;
		translated?: string;
	};
	languageId: Liferay.Language.Locale;
	languageName: string;
	localeValue: string | null;
	translationProgress: TranslationProgress | null;
}

interface Status {
	displayType: DisplayType;
	label: string;
}

export default function TranslationAdminStatusLabel({
	defaultLanguageId,
	labels,
	languageId,
	languageName,
	localeValue,
	translationProgress = null,
}: Props) {
	const status = {
		default: {
			displayType: 'info',
			label: labels?.default || Liferay.Language.get('default'),
		},
		notTranslated: {
			displayType: 'warning',
			label:
				labels?.notTranslated || Liferay.Language.get('not-translated'),
		},
		translated: {
			displayType: 'success',
			label: labels?.translated || Liferay.Language.get('translated'),
		},
	} as const;

	let statusLabel: Status = status.notTranslated;

	if (languageId === defaultLanguageId) {
		statusLabel = status.default;
	}
	else if (localeValue) {
		statusLabel = status.translated;
	}
	else if (translationProgress?.translatedItems[languageId]) {
		const {totalItems, translatedItems} = translationProgress;

		statusLabel =
			totalItems === translatedItems[languageId]
				? status.translated
				: {
						displayType: 'secondary',
						label: sub(Liferay.Language.get('translating-x-x'), [
							translatedItems[languageId],
							totalItems,
						]),
					};
	}

	return (
		<ClayLayout.ContentCol containerElement="span">
			<span className="sr-only">
				{sub(
					Liferay.Language.get('x-language-x'),
					languageName,
					statusLabel.label
				)}
			</span>

			<ClayLayout.ContentSection>
				<ClayLabel
					aria-hidden="true"
					displayType={statusLabel.displayType}
				>
					{statusLabel.label}
				</ClayLabel>
			</ClayLayout.ContentSection>
		</ClayLayout.ContentCol>
	);
}
