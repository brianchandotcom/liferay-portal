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

import {useResource} from '@clayui/data-provider';
import {ClayIconSpriteContext} from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {
	PagesVisitor,
	getConnectedReactComponentAdapter,
} from 'dynamic-data-mapping-form-renderer';
import {fetch} from 'frontend-js-web';
import React, {useImperativeHandle, useMemo, useState} from 'react';

import {FormsRuleEditor} from './FormsRuleEditor.es';
import {FormsRuleList} from './FormsRuleList.es';

const Route = ({children, location, path}) => {
	if (path !== location) {
		return null;
	}

	return children;
};

const FormsRuleBuilder = React.forwardRef(
	(
		{
			dataProviderInstancesURL,
			functionsMetadata,
			instance: metal,
			pages,
			rolesURL,
			rules,
			visible,
			spritemap,
			...otherProps
		},
		ref
	) => {
		const [path, setPath] = useState('list');
		const [rule, setRule] = useState(null);

		const {resource: resourceDataProvider} = useResource({
			fetch,
			link: location.origin + dataProviderInstancesURL,
			variables: {
				languageId: themeDisplay.getLanguageId(),
				scopeGroupId: themeDisplay.getScopeGroupId(),
			},
		});

		const {resource: resourceRoles} = useResource({
			fetch,
			link: location.origin + rolesURL,
		});

		useImperativeHandle(
			ref,
			() => ({
				isViewMode: () => path === 'list',
				showRuleCreation: () => {
					setRule(null);
					setPath('editor');
				},
				showRuleList: () => {
					setRule(null);
					setPath('list');
				},
			}),
			[path, setPath, setRule]
		);

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

		// This visible behavior is only for compatibility with the
		// Forms application using Metal.js, the visibility conditional
		// must be controlled by the parent component.

		if (!visible) {
			return null;
		}

		const dataProvider = resourceDataProvider?.map((data) => ({
			...data,
			label: data.name,
			value: data.id,
		}));

		const roles = resourceRoles?.map((role) => ({
			...role,
			label: role.name,
			value: role.name,
		}));

		const dispatch = (name, event) => metal.context.dispatch(name, event);

		return (
			<ClayIconSpriteContext.Provider value={spritemap}>
				<ClayLayout.Container>
					<Route location={path} path="editor">
						<FormsRuleEditor
							{...otherProps}
							dataProvider={dataProvider}
							fields={fields}
							onCancel={() => {
								setPath('list');
								setRule(null);
								dispatch('ruleCancelled');
							}}
							onSave={(event) => {
								if (rule !== null) {
									dispatch('ruleSaved', {
										...event,
										ruleEditedIndex: rule,
									});
								}
								else {
									dispatch('ruleAdded', event);
								}

								setPath('list');
								setRule(null);
							}}
							operatorsByType={functionsMetadata}
							pages={pageOptions}
							roles={roles}
							rule={rules[rule]}
						/>
					</Route>
					<Route location={path} path="list">
						<FormsRuleList
							dataProvider={dataProvider}
							fields={fields}
							onDelete={(ruleId) => {
								dispatch('ruleDeleted', {ruleId});
							}}
							onEdit={(index) => {
								setRule(index);
								setPath('editor');
							}}
							pages={pageOptions}
							rules={rules}
						/>
					</Route>
				</ClayLayout.Container>
			</ClayIconSpriteContext.Provider>
		);
	}
);

FormsRuleBuilder.displayName = 'FormsRuleBuilder';

const MetalFormsRuleBuilderAdapter = getConnectedReactComponentAdapter(
	FormsRuleBuilder
);

export default MetalFormsRuleBuilderAdapter;
