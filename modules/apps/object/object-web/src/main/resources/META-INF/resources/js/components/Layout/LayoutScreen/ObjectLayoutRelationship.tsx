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
