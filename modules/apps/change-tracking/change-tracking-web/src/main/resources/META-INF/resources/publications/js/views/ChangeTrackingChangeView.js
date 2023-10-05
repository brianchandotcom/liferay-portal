/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayEmptyState from '@clayui/empty-state';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {useCallback, useRef, useState} from 'react';

import ChangeTrackingRenderView from './ChangeTrackingRenderView';

export default function ChangeTrackingChangeView({
	changes,
	columnFromURL,
	contextView,
	ctMappingInfos,
	dataURL,
	defaultLocale,
	deltaFromURL,
	discardURL,
	entryFromURL,
	expired,
	modelData,
	namespace,
	navigationFromURL,
	orderByTypeFromURL,
	pageFromURL,
	showHideableFromURL,
	siteNames,
	spritemap,
	total,
	typeNames,
	userInfo,
}) {
	const CHANGE_TYPE_ADDITION = 0;
	const CHANGE_TYPE_DELETION = 1;
	const COLUMN_TITLE = 'title';
	const GLOBAL_SITE_NAME = Liferay.Language.get('global');
	const NAVIGATION_DATA = 'data';
	const NAVIGATION_RELATIONSHIPS = 'relationships';
	const PARAM_COLUMN = namespace + 'column';
	const PARAM_DELTA = namespace + 'delta';
	const PARAM_ENTRY = namespace + 'entry';
	const PARAM_NAVIGATION = namespace + 'navigation';
	const PARAM_ORDER_BY_TYPE = namespace + 'orderByType';
	const PARAM_PAGE = namespace + 'page';
	const PARAM_SHOW_HIDEABLE = namespace + 'showHideable';
	const ORDER_BY_TYPE_ASC = 'asc';
	const ORDER_BY_TYPE_DESC = 'desc';

	const pathname = window.location.pathname;

	const search = window.location.search;

	const params = new URLSearchParams(search);

	params.delete(PARAM_COLUMN);
	params.delete(PARAM_DELTA);
	params.delete(PARAM_ENTRY);
	params.delete(PARAM_NAVIGATION);
	params.delete(PARAM_ORDER_BY_TYPE);
	params.delete(PARAM_PAGE);
	params.delete(PARAM_SHOW_HIDEABLE);

	const basePathRef = useRef(pathname + '?' + params.toString());

	const renderCacheRef = useRef({});

	const getNodeId = useCallback(
		(modelKey) => {
			if (!contextView) {
				return modelKey;
			}

			const stack = [contextView.everything];

			while (stack.length) {
				const element = stack.pop();

				if (element.modelKey === modelKey) {
					return element.nodeId;
				}

				if (!element.children) {
					continue;
				}

				for (let i = 0; i < element.children.length; i++) {
					stack.push(element.children[i]);
				}
			}

			return 0;
		},
		[contextView]
	);

	const modelsRef = useRef(null);

	if (modelsRef.current === null) {
		modelsRef.current = JSON.parse(JSON.stringify(modelData));

		const modelKeys = Object.keys(modelsRef.current);

		for (let i = 0; i < modelKeys.length; i++) {
			const model = modelsRef.current[modelKeys[i]];

			if (model.groupId) {
				model.siteName = siteNames[model.groupId];
			}
			else {
				model.groupId = 0;
				model.siteName = GLOBAL_SITE_NAME;
			}

			model.nodeId = getNodeId(Number(modelKeys[i]));
			model.typeName = typeNames[model.modelClassNameId];

			if (model.ctEntryId) {
				const user = userInfo[model.userId.toString()];

				if (user) {
					model.portraitURL = user.portraitURL;
					model.userName = user.userName;
				}
				else {
					model.userName = '';
				}

				if (model.siteName === GLOBAL_SITE_NAME) {
					let key = Liferay.Language.get('x-modified-a-x-x-ago');

					if (model.changeType === CHANGE_TYPE_ADDITION) {
						key = Liferay.Language.get('x-added-a-x-x-ago');
					}
					else if (model.changeType === CHANGE_TYPE_DELETION) {
						key = Liferay.Language.get('x-deleted-a-x-x-ago');
					}

					model.description = sub(
						key,
						model.userName,
						model.typeName,
						model.timeDescription
					);
				}
				else {
					let key = Liferay.Language.get('x-modified-a-x-in-x-x-ago');

					if (model.changeType === CHANGE_TYPE_ADDITION) {
						key = Liferay.Language.get('x-added-a-x-in-x-x-ago');
					}
					else if (model.changeType === CHANGE_TYPE_DELETION) {
						key = Liferay.Language.get('x-deleted-a-x-in-x-x-ago');
					}

					model.description = sub(
						key,
						model.userName,
						model.typeName,
						model.siteName,
						model.timeDescription
					);
				}
			}
		}
	}

	const contextViewRef = useRef(null);

	if (contextView && contextViewRef.current === null) {
		contextViewRef.current = JSON.parse(JSON.stringify(contextView));
	}

	const getModels = useCallback((nodes) => {
		if (!nodes) {
			return [];
		}

		const models = [];

		for (let i = 0; i < nodes.length; i++) {
			const node = nodes[i];

			let modelKey = node;

			if (typeof node === 'object') {
				modelKey = node.modelKey;
			}

			if (
				!Object.prototype.hasOwnProperty.call(
					modelsRef.current,
					modelKey.toString()
				)
			) {
				continue;
			}

			const json = JSON.parse(
				JSON.stringify(modelsRef.current[modelKey])
			);

			if (typeof node === 'object') {
				json.nodeId = node.nodeId;
			}

			models.push(json);
		}

		return models;
	}, []);

	const getNode = useCallback(
		(nodeId) => {
			const rootNode = {children: getModels(changes), nodeId: 0};

			if (!nodeId) {
				return rootNode;
			}

			let modelKey = null;

			if (typeof nodeId === 'string') {
				const parts = nodeId.split('-');

				if (parts.length !== 2) {
					return rootNode;
				}

				const modelClassNameId = parts[0];
				const modelClassPK = parts[1];

				const keys = Object.keys(modelsRef.current);

				for (let i = 0; i < keys.length; i++) {
					const model = modelsRef.current[keys[i]];

					if (
						model.modelClassNameId === modelClassNameId &&
						model.modelClassPK === modelClassPK
					) {
						if (!contextView) {
							return model;
						}

						modelKey = Number(keys[i]);

						break;
					}
				}

				if (modelKey === null) {
					return rootNode;
				}
			}

			if (!contextView) {
				const keys = Object.keys(modelsRef.current);

				for (let i = 0; i < keys.length; i++) {
					const model = modelsRef.current[keys[i]];

					if (model.nodeId === nodeId) {
						return model;
					}
				}

				return rootNode;
			}

			let node = null;
			const parentsMap = {};

			const stack = [contextViewRef.current.everything];

			while (stack.length) {
				const element = stack.pop();

				if (
					(modelKey !== null && element.modelKey === modelKey) ||
					element.nodeId === nodeId
				) {
					if (element.parentNodeId) {
						parentsMap[element.parentModelKey] =
							element.parentNodeId;
					}

					if (node === null) {
						node = JSON.parse(
							JSON.stringify(modelsRef.current[element.modelKey])
						);

						node.children = getModels(element.children);
						node.nodeId = element.nodeId;
					}

					if (modelKey === null) {
						modelKey = element.modelKey;
					}
				}

				if (!element.children) {
					continue;
				}

				for (let i = 0; i < element.children.length; i++) {
					const child = element.children[i];

					child.parentModelKey = element.modelKey;
					child.parentNodeId = element.nodeId;

					stack.push(child);
				}
			}

			if (node === null) {
				return rootNode;
			}

			const parents = [];

			const keys = Object.keys(parentsMap);

			for (let i = 0; i < keys.length; i++) {
				const modelKey = keys[i];

				const nodeId = parentsMap[modelKey];

				parents.push({
					modelKey,
					nodeId,
				});
			}

			node.parents = getModels(parents);

			return node;
		},
		[changes, contextView, getModels]
	);

	const initialNode = getNode(entryFromURL);

	const initialShowHideable = initialNode.hideable
		? true
		: !!showHideableFromURL;

	const ascendingState = orderByTypeFromURL !== ORDER_BY_TYPE_DESC;
	const columnState = columnFromURL ? columnFromURL : COLUMN_TITLE;

	const filterNodes = useCallback(
		(showHideable) => {
			const nodes = getModels(changes);

			return nodes.slice(0).filter((node) => {
				if (!showHideable && node.hideable) {
					return false;
				}
			});
		},
		[changes, getModels]
	);

	const initialDelta = deltaFromURL ? Number(deltaFromURL) : 20;
	const initialNodes = filterNodes(initialShowHideable);

	const calculatePage = (delta, page, total) => {
		const lastPage = total > 0 ? Math.ceil(total / delta) : 1;

		if (page > lastPage) {
			return lastPage;
		}

		return page;
	};

	const [renderState, setRenderState] = useState({
		changes: initialNodes,
		children: initialNode.children,
		delta: initialDelta,
		id: initialNode.nodeId,
		nav:
			!!ctMappingInfos.length &&
			(!total || navigationFromURL === NAVIGATION_RELATIONSHIPS)
				? NAVIGATION_RELATIONSHIPS
				: NAVIGATION_DATA,
		node: initialNode,
		page: calculatePage(
			initialDelta,
			pageFromURL ? Number(pageFromURL) : 1,
			initialNodes.length
		),
		parents: initialNode.parents,
		showHideable: initialShowHideable,
	});

	const getEntryParam = (node) => {
		if (node.modelClassNameId) {
			return node.modelClassNameId + '-' + node.modelClassPK;
		}

		return '';
	};

	const getPath = useCallback(
		(
			ascending,
			column,
			delta,
			entryParam,
			navigation,
			page,
			showHideable
		) => {
			let orderByType = ORDER_BY_TYPE_DESC;

			if (ascending) {
				orderByType = ORDER_BY_TYPE_ASC;
			}

			let path =
				basePathRef.current +
				'&' +
				PARAM_COLUMN +
				'=' +
				column +
				'&' +
				PARAM_DELTA +
				'=' +
				delta.toString() +
				'&' +
				PARAM_NAVIGATION +
				'=' +
				navigation +
				'&' +
				PARAM_ORDER_BY_TYPE +
				'=' +
				orderByType +
				'&' +
				PARAM_PAGE +
				'=' +
				page.toString() +
				'&' +
				PARAM_SHOW_HIDEABLE +
				'=' +
				showHideable.toString();

			if (entryParam) {
				path = path + '&' + PARAM_ENTRY + '=' + entryParam;
			}

			return path;
		},
		[
			PARAM_COLUMN,
			PARAM_DELTA,
			PARAM_ENTRY,
			PARAM_NAVIGATION,
			PARAM_ORDER_BY_TYPE,
			PARAM_PAGE,
			PARAM_SHOW_HIDEABLE,
		]
	);

	const pushState = (path) => {
		if (Liferay.SPA && Liferay.SPA.app) {
			Liferay.SPA.app.updateHistory_(
				document.title,
				path,
				{
					form: false,
					path,
					senna: true,
				},
				false
			);

			return;
		}

		window.history.pushState({path}, document.title, path);
	};

	const navigate = useCallback(
		(nodeId, resetPage) => {
			const node = getNode(nodeId);

			const page = resetPage ? 1 : renderState.page;

			pushState(
				getPath(
					ascendingState,
					columnState,
					renderState.delta,
					getEntryParam(node),
					renderState.nav,
					page,
					renderState.showHideable
				)
			);

			setRenderState({
				changes: filterNodes(renderState.showHideable),
				children: node.children,
				delta: renderState.delta,
				id: nodeId,
				nav: renderState.nav,
				node,
				page,
				parents: node.parents,
				showHideable: renderState.showHideable,
			});

			window.scrollTo(0, 0);
		},
		[
			ascendingState,
			columnState,
			filterNodes,
			getNode,
			getPath,
			renderState,
		]
	);

	const setParameter = useCallback(
		(url, name, value) => {
			return (
				url + '&' + namespace + name + '=' + encodeURIComponent(value)
			);
		},
		[namespace]
	);

	const getDataURL = (node) => {
		if (node.ctEntryId) {
			return setParameter(dataURL, 'ctEntryId', node.ctEntryId);
		}

		const url = setParameter(
			dataURL,
			'modelClassNameId',
			node.modelClassNameId
		);

		return setParameter(url, 'modelClassPK', node.modelClassPK);
	};

	const getDiscardURL = useCallback(
		(node) => {
			const url = setParameter(
				discardURL,
				'modelClassNameId',
				node.modelClassNameId
			);

			return setParameter(url, 'modelClassPK', node.modelClassPK);
		},
		[discardURL, setParameter]
	);

	const handleShowHideableToggle = (showHideable) => {
		const nodes = filterNodes(showHideable);

		const page = calculatePage(
			renderState.delta,
			renderState.id > 0 ? 1 : renderState.page,
			nodes.length
		);

		pushState(
			getPath(
				ascendingState,
				columnState,
				renderState.delta,
				getEntryParam(renderState.node),
				renderState.nav,
				page,
				showHideable
			)
		);

		setRenderState({
			changes: nodes,
			children: renderState.children,
			delta: renderState.delta,
			id: renderState.id,
			nav: renderState.nav,
			node: renderState.node,
			page,
			parents: renderState.parents,
			showHideable,
		});

		if (!showHideableFromURL) {
			window.location.reload();
		}
	};

	const renderExpiredBanner = () => {
		if (!expired) {
			return '';
		}

		return (
			<ClayAlert
				displayType="warning"
				spritemap={spritemap}
				title={Liferay.Language.get('out-of-date')}
			>
				{Liferay.Language.get(
					'this-publication-was-created-on-a-previous-liferay-version.-you-cannot-publish,-revert,-or-make-additional-changes'
				)}
			</ClayAlert>
		);
	};

	const renderMainContent = () => {
		if (!total && !ctMappingInfos.length) {
			return (
				<div className="container-fluid container-fluid-max-xl">
					{renderExpiredBanner()}

					<ClayLayout.Sheet>
						<ClayEmptyState
							className="mt-0"
							description={Liferay.Language.get(
								'no-changes-were-found'
							)}
							imgSrc={`${themeDisplay.getPathThemeImages()}/states/empty_state.gif`}
							title={Liferay.Language.get('no-results-found')}
						/>
					</ClayLayout.Sheet>
				</div>
			);
		}

		return (
			<div className="container-fluid container-fluid-max-xl">
				{renderExpiredBanner()}

				<div className="publications-changes-content row">
					<div className="col-md-12">
						{renderState.node.modelClassNameId && (
							<ChangeTrackingRenderView
								childEntries={renderState.children}
								ctEntry={!!renderState.node.ctEntryId}
								defaultLocale={defaultLocale}
								description={
									renderState.node.description
										? renderState.node.description
										: renderState.node.typeName
								}
								discardURL={getDiscardURL(renderState.node)}
								getCache={() =>
									renderCacheRef.current[
										renderState.node.modelClassNameId +
											'-' +
											renderState.node.modelClassPK
									]
								}
								handleNavigation={(nodeId) =>
									navigate(nodeId, true)
								}
								handleShowHideable={handleShowHideableToggle}
								initialDataURL={getDataURL(renderState.node)}
								parentEntries={renderState.parents}
								showDropdown={renderState.node.modelClassNameId}
								showHideable={renderState.showHideable}
								spritemap={spritemap}
								title={renderState.node.title}
								updateCache={(data) => {
									renderCacheRef.current[
										renderState.node.modelClassNameId +
											'-' +
											renderState.node.modelClassPK
									] = data;
								}}
							/>
						)}
					</div>
				</div>
			</div>
		);
	};

	return <>{renderMainContent()}</>;
}
