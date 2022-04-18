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
import {ClayCardWithHorizontal} from '@clayui/card';
import ClayForm from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch, openToast} from 'frontend-js-web';
import React, {useState} from 'react';

export default function AddIconPackModal({
	icons,
	onSelectedPacksChange,
	onVisibleChange,
	portletNamespace,
	saveSiteSettingsURL,
	selectedPacks: initialSelectedPacks,
	visible,
}) {
	const iconPackNames = Object.keys(icons);

	const [loading, setLoading] = useState(false);

	const [selectedPacks, setSelectedPacks] = useState(initialSelectedPacks);

	const {observer, onClose} = useModal({
		onClose: () => {
			onVisibleChange(false);
		},
	});

	const handleSave = () => {
		setLoading(true);
		const formData = new FormData();

		formData.append(portletNamespace + 'selectedIconPacks', selectedPacks);

		return fetch(saveSiteSettingsURL, {
			body: formData,
			method: 'post',
		}).then(() => {
			setLoading(false);

			openToast({
				message: Liferay.Language.get('icon-packs-saved'),
				title: Liferay.Language.get('success'),
				toastProps: {
					autoClose: 5000,
				},
				type: 'success',
			});

			onSelectedPacksChange(selectedPacks);
			onClose();
		});
	};

	return (
		visible && (
			<ClayModal observer={observer} size="lg">
				<ClayModal.Header withTitle>
					{Liferay.Language.get('add-icon-pack')}
				</ClayModal.Header>

				<ClayModal.Body>
					<ClayForm
						onSubmit={(event) => {
							event.preventDefault();
						}}
					>
						<ClayForm.Group>
							<div className="row">
								{iconPackNames.map((packName, i) => (
									<div className="col-md-4" key={i}>
										<ClayCardWithHorizontal
											onSelectChange={() => {
												const newSelected = selectedPacks.includes(
													packName
												)
													? selectedPacks.filter(
															(name) =>
																name !==
																packName
													  )
													: [
															...selectedPacks,
															packName,
													  ];

												setSelectedPacks(newSelected);
											}}
											selected={selectedPacks.includes(
												packName
											)}
											title={packName}
										/>
									</div>
								))}
							</div>
						</ClayForm.Group>
					</ClayForm>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								disabled={loading}
								onClick={() => {
									handleSave();
								}}
								type="submit"
							>
								{loading ? (
									<ClayLoadingIndicator
										className="d-inline-block m-0"
										small
									/>
								) : (
									Liferay.Language.get('save')
								)}
							</ClayButton>

							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayModal>
		)
	);
}
