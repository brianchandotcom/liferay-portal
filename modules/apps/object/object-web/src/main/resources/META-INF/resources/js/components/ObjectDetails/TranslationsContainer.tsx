/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {Toggle} from '@liferay/object-js-components-web';
import React from 'react';

import './TranslationsContainer.scss';

interface TranslationsContainerProps {
	onSubmit?: (editedObjectDefinition?: Partial<ObjectDefinition>) => void;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function TranslationsContainer({
	onSubmit,
	setValues,
	values,
}: TranslationsContainerProps) {
	return (
		<div className="lfr-objects-translations-container">
			<ClayForm.Group>
				<Toggle
					disabled={values.active}
					label={Liferay.Language.get('enable-entry-translations')}
					onBlur={(event) => {
						event.stopPropagation();

						if (onSubmit) {
							onSubmit();
						}
					}}
					onToggle={() =>
						setValues({
							enableLocalization: !values.enableLocalization,
						})
					}
					toggled={values.enableLocalization}
					tooltip={Liferay.Language.get(
						'enable-entry-translations-in-all-fields'
					)}
				/>
			</ClayForm.Group>
		</div>
	);
}
