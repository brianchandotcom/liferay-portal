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
import React, {useLayoutEffect, useState, useCallback} from 'react';
import {createPortal} from 'react-dom';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import selectEditableValue from '../../selectors/selectEditableValue';
import {useSelector} from '../../store/index';
import {useEditableIsTranslated, useIsActive, useIsHovered} from '../Controls';

const ACTIVE_CLASS = 'page-editor__editable-decoration--active';
const HIGHLIGHTED_CLASS = 'page-editor__editable-decoration--highlighted';
const HOVERED_CLASS = 'page-editor__editable-decoration--hovered';
const MAPPED_CLASS = 'page-editor__editable-decoration--mapped';
const TRANSLATED_CLASS = 'page-editor__editable-decoration--translated';

const useIsHighlighted = () => {
	const isActive = useIsActive();

	return useCallback(
		(parentId, siblingsIds) =>
			isActive(parentId) ||
			siblingsIds.some(siblingId => isActive(siblingId)),
		[isActive]
	);
};

const isMapped = editableValue =>
	(editableValue.classNameId &&
		editableValue.classPK &&
		editableValue.fieldId) ||
	editableValue.mappedField;

export default function EditableDecoration({
	editableId,
	editableUniqueId,
	parentItemId,
	parentRef,
	siblingsIds
}) {
	const isActive = useIsActive();
	const isHighlighted = useIsHighlighted();
	const isHovered = useIsHovered();
	const isTranslated = useEditableIsTranslated();

	const sidebarOpen = useSelector(
		state => state.sidebarPanelId && state.sidebarOpen
	);
	const [style, setStyle] = useState({});
	const wrapper = document.getElementById('wrapper');

	const editableValue = useSelector(state => {
		const parentItem = state.layoutData.items[parentItemId];

		if (parentItem) {
			return selectEditableValue(
				state,
				state.layoutData.items[parentItemId].config.fragmentEntryLinkId,
				editableId,
				EDITABLE_FRAGMENT_ENTRY_PROCESSOR
			);
		}

		return {};
	});

	const hideDecoration = useCallback(() => {
		setStyle({
			opacity: 0
		});
	}, []);

	const showDecoration = useCallback(() => {
		const editableElement = parentRef.current.querySelector(
			`[id="${editableId}"]`
		);

		const editableElementRect = editableElement.getBoundingClientRect();
		const wrapperRect = document
			.getElementById('wrapper')
			.getBoundingClientRect();

		setStyle({
			height: editableElementRect.height,
			left: editableElementRect.left,
			opacity: 1,
			top: editableElementRect.top - wrapperRect.top + wrapper.scrollTop,
			width: editableElementRect.width
		});
	}, [editableId, parentRef, wrapper.scrollTop]);

	useLayoutEffect(() => {
		window.addEventListener('resize', showDecoration);

		return () => {
			window.removeEventListener('resize', showDecoration);
		};
	}, [showDecoration]);

	useLayoutEffect(() => {
		const sideNavigation = Liferay.SideNavigation.instance(
			document.querySelector('.product-menu-toggle')
		);

		const onStartAnimation = () => {
			hideDecoration();
		};

		const onEndAnimation = () => {
			showDecoration();
		};

		const sideNavigationListeners = [];

		sideNavigationListeners.push(
			sideNavigation.on('openStart.lexicon.sidenav', onStartAnimation)
		);
		sideNavigationListeners.push(
			sideNavigation.on('open.lexicon.sidenav', onEndAnimation)
		);
		sideNavigationListeners.push(
			sideNavigation.on('closedStart.lexicon.sidenav', onStartAnimation)
		);
		sideNavigationListeners.push(
			sideNavigation.on('closed.lexicon.sidenav', onEndAnimation)
		);

		showDecoration();

		return () => {
			sideNavigationListeners.forEach(listener =>
				listener.removeListener()
			);
		};
	}, [hideDecoration, showDecoration]);

	useLayoutEffect(() => {
		hideDecoration();

		const pageEditor =
			document.getElementById('master-layout') ||
			document.getElementById('page-editor');

		const onTransitionEnd = () => {
			showDecoration();

			pageEditor.removeEventListener('transitionend', onTransitionEnd);
		};

		pageEditor.addEventListener('transitionend', onTransitionEnd);

		return () => {
			pageEditor.removeEventListener('transitionend', onTransitionEnd);
		};
	}, [hideDecoration, showDecoration, sidebarOpen]);

	return createPortal(
		<div
			className={classNames('page-editor__editable-decoration', {
				[ACTIVE_CLASS]: isActive(editableUniqueId),
				[HIGHLIGHTED_CLASS]: isHighlighted(parentItemId, siblingsIds),
				[HOVERED_CLASS]: isHovered(editableUniqueId),
				[MAPPED_CLASS]: isMapped(editableValue),
				[TRANSLATED_CLASS]: isTranslated(editableValue)
			})}
			style={style}
		></div>,
		wrapper
	);
}
