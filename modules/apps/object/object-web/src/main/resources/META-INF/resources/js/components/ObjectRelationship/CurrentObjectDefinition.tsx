/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {Input, getLocalizableLabel} from '@liferay/object-js-components-web';
import React from 'react';

interface CurrentObjectDefinitionProps {
	currentObjectDefinition?: Partial<ObjectDefinition>;
	disableReverseButton?: boolean;
	disabled?: boolean;
	error?: string;
	handleReverseOrder: () => void;
	hideReverseButton: boolean;
	label?: string;
}

export default function CurrentObjectDefinition({
	currentObjectDefinition,
	disableReverseButton,
	disabled,
	error,
	handleReverseOrder,
	hideReverseButton,
	label,
}: CurrentObjectDefinitionProps) {
	const readOnly = disabled ? false : true;

	return (
		<div className="lfr-object-web__modal-add-object-relationship-current-object-input">
			<Input
				disabled={disabled}
				error={error}
				label={label}
				name="currentObjectInput"
				readOnly={readOnly}
				required
				value={getLocalizableLabel(
					currentObjectDefinition?.defaultLanguageId as Liferay.Language.Locale,
					currentObjectDefinition?.label,
					currentObjectDefinition?.name
				)}
			/>

			{!hideReverseButton && (
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('reverse-order')}
					disabled={disableReverseButton}
					displayType="secondary"
					onClick={handleReverseOrder}
					symbol="order-arrow"
				/>
			)}
		</div>
	);
}
