/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useState} from 'react';

import './LaunchDraftSelector.scss';

interface DraftLayout {
	active: boolean;
	disabled: boolean;
	href?: string;
	label: string;
	launchSetId: number;
}

interface Props {
	draftLayouts: DraftLayout[];
}

function DraftIcon({launchSetId}: {launchSetId: number}) {
	const launch = launchSetId > 0;

	return (
		<ClayIcon
			className={classNames('flex-shrink-0 mr-2', {
				'launch-draft-selector__icon--launch': launch,
				'launch-draft-selector__icon--main': !launch,
			})}
			symbol={launch ? 'arrow-split' : 'simple-circle'}
		/>
	);
}

export default function LaunchDraftSelector({draftLayouts}: Props) {
	const [active, setActive] = useState(false);

	const current =
		draftLayouts.find((draftLayout) => draftLayout.active) ??
		draftLayouts[0];

	if (!current) {
		return null;
	}

	return (
		<ClayDropDown
			active={active}
			menuElementAttrs={{className: 'launch-draft-selector__menu'}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					aria-label={Liferay.Language.get('select-draft')}
					className="form-control-select launch-draft-selector text-left text-truncate"
					displayType="secondary"
					size="sm"
				>
					<DraftIcon launchSetId={current.launchSetId} />

					{current.label}
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{draftLayouts.map((draftLayout, index) => (
					<React.Fragment key={draftLayout.launchSetId}>
						<ClayDropDown.Item
							active={draftLayout.active}
							aria-disabled={draftLayout.disabled || undefined}
							className="align-items-center d-flex"
							disabled={draftLayout.disabled}
							href={draftLayout.href}
						>
							<DraftIcon launchSetId={draftLayout.launchSetId} />

							{draftLayout.label}
						</ClayDropDown.Item>

						{index === 0 && draftLayouts.length > 1 && (
							<ClayDropDown.Divider />
						)}
					</React.Fragment>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
