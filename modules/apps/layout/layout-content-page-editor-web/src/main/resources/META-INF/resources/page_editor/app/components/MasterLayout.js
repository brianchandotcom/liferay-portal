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

import {useIsMounted} from 'frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import {closest} from 'metal-dom';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../prop-types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {config} from '../config/index';
import {useSelector} from '../store/index';
import {useGetFieldValue} from './CollectionItemContext';
import {useSelectItem} from './Controls';
import Layout from './Layout';
import UnsafeHTML from './UnsafeHTML';
import getAllEditables from './fragment-content/getAllEditables';
import resolveEditableValue from './fragment-content/resolveEditableValue';
import {Collection, Column, Container, Row} from './layout-data-items/index';

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.collection]: Collection,
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: CollectionItem,
	[LAYOUT_DATA_ITEM_TYPES.column]: Column,
	[LAYOUT_DATA_ITEM_TYPES.container]: Container,
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: DropZoneContainer,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: Fragment,
	[LAYOUT_DATA_ITEM_TYPES.root]: Root,
	[LAYOUT_DATA_ITEM_TYPES.row]: Row,
};

export default function MasterPage() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const masterLayoutData = useSelector((state) => state.masterLayoutData);

	const mainItem = masterLayoutData.items[masterLayoutData.rootItems.main];

	return (
		<div className="master-page" id="master-layout">
			<MasterLayoutDataItem
				fragmentEntryLinks={fragmentEntryLinks}
				item={mainItem}
				layoutData={masterLayoutData}
			/>
		</div>
	);
}

function MasterLayoutDataItem({fragmentEntryLinks, item, layoutData}) {
	const Component = LAYOUT_DATA_ITEMS[item.type];

	if (!Component) {
		return null;
	}

	return (
		<Component
			fragmentEntryLinks={fragmentEntryLinks}
			item={item}
			layoutData={layoutData}
		>
			{item.children.map((childId) => {
				return (
					<MasterLayoutDataItem
						fragmentEntryLinks={fragmentEntryLinks}
						item={layoutData.items[childId]}
						key={childId}
						layoutData={layoutData}
					/>
				);
			})}
		</Component>
	);
}

MasterLayoutDataItem.propTypes = {
	fragmentEntryLinks: PropTypes.object.isRequired,
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

function DropZoneContainer() {
	const mainItemId = useSelector((state) => state.layoutData.rootItems.main);

	return <Layout mainItemId={mainItemId} withinMasterPage />;
}

function Root({children}) {
	return <div>{children}</div>;
}

function CollectionItem({children}) {
	return <div>{children}</div>;
}

const FragmentContent = React.memo(function FragmentContent({
	content: defaultContent,
	editableValues,
	languageId,
}) {
	const ref = useRef(null);
	const isMounted = useIsMounted();
	const [content, setContent] = useState(defaultContent);
	const selectItem = useSelectItem();
	const getFieldValue = useGetFieldValue();

	useEffect(() => {
		const element = ref.current;

		if (!element) {
			return;
		}

		const handler = (event) => {
			const element = event.target;

			if (closest(element, '[href]')) {
				event.preventDefault();
			}

			if (!closest(event.target, '.page-editor')) {
				selectItem(null);
			}
		};

		element.addEventListener('click', handler);

		return () => {
			element.removeEventListener('click', handler);
		};
	});

	useEffect(() => {
		let element = document.createElement('div');
		element.innerHTML = content;

		const updateContent = debounce(() => {
			if (isMounted() && element) {
				setContent(element.innerHTML);
			}
		}, 50);

		getAllEditables(element).forEach((editable) => {
			resolveEditableValue(
				editableValues,
				editable.editableId,
				editable.editableValueNamespace,
				languageId,
				null,
				getFieldValue
			).then(([value, editableConfig]) => {
				editable.processor.render(
					editable.element,
					value,
					editableConfig
				);
			});
		});

		updateContent();

		return () => {
			element = null;
		};
	}, [
		defaultContent,
		content,
		isMounted,
		editableValues,
		languageId,
		getFieldValue,
	]);

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const masterLayoutData = useSelector((state) => state.masterLayoutData);

	const getPortals = useCallback(
		(element) =>
			Array.from(element.querySelectorAll('lfr-drop-zone')).map(
				(dropZoneElement) => {
					const mainItemId =
						dropZoneElement.getAttribute('uuid') || '';

					const Component = () =>
						mainItemId ? (
							<MasterLayoutDataItem
								fragmentEntryLinks={fragmentEntryLinks}
								item={masterLayoutData.items[mainItemId]}
								layoutData={masterLayoutData}
							/>
						) : null;

					Component.displayName = `DropZone(${mainItemId})`;

					return {
						Component,
						element: dropZoneElement,
					};
				}
			),
		[fragmentEntryLinks, masterLayoutData]
	);

	return (
		<UnsafeHTML
			className="page-editor__fragment-content page-editor__fragment-content--master"
			contentRef={ref}
			getPortals={getPortals}
			markup={content}
		/>
	);
});

FragmentContent.propTypes = {
	content: PropTypes.string.isRequired,
	editableValues: PropTypes.object.isRequired,
	languageId: PropTypes.string,
};

function Fragment({fragmentEntryLinks, item}) {
	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	const languageId = useSelector((state) => state.languageId);

	return (
		<FragmentContent
			content={fragmentEntryLink.content}
			editableValues={fragmentEntryLink.editableValues}
			languageId={languageId || config.defaultLanguageId}
		/>
	);
}

Fragment.propTypes = {
	fragmentEntryLinks: PropTypes.object.isRequired,
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}),
	}).isRequired,
};
