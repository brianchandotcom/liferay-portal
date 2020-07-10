/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {getItem} from 'app-builder-web/js/utils/client.es';
import {getTranslatedValue} from 'app-builder-web/js/utils/utils.es';

export function populateConfigData([app, workflowApp]) {
	return new Promise((resolve, reject) => {
		const {dataDefinitionId, dataLayoutId, dataListViewId} = app;

		const params = {keywords: '', page: -1, pageSize: -1, sort: ''};

		const promises = [
			getItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-layouts`,
				params
			).then(({items}) => items),
			Promise.all([
				getItem(
					'/o/data-engine/v2.0/data-definitions/by-content-type/app-builder',
					params
				),
				getItem(
					'/o/data-engine/v2.0/data-definitions/by-content-type/native-object',
					params
				),
			]).then(([customObjects, nativeObjects]) => {
				return [
					...customObjects.items.map((item) => ({
						...item,
						type: 'custom',
					})),
					...nativeObjects.items.map((item) => ({
						...item,
						type: 'native',
					})),
				];
			}),
			getItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-list-views`,
				params
			).then(({items}) => items),
		];

		return Promise.all(promises)
			.then(([formViews, objects, tableViews]) => {
				const parseItem = (item = {}) => {
					return {
						...item,
						name: getTranslatedValue(item, 'name'),
					};
				};

				const config = {
					dataObject: parseItem(
						objects.find(({id}) => id === dataDefinitionId)
					),
					formView: parseItem(
						formViews.find(({id}) => id === dataLayoutId)
					),
					tableView: parseItem(
						tableViews.find(({id}) => id === dataListViewId)
					),
				};

				workflowApp.appWorkflowTasks.forEach((task) => {
					task.appWorkflowDataLayoutLinks = task.appWorkflowDataLayoutLinks.map(
						(item) => {
							const {name} = parseItem(
								formViews.find(
									({id}) => id === item.dataLayoutId
								)
							);

							return {...item, name};
						}
					);
				});

				resolve([app, workflowApp, config]);
			})
			.catch(reject);
	});
}
