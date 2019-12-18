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
import React, {useContext, useEffect, useState, useRef} from 'react';

import {ConfigContext} from '../config/index';
import * as Processors from '../processors/index';
import selectEditableValueContent from '../selectors/selectEditableValueContent';
import {StoreContext} from '../store/index';
import {useSelectItem} from './Controls';
import UnsafeHTML from './UnsafeHTML';
import {showFloatingToolbar} from './showFloatingToolbar';

const resolveEditableValue = (state, config, fragmentEntryLinkId, editableId) =>
	new Promise(resolve => {
		resolve(
			selectEditableValueContent(
				state,
				config,
				fragmentEntryLinkId,
				editableId
			)
		);
	});

function FragmentContent({fragmentEntryLink}, ref) {
	const config = useContext(ConfigContext);
	const defaultContent = fragmentEntryLink.content.value.content;
	const {fragmentEntryLinkId} = fragmentEntryLink;
	const isMounted = useIsMounted();
	const state = useContext(StoreContext);

	const selectItem = useSelectItem();
	const activeEditable = useRef(null);

	const [content, setContent] = useState(defaultContent);

	const [hasEditableActive, setHasEditableActive] = useState(false);

	useEffect(() => {
		let element = document.createElement('div');
		element.innerHTML = defaultContent;

		const updateContent = debounce(() => {
			if (isMounted()) {
				setContent(element.innerHTML);
			}
		}, 50);

		Array.from(element.querySelectorAll('lfr-editable')).forEach(
			editable => {
				resolveEditableValue(
					state,
					config,
					fragmentEntryLinkId,
					editable.getAttribute('id')
				).then(value => {
					if (element) {
						const processor =
							Processors[editable.getAttribute('type')] ||
							Processors.fallback;

						processor.render(editable, value);

						updateContent();
					}
				});
			}
		);

		return () => {
			element = null;
		};
	}, [state, config, defaultContent, fragmentEntryLinkId, isMounted]);

	const onDoubleClick = event => {
		const target = event.target;

		const editable = target.closest('lfr-editable');

		if (editable) {
			const editableId = `${fragmentEntryLinkId}-${editable.getAttribute(
				'id'
			)}`;

			if (activeEditable.current) {
				destroyProcessor(
					activeEditable.current,
					activeEditable.current.getAttribute('type')
				);
			}

			activeEditable.current = editable;

			initProcessor(
				editable,
				editable.getAttribute('id'),
				editable.getAttribute('type'),
				fragmentEntryLinkId,
				state,
				config
			);

			selectItem(editableId);

			setHasEditableActive(true);
		} else {
			if (activeEditable.current) {
				destroyProcessor(
					activeEditable.current,
					activeEditable.current.getAttribute('type')
				);
				activeEditable.current = null;

				setHasEditableActive(false);

				selectItem(null);
			}
		}
	};

	return (
		<>
			{hasEditableActive &&
				showFloatingToolbar(activeEditable, fragmentEntryLinkId)}
			<UnsafeHTML
				markup={content}
				onDoubleClick={onDoubleClick}
				ref={ref}
			/>
		</>
	);
}

function initProcessor(
	element,
	editableId,
	editableType,
	fragmentEntryLinkId,
	store,
	config
) {
	const processor = Processors[editableType] || Processors.fallback;

	processor.enableEditor(
		element,
		editableId,
		fragmentEntryLinkId,
		store,
		config
	);
}

function destroyProcessor(element, editableType) {
	const processor = Processors[editableType] || Processors.fallback;

	processor.disableEditor(element);
}

export default React.forwardRef(FragmentContent);
