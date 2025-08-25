/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {FormRelationship as FormRelationshipItem} from '../../../types/layout_data/FormRelationship';
import {ContainerWithControls} from '../../js-index';
import FormRelationship from './FormRelationship';

export default React.forwardRef<
	HTMLDivElement,
	{children: React.ReactNode; item: FormRelationshipItem}
>(({children, item, ...rest}, ref) => {
	return (
		<ContainerWithControls {...rest} item={item} ref={ref}>
			<FormRelationshipWithControls item={item}>
				{children}
			</FormRelationshipWithControls>
		</ContainerWithControls>
	);
});

function FormRelationshipWithControls({
	children,
	item,
}: {
	children: React.ReactNode;
	item: FormRelationshipItem;
}) {
	return <FormRelationship item={item}>{children}</FormRelationship>;
}
