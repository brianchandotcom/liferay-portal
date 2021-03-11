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

import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelectWithOption,
} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import CurrentLanguageFlag from '../../../common/components/CurrentLanguageFlag';
import ItemSelector from '../../../common/components/ItemSelector';
import {LayoutSelector} from '../../../common/components/LayoutSelector';
import MappingSelector from '../../../common/components/MappingSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {config} from '../../config/index';
import selectLanguageId from '../../selectors/selectLanguageId';
import {useSelector} from '../../store/index';
import isMapped from '../../utils/editable-value/isMapped';
import isMappedToLayout from '../../utils/editable-value/isMappedToLayout';
import isMappedToStructure from '../../utils/editable-value/isMappedToStructure';
import resolveEditableValue from '../../utils/editable-value/resolveEditableValue';
import itemSelectorValueToInfoItem from '../../utils/item-selector-value/itemSelectorValueToInfoItem';
import {useId} from '../../utils/useId';
import {useGetFieldValue} from '../CollectionItemContext';

const DISPLAY_PAGE_URL_FIELD_ID = 'displayPageURL';

const SOURCE_OPTIONS = {
	fromContentField: {
		label: Liferay.Language.get('from-content-field'),
		value: 'fromContentField',
	},

	fromItemDisplayPage: {
		label: Liferay.Language.get('display-page'),
		value: 'fromItemDisplayPage',
	},

	fromLayout: {
		label: Liferay.Language.get('page'),
		value: 'fromLayout',
	},

	manual: {
		label: Liferay.Language.get('manual'),
		value: 'manual',
	},
};

export const TARGET_OPTIONS = {
	blank: '_blank',
	parent: '_parent',
	self: '_self',
	top: '_top',
};

export default function LinkField({field, onValueSelect, value}) {
	const getFieldValue = useGetFieldValue();
	const [nextValue, setNextValue] = useState({});
	const [nextHref, setNextHref] = useState('');
	const [openNewTab, setOpenNewTab] = useState('');

	const [mappedHrefPreview, setMappedHrefPreview] = useState(null);
	const languageId = useSelector(selectLanguageId);

	const [source, setSource] = useState(SOURCE_OPTIONS.manual.value);

	useEffect(() => {
		setNextValue(value);
		setNextHref(value.href);
		setOpenNewTab(value.target === '_blank');

		if (isMappedToLayout(value)) {
			setSource(SOURCE_OPTIONS.fromLayout.value);
		}
		else if (isMapped(value)) {
			setSource(SOURCE_OPTIONS.fromContentField.value);
		}
		else if (value.href) {
			setSource(SOURCE_OPTIONS.manual.value);
		}
	}, [value]);

	const hrefInputId = useId();
	const hrefPreviewInputId = useId();
	const sourceInputId = useId();
	const targetInputId = useId();

	useEffect(() => {
		if (isMapped(nextValue) && !isMappedToStructure(nextValue)) {
			setMappedHrefPreview('');

			resolveEditableValue(nextValue, languageId, getFieldValue).then(
				(href) => {
					setMappedHrefPreview(href || '');
				}
			);
		}
		else {
			setMappedHrefPreview(null);
		}
	}, [languageId, nextValue, getFieldValue]);

	const handleChange = (value) => {
		const updatedValue = {
			...(Object.keys(value).length && nextValue),
			...value,
		};

		onValueSelect(field.name, updatedValue);
		setNextValue(updatedValue);
	};

	const handleSourceChange = (event) => {
		onValueSelect(field.name, {});
		setNextValue({});
		setSource(event.target.value);
	};

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor={sourceInputId}>
					{Liferay.Language.get('link')}
				</label>

				<ClaySelectWithOption
					id={sourceInputId}
					onChange={handleSourceChange}
					options={
						config.layoutMappingEnabled
							? Object.values(SOURCE_OPTIONS)
							: Object.values(SOURCE_OPTIONS).filter(
									({value}) =>
										value !== 'fromItemDisplayPage' &&
										value !== 'fromLayout'
							  )
					}
					value={source}
				/>
			</ClayForm.Group>

			{source === SOURCE_OPTIONS.manual.value && (
				<div className="autofit-row mb-3">
					<div className="autofit-col autofit-col-expand">
						<ClayForm.Group small>
							<label htmlFor={hrefInputId}>
								{Liferay.Language.get('url')}
							</label>

							<ClayInput
								id={hrefInputId}
								onBlur={() => handleChange({href: nextHref})}
								onChange={(event) =>
									setNextHref(event.target.value)
								}
								type="text"
								value={nextHref || ''}
							/>
						</ClayForm.Group>
					</div>
					<CurrentLanguageFlag />
				</div>
			)}

			{source === SOURCE_OPTIONS.fromLayout.value && (
				<LayoutSelector
					mappedLayout={nextValue?.layout}
					onLayoutSelect={(layout) => handleChange({layout})}
				/>
			)}

			{source === SOURCE_OPTIONS.fromContentField.value && (
				<>
					<MappingSelector
						fieldType={EDITABLE_TYPES.link}
						mappedItem={nextValue}
						onMappingSelect={(mappedItem) =>
							handleChange(mappedItem)
						}
					/>

					{mappedHrefPreview !== null && (
						<ClayForm.Group small>
							<label htmlFor={hrefPreviewInputId}>
								{Liferay.Language.get('url')}
							</label>

							<ClayInput
								id={hrefPreviewInputId}
								readOnly
								value={mappedHrefPreview}
							/>
						</ClayForm.Group>
					)}
				</>
			)}

			{source === SOURCE_OPTIONS.fromItemDisplayPage.value && (
				<ItemSelector
					label={Liferay.Language.get('item')}
					onItemSelect={(item) =>
						handleChange({
							...item,
							fieldId: DISPLAY_PAGE_URL_FIELD_ID,
						})
					}
					selectedItemTitle={nextValue?.title}
					transformValueCallback={itemSelectorValueToInfoItem}
				/>
			)}

			<ClayCheckbox
				aria-label={Liferay.Language.get('open-in-a-new-tab')}
				checked={openNewTab}
				id={targetInputId}
				label={Liferay.Language.get('open-in-a-new-tab')}
				onChange={(event) => {
					setOpenNewTab(event.target.checked);
					handleChange({
						target: event.target.checked
							? TARGET_OPTIONS.blank
							: TARGET_OPTIONS.self,
					});
				}}
			/>
		</>
	);
}

LinkField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([
		PropTypes.shape({
			classNameId: PropTypes.string,
			classPK: PropTypes.string,
			fieldId: PropTypes.string,
			target: PropTypes.string,
		}),

		PropTypes.shape({
			href: PropTypes.string,
			target: PropTypes.string,
		}),

		PropTypes.shape({
			mappedField: PropTypes.string,
			target: PropTypes.string,
		}),
	]),
};
