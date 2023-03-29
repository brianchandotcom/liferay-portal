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
import {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {Card} from '@liferay/object-js-components-web';
import React from 'react';

import './TranslationOptionsContainer.scss';

interface TranslationOptionsContainerProps {
	published: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

export function TranslationOptionsContainer({
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
		<Card title={Liferay.Language.get('translation-options')}>
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
				<ClayToggle
					disabled={published || !translatableField}
					label={Liferay.Language.get('enable-entry-translation')}
					onToggle={() =>
						setValues({
							localized: !values.localized,
						})
					}
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
		</Card>
	);
}
