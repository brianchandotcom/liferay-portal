/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React from 'react';

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
		displayName: Liferay.Language.get('model.resource.com.liferay.journal'),
	},
];

function ContentTypeModal({
	initialSelectedType = '',
	observer,
	onBlur,
	onChange,
	onClose,
}) {
	const _handleCancel = () => {
		onClose();
		onBlur();
	};

	const _handleSelect = (type) => {
		onChange(type);

		_handleCancel();
	};

	return (
		<ClayModal className="modal-height-xl" observer={observer} size="lg">
			<ClayModal.Header>
				{Liferay.Language.get('select-types')}
			</ClayModal.Header>

			<ClayModal.Body scrollable>
				<ClayTable>
					<ClayTable.Body>
						{CONTENT_TYPES.map(({className, displayName}) => {
							return (
								<ClayTable.Row key={className}>
									<ClayTable.Cell expanded headingTitle>
										{displayName}
									</ClayTable.Cell>

									<ClayTable.Cell className="text-right">
										<ClayButton
											aria-label={Liferay.Util.sub(
												Liferay.Language.get(
													'select-x'
												),
												[displayName]
											)}
											disabled={
												className ===
												initialSelectedType
											}
											displayType="secondary"
											onClick={() =>
												_handleSelect(className)
											}
										>
											{className === initialSelectedType
												? Liferay.Language.get(
														'selected'
												  )
												: Liferay.Language.get(
														'select'
												  )}
										</ClayButton>
									</ClayTable.Cell>
								</ClayTable.Row>
							);
						})}
					</ClayTable.Body>
				</ClayTable>
			</ClayModal.Body>
		</ClayModal>
	);
}

export default function ContentTypeInput({onBlur, onChange, touched, value}) {
	const {observer, onOpenChange, open} = useModal();

	const _handleChange = (event) => {

		// To use validation from 'required' field, keep the onChange and value
		// properties but make its behavior resemble readOnly (input can only be
		// changed with the selector modal).

		event.preventDefault();
	};

	const _handleClickRemove = () => {
		onChange('');

		onBlur();
	};

	const _handleClose = () => {
		onOpenChange(false);
	};

	const _handleOpen = () => {
		onOpenChange(true);
	};

	return (
		<>
			{open && (
				<ContentTypeModal
					initialSelectedType={value}
					observer={observer}
					onBlur={onBlur}
					onChange={onChange}
					onClose={_handleClose}
				/>
			)}

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
						<ClayInput.GroupItem prepend>
							<ClayInput
								className="bg-transparent form-control input-group-inset input-group-inset-after"
								onBlur={onBlur}
								onChange={_handleChange}
								required
								style={{caretColor: 'transparent'}}
								type="text"
								value={
									value
										? CONTENT_TYPES.find(
												({className}) =>
													className === value
										  )?.displayName
										: ''
								}
							/>

							<ClayInput.GroupInsetItem
								after
								className="bg-transparent rounded-0"
							>
								{value && (
									<ClayButtonWithIcon
										displayType="unstyled"
										onClick={_handleClickRemove}
										symbol="times-circle"
									/>
								)}
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append shrink>
							<ClayButton
								displayType="secondary"
								onClick={_handleOpen}
							>
								{Liferay.Language.get('select')}
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayInput.Group>
			</ClayInput.GroupItem>
		</>
	);
}
