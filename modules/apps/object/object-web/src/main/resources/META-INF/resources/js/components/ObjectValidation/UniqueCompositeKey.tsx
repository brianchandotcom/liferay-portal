/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BuilderScreen, Card} from '@liferay/object-js-components-web';
import React from 'react';

export function UniqueCompositeKey() {
	return (
		<>
			<Card
				alert={{
					content: Liferay.Language.get(
						'a-unique-composite-key-validation-checks-if-the-combination-of-two-or-more-fields-can-be-used-to-uniquely-identify-each-entry'
					),
					otherProps: {
						displayType: 'info',
						title: Liferay.Language.get('info'),
						variant: 'stripe',
					},
				}}
				title={Liferay.Language.get('fields')}
			>
				<BuilderScreen
					emptyState={{
						buttonText: Liferay.Language.get('add-fields'),
						description: Liferay.Language.get(
							'add-a-minimum-of-two-fields-to-create-composite-unique-keys'
						),
						title: Liferay.Language.get('no-fields-added-yet'),
					}}
					firstColumnHeader={Liferay.Language.get('label')}
					objectColumns={[]}
					onDeleteColumn={() => {}}
					openModal={() => {}}
					secondColumnHeader={Liferay.Language.get('type')}
				/>
			</Card>
		</>
	);
}
