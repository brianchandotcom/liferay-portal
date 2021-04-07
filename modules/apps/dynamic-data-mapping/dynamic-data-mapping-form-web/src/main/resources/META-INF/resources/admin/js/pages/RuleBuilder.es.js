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
import {
	PagesVisitor,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo} from 'react';
import {Route, Switch} from 'react-router-dom';

import {ManagementToolbar} from '../components/ManagementToolbar.es';
import {EVENT_TYPES} from '../eventTypes.es';
import {RuleEditor} from './RuleEditor.es';
import {RuleList} from './RuleList.es';

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
	const {currentRuleLoc, pages, rules} = useFormState();
	const dispatch = useForm();

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

	useEffect(() => {

		// Redirects the user to the edit page if a rule is being edited or created.
		// - `undefined` indicates that a new rule is being created
		// - `0...9` indicates the index of the rule
		// - `null` indicates that no rules are in progress

		if (currentRuleLoc !== null) {
			navigate('/rules/editor');
		}
	}, [currentRuleLoc, navigate]);

	const onAddRule = useCallback(() => {
		dispatch({payload: {loc: undefined}, type: EVENT_TYPES.RULE.EDIT});

		navigate('/rules/editor');
	}, [dispatch, navigate]);

	return (
		<ClayLayout.Container>
			<ManagementToolbar
				onPlusClick={location.pathname === '/rules' ? onAddRule : null}
				portletNamespace={portletNamespace}
				variant="rules"
			/>
			<Switch>
				<Route exact path="/rules">
					<RuleList
						dataProvider={dataProvider}
						fields={fields}
						onDelete={(ruleId) =>
							dispatch({
								payload: ruleId,
								type: EVENT_TYPES.RULE.DELETE,
							})
						}
						onEdit={(index) => {
							dispatch({
								payload: {loc: index},
								type: EVENT_TYPES.RULE.EDIT,
							});
							navigate('/rules/editor');
						}}
						pages={pageOptions}
						rules={rules}
					/>
				</Route>
				<Route path="/rules/editor">
					<RuleEditor
						dataProvider={dataProvider}
						dataProviderInstanceParameterSettingsURL={
							dataProviderInstanceParameterSettingsURL
						}
						fields={fields}
						functionsURL={functionsURL}
						onCancel={() => {
							navigate('/rules');
							dispatch({
								payload: {loc: null},
								type: EVENT_TYPES.RULE.EDIT,
							});
						}}
						onSave={(event) => {
							if (currentRuleLoc === undefined) {
								dispatch({
									payload: event,
									type: EVENT_TYPES.RULE.ADD,
								});
							}
							else {
								dispatch({
									payload: {
										loc: currentRuleLoc,
										rule: event,
									},
									type: EVENT_TYPES.RULE.CHANGE,
								});
							}

							navigate('/rules');
						}}
						operatorsByType={functionsMetadata}
						pages={pageOptions}
						roles={roles}
						rule={rules[currentRuleLoc]}
					/>
				</Route>
			</Switch>
		</ClayLayout.Container>
	);
};

RuleBuilder.displayName = 'RuleBuilder';
