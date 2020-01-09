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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import React from 'react';

const CONTAINER_TYPE_IDENTIFIER = 'type';

export const ContainerTypeConfiguration = ({containerType, onValueChange}) => (
	<ClayForm.Group>
		<label htmlFor="containerType">
			{Liferay.Language.get('container')}
		</label>
		<ClaySelectWithOption
			aria-label={Liferay.Language.get('container')}
			id="containerType"
			onChange={({target: {value}}) =>
				onValueChange({[CONTAINER_TYPE_IDENTIFIER]: value})
			}
			options={[
				{
					label: Liferay.Language.get('fixed-width'),
					value: 'fixed'
				},
				{
					label: Liferay.Language.get('fluid'),
					value: 'fluid'
				}
			]}
			value={containerType}
		/>
	</ClayForm.Group>
);
