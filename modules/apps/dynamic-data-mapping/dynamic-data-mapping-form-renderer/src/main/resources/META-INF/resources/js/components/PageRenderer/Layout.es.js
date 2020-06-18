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

import React from 'react';

import {EVENT_TYPES, usePage} from '../../hooks/usePage.es';
import {Field} from '../Field/Field.es';
import * as DefaultVariant from './DefaultVariant.es';

export const Layout = ({components: Components = DefaultVariant, rows}) => {
	const {
		dispatch,
		store: {activePage, allowNestedFields, editable, pageIndex, spritemap},
	} = usePage();

	return (
		<Components.Rows
			activePage={activePage}
			editable={editable}
			pageIndex={pageIndex}
			rows={rows}
		>
			{({index: rowIndex, row}) => (
				<Components.Row key={rowIndex} row={row}>
					{({column, index}) => (
						<Components.Column
							activePage={activePage}
							allowNestedFields={allowNestedFields}
							column={column}
							editable={editable}
							index={index}
							key={index}
							pageIndex={pageIndex}
							rowIndex={rowIndex}
						>
							{(fieldProps) => (
								<Field
									{...fieldProps}
									activePage={activePage}
									editable={editable}
									key={
										fieldProps.field?.instanceId ??
										fieldProps.field.name
									}
									onBlur={(event) =>
										dispatch({
											payload: event,
											type: EVENT_TYPES.FIELD_BLUR,
										})
									}
									onChange={(event) =>
										dispatch({
											payload: event,
											type: EVENT_TYPES.FIELD_CHANGE,
										})
									}
									onFocus={(event) =>
										dispatch({
											payload: event,
											type: EVENT_TYPES.FIELD_FOCUS,
										})
									}
									pageIndex={pageIndex}
									spritemap={spritemap}
								/>
							)}
						</Components.Column>
					)}
				</Components.Row>
			)}
		</Components.Rows>
	);
};
