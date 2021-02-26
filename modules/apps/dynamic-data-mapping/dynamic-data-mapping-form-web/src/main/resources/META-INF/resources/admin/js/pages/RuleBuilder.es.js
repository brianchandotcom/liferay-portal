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
import ClayLayout from '@clayui/layout';
import {FormsRuleEditor, FormsRuleList} from 'data-engine-taglib';
import {
	PagesVisitor,
	useConfig,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useMemo, useState} from 'react';
import {Route, Switch} from 'react-router-dom';

import {ManagementToolbar} from '../components/ManagementToolbar.es';
import {EVENT_TYPES} from '../eventTypes.es';

export const RuleBuilder = ({history, location}) => {
	const {
		cache,
		dataProviderInstanceParameterSettingsURL,
		dataProviderInstancesURL,
		functionsMetadata,
		functionsURL,
		portletNamespace,
		rolesURL,
	} = useConfig();
	const {pages, rules} = useFormState();
	const dispatch = useForm();

	const [rule, setRule] = useState(null);

	const {resource: resourceDataProvider} = useResource({
		fetch,
		link: window.location.origin + dataProviderInstancesURL,
		storage: cache,
		variables: {
			languageId: themeDisplay.getLanguageId(),
			scopeGroupId: themeDisplay.getScopeGroupId(),
		},
	});

	const {resource: resourceRoles} = useResource({
		fetch,
		link: window.location.origin + rolesURL,
		storage: cache,
	});

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
			},
			true,
			true
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

	const navigate = useCallback(
		(path) => {
			const method =
				path === location.pathname ? history.replace : history.push;

			method(path);
		},
		[history, location.pathname]
	);

	const onAddRule = useCallback(() => navigate('/rules/editor'), [navigate]);

	return (
		<ClayLayout.Container>
			<ManagementToolbar
				onPlusClick={onAddRule}
				portletNamespace={portletNamespace}
				variant="rules"
				visiblePlus={location.pathname === '/rules'}
			/>
			<Switch>
				<Route exact path="/rules">
					<FormsRuleList
						dataProvider={dataProvider}
						fields={fields}
						onDelete={(ruleId) =>
							dispatch({
								payload: ruleId,
								type: EVENT_TYPES.RULE.DELETE,
							})
						}
						onEdit={(index) => {
							setRule(index);
							navigate('/rules/editor');
						}}
						pages={pageOptions}
						rules={rules}
					/>
				</Route>
				<Route path="/rules/editor">
					<FormsRuleEditor
						dataProvider={dataProvider}
						dataProviderInstanceParameterSettingsURL={
							dataProviderInstanceParameterSettingsURL
						}
						fields={fields}
						functionsURL={functionsURL}
						onCancel={() => {
							navigate('/rules');
							setRule(null);
						}}
						onSave={(event) => {
							if (rule !== null) {
								dispatch({
									payload: {
										loc: rule,
										rule: event,
									},
									type: EVENT_TYPES.RULE.CHANGE,
								});
							}
							else {
								dispatch({
									payload: event,
									type: EVENT_TYPES.RULE.ADD,
								});
							}

							navigate('/rules');
							setRule(null);
						}}
						operatorsByType={functionsMetadata}
						pages={pageOptions}
						roles={roles}
						rule={rules[rule]}
					/>
				</Route>
			</Switch>
		</ClayLayout.Container>
	);
};

RuleBuilder.displayName = 'RuleBuilder';
