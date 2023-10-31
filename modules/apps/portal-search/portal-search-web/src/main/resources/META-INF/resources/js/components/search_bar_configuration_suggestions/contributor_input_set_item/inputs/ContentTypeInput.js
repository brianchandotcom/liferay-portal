/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React, {useState} from 'react';

const CONTENT_TYPES = [
	{
		className: 'blog',
		displayName: Liferay.Language.get(
			'model.resource.com.liferay.blogs.model.BlogsEntry'
		),
	},
	{
		className: 'document',
		displayName: Liferay.Language.get(
			'model.resource.com.liferay.portal.kernel.repository.model.FileEntry'
		),
	},
	{
		className: 'form',
		displayName: Liferay.Language.get(
			'model.resource.com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord'
		),
	},
	{
		className: 'web-content',
		displayName: Liferay.Language.get(
			'model.resource.com.liferay.journal.model.JournalArticle'
		),
	},
];

export default function ContentTypeInput({onBlur, onChange, touched, value}) {
	const [activeDropdown, setActiveDropdown] = useState(false);

	const _handleChange = (event) => {

		// To use validation from 'required' field, keep the onChange and value
		// properties but make its behavior resemble readOnly (input can only be
		// changed with the selector modal).

		event.preventDefault();
	};

	return (
		<ClayInput.GroupItem
			className={getCN({
				'has-error': !value && touched,
			})}
		>
			<label>
				{Liferay.Language.get('content-type')}

				<span className="reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>

				<ClayTooltipProvider>
					<span
						className="c-ml-2"
						data-tooltip-align="top"
						title={Liferay.Language.get('content-type-help')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</ClayTooltipProvider>
			</label>

			<ClayInput.Group>
				<ClayInput.Group>
					<ClayDropDown
						active={activeDropdown}
						className="w-100"
						closeOnClick={true}
						closeOnClickOutside={true}
						onActiveChange={(value) => {
							if (!value) {
								onBlur();
							}

							setActiveDropdown(value);
						}}
						trigger={
							<ClayInput
								className="form-control form-control-select"
								onChange={_handleChange}
								placeholder={Liferay.Util.sub(
									Liferay.Language.get('select-x'),
									Liferay.Language.get('content-type')
								)}
								required
								style={{
									caretColor: 'transparent',
									cursor: 'pointer',
								}}
								type="text"
								value={
									CONTENT_TYPES.find(
										({className}) => className === value
									)?.displayName
								}
							/>
						}
					>
						<ClayDropDown.ItemList items={CONTENT_TYPES}>
							{({className, displayName}) => (
								<ClayDropDown.Item
									key={className}
									name={className}
									onClick={() => onChange(className)}
								>
									{displayName}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayInput.Group>
			</ClayInput.Group>
		</ClayInput.GroupItem>
	);
}
