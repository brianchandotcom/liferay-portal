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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {ItemSelectorDialog, cancelDebounce, debounce} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import templates from './ImagePickerAdapter.soy';

const useDebounceCallback = (callback, milliseconds) => {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
};

const ReactImagePicker = ({
	dispatch,
	inputValue = '',
	itemSelectorURL,
	name,
	portletNamespace,
	readOnly
}) => {
	const [imageValues, setImageValues] = useState({});
	const [modalVisible, setModalVisible] = useState(false);

	useEffect(() => {
		setImageValues({
			...{description: '', title: '', url: ''},
			...JSON.parse(inputValue || '{}')
		});
	}, [inputValue]);

	const {observer} = useModal({
		onClose: () => setModalVisible(false)
	});

	const _dispatchValue = (value, clear) => {
		setImageValues(oldValues => {
			const mergedValues = {...oldValues, ...value};

			dispatch({
				payload: clear ? '' : JSON.stringify(mergedValues),
				type: 'value'
			});

			return mergedValues;
		});
	};

	const _handleClearClick = () => {
		_dispatchValue({description: '', title: '', url: ''}, true);
	};

	const [debouncedTest] = useDebounceCallback(value => {
		_dispatchValue({description: value}, false);
	}, 500);

	const _handleDescriptionChange = ({target: {value}}) =>
		debouncedTest(value);

	const _handleFieldChanged = event => {
		const selectedItem = event.selectedItem;

		if (selectedItem && selectedItem.value) {
			const img = new Image();
			const item = JSON.parse(selectedItem.value);
			img.addEventListener('load', function() {
				const {height, width} = this;

				_dispatchValue({
					...{description: '', height, title: '', url: '', width},
					...item
				});
			});
			img.src = item.url;
		}
	};

	const _handleItemSelectorTriggerClick = event => {
		event.preventDefault();

		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectDocumentLibrary`,
			singleSelect: true,
			url: itemSelectorURL
		});

		itemSelectorDialog.on('selectedItemChange', _handleFieldChanged);

		itemSelectorDialog.open();
	};

	const spritemap =
		Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';

	return (
		<>
			<ClayForm.Group style={{marginBottom: '0.5rem'}}>
				<ClayInput.Group>
					<ClayInput.GroupItem className="d-none d-sm-block" prepend>
						<input name={name} type="hidden" value={inputValue} />

						<ClayInput
							className="bg-light"
							disabled={readOnly}
							onClick={_handleItemSelectorTriggerClick}
							readOnly
							type="text"
							value={imageValues.title}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							disabled={readOnly}
							displayType="secondary"
							onClick={_handleItemSelectorTriggerClick}
							type="button"
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>

					{imageValues.url && (
						<ClayInput.GroupItem shrink>
							<ClayButton
								disabled={readOnly}
								displayType="secondary"
								onClick={_handleClearClick}
								type="button"
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			</ClayForm.Group>
			{imageValues.url && modalVisible && (
				<ClayModal
					className="image-picker-preview-modal"
					observer={observer}
					size="full-screen"
					spritemap={spritemap}
				>
					<ClayModal.Header />
					<ClayModal.Body>
						<img
							alt={imageValues.description}
							className="d-block img-fluid mb-2 mx-auto rounded"
							src={imageValues.url}
							style={{maxHeight: '95%'}}
						/>
						<p
							className="font-weight-light text-center"
							style={{color: '#FFFFFF'}}
						>
							{imageValues.description}
						</p>
					</ClayModal.Body>
				</ClayModal>
			)}

			{imageValues.url && (
				<>
					<img
						alt={imageValues.description}
						className="d-block img-fluid mb-2 rounded"
						onClick={() => setModalVisible(true)}
						src={imageValues.url}
						style={{cursor: 'zoom-in'}}
					/>

					<ClayInput
						defaultValue={imageValues.description}
						disabled={readOnly}
						name={`${name}-description`}
						onChange={_handleDescriptionChange}
						placeholder={Liferay.Language.get(
							'add-image-description'
						)}
						type="text"
					/>
				</>
			)}
		</>
	);
};

const ReactImagePickerAdapter = getConnectedReactComponentAdapter(
	ReactImagePicker,
	templates
);

export {ReactImagePickerAdapter};
export default ReactImagePickerAdapter;
