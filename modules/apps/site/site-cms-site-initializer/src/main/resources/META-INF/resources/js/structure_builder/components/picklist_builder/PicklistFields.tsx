/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {InputLocalized} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import {
	useErc,
	useName,
	useSetErc,
	useSetName,
} from '../../contexts/PicklistBuilderContext';
import ERCInput from '../ERCInput';

export default function PicklistFields() {
	const erc = useErc();
	const name = useName();
	const setErc = useSetErc();
	const setName = useSetName();

	return (
		<ClayForm.Group className={classNames({'has-error': !name})}>
			<InputLocalized
				aria-label={Liferay.Language.get('picklist-name')}
				error={
					name[Liferay.ThemeDisplay.getDefaultLanguageId()]
						? ''
						: Liferay.Language.get('this-field-is-required')
				}
				label={Liferay.Language.get('name')}
				onBlur={() => setName(name)}
				onChange={(name) => setName(name)}
				required
				translations={name as Liferay.Language.LocalizedValue<string>}
			/>

			<ERCInput
				helpText={sub(
					Liferay.Language.get('unique-key-for-referencing-the-x'),
					Liferay.Language.get('picklist')
				)}
				onValueChange={(erc) => setErc(erc)}
				value={erc}
			/>
		</ClayForm.Group>
	);
}
