/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import React from 'react';

const Actions = () => {
	return (
		<ClayLayout.Sheet className="mt-3" size="lg">
			<ClayLayout.SheetHeader>
				<h2 className="sheet-title">
					{Liferay.Language.get('actions')}
				</h2>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetFooter>
				<ClayButton.Group spaced>
					<ClayButton>{Liferay.Language.get('save')}</ClayButton>

					<ClayButton displayType="secondary">
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</ClayLayout.SheetFooter>
		</ClayLayout.Sheet>
	);
};

export default Actions;
