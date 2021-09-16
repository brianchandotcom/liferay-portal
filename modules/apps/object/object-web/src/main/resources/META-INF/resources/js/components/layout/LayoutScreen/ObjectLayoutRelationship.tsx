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
import React, {useContext} from 'react';

import {normalizeLanguageId} from '../../../utils/string';
import Panel from '../../Panel/Panel';
import LayoutContext from '../context';
import {TObjectRelationship} from '../types';

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

interface IObjectLayoutRelationshipProps
	extends React.HTMLAttributes<HTMLElement> {
	objectRelationshipId: number;
}

const ObjectLayoutRelationship: React.FC<IObjectLayoutRelationshipProps> = ({
	objectRelationshipId,
}) => {
	const [{objectRelationships}] = useContext(LayoutContext);

	const objectRelationship = objectRelationships.find(
		({id}) => id === objectRelationshipId
	) as TObjectRelationship;

	return (
		<>
			<Panel key={`field_${objectRelationshipId}`}>
				<Panel.SimpleBody
					title={objectRelationship?.label[defaultLanguageId]}
				>
					<small className="text-secondary">
						{Liferay.Language.get('relationship')} |{' '}
					</small>

					<ClayLabel displayType="secondary">
						{objectRelationship?.type}
					</ClayLabel>
				</Panel.SimpleBody>
			</Panel>
		</>
	);
};

export default ObjectLayoutRelationship;
