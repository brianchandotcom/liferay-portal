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

import ClayAlert from '@clayui/alert';
import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayCheckbox, ClayInput, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import ClayManagementToolbar, {
	ClayResultsBar,
} from '@clayui/management-toolbar';
import ClayNavigationBar from '@clayui/navigation-bar';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClaySticker from '@clayui/sticker';
import ClayTable from '@clayui/table';
import ClayToolbar from '@clayui/toolbar';
import classNames from 'classnames';
import React, {useCallback, useEffect, useRef, useState} from 'react';
import {CSSTransition} from 'react-transition-group';

import ChangeTrackingComments from './ChangeTrackingComments';
import ChangeTrackingRenderView from './ChangeTrackingRenderView';
import ManageCollaborators from './ManageCollaborators';

const DIRECTION_NEXT = 'next';
const DIRECTION_PREV = 'prev';

const DrilldownMenu = ({
	active,
	children,
	direction,
	header,
	onBack,
	spritemap,
}) => {
	const initialClasses = classNames('transitioning', {
		'drilldown-prev-initial': direction === DIRECTION_PREV,
	});

	return (
		<CSSTransition
			className={classNames('drilldown-item', {
				'drilldown-current': active,
			})}
			classNames={{
				enter: initialClasses,
				enterActive: `drilldown-transition drilldown-${direction}-active`,
				exit: initialClasses,
				exitActive: `drilldown-transition drilldown-${direction}-active`,
			}}
			in={active}
			timeout={250}
		>
			<div className="drilldown-item-inner">
				{header && (
					<>
						<div className="dropdown-header" onClick={onBack}>
							<ClayButtonWithIcon
								className="component-action dropdown-item-indicator-start"
								onClick={onBack}
								spritemap={spritemap}
								symbol="angle-left"
							/>

							<span className="dropdown-item-indicator-text-start">
								{header}
							</span>
						</div>

						<div className="dropdown-divider" />
					</>
				)}

				{children}
			</div>
		</CSSTransition>
	);
};

export default function ChangeTrackingChangesView({
	activeCTCollection,
	changeTypesFromURL,
	changes,
	collaboratorsData,
	columnFromURL,
	contextView,
	ctCollectionId,
	ctMappingInfos,
	currentUserId,
	dataURL,
	defaultLocale,
	deleteCTCommentURL,
	deltaFromURL,
	description,
	discardURL,
	dropdownItems,
	entryFromURL,
	expired,
	getCTCommentsURL,
	keywordsFromURL,
	modelData,
	name,
	namespace,
	navigationFromURL,
	orderByTypeFromURL,
	pageFromURL,
	publishURL,
	rescheduleURL,
	revertURL,
	rootDisplayClasses,
	scheduleURL,
	showHideableFromURL,
	siteNames,
	sitesFromURL,
	spritemap,
	statusLabel,
	statusStyle,
	typeNames,
	typesFromURL,
	unscheduleURL,
	updateCTCommentURL,
	userInfo,
	usersFromURL,
}) {
	const CHANGE_TYPE_ADDITION = 0;
	const CHANGE_TYPE_DELETION = 1;
	const CHANGE_TYPE_MODIFICATION = 2;
	const COLUMN_CHANGE_TYPE = 'changeType';
	const COLUMN_MODIFIED_DATE = 'modifiedDate';
	const COLUMN_SITE = 'site';
	const COLUMN_TITLE = 'title';
	const COLUMN_USER = 'user';
	const GLOBAL_SITE_NAME = Liferay.Language.get('global');
	const MENU_CHANGE_TYPES = 'MENU_CHANGE_TYPES';
	const MENU_ROOT = 'MENU_ROOT';
	const MENU_SITES = 'MENU_SITES';
	const MENU_TYPES = 'MENU_TYPES';
	const MENU_USERS = 'MENU_USERS';
	const MVC_RENDER_COMMAND_NAME = '/change_tracking/view_changes';
	const NAVIGATION_DATA = 'data';
	const NAVIGATION_RELATIONSHIPS = 'relationships';
	const PARAM_CHANGE_TYPES = namespace + 'changeTypes';
	const PARAM_COLUMN = namespace + 'column';
	const PARAM_DELTA = namespace + 'delta';
	const PARAM_CT_COLLECTION_ID = namespace + 'ctCollectionId';
	const PARAM_ENTRY = namespace + 'entry';
	const PARAM_KEYWORDS = namespace + 'keywords';
	const PARAM_MVC_RENDER_COMMAND_NAME = namespace + 'mvcRenderCommandName';
	const PARAM_NAVIGATION = namespace + 'navigation';
	const PARAM_ORDER_BY_TYPE = namespace + 'orderByType';
	const PARAM_PAGE = namespace + 'page';
	const PARAM_SHOW_HIDEABLE = namespace + 'showHideable';
	const PARAM_SITES = namespace + 'sites';
	const PARAM_TYPES = namespace + 'types';
	const PARAM_USERS = namespace + 'users';
	const POP_STATE = 'popstate';
	const ORDER_BY_TYPE_ASC = 'asc';
	const ORDER_BY_TYPE_DESC = 'desc';

	const isWithinApp = useCallback(
		(params) => {
			const ctCollectionIdParam = params.get(PARAM_CT_COLLECTION_ID);
			const mvcRenderCommandName = params.get(
				PARAM_MVC_RENDER_COMMAND_NAME
			);

			if (
				ctCollectionIdParam &&
				ctCollectionIdParam === ctCollectionId.toString() &&
				mvcRenderCommandName &&
				mvcRenderCommandName === MVC_RENDER_COMMAND_NAME
			) {
				return true;
			}

			return false;
		},
		[
			MVC_RENDER_COMMAND_NAME,
			PARAM_CT_COLLECTION_ID,
			PARAM_MVC_RENDER_COMMAND_NAME,
			ctCollectionId,
		]
	);

	const pathname = window.location.pathname;

	const search = window.location.search;

	const params = new URLSearchParams(search);

	params.delete(PARAM_CHANGE_TYPES);
	params.delete(PARAM_COLUMN);
	params.delete(PARAM_DELTA);
	params.delete(PARAM_ENTRY);
	params.delete(PARAM_KEYWORDS);
	params.delete(PARAM_NAVIGATION);
	params.delete(PARAM_ORDER_BY_TYPE);
	params.delete(PARAM_PAGE);
	params.delete(PARAM_SHOW_HIDEABLE);
	params.delete(PARAM_SITES);
	params.delete(PARAM_TYPES);
	params.delete(PARAM_USERS);

	const basePathRef = useRef(pathname + '?' + params.toString());

	const commentsCacheRef = useRef({});
	const renderCacheRef = useRef({});

	const getNodeId = useCallback(
		(modelKey) => {
			if (!contextView) {
				return modelKey;
			}

			const stack = [contextView.everything];

			while (stack.length > 0) {
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
				model.changeTypeLabel = Liferay.Language.get('modified');

				if (model.changeType === CHANGE_TYPE_ADDITION) {
					model.changeTypeLabel = Liferay.Language.get('added');
				}
				else if (model.changeType === CHANGE_TYPE_DELETION) {
					model.changeTypeLabel = Liferay.Language.get('deleted');
				}

				model.portraitURL = userInfo[model.userId].portraitURL;
				model.userName = userInfo[model.userId].userName;

				if (model.siteName === GLOBAL_SITE_NAME) {
					let key = Liferay.Language.get('x-modified-a-x-x-ago');

					if (model.changeType === CHANGE_TYPE_ADDITION) {
						key = Liferay.Language.get('x-added-a-x-x-ago');
					}
					else if (model.changeType === CHANGE_TYPE_DELETION) {
						key = Liferay.Language.get('x-deleted-a-x-x-ago');
					}

					model.description = Liferay.Util.sub(
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

					model.description = Liferay.Util.sub(
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

	const typesRef = useRef(null);

	if (typesRef.current === null) {
		typesRef.current = {};

		const ctEntryTypes = [];
		const nonHideableTypes = [];

		const modelKeys = Object.keys(modelsRef.current);

		for (let i = 0; i < modelKeys.length; i++) {
			const model = modelsRef.current[modelKeys[i]];

			if (model.ctEntryId && !ctEntryTypes.includes(model.typeName)) {
				ctEntryTypes.push(model.typeName);
			}

			if (!model.hideable && !nonHideableTypes.includes(model.typeName)) {
				nonHideableTypes.push(model.typeName);
			}
		}

		const typeNameKeys = Object.keys(typeNames);

		for (let i = 0; i < typeNameKeys.length; i++) {
			const typeName = typeNames[typeNameKeys[i]];

			typesRef.current[typeNameKeys[i]] = {
				ctEntry: !!ctEntryTypes.includes(typeName),
				hideable: !nonHideableTypes.includes(typeName),
				label: typeName.replace(/.*\./g, ''),
				name: typeName,
			};
		}
	}

	const contextViewRef = useRef(null);

	if (contextView && contextViewRef.current === null) {
		contextViewRef.current = JSON.parse(JSON.stringify(contextView));

		for (let i = 0; i < rootDisplayClasses.length; i++) {
			const className = rootDisplayClasses[i];

			const rootClass = contextViewRef.current[className];

			const keys = Object.keys(typesRef.current);

			for (let j = 0; j < keys.length; j++) {
				const type = typesRef.current[keys[j]];

				if (type.name === className && type.hideable) {
					rootClass.hideable = true;

					break;
				}
			}
		}
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

			while (stack.length > 0) {
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

	const [ascendingState, setAscendingState] = useState(
		orderByTypeFromURL !== ORDER_BY_TYPE_DESC
	);
	const [columnState, setColumnState] = useState(
		columnFromURL ? columnFromURL : COLUMN_TITLE
	);
	const [drilldownDirection, setDrilldownDirection] = useState(
		DIRECTION_NEXT
	);
	const [dropdownActive, setDropdownActive] = useState(false);
	const [entrySearchTerms, setEntrySearchTerms] = useState(keywordsFromURL);
	const [filterSearchTerms, setFilterSearchTerms] = useState('');
	const [menu, setMenu] = useState(MENU_ROOT);
	const [resultsKeywords, setResultsKeywords] = useState(keywordsFromURL);
	const [searchMobile, setSearchMobile] = useState(false);
	const [showComments, setShowComments] = useState(false);

	const getFilters = useCallback(
		(changeTypes, sites, types, users) => {
			let changeTypeIds = [];

			if (changeTypes) {
				changeTypeIds = changeTypes.split(',').map((id) => Number(id));
			}

			let siteIds = [];

			if (sites) {
				siteIds = sites
					.split(',')
					.map((id) => Number(id))
					.filter((id) => id === 0 || !!siteNames[id]);
			}

			let typeIds = [];

			if (types) {
				typeIds = types
					.split(',')
					.filter((id) => !!typesRef.current[id])
					.map((id) => Number(id));
			}

			let userIds = [];

			if (users) {
				userIds = users
					.split(',')
					.filter((id) => !!userInfo[id])
					.map((id) => Number(id));
			}

			return {
				changeTypes: changeTypeIds,
				sites: siteIds,
				types: typeIds,
				users: userIds,
			};
		},
		[siteNames, userInfo]
	);

	const initialFilters = getFilters(
		changeTypesFromURL,
		sitesFromURL,
		typesFromURL,
		usersFromURL
	);

	const [filtersState, setFiltersState] = useState(initialFilters);

	const filterNodes = useCallback(
		(filters, keywords, showHideable) => {
			const nodes = getModels(changes);

			let filterTypes = [];

			const typeIds = filters['types'];

			if (typeIds && typeIds.length > 0) {
				filterTypes = typeIds
					.map((typeId) => typesRef.current[typeId])
					.filter((type) => showHideable || !type.hideable)
					.map((type) => type.name);
			}

			let pattern = null;

			if (keywords) {
				pattern = keywords
					.toLowerCase()
					.replace(/[^0-9a-z]+/g, '|')
					.replace(/^\||\|$/g, '');
			}

			return nodes.slice(0).filter((node) => {
				if (!showHideable && node.hideable) {
					return false;
				}

				const changeTypes = filters['changeTypes'];

				if (
					changeTypes.length > 0 &&
					!changeTypes.includes(node.changeType)
				) {
					return false;
				}

				if (
					filterTypes.length > 0 &&
					!filterTypes.includes(node.typeName)
				) {
					return false;
				}

				const siteIds = filters['sites'];

				if (siteIds.length > 0) {
					let groupId = Number(node.groupId);

					if (groupId > 0) {
						const siteName = siteNames[groupId];

						if (siteName === GLOBAL_SITE_NAME) {
							groupId = 0;
						}
					}

					if (!siteIds.includes(groupId)) {
						return false;
					}
				}

				const userIds = filters['users'];

				if (
					userIds.length > 0 &&
					!userIds.includes(Number(node.userId))
				) {
					return false;
				}

				if (
					pattern &&
					(!node.title || !node.title.toLowerCase().match(pattern))
				) {
					return false;
				}

				return true;
			});
		},
		[GLOBAL_SITE_NAME, changes, getModels, siteNames]
	);

	const initialDelta = deltaFromURL ? Number(deltaFromURL) : 20;
	const initialNodes = filterNodes(
		initialFilters,
		keywordsFromURL,
		initialShowHideable
	);

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
			ctMappingInfos.length > 0 &&
			(changes.length === 0 ||
				navigationFromURL === NAVIGATION_RELATIONSHIPS)
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
			filters,
			keywords,
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

			const changeTypes = filters['changeTypes'];

			if (changeTypes && changeTypes.length > 0) {
				path =
					path +
					'&' +
					PARAM_CHANGE_TYPES +
					'=' +
					changeTypes.join(',');
			}

			if (entryParam) {
				path = path + '&' + PARAM_ENTRY + '=' + entryParam;
			}

			if (keywords) {
				path = path + '&' + PARAM_KEYWORDS + '=' + keywords.toString();
			}

			const siteIds = filters['sites'];

			if (siteIds && siteIds.length > 0) {
				path = path + '&' + PARAM_SITES + '=' + siteIds.join(',');
			}

			const typeIds = filters['types'];

			if (typeIds && typeIds.length > 0) {
				path = path + '&' + PARAM_TYPES + '=' + typeIds.join(',');
			}

			const userIds = filters['users'];

			if (userIds && userIds.length > 0) {
				path = path + '&' + PARAM_USERS + '=' + userIds.join(',');
			}

			return path;
		},
		[
			PARAM_CHANGE_TYPES,
			PARAM_COLUMN,
			PARAM_DELTA,
			PARAM_ENTRY,
			PARAM_KEYWORDS,
			PARAM_NAVIGATION,
			PARAM_ORDER_BY_TYPE,
			PARAM_PAGE,
			PARAM_SHOW_HIDEABLE,
			PARAM_SITES,
			PARAM_TYPES,
			PARAM_USERS,
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
					filtersState,
					resultsKeywords,
					renderState.nav,
					page,
					renderState.showHideable
				)
			);

			setRenderState({
				changes: filterNodes(
					filtersState,
					resultsKeywords,
					renderState.showHideable
				),
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
			filtersState,
			filterNodes,
			getNode,
			getPath,
			renderState,
			resultsKeywords,
		]
	);

	const handlePopState = useCallback(
		(event) => {
			const state = event.state;

			let search = window.location.search;

			if (state) {
				const index = state.path.indexOf('?');

				if (index < 0) {
					if (Liferay.SPA && Liferay.SPA.app) {
						Liferay.SPA.app.skipLoadPopstate = false;

						Liferay.SPA.app.navigate(window.location.href, true);
					}

					return;
				}

				search = state.path.substring(index);
			}

			const params = new URLSearchParams(search);

			if (!isWithinApp(params)) {
				if (Liferay.SPA && Liferay.SPA.app) {
					Liferay.SPA.app.skipLoadPopstate = false;

					Liferay.SPA.app.navigate(window.location.href, true);
				}

				return;
			}

			const node = getNode(params.get(PARAM_ENTRY));

			let ascending = params.get(PARAM_ORDER_BY_TYPE);

			if (ascending === ORDER_BY_TYPE_DESC) {
				ascending = false;
			}
			else {
				ascending = true;
			}

			let column = params.get(PARAM_COLUMN);

			if (!column) {
				column = COLUMN_TITLE;
			}

			let delta = params.get(PARAM_DELTA);

			if (delta) {
				delta = Number(delta);
			}
			else {
				delta = 20;
			}

			let keywords = params.get(PARAM_KEYWORDS);

			if (!keywords) {
				keywords = '';
			}

			let navigation = params.get(PARAM_NAVIGATION);

			if (
				ctMappingInfos.length > 0 &&
				(changes.length === 0 ||
					navigation === NAVIGATION_RELATIONSHIPS)
			) {
				navigation = NAVIGATION_RELATIONSHIPS;
			}
			else {
				navigation = NAVIGATION_DATA;
			}

			let page = params.get(PARAM_PAGE);

			if (page) {
				page = Number(page);
			}
			else {
				page = 1;
			}

			const filters = getFilters(
				params.get(PARAM_CHANGE_TYPES),
				params.get(PARAM_SITES),
				params.get(PARAM_TYPES),
				params.get(PARAM_USERS)
			);

			let showHideable = params.get(PARAM_SHOW_HIDEABLE);

			if (showHideable === 'true') {
				showHideable = true;
			}
			else {
				showHideable = false;
			}

			if (!showHideable) {
				const typeIds = filters['types'];

				if (typeIds) {
					showHideable = !!typeIds.find((typeId) => {
						const type = typesRef.current[typeId];

						return type.hideable;
					});
				}
			}

			const nodes = filterNodes(filters, keywords, showHideable);

			setAscendingState(ascending);
			setColumnState(column);
			setEntrySearchTerms(keywords);
			setFiltersState(filters);
			setRenderState({
				changes: nodes,
				children: node.children,
				delta,
				id: node.nodeId,
				nav: navigation,
				node,
				page: calculatePage(delta, page, nodes.length),
				parents: node.parents,
				showHideable,
			});
			setResultsKeywords(keywords);
		},
		[
			PARAM_CHANGE_TYPES,
			PARAM_COLUMN,
			PARAM_DELTA,
			PARAM_ENTRY,
			PARAM_KEYWORDS,
			PARAM_NAVIGATION,
			PARAM_ORDER_BY_TYPE,
			PARAM_PAGE,
			PARAM_SHOW_HIDEABLE,
			PARAM_SITES,
			PARAM_TYPES,
			PARAM_USERS,
			changes,
			ctMappingInfos,
			filterNodes,
			getFilters,
			getNode,
			isWithinApp,
		]
	);

	useEffect(() => {
		window.addEventListener(POP_STATE, handlePopState);

		if (Liferay.SPA && Liferay.SPA.app) {
			Liferay.SPA.app.skipLoadPopstate = true;
		}

		return () => {
			window.removeEventListener(POP_STATE, handlePopState);

			if (Liferay.SPA && Liferay.SPA.app) {
				Liferay.SPA.app.skipLoadPopstate = false;
			}
		};
	}, [handlePopState]);

	const filterDisplayNodes = (nodes) => {
		if (columnState === COLUMN_CHANGE_TYPE) {
			nodes.sort((a, b) => {
				if (a.changeType < b.changeType) {
					if (ascendingState) {
						return -1;
					}

					return 1;
				}

				if (a.changeType > b.changeType) {
					if (ascendingState) {
						return 1;
					}

					return -1;
				}

				const typeNameA = a.typeName.toLowerCase();
				const typeNameB = b.typeName.toLowerCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				const titleA = a.title.toLowerCase();
				const titleB = b.title.toLowerCase();

				if (titleA < titleB) {
					return -1;
				}

				if (titleA > titleB) {
					return 1;
				}

				return 0;
			});
		}
		else if (columnState === COLUMN_SITE) {
			nodes.sort((a, b) => {
				const siteNameA = a.siteName.toLowerCase();
				const siteNameB = b.siteName.toLowerCase();

				if (
					siteNameA < siteNameB ||
					(a.siteName === GLOBAL_SITE_NAME &&
						b.siteName !== GLOBAL_SITE_NAME)
				) {
					if (ascendingState) {
						return -1;
					}

					return 1;
				}

				if (
					siteNameA > siteNameB ||
					(a.siteName !== GLOBAL_SITE_NAME &&
						b.siteName === GLOBAL_SITE_NAME)
				) {
					if (ascendingState) {
						return 1;
					}

					return -1;
				}

				const typeNameA = a.typeName.toLowerCase();
				const typeNameB = b.typeName.toLowerCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				const titleA = a.title.toLowerCase();
				const titleB = b.title.toLowerCase();

				if (titleA < titleB) {
					return -1;
				}

				if (titleA > titleB) {
					return 1;
				}

				return 0;
			});
		}
		else if (columnState === COLUMN_TITLE) {
			nodes.sort((a, b) => {
				const typeNameA = a.typeName.toLowerCase();
				const typeNameB = b.typeName.toLowerCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				const titleA = a.title.toLowerCase();
				const titleB = b.title.toLowerCase();

				if (titleA < titleB) {
					if (ascendingState) {
						return -1;
					}

					return 1;
				}

				if (titleA > titleB) {
					if (ascendingState) {
						return 1;
					}

					return -1;
				}

				return 0;
			});
		}
		else if (columnState === COLUMN_USER) {
			nodes.sort((a, b) => {
				const userNameA = a.userName.toLowerCase();
				const userNameB = b.userName.toLowerCase();

				if (userNameA < userNameB) {
					if (ascendingState) {
						return -1;
					}

					return 1;
				}

				if (userNameA > userNameB) {
					if (ascendingState) {
						return 1;
					}

					return -1;
				}

				const typeNameA = a.typeName.toLowerCase();
				const typeNameB = b.typeName.toLowerCase();

				if (typeNameA < typeNameB) {
					return -1;
				}

				if (typeNameA > typeNameB) {
					return 1;
				}

				const titleA = a.title.toLowerCase();
				const titleB = b.title.toLowerCase();

				if (titleA < titleB) {
					return -1;
				}

				if (titleA > titleB) {
					return 1;
				}

				return 0;
			});
		}
		else {
			nodes.sort((a, b) => {
				if (a.modifiedTime < b.modifiedTime) {
					if (ascendingState) {
						return -1;
					}

					return 1;
				}

				if (a.modifiedTime > b.modifiedTime) {
					if (ascendingState) {
						return 1;
					}

					return -1;
				}

				return 0;
			});
		}

		if (nodes.length > 5) {
			return nodes.slice(
				renderState.delta * (renderState.page - 1),
				renderState.delta * renderState.page
			);
		}

		return nodes;
	};

	const getColumnHeader = (column, title) => {
		let orderListIcon = 'order-list-up';

		if (ascendingState) {
			orderListIcon = 'order-list-down';
		}

		return (
			<ClayButton
				className={columnState === column ? '' : 'text-secondary'}
				displayType="unstyled"
				onClick={() => {
					if (columnState === column) {
						pushState(
							getPath(
								!ascendingState,
								columnState,
								renderState.delta,
								getEntryParam(renderState.node),
								filtersState,
								resultsKeywords,
								renderState.nav,
								renderState.page,
								renderState.showHideable
							)
						);

						setAscendingState(!ascendingState);

						return;
					}

					pushState(
						getPath(
							ascendingState,
							column,
							renderState.delta,
							getEntryParam(renderState.node),
							filtersState,
							resultsKeywords,
							renderState.nav,
							renderState.page,
							renderState.showHideable
						)
					);

					setColumnState(column);
				}}
			>
				{title}

				<span
					className={classNames('inline-item inline-item-after', {
						'text-muted': columnState !== column,
					})}
				>
					<ClayIcon
						spritemap={spritemap}
						symbol={
							columnState === column
								? orderListIcon
								: 'order-arrow'
						}
					/>
				</span>
			</ClayButton>
		);
	};

	const toggleFilter = (name, id) => {
		const filters = JSON.parse(JSON.stringify(filtersState));

		const ids = filters[name];

		if (ids.includes(id)) {
			filters[name] = ids.filter((item) => id !== item);
		}
		else {
			ids.push(id);
		}

		handleFiltersUpdate(filters, resultsKeywords);
	};

	const getFilterList = (items, name) => {
		const checkedIds = filtersState[name];

		const pattern = filterSearchTerms
			.toLowerCase()
			.replace(/[^0-9a-z]+/g, '|')
			.replace(/^\||\|$/g, '');

		return (
			<ClayDropDown.Group>
				{items
					.filter((item) => {
						if (
							filterSearchTerms &&
							!item.label.toLowerCase().match(pattern)
						) {
							return false;
						}

						return true;
					})
					.sort((a, b) => {
						if (a.label < b.label) {
							return -1;
						}

						if (a.label > b.label) {
							return 1;
						}

						return 0;
					})
					.map((item) => (
						<ClayDropDown.Section key={item.id}>
							<ClayCheckbox
								checked={checkedIds.includes(item.id)}
								label={item.label}
								onChange={() => toggleFilter(name, item.id)}
							/>
						</ClayDropDown.Section>
					))}
			</ClayDropDown.Group>
		);
	};

	const getChangeTypesFilterList = () => {
		const items = [
			{
				id: CHANGE_TYPE_ADDITION,
				label: Liferay.Language.get('added'),
			},
			{
				id: CHANGE_TYPE_DELETION,
				label: Liferay.Language.get('deleted'),
			},
			{
				id: CHANGE_TYPE_MODIFICATION,
				label: Liferay.Language.get('modified'),
			},
		];

		return getFilterList(items, 'changeTypes');
	};

	const getSitesFilterList = () => {
		const sites = [];

		let hasGlobal = false;

		const siteIds = Object.keys(siteNames);

		for (let i = 0; i < siteIds.length; i++) {
			const label = siteNames[siteIds[i]];

			if (label === GLOBAL_SITE_NAME) {
				if (!hasGlobal) {
					sites.push({
						id: 0,
						label: GLOBAL_SITE_NAME,
					});

					hasGlobal = true;
				}

				continue;
			}

			sites.push({
				id: Number(siteIds[i]),
				label,
			});
		}

		return getFilterList(sites, 'sites');
	};

	const getTypesFilterList = () => {
		const types = [];

		const keys = Object.keys(typesRef.current);

		for (let i = 0; i < keys.length; i++) {
			const type = typesRef.current[keys[i]];

			if (type.ctEntry && (renderState.showHideable || !type.hideable)) {
				types.push({
					id: Number(keys[i]),
					label: type.label,
				});
			}
		}

		return getFilterList(types, 'types');
	};

	const getUsersFilterList = () => {
		const users = [];

		const userIds = Object.keys(userInfo);

		for (let i = 0; i < userIds.length; i++) {
			const user = userInfo[userIds[i]];

			users.push({
				id: Number(userIds[i]),
				label: user.userName,
			});
		}

		return getFilterList(users, 'users');
	};

	const getDrilldownRootItem = (label, value) => {
		return (
			<ClayButton
				className="dropdown-item"
				displayType="unstyled"
				onClick={() => {
					setMenu(value);
					setDrilldownDirection(DIRECTION_NEXT);
					setFilterSearchTerms('');
				}}
			>
				<span className="dropdown-item-indicator-text-end">
					{label}
				</span>

				<span className="dropdown-item-indicator-end">
					<ClayIcon spritemap={spritemap} symbol="angle-right" />
				</span>
			</ClayButton>
		);
	};

	const getDrilldownMenu = (
		getFilterListFunction,
		header,
		showSearch,
		value
	) => {
		return (
			<DrilldownMenu
				active={menu === value}
				direction={drilldownDirection}
				header={header}
				onBack={() => {
					setMenu(MENU_ROOT);
					setDrilldownDirection(DIRECTION_PREV);
					setFilterSearchTerms('');
				}}
				spritemap={spritemap}
			>
				{showSearch && (
					<div className="dropdown-section">
						<ClayInput.Group small>
							<ClayInput.GroupItem>
								<ClayInput
									insetAfter
									onChange={(event) =>
										setFilterSearchTerms(event.target.value)
									}
									placeholder={`${Liferay.Language.get(
										'search'
									)}...`}
									type="text"
									value={filterSearchTerms}
								/>

								<ClayInput.GroupInsetItem after tag="span">
									{filterSearchTerms ? (
										<ClayButton
											displayType="unstyled"
											onClick={() =>
												setFilterSearchTerms('')
											}
											type="button"
										>
											<ClayIcon
												spritemap={spritemap}
												symbol="times-circle"
											/>
										</ClayButton>
									) : (
										<ClayButton
											displayType="unstyled"
											type="button"
										>
											<ClayIcon
												spritemap={spritemap}
												symbol="search"
											/>
										</ClayButton>
									)}
								</ClayInput.GroupInsetItem>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</div>
				)}

				<div className="inline-scroller">
					<ClayDropDown.ItemList>
						{getFilterListFunction()}
					</ClayDropDown.ItemList>
				</div>
			</DrilldownMenu>
		);
	};

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
			const url = setParameter(
				dataURL,
				'activeCTCollection',
				activeCTCollection.toString()
			);

			return setParameter(url, 'ctEntryId', node.ctEntryId);
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

	const getTableRows = (nodes) => {
		const rows = [];

		if (!nodes) {
			return rows;
		}

		let currentTypeName = '';

		for (let i = 0; i < nodes.length; i++) {
			const node = nodes[i];

			if (node.typeName !== currentTypeName) {
				currentTypeName = node.typeName;

				rows.push(
					<ClayTable.Row divider>
						<ClayTable.Cell colSpan={5}>
							{node.typeName}
						</ClayTable.Cell>
					</ClayTable.Row>
				);
			}

			rows.push(
				<ClayTable.Row
					className="cursor-pointer"
					onClick={() => navigate(node.nodeId)}
				>
					<ClayTable.Cell>
						<ClaySticker
							className={`sticker-user-icon ${
								node.portraitURL
									? ''
									: 'user-icon-color-' + (node.userId % 10)
							}`}
							data-tooltip-align="top"
							title={node.userName}
						>
							{node.portraitURL ? (
								<div className="sticker-overlay">
									<img
										className="sticker-img"
										src={node.portraitURL}
									/>
								</div>
							) : (
								<ClayIcon symbol="user" />
							)}
						</ClaySticker>
					</ClayTable.Cell>

					<ClayTable.Cell>{node.siteName}</ClayTable.Cell>

					<ClayTable.Cell className="publication-name table-cell-expand">
						{node.title}
					</ClayTable.Cell>

					<ClayTable.Cell className="table-cell-expand-smallest">
						{node.changeTypeLabel}
					</ClayTable.Cell>

					<ClayTable.Cell className="table-cell-expand-smallest">
						{Liferay.Util.sub(
							Liferay.Language.get('x-ago'),
							node.timeDescription
						)}
					</ClayTable.Cell>
				</ClayTable.Row>
			);
		}

		return rows;
	};

	const handleFiltersUpdate = (filters, keywords) => {
		const nodes = filterNodes(filters, keywords, renderState.showHideable);

		const page = calculatePage(
			renderState.delta,
			renderState.page,
			nodes.length
		);

		pushState(
			getPath(
				ascendingState,
				columnState,
				renderState.delta,
				getEntryParam(renderState.node),
				filters,
				keywords,
				renderState.nav,
				page,
				renderState.showHideable
			)
		);

		setFiltersState(filters);
		setResultsKeywords(keywords);
		setRenderState({
			changes: nodes,
			children: renderState.children,
			delta: renderState.delta,
			id: renderState.id,
			nav: renderState.nav,
			node: renderState.node,
			page,
			parents: renderState.parents,
			showHideable: renderState.showHideable,
		});

		window.scrollTo(0, 0);
	};

	const handleNavigationUpdate = (navigation) => {
		const filters = {changeTypes: [], sites: [], types: [], users: []};
		const node = getNode(0);

		pushState(
			getPath(
				ascendingState,
				columnState,
				renderState.delta,
				getEntryParam(node),
				filters,
				'',
				navigation,
				1,
				renderState.showHideable
			)
		);

		setEntrySearchTerms('');
		setFiltersState(filters);
		setRenderState({
			changes: filterNodes(filters, '', renderState.showHideable),
			children: node.children,
			delta: renderState.delta,
			id: 0,
			nav: navigation,
			node,
			page: 1,
			parents: node.parents,
			showHideable: renderState.showHideable,
		});
		setResultsKeywords('');

		window.scrollTo(0, 0);
	};

	const handleShowHideableToggle = (showHideable) => {
		const filters = JSON.parse(JSON.stringify(filtersState));

		if (!showHideable) {
			const typeIds = filters['types'];

			if (typeIds && typeIds.length > 0) {
				filters['types'] = typeIds.filter(
					(typeId) => !typesRef.current[typeId].hideable
				);
			}
		}

		const nodes = filterNodes(filters, resultsKeywords, showHideable);

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
				filters,
				resultsKeywords,
				renderState.nav,
				page,
				showHideable
			)
		);

		setFiltersState(filters);
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

	const renderFilterDropdown = () => {
		if (renderState.id > 0) {
			return '';
		}

		return (
			<ClayManagementToolbar.ItemList>
				<ClayManagementToolbar.Item>
					<ClayDropDown
						active={dropdownActive}
						menuElementAttrs={{
							className:
								'drilldown publications-filter-dropdown-menu',
						}}
						onActiveChange={(value) => {
							if (!value) {
								setMenu(MENU_ROOT);
								setFilterSearchTerms('');
							}

							setDropdownActive(value);
						}}
						spritemap={spritemap}
						trigger={
							<ClayButton
								className="nav-link"
								disabled={changes.length === 0}
								displayType="unstyled"
							>
								<span className="navbar-breakpoint-down-d-none">
									<span className="navbar-text-truncate">
										{Liferay.Language.get('filter-by')}
									</span>

									<ClayIcon
										className="inline-item inline-item-after"
										spritemap={spritemap}
										symbol="caret-bottom"
									/>
								</span>

								<span className="navbar-breakpoint-d-none">
									<ClayIcon
										spritemap={spritemap}
										symbol="filter"
									/>
								</span>
							</ClayButton>
						}
					>
						<form
							onSubmit={(event) => {
								event.preventDefault();
							}}
						>
							<div className="drilldown-inner">
								<DrilldownMenu
									active={menu === MENU_ROOT}
									direction={drilldownDirection}
									spritemap={spritemap}
								>
									{getDrilldownRootItem(
										Liferay.Language.get('change-types'),
										MENU_CHANGE_TYPES
									)}

									{getDrilldownRootItem(
										Liferay.Language.get('sites'),
										MENU_SITES
									)}

									{getDrilldownRootItem(
										Liferay.Language.get('types'),
										MENU_TYPES
									)}

									{getDrilldownRootItem(
										Liferay.Language.get('users'),
										MENU_USERS
									)}
								</DrilldownMenu>

								{getDrilldownMenu(
									getChangeTypesFilterList,
									Liferay.Language.get('change-types'),
									false,
									MENU_CHANGE_TYPES
								)}

								{getDrilldownMenu(
									getSitesFilterList,
									Liferay.Language.get('sites'),
									true,
									MENU_SITES
								)}

								{getDrilldownMenu(
									getTypesFilterList,
									Liferay.Language.get('types'),
									true,
									MENU_TYPES
								)}

								{getDrilldownMenu(
									getUsersFilterList,
									Liferay.Language.get('users'),
									true,
									MENU_USERS
								)}
							</div>
						</form>
					</ClayDropDown>
				</ClayManagementToolbar.Item>
			</ClayManagementToolbar.ItemList>
		);
	};

	const renderManagementToolbar = () => {
		return (
			<ClayManagementToolbar>
				{renderFilterDropdown()}

				{renderState.id > 0 ? (
					<ClayManagementToolbar.ItemList expand />
				) : (
					<ClayManagementToolbar.Search
						onSubmit={(event) => {
							event.preventDefault();

							handleFiltersUpdate(
								filtersState,
								entrySearchTerms.trim()
							);
						}}
						showMobile={searchMobile}
					>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayInput
									aria-label={Liferay.Language.get('search')}
									className="form-control input-group-inset input-group-inset-after"
									disabled={changes.length === 0}
									onChange={(event) =>
										setEntrySearchTerms(event.target.value)
									}
									placeholder={`${Liferay.Language.get(
										'search'
									)}...`}
									type="text"
									value={entrySearchTerms}
								/>

								<ClayInput.GroupInsetItem after tag="span">
									<ClayButtonWithIcon
										className="navbar-breakpoint-d-none"
										disabled={changes.length === 0}
										displayType="unstyled"
										onClick={() => setSearchMobile(false)}
										spritemap={spritemap}
										symbol="times"
									/>

									<ClayButtonWithIcon
										disabled={changes.length === 0}
										displayType="unstyled"
										spritemap={spritemap}
										symbol="search"
										type="submit"
									/>
								</ClayInput.GroupInsetItem>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayManagementToolbar.Search>
				)}

				<ClayManagementToolbar.ItemList>
					{renderState.id === 0 && (
						<ClayManagementToolbar.Item className="navbar-breakpoint-d-none">
							<ClayButton
								className="nav-link nav-link-monospaced"
								disabled={changes.length === 0}
								displayType="unstyled"
								onClick={() => setSearchMobile(true)}
							>
								<ClayIcon
									spritemap={spritemap}
									symbol="search"
								/>
							</ClayButton>
						</ClayManagementToolbar.Item>
					)}

					<ClayManagementToolbar.Item className="simple-toggle-switch-reverse">
						<ClayToggle
							disabled={changes.length === 0}
							label={Liferay.Language.get('show-all-items')}
							onToggle={(showHideable) =>
								handleShowHideableToggle(showHideable)
							}
							toggled={renderState.showHideable}
						/>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>
		);
	};

	const renderNavigationBar = () => {
		return (
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell
						className="publications-header-td"
						colSpan={5}
					>
						<ClayNavigationBar spritemap={spritemap}>
							<ClayNavigationBar.Item
								active={renderState.nav === NAVIGATION_DATA}
							>
								<ClayLink
									className={
										changes.length === 0
											? 'nav-link btn-link disabled'
											: 'nav-link'
									}
									displayType="unstyled"
									onClick={() =>
										handleNavigationUpdate(NAVIGATION_DATA)
									}
								>
									{Liferay.Language.get('data')}
								</ClayLink>
							</ClayNavigationBar.Item>

							<ClayNavigationBar.Item
								active={
									renderState.nav === NAVIGATION_RELATIONSHIPS
								}
							>
								<ClayLink
									className={
										ctMappingInfos.length === 0
											? 'nav-link btn-link disabled'
											: 'nav-link'
									}
									displayType="unstyled"
									onClick={() =>
										handleNavigationUpdate(
											NAVIGATION_RELATIONSHIPS
										)
									}
								>
									{Liferay.Language.get('relationships')}
								</ClayLink>
							</ClayNavigationBar.Item>
						</ClayNavigationBar>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>
		);
	};

	const renderPaginationBar = () => {
		if (!renderState.changes || renderState.changes.length <= 5) {
			return '';
		}

		return (
			<ClayPaginationBarWithBasicItems
				activeDelta={renderState.delta}
				activePage={calculatePage(
					renderState.delta,
					renderState.page,
					renderState.changes.length
				)}
				deltas={[4, 8, 20, 40, 60].map((size) => ({
					label: size,
				}))}
				ellipsisBuffer={3}
				onDeltaChange={(delta) => {
					if (delta === renderState.delta) {
						return;
					}

					const page = calculatePage(
						delta,
						renderState.page,
						renderState.changes.length
					);

					pushState(
						getPath(
							ascendingState,
							columnState,
							delta,
							getEntryParam(renderState.node),
							filtersState,
							resultsKeywords,
							renderState.nav,
							page,
							renderState.showHideable
						)
					);

					setRenderState({
						changes: renderState.changes,
						children: renderState.children,
						delta,
						id: renderState.id,
						nav: renderState.nav,
						node: renderState.node,
						page,
						parents: renderState.parents,
						showHideable: renderState.showHideable,
					});
				}}
				onPageChange={(page) => {
					pushState(
						getPath(
							ascendingState,
							columnState,
							renderState.delta,
							getEntryParam(renderState.node),
							filtersState,
							resultsKeywords,
							renderState.nav,
							page,
							renderState.showHideable
						)
					);

					setRenderState({
						changes: renderState.changes,
						children: renderState.children,
						delta: renderState.delta,
						id: renderState.id,
						nav: renderState.nav,
						node: renderState.node,
						page,
						parents: renderState.parents,
						showHideable: renderState.showHideable,
					});
				}}
				totalItems={renderState.changes.length}
			/>
		);
	};

	const renderResultsBar = () => {
		if (renderState.id > 0) {
			return '';
		}

		const labels = [];

		const changeTypes = filtersState['changeTypes'];

		if (changeTypes && changeTypes.length > 0) {
			for (let i = 0; i < changeTypes.length; i++) {
				const changeType = changeTypes[i];

				let label = Liferay.Language.get('modified');

				if (changeType === CHANGE_TYPE_ADDITION) {
					label = Liferay.Language.get('added');
				}
				else if (changeType === CHANGE_TYPE_DELETION) {
					label = Liferay.Language.get('deleted');
				}

				labels.push({
					label: Liferay.Language.get('change-type') + ': ' + label,
					onClick: () => toggleFilter('changeTypes', changeType),
				});
			}
		}

		const siteIds = filtersState['sites'];

		if (siteIds && siteIds.length > 0) {
			for (let i = 0; i < siteIds.length; i++) {
				const siteName = siteNames[siteIds[i]];

				labels.push({
					label: Liferay.Language.get('site') + ': ' + siteName,
					onClick: () => toggleFilter('sites', siteIds[i]),
				});
			}
		}

		const typeIds = filtersState['types'];

		if (typeIds && typeIds.length > 0) {
			for (let i = 0; i < typeIds.length; i++) {
				const type = typesRef.current[typeIds[i]];

				if (renderState.showHideable || !type.hideable) {
					labels.push({
						label: Liferay.Language.get('type') + ': ' + type.label,
						onClick: () => toggleFilter('types', typeIds[i]),
					});
				}
			}
		}

		const userIds = filtersState['users'];

		if (userIds && userIds.length > 0) {
			for (let i = 0; i < userIds.length; i++) {
				const user = userInfo[userIds[i]];

				labels.push({
					label: Liferay.Language.get('user') + ': ' + user.userName,
					onClick: () => toggleFilter('users', userIds[i]),
				});
			}
		}

		if (!resultsKeywords && labels.length === 0) {
			return '';
		}

		labels.sort((a, b) => {
			if (a.label < b.label) {
				return -1;
			}

			if (a.label > b.label) {
				return 1;
			}

			return 0;
		});

		const items = [];

		items.push(
			<ClayResultsBar.Item>
				<span className="component-text text-truncate-inline">
					<span className="text-truncate">
						{Liferay.Util.sub(
							renderState.changes &&
								renderState.changes.length === 1
								? Liferay.Language.get('x-result-for')
								: Liferay.Language.get('x-results-for'),
							renderState.changes
								? renderState.changes.length.toString()
								: '0'
						)}
					</span>
				</span>
			</ClayResultsBar.Item>
		);

		if (resultsKeywords) {
			items.push(
				<ClayResultsBar.Item>
					<ClayLabel
						className="component-label tbar-label"
						closeButtonProps={{
							onClick: () => {
								handleFiltersUpdate(filtersState, '');
								setEntrySearchTerms('');
							},
						}}
						displayType="unstyled"
						spritemap={spritemap}
					>
						{Liferay.Language.get('keywords') +
							': ' +
							resultsKeywords}
					</ClayLabel>
				</ClayResultsBar.Item>
			);
		}

		for (let i = 0; i < labels.length; i++) {
			items.push(
				<ClayResultsBar.Item>
					<ClayLabel
						className="component-label tbar-label"
						closeButtonProps={{
							onClick: labels[i].onClick,
						}}
						displayType="unstyled"
						spritemap={spritemap}
					>
						{labels[i].label}
					</ClayLabel>
				</ClayResultsBar.Item>
			);
		}

		items.push(<ClayResultsBar.Item expand />);
		items.push(
			<ClayResultsBar.Item>
				<ClayButton
					className="component-link tbar-link"
					displayType="unstyled"
					onClick={() => {
						handleFiltersUpdate(
							{changeTypes: [], sites: [], types: [], users: []},
							''
						);
						setEntrySearchTerms('');
					}}
				>
					{Liferay.Language.get('clear')}
				</ClayButton>
			</ClayResultsBar.Item>
		);

		return <ClayResultsBar>{items}</ClayResultsBar>;
	};

	const renderTableBody = () => {
		return (
			<ClayTable.Body>
				{getTableRows(filterDisplayNodes(renderState.changes))}
			</ClayTable.Body>
		);
	};

	const renderTableHead = () => {
		if (!renderState.changes || renderState.changes.length === 0) {
			return (
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell colSpan={5}>
							<div className="taglib-empty-result-message">
								<div className="taglib-empty-search-result-message-header" />

								<div className="sheet-text text-center">
									{Liferay.Language.get(
										'there-are-no-changes-to-display-in-this-view'
									)}
								</div>
							</div>
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>
			);
		}

		return (
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell>
						{getColumnHeader(
							COLUMN_USER,
							Liferay.Language.get('user')
						)}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell>
						{getColumnHeader(
							COLUMN_SITE,
							Liferay.Language.get('site')
						)}
					</ClayTable.Cell>

					<ClayTable.Cell className="table-cell-expand" headingCell>
						{getColumnHeader(
							COLUMN_TITLE,
							Liferay.Language.get('title')
						)}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="table-cell-expand-smallest"
						headingCell
					>
						{getColumnHeader(
							COLUMN_CHANGE_TYPE,
							Liferay.Language.get('change-type')
						)}
					</ClayTable.Cell>

					<ClayTable.Cell
						className="table-cell-expand-smallest"
						headingCell
					>
						{getColumnHeader(
							COLUMN_MODIFIED_DATE,
							Liferay.Language.get('last-modified')
						)}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>
		);
	};

	const renderTable = () => {
		if (renderState.id > 0) {
			return '';
		}

		return (
			<>
				<ClayTable
					className="publications-table"
					headingNoWrap
					hover
					noWrap
				>
					{renderNavigationBar()}

					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell
								className="publications-header-td"
								colSpan={5}
							>
								{renderManagementToolbar()}
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>

					{renderTableHead()}

					{renderTableBody()}
				</ClayTable>

				{renderPaginationBar()}
			</>
		);
	};

	const renderMainContent = () => {
		if (changes.length === 0 && ctMappingInfos.length === 0) {
			return (
				<div className="container-fluid container-fluid-max-xl">
					{renderExpiredBanner()}

					<div className="sheet taglib-empty-result-message">
						<div className="taglib-empty-result-message-header" />

						<div className="sheet-text text-center">
							{Liferay.Language.get('no-changes-were-found')}
						</div>
					</div>
				</div>
			);
		}

		const items = [
			{
				label: Liferay.Language.get('all-items'),
				onClick: () => navigate(0),
			},
			{
				active: true,
				label: renderState.node.title,
			},
		];

		return (
			<div className="container-fluid container-fluid-max-xl">
				{renderExpiredBanner()}

				{renderState.id > 0 && (
					<ClayBreadcrumb items={items} spritemap={spritemap} />
				)}

				<div className="publications-changes-content row">
					<div className="col-md-12">
						{renderState.node.modelClassNameId && (
							<ChangeTrackingRenderView
								childEntries={renderState.children}
								ctEntry={!!renderState.node.ctEntryId}
								dataURL={getDataURL(renderState.node)}
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
								parentEntries={renderState.parents}
								showDropdown={
									activeCTCollection &&
									renderState.node.modelClassNameId
								}
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

						{renderTable()}
					</div>
				</div>
			</div>
		);
	};

	const renderToolbarAction = (displayType, label, symbol, url) => {
		if (!url) {
			return '';
		}

		return (
			<ClayToolbar.Item>
				<a
					className={classNames(
						'btn btn-' + displayType + ' btn-sm',
						{
							disabled: changes.length === 0 || expired,
						}
					)}
					href={setParameter(
						url,
						'redirect',
						window.location.pathname + window.location.search
					)}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon spritemap={spritemap} symbol={symbol} />
					</span>

					{label}
				</a>
			</ClayToolbar.Item>
		);
	};

	const renderPublicationsToolbar = () => {
		return (
			<ClayToolbar className="publications-tbar" light>
				<div className="container-fluid container-fluid-max-xl">
					<ClayToolbar.Nav>
						<ClayToolbar.Item className="text-left" expand>
							<ClayToolbar.Section>
								<div className="publication-name">
									<span>{name}</span>

									<ClayLabel
										displayType={statusStyle}
										spritemap={spritemap}
									>
										{statusLabel}
									</ClayLabel>
								</div>

								<div className="publication-description">
									{description}
								</div>
							</ClayToolbar.Section>
						</ClayToolbar.Item>

						<ClayToolbar.Item>
							<ManageCollaborators {...collaboratorsData} />
						</ClayToolbar.Item>

						{renderToolbarAction(
							'secondary',
							Liferay.Language.get('schedule'),
							'calendar',
							scheduleURL
						)}

						{renderToolbarAction(
							'primary',
							Liferay.Language.get('publish'),
							'change',
							publishURL
						)}

						{renderToolbarAction(
							'secondary',
							Liferay.Language.get('unschedule'),
							'times-circle',
							unscheduleURL
						)}

						{renderToolbarAction(
							'primary',
							Liferay.Language.get('reschedule'),
							'calendar',
							rescheduleURL
						)}

						{renderToolbarAction(
							'secondary',
							Liferay.Language.get('revert'),
							'undo',
							revertURL
						)}

						<ClayToolbar.Item
							data-tooltip-align="top"
							title={Liferay.Language.get('comments')}
						>
							<ClayButton
								className={classNames(
									'nav-link nav-link-monospaced',
									{
										active: showComments,
									}
								)}
								displayType="unstyled"
								onClick={() => setShowComments(!showComments)}
							>
								<ClayIcon
									spritemap={spritemap}
									symbol="comments"
								/>
							</ClayButton>
						</ClayToolbar.Item>

						{dropdownItems && dropdownItems.length > 0 && (
							<ClayToolbar.Item>
								<ClayDropDownWithItems
									items={dropdownItems}
									spritemap={spritemap}
									trigger={
										<ClayButtonWithIcon
											displayType="unstyled"
											small
											spritemap={spritemap}
											symbol="ellipsis-v"
										/>
									}
								/>
							</ClayToolbar.Item>
						)}
					</ClayToolbar.Nav>
				</div>
			</ClayToolbar>
		);
	};

	return (
		<>
			{renderPublicationsToolbar()}
			{renderResultsBar()}
			<div
				className={classNames('sidenav-container sidenav-right', {
					closed: !showComments,
					open: showComments,
				})}
			>
				<div
					className="info-panel sidenav-menu-slider"
					style={
						showComments
							? {
									'height': '100%',
									'min-height': '485px',
									'width': '320px',
							  }
							: {}
					}
				>
					<div
						className="sidebar sidebar-light sidenav-menu"
						style={
							showComments
								? {
										'height': '100%',
										'min-height': '485px',
										'width': '320px',
								  }
								: {}
						}
					>
						{showComments && (
							<ChangeTrackingComments
								ctEntryId={0}
								currentUserId={currentUserId}
								deleteCommentURL={deleteCTCommentURL}
								getCache={() => {
									return commentsCacheRef.current['0'];
								}}
								getCommentsURL={getCTCommentsURL}
								keyParam=""
								setShowComments={setShowComments}
								spritemap={spritemap}
								updateCache={(data) => {
									const cacheData = JSON.parse(
										JSON.stringify(data)
									);

									cacheData.updatedCommentId = null;

									commentsCacheRef.current['0'] = cacheData;
								}}
								updateCommentURL={updateCTCommentURL}
							/>
						)}
					</div>
				</div>

				<div
					className="sidenav-content"
					style={
						showComments
							? {'min-height': '485px', 'padding-right': '320px'}
							: {}
					}
				>
					{renderMainContent()}
				</div>
			</div>
		</>
	);
}
