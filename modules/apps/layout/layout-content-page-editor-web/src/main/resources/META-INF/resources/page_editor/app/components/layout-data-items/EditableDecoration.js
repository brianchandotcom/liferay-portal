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
import {closest} from 'metal-dom';
import React, {
	useLayoutEffect,
	useState,
	useCallback,
	useContext,
	useMemo
} from 'react';
import {createPortal} from 'react-dom';

import {ConfigContext} from '../../config/index';
import selectEditableValue from '../../selectors/selectEditableValue';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useSelector} from '../../store/index';
import {
	useIsActive,
	useIsHovered,
	useHoverItem,
	useSelectItem
} from '../Controls';

const ACTIVE_CLASS = 'page-editor__editable-decoration--active';
const HIGHLIGHTED_CLASS = 'page-editor__editable-decoration--highlighted';
const HOVERED_CLASS = 'page-editor__editable-decoration--hovered';
const MAPPED_CLASS = 'page-editor__editable-decoration--mapped';
const TRANSLATED_CLASS = 'page-editor__editable-decoration--translated';

export default function EditableDecoration({
	editableId,
	fragmentEntryLinkId,
	itemId,
	onEditableDoubleClick,
	parentItemId,
	parentRef,
	siblingsItemIds
}) {
	const {defaultLanguageId} = useContext(ConfigContext);
	const [style, setStyle] = useState({});
	const wrapper = useMemo(() => document.getElementById('wrapper'), []);

	const isActive = useIsActive();
	const isHovered = useIsHovered();
	const hoverItem = useHoverItem();
	const selectItem = useSelectItem();

	const isHighlighted = useMemo(
		() =>
			[parentItemId, ...siblingsItemIds].some(_itemId =>
				isActive(_itemId)
			),
		[isActive, parentItemId, siblingsItemIds]
	);

	const isMapped = useSelector(state => {
		const editableValue = selectEditableValue(
			state,
			fragmentEntryLinkId,
			editableId
		);

		return (
			(editableValue.classNameId &&
				editableValue.classPK &&
				editableValue.fieldId) ||
			editableValue.mappedField
		);
	});

	const isTranslated = useSelector(state => {
		const editableValue = selectEditableValue(
			state,
			fragmentEntryLinkId,
			editableId
		);

		const {languageId} = state;
		const segmentsExperienceId = selectPrefixedSegmentsExperienceId(state);

		return (
			editableValue &&
			defaultLanguageId !== languageId &&
			(editableValue[languageId] ||
				(segmentsExperienceId in editableValue &&
					editableValue[segmentsExperienceId][languageId]))
		);
	});

	const sidebarOpen = useSelector(
		state => state.sidebarPanelId && state.sidebarOpen
	);

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
		const onClick = event => {
			const editableElement = closest(event.target, 'lfr-editable');

			if (editableElement && editableElement.id === editableId) {
				event.stopPropagation();

				if (isActive(itemId) && onEditableDoubleClick) {
					onEditableDoubleClick(editableElement);
				} else {
					selectItem(itemId);
				}
			}
		};

		const onMouseOver = event => {
			const editableElement = closest(event.target, 'lfr-editable');

			if (editableElement && editableElement.id === editableId) {
				event.stopPropagation();
				hoverItem(itemId);
			}
		};

		const onMouseOut = event => {
			const editableElement = closest(event.target, 'lfr-editable');

			if (
				editableElement &&
				editableElement.id === editableId &&
				isHovered(itemId)
			) {
				event.stopPropagation();
				hoverItem(null);
			}
		};

		const parent = parentRef.current;

		if (parent) {
			parent.addEventListener('click', onClick);
			parent.addEventListener('mouseover', onMouseOver);
			parent.addEventListener('mouseout', onMouseOut);
		}

		return () => {
			if (parent) {
				parent.removeEventListener('click', onClick);
				parent.removeEventListener('mouseover', onMouseOver);
				parent.removeEventListener('mouseout', onMouseOut);
			}
		};
	}, [
		editableId,
		hoverItem,
		isHovered,
		itemId,
		isActive,
		onEditableDoubleClick,
		parentRef,
		selectItem
	]);

	useLayoutEffect(() => {
		showDecoration();
	}, [siblingsItemIds, showDecoration]);

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
				[ACTIVE_CLASS]: isActive(itemId),
				[HIGHLIGHTED_CLASS]: isHighlighted,
				[HOVERED_CLASS]: !isActive(itemId) && isHovered(itemId),
				[MAPPED_CLASS]: isMapped,
				[TRANSLATED_CLASS]: isTranslated
			})}
			style={style}
		></div>,
		wrapper
	);
}
