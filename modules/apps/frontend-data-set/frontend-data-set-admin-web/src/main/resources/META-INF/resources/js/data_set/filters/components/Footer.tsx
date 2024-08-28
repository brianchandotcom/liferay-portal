/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import React from 'react';

export interface IFooterProps {
	onCancel: Function;
	onSave: Function;
	saveButtonDisabled: boolean;
}

function Footer({onCancel, onSave, saveButtonDisabled}: IFooterProps) {
	return (
		<ClayLayout.SheetFooter>
			<ClayButton.Group spaced>
				<ClayButton
					disabled={saveButtonDisabled}
					onClick={() => onSave()}
					type="submit"
				>
					{Liferay.Language.get('save')}
				</ClayButton>

				<ClayButton displayType="secondary" onClick={() => onCancel()}>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayButton.Group>
		</ClayLayout.SheetFooter>
	);
}

export default Footer;
