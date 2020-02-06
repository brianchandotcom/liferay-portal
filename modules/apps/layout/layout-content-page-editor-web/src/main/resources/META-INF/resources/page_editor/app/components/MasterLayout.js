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

import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import {closest} from 'metal-dom';
import React, {useRef, useEffect, useState, useContext} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/editableFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../config/index';
import Processors from '../processors/index';
import {useSelector} from '../store/index';
import PageEditor from './PageEditor';
import UnsafeHTML from './UnsafeHTML';
import {Column, Container, Row} from './layout-data-items/index';

const LAYOUT_DATA_ITEMS = {
	[LAYOUT_DATA_ITEM_TYPES.column]: Column,
	[LAYOUT_DATA_ITEM_TYPES.container]: Container,
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: DropZoneContainer,
	[LAYOUT_DATA_ITEM_TYPES.fragment]: Fragment,
	[LAYOUT_DATA_ITEM_TYPES.root]: Root,
	[LAYOUT_DATA_ITEM_TYPES.row]: Row
};

export default function MasterPage() {
	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const masterLayoutData = useSelector(state => state.masterLayoutData);
	const sidebarOpen = useSelector(
		state => state.sidebarPanelId && state.sidebarOpen
	);

	const mainItem = masterLayoutData.items[masterLayoutData.rootItems.main];

	return (
		<div
			className={classNames('master-page', 'master-page--with-sidebar', {
				'master-page--with-sidebar-open': sidebarOpen
			})}
			id="master-layout"
		>
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
			{item.children.map(childId => {
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

function DropZoneContainer() {
	return <PageEditor withinMasterPage />;
}

function Root({children}) {
	return <div className="pt-4">{children}</div>;
}

const FragmentContent = React.memo(function FragmentContent({
	content: defaultContent,
	editableValues,
	languageId
}) {
	const ref = useRef(null);
	const isMounted = useIsMounted();
	const [content, setContent] = useState(defaultContent);

	useEffect(() => {
		const element = ref.current;

		if (!element) {
			return;
		}

		const handler = event => {
			const element = event.target;

			if (closest(element, '[href]')) {
				event.preventDefault();
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

		Array.from(
			element.querySelectorAll('[data-lfr-background-image-id]')
		).map(editable => {
			const editableId = editable.dataset.lfrBackgroundImageId;

			const editableValue =
				editableValues[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR][
					editableId
				];

			if (editableIsMapped(editableValue)) {
				return;
			}

			const value = editableValue[languageId];

			if (value) {
				const processor = Processors['background-image'];

				processor.render(editable, value);
			}
		});

		Array.from(element.querySelectorAll('lfr-editable')).forEach(
			editable => {
				const editableId = editable.getAttribute('id');

				const editableValue =
					editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][
						editableId
					];

				if (editableIsMapped(editableValue)) {
					return;
				}

				const value = editableValue[languageId];

				const editableConfig = editableValue.config || {};

				if (value && editableConfig) {
					const processor =
						Processors[editable.getAttribute('type')] ||
						Processors.fallback;

					processor.render(editable, value, editableConfig);
				}
			}
		);

		updateContent();

		return () => {
			element = null;
		};
	}, [defaultContent, content, isMounted, editableValues, languageId]);

	return <UnsafeHTML markup={content} ref={ref} />;
});

function Fragment({fragmentEntryLinks, item}) {
	const config = useContext(ConfigContext);

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	const languageId = useSelector(state => state.languageId);

	return (
		<FragmentContent
			content={fragmentEntryLink.content.value.content}
			editableValues={fragmentEntryLink.editableValues}
			languageId={languageId || config.defaultLanguageId}
		/>
	);
}

function editableIsMapped(editableValue) {
	return (
		editableValue &&
		((editableValue.classNameId &&
			editableValue.classPK &&
			editableValue.fieldId) ||
			editableValue.mappedField)
	);
}
