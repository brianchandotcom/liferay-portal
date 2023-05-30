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

import ClayPanel from '@clayui/panel';
import {Toggle} from '@liferay/object-js-components-web';
import React from 'react';

import './TranslationsContainer.scss';

interface TranslationsContainerProps {
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function TranslationsContainer({
	setValues,
	values,
}: TranslationsContainerProps) {
	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('translations')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
				<div className="lfr-objects-translations-container">
					<Toggle
						disabled={values.active}
						label={Liferay.Language.get(
							'enable-entry-translations'
						)}
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
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
