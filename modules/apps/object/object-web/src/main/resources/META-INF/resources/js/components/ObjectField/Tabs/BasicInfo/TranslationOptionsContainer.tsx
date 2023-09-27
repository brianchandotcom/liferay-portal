/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {Toggle} from '@liferay/object-js-components-web';
import React from 'react';

import './TranslationOptionsContainer.scss';

interface TranslationOptionsContainerProps {
	objectDefinition: Partial<ObjectDefinition>;
	published: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function TranslationOptionsContainer({
	objectDefinition,
	published,
	setValues,
	values,
}: TranslationOptionsContainerProps) {
	const translatableField =
		(values.businessType === 'LongText' ||
			values.businessType === 'RichText' ||
			values.businessType === 'Text') &&
		!values.system;

	return (
		<>
			{!translatableField && (
				<ClayAlert
					displayType="info"
					title={`${Liferay.Language.get('info')}:`}
				>
					{`${Liferay.Language.get(
						'this-field-type-does-not-support-translations'
					)} `}

					<ClayLink href="#" target="_blank" weight="semi-bold">
						{Liferay.Language.get('click-here-for-documentation')}
					</ClayLink>
				</ClayAlert>
			)}

			<div className="lfr__objects-translation-options-container">
				<Toggle
					disabled={
						published ||
						!translatableField ||
						!objectDefinition.enableLocalization
					}
					label={Liferay.Language.get('enable-entry-translations')}
					onToggle={(localized) =>
						setValues({
							localized,
							required: Liferay.FeatureFlags['LPS-172017']
								? !localized && values.required
								: values.required,
						})
					}
					toggled={values.localized}
				/>

				<ClayTooltipProvider>
					<span
						title={Liferay.Language.get(
							'users-will-be-able-to-add-translations-for-the-entries-of-this-field'
						)}
					>
						<ClayIcon
							className="lfr__objects-translation-options-container-icon"
							symbol="question-circle-full"
						/>
					</span>
				</ClayTooltipProvider>
			</div>
		</>
	);
}
