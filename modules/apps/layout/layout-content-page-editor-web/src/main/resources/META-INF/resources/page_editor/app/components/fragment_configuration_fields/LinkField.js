/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelectWithOption,
} from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import CurrentLanguageFlag from '../../../common/components/CurrentLanguageFlag';
import {LayoutSelector} from '../../../common/components/LayoutSelector';
import MappingSelector from '../../../common/components/MappingSelector';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {useGetFieldValue} from '../../contexts/CollectionItemContext';
import {useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import isMapped from '../../utils/editable_value/isMapped';
import isMappedToLayout from '../../utils/editable_value/isMappedToLayout';
import isMappedToStructure from '../../utils/editable_value/isMappedToStructure';
import resolveEditableValue from '../../utils/editable_value/resolveEditableValue';

const SOURCE_OPTION_FROM_CONTENT_FIELD = 'fromContentField';
const SOURCE_OPTION_FROM_LAYOUT = 'fromLayout';
const SOURCE_OPTION_MANUAL = 'manual';

const SOURCE_OPTIONS = [
	{
		label: Liferay.Language.get('url'),
		value: SOURCE_OPTION_MANUAL,
	},
	{
		label: Liferay.Language.get('page'),
		value: SOURCE_OPTION_FROM_LAYOUT,
	},
	{
		label: Liferay.Language.get('mapped-url'),
		value: SOURCE_OPTION_FROM_CONTENT_FIELD,
	},
];

export const TARGET_OPTIONS = {
	blank: '_blank',
	parent: '_parent',
	self: '_self',
	top: '_top',
};

export default function LinkField({field, onValueSelect, value}) {
	const getFieldValue = useGetFieldValue();
	const [nextHref, setNextHref] = useControlledState(value.href || '');
	const [nextValue, setNextValue] = useControlledState(value || {});
	const [openNewTab, setOpenNewTab] = useControlledState(
		value.target === '_blank' || ''
	);

	const [mappedHrefPreview, setMappedHrefPreview] = useState(null);
	const languageId = useSelector(selectLanguageId);

	const [source, setSource] = useState(SOURCE_OPTION_MANUAL);

	useEffect(() => {
		if (isMappedToLayout(value)) {
			setSource(SOURCE_OPTION_FROM_LAYOUT);
		}
		else if (isMapped(value)) {
			setSource(SOURCE_OPTION_FROM_CONTENT_FIELD);
		}
		else if (value.href) {
			setSource(SOURCE_OPTION_MANUAL);
		}
	}, [value]);

	const hrefInputId = useId();
	const hrefPreviewInputId = useId();
	const sourceInputId = useId();
	const targetInputId = useId();

	useEffect(() => {
		if (isMapped(nextValue) && !isMappedToStructure(nextValue)) {
			setMappedHrefPreview('');

			resolveEditableValue(
				nextValue,
				languageId,
				getFieldValue
			).then((href) =>
				setMappedHrefPreview(typeof href === 'string' ? href : '')
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
					options={SOURCE_OPTIONS}
					value={source}
				/>
			</ClayForm.Group>

			{source === SOURCE_OPTION_MANUAL && (
				<ClayForm.Group>
					<label htmlFor={hrefInputId}>
						{Liferay.Language.get('url')}
					</label>

					<ClayInput.Group small>
						<ClayInput.GroupItem>
							<ClayInput
								id={hrefInputId}
								onBlur={() => handleChange({href: nextHref})}
								onChange={(event) =>
									setNextHref(event.target.value)
								}
								type="text"
								value={nextHref || ''}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<CurrentLanguageFlag />
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			)}

			{source === SOURCE_OPTION_FROM_LAYOUT && (
				<LayoutSelector
					mappedLayout={nextValue?.layout}
					onLayoutSelect={(layout) => {
						if (layout && !!Object.keys(layout).length) {
							handleChange({layout});
						}
						else {
							handleChange({});
						}
					}}
				/>
			)}

			{source === SOURCE_OPTION_FROM_CONTENT_FIELD && (
				<>
					<MappingSelector
						fieldType={EDITABLE_TYPES.link}
						filterLinkTypes
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
