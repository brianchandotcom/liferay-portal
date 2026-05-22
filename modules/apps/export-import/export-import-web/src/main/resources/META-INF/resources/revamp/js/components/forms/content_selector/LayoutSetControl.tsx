/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React, {useState} from 'react';

import PageTreeModal, {
	PageTreeModalConfiguration,
} from '../../../pages/export/components/PageTreeModal';
import {HandlerSelection} from '../../../utils/contentSelection';

interface Props {
	label: string;
	onChange: (value: HandlerSelection | undefined) => void;
	pageTreeModalConfiguration: PageTreeModalConfiguration;
	value: HandlerSelection | undefined;
}

function SelectPagesButton({onClick}: {onClick: () => void}) {
	return (
		<ClayButton
			className="border-0 ml-2 p-0"
			displayType="link"
			onClick={onClick}
			size="sm"
		>
			{Liferay.Language.get('select-layouts')}
		</ClayButton>
	);
}

function LayoutVisibilitySelector({
	onSelectPages,
	onSetMode,
	privateLayout,
}: {
	onSelectPages: () => void;
	onSetMode: (mode: boolean) => void;
	privateLayout: boolean;
}) {
	return (
		<div className="mt-2 pl-4">
			<div className="align-items-center d-flex mb-1">
				<ClayRadio
					checked={!privateLayout}
					containerProps={{className: 'mb-0'}}
					label={Liferay.Language.get('public-pages')}
					name="layoutSetPrivateLayout"
					onChange={() => onSetMode(false)}
					value="false"
				/>

				{!privateLayout && (
					<SelectPagesButton onClick={onSelectPages} />
				)}
			</div>

			<div className="align-items-center d-flex mb-1">
				<ClayRadio
					checked={privateLayout}
					containerProps={{className: 'mb-0'}}
					label={Liferay.Language.get('private-pages')}
					name="layoutSetPrivateLayout"
					onChange={() => onSetMode(true)}
					value="true"
				/>

				{privateLayout && <SelectPagesButton onClick={onSelectPages} />}
			</div>
		</div>
	);
}

export default function LayoutSetControl({
	label,
	onChange,
	pageTreeModalConfiguration,
	value,
}: Props) {
	const {privateLayoutsEnabled, ...modalConfiguration} =
		pageTreeModalConfiguration;

	const [showModal, setShowModal] = useState(false);

	const {layoutIds = [], privateLayout = false} = (
		typeof value === 'object' ? value : {}
	) as {layoutIds?: number[]; privateLayout?: boolean};

	const isAll = !!value && !layoutIds.length;

	const openModal = () => setShowModal(true);

	return (
		<div className="p-3">
			<ClayLayout.ContentRow className="mb-2">
				<ClayLayout.ContentCol className="pr-2" expand={false}>
					<ClayCheckbox
						checked={isAll}
						indeterminate={!isAll && !!layoutIds.length}
						onChange={() =>
							onChange(isAll ? undefined : {privateLayout})
						}
					/>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<div className="align-items-center d-flex justify-content-between">
						<span className="font-weight-semi-bold small">
							{label}
						</span>

						{!privateLayoutsEnabled && (
							<SelectPagesButton onClick={openModal} />
						)}
					</div>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			{privateLayoutsEnabled && (
				<LayoutVisibilitySelector
					onSelectPages={openModal}
					onSetMode={(next) => onChange({privateLayout: next})}
					privateLayout={privateLayout}
				/>
			)}

			{showModal && (
				<PageTreeModal
					{...modalConfiguration}
					initialSelectedIds={layoutIds}
					onClose={() => setShowModal(false)}
					onSubmit={(result) => {
						setShowModal(false);

						onChange(result ?? undefined);
					}}
					privateLayout={privateLayout}
				/>
			)}
		</div>
	);
}
