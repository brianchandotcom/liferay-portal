/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {Locale} from './TranslationAdminContent';
import {TranslationProgress} from './TranslationAdminSelector';
import TranslationAdminStatusLabel from './TranslationAdminStatusLabel';

interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	item: Locale;
	labels?: {
		default?: string;
		notTranslated?: string;
		translated?: string;
	};
	localeValue: string | null;
	showOnlyFlags?: boolean;
	translationProgress?: TranslationProgress | null;
}

export default function TranslationAdminItem({
	defaultLanguageId,
	item,
	labels,
	localeValue,
	showOnlyFlags,
	translationProgress = null,
}: Props) {
	return (
		<ClayLayout.ContentRow containerElement="span">
			<ClayLayout.ContentCol containerElement="span" expand>
				<ClayLayout.ContentSection>
					<ClayIcon
						className="inline-item inline-item-before"
						symbol={item.symbol}
					/>

					<span aria-hidden="true">{item.label}</span>
				</ClayLayout.ContentSection>
			</ClayLayout.ContentCol>

			{!showOnlyFlags && (
				<TranslationAdminStatusLabel
					defaultLanguageId={defaultLanguageId}
					labels={labels}
					languageId={item.id}
					languageName={item.displayName}
					localeValue={localeValue}
					translationProgress={translationProgress}
				/>
			)}
		</ClayLayout.ContentRow>
	);
}
