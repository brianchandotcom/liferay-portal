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
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/editableFloatingToolbarButtons';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import Processors from '../../processors/index';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useSelector} from '../../store/index';
import UnsafeHTML from '../UnsafeHTML';
import {useSetEditableProcessorUniqueId} from './EditableProcessorContext';
import FragmentContentClickFilter from './FragmentContentClickFilter';
import FragmentContentDecoration from './FragmentContentDecoration';
import FragmentContentFloatingToolbar from './FragmentContentFloatingToolbar';
import FragmentContentProcessor from './FragmentContentProcessor';
import getEditableUniqueId from './getEditableUniqueId';
import resolveEditableValue from './resolveEditableValue';

function FragmentContent({fragmentEntryLinkId, itemId}, ref) {
	const element = ref.current;
	const isMounted = useIsMounted();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();

	const [editableElements, setEditableElements] = useState([]);

	const updateEditableElements = (parent = element) => {
		setEditableElements(
			parent ? Array.from(parent.querySelectorAll('lfr-editable')) : []
		);
	};

	const languageId = useSelector(state => state.languageId);

	const segmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);

	const defaultContent = useSelector(
		state => state.fragmentEntryLinks[fragmentEntryLinkId].content
	);

	const editableValues = useSelector(
		state => state.fragmentEntryLinks[fragmentEntryLinkId].editableValues
	);

	const [content, setContent] = useState(defaultContent);

	useEffect(() => {
		let element = document.createElement('div');
		element.innerHTML = defaultContent;

		const updateContent = debounce(() => {
			if (isMounted() && element) {
				setContent(element.innerHTML);
			}
		}, 50);

		Array.from(
			element.querySelectorAll('[data-lfr-background-image-id]')
		).map(editable => {
			resolveEditableValue(
				editableValues,
				editable.dataset.lfrBackgroundImageId,
				BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
				languageId,
				segmentsExperienceId
			).then(([value, _editableConfig]) => {
				const processor = Processors['background-image'];

				processor.render(editable, value);
				updateContent();
			});
		});

		Array.from(element.querySelectorAll('lfr-editable')).forEach(
			editable => {
				editable.classList.add('page-editor__editable');

				resolveEditableValue(
					editableValues,
					editable.getAttribute('id'),
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					languageId,
					segmentsExperienceId
				).then(([value, editableConfig]) => {
					const processor =
						Processors[editable.getAttribute('type')] ||
						Processors.fallback;

					processor.render(editable, value, editableConfig);
					updateContent();
				});
			}
		);

		return () => {
			element = null;
		};
	}, [
		defaultContent,
		editableValues,
		isMounted,
		languageId,
		segmentsExperienceId
	]);

	const onFloatingToolbarButtonClick = (buttonId, editableId) => {
		if (buttonId === EDITABLE_FLOATING_TOOLBAR_BUTTONS.edit.id) {
			setEditableProcessorUniqueId(
				getEditableUniqueId(fragmentEntryLinkId, editableId)
			);
		}
	};

	return (
		<>
			<UnsafeHTML
				className="page-editor__fragment"
				contentRef={ref}
				markup={content}
				onRender={updateEditableElements}
			/>

			<FragmentContentClickFilter
				element={element}
				fragmentEntryLinkId={fragmentEntryLinkId}
				itemId={itemId}
			/>

			<FragmentContentFloatingToolbar
				element={element}
				fragmentEntryLinkId={fragmentEntryLinkId}
				onButtonClick={onFloatingToolbarButtonClick}
			/>

			<FragmentContentProcessor
				element={element}
				fragmentEntryLinkId={fragmentEntryLinkId}
			/>

			{editableElements.map(editableElement => (
				<FragmentContentDecoration
					editableElement={editableElement}
					element={element}
					fragmentEntryLinkId={fragmentEntryLinkId}
					itemId={itemId}
					key={editableElement.id}
				/>
			))}
		</>
	);
}

FragmentContent.propTypes = {
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired
};

export default React.forwardRef(FragmentContent);
