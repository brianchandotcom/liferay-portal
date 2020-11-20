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

import ClayButton from '@clayui/button';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import React, {useMemo, useRef} from 'react';

import {Editor} from './editor/Editor.es';

export const FormsRuleEditor = ({onCancel, onSave, pages, ...otherProps}) => {
	const ruleRef = useRef(null);

	const fields = useMemo(() => {
		const fields = [];
		const visitor = new PagesVisitor(pages);

		visitor.mapFields(
			(field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
				if (field.type != 'fieldset') {
					fields.push({
						...field,
						pageIndex,
						value: field.fieldName,
					});
				}
			}
		);

		return fields;
	}, [pages]);

	const pageOptions = useMemo(() => {
		return pages.map(({title}, index) => ({
			label: `${index + 1} ${
				title || Liferay.Language.get('page-title')
			}`,
			name: index.toString(),
			value: index.toString(),
		}));
	}, [pages]);

	return (
		<div className="form-builder-rule-builder liferay-ddm-form-builder-rule-builder-content">
			<div className="liferay-ddm-form-rule-builder-header">
				<h2 className="form-builder-section-title text-default">
					{Liferay.Language.get('rule')}
				</h2>

				<h4 className="text-default">
					{Liferay.Language.get(
						'define-condition-and-action-to-change-fields-and-elements-on-the-form'
					)}
				</h4>
			</div>
			<Editor
				fields={fields}
				onChange={({logicalOperator, ...otherProps}) => {
					ruleRef.current = {
						...otherProps,
						['logical-operator']: logicalOperator,
					};
				}}
				pages={pageOptions}
				{...otherProps}
			/>
			<div className="liferay-ddm-form-rule-builder-footer">
				<ClayButton.Group spaced>
					<ClayButton displayType="primary">
						{Liferay.Language.get('save')}
					</ClayButton>
					<ClayButton displayType="secondary" onClick={onCancel}>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</div>
	);
};
