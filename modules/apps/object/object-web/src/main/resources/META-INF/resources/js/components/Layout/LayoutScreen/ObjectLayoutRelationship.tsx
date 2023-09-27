/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {
	Panel,
	PanelSimpleBody,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React from 'react';

import {useLayoutContext} from '../objectLayoutContext';

interface ObjectLayoutRelationshipProps
	extends React.HTMLAttributes<HTMLElement> {
	objectRelationshipId: number;
}

export function ObjectLayoutRelationship({
	objectRelationshipId,
}: ObjectLayoutRelationshipProps) {
	const [{creationLanguageId, objectRelationships}] = useLayoutContext();

	const objectRelationship = objectRelationships.find(
		({id}) => id === objectRelationshipId
	)!;

	return (
		<>
			<Panel key={`field_${objectRelationshipId}`}>
				<PanelSimpleBody
					title={getLocalizableLabel(
						creationLanguageId,
						objectRelationship.label,
						objectRelationship.name
					)}
				>
					<small className="text-secondary">
						{Liferay.Language.get('relationship')} |{' '}
					</small>

					<ClayLabel
						displayType={
							objectRelationship.reverse ? 'info' : 'success'
						}
					>
						{objectRelationship.reverse
							? Liferay.Language.get('child')
							: Liferay.Language.get('parent')}
					</ClayLabel>
				</PanelSimpleBody>
			</Panel>
		</>
	);
}
