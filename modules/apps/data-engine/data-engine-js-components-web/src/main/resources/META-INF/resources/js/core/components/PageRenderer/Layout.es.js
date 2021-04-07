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

import React, {useContext} from 'react';

import {getFormId, getFormNode} from '../../../utils/formId.es';
import {useConfig} from '../../hooks/useConfig.es';
import {useEvaluate} from '../../hooks/useEvaluate.es';
import {useForm, useFormState} from '../../hooks/useForm.es';
import {usePage} from '../../hooks/usePage.es';
import fieldBlur from '../../thunks/fieldBlur.es';
import fieldChange from '../../thunks/fieldChange.es';
import fieldFocus from '../../thunks/fieldFocus.es';
import {mergeVariants} from '../../utils/merge-variants.es';
import {Field} from '../Field/Field.es';
import {VariantsContext} from './VariantsContext.es';

export const Layout = ({components, editable, rows}) => {
	const {containerElement, pageIndex} = usePage();
	const {activePage, defaultLanguageId} = useFormState();
	const {allowNestedFields} = useConfig();

	const createFieldChange = useEvaluate(fieldChange);
	const dispatch = useForm();

	const variants = useContext(VariantsContext);

	const Components = components ?? mergeVariants(editable, variants);

	return (
		<Components.Rows
			activePage={activePage}
			editable={editable}
			pageIndex={pageIndex}
			rows={rows}
		>
			{({index: rowIndex, row}) => (
				<Components.Row key={rowIndex} row={row}>
					{({column, index, ...otherProps}) => (
						<Components.Column
							activePage={activePage}
							allowNestedFields={allowNestedFields}
							column={column}
							editable={editable}
							index={index}
							key={index}
							pageIndex={pageIndex}
							row={row}
							rowIndex={rowIndex}
							{...otherProps}
						>
							{(fieldProps) => (
								<Field
									{...fieldProps}
									activePage={activePage}
									defaultLanguageId={defaultLanguageId}
									editable={editable}
									key={
										fieldProps.field?.instanceId ??
										fieldProps.field.name
									}
									onBlur={(event, focusDuration) =>
										dispatch(
											fieldBlur({
												activePage,
												focusDuration,
												formId: getFormId(
													getFormNode(
														containerElement.current
													)
												),
												properties: event,
											})
										)
									}
									onChange={(properties) =>
										dispatch(
											createFieldChange({
												properties,
											})
										)
									}
									onFocus={(event) =>
										dispatch(
											fieldFocus({
												activePage,
												formId: getFormId(
													getFormNode(
														containerElement.current
													)
												),
												properties: event,
											})
										)
									}
									pageIndex={pageIndex}
								/>
							)}
						</Components.Column>
					)}
				</Components.Row>
			)}
		</Components.Rows>
	);
};
