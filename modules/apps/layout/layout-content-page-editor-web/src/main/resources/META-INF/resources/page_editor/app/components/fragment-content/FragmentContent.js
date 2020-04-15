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
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {updateFragmentEntryLinkContent} from '../../actions/index';
import {DROP_ZONE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/dropZoneFragmentEntryProcessor';
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/editableFloatingToolbarButtons';
import selectCanUpdateLayoutContent from '../../selectors/selectCanUpdateLayoutContent';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import FragmentService from '../../services/FragmentService';
import {useDispatch, useSelector} from '../../store/index';
import {useGetFieldValue} from '../CollectionItemContext';
import Layout from '../Layout';
import UnsafeHTML from '../UnsafeHTML';
import {
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from './EditableProcessorContext';
import FragmentContentFloatingToolbar from './FragmentContentFloatingToolbar';
import FragmentContentInteractionsFilter from './FragmentContentInteractionsFilter';
import FragmentContentProcessor from './FragmentContentProcessor';
import getAllEditables from './getAllEditables';
import getEditableUniqueId from './getEditableUniqueId';
import resolveEditableValue from './resolveEditableValue';

const FragmentContent = React.forwardRef(
	({fragmentEntryLinkId, itemId}, ref) => {
		const dispatch = useDispatch();
		const isMounted = useIsMounted();
		const editableProcessorUniqueId = useEditableProcessorUniqueId();
		const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
		const canUpdateLayoutContent = useSelector(
			selectCanUpdateLayoutContent
		);

		const getFieldValue = useGetFieldValue();

		const [editables, setEditables] = useState([]);

		const editableElements = useMemo(
			() => editables.map(editable => editable.element),
			[editables]
		);

		const updateEditables = useCallback(
			parent => {
				let updatedEditableValues = [];
				if (isMounted()) {
					updatedEditableValues = parent
						? getAllEditables(parent)
						: [];
					setEditables(updatedEditableValues);
				}

				return updatedEditableValues;
			},
			[isMounted]
		);

		const languageId = useSelector(state => state.languageId);

		const prefixedSegmentsExperienceId = useSelector(
			selectPrefixedSegmentsExperienceId
		);
		const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

		const defaultContent = useSelector(state =>
			state.fragmentEntryLinks[fragmentEntryLinkId]
				? state.fragmentEntryLinks[fragmentEntryLinkId].content
				: ''
		);

		const editableValues = useSelector(state =>
			state.fragmentEntryLinks[fragmentEntryLinkId]
				? state.fragmentEntryLinks[fragmentEntryLinkId].editableValues
				: {}
		);

		const [content, setContent] = useState(defaultContent);

		useEffect(() => {
			FragmentService.renderFragmentEntryLinkContent({
				fragmentEntryLinkId,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			}).then(({content}) =>
				dispatch(
					updateFragmentEntryLinkContent({
						content,
						editableValues,
						fragmentEntryLinkId,
					})
				)
			);
		}, [
			dispatch,
			editableValues,
			fragmentEntryLinkId,
			segmentsExperienceId,
		]);

		useEffect(() => {
			let element = document.createElement('div');
			element.innerHTML = defaultContent;
			const updatedEditables = updateEditables(element);

			const updateContent = debounce(() => {
				if (isMounted() && element) {
					setContent(element.innerHTML);
				}
			}, 50);

			if (!editableProcessorUniqueId) {
				updatedEditables.forEach(editable => {
					resolveEditableValue(
						editableValues,
						editable.editableId,
						editable.editableValueNamespace,
						languageId,
						prefixedSegmentsExperienceId,
						getFieldValue
					).then(([value, editableConfig]) => {
						editable.processor.render(
							editable.element,
							value,
							editableConfig
						);

						editable.element.classList.add('page-editor__editable');

						updateContent();
					});
				});

				updateContent();
			}

			return () => {
				element = null;
			};
		}, [
			defaultContent,
			editableProcessorUniqueId,
			editableValues,
			getFieldValue,
			isMounted,
			languageId,
			prefixedSegmentsExperienceId,
			updateEditables,
		]);

		const dropZones = useSelector(state => {
			const fragmentEntryLink = state.fragmentEntryLinks[
				fragmentEntryLinkId
			] || {editableValues: {}};

			const dropZoneValues =
				fragmentEntryLink.editableValues[
					DROP_ZONE_FRAGMENT_ENTRY_PROCESSOR
				] || {};

			return dropZoneValues.dropZones || {};
		});

		const getPortals = useCallback(
			element =>
				Array.from(element.querySelectorAll('lfr-drop-zone')).map(
					dropZoneElement => {
						const mainItemId = (
							dropZones.find(
								dropZone =>
									dropZone.id ===
									dropZoneElement.getAttribute('id')
							) || {}
						).uuid;

						const Component = () =>
							mainItemId && (
								<Layout
									mainItemId={mainItemId}
									withinMasterPage
								/>
							);

						Component.displayName = 'DropZoneComponent';

						return {
							Component,
							element: dropZoneElement,
						};
					}
				),
			[dropZones]
		);

		const onFloatingToolbarButtonClick = (buttonId, editableId) => {
			if (buttonId === EDITABLE_FLOATING_TOOLBAR_BUTTONS.edit.id) {
				setEditableProcessorUniqueId(
					getEditableUniqueId(fragmentEntryLinkId, editableId)
				);
			}
		};

		return (
			<>
				<FragmentContentInteractionsFilter
					editableElements={editableElements}
					fragmentEntryLinkId={fragmentEntryLinkId}
					itemId={itemId}
				>
					<UnsafeHTML
						className={classNames('page-editor__fragment-content', {
							'page-editor__fragment-content--portlet-topper-hidden': !canUpdateLayoutContent,
						})}
						contentRef={ref}
						getPortals={getPortals}
						markup={content}
						onRender={updateEditables}
					/>
				</FragmentContentInteractionsFilter>

				{canUpdateLayoutContent && (
					<FragmentContentFloatingToolbar
						editables={editables}
						fragmentEntryLinkId={fragmentEntryLinkId}
						onButtonClick={onFloatingToolbarButtonClick}
					/>
				)}

				<FragmentContentProcessor
					editables={editables}
					fragmentEntryLinkId={fragmentEntryLinkId}
				/>
			</>
		);
	}
);

FragmentContent.displayName = 'FragmentContent';

FragmentContent.propTypes = {
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired,
};

export default FragmentContent;
