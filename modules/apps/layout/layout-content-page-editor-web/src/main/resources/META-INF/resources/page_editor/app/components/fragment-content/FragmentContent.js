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
import React, {useEffect, useState} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/editableFloatingToolbarButtons';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {config} from '../../config/index';
import Processors from '../../processors/index';
import selectEditableValue from '../../selectors/selectEditableValue';
import selectEditableValueConfig from '../../selectors/selectEditableValueConfig';
import selectEditableValueContent from '../../selectors/selectEditableValueContent';
import InfoItemService from '../../services/InfoItemService';
import {useSelector} from '../../store/index';
import {useActiveItemId} from '../Controls';
import UnsafeHTML from '../UnsafeHTML';
import {useSetEditableProcessorUniqueId} from './EditableProcessorContext';
import FragmentContentClickFilter from './FragmentContentClickFilter';
import FragmentContentDecoration from './FragmentContentDecoration';
import FragmentContentFloatingToolbar from './FragmentContentFloatingToolbar';
import FragmentContentProcessor from './FragmentContentProcessor';

function FragmentContent({fragmentEntryLink, itemId}, ref) {
	const element = ref.current;
	const activeItemId = useActiveItemId();
	const isMounted = useIsMounted();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const state = useSelector(state => state);

	const defaultContent = fragmentEntryLink.content;
	const {fragmentEntryLinkId} = fragmentEntryLink;

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
				state,
				config,
				fragmentEntryLinkId,
				editable.dataset.lfrBackgroundImageId,
				BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			).then(([value, _editableConfig]) => {
				const processor = Processors['background-image'];

				processor.render(editable, value);
			});
		});

		Array.from(element.querySelectorAll('lfr-editable')).forEach(
			editable => {
				editable.classList.add('page-editor__editable');

				resolveEditableValue(
					state,
					config,
					fragmentEntryLinkId,
					editable.getAttribute('id'),
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				).then(([value, editableConfig]) => {
					if (element) {
						const processor =
							Processors[editable.getAttribute('type')] ||
							Processors.fallback;

						processor.render(editable, value, editableConfig);
					}
				});
			}
		);

		updateContent();

		return () => {
			element = null;
		};
	}, [state, defaultContent, fragmentEntryLinkId, isMounted]);

	const onFloatingToolbarButtonClick = buttonId => {
		if (buttonId === EDITABLE_FLOATING_TOOLBAR_BUTTONS.edit.id) {
			setEditableProcessorUniqueId(activeItemId);
		}
	};

	return (
		<>
			<UnsafeHTML
				className="page-editor__fragment"
				contentRef={ref}
				markup={content}
			/>

			<FragmentContentClickFilter element={element} />

			<FragmentContentFloatingToolbar
				element={element}
				fragmentEntryLinkId={fragmentEntryLinkId}
				onButtonClick={onFloatingToolbarButtonClick}
			/>

			<FragmentContentProcessor
				element={element}
				fragmentEntryLinkId={fragmentEntryLinkId}
			/>

			{element &&
				Array.from(
					element.querySelectorAll('lfr-editable')
				).map(editableElement => (
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

export default React.forwardRef(FragmentContent);

const editableIsMappedToInfoItem = editableValue =>
	editableValue &&
	editableValue.classNameId &&
	editableValue.classPK &&
	editableValue.fieldId;

const getMappingValue = ({classNameId, classPK, config, fieldId}) =>
	InfoItemService.getAssetFieldValue({
		classNameId,
		classPK,
		config,
		fieldId,
		onNetworkStatus: () => {}
	}).then(response => {
		const {fieldValue = ''} = response;

		return fieldValue;
	});

const resolveEditableValue = (
	state,
	config,
	fragmentEntryLinkId,
	editableId,
	processorType
) => {
	const editableValue = selectEditableValue(
		state,
		fragmentEntryLinkId,
		editableId,
		processorType
	);

	let valuePromise;

	if (editableIsMappedToInfoItem(editableValue)) {
		valuePromise = getMappingValue({
			classNameId: editableValue.classNameId,
			classPK: editableValue.classPK,
			config,
			fieldId: editableValue.fieldId
		});
	}
	else {
		valuePromise = Promise.resolve(
			selectEditableValueContent(
				state,
				config,
				fragmentEntryLinkId,
				editableId,
				processorType
			)
		);
	}

	let configPromise;

	if (editableIsMappedToInfoItem(editableValue.config)) {
		configPromise = getMappingValue({
			classNameId: editableValue.config.classNameId,
			classPK: editableValue.config.classPK,
			config,
			fieldId: editableValue.config.fieldId
		}).then(href => {
			return {...editableValue.config, href};
		});
	}
	else {
		configPromise = Promise.resolve(
			selectEditableValueConfig(
				state,
				fragmentEntryLinkId,
				editableId,
				processorType
			)
		);
	}

	return Promise.all([valuePromise, configPromise]);
};
