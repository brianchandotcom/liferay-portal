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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useModal} from '@clayui/modal';
import getCN from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SelectSXPBlueprintModal from '../../../select_sxp_blueprint_modal/SelectSXPBlueprintModal';

function SXPBlueprintSelectorInput({
	onBlur,
	onSubmit,
	sxpBlueprintId,
	touched,
}) {
	const [showModal, setShowModal] = useState(false);
	const [sxpBlueprint, setSXPBlueprint] = useState({
		loading: false,
		title: '',
	});

	const {observer, onClose} = useModal({
		onClose: () => setShowModal(false),
	});

	const _handleChange = (event) => {

		// To use validation from 'required' field, keep the onChange and value
		// properties but make its behavior resemble readOnly (input can only be
		// changed with the selector modal).

		event.preventDefault();
	};

	const _handleClickRemove = () => {
		_handleSubmit('', '');

		onBlur();
	};

	const _handleClickSelect = () => {
		setShowModal(true);
	};

	const _handleSubmit = (id, title) => {
		onSubmit(id);

		setSXPBlueprint({loading: false, title});
	};

	useEffect(() => {

		// Fetch the blueprint title using sxpBlueprintId inside attributes, since
		// title is not saved within initialSuggestionsContributorConfiguration.

		if (sxpBlueprintId) {
			setSXPBlueprint({loading: true, title: ''});

			fetch(
				`/o/search-experiences-rest/v1.0/sxp-blueprints/${sxpBlueprintId}`,
				{
					headers: new Headers({
						'Accept': 'application/json',
						'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
						'Content-Type': 'application/json',
					}),
					method: 'GET',
				}
			)
				.then((response) =>
					response.json().then((data) => ({
						data,
						ok: response.ok,
					}))
				)
				.then(({data, ok}) => {
					setSXPBlueprint({
						loading: false,
						title:
							!ok || data.status === 'NOT_FOUND'
								? JSON.stringify(sxpBlueprintId)
								: data.title,
					});
				})
				.catch(() => {
					setSXPBlueprint({
						loading: false,
						title: JSON.stringify(sxpBlueprintId),
					});
				});
		}
	}, []); //eslint-disable-line

	return (
		<>
			{showModal && (
				<SelectSXPBlueprintModal
					observer={observer}
					onClose={onClose}
					onSubmit={_handleSubmit}
					selectedId={sxpBlueprintId || ''}
				/>
			)}

			<ClayInput.GroupItem
				className={getCN({
					'has-error': !sxpBlueprintId && touched,
				})}
			>
				<ClayForm.Group className="c-mb-0 w-100">
					<label>
						{Liferay.Language.get('blueprint')}

						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem prepend>
							<ClayInput
								className="bg-transparent form-control input-group-inset input-group-inset-after"
								onBlur={onBlur}
								onChange={_handleChange}
								required
								style={{caretColor: 'transparent'}}
								type="text"
								value={sxpBlueprint.title}
							/>

							<ClayInput.GroupInsetItem
								after
								className="bg-transparent rounded-0"
							>
								{sxpBlueprint.loading && (
									<ClayLoadingIndicator small />
								)}

								{sxpBlueprint.title && (
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
								onClick={_handleClickSelect}
							>
								{Liferay.Language.get('select')}
							</ClayButton>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			</ClayInput.GroupItem>
		</>
	);
}

export default SXPBlueprintSelectorInput;
