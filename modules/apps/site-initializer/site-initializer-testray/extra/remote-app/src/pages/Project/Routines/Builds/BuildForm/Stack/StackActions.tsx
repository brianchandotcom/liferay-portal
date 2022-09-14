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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';

import {Fields} from './StackList';

import type {UseFieldArrayAppend} from 'react-hook-form';

type StackActionsProps = {
	append: UseFieldArrayAppend<any>;
	defaultItem: {
		[index: string]: {
			factorCategory: string;
			factorCategoryId: string;
		};
	};
	field: Fields;
	index: number;
	remove: (index: number) => void;
};

const StackActions: React.FC<StackActionsProps> = ({
	append,
	defaultItem,
	field,
	index,
	remove,
}) => (
	<ClayLayout.Col className="d-flex justify-content-end">
		{!field.disabled && (
			<ClayButtonWithIcon
				displayType="secondary"
				onClick={() => append(defaultItem as any)}
				symbol="plus"
			/>
		)}

		<ClayButtonWithIcon
			className="ml-1"
			displayType="secondary"
			onClick={() => remove(index)}
			symbol="hr"
		/>
	</ClayLayout.Col>
);

export default StackActions;
