/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
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

export default function LayoutSetControl({
	label,
	onChange,
	pageTreeModalConfiguration,
	value,
}: Props) {
	const [showModal, setShowModal] = useState(false);

	const selection = (value && typeof value === 'object' ? value : {}) as {
		layoutIds?: number[];
	};
	const layoutIds = selection.layoutIds ?? [];
	const isAll = !!value && typeof value === 'object' && !selection.layoutIds;

	return (
		<div className="p-3">
			<ClayLayout.ContentRow className="mb-2">
				<ClayLayout.ContentCol className="pr-2" expand={false}>
					<ClayCheckbox
						checked={isAll}
						indeterminate={!isAll && !!layoutIds.length}
						onChange={() =>
							onChange(value ? undefined : {privateLayout: false})
						}
					/>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<div className="align-items-center d-flex justify-content-between">
						<span className="font-weight-semi-bold small">
							{label}
						</span>

						<ClayButton
							className="border-0 p-0"
							displayType="link"
							onClick={() => setShowModal(true)}
							small
						>
							{Liferay.Language.get('select-layouts')}
						</ClayButton>
					</div>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			{showModal && (
				<PageTreeModal
					{...pageTreeModalConfiguration}
					initialSelectedIds={layoutIds}
					onClose={() => setShowModal(false)}
					onSubmit={(result) => {
						setShowModal(false);

						onChange(result ?? undefined);
					}}
				/>
			)}
		</div>
	);
}
