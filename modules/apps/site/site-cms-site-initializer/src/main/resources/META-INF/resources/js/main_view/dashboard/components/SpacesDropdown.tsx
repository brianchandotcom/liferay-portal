/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect, useState} from 'react';

import SpaceService from '../../../common/services/SpaceService';
import {ViewDashboardContext, initialSpace} from '../ViewDashboardContext';

type SpaceOption = {
	externalReferenceCode?: string;
	label: string;
	value: string;
};

const Trigger = React.forwardRef<HTMLButtonElement, any>(
	(
		{
			children,
			'className': _className,
			triggerClassName,
			triggerIcon,
			...otherProps
		},
		ref
	) => (
		<ClayButton
			{...otherProps}
			className={triggerClassName}
			displayType="secondary"
			ref={ref}
			size="sm"
		>
			{triggerIcon && <ClayIcon className="mr-2" symbol={triggerIcon} />}

			{children}

			<ClayIcon className="ml-2" symbol="caret-bottom" />
		</ClayButton>
	)
);

Trigger.displayName = 'Trigger';

const SpacesDropdown: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	className,
}) => {
	const {
		changeSpace,
		filters: {space},
	} = useContext(ViewDashboardContext);

	const [spaces, setSpaces] = useState<SpaceOption[]>([initialSpace]);

	useEffect(() => {
		const fetchSpaces = async () => {
			const spaces = await SpaceService.getSpaces();

			setSpaces([
				initialSpace,
				...spaces.map(({externalReferenceCode, id, name}) => ({
					externalReferenceCode,
					label: name,
					value: String(id),
				})),
			]);
		};

		fetchSpaces();
	}, []);

	return (
		<Picker
			aria-label={Liferay.Language.get('filter-by-spaces')}
			as={Trigger}
			filterKey="label"
			items={spaces}
			messages={{
				noResultsFound: Liferay.Language.get('no-results-were-found'),
				searchPlaceholder: Liferay.Language.get('search'),
			}}
			onSelectionChange={(key) => {
				const selectedSpace = spaces.find(
					({value}) => value === String(key)
				);

				if (selectedSpace) {
					changeSpace(selectedSpace);
				}
			}}
			searchable
			selectedKey={space.value}
			triggerClassName={className}
			triggerIcon="box-container"
		>
			{(item: SpaceOption) => (
				<Option key={item.value}>{item.label}</Option>
			)}
		</Picker>
	);
};

export {SpacesDropdown};
