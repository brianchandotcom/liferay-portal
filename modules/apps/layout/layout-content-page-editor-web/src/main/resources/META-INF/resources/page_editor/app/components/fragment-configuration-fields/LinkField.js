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

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import MappingSelector from '../../../common/components/MappingSelector';
import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import InfoItemService from '../../services/InfoItemService';
import {useSelector} from '../../store/index';
import {useId} from '../../utils/useId';

const SOURCE_OPTIONS = {
	fromContentField: {
		label: `${Liferay.Language.get('from-content-field')}`,
		value: 'fromContentField',
	},

	manual: {
		label: `${Liferay.Language.get('manual')}`,
		value: 'manual',
	},
};

export const TARGET_OPTIONS = [
	{
		label: `${Liferay.Language.get('self')}`,
		value: '_self',
	},
	{
		label: `${Liferay.Language.get('blank')}`,
		value: '_blank',
	},
	{
		label: `${Liferay.Language.get('parent')}`,
		value: '_parent',
	},
	{
		label: `${Liferay.Language.get('top')}`,
		value: '_top',
	},
];

export default function LinkField({field, onValueSelect, value}) {
	const [nextValue, setNextValue] = useControlledState(value || {});
	const [nextHref, setNextHref] = useControlledState(nextValue.href);

	const [mappedHrefPreview, setMappedHrefPreview] = useState(null);
	const languageId = useSelector((state) => state.languageId);

	const [source, setSource] = useState(
		value.fieldId || value.mappedField
			? SOURCE_OPTIONS.fromContentField.value
			: SOURCE_OPTIONS.manual.value
	);

	const hrefInputId = useId();
	const hrefPreviewInputId = useId();
	const sourceInputId = useId();
	const targetInputId = useId();

	useEffect(() => {
		if (nextValue.classNameId && nextValue.classPK && nextValue.fieldId) {
			setMappedHrefPreview('');

			InfoItemService.getInfoItemFieldValue({
				...nextValue,
				languageId,
				onNetworkStatus: () => {},
			}).then(({fieldValue}) => {
				setMappedHrefPreview(fieldValue || '');
			});
		}
		else {
			setMappedHrefPreview(null);
		}
	}, [languageId, nextValue]);

	const handleChange = (value) => {
		const updatedValue = {
			...nextValue,
			...value,
		};

		onValueSelect(field.name, updatedValue);
		setNextValue(updatedValue);
	};

	const handleSourceChange = (event) => {
		onValueSelect(field.name, {});
		setNextValue({});
		setSource(event.target.value);
		setMappedHrefPreview(null);
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
					options={Object.values(SOURCE_OPTIONS)}
					value={source}
				/>
			</ClayForm.Group>

			{source === SOURCE_OPTIONS.manual.value && (
				<ClayForm.Group small>
					<label htmlFor={hrefInputId}>
						{Liferay.Language.get('url')}
					</label>

					<ClayInput
						id={hrefInputId}
						onBlur={() => handleChange({href: nextHref})}
						onChange={(event) => setNextHref(event.target.value)}
						type="text"
						value={nextHref || ''}
					/>
				</ClayForm.Group>
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

			<ClayForm.Group small>
				<label htmlFor={targetInputId}>
					{Liferay.Language.get('target')}
				</label>

				<ClaySelectWithOption
					id={targetInputId}
					onChange={(event) =>
						handleChange({target: event.target.value})
					}
					options={TARGET_OPTIONS}
					value={nextValue.target}
				/>
			</ClayForm.Group>
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
			target: PropTypes.oneOf(
				TARGET_OPTIONS.map((option) => option.value)
			),
		}),

		PropTypes.shape({
			href: PropTypes.string,
			target: PropTypes.oneOf(
				TARGET_OPTIONS.map((option) => option.value)
			),
		}),

		PropTypes.shape({
			mappedField: PropTypes.string,
			target: PropTypes.oneOf(
				TARGET_OPTIONS.map((option) => option.value)
			),
		}),
	]),
};
