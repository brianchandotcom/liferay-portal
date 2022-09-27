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

import CurrentLanguageFlag from '../../../common/components/CurrentLanguageFlag';
import {LayoutSelector} from '../../../common/components/LayoutSelector';
import useControlledState from '../../../core/hooks/useControlledState';
import {useId} from '../../../core/hooks/useId';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import isMappedToLayout from '../../utils/editable-value/isMappedToLayout';

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
];

export default function URLField({field, onValueSelect, value}) {
	const [nextHref, setNextHref] = useControlledState(value.href || '');

	const [source, setSource] = useState(SOURCE_OPTION_MANUAL);

	useEffect(() => {
		if (isMappedToLayout(value)) {
			setSource(SOURCE_OPTION_FROM_LAYOUT);
		}
		else if (value.href) {
			setSource(SOURCE_OPTION_MANUAL);
		}
	}, [value]);

	const hrefInputId = useId();
	const sourceInputId = useId();

	const handleChange = (value) => {
		onValueSelect(field.name, value);
	};

	const handleSourceChange = (event) => {
		onValueSelect(field.name, {});
		setSource(event.target.value);
	};

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor={sourceInputId}>{field.label}</label>

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

						{field.localizable && (
							<ClayInput.GroupItem shrink>
								<CurrentLanguageFlag />
							</ClayInput.GroupItem>
						)}
					</ClayInput.Group>
				</ClayForm.Group>
			)}
			{source === SOURCE_OPTION_FROM_LAYOUT && (
				<LayoutSelector
					mappedLayout={value?.layout}
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
		</>
	);
}

URLField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([
		PropTypes.shape({
			classNameId: PropTypes.string,
			classPK: PropTypes.string,
			fieldId: PropTypes.string,
		}),

		PropTypes.shape({
			href: PropTypes.string,
		}),
	]),
};
