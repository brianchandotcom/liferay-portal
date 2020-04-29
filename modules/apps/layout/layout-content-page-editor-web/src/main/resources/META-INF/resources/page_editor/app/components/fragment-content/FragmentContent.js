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
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/editableFloatingToolbarButtons';
import selectCanUpdateLayoutContent from '../../selectors/selectCanUpdateLayoutContent';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import {
	useGetContent,
	useGetFieldValue,
	useRenderFragmentContent,
} from '../CollectionItemContext';
import {useFrameContext} from '../Frame';
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
		const [fragmentElement, setFragmentElement] = useState(null);
		const dispatch = useDispatch();
		const isMounted = useIsMounted();
		const editableProcessorUniqueId = useEditableProcessorUniqueId();
		const frameContext = useFrameContext();
		const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
		const canUpdateLayoutContent = useSelector(
			selectCanUpdateLayoutContent
		);

		const getFieldValue = useGetFieldValue();
		const getContent = useGetContent();
		const renderFragmentContent = useRenderFragmentContent();

		const [editables, setEditables] = useState([]);

		const editableElements = useMemo(
			() => editables.map((editable) => editable.element),
			[editables]
		);

		/**
		 * Updates editables array for the rendered fragment.
		 * @param {HTMLElement} [nextFragmentElement] Fragment element
		 *  If not specified, fragmentElement state is used instead.
		 * @return {Array} Updated editables array
		 */
		const updateEditables = useCallback(
			(nextFragmentElement = undefined) => {
				let updatedEditableValues = [];

				if (isMounted()) {
					if (nextFragmentElement) {
						setFragmentElement(nextFragmentElement);
						updatedEditableValues = getAllEditables(
							nextFragmentElement
						);
					}
					else if (fragmentElement) {
						updatedEditableValues = getAllEditables(
							fragmentElement
						);
					}

					setEditables(updatedEditableValues);
				}

				return updatedEditableValues;
			},
			[fragmentElement, isMounted]
		);

		const languageId = useSelector((state) => state.languageId);

		const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

		const fragmentEntryLink = useSelector(
			(state) => state.fragmentEntryLinks[fragmentEntryLinkId]
		);

		const defaultContent = getContent(fragmentEntryLink);

		const editableValues = useSelector((state) =>
			state.fragmentEntryLinks[fragmentEntryLinkId]
				? state.fragmentEntryLinks[fragmentEntryLinkId].editableValues
				: {}
		);

		const [content, setContent] = useState(defaultContent);

		useEffect(() => {
			renderFragmentContent({
				fragmentEntryLinkId,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			}).then((action) => {
				dispatch(
					updateFragmentEntryLinkContent({
						...action,
						editableValues,
						fragmentEntryLinkId,
					})
				);
			});
		}, [
			dispatch,
			editableValues,
			fragmentEntryLinkId,
			renderFragmentContent,
			segmentsExperienceId,
		]);

		useEffect(() => {
			let element = document.createElement('div');
			element.innerHTML = defaultContent;

			const updateContent = debounce(() => {
				if (isMounted() && element) {
					setContent(element.innerHTML);
				}
			}, 50);

			if (!editableProcessorUniqueId) {
				updateEditables().forEach((editable) => {
					resolveEditableValue(
						editableValues,
						editable.editableId,
						editable.editableValueNamespace,
						languageId,
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
			updateEditables,
		]);

		const getPortals = useCallback(
			(element) =>
				Array.from(element.querySelectorAll('lfr-drop-zone')).map(
					(dropZoneElement) => {
						const mainItemId =
							dropZoneElement.getAttribute('uuid') || '';

						const Component = () =>
							mainItemId ? (
								<Layout mainItemId={mainItemId} />
							) : null;

						Component.displayName = `DropZone(${mainItemId})`;

						return {
							Component,
							element: dropZoneElement,
						};
					}
				),
			[]
		);

		const onFloatingToolbarButtonClick = useCallback(
			(buttonId, editableId) => {
				if (buttonId === EDITABLE_FLOATING_TOOLBAR_BUTTONS.edit.id) {
					setEditableProcessorUniqueId(
						getEditableUniqueId(fragmentEntryLinkId, editableId)
					);
				}
			},
			[fragmentEntryLinkId, setEditableProcessorUniqueId]
		);

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
						globalContext={frameContext || window}
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
